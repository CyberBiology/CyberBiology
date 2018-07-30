package ru.cyberbiology.simulation.record.v0;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import ru.cyberbiology.simulation.Bot;
import ru.cyberbiology.simulation.prototype.IBot;
import ru.cyberbiology.simulation.prototype.IWorld;
import ru.cyberbiology.simulation.prototype.record.AbstractRecordManager;
import ru.cyberbiology.simulation.prototype.record.IFrame;

/**
 * @author Nickolay
 */
public class RecordManager extends AbstractRecordManager
{
    static final int VERSION = 0;
    public static final int botDataLength = 14 + Bot.MIND_SIZE;


    public RecordManager(IWorld world)
    {
        super(world);
    }


    @Override
    protected IFrame newFrame()
    {

        return new Frame();
    }


    /**
     * Метод вызывается отдельным потоком
     */
    @Override
    protected void save()
    {
        try
        {
            started = true;
            String dirName = getWorld().getProperties().getFileDirectory();
            new File(dirName).mkdirs();

            //Создаем временный файл с данными
            File tmpfile = new File(dirName + "data.tmp.zip");

            ZipOutputStream fileout = new ZipOutputStream(new FileOutputStream(tmpfile));
            fileout.putNextEntry(new ZipEntry("data"));

            DataOutputStream out = new DataOutputStream(fileout);

            //Записываем данные
            while (started)
            {
                if (frames.size() > 0)
                {
                    IFrame frame = frames.remove(0);
                    int fs = frame.save(out);
                    frameSavedCounter++;
                    fameSizes.add(fs);//подсчитываем количество и длинну фреймов
                    //System.out.println("Frame set size = "+frames.size());
                }
                Thread.sleep(20);
            }
            //дописываем буфер
            while (frames.size() > 0)
            {
                IFrame frame = frames.remove(0);
                int fs = frame.save(out);
                frameSavedCounter++;
                fameSizes.add(fs);//подсчитываем количество и длинну фреймов
                //System.out.println("Frame set size = "+frames.size());
            }
            out.writeInt(0);// Следующий фрейм размером 0 - больше данных нет, конец записи

            fileout.closeEntry();
            out.flush();
            out.close();

            //создаем финальный архив
            String fileName = formatter.format(new Date()) + ".cb";
            file = new File(dirName + fileName + ".zip");
            fileout = new ZipOutputStream(new FileOutputStream(file));
            //вначале архива вписываем общую информацию по кадрам
            fileout.putNextEntry(new ZipEntry("info"));

            out = new DataOutputStream(fileout);
            // Версия
            out.writeInt(getVersion());
            // Ширина мира
            out.writeInt(getWidth());
            // Высота мира
            out.writeInt(getHeight());
            // общее количество кадров
            out.writeInt(fameSizes.size());
            // вписываем длинну каждого кадра
            for (int i = 0; i < fameSizes.size(); i++)
            {
                out.writeInt(i);
                out.writeInt(fameSizes.get(i));
            }
            // Вписываем данные по кадрам
            fileout.putNextEntry(new ZipEntry("data"));
            {
                ZipInputStream in = new ZipInputStream(new FileInputStream(tmpfile));
                in.getNextEntry();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) >= 0)
                {
                    out.write(buffer, 0, len);
                }
                in.close();
            }
            out.close();
            // Удаляем временный файл
            tmpfile.delete();
            out = null;
            //file=null;
            thread = null;

        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Override
    public void makeSnapShot()
    {
        try
        {
            String dirName = getWorld().getProperties().getFileDirectory();
            new File(dirName).mkdirs();

            //Создаем временный файл с данными
            String fileName = formatter.format(new Date()) + "";
            file = new File(dirName + fileName + ".frame.cb.zip");
            ZipOutputStream fileout = new ZipOutputStream(new FileOutputStream(file));
            fileout.putNextEntry(new ZipEntry("data"));

            DataOutputStream out = new DataOutputStream(fileout);
            ;

            // Версия
            out.writeInt(getVersion());
            int width = getWorld().getWidth();
            // Ширина мира
            out.writeInt(width);
            int height = getWorld().getHeight();
            // Высота мира
            out.writeInt(height);

            Frame frame = (Frame) this.newFrame();
            Bot[][] w = this.getWorld().getWorldArray();
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    if (w[x][y] != null)
                    {
                        frame.addBot(w[x][y], x, y);
                    }
                }
            }
            frame.save(out);
            out.writeInt(0);// Следующий фрейм размером 0 - больше данных нет, конец записи

            fileout.closeEntry();
            out.close();

        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    class Frame implements IFrame
    {
        List<Item> list;


        public Frame()
        {
            this.list = new ArrayList<Item>();
        }


        public int save(DataOutputStream fileout) throws IOException
        {
            int length = list.size() * botDataLength;
            fileout.writeInt(length);
            for (int i = 0; i < list.size(); i++)
            {
                list.get(i).save(fileout);
            }
            return length;
        }


        public void addBot(IBot bot, int x, int y)
        {
            this.list.add(new Item((Bot) bot, x, y));
        }
    }

    class Item
    {
        //int[] data;
        //Bot bot;

        byte bot_adr;
        int bot_x;
        int bot_y;
        int bot_health;
        int bot_mineral;
        byte bot_alive;
        int bot_c_red;
        int bot_c_green;
        int bot_c_blue;
        byte bot_direction;
        int bot_mprev_x;
        int bot_mprev_y;
        int bot_mnext_x;
        int bot_mnext_y;
        byte[] mind;


        public Item(Bot bot, int x, int y)
        {
            // жестко сохраняем все зхначения, так как к моменту сохранения кадра данные могут изменится
            bot_adr = (byte) bot.adr;
            bot_x = bot.x;
            bot_y = bot.y;
            bot_health = bot.health;
            bot_mineral = bot.mineral;
            bot_alive = (byte) bot.alive;
            bot_c_red = bot.c_red;
            bot_c_green = bot.c_green;
            bot_c_blue = bot.c_blue;
            bot_direction = (byte) bot.direction;

            if (bot.mprev != null)
            {
                bot_mprev_x = bot.mprev.x;
                bot_mprev_y = bot.mprev.y;
            }
            else
            {
                bot_mprev_x = bot_mprev_y = -1;
            }
            if (bot.mnext != null)
            {
                bot_mnext_x = bot.mnext.x;
                bot_mnext_y = bot.mnext.y;
            }
            else
            {
                bot_mnext_x = bot_mnext_y = -1;
            }
            mind = new byte[bot.mind.length];
            for (int i = 0; i < bot.mind.length; i++)
            {
                mind[i] = bot.mind[i];
            }
        }


        public void save(DataOutputStream fileout) throws IOException
        {
            fileout.writeByte(bot_adr);
            fileout.writeInt(bot_x);
            fileout.writeInt(bot_y);
            fileout.writeInt(bot_health);
            fileout.writeInt(bot_mineral);
            fileout.writeByte(bot_alive);
            fileout.writeInt(bot_c_red);
            fileout.writeInt(bot_c_green);
            fileout.writeInt(bot_c_blue);
            fileout.writeByte(bot_direction);
            fileout.writeInt(bot_mprev_x);
            fileout.writeInt(bot_mprev_y);
            fileout.writeInt(bot_mnext_x);
            fileout.writeInt(bot_mnext_y);
            for (int i = 0; i < mind.length; i++)
            {
                fileout.writeByte(mind[i]);
            }
        }
    }


    @Override
    public int getVersion()
    {
        return VERSION;
    }
}

package ru.cyberbiology.simulation.record.v0;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import ru.cyberbiology.simulation.Bot;
import ru.cyberbiology.simulation.World;
import ru.cyberbiology.simulation.prototype.IWorld;
import ru.cyberbiology.simulation.prototype.record.IFrame;
/**

* @author Nickolay
*
*/
public class PlaybackManager
{
	IWorld world;
	int width;
	int height;
	int frameCount;
	int[] frameSizes;
	List<IFrame> frames;
	//IFrame frame;
	Worker thread;
	File file;
	DataInputStream in;
	
	int frameNo	= 0;
	public PlaybackManager(IWorld world,File file)
	{
		this.world	= world;
		this.file	= file;
		
		try
		{
			if(file.getName().endsWith(".frame.cb.zip"))
			{
				ZipInputStream filein	= new ZipInputStream((new FileInputStream(file)));
				ZipEntry zipEntry	= filein.getNextEntry();
				in = new DataInputStream(filein);
				int version 	= in.readInt();
				this.width		= in.readInt();
				this.height		= in.readInt();
				world.setSize(width,height);
				thread = new Worker();
				thread.run();// Запускаем как метод, без потока
			}else
			{
				ZipInputStream filein	= new ZipInputStream((new FileInputStream(file)));
				ZipEntry zipEntry	= filein.getNextEntry();
				in = new DataInputStream(filein);
				int version 	= in.readInt();
				this.width		= in.readInt();
				this.height		= in.readInt();
				this.frameCount	= in.readInt();
				world.setSize(width,height);
				//читаем общую информацию
				frameSizes	= new int[this.frameCount];
				for(int i=0;i<this.frameCount;i++)
				{
					int fno	= in.readInt();//номер фрейма
					frameSizes[i]=in.readInt();//длинна фрейма
				}
				//переключаемся на кадры 
				zipEntry	= filein.getNextEntry();
				
				thread = new Worker();
				thread.start();// Запускаем как поток
			}
			
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	boolean started;
	class Worker extends Thread
	{
		public Worker()
		{
			this.setPriority(MAX_PRIORITY);
		}
		@Override
		public void run()
		{
			try
			{
				started = true;
				while(started)
				{
					int frameLength	= in.readInt();
					if(frameLength==0)
						break;
					
					world.setSize(width,height);
					int i=0;
					while(i<frameLength)
					{
						Bot bot	= new Bot((World) world);
						bot.adr	= in.readByte();//data[0]
						bot.x	= in.readInt();//data[1]
						bot.y = in.readInt();//data[2]= 
						bot.health	= in.readInt();//data[3]= 
						bot.mineral	= in.readInt();//data[4]= 
						bot.alive	= in.readByte();//data[5]= 
						bot.c_red	= in.readInt();//data[6]= 
						bot.c_green	= in.readInt();//data[7]= 
						bot.c_blue	= in.readInt();//data[8]= 
						bot.direction	= in.readByte();//data[9]= 
						bot.mprevX	= in.readInt();//data[10]
						bot.mprevY	= in.readInt();//data[11]
						bot.mnextX	= in.readInt();//data[12]
						bot.mnextY	= in.readInt();//data[13]
						i+=14;
						
						for(int m=0;m<bot.mind.length;m++)
						{
							bot.mind[m]=(byte) in.readByte();
							i++;
						}
						world.setBot(bot);
					}
					world.restoreLinks();
					frameNo++;
					world.paint();
					sleep(20);
				}
				in.close();
				thread	= null;
				
			} catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

package ru.cyberbiology.simulation.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import ru.cyberbiology.simulation.World;
import ru.cyberbiology.simulation.prototype.view.IView;

public class ViewMultiCell implements IView
{

    public ViewMultiCell()
    {
        // TODO Auto-generated constructor stub
    }


    @Override
    public String getName()
    {
        // Отображение ...
        return "Подсветить многоклеточных";
    }


    public Image paint(World world, JPanel canvas)
    {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        //Создаем временный буфер для рисования
        Image buf = canvas.createImage(w, h);
        //подеменяем графику на временный буфер
        Graphics g = buf.getGraphics();

        g.drawRect(0, 0, world.width * World.BOTW + 1, world.height * 4 + 1);

        world.population = 0;
        world.organic = 0;
        for (int y = 0; y < world.height; y++)
        {
            for (int x = 0; x < world.width; x++)
            {
                if (world.matrix[x][y] == null)
                {
                    g.setColor(Color.WHITE);
                    g.fillRect(x * World.BOTW, y * World.BOTH, World.BOTW, World.BOTH);
                }
                else if ((world.matrix[x][y].alive == 1) || (world.matrix[x][y].alive == 2))
                {
                    g.setColor(new Color(200, 200, 200));
                    g.fillRect(x * World.BOTW, y * World.BOTH, World.BOTW, World.BOTH);
                    world.organic = world.organic + 1;
                }
                else if (world.matrix[x][y].alive == 3)
                {
                    g.setColor(Color.BLACK);
                    g.drawRect(x * World.BOTW, y * World.BOTH, World.BOTW, World.BOTH);
                    switch (world.matrix[x][y].isMulti())
                    {

                        case 1:// - есть MPREV,
                            g.setColor(Color.MAGENTA);
                            g.fillRect(x * World.BOTW + 1, y * World.BOTH + 1, World.BOTW - 1, World.BOTH - 1);
                            break;
                        case 2:// - есть MNEXT,
                            g.setColor(Color.BLACK);
                            g.fillRect(x * World.BOTW + 1, y * World.BOTH + 1, World.BOTW - 1, World.BOTH - 1);
                            break;
                        case 3:// есть MPREV и MNEXT
                            g.setColor(Color.MAGENTA);
                            g.fillRect(x * World.BOTW + 1, y * World.BOTH + 1, World.BOTW - 1, World.BOTH - 1);
                            break;
                        default:
                            int green = (int) (world.matrix[x][y].c_green - ((world.matrix[x][y].c_green * world.matrix[x][y].health) / 2000));
                            if (green < 0)
                            {
                                green = 0;
                            }
                            if (green > 255)
                            {
                                green = 255;
                            }
                            int blue = (int) (world.matrix[x][y].c_blue * 0.8 - ((world.matrix[x][y].c_blue * world.matrix[x][y].mineral) / 2000));
                            g.setColor(new Color(world.matrix[x][y].c_red, green, blue));
                            g.fillRect(x * World.BOTW + 1, y * World.BOTH + 1, World.BOTW - 1, World.BOTH - 1);
                            break;
                    }

                    world.population = world.population + 1;
                }
            }
        }
        return buf;
    }
}

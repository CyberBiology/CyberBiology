package ru.cyberbiology.test.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import ru.cyberbiology.test.MainWindow;
import ru.cyberbiology.test.World;
import ru.cyberbiology.test.prototype.view.IView;

public class ViewBasic implements IView
{

	public ViewBasic()
	{
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getName()
	{
		// Отображение ...
		return "Базовое";
	}
    public Image paint(World world,JPanel canvas) {
    	int w = canvas.getWidth();
    	int h = canvas.getHeight();
    	//Создаем временный буфер для рисования
    	Image buf = canvas.createImage(w, h);
    	//подеменяем графику на временный буфер
    	Graphics g = buf.getGraphics();
    	
        g.drawRect(0, 0, world.width * World.BOTW + 1, world.height * World.BOTH + 1);

        world.population = 0;
        world.organic = 0;
        for (int y = 0; y < world.height; y++) {
            for (int x = 0; x < world.width; x++) {
                if (world.matrix[x][y] == null) {
                    g.setColor(Color.WHITE);
                    g.fillRect(x * World.BOTW,y * World.BOTH, World.BOTW, World.BOTH);
                } else if ((world.matrix[x][y].alive == 1) || (world.matrix[x][y].alive == 2)) {
                    g.setColor(new Color(200, 200, 200));
                    g.fillRect(x * World.BOTW, y * World.BOTH, World.BOTW, World.BOTH);
                    world.organic = world.organic + 1;
                } else if (world.matrix[x][y].alive == 3) {
                    g.setColor(Color.BLACK);
                    g.drawRect(x * World.BOTW, y * World.BOTH, World.BOTW, World.BOTH);

//                    g.setColor(new Color(matrix[x][y].c_red, matrix[x][y].c_green, matrix[x][y].c_blue));
                    int green = (int) (world.matrix[x][y].c_green - ((world.matrix[x][y].c_green * world.matrix[x][y].health) / 2000));
                    if (green < 0) green = 0;
                    if (green > 255) green = 255;
                    int blue = (int) (world.matrix[x][y].c_blue * 0.8 - ((world.matrix[x][y].c_blue * world.matrix[x][y].mineral) / 2000));
                    g.setColor(new Color(world.matrix[x][y].c_red, green, blue));
//                    g.setColor(new Color(matrix[x][y].c_red, matrix[x][y].c_green, matrix[x][y].c_blue));
                    g.fillRect(x * World.BOTW + 1, y * World.BOTH + 1,World.BOTW-1, World.BOTH-1);
                    world.population = world.population + 1;
                }
            }
        }
        return buf;
    }
}

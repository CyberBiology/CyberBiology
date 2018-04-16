
import java.awt.*;
import javax.swing.*;
// Тест
// Основной класс программы.

public class World extends JFrame {

    public int width;
    public int height;
    public Bot[][] matrix;    //Матрица мира
    public int generation;
    public int population;
    public int organic;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new Bot[width][height];

        simulation = this;

        setTitle ("CyberBiology 1.0.1");
        setSize (new Dimension (1670, 870));
        Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize (), fSize = getSize ();
        if (fSize.height > sSize.height) {fSize.height = sSize.height;}
        if (fSize.width  > sSize.width)  {fSize.width = sSize.width;}
        setLocation ((sSize.width - fSize.width)/2, (sSize.height - fSize.height)/2);

        setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
        setVisible (true);

//        JButton button = new JButton("Sort");
//        button.setSize(30,40);
//        ActionListener actionListener = new CPS1();
//        button.addActionListener(actionListener);

    }

    @Override
    public void paint(Graphics g) {

        g.drawRect(49, 49, simulation.width * 4 + 1, simulation.height * 4 + 1);

        population = 0;
        organic = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix[x][y] == null) {
                    g.setColor(Color.WHITE);
                    g.fillRect(50 + x * 4, 50 + y * 4, 4, 4);
                } else if ((matrix[x][y].alive == 1) || (matrix[x][y].alive == 2)) {
                    g.setColor(new Color(200, 200, 200));
                    g.fillRect(50 + x * 4, 50 + y * 4, 4, 4);
                    organic = organic + 1;
                } else if (matrix[x][y].alive == 3) {
                    g.setColor(Color.BLACK);
                    g.drawRect(50 + x * 4, 50 + y * 4, 4, 4);

//                    g.setColor(new Color(matrix[x][y].c_red, matrix[x][y].c_green, matrix[x][y].c_blue));
                    int green = (int) (matrix[x][y].c_green - ((matrix[x][y].c_green * matrix[x][y].health) / 2000));
                    if (green < 0) green = 0;
                    if (green > 255) green = 255;
                    int blue = (int) (matrix[x][y].c_blue * 0.8 - ((matrix[x][y].c_blue * matrix[x][y].mineral) / 2000));
                    g.setColor(new Color(matrix[x][y].c_red, green, blue));
//                    g.setColor(new Color(matrix[x][y].c_red, matrix[x][y].c_green, matrix[x][y].c_blue));
                    g.fillRect(50 + x * 4 + 1, 50 + y * 4 + 1, 3, 3);
                    population = population + 1;
                }
            }
        }

        g.setColor(Color.WHITE);
        g.fillRect(50, 30, 140, 16);
        g.setColor(Color.BLACK);
        g.drawString("Generation: " + String.valueOf(generation), 54, 44);

        g.setColor(Color.WHITE);
        g.fillRect(200, 30, 140, 16);
        g.setColor(Color.BLACK);
        g.drawString("Population: " + String.valueOf(population), 204, 44);

        g.setColor(Color.WHITE);
        g.fillRect(350, 30, 140, 16);
        g.setColor(Color.BLACK);
        g.drawString("Organic: " + String.valueOf(organic), 354, 44);

    }

    // Основной цикл ------------------------------------------------------------------------
    public void run() {
        //пока не остановят симуляцию
        generation = 0;
        while (true) {
            //Обновляем матрицу мира
            for (int yw = 0; yw < height; yw++) {
                for (int xw = 0; xw < width; xw++) {
                    if (matrix[xw][yw] != null) {
                        matrix[xw][yw].step();      //Выполняем ход бота
                    }
                }
            }
            generation = generation + 1;
            if (generation % 10 == 0) {
                paint(getGraphics());        //отображаем текущее состояние симуляции на экран
            }
//            sleep();        //пауза между ходами
        }
    }

/*    public void print() {
        //Выводим в консоль текущее состояние симуляции
        String out;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix[x][y] == null) {
                    out = " . ";
                } else if (!matrix[x][y].alive) {
                    out = " x ";
                } else {
                    out = "[" + matrix[x][y].health + "]";
                }
                System.out.print(out);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }
*/

    public static World simulation;

    public static void main(String[] args) {
        simulation = new World(400, 200);
        simulation.generateAdam();
        simulation.run();
    }

    // делает паузу
    public void sleep() {
        try {
            int delay = 20;
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
    }

    public void generateAdam() {
        //==========  1  ==============
        // бот номер 1 - это уже реальный бот
        Bot bot = new Bot();

        bot.adr = 0;
        bot.x = width / 2;       // координаты бота
        bot.y = height / 2;
        bot.health = 990;      // энергия
        bot.mineral = 0;        // минералы
        bot.alive = 3; // отмечаем, что бот живой
        bot.c_red = 170;  // задаем цвет бота
        bot.c_blue = 170;
        bot.c_green = 170;
        bot.direction = 5;        // направление
        bot.mprev = null;      // бот не входит в многоклеточные цепочки, поэтому ссылки
        bot.mnext = null;      // на предыдущего, следующего в многоклеточной цепочке пусты
        for (int i = 0; i < 64; i++) {        // заполняем геном командой 25 - фотосинтез
            bot.mind[i] = 25;
        }

        matrix[bot.x][bot.y] = bot;            // даём ссылку на бота в массиве world[]

        return;
    }


}

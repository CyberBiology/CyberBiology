package ru.cyberbiology.simulation.gene;

import ru.cyberbiology.simulation.prototype.IBot;
import ru.cyberbiology.simulation.prototype.gene.ABotGeneController;

/**
 * //*******************************************************************
 * //...............  сменить направление относительно   ....
 * if (command == 23) {                            // вычисляем новое направление
 * int param = botGetParam(this) % 8;          // берём следующи байт за командой и вычисляем остаток от деления на 8
 * int newdrct = direction + param;            // полученное число прибавляем к значению направления бота
 * if (newdrct >= 8) {
 * newdrct = newdrct - 8;
 * }// результат должен быть в пределах от 0 до 8
 * direction = newdrct;
 * botIncCommandAddress(this, 2);                              // адрес текущей команды увеличивается на 2,
 * }
 *
 * @author Nickolay
 */
public class GeneChangeDirectionRelative extends ABotGeneController
{

    @Override
    public boolean onGene(IBot bot)
    {
        // вычисляем новое направление
        int param = bot.getParam() % 8;          // берём следующи байт за командой и вычисляем остаток от деления на 8
        int newdrct = bot.getDirection() + param;            // полученное число прибавляем к значению направления бота
        if (newdrct >= 8)
        {
            newdrct = newdrct - 8;
        }// результат должен быть в пределах от 0 до 8
        bot.setDirection(newdrct);
        bot.incCommandAddress(2);                              // адрес текущей команды увеличивается на 2,

        return false;
    }


    public String getDescription(IBot bot, int i)
    {
        return "сменить направление относительно";
    }
}

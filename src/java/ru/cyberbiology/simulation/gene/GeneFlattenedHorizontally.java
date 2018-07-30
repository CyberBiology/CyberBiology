package ru.cyberbiology.simulation.gene;

import ru.cyberbiology.simulation.prototype.IBot;
import ru.cyberbiology.simulation.prototype.gene.ABotGeneController;

/**
 * //...................   выравнится по горизонтали  ...............................
 * if (command == 36) {
 * if (Math.random() < 0.5) {              // кидаем монетку
 * direction = 3;                 // если ноль, то поворачиваем в одну сторону
 * } else {
 * direction = 7;                 // если один, то поворачиваем в другую сторону
 * }
 * botIncCommandAddress(this, 1); // увеличиваем указатель текущей команды на 1
 * }
 *
 * @author Nickolay
 */
public class GeneFlattenedHorizontally extends ABotGeneController
{

    @Override
    public boolean onGene(IBot bot)
    {
        if (Math.random() < 0.5)
        {              // кидаем монетку
            bot.setDirection(3);                 // если ноль, то поворачиваем в одну сторону
        }
        else
        {
            bot.setDirection(7);                 // если один, то поворачиваем в другую сторону
        }
        bot.incCommandAddress(1); // увеличиваем указатель текущей команды на 1
        return false;
    }


    public String getDescription(IBot bot, int i)
    {
        return "выравнится по горизонтали";
    }
}

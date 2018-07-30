package ru.cyberbiology.simulation.gene;

import ru.cyberbiology.simulation.prototype.IBot;
import ru.cyberbiology.simulation.prototype.gene.ABotGeneController;

/**
 * //*******************************************************************
 * //...............  шаг   в абсолютном направлении     .................
 * if (command == 27) {
 * if (isMulti(this) == 0) {
 * int drct = botGetParam(this) % 8;
 * botIndirectIncCmdAddress(this, botMove(this, drct, 1));
 * }
 * break;
 * aaaa
 * }
 *
 * @author Nickolay
 */
public class GeneStepInAbsolutelyDirection extends ABotGeneController
{

    @Override
    public boolean onGene(IBot bot)
    {
        if (bot.isMulti() == 0)
        {           // бот многоклеточный? перемещаются только одноклеточные
            int drct = bot.getParam() % 8;   // вычисляем направление из следующего за командой байта
            bot.indirectIncCmdAddress(bot.move(drct, 1)); // меняем адрес текущей команды
            // в зависимости от того, что было в этом направлении
            // смещение условного перехода 2-пусто  3-стена  4-органика 5-бот 6-родня
        }
        else
        {
            bot.incCommandAddress(2);
        }
        return true;// выходим, так как команда шагнуть - завершающая
    }


    @Override
    public String getDescription(IBot bot, int i)
    {
        return "шаг   в абсолютном направлении ";
    }
}

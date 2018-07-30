package ru.cyberbiology.simulation.gene;

import ru.cyberbiology.simulation.prototype.IBot;
import ru.cyberbiology.simulation.prototype.gene.ABotGeneController;

/**
 * //..............   съесть  в абсолютном направлении      ...............
 * if (command == 29) {  //смещение условного перехода  стена - 2 пусто - 3 органика - 4 живой - 5
 * int drct = botGetParam(this) % 8;
 * botIndirectIncCmdAddress(this, botEat(this, drct, 1));
 * break;
 * }
 *
 * @author Nickolay
 */
public class GeneEatAbsoluteDirection extends ABotGeneController
{

    @Override
    public boolean onGene(IBot bot)
    {
        int drct = bot.getParam() % 8;       // вычисляем направление из следующего за командой байта
        bot.indirectIncCmdAddress(bot.eat(drct, 1)); // меняем адрес текущей команды
        return true;// выходим, так как команда шагнуть - завершающая
    }


    public String getDescription(IBot bot, int i)
    {
        return "съесть  в абсолютном направлении";
    }
}

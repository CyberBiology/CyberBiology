package ru.cyberbiology.simulation.gene;

import ru.cyberbiology.simulation.prototype.IBot;
import ru.cyberbiology.simulation.prototype.gene.ABotGeneController;

/**
 * //.............   отдать  в абсолютном направлении  ........................
 * if ((command == 35) || (command == 52)) {      // здесь я увеличил шансы появления этой команды
 * int drct = botGetParam(this) % 8;
 * botIndirectIncCmdAddress(this, botGive(this, drct, 1)); // стена - 2 пусто - 3 органика - 4 удачно - 5
 * }
 *
 * @author Nickolay
 */
public class GeneGiveAbsolutelyDirection extends ABotGeneController
{

    @Override
    public boolean onGene(IBot bot)
    {
        int drct = bot.getParam() % 8;       // вычисляем направление из следующего за командой байта
        bot.indirectIncCmdAddress(bot.give(drct, 1)); // меняем адрес текущей команды
        // в зависимости от того, что было в этом направлении
        // стена - 2 пусто - 3 органика - 4 удачно - 5
        return false;
    }


    public String getDescription(IBot bot, int i)
    {
        return "отдать  в абсолютном направлении";
    }
}

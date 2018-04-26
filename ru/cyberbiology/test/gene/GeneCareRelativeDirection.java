package ru.cyberbiology.test.gene;

import ru.cyberbiology.test.prototype.IBot;
import ru.cyberbiology.test.prototype.gene.ABotGeneController;

/**
//******************************************************************************
// делиться - если у бота больше энергии или минералов, чем у соседа, то они распределяются поровну
//.............   делится   в относительном напралении  ........................
            if ((command == 32) || (command == 42)) {   // здесь я увеличил шансы появления этой команды
                int drct = botGetParam(this) % 8;    // вычисляем направление из следующего за командой байта
                botIndirectIncCmdAddress(this, botCare(this, drct, 0)); // меняем адрес текущей команды
                // в зависимости от того, что было в этом направлении
                // стена - 2 пусто - 3 органика - 4 удачно - 5
            }
 * @author Nickolay
 *
 */
public class GeneCareRelativeDirection extends ABotGeneController
{

	@Override
	public boolean onGene(IBot bot)
	{
        int drct = bot.getParam() % 8;       // вычисляем направление из следующего за командой байта
        bot.indirectIncCmdAddress(bot.care(drct, 0)); // меняем адрес текущей команды
        // в зависимости от того, что было в этом направлении
        // пусто - 2 стена - 3 органик - 4 бот -5 родня -  6
        return false;
	}
	public String getDescription(IBot bot, int i)
	{
		return "поделится в относительном напралении";
	}
}

package ru.cyberbiology.test.gene;

import ru.cyberbiology.test.prototype.IBot;
import ru.cyberbiology.test.prototype.gene.ABotGeneController;

/**
// отдать - безвозмездно отдать часть энергии и минералов соседу
//.............   отдать   в относительном напралении  ........................
            if ((command == 34) || (command == 51)) {    // здесь я увеличил шансы появления этой команды
                int drct = botGetParam(this) % 8;    // вычисляем направление из следующего за командой байта
                botIndirectIncCmdAddress(this, botGive(this, drct, 0)); // меняем адрес текущей команды
                // в зависимости от того, что было в этом направлении
                // стена - 2 пусто - 3 органика - 4 удачно - 5
            }
 * @author Nickolay
 *
 */
public class GeneGiveRelativeDirection extends ABotGeneController
{

	@Override
	public boolean onGene(IBot bot)
	{
        int drct = bot.getParam() % 8;       // вычисляем направление из следующего за командой байта
        bot.indirectIncCmdAddress(bot.give(drct, 0)); // меняем адрес текущей команды
        // в зависимости от того, что было в этом направлении
        // стена - 2 пусто - 3 органика - 4 удачно - 5
        return false;
	}
	public String getDescription(IBot bot, int i)
	{
		return "отдать - безвозмездно отдать часть энергии и минералов соседу";
	}
}

package ru.cyberbiology.simulation.gene;

import ru.cyberbiology.simulation.prototype.IBot;
import ru.cyberbiology.simulation.prototype.gene.ABotGeneController;

/**
//*******************************************************************
//...............  шаг  в относительном напралении  .................
            if (command == 26) {
                if (isMulti(this) == 0) {           // бот многоклеточный? перемещаются только одноклеточные
                    int drct = botGetParam(this) % 8;   // вычисляем направление из следующего за командой байта
                    botIndirectIncCmdAddress(this, botMove(this, drct, 0)); // меняем адрес текущей команды
                    // в зависимости от того, что было в этом направлении
                    // смещение условного перехода 2-пусто  3-стена  4-органика 5-бот 6-родня
                } else {
                    botIncCommandAddress(this, 2);
                }
                break;         // выходим, так как команда шагнуть - завершающая
            }
 * @author Nickolay
 *
 */
public class GeneStepInRelativeDirection extends ABotGeneController
{

	@Override
	public boolean onGene(IBot bot)
	{
        if (bot.isMulti() == 0)
        {           // бот многоклеточный? перемещаются только одноклеточные
            int drct = bot.getParam() % 8;   // вычисляем направление из следующего за командой байта
            bot.indirectIncCmdAddress(bot.move(drct, 0)); // меняем адрес текущей команды
            // в зависимости от того, что было в этом направлении
            // смещение условного перехода 2-пусто  3-стена  4-органика 5-бот 6-родня
        } else {
            bot.incCommandAddress(2);
        }
        return true;// выходим, так как команда шагнуть - завершающая
	}
	@Override
	public String getDescription(IBot bot, int i)
	{
		return "шаг   в относительном направлении ";
	}
}

package ru.cyberbiology.test.gene;

import ru.cyberbiology.test.World;
import ru.cyberbiology.test.prototype.IBot;
import ru.cyberbiology.test.prototype.gene.ABotGeneController;

/**
//****************************************************
          //.............. приход энергии есть? ........................
            if (command == 44) {  // is_health_grow() возвращает 1, если энегрия у бота прибавляется, иначе - 2
                botIndirectIncCmdAddress(this, isHealthGrow(this));
            }

 * @author Nickolay
 *
 */
public class GeneIsHealthGrow extends ABotGeneController
{

	@Override
	public boolean onGene(IBot bot)
	{
		// функция full_aroud() возвращает  1, если бот окружен и 2, если нет
        // увеличиваем значение указателя текущей команды
        // на значение следующего байта после команды или 2-го байта после команды
        // в зависимости от того, окружен бот или нет
        bot.indirectIncCmdAddress(bot.isHealthGrow());
        return false;
	}
	public String getDescription(IBot bot, int i)
	{
		return "приход энергии есть?";
	}
}

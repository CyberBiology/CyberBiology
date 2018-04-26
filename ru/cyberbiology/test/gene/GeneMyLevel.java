package ru.cyberbiology.test.gene;

import ru.cyberbiology.test.World;
import ru.cyberbiology.test.prototype.IBot;
import ru.cyberbiology.test.prototype.gene.ABotGeneController;

/**
//...................  какой мой уровень (на какой высоте бот)  .........
            if (command == 37) {   // у меня поле высотой в 96 клеток
                // байт в геноме может иметь значение от 0 до 63
                // умножая значение байта на 1,5 получаем значение от 0 до 95
                int param = botGetParam(this) * World.simulation.height / MIND_SIZE;   // берем следующий за командой байт и умножаем на 1,5
                // если уровень бота ниже, чем полученное значение,
                // то прибавляем к указатели текущей команды значение 2-го байта, после выполняемой команды
                if (y < param) {
                    botIndirectIncCmdAddress(this, 2);
                } else { // иначе прибавляем к указатели текущей команды значение 3-го байта, после выполняемой команды
                    botIndirectIncCmdAddress(this, 3);
                }
            }
 * @author Nickolay
 *
 */
public class GeneMyLevel extends ABotGeneController
{

	@Override
	public boolean onGene(IBot bot)
	{
        // байт в геноме может иметь значение от 0 до 63
        // умножая значение байта на 1,5 получаем значение от 0 до 95
        int param = bot.getParam() * bot.getWorld().getHeight() / bot.MIND_SIZE;   // берем следующий за командой байт и умножаем на 1,5
        // если уровень бота ниже, чем полученное значение,
        // то прибавляем к указатели текущей команды значение 2-го байта, после выполняемой команды
        if (bot.getY() < param) {
            bot.indirectIncCmdAddress(2);
        } else { // иначе прибавляем к указатели текущей команды значение 3-го байта, после выполняемой команды
            bot.indirectIncCmdAddress(3);
        }
        return false;
	}
	@Override
	public String getDescription(IBot bot, int i)
	{
		return "какой мой уровень (на какой высоте бот)";
	}
}

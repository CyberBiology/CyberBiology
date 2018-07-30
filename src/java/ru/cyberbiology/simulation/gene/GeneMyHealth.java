package ru.cyberbiology.simulation.gene;

import ru.cyberbiology.simulation.prototype.IBot;
import ru.cyberbiology.simulation.prototype.gene.ABotGeneController;

/**
 * //**************************************************************************
 * //...................  какое моё здоровье  ...............................
 * if (command == 38) {   // максимальное здоровье  999
 * // байт в геноме может иметь значение от 0 до 63
 * // умножая значение байта на 15 получаем значение от 0 до 945
 * int param = botGetParam(this) * 1000 / MIND_SIZE;   // берем следующий за командой байт и умножаем на 15
 * // если здоровье бота ниже, чем полученное значение,
 * // то прибавляем к указатели текущей команды значение 2-го байта, после выполняемой команды
 * if (health < param) {
 * botIndirectIncCmdAddress(this, 2);
 * } else { // иначе прибавляем к указатели текущей команды значение 3-го байта, после выполняемой команды
 * botIndirectIncCmdAddress(this, 3);
 * }
 * }
 *
 * @author Nickolay
 */
public class GeneMyHealth extends ABotGeneController
{

    @Override
    public boolean onGene(IBot bot)
    {
        // байт в геноме может иметь значение от 0 до 63
        // умножая значение байта на 15 получаем значение от 0 до 945
        int param = bot.getParam() * 1000 / bot.MIND_SIZE;   // берем следующий за командой байт и умножаем на 15
        // если здоровье бота ниже, чем полученное значение,
        // то прибавляем к указатели текущей команды значение 2-го байта, после выполняемой команды
        if (bot.getHealth() < param)
        {
            bot.indirectIncCmdAddress(2);
        }
        else
        { // иначе прибавляем к указатели текущей команды значение 3-го байта, после выполняемой команды
            bot.indirectIncCmdAddress(3);
        }
        return false;
    }


    @Override
    public String getDescription(IBot bot, int i)
    {
        return "какое моё здоровье";
    }
}

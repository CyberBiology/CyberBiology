package ru.cyberbiology.simulation.gene;

import ru.cyberbiology.simulation.prototype.IBot;
import ru.cyberbiology.simulation.prototype.gene.ABotGeneController;

/**
//****************************************************************************
//................   генная атака  ...................................
            if (command == 49) {  // бот атакует геном соседа, на которого он повернут
                botGenAttack(this); // случайным образом меняет один байт
                botIncCommandAddress(this, 1);
                break; 
            }        // после её выполнения, управление передаётся следующему боту
 * @author Nickolay
 *
 */
public class GeneAttack extends ABotGeneController
{

	@Override
	public boolean onGene(IBot bot)
	{
		bot.genAttack(); // случайным образом меняет один байт
        bot.incCommandAddress(1);
        return true;//// выходим, так как команда мутировать - завершающая
	}
	public String getDescription(IBot bot, int i)
	{
		return "генная атака";
	}
}

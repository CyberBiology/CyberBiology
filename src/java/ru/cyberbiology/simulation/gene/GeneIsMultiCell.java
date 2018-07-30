package ru.cyberbiology.simulation.gene;

import ru.cyberbiology.simulation.prototype.IBot;
import ru.cyberbiology.simulation.prototype.gene.ABotGeneController;

/********************************************************************
 //.............. многоклеточный ли я ? ........................
 if (command == 46) {
 int mu = isMulti(this);
 if (mu == 0) {
 botIndirectIncCmdAddress(this, 1); // бот свободно живущий
 } else {
 if (mu == 3) {
 botIndirectIncCmdAddress(this, 3); // бот внутри цепочки
 } else {
 botIndirectIncCmdAddress(this, 2); // бот скраю цепочки
 }
 }
 }
 * @author Nickolay
 *
 */
public class GeneIsMultiCell extends ABotGeneController
{

    @Override
    public boolean onGene(IBot bot)
    {
        int mu = bot.isMulti();
        if (mu == 0)
        {
            bot.indirectIncCmdAddress(1); // бот свободно живущий
        }
        else
        {
            if (mu == 3)
            {
                bot.indirectIncCmdAddress(3); // бот внутри цепочки
            }
            else
            {
                bot.indirectIncCmdAddress(2); // бот скраю цепочки
            }
        }
        return false;
    }


    @Override
    public String getDescription(IBot bot, int i)
    {
        return "многоклеточный ли я ?";
    }
}

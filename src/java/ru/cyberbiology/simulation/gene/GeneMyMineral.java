package ru.cyberbiology.simulation.gene;

import ru.cyberbiology.simulation.prototype.IBot;
import ru.cyberbiology.simulation.prototype.gene.ABotGeneController;

/**
 * //*********************************************************************
 * //...................сколько  минералов ...............................
 * if (command == 39) {
 * int param = botGetParam(this) * 1000 / MIND_SIZE;
 * if (mineral < param) {
 * botIndirectIncCmdAddress(this, 2);
 * } else {
 * botIndirectIncCmdAddress(this, 3);
 * }
 * }
 *
 * @author Nickolay
 */
public class GeneMyMineral extends ABotGeneController
{

    @Override
    public boolean onGene(IBot bot)
    {
        int param = bot.getParam() * 1000 / bot.MIND_SIZE;
        if (bot.getMineral() < param)
        {
            bot.indirectIncCmdAddress(2);
        }
        else
        {
            bot.indirectIncCmdAddress(3);
        }
        return false;
    }


    public String getDescription(IBot bot, int i)
    {
        return "сколько  минералов";
    }
}

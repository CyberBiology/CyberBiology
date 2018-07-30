package ru.cyberbiology.simulation.prototype.record;

import java.io.DataOutputStream;
import java.io.IOException;

import ru.cyberbiology.simulation.prototype.IBot;

public interface IFrame
{

	public void addBot(IBot bot, int x, int y);

	public int save(DataOutputStream fileout) throws IOException;

}

package ru.cyberbiology.simulation.prototype;

import ru.cyberbiology.simulation.Bot;
import ru.cyberbiology.simulation.util.ProjectProperties;

public interface IWorld
{

    public int getWidth();

    public int getHeight();

    public void setSize(int width, int height);

    public void setBot(Bot bot);

    public void paint();

    public ProjectProperties getProperties();

    public Bot[][] getWorldArray();

    public void restoreLinks();

}

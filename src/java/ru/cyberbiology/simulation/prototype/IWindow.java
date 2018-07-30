package ru.cyberbiology.simulation.prototype;

import ru.cyberbiology.simulation.prototype.view.IView;
import ru.cyberbiology.simulation.util.ProjectProperties;

public interface IWindow
{

	public void paint();

	public void setView(IView view);

	public ProjectProperties getProperties();

}

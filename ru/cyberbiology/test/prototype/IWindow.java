package ru.cyberbiology.test.prototype;

import ru.cyberbiology.test.prototype.view.IView;
import ru.cyberbiology.test.util.ProjectProperties;

public interface IWindow
{

	public void paint();

	public void setView(IView view);

	public ProjectProperties getProperties();

}

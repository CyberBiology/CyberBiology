package ru.cyberbiology.simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ru.cyberbiology.simulation.prototype.IWindow;
import ru.cyberbiology.simulation.prototype.view.IView;

public class ViewMenuActionListener implements ActionListener
{
	IWindow window;
	IView view;
	public ViewMenuActionListener(IWindow window,IView rend)
	{
		this.window		= window;
		this.view	= rend;
	}
    public void actionPerformed(ActionEvent e)
    {
    	this.window.setView(this.view);            
    }    
}

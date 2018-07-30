package ru.cyberbiology.simulation.prototype.view;

import java.awt.Image;

import javax.swing.JPanel;

import ru.cyberbiology.simulation.World;

public interface IView
{
	public Image paint(World world,JPanel canvas);

	public String getName();
}

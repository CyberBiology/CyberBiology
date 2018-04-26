package ru.cyberbiology.test.prototype.view;

import java.awt.Image;

import javax.swing.JPanel;

import ru.cyberbiology.test.World;

public interface IView
{
	public Image paint(World world,JPanel canvas);

	public String getName();
}

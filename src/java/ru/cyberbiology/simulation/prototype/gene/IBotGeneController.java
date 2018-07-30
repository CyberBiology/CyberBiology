package ru.cyberbiology.simulation.prototype.gene;

import ru.cyberbiology.simulation.prototype.IBot;
/**
 * Интерфейс обработчика гена бота
 * @author Nickolay
 *
 */
public interface IBotGeneController
{
	/**
	 * Реализация одного шага интерпетации гена
	 * @param bot бот, над которыым проводится процедура
	 * @return возвращает true если обработка в данной серии этому боту больше не требуется ?
	 */
	public boolean onGene(IBot bot);

	public String getDescription(IBot bot, int i);
}

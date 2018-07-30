package ru.cyberbiology.simulation.prototype;

public interface IBot
{
    public static final int MIND_SIZE = 64;

    /**
     * получение параметра для команды
     *
     * @return возвращает число из днк, следующее за выполняемой командой
     */
    public int getParam();

    /**
     * получение направления движения
     *
     * @return возвращает число от 0 до 8 (0 вверх, 5 вниз)
     */
    public int getDirection();

    /**
     * устанавливает направления движения
     *
     * @param newdrct число от 0 до 8 (0 вверх, 5 вниз)
     */
    public void setDirection(int newdrct);

    /**
     * увеличение адреса команды
     *
     * @param increment насколько прибавить адрес
     */
    public void incCommandAddress(int increment);

    /**
     * фотосинтез
     */
    public void eatSun();

    /**
     * Многоклеточный ли бот?
     *
     * @return
     */
    public int isMulti();

    public int move(int drct, int i);

    public void indirectIncCmdAddress(int move);

    public int eat(int drct, int i);

    public int seeBots(int drct, int i);

    public int care(int drct, int i);

    public int give(int drct, int i);

    public int getY();

    public int getHealth();

    public int getMineral();

    public void Double();

    public void multi();

    public int fullAroud();

    public int isHealthGrow();

    public void mineral2Energy();

    public void setMind(byte ma, byte mc);

    public void genAttack();

    public IWorld getWorld();

}

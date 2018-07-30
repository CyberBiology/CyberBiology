package ru.cyberbiology.simulation.prototype.record;

import ru.cyberbiology.simulation.prototype.IBot;

public interface IRecordManager
{

    public int getBufferSize();

    public int getFrameSavedCounter();

    public int getFrameSkipSize();

    public boolean isRecording();

    public void startFrame();

    public void startRecording();

    public void stopFrame();

    public boolean stopRecording();

    public void writeBot(IBot bot, int x, int y);

    //public void save(File selectedFile);

    public boolean haveRecord();

    public void makeSnapShot();

    //public void deleteRecord();

}

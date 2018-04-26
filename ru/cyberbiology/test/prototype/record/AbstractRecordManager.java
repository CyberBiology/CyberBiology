package ru.cyberbiology.test.prototype.record;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import ru.cyberbiology.test.prototype.IBot;
import ru.cyberbiology.test.prototype.IWorld;
/**
 * Абстрактный класс , реализующий базовый функционал
 * Запись ведется в отдельном потоке, поскольку данный очень много 
 * пишется не каждый кадр, а каждый frameSkipSize
 * 
 * TODO: Можно сделать автоматическую подстройку, увеличивать количество пропускаемых кадров, если
 * буффер заполняется более некоторого значения
 * 
 * 
 * @author Nickolay
 *
 */
public abstract class AbstractRecordManager implements IRecordManager
{
	IWorld world;
	int width;
	int height;
	
	protected List<IFrame> frames;
	IFrame frame;
	protected Worker thread;
	protected boolean started;// флаг о том, что запись заканчивается
	int frameCounter;
	protected int frameSavedCounter;
	int frameSkipSize;
	int frameSetSizeLimit;
	protected File file;
	protected ArrayList<Integer> fameSizes;
	public AbstractRecordManager(IWorld world)
	{
		this.world = world;
		this.started = false;
		this.frameSkipSize	= 200;
		this.frameSetSizeLimit = 50;
		this.frameCounter = frameSkipSize;//Первый фрейм записываем, потом начинаем пропускать
		this.frameSavedCounter = 0;
		this.fameSizes	= new ArrayList<Integer>();
		frames = Collections.synchronizedList(new ArrayList());
	}
	protected IWorld getWorld()
	{
		return this.world;
	}
	public synchronized void startRecording()
	{
		this.width	= this.world.getWidth();
		this.height	= this.world.getHeight();
		
		this.thread	= new Worker();
		this.thread.start();
	}
	
	@Override
	public boolean haveRecord()
	{
		return file!=null;
	}
	/*@Override
	public void deleteRecord()
	{
		if(file!=null)
			file.delete();
	}*/
	/*
	@Override
	public void save(File selectedFile)
	{
		if(file!=null)
		{
			if(!selectedFile.getName().endsWith(".cb.zip"))
			{
				selectedFile = new File(selectedFile.getName()+".cb.zip");
			}
			file.renameTo(selectedFile);
			file = null;
		}
	}*/
	public synchronized boolean stopRecording()
	{
		if(this.started)
		{
			this.started	= false;
			return true;
		}
		return false;
	}

	public synchronized void startFrame()
	{
		frameCounter++;
		if(frameCounter>=frameSkipSize)
		{
			this.frame	= newFrame();
			frameCounter =0;
		}
	}
	protected abstract IFrame newFrame();
	public synchronized void stopFrame()
	{
		if(this.frame!=null)
		{
			frames.add(this.frame);
			this.frame=null;// обнуляем актуальный фрейм
			
			while(frames.size()>frameSetSizeLimit)
			{
				try
				{
					//TODO: замедлить генерацию контента
					System.out.println("Go to sleep, Frame set size = "+frames.size());
					Thread.sleep(200);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public int getBufferSize()
	{
		return frames.size();
	}
	public int getFrameSavedCounter()
	{
		return this.frameSavedCounter;
	}
	public int getFrameSkipSize()
	{
		return frameSkipSize;
	}
	public synchronized void writeBot(IBot bot, int x, int y)
	{
		if(bot!=null & this.frame!=null)
			this.frame.addBot(bot, x, y);
	}

	public boolean isRecording()
	{
		return this.thread!=null;
	}
	protected abstract void save();
	protected static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
	class Worker extends Thread
	{
		public Worker()
		{
			this.setDaemon(true);
			this.setPriority(MAX_PRIORITY);
		}
		@Override
		public void run()
		{
			save();
		}
	}
	public int getHeight()
	{
		return height;
	}
	public int getWidth()
	{
		return width;
	}
	public abstract int getVersion();
}

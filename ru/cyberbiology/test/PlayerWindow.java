package ru.cyberbiology.test;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
// Тест
// Основной класс программы.
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import ru.cyberbiology.test.prototype.IWindow;
import ru.cyberbiology.test.prototype.gene.IBotGeneController;
import ru.cyberbiology.test.prototype.view.IView;
import ru.cyberbiology.test.util.ProjectProperties;
import ru.cyberbiology.test.view.ViewBasic;
import ru.cyberbiology.test.view.ViewMultiCell;

public class PlayerWindow extends JFrame implements IWindow
{
	//public static PlayerWindow window;
	
	public static final int BOTW	= 4;
	public static final int BOTH	= 4;
	
    public static World world;

    //public JLabel generationLabel = new JLabel(" Generation: 0 ");
    public JLabel populationLabel = new JLabel(" Population: 0 ");
    public JLabel organicLabel = new JLabel(" Organic: 0 ");
    
    //public JLabel recorderBufferLabel = new JLabel("");
    public JLabel memoryLabel = new JLabel("");
    
    //public JLabel frameSavedCounterLabel = new JLabel("");
    //public JLabel frameSkipSizeLabel = new JLabel("");
    
    /** буфер для отрисовки ботов */
    public Image buffer	= null;
    /** актуальный отрисовщик*/
    IView	view;
    /** Перечень возможных отрисовщиков*/
    IView[]  views = new IView[]
		{
			new ViewBasic(),
			new ViewMultiCell()
		};
	JMenuItem folderItem;
    //JMenuItem recordItem;
    //JMenuItem saveItem;
    //JMenuItem deleteItem;
    public JPanel paintPanel = new JPanel()
    {
    	public void paint(Graphics g)
    	{
    		g.drawImage(buffer, 0, 0, null);
    	};
    }; 
    
    public PlayerWindow()
    {
    	//window	= this;
        setTitle("CyberBiologyTest 1.0.0");
        setSize(new Dimension(1800, 900));
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize(), fSize = getSize();
        if (fSize.height > sSize.height) { fSize.height = sSize.height; }
        if (fSize.width  > sSize.width)  { fSize.width = sSize.width; }
        //setLocation((sSize.width - fSize.width)/2, (sSize.height - fSize.height)/2);
        setSize(new Dimension(sSize.width, sSize.height));
        
        
        setDefaultCloseOperation (WindowConstants.DISPOSE_ON_CLOSE);

        Container container = getContentPane();

        container.setLayout(new BorderLayout());// у этого лейаута приятная особенность - центральная часть растягивается автоматически
        container.add(paintPanel, BorderLayout.CENTER);// добавляем нашу карту в центр
        //container.add(paintPanel);
        
        
        JPanel statusPanel = new JPanel(new FlowLayout());
        statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        container.add(statusPanel, BorderLayout.SOUTH);
        
        //generationLabel.setPreferredSize(new Dimension(140, 18));
       // generationLabel.setBorder(BorderFactory.createLoweredBevelBorder());
       // statusPanel.add(generationLabel);
        
        populationLabel.setPreferredSize(new Dimension(140, 18));
        populationLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.add(populationLabel);
        
        organicLabel.setPreferredSize(new Dimension(140, 18));
        organicLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.add(organicLabel);

        memoryLabel.setPreferredSize(new Dimension(140, 18));
        memoryLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.add(memoryLabel);
        
      //  recorderBufferLabel.setPreferredSize(new Dimension(140, 18));
      //  recorderBufferLabel.setBorder(BorderFactory.createLoweredBevelBorder());
      //  statusPanel.add(recorderBufferLabel);
        
      //  frameSavedCounterLabel.setPreferredSize(new Dimension(140, 18));
      //  frameSavedCounterLabel.setBorder(BorderFactory.createLoweredBevelBorder());
      //  statusPanel.add(frameSavedCounterLabel);
        
      //  frameSkipSizeLabel.setPreferredSize(new Dimension(140, 18));
      //  frameSkipSizeLabel.setBorder(BorderFactory.createLoweredBevelBorder());
      //  statusPanel.add(frameSkipSizeLabel);
        
        paintPanel.addMouseListener(new CustomListener());
        
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        JMenu openMenu = new JMenu("Открыть");
        fileMenu.add(openMenu);
        /*
        openItem.addActionListener(new ActionListener()
        {           
            public void actionPerformed(ActionEvent e)
            {
            	FileNameExtensionFilter filter = new FileNameExtensionFilter("*.cb.zip","*.*");
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(filter);
                if (fc.showSaveDialog(window) == JFileChooser.APPROVE_OPTION)
                {
                	File f	= fc.getSelectedFile();
                	openFile(f);
                }
            }           
        });*/
        File dir = new File(MainWindow.window.getFileDirectory());
        File[] files	= dir.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return name.endsWith("cb.zip");
			}
		});
        for(int i=0;i<files.length;i++)
        {
        	FileMenuItem item	= new FileMenuItem(files[i]);
        	openMenu.add(item);
        }
        menuBar.add(fileMenu);
        
        
        JMenu ViewMenu = new JMenu("Вид");
        menuBar.add(ViewMenu);
        
        JMenuItem item;
        for(int i=0;i<views.length;i++)
        {
        	item = new JMenuItem(views[i].getName());
        	ViewMenu.add(item);
            item.addActionListener(new ViewMenuActionListener(this, views[i]));
        }
        
        this.setJMenuBar(menuBar);
        
        view = new ViewBasic();
        this.pack();
        this.setVisible(true);
        setExtendedState(MAXIMIZED_BOTH);
        

    }
    class FileMenuItem extends JMenuItem
    {
    	File file;
    	public FileMenuItem(File f)
    	{
    		super(f.getName());
    		this.file	= f;
    		this.addActionListener(new ActionListener()
            {           
                public void actionPerformed(ActionEvent e)
                {
                	openFile(file);
                }           
            });
    	}
    }
	public void openFile(File f)
	{
		this.world	= new World(this);
		this.world.openFile(f);
	}
    class CustomListener implements MouseListener {
    	 
        public void mouseClicked(MouseEvent e) {
        	if(world.started()) return;//Если идет обсчет не суетимся, выводить ничего не надо.
        	
        	Point p	= e.getPoint();
        	int x	= (int) p.getX();
        	int y	= (int) p.getY();
        	int botX=(x-2)/BOTW;
        	int botY=(y-2)/BOTH;	
        	Bot bot	= world.getBot(botX,botY);
        	if(bot!=null)
        	{
        		{
        			Graphics g	= buffer.getGraphics();
	        		g.setColor(Color.MAGENTA);
	        		g.fillRect(botX * BOTW, botY * BOTH, BOTW, BOTH);
//                    g.setColor(Color.BLACK);
  //                  g.drawRect(botX * 4, botY * 4, 4, 4);
	                paintPanel.repaint();
	        	}
        		StringBuilder buf	= new StringBuilder();
        		buf.append("<html>");
        		buf.append("<p>Многоклеточный: ");
        		switch(bot.isMulti())
        		{
        			case 0:// - нет,
        				buf.append("нет</p>");
        				break;
        			case 1:// - есть MPREV,
        				buf.append("есть MPREV</p>");
        				break;
        			case 2:// - есть MNEXT,
        				buf.append("есть MNEXT</p>");
        				break;
        			case 3:// есть MPREV и MNEXT
        				buf.append("есть MPREV и MNEXT</p>");
        				break;
        		}
        		buf.append("<p>c_blue="+bot.c_blue);
        		buf.append("<p>c_green="+bot.c_green);
        		buf.append("<p>c_red="+bot.c_red);
        		buf.append("<p>direction="+bot.direction);
        		buf.append("<p>health="+bot.health);
        		buf.append("<p>mineral="+bot.mineral);
        		
        		
        	    //buf.append("");
       	    
        	    IBotGeneController cont;
                for (int i = 0; i < Bot.MIND_SIZE; i++)
                {//15
                    int command = bot.mind[i];  // текущая команда
                    
                    // Получаем обработчика команды
                    cont	= Bot.geneController[command];
                    if(cont!=null)// если обработчик такой команды назначен
                    {
                    	buf.append("<p>");
                    	buf.append(String.valueOf(i));
                    	buf.append("&nbsp;");
                    	buf.append(cont.getDescription(bot, i));
                    	buf.append("</p>");
                    }
                }
        	    
        	    buf.append("</html>");
	        	JComponent component = (JComponent)e.getSource();
	        	//System.out.println(bot);
	        	paintPanel.setToolTipText(buf.toString());
	            MouseEvent phantom = new MouseEvent(
	                    component,
	                    MouseEvent.MOUSE_MOVED,
	                    System.currentTimeMillis()-2000,
	                    0,
	                    x,
	                    y,
	                    0,
	                    false);
	
	            ToolTipManager.sharedInstance().mouseMoved(phantom);
        	}
        
        }

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {}

        public void mouseReleased(MouseEvent e) {}
   }
	@Override
	public void setView(IView view)
	{
		this.view	= view;
		this.paint();
	}
    public void paint() {
    	buffer = this.view.paint(this.world,this.paintPanel);
        //generationLabel.setText(" Generation: " + String.valueOf(world.generation));
        populationLabel.setText(" Population: " + String.valueOf(world.population));
        organicLabel.setText(" Organic: " + String.valueOf(world.organic));
        //recorderBufferLabel.setText(" Buffer: " + String.valueOf(world.recorder.getBufferSize()));
        
        Runtime runtime = Runtime.getRuntime();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        memoryLabel.setText(" Memory MB: " + String.valueOf(memory/(1024L * 1024L)));
        
        //frameSavedCounterLabel.setText(" Saved frames: " + String.valueOf(world.world.recorder.getFrameSavedCounter()));
        //frameSkipSizeLabel.setText(" Skip frames: " + String.valueOf(world.world.recorder.getFrameSkipSize()));
        

        paintPanel.repaint();
    }
    @Override
    public ProjectProperties getProperties()
    {
    	return MainWindow.window.getProperties();
    }
}

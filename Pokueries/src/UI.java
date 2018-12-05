import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;

import javax.print.DocFlavor.URL;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import sun.audio.*;

public class UI implements ActionListener {
	
	String key ="a";
	
	static JFrame mainFrame = new JFrame();
	static JInternalFrame menuFrame = new JInternalFrame("Menu",true,true,true,true);
	static JInternalFrame gameFrame = new JInternalFrame("Game",true,true,true,true);
	static JInternalFrame playFrame = new JInternalFrame("Game",true,true,true,true);
	static JInternalFrame pokedexFrame = new JInternalFrame("Pokedex",true,true,true,true);
	
	JButton play = new JButton();
	JButton pokedex = new JButton();
	JButton exit = new JButton();
	
	JPanel top = new JPanel();
	JPanel middle = new JPanel();
	JPanel bottom = new JPanel();
	JPanel left = new JPanel();
	JPanel right = new JPanel();
	
	Font font = new Font("sans_serif", Font.PLAIN, 40);
	
	GridBagConstraints gbcMenu = new GridBagConstraints();
	GridBagConstraints gbcGame = new GridBagConstraints();
	GridBagConstraints gbcGame1 = new GridBagConstraints();
	GridBagConstraints gbcGame2 = new GridBagConstraints();
	GridBagConstraints gbcPokedex = new GridBagConstraints();

	public void draw()
	{
		mainFrame.setSize(600, 700);
		mainFrame.setTitle("Pokueries");
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		menuFrame.setSize(600, 700);
		
		gbcMenu.weightx = 1.0;
		gbcMenu.weighty = 1.0;
		gbcMenu.ipady = 40;
		gbcMenu.fill = GridBagConstraints.BOTH;
		
		play.setText("Play");
		pokedex.setText("Pokedex");
		exit.setText("Exit");
		
		play.setFont(font);
		pokedex.setFont(font);
		exit.setFont(font);
		
		play.setAlignmentX(Component.CENTER_ALIGNMENT);
		pokedex.setAlignmentX(Component.CENTER_ALIGNMENT);
		exit.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		middle.add(play, gbcMenu);
		gbcMenu.gridy = 1;
		middle.add(pokedex, gbcMenu);
		gbcMenu.gridy = 2;
		middle.add(exit, gbcMenu);
		
		
		menuFrame.add(top, BorderLayout.NORTH);
		menuFrame.add(middle, BorderLayout.CENTER);
		menuFrame.add(bottom, BorderLayout.SOUTH);
		
		menuFrame.setVisible(true);
		
		mainFrame.add(menuFrame);
		mainFrame.setVisible(true);
		
        play.addActionListener(new ActionListener(){
       	 public void actionPerformed(ActionEvent e){
       		game(); 
       	 }
        });
        pokedex.addActionListener(new ActionListener(){
          	 public void actionPerformed(ActionEvent e){
          		pokedex(); 
          	 }
        });
	}
	public void pokedex()
	{
		try 
		{
			menuFrame.setClosed(true);
		} 
		catch (PropertyVetoException e) 
		{
			e.printStackTrace();
		}
		
		cleanPanels();
		
		
		pokedexFrame.setSize(mainFrame.getSize());
		
		java.net.URL url = UI.class.getResource("/resources/Bulbasaur.gif.gif");
        ImageIcon iconPoke = new ImageIcon(url);
        Image Pokeimage = iconPoke.getImage();
        Image newimg = Pokeimage.getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        
        JLabel label = new JLabel(new ImageIcon(newimg));
        
        String col[] = {"ID", "Name", "HP"};

        DefaultTableModel tableModel = new DefaultTableModel(col, 0);            // The 0 argument is number rows.

        JTable table = new JTable(tableModel);
        
        table.setDefaultEditor(Object.class, null);
        
        JScrollPane scroll = new JScrollPane(table);
        
        final ArrayList<Pokemon> pokemon = GameMaster.allPokemon;
        
        
        System.out.println(pokemon.size());
        
        for(int i = 0; i < pokemon.size(); i++)
        {
        	Object[] objs = {pokemon.get(i).getID(), pokemon.get(i).getName(), pokemon.get(i).getHP(), 11, 2, 2, 15, 30, 11, 19};
        	tableModel.addRow(objs);
        }
		
		right.add(label, gbcPokedex);
		bottom.add(scroll, gbcPokedex);
		
		right.setBorder(BorderFactory.createBevelBorder(1));
		bottom.setBorder(BorderFactory.createBevelBorder(1));
		left.setBorder(BorderFactory.createBevelBorder(1));
		
		pokedexFrame.add(right, BorderLayout.EAST);
		pokedexFrame.add(bottom, BorderLayout.SOUTH);
		pokedexFrame.add(left, BorderLayout.WEST);
		
		pokedexFrame.setVisible(true);
		
		mainFrame.add(pokedexFrame);
		
		table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		            System.out.println(row);
		            pokedexFrame.setTitle(pokemon.get(row).getName());
		            
		            right.removeAll();
		            
		    		java.net.URL url = UI.class.getResource("/resources/" + pokemon.get(row).getName() + ".gif.gif");
		            ImageIcon iconPoke = new ImageIcon(url);
		            Image Pokeimage = iconPoke.getImage();
		            Image newimg = Pokeimage.getScaledInstance(120, 120, Image.SCALE_DEFAULT);
		            
		            JLabel label = new JLabel(new ImageIcon(newimg));
		            
		            right.add(label, gbcPokedex);
		            
		            String attack2 = null;
		            
		            left.removeAll();
		            attack2 = pokemon.get(row).getAttack2Name();
		            
		            
		            if (!(attack2 == null))
		            {
		            left.add(new JLabel("<html>HP: " + pokemon.get(row).getHP() + "<br>Attack 1: " + pokemon.get(row).getAttack1Name() + 
		            		"<br>Damage: " + pokemon.get(row).getAttack1Damage() + "<br>Attack 2: " + attack2 + 
		            		"<br>Damage: " + pokemon.get(row).getAttack2Damage() + "<br></html>"), gbcPokedex);
		            }
		            else
		            {
		            	System.out.println("else");
		            	left.add(new JLabel("<html>HP: " + pokemon.get(row).getHP() + "<br>Attack 1: " + pokemon.get(row).getAttack1Name() + 
			            		"<br>Damage: " + pokemon.get(row).getAttack1Damage() + "<br></html>"), gbcPokedex);
		            }
		        }
		    }
		});
	}
	
	public void game()
	{
		try 
		{
			menuFrame.setClosed(true);
		} 
		catch (PropertyVetoException e) 
		{
			e.printStackTrace();
		}
		
		cleanPanels();
		
		mainFrame.setSize(1600, 900);
		
		Player player1 = new Player();
		Player player2 = new Player();
		
		gbcGame.insets = new Insets(1,1,1,100);
		gbcGame1.insets = new Insets(1,100,1,1);
		
		
		//gbcGame.gridx = 0;
		//gbcGame.gridy = 0;
		
		gameFrame.setSize(mainFrame.getSize());
		
        String col[] = {"Name", "Image"};

        DefaultTableModel tableModel1 = new DefaultTableModel(col, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                switch(column) {
                    case 1: return ImageIcon.class;
                    default: return Object.class;
                }
            }
        };           // The 0 argument is number rows.
        DefaultTableModel tableModel2 = new DefaultTableModel(col, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                switch(column) {
                    case 1: return ImageIcon.class;
                    default: return Object.class;
                }
            }
        }; 
        
        
        JTable table1 = new JTable(tableModel1);
        JTable table2 = new JTable(tableModel2);
        
        table1.setTableHeader(null);
        table2.setTableHeader(null);
        
        table1.setRowHeight(100);
        table2.setRowHeight(100);
        
        table1.setDefaultEditor(Object.class, null);
        table2.setDefaultEditor(Object.class, null);
        
        JScrollPane scroll1 = new JScrollPane(table1);
        JScrollPane scroll2 = new JScrollPane(table2);
        
        final ArrayList<Pokemon> pokemon = GameMaster.allPokemon;
        
        
        java.net.URL url = UI.class.getResource("/resources/Bulbasaur.png");
        ImageIcon iconPoke = new ImageIcon(url);
        Image Pokeimage = iconPoke.getImage();
        Image newimg = Pokeimage.getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        
        JLabel label = new JLabel(new ImageIcon(newimg));
        
        for(int i = 0; i < pokemon.size(); i++)
        {
        	ImageIcon place = new ImageIcon(newimg);
        	Object[] objs = {pokemon.get(i).getName(), place, 11, 2, 2, 15, 30, 11, 19};
        	tableModel1.addRow(objs);
        	tableModel2.addRow(objs);
        }
        
        Dimension button = new Dimension(135,135);
        
		table1.addMouseListener(new MouseAdapter() 
		{
		    public void mousePressed(MouseEvent mouseEvent) 
		    {
		    	Point point = mouseEvent.getPoint();
		        int row = table1.rowAtPoint(point);
		        if(player1.Pokemon.size() < 6)
		        if (mouseEvent.getClickCount() == 2 && table1.getSelectedRow() != -1) 
		        {
		            java.net.URL url2 = UI.class.getResource("/resources/" + pokemon.get(row).getName() + ".gif.gif");
		            ImageIcon iconPoke2 = new ImageIcon(url2);
		            Image Pokeimage2 = iconPoke2.getImage();
		            Image newimg2 = Pokeimage2.getScaledInstance(110, 110, Image.SCALE_DEFAULT);
		        	
		        	JButton here = new JButton(new ImageIcon(newimg2));
		        	
		        	here.setName(key + "b");
		        	
		        	key = key.concat("b");
		        	
		        	here.setMinimumSize(button);
		        	gbcGame.gridx=1;
		        	gbcGame.gridy++;
		        	middle.add(here, gbcGame);
		        	
		        	pokemon.get(row).setKey(key);
		        	
		        	player1.addPokemon(pokemon.get(row));
		        	
		        	System.out.println(pokemon.get(row) + " Added");
		        
		        	Container parent = middle.getParent();
		        	parent.revalidate();
		        	parent.repaint();
		        	
		            here.addActionListener(new ActionListener()
		            {
		            	 public void actionPerformed(ActionEvent e)
		            	 {
		            		 JButton btn = (JButton)e.getSource();
			            	for(int i = 0; i < player1.Pokemon.size(); i++)
			            		if(player1.Pokemon.get(i).getKey().equals(btn.getName()))
			          			{
					           		System.out.println("Removing " + player1.Pokemon.get(i).getName());				            		
					           		player1.Pokemon.remove(i);
			           			}
		            		gbcGame.gridy--;
		            		Container parent = btn.getParent();
		            		parent.remove(btn);
		            		parent.revalidate();
		            		parent.repaint();
		            	 }
		          });
		        
		        }
		    }
		});
		
		table2.addMouseListener(new MouseAdapter() 
		{
		    public void mousePressed(MouseEvent mouseEvent) 
		    {
		    	Point point = mouseEvent.getPoint();
		        int row = table2.rowAtPoint(point);
		        if(player2.Pokemon.size() < 6)
		        if (mouseEvent.getClickCount() == 2 && table2.getSelectedRow() != -1) 
		        {
		            java.net.URL url2 = UI.class.getResource("/resources/" + pokemon.get(row).getName() + ".gif.gif");
		            ImageIcon iconPoke2 = new ImageIcon(url2);
		            Image Pokeimage2 = iconPoke2.getImage();
		            Image newimg2 = Pokeimage2.getScaledInstance(110, 110, Image.SCALE_DEFAULT);
		        	
		        	JButton here = new JButton(new ImageIcon(newimg2));
		        	here.setName(key + "b");
		        	
		        	key = key.concat("b");
		        	
		        	here.setMinimumSize(button);
		        	gbcGame1.gridx=4;
		        	gbcGame1.gridy++;
		        	middle.add(here, gbcGame1);
		        	
		        	pokemon.get(row).setKey(key);
		        	
		        	player2.addPokemon(pokemon.get(row));
		        	
		        	System.out.println(pokemon.get(row) + " Added");
		        
		        	Container parent = middle.getParent();
		        	parent.revalidate();
		        	parent.repaint();
		        	
		            here.addActionListener(new ActionListener()
		            {
		            	 public void actionPerformed(ActionEvent e)
		            	 {
		            		JButton btn = (JButton)e.getSource();
		            		for(int i = 0; i <player2.Pokemon.size(); i++)
		            			if(player2.Pokemon.get(i).getKey().equals(btn.getName()))
		            			{
				            		System.out.println("Removing " + player2.Pokemon.get(i).getName());				            		
				            		player2.Pokemon.remove(i);
		            			}

		            		gbcGame1.gridy--;
		            		Container parent = btn.getParent();
		            		parent.remove(btn);
		            		parent.revalidate();
		            		parent.repaint();
		            	 }
		          });
		        
		        }
		    }
		});
        
        JButton playGame = new JButton("Play!");
        
        playGame.addActionListener(new ActionListener(){
         	 public void actionPerformed(ActionEvent e){
         		play(); 
         	 }
       });
		
        java.net.URL url2 = UI.class.getResource("/resources/bulbasaur.gif.gif");
        ImageIcon iconPoke2 = new ImageIcon(url2);
        Image Pokeimage2 = iconPoke2.getImage();
        Image newimg2 = Pokeimage2.getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        
      /*  JButton team11 = new JButton(new ImageIcon(newimg2));
        JButton team12 = new JButton();
        JButton team13 = new JButton();
        JButton team14 = new JButton();
        JButton team15 = new JButton();
        JButton team16 = new JButton();
        
        team11.setBorderPainted(false);
        team12.setBorderPainted(false);
        team13.setBorderPainted(false);
        team14.setBorderPainted(false);
        team15.setBorderPainted(false);
        team16.setBorderPainted(false);
        
        JButton team21 = new JButton();
        JButton team22 = new JButton();
        JButton team23 = new JButton();
        JButton team24 = new JButton();
        JButton team25 = new JButton();
        JButton team26 = new JButton();
        
        team11.addActionListener(new ActionListener(){
         	 public void actionPerformed(ActionEvent e){
         		JButton btn = (JButton)e.getSource();
         		Container parent = btn.getParent();
         		parent.remove(btn);
         		parent.revalidate();
         		parent.repaint();
         	 }
       });
        
        team21.setBorderPainted(false);
        team22.setBorderPainted(false);
        team23.setBorderPainted(false);
        team24.setBorderPainted(false);
        team25.setBorderPainted(false);
        team26.setBorderPainted(false);*/
        
        
        gbcGame.gridx=1;
        JLabel team1 = new JLabel("Team 1:");
        team1.setFont(font);
        
        JLabel team2 = new JLabel("Team 2:");
        team2.setFont(font);
        
        middle.add(team1, gbcGame);
       // gbcGame.gridy = 1;
       //middle.add(team11, gbcGame);
       // middle.add(team12, gbcGame);
       // middle.add(team13, gbcGame);
       // middle.add(team14, gbcGame);
       // middle.add(team15, gbcGame);
       // middle.add(team16, gbcGame);
        gbcGame1.gridx=4;
        middle.add(team2, gbcGame1);
        gbcGame.gridy++;
        gbcGame1.gridy++;
        
        gbcGame2.gridx=2;
        playGame.setFont(font);
        middle.add(playGame, gbcGame2);
       // gbcGame.gridy = 3;
       // middle.add(team21, gbcGame);
       // middle.add(team22, gbcGame);
       // middle.add(team23, gbcGame);
       // middle.add(team24, gbcGame);
       // middle.add(team25, gbcGame);
       // middle.add(team26, gbcGame);
		
        left.add(scroll1, gbcGame);
		right.add(scroll2, gbcGame);
		
		gameFrame.add(middle, BorderLayout.CENTER);
		gameFrame.add(scroll1, BorderLayout.WEST);
		gameFrame.add(scroll2, BorderLayout.EAST);
		
		gameFrame.setVisible(true);
		mainFrame.add(gameFrame);
	}
	
	public void cleanPanels()
	{
		top.removeAll();
		middle.removeAll();
		bottom.removeAll();
		left.removeAll();
		right.removeAll();
	}
	public void play()
	{
		try 
		{
			gameFrame.setClosed(true);
		} 
		catch (PropertyVetoException e) 
		{
			e.printStackTrace();
		}
		
        java.net.URL url2 = UI.class.getResource("/resources/grassBackground.png");
        ImageIcon iconPoke2 = new ImageIcon(url2);
        Image Pokeimage2 = iconPoke2.getImage();
        Image newimg2 = Pokeimage2.getScaledInstance(1600, 1800, Image.SCALE_DEFAULT);
        
        JLabel label = new JLabel(new ImageIcon(newimg2));
        
        playFrame.add(label);
		
		cleanPanels();
		
	    String thisFile = "C:\\Users\\Dogan\\git\\Pokueries\\Pokueries\\src\\resources\\battle.wav";
	    InputStream in = null;
	    
	    
		try {
			in = new FileInputStream(thisFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // create an audiostream from the inputstream
	    AudioStream audioStream = null;
		try {
			audioStream = new AudioStream(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // play the audio clip with the audioplayer class
	    AudioPlayer.player.start(audioStream);
		
		playFrame.setVisible(true);
		mainFrame.add(playFrame);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	public UI()
	{
		top.setLayout(new GridBagLayout());
		middle.setLayout(new GridBagLayout());
		bottom.setLayout(new GridBagLayout());
		left.setLayout(new GridBagLayout());
		right.setLayout(new GridBagLayout());		
		
		draw();
	}
}
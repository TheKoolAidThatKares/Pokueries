import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.BufferedInputStream;
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
import java.util.TimerTask;

import javax.print.DocFlavor.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
	Font font2 = new Font("sans_serif", Font.PLAIN, 20);
	Font font3 = new Font("sans_serif", Font.BOLD, 40);
	
	GridBagConstraints gbcGame = new GridBagConstraints();
	GridBagConstraints gbcGame1 = new GridBagConstraints();
	GridBagConstraints gbcGame2 = new GridBagConstraints();
	GridBagConstraints gbcPokedex = new GridBagConstraints();
	
	static int turns = 1;
	static int player1Poke = 0;
	static int player2Poke = 0;
	static int dmg;
	static int hp;
	static int j = 0;

	public void draw()
	{
		GridBagConstraints gbcMenu = new GridBagConstraints();
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
        exit.addActionListener(new ActionListener(){
         	 public void actionPerformed(ActionEvent e){
         		System.exit(1);
         	 }
       });
	}
	public void pokedex()
	{
		try 
		{
			menuFrame.setVisible(false);
		} 
		catch (Exception e) 
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
        
		JButton back = new JButton("Menu");
		
        back.addActionListener(new ActionListener(){
        	 public void actionPerformed(ActionEvent e){
        		try {
        			
					pokedexFrame.setClosed(true);
					menuFrame.setClosed(false);
					menuFrame.remove(top);
					menuFrame.remove(middle);
					menuFrame.remove(bottom);
					bottom.removeAll();
					middle.removeAll();
					top.removeAll();
					menuFrame.setVisible(true);
					cleanPanels();
					showMenu();
					mainFrame.repaint();
					mainFrame.revalidate();
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
        	 }
      });
        
        back.setMinimumSize(new Dimension(100, 30));
        
       
        String col[] = {"ID", "Name", "HP"};

        DefaultTableModel tableModel = new DefaultTableModel(col, 0);            // The 0 argument is number rows.

        JTable table = new JTable(tableModel);
        
        table.setDefaultEditor(Object.class, null);
        
        JScrollPane scroll = new JScrollPane(table);
        
        final ArrayList<Pokemon> pokemon = GameMaster.allPokemon;
        
        
        //System.out.println(pokemon.size());
        
        for(int i = 0; i < pokemon.size(); i++)
        {
        	Object[] objs = {pokemon.get(i).getID(), pokemon.get(i).getName(), pokemon.get(i).getHP(), 11, 2, 2, 15, 30, 11, 19};
        	tableModel.addRow(objs);
        }
		
		right.add(label, gbcPokedex);
		bottom.add(scroll, gbcPokedex);
		
        gbcPokedex.gridy = 20;
		
        bottom.add(back, gbcPokedex);
		
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
		        if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
		            System.out.println(row);
		            pokedexFrame.setTitle(pokemon.get(row).getName());
		            
		            right.removeAll();
		            
		    		java.net.URL url = UI.class.getResource("/resources/" + pokemon.get(row).getName() + ".gif.gif");
		            ImageIcon iconPoke = new ImageIcon(url);
		            Image Pokeimage = iconPoke.getImage();
		            Image newimg = Pokeimage.getScaledInstance(120, 120, Image.SCALE_DEFAULT);
		            
		            JLabel label = new JLabel(new ImageIcon(newimg));
		            
		            right.add(label, gbcPokedex);
		            
		           
		            
		            left.removeAll();
		            
		            // PokemonGetter to query the database
		            PokemonGetter dataModifier = new PokemonGetter();
		            
		            
		            
		            JLabel lblHP = new JLabel("HP:");
		            JLabel lblAtt1 = new JLabel("Attack 1:");
		            JLabel lblDmg1 = new JLabel("Damage:");
		            JLabel lblAtt2 = new JLabel("Attack 2:");
		            JLabel lblDmg2 = new JLabel("Damage:");
		            
		            JTextField txtHP = new JTextField("", 2);
		            txtHP.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								dataModifier.connectToDB();
								dataModifier.statement = dataModifier.connect.createStatement();
								dataModifier.statement.executeUpdate("update cards set hp = " + Integer.parseInt(txtHP.getText()) + 
										" where pokemon_id = " + pokemon.get(row).getID());
								dataModifier.statement.close();
								dataModifier.connect.close();
								
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							System.out.println("HP Changed");
						}
		            	
		            });
		            
		            JTextField txtAtt1 = new JTextField("", 5);
		            txtAtt1.addActionListener(new ActionListener() {
		            	public void actionPerformed(ActionEvent e) {
		            		try {
								dataModifier.connectToDB();
								dataModifier.statement = dataModifier.connect.createStatement();
								dataModifier.statement.executeUpdate("update cards set attack1 = " + Integer.parseInt(txtAtt1.getText()) + 
										" where pokemon_id = " + pokemon.get(row).getID());
								dataModifier.statement.close();
								dataModifier.connect.close();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		            		System.out.println("Attack1 Changed");
		            	}
		            });
		            
		            JTextField txtDmg1 = new JTextField("", 2);
		            txtDmg1.addActionListener(new ActionListener() {
		            	public void actionPerformed(ActionEvent e) {
		            		try {
								dataModifier.connectToDB();
								dataModifier.statement = dataModifier.connect.createStatement();
								dataModifier.statement.executeUpdate("update attacks set damage = " + Integer.parseInt(txtDmg1.getText()) + 
										" where attack_id = " + pokemon.get(row).getAttack1());
								System.out.println("Pokemon Attack1 = " + pokemon.get(row).getAttack1());
								dataModifier.statement.close();
								dataModifier.connect.close();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		            		System.out.println("Damage1 Changed");
		            	}
		            });
		            
		            JTextField txtAtt2 = new JTextField("", 5);
		            txtAtt2.addActionListener(new ActionListener() {
		            	public void actionPerformed(ActionEvent e)
		            	{
		            		try {
								dataModifier.connectToDB();
								dataModifier.statement = dataModifier.connect.createStatement();
								dataModifier.statement.executeUpdate("update cards set attack2 = " + Integer.parseInt(txtAtt2.getText()) + 
										" where pokemon_id = " + pokemon.get(row).getID());
								dataModifier.statement.close();
								dataModifier.connect.close();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		            		System.out.println("Attack2 Changed");
		            	}
		            });
		            
		            JTextField txtDmg2 = new JTextField("", 2);
		            txtDmg2.addActionListener(new ActionListener() {
		            	public void actionPerformed(ActionEvent e) {
		            		try {
								dataModifier.connectToDB();
								dataModifier.statement = dataModifier.connect.createStatement();
								dataModifier.statement.executeUpdate("update attacks set damage = " + Integer.parseInt(txtDmg2.getText()) + 
										" where attack_id = " + pokemon.get(row).getAttack2());
								
								System.out.println("PokemonAttack2 = " + pokemon.get(row).getAttack2());
								dataModifier.statement.close();
								dataModifier.connect.close();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		            		System.out.println("Damage2 Changed");
		            	}
		            });
		           
		            JButton insert = new JButton("Insert");
		            JButton update = new JButton("Update");
		            JButton delete = new JButton("Delete");
		            
		            gbcPokedex.gridx = 0;
		            gbcPokedex.gridy = 0;		            
		            left.add(lblHP, gbcPokedex);
		            gbcPokedex.gridx= 1;
		            gbcPokedex.gridy = 0;
		            left.add(txtHP, gbcPokedex);
		            gbcPokedex.gridx = 0;
		            gbcPokedex.gridy = 1;		            
		            left.add(lblAtt1, gbcPokedex);
		            gbcPokedex.gridx = 1;
		            gbcPokedex.gridy = 1;
		            left.add(txtAtt1, gbcPokedex);
		            gbcPokedex.gridx = 0;
		            gbcPokedex.gridy = 2;
		            left.add(lblDmg1, gbcPokedex);
		            gbcPokedex.gridx = 1;
		            gbcPokedex.gridy = 2;
		            left.add(txtDmg1, gbcPokedex);
		            gbcPokedex.gridx = 0;
		            gbcPokedex.gridy = 3;
		            left.add(lblAtt2, gbcPokedex);
		            gbcPokedex.gridx = 1;
		            gbcPokedex.gridy = 3;
		            left.add(txtAtt2, gbcPokedex);
		            gbcPokedex.gridx = 0;
		            gbcPokedex.gridy = 4;
		            left.add(lblDmg2, gbcPokedex);
		            gbcPokedex.gridx = 1;
		            gbcPokedex.gridy = 4;
		            left.add(txtDmg2, gbcPokedex);
		            
		            String attack2 = null;
		            attack2 = pokemon.get(row).getAttack2Name();
		            
		            if (!(attack2 == null))
		            {
		            	lblHP.setVisible(true);
			            txtHP.setVisible(true);
			            lblAtt1.setVisible(true);
			            txtAtt1.setVisible(true);
			            lblDmg1.setVisible(true);
			            txtDmg1.setVisible(true);
			            lblAtt2.setVisible(true);
			            txtAtt2.setVisible(true);
			            lblDmg2.setVisible(true);
			            txtDmg2.setVisible(true);
		            	
						txtHP.setText(String.valueOf(pokemon.get(row).getHP()));
						txtAtt1.setText(String.valueOf(pokemon.get(row).getAttack1()));
						txtDmg1.setText(String.valueOf(pokemon.get(row).getAttack1Damage()));
						txtAtt2.setText(String.valueOf(pokemon.get(row).getAttack2()));
						txtDmg2.setText(String.valueOf(pokemon.get(row).getAttack2Damage()));
		            }
		            else
		            {
		            	lblAtt2.setVisible(false);
			            txtAtt2.setVisible(false);
			            lblDmg2.setVisible(false);
			            txtDmg2.setVisible(false);
		            	
		            	lblHP.setVisible(true);
			            txtHP.setVisible(true);
			            lblAtt1.setVisible(true);
			            txtAtt1.setVisible(true);
			            lblDmg1.setVisible(true);
			            txtDmg1.setVisible(true);
			            	            	
		            	txtHP.setText(String.valueOf(pokemon.get(row).getHP()));
						txtAtt1.setText(String.valueOf(pokemon.get(row).getAttack1()));
						txtDmg1.setText(String.valueOf(pokemon.get(row).getAttack1Damage()));
		            }
		            
		            /*
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
		            */
		            
		           
		            
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
                            
        for(int i = 0; i < pokemon.size(); i++)
        {
        	try{
            java.net.URL url = UI.class.getResource("/resources/" + pokemon.get(i).getName() + ".png");
            ImageIcon iconPoke = new ImageIcon(url);
            Image Pokeimage = iconPoke.getImage();
            Image newimg = Pokeimage.getScaledInstance(120, 120, Image.SCALE_DEFAULT);
            
          //  //System.out.println(i);
            
            JLabel label = new JLabel(new ImageIcon(newimg));
        	
        	ImageIcon place = new ImageIcon(newimg);
        	Object[] objs = {pokemon.get(i).getName(), place, 11, 2, 2, 15, 30, 11, 19};
        	tableModel1.addRow(objs);
        	tableModel2.addRow(objs);
        	}
        	catch(Exception e)
        	{
        		
        	}
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
		        	
		        	//System.out.println(pokemon.get(row) + " Added");
		        
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
					           		//System.out.println("Removing " + player1.Pokemon.get(i).getName());				            		
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
		        	
		        	//System.out.println(pokemon.get(row) + " Added");
		        
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
				            		//System.out.println("Removing " + player2.Pokemon.get(i).getName());				            		
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
         		play(player1, player2); 
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
	public void play(Player player1, Player player2)
	{
		try 
		{
			gameFrame.setClosed(true);
		} 
		catch (PropertyVetoException e) 
		{
			e.printStackTrace();
		}
		
		playFrame.setLayout(null);
		
		cleanPanels();
		

		
		//playFrame.setLayout(new FlowLayout());;
		
        java.net.URL url2 = UI.class.getResource("/resources/grassBackground.png");
        ImageIcon iconPoke2 = new ImageIcon(url2);
        Image Pokeimage2 = iconPoke2.getImage();
        Image background = Pokeimage2.getScaledInstance(1600, 2000, Image.SCALE_DEFAULT);
        
        java.net.URL url3 = UI.class.getResource("/resources/" + player1.Pokemon.get(0).getName() + "Back.gif.gif");
        ImageIcon iconPoke3 = new ImageIcon(url3);
        Image Pokeimage3 = iconPoke3.getImage();
        Image play3 = Pokeimage3.getScaledInstance(600, 600, Image.SCALE_DEFAULT);
        
        java.net.URL url4 = UI.class.getResource("/resources/" + player2.Pokemon.get(0).getName() + ".gif.gif");
        ImageIcon iconPoke4 = new ImageIcon(url4);
        Image Pokeimage4 = iconPoke4.getImage();
        Image play4 = Pokeimage4.getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        
        java.net.URL url5 = UI.class.getResource("/resources/health2.png");
        ImageIcon iconPoke5 = new ImageIcon(url5);
        Image Pokeimage5 = iconPoke5.getImage();
        Image play5 = Pokeimage5.getScaledInstance(500, 150, Image.SCALE_DEFAULT);
        
        java.net.URL url6 = UI.class.getResource("/resources/health1.png");
        ImageIcon iconPoke6 = new ImageIcon(url6);
        Image Pokeimage6 = iconPoke6.getImage();
        Image play6 = Pokeimage6.getScaledInstance(600, 200, Image.SCALE_DEFAULT);
        
        java.net.URL url7 = UI.class.getResource("/resources/green.jpg");
        ImageIcon iconPoke7 = new ImageIcon(url7);
        Image Pokeimage7 = iconPoke7.getImage();
        Image play7 = Pokeimage7.getScaledInstance(250, 10, Image.SCALE_DEFAULT);
        
        java.net.URL url8 = UI.class.getResource("/resources/yellow.jpg");
        ImageIcon iconPoke8 = new ImageIcon(url8);
        Image Pokeimage8 = iconPoke8.getImage();
        Image yellow = Pokeimage8.getScaledInstance(230, 15, Image.SCALE_DEFAULT);
        
        java.net.URL url9 = UI.class.getResource("/resources/red.jpg");
        ImageIcon iconPoke9 = new ImageIcon(url9);
        Image Pokeimage9 = iconPoke9.getImage();
        Image red = Pokeimage9.getScaledInstance(230, 15, Image.SCALE_DEFAULT);
        
        java.net.URL urla = UI.class.getResource("/resources/green.jpg");
        ImageIcon iconPokea = new ImageIcon(urla);
        Image Pokeimagea = iconPokea.getImage();
        Image green = Pokeimagea.getScaledInstance(230, 15, Image.SCALE_DEFAULT);
        
        JLabel label = new JLabel(new ImageIcon(background));
        BlinkLabel label2 = new BlinkLabel(new ImageIcon(play3)); 
        BlinkLabel label3 = new BlinkLabel(new ImageIcon(play4));
        BlinkLabel label4 = new BlinkLabel(new ImageIcon(play5)); 
        BlinkLabel label5 = new BlinkLabel(new ImageIcon(play6));
        BlinkLabel name1 = new BlinkLabel(player1.Pokemon.get(player1Poke).getName()); 
        BlinkLabel name2 = new BlinkLabel(player2.Pokemon.get(player1Poke).getName());
        BlinkLabel health1 = new BlinkLabel(new ImageIcon(green));
        BlinkLabel health2 = new BlinkLabel(new ImageIcon(green));
        
       // GridBagConstraints gameCon = new GridBagConstraints();
        
        label.setLayout(null);
        
        label.setBounds(00, 00, 00, 00);
        label.setSize(new Dimension(1600,900));
        //gameCon.insets = new Insets(650,450,50,100);
        //gameCon.insets = new Insets(650,450,50,100);
        //label.add(label2, gameCon);
        //gameCon.insets = new Insets(0,0,400,550);
        //label.add(label3, gameCon);
        
       /* JPanel pane1 = new JPanel();
        pane1.add(label2);
        pane1.setOpaque(false);
        JPanel pane2 = new JPanel();
        pane1.add(label3);
        pane2.setOpaque(false); */
        
        GridLayout buttonLay = new GridLayout();
        
        JPanel buttons = new JPanel(buttonLay);
        JButton attack = new JButton("Attack");
        attack.setMinimumSize(new Dimension(100,30));
        JButton items = new JButton("Items");
        buttonLay.setRows(2);
        JButton poke = new JButton("Pokemon");
        JButton exit = new JButton("Exit");
        
        attack.setFont(font2);
        items.setFont(font2);
        poke.setFont(font2);
        exit.setFont(font2);
        
        buttons.add(attack);
        buttons.add(items);
        buttons.add(poke);
        buttons.add(exit);
        
        label2.move(70, 300);
        label2.setSize(new Dimension(700,700));
        label.add(label2);
        label3.move(1050, 100);
        label3.setSize(new Dimension(400,400));
        label.add(label3);
        label4.move(-100, -300);
        label4.setSize(new Dimension(700,700));
        label.add(label4);
        label5.move(975, 260);
        label5.setSize(new Dimension(700,700));
        
        name1.setSize(new Dimension(250,100));
        name2.setSize(new Dimension(250,100));
        
        name1.move(150, 275);
        name2.move(140, 290);
        
        name1.setFont(font3);
        name1.setForeground(Color.DARK_GRAY);
        name2.setFont(font3);
        name2.setForeground(Color.DARK_GRAY);
        
        health1.setSize(new Dimension(230,200));
        health2.setSize(new Dimension(230,200));
        
        
        health1.move(270, 292);
        health2.move(410, 261);
        
        label4.add(name2);
        label5.add(name1);
        
        label4.add(health1);
        label5.add(health2);
        
        label.add(label5);
        buttons.move(1040, 700);
        buttons.setOpaque(false);
        buttons.setSize(new Dimension(500,100));
        buttons.setVisible(true);
        label.add(buttons);

        
        //pane1.setBounds(0, 0, 0, 0);
        
        
        //pane.moveToBack(label);
        //pane2.add(label2, 2, 0);
        
        playFrame.setBounds(0, 0, 0, 0);
                
        
        
        //pane1.setVisible(true);
        
        //playFrame.setContentPane(pane1);
        
        //playFrame.pack();
        //playFrame.add(pane);
        playFrame.add(label,0);
        //playFrame.add(pane2,1);
        
        
		
	    String thisFile = "C:\\Users\\Dogan\\git\\Pokueries\\Pokueries\\src\\resources\\battle.wav";
	    InputStream in = null;
	    
	    
		try {
			in = new FileInputStream(thisFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // create an audiostream from the inputstream
	   // final AudioStream battle;
		try {
			 final AudioStream battle = new AudioStream(in);
			 AudioPlayer.player.start(battle);
		
		
		//AudioPlayer.player.start(battle);
		
		//String thisFile1 = "C:\\Users\\Dogan\\git\\Pokueries\\Pokueries\\src\\resources\\ping.wav";
	    //InputStream in1 = null;
	    
			AudioStream s;
		String thisFile1 = "C:\\Users\\Dogan\\git\\Pokueries\\Pokueries\\src\\resources\\critical.wav";
	   
		InputStream in1 = null;
		
			in1 = new FileInputStream(thisFile1);

			 AudioStream loop = new AudioStream(in1);
	    
        attack.addActionListener(new ActionListener(){
        	 public void actionPerformed(ActionEvent e){
        		 
        		 ping();
    		 
    		 buttons.removeAll();
    		 
    		 JButton attack1 = new JButton();
    		 JButton attack2 = new JButton();
    		 
    		 if(turns % 2 == 1)
 			{
    			 attack1.setText(player1.Pokemon.get(player1Poke).getAttack1Name());
    			 attack2.setText(player1.Pokemon.get(player1Poke).getAttack2Name());
 			}
    		 else
    		 {
    			 attack1.setText(player2.Pokemon.get(player2Poke).getAttack1Name());
    			 attack2.setText(player2.Pokemon.get(player2Poke).getAttack2Name());
    		 }
    		 
    		// AudioPlayer.player.stop(battle);
    		 
    		 attack1.setMinimumSize(new Dimension(100,60));
    		 attack1.setFont(font2);
    		 
    		 attack2.setMinimumSize(new Dimension(100,60));
    		 attack2.setFont(font2);
    		 
    		 attack1.addActionListener(new ActionListener(){
    	       	 public void actionPerformed(ActionEvent e){
    	       		 ping();
    	     		
    	     	
    	     		
    	     		buttons.removeAll();
    	     		
    	     		dmg = 0;
    	     		hp = 0;
    	     		
	       			if(turns % 2 == 1)
	    			{
	       				dmg = player1.Pokemon.get(player1Poke).getAttack1Damage();
	       				hp = player2.Pokemon.get(player2Poke).getHP();
	    			}
	       			else
	       			{
	       				dmg = player2.Pokemon.get(player2Poke).getAttack1Damage();
	       				hp = player1.Pokemon.get(player1Poke).getHP();
	       			}
	       			
	       			////System.out.println(dmg + " " + hp);
	       			

    	     		
    	     		buttons.repaint();
    	     		buttons.revalidate();
    	     		
    	       		Timer timer = new Timer();
    	       		timer.schedule(new TimerTask() {
    	       			int i = 0;
    	       			
    	       			
    	       		  @Override
    	       		  
    	       		  public void run() {
    	       			  
    	       			  
    	       			i++;
    	       			
    	       			if(turns % 2 == 1) //player 2s turn
    	       				hp = player2.Pokemon.get(player2Poke).getHP();
    	       			else
    	       				hp = player1.Pokemon.get(player1Poke).getHP();
    	       			
    	       			//if(turns % 2 == 0)
    	       			
    	       			if(i%8==0)
    	       				if(dmg > 0)
    	       				{

    	       			if(turns % 2 == 1)
    	    			{
	       					if(j == 1){
	       						//health1.setSize(new Dimension(230, 200));
    	       					player2.Pokemon.get(player2Poke).setHP(0);
    	       					faint();
    	       					player2Poke++;
    	       					player2.Pokemon.get(player2Poke).setCurrentHP(player2.Pokemon.get(player2Poke).getHP());
    	       					System.out.println("poke up");
    	       					dmg = 0;
    	       					j = 0;
    	       				}
    	       				else
    	       				{
    	       				player2.Pokemon.get(player2Poke).setHP(player2.Pokemon.get(player2Poke).getHP()-1);
    	       				System.out.println(dmg);
    	       				dmg--;
    	       				
    	       				health1.setSize(health1.getSize().width-(6),health1.getSize().height);
    	       				health1.move(health1.getX(), health1.getY());
    	       				
    	       				if(health1.getSize().width < 60)
    	       					health1.setIcon(new ImageIcon(red));
    	       				else if(health1.getSize().width < 150)
    	       					health1.setIcon(new ImageIcon(yellow));
    	       				System.out.println(health1.getSize().width);
    	       				
    	       				
    	       				health1.getParent().repaint();
    	       				
    	       				
    	       				
    	       				
    	       				}
    	       				

    	    			}
    	       			else
    	       			{
	       					if(j == 1){
	       						//health1.setSize(new Dimension(230, 200));
    	       					player1.Pokemon.get(player1Poke).setHP(0);
    	       					faint();
    	       					player1Poke++;
    	       					pokeball();
    	       					player1.Pokemon.get(player1Poke).setCurrentHP(player1.Pokemon.get(player1Poke).getHP());
    	       					
    	       					j = 0;
    	       					
    	       					//System.out.println(hp);
    	       					dmg = 0;
    	       				}
    	       				else
    	       				{
    	       				player1.Pokemon.get(player1Poke).setHP(player1.Pokemon.get(player1Poke).getHP()-1);
    	       				System.out.println(dmg);
    	       				dmg--;
    	       				
    	       				health1.setSize(health1.getSize().width-(6),health1.getSize().height);
    	       				health1.move(health1.getX(), health1.getY());
    	       				
    	       				if(health1.getSize().width < 60)
    	       					health1.setIcon(new ImageIcon(red));
    	       				else if(health1.getSize().width < 150)
    	       					health1.setIcon(new ImageIcon(yellow));
    	       				System.out.println(health1.getSize().width);
    	       				
    	       				health1.getParent().repaint();
    	       				}
    	       				
    	       			}
    	       		}
    	       			
    	       			
    	       			  if(i<10){
    	       		    System.out.println(health1.size()+ " size");
    	       		    label2.move(label2.getX()+10, label2.getY()-10);
    	       		    playFrame.repaint();
    	       		    //i++;
    	       			  }
    	       		    if(i>=10&& i <20)
    	       		    {
    	       		    	if(i == 10)
    	       		    	{
    	       		    	attack();
    	       		    	label3.blink();
    	       		    	}
    	       		    	label2.move(label2.getX()-10, label2.getY()+10);
    	       		    	playFrame.repaint();
    	       		    }
    	       		    else if(i >=300)
    		    		{
    		    			//label3.repositionRight();
    		    			transition();
    		    			
	       					if(health1.size().getWidth() < 5)
	       					{
	       						health1.setSize(new Dimension(230, 200));
	       						if(turns % 2 == 1)
	       							player2Poke++;
	       						else
	       							player1Poke++;
	       						pokeball();
	       						
	       					}
	       					if(health2.size().getWidth() < 5)
	       					{
	       						j = 1;
	       					}
    		    			
    		    			if(turns % 2 == 1)
    		    			{
    		    	        java.net.URL url6 = UI.class.getResource("/resources/" + player1.Pokemon.get(player1Poke).getName() + ".gif.gif");
    		    	        ImageIcon iconPoke6 = new ImageIcon(url6);
    		    	        Image Pokeimage6 = iconPoke6.getImage();
    		    	        Image play6 = Pokeimage6.getScaledInstance(300, 300, Image.SCALE_DEFAULT);
    		    	        
    		    	        java.net.URL url5 = UI.class.getResource("/resources/" + player2.Pokemon.get(player2Poke).getName() + "Back.gif.gif");
    		    	        ImageIcon iconPoke5 = new ImageIcon(url5);
    		    	        Image Pokeimage5 = iconPoke5.getImage();
    		    	        Image play5 = Pokeimage5.getScaledInstance(600, 600, Image.SCALE_DEFAULT);
    		    	        
    		    	        label2.setIcon(new ImageIcon(play5)); 
    		    	        label3.setIcon(new ImageIcon(play6)); 
    		    	        
    		    	        name1.setText(player2.Pokemon.get(player2Poke).getName());
    		    	        name2.setText(player1.Pokemon.get(player1Poke).getName());
    		    			}
    		    			else
    		    			{
        		    	        java.net.URL url6 = UI.class.getResource("/resources/" + player2.Pokemon.get(player2Poke).getName() + ".gif.gif");
        		    	        ImageIcon iconPoke6 = new ImageIcon(url6);
        		    	        Image Pokeimage6 = iconPoke6.getImage();
        		    	        Image play6 = Pokeimage6.getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        		    	        
        		    	        java.net.URL url5 = UI.class.getResource("/resources/" + player1.Pokemon.get(player1Poke).getName() + "Back.gif.gif");
        		    	        ImageIcon iconPoke5 = new ImageIcon(url5);
        		    	        Image Pokeimage5 = iconPoke5.getImage();
        		    	        Image play5 = Pokeimage5.getScaledInstance(600, 600, Image.SCALE_DEFAULT);
        		    	        
        		    	        label2.setIcon(new ImageIcon(play5)); 
        		    	        label3.setIcon(new ImageIcon(play6)); 
        		    	        
        		    	        name2.setText(player2.Pokemon.get(player2Poke).getName());
        		    	        name1.setText(player1.Pokemon.get(player1Poke).getName());
    		    			}
    		    			
    		    			label2.move(70, 300);
    		    			label3.move(1000, 100);
    		    			
    		    	        label4.move(-100, -300);
    		    	        label5.move(975, 260);
    		    	        
    		    	        
    		    	        Dimension buff = new Dimension(health1.getSize());
 
        	       				
        	       				health1.setSize(health2.getSize().width,health2.getSize().height);
        	       				health2.setSize(buff);
        	       				
        	       				if(health1.getSize().width < 60)
        	       					health1.setIcon(new ImageIcon(red));
        	       				else if(health1.getSize().width < 150)
        	       					health1.setIcon(new ImageIcon(yellow));
        	       				else
        	       					health1.setIcon(new ImageIcon(green));
        	       				try {

										 //AudioPlayer.player.start(battle);
								  //AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
								    
								    //AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(in1);
								    
								    //Clip clip = AudioSystem.getClip();
								    //clip.open(audioInputStream);
								    //clip.loop(Clip.LOOP_CONTINUOUSLY);
								    //clip.start();
								    
								    
									
										//in1 = new FileInputStream(thisFile);
									
									
        	       					
        	       				if(health2.getSize().width < 60)
        	       				{
        	       					
									

		        	       					AudioPlayer.player.start(loop);
        	       					//clip.start();
		        	       					health2.setIcon(new ImageIcon(red));
										
  
        	       				}
        	       				
        	       				else if(health2.getSize().width < 150)
        	       				{
        	       					
        	       					AudioPlayer.player.stop(loop);
        	       					//clip.stop();
        	       					health2.setIcon(new ImageIcon(yellow));
        	       				}
        	       				else
        	       				{
        	       					AudioPlayer.player.stop(loop);
        	       					//clip.stop();
        	       					health2.setIcon(new ImageIcon(green));
        	       				}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}   
        	       				System.out.println(health1.getSize().width);
        	       				
        	       				System.out.println(health1.getSize().width);
        	       				//health1.move(health1.getX(), health1.getY());
        	       				health1.getParent().repaint();
        	       				health2.getParent().repaint();
        	       				
        	       				
        	       				
        	       				

        	    			

        	       				
    		    			
    		    			turns++;
    		    			
    		    			label2.fastTransitionRight(0,0);
    		    			label3.fastTransitionLeft(0,0);
    		    			label4.fastTransitionRight(1000,-50);
    		    			label5.fastTransitionLeft(1000,0);
    		    			
    		    	        buttons.add(attack);
    		    	        buttons.add(items);
    		    	        buttons.add(poke);
    		    	        buttons.add(exit);
    		    			
    		    			buttons.setVisible(true);
    		    			
    		    			playFrame.repaint();
    		    			this.cancel();
    		    		}
    	       		 ////System.out.println(i);
    	       		  }
    	       		}, 500,10);
    	       		
    	        	 }
    	      });
    		 attack2.addActionListener(new ActionListener(){
    	       	 public void actionPerformed(ActionEvent e){
    	       		 ping();
    	     		
    	     	
    	     		
    	     		buttons.removeAll();
    	     		
    	     		dmg = 0;
    	     		hp = 0;
    	     		
	       			if(turns % 2 == 1)
	    			{
	       				dmg = player1.Pokemon.get(player1Poke).getAttack2Damage();
	       				hp = player2.Pokemon.get(player2Poke).getHP();
	    			}
	       			else
	       			{
	       				dmg = player2.Pokemon.get(player2Poke).getAttack2Damage();
	       				hp = player1.Pokemon.get(player1Poke).getHP();
	       			}
	       			
	       			////System.out.println(dmg + " " + hp);
	       			

    	     		
    	     		buttons.repaint();
    	     		buttons.revalidate();
    	     		
    	       		Timer timer = new Timer();
    	       		timer.schedule(new TimerTask() {
    	       			int i = 0;
    	       			
    	       			
    	       		  @Override
    	       		  
    	       		  public void run() {
    	       			  
    	       			  
    	       			i++;
    	       			
    	       			if(turns % 2 == 1) //player 2s turn
    	       				hp = player2.Pokemon.get(player2Poke).getHP();
    	       			else
    	       				hp = player1.Pokemon.get(player1Poke).getHP();
    	       			
    	       			//if(turns % 2 == 0)
    	       			
    	       			if(i%8==0)
    	       				if(dmg > 0)
    	       				{

    	       			if(turns % 2 == 1)
    	    			{
	       					if(j == 1){
	       						//health1.setSize(new Dimension(230, 200));
    	       					player2.Pokemon.get(player2Poke).setHP(0);
    	       					faint();
    	       					player2Poke++;
    	       					player2.Pokemon.get(player2Poke).setCurrentHP(player2.Pokemon.get(player2Poke).getHP());
    	       					System.out.println("poke up");
    	       					dmg = 0;
    	       					j = 0;
    	       				}
    	       				else
    	       				{
    	       				player2.Pokemon.get(player2Poke).setHP(player2.Pokemon.get(player2Poke).getHP()-1);
    	       				System.out.println(dmg);
    	       				dmg--;
    	       				
    	       				health1.setSize(health1.getSize().width-(6),health1.getSize().height);
    	       				health1.move(health1.getX(), health1.getY());
    	       				
    	       				if(health1.getSize().width < 60)
    	       					health1.setIcon(new ImageIcon(red));
    	       				else if(health1.getSize().width < 150)
    	       					health1.setIcon(new ImageIcon(yellow));
    	       				System.out.println(health1.getSize().width);
    	       				
    	       				
    	       				health1.getParent().repaint();
    	       				
    	       				
    	       				
    	       				
    	       				}
    	       				

    	    			}
    	       			else
    	       			{
	       					if(j == 1){
	       						//health1.setSize(new Dimension(230, 200));
    	       					player1.Pokemon.get(player1Poke).setHP(0);
    	       					faint();
    	       					player1Poke++;
    	       					pokeball();
    	       					player1.Pokemon.get(player1Poke).setCurrentHP(player1.Pokemon.get(player1Poke).getHP());
    	       					
    	       					j = 0;
    	       					
    	       					//System.out.println(hp);
    	       					dmg = 0;
    	       				}
    	       				else
    	       				{
    	       				player1.Pokemon.get(player1Poke).setHP(player1.Pokemon.get(player1Poke).getHP()-1);
    	       				System.out.println(dmg);
    	       				dmg--;
    	       				
    	       				health1.setSize(health1.getSize().width-(6),health1.getSize().height);
    	       				health1.move(health1.getX(), health1.getY());
    	       				
    	       				if(health1.getSize().width < 60)
    	       					health1.setIcon(new ImageIcon(red));
    	       				else if(health1.getSize().width < 150)
    	       					health1.setIcon(new ImageIcon(yellow));
    	       				System.out.println(health1.getSize().width);
    	       				
    	       				health1.getParent().repaint();
    	       				}
    	       				
    	       			}
    	       		}
    	       			
    	       			
    	       			  if(i<10){
    	       		    System.out.println(health1.size()+ " size");
    	       		    label2.move(label2.getX()+10, label2.getY()-10);
    	       		    playFrame.repaint();
    	       		    //i++;
    	       			  }
    	       		    if(i>=10&& i <20)
    	       		    {
    	       		    	if(i == 10)
    	       		    	{
    	       		    	attack();
    	       		    	label3.blink();
    	       		    	}
    	       		    	label2.move(label2.getX()-10, label2.getY()+10);
    	       		    	playFrame.repaint();
    	       		    }
    	       		    else if(i >=300)
    		    		{
    		    			//label3.repositionRight();
    		    			transition();
    		    			
	       					if(health1.size().getWidth() < 5)
	       					{
	       						health1.setSize(new Dimension(230, 200));
	       						if(turns % 2 == 1)
	       							player2Poke++;
	       						else
	       							player1Poke++;
	       						pokeball();
	       						
	       					}
	       					if(health2.size().getWidth() < 5)
	       					{
	       						j = 1;
	       					}
    		    			
    		    			if(turns % 2 == 1)
    		    			{
    		    	        java.net.URL url6 = UI.class.getResource("/resources/" + player1.Pokemon.get(player1Poke).getName() + ".gif.gif");
    		    	        ImageIcon iconPoke6 = new ImageIcon(url6);
    		    	        Image Pokeimage6 = iconPoke6.getImage();
    		    	        Image play6 = Pokeimage6.getScaledInstance(300, 300, Image.SCALE_DEFAULT);
    		    	        
    		    	        java.net.URL url5 = UI.class.getResource("/resources/" + player2.Pokemon.get(player2Poke).getName() + "Back.gif.gif");
    		    	        ImageIcon iconPoke5 = new ImageIcon(url5);
    		    	        Image Pokeimage5 = iconPoke5.getImage();
    		    	        Image play5 = Pokeimage5.getScaledInstance(600, 600, Image.SCALE_DEFAULT);
    		    	        
    		    	        label2.setIcon(new ImageIcon(play5)); 
    		    	        label3.setIcon(new ImageIcon(play6)); 
    		    	        
    		    	        name1.setText(player2.Pokemon.get(player2Poke).getName());
    		    	        name2.setText(player1.Pokemon.get(player1Poke).getName());
    		    			}
    		    			else
    		    			{
        		    	        java.net.URL url6 = UI.class.getResource("/resources/" + player2.Pokemon.get(player2Poke).getName() + ".gif.gif");
        		    	        ImageIcon iconPoke6 = new ImageIcon(url6);
        		    	        Image Pokeimage6 = iconPoke6.getImage();
        		    	        Image play6 = Pokeimage6.getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        		    	        
        		    	        java.net.URL url5 = UI.class.getResource("/resources/" + player1.Pokemon.get(player1Poke).getName() + "Back.gif.gif");
        		    	        ImageIcon iconPoke5 = new ImageIcon(url5);
        		    	        Image Pokeimage5 = iconPoke5.getImage();
        		    	        Image play5 = Pokeimage5.getScaledInstance(600, 600, Image.SCALE_DEFAULT);
        		    	        
        		    	        label2.setIcon(new ImageIcon(play5)); 
        		    	        label3.setIcon(new ImageIcon(play6)); 
        		    	        
        		    	        name2.setText(player2.Pokemon.get(player2Poke).getName());
        		    	        name1.setText(player1.Pokemon.get(player1Poke).getName());
    		    			}
    		    			
    		    			label2.move(70, 300);
    		    			label3.move(1000, 100);
    		    			
    		    	        label4.move(-100, -300);
    		    	        label5.move(975, 260);
    		    	        
    		    	        
    		    	        Dimension buff = new Dimension(health1.getSize());
 
        	       				
        	       				health1.setSize(health2.getSize().width,health2.getSize().height);
        	       				health2.setSize(buff);
        	       				
        	       				if(health1.getSize().width < 60)
        	       					health1.setIcon(new ImageIcon(red));
        	       				else if(health1.getSize().width < 150)
        	       					health1.setIcon(new ImageIcon(yellow));
        	       				else
        	       					health1.setIcon(new ImageIcon(green));
        	       				try {

										 //AudioPlayer.player.start(battle);
								  //AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
								    
								    //AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(in1);
								    
								    //Clip clip = AudioSystem.getClip();
								    //clip.open(audioInputStream);
								    //clip.loop(Clip.LOOP_CONTINUOUSLY);
								    //clip.start();
								    
								    
									
										//in1 = new FileInputStream(thisFile);
									
									
        	       					
        	       				if(health2.getSize().width < 60)
        	       				{
        	       					
									

		        	       					AudioPlayer.player.start(loop);
        	       					//clip.start();
		        	       					health2.setIcon(new ImageIcon(red));
										
  
        	       				}
        	       				
        	       				else if(health2.getSize().width < 150)
        	       				{
        	       					
        	       					AudioPlayer.player.stop(loop);
        	       					//clip.stop();
        	       					health2.setIcon(new ImageIcon(yellow));
        	       				}
        	       				else
        	       				{
        	       					AudioPlayer.player.stop(loop);
        	       					//clip.stop();
        	       					health2.setIcon(new ImageIcon(green));
        	       				}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}   
        	       				System.out.println(health1.getSize().width);
        	       				
        	       				System.out.println(health1.getSize().width);
        	       				//health1.move(health1.getX(), health1.getY());
        	       				health1.getParent().repaint();
        	       				health2.getParent().repaint();
        	       				
        	       				
        	       				
        	       				

        	    			

        	       				
    		    			
    		    			turns++;
    		    			
    		    			label2.fastTransitionRight(0,0);
    		    			label3.fastTransitionLeft(0,0);
    		    			label4.fastTransitionRight(1000,-50);
    		    			label5.fastTransitionLeft(1000,0);
    		    			
    		    	        buttons.add(attack);
    		    	        buttons.add(items);
    		    	        buttons.add(poke);
    		    	        buttons.add(exit);
    		    			
    		    			buttons.setVisible(true);
    		    			
    		    			playFrame.repaint();
    		    			this.cancel();
    		    		}
    	       		 ////System.out.println(i);
    	       		  }
    	       		}, 500,10);
    	       		
    	        	 }
    	      });
    		 
	      
    		 
    		 
    		 buttons.add(attack1);
    		 buttons.add(attack2);
    		 
    		 playFrame.repaint();
    		 playFrame.revalidate();
    		 
        	 }
      });
        
        
        items.addActionListener(new ActionListener(){
       	 public void actionPerformed(ActionEvent e){
   		 
       		 ping();
       		 
   		 
   		 
   		 
       	 }
     });
        poke.addActionListener(new ActionListener(){
       	 public void actionPerformed(ActionEvent e){
    		ping();
       	 }
     });
        exit.addActionListener(new ActionListener(){
        	 public void actionPerformed(ActionEvent e){
        		System.exit(1);
        	 }
      });
		
		playFrame.setVisible(true);
		mainFrame.add(playFrame);
		
        label2.slowTransitionRight(0,0);
        label3.slowTransitionLeft(0,0);
        label4.slowTransitionRight(1000,-50);
        label5.slowTransitionLeft(1000,0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void delay()
	{
		Timer timer = new Timer();
   		timer.schedule(new TimerTask() {
   			int i = 0;
   		  @Override
   		  
   		  public void run() {

   		  }
   		}, 500,10);
	}
	public void cleanPanels()
	{
		top.removeAll();
		middle.removeAll();
		bottom.removeAll();
		left.removeAll();
		right.removeAll();
	}
	public void ping()
	{
		String thisFile1 = "C:\\Users\\Dogan\\git\\Pokueries\\Pokueries\\src\\resources\\ping.wav";
		    InputStream in1 = null;
			try {
				in1 = new FileInputStream(thisFile1);
			} catch (FileNotFoundException eg) {
				// TODO Auto-generated catch block
				eg.printStackTrace();
			}
		    AudioStream ping = null;
			try {
				ping = new AudioStream(in1);
			} catch (IOException ei) {
				// TODO Auto-generated catch block
				ei.printStackTrace();
			}
		 AudioPlayer.player.start(ping);
	}
	public void pokeball()
	{
		String thisFile1 = "C:\\Users\\Dogan\\git\\Pokueries\\Pokueries\\src\\resources\\pokeball.wav";
		    InputStream in1 = null;
			try {
				in1 = new FileInputStream(thisFile1);
			} catch (FileNotFoundException eg) {
				// TODO Auto-generated catch block
				eg.printStackTrace();
			}
		    AudioStream ping = null;
			try {
				ping = new AudioStream(in1);
			} catch (IOException ei) {
				// TODO Auto-generated catch block
				ei.printStackTrace();
			}
		 AudioPlayer.player.start(ping);
	}
	public void faint()
	{
		String thisFile1 = "C:\\Users\\Dogan\\git\\Pokueries\\Pokueries\\src\\resources\\faint.wav";
		    InputStream in1 = null;
			try {
				in1 = new FileInputStream(thisFile1);
			} catch (FileNotFoundException eg) {
				// TODO Auto-generated catch block
				eg.printStackTrace();
			}
		    AudioStream ping = null;
			try {
				ping = new AudioStream(in1);
			} catch (IOException ei) {
				// TODO Auto-generated catch block
				ei.printStackTrace();
			}
		 AudioPlayer.player.start(ping);
	}
	public void attack()
	{
		String thisFile1 = "C:\\Users\\Dogan\\git\\Pokueries\\Pokueries\\src\\resources\\attack.wav";
		    InputStream in1 = null;
			try {
				in1 = new FileInputStream(thisFile1);
			} catch (FileNotFoundException eg) {
				// TODO Auto-generated catch block
				eg.printStackTrace();
			}
		    AudioStream ping = null;
			try {
				ping = new AudioStream(in1);
			} catch (IOException ei) {
				// TODO Auto-generated catch block
				ei.printStackTrace();
			}
			////System.out.println("attack");
		 AudioPlayer.player.start(ping);
	}
	public void transition()
	{
		String thisFile1 = "C:\\Users\\Dogan\\git\\Pokueries\\Pokueries\\src\\resources\\transition.wav";
		    InputStream in1 = null;
			try {
				in1 = new FileInputStream(thisFile1);
			} catch (FileNotFoundException eg) {
				// TODO Auto-generated catch block
				eg.printStackTrace();
			}
		    AudioStream ping = null;
			try {
				ping = new AudioStream(in1);
			} catch (IOException ei) {
				// TODO Auto-generated catch block
				ei.printStackTrace();
			}
			////System.out.println("attack");
		 AudioPlayer.player.start(ping);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void showMenu()
	{
		menuFrame.setSize(600, 700);
		
		GridBagConstraints gbcMenu = new GridBagConstraints();
		
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
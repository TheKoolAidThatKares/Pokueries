import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class UI implements ActionListener {
	
	static JFrame mainFrame = new JFrame();
	static JInternalFrame menuFrame = new JInternalFrame("Menu",true,true,true,true);
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
		
		java.net.URL url = UI.class.getResource("/resources/Bulbasaur.gif");
        ImageIcon iconPoke = new ImageIcon(url);
        Image Pokeimage = iconPoke.getImage();
        Image newimg = Pokeimage.getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        
        JLabel label = new JLabel(new ImageIcon(newimg));
		
		top.add(label, gbcPokedex);
		bottom.add(new JLabel("Test2"), gbcPokedex);
		left.add(new JLabel("Test3"), gbcPokedex);
		
		top.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		bottom.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		left.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		
		pokedexFrame.add(top, BorderLayout.NORTH);
		pokedexFrame.add(bottom, BorderLayout.SOUTH);
		pokedexFrame.add(left, BorderLayout.WEST);
		
		pokedexFrame.setVisible(true);
		
		mainFrame.add(pokedexFrame);
	}
	
	public void cleanPanels()
	{
		top.removeAll();
		middle.removeAll();
		bottom.removeAll();
		left.removeAll();
		right.removeAll();
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
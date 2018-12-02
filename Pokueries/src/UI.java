import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class UI implements ActionListener {
	
	static JFrame frame = new JFrame();
	
	JButton play = new JButton();
	JButton pokedex = new JButton();
	JButton exit = new JButton();
	
	JPanel top = new JPanel();
	JPanel middle = new JPanel();
	JPanel bottom = new JPanel();
	
	Font font = new Font("sans_serif", Font.PLAIN, 40);
	
	GridBagConstraints gbc = new GridBagConstraints();

	public void draw()
	{
		frame.setSize(500, 700);
		frame.setTitle("Pokueries");
		
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.ipady = 40;
		gbc.fill = GridBagConstraints.BOTH;
		
		play.setText("Play");
		pokedex.setText("Pokedex");
		exit.setText("Exit");
		
		play.setFont(font);
		pokedex.setFont(font);
		exit.setFont(font);
		
		play.setAlignmentX(Component.CENTER_ALIGNMENT);
		pokedex.setAlignmentX(Component.CENTER_ALIGNMENT);
		exit.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		top.setLayout(new GridBagLayout());
		middle.setLayout(new GridBagLayout());
		bottom.setLayout(new GridBagLayout());
		
		middle.add(play, gbc);
		gbc.gridy = 1;
		middle.add(pokedex, gbc);
		gbc.gridy = 2;
		middle.add(exit, gbc);
		
		
		frame.add(top, BorderLayout.NORTH);
		frame.add(middle, BorderLayout.CENTER);
		frame.add(bottom, BorderLayout.SOUTH);
        
		
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	public UI()
	{
		draw();
	}
}
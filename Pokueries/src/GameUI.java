import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class GameUI extends JPanel	implements ActionListener {

    private final int B_WIDTH = 550;
    private final int B_HEIGHT = 550;
    private final int INITIAL_X = 300;
    private final int INITIAL_Y = 300;
    private final int DELAY = 750; // milliseconds?
    
    ImageIcon ii = new ImageIcon();


    
    private Image star;
    private Timer timer;
    private int x, y;

    public GameUI() {
    	
    	try {
    	     ii = new ImageIcon(new URL("https://img.pokemondb.net/sprites/black-white/anim/normal/bulbasaur.gif"));
    	}
    	catch(Exception e)
    	{
    		
    	}

        initBoard();
    }

    private void loadImage() {

        star = ii.getImage();
    }

    private void initBoard() {

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        loadImage();
        
        x = INITIAL_X;
        y = INITIAL_Y;
        
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawStar(g);
    }

    private void drawStar(Graphics g) {

        g.drawImage(star, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
/*
        x += 1;
        y += 1;

        if (y > B_HEIGHT) {

            y = INITIAL_Y;
            x = INITIAL_X;
        }

        repaint();*/
    }
}
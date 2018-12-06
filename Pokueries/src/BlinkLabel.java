import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Icon;
import javax.swing.JLabel;

public class BlinkLabel extends JLabel {

	public BlinkLabel() {
		// TODO Auto-generated constructor stub
	}

	public BlinkLabel(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public BlinkLabel(Icon image) {
		super(image);
		// TODO Auto-generated constructor stub
	}

	public BlinkLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		// TODO Auto-generated constructor stub
	}

	public BlinkLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
		// TODO Auto-generated constructor stub
	}

	public BlinkLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
		// TODO Auto-generated constructor stub
	}
	
 	public void blink()
	{
		Timer timer = new Timer();
   		timer.schedule(new TimerTask() {
   			int i = 0;
   		  @Override
   		  public void run() {
   			  if (i % 2 == 0)
   				  off();
   			  else
   				  on();
   			  if(i>10)
   				  this.cancel();
   			  i++;
   		  }
   		}, 5,200);
	}
 	public void fastTransitionLeft(int delay, int set)
	{
 		repositionRight(0);
		Timer timer = new Timer();
   		timer.schedule(new TimerTask() {
   			int i = 1;
   		  @Override
   		  public void run() {   
   			  slideLeft();
   			  if(i>30)
   				  this.cancel();
   			  i++;
   		  }
   		}, 5+delay,20);
	}
 	public void fastTransitionRight(int delay, int set)
	{
 		repositionLeft(set);
		Timer timer = new Timer();
   		timer.schedule(new TimerTask() {
   			int i = 1;
   		  @Override
   		  public void run() {   
   			  slideRight();
   			  if(i>30)
   				  this.cancel();
   			  i++;
   		  }
   		}, 5+delay,20);
	}
 	public void slowTransitionLeft(int delay, int set)
	{
 		repositionRight(set);
		Timer timer = new Timer();
   		timer.schedule(new TimerTask() {
   			int i = 1;
   		  @Override
   		  public void run() {   
   			  slideLeft();
   			  if(i>30)
   				  this.cancel();
   			  i++;
   		  }
   		}, 500 + delay,50);
	}
 	public void slowTransitionRight(int delay, int set)
	{
 		repositionLeft(set);
		Timer timer = new Timer();
   		timer.schedule(new TimerTask() {
   			int i = 1;
   		  @Override
   		  public void run() {   
   			  slideRight();
   			  if(i>30)
   				  this.cancel();
   			  i++;
   		  }
   		}, 500 + delay,50);
	}
	public void off()
	{
		this.setVisible(false);
		this.getParent().repaint();
	}
	public void on()
	{
		this.setVisible(true);
		
		
		this.getParent().repaint();
	}
	public void repositionRight(int set)
	{
		this.move(this.getX()+900 + set, this.getY());
		this.getParent().repaint();
	}
	public void slideLeft()
	{
		this.move(this.getX()-30, this.getY());
		System.out.println("hello");
		this.getParent().repaint();
	}
	public void repositionLeft(int set)
	{
		this.move(this.getX()-900 + set, this.getY());
		this.getParent().repaint();
	}
	public void slideRight()
	{
		this.move(this.getX()+30, this.getY());
		System.out.println("hello");
		this.getParent().repaint();
	}


}

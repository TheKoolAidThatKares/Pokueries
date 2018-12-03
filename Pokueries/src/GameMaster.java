import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class GameMaster 
{
	
	static ArrayList<Pokemon> allPokemon = new ArrayList<>();
	
	public static void main(String[] args)
	{
		makeArt();
		formUI();
		getAllPokemon();
	}
	
	public static void formUI()
	{
		UI UserInterface = new UI();
	}
	public static void makeArt()
	{
		try 
        { 	  
        	UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } 
        catch (Exception e)
		{ 
            System.out.println("Look and Feel not set"); 
        }
		UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels(); 
	    for (UIManager.LookAndFeelInfo look : looks) 
	    { 
	            System.out.println(look.getClassName()); 
	    } 
	}
	public static void getAllPokemon()
	{
		//This method will use the PokemonGetter Class to get all pokemon
		//for the pokedex
		
		PokemonGetter newOne = new PokemonGetter();
		try {
			newOne.connectToDB();
			newOne.displayPokemon();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error: unable to connect to Database");
			e.printStackTrace();
		}	
	}
}

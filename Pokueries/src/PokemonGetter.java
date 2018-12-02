import java.util.ArrayList;

public class PokemonGetter 
{
	//This class exists for the sole purpose of getting all pokemon
	//from the database and returning them in an Array List
	static ArrayList<Pokemon> pokemon = new ArrayList<>();
	
	public static ArrayList<Pokemon> getPokemon()
	{
		//This is where the magic should happen. Im going to put stuff here for testing reasons
		//but it can all be changed
		Pokemon bulbasaur = new Pokemon();
		bulbasaur.setName("Bulbasaur");  
		bulbasaur.setHP(50);
		pokemon.add(bulbasaur);
		
		Pokemon charmander = new Pokemon();
		bulbasaur.setName("charmander");  
		bulbasaur.setHP(60);
		pokemon.add(charmander);
		
		return pokemon;
	}
}

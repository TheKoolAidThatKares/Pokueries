
import java.util.ArrayList;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import java.util.Date;

public class PokemonGetter {
	// This class exists for the sole purpose of getting all pokemon
	// from the database and returning them in an Array List
	public Connection connect = null;
	//public Connection connect2 = null;
	public Statement statement = null;
	public Statement statement2 = null;
	//public PreparedStatement preparedStatement = null;
	public ResultSet resultSet = null;
	public ResultSet resultSet2 = null;
	
	// Per mySQL
	// host = 127.0.0.1
	// Port: 3306
	// User: root
	// SSL: enabled with DHE-RSA-AES128-GCM-SHA256
	// connect = DriverManager.getConnection("jdbc:mysql://" + host + "/"
	// + database + "?" + sslSwitch + "user=" + user + "&password=" + passwd
	// host = localhost:3306
	final private String host = "localhost:3306";
	final private String user = "root";
	final private String passwd = "PasswordF181234";
	final private String database = "sys";
	final private String sslSwitch = "useSSL=false&";
	
	// ArrayList used to store all instantiated Pokemon cards
	static ArrayList<Pokemon> pokemon = new ArrayList<>();

	public void connectToDB() throws Exception {
		try {
			// Load the MySQL driver
			Class.forName("com.mysql.jdbc.Driver");
			// Establish DB connection
			connect = DriverManager.getConnection(
					"jdbc:mysql://" + host + "/" + database + "?" + sslSwitch + "user=" + user + "&password=" + passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// For testing purposes. Displays the number, name and HP of all 151 Pokemon
	public ArrayList<Pokemon> getPokemon() throws Exception {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from " + database + ".cards");
			while (resultSet.next()) {
				int pokemon_id = resultSet.getInt("pokemon_id");
				String name = resultSet.getString("name");
				int hp = resultSet.getInt("hp");
				int pokemon_power = resultSet.getInt("pokemon_power");
				int attack1 = resultSet.getInt("attack1");
				int attack2 = resultSet.getInt("attack2");
				int strength = resultSet.getInt("strength");
				int weakness = resultSet.getInt("weakness");
				int resistance = resultSet.getInt("resistance");
				int retreat_cost = resultSet.getInt("retreat_cost");
				
				// Need to run additional queries here to determine the missing data for each Pokemon
				// 1. Query the attacks table using the integer stored in the attack1 variable and
				// assign those values to temporary variables				
				statement2 = connect.createStatement();
				resultSet2 = statement2.executeQuery("select * from " + database + ".attacks where attack_id = " + attack1);
				
				//  Variables used to store attack1 data
				String attack1_name = null;
				int attack1_damage = 0;
				int attack1_energy1 = 0;
				int attack1_energy1_required = 0;
				int attack1_energy2 = 0;
				int attack1_energy2_required = 0;
				int effect1ID = 0;
				
				// Retrieve attack1 data from resultSet
				if (resultSet2.next()) {
					attack1_name = resultSet2.getString("name");
					attack1_damage = resultSet2.getInt("damage");
					attack1_energy1 = resultSet2.getInt("energy_1");
					attack1_energy1_required = resultSet2.getInt("energy_1_required");
					attack1_energy2 = resultSet2.getInt("energy_2");
					attack1_energy2_required = resultSet2.getInt("energy_2_required");
					effect1ID = resultSet2.getInt("effects");
				}
				
				// Need to query the effects table
				resultSet2 = statement2.executeQuery("select * from " + database + ".effects where effect_id = " + effect1ID);
			
				// Variables used to store effect1 data
				String effect1_type = null;
				int effect1CoinFlips = 0;
				
				// Store effect1 data into variables
				if (resultSet2.next())
				{
				effect1_type = resultSet2.getString("effect_type");
				effect1CoinFlips = resultSet2.getInt("coin_flips");
				}
							
				// 2. Query the attacks table using the integer stored in the attack2 variable and
				// assign those values to temporary variables
				resultSet2 = statement2.executeQuery("select * from " + database + ".attacks where attack_id = " + attack2);
				
				// Variables used to store attack2 data
				String attack2_name = null;
				int attack2_damage = 0;
				int attack2_energy1 = 0;
				int attack2_energy1_required = 0;
				int attack2_energy2 = 0;
				int attack2_energy2_required = 0;
				int effect2ID = 0;
				
				// Retrieve attack2 data from ResultSet
				if (resultSet2.next()) {
					attack2_name = resultSet2.getString("name");
					attack2_damage = resultSet2.getInt("damage");
					attack2_energy1 = resultSet2.getInt("energy_1");
					attack2_energy1_required = resultSet2.getInt("energy_1_required");
					attack2_energy2 = resultSet2.getInt("energy_2");
					attack2_energy2_required = resultSet2.getInt("energy_2_required");
					effect2ID = resultSet2.getInt("effects");
				}
				
				// Need to query the effects table
				resultSet2 = statement2.executeQuery("select * from " + database + ".effects where effect_id = " + effect2ID);	
				
				// Variables used to store effect2 data
				String effect2_type = null;
				int effect2CoinFlips = 0;
				
				if (resultSet2.next())
				{
					effect2_type = resultSet2.getString("effect_type");
					effect2CoinFlips = resultSet2.getInt("coin_flips");
				}
				
				// 3. Now that all information has been retrieved, can pass values to constructor in Pokemon class
				// Create temporary Pokemon object, buffer, which will be added to the arrayList
				Pokemon buffer = new Pokemon(pokemon_id, name, hp, hp, pokemon_power, attack1, attack1_name, attack1_damage, 
						attack1_energy1, attack1_energy1_required, attack1_energy2, attack1_energy2_required, effect1ID, effect1_type, 
						effect1CoinFlips, attack2, attack2_name, attack2_damage, attack2_energy1, attack2_energy1_required,
						attack2_energy2, attack2_energy2_required, effect2ID, effect2_type, effect2CoinFlips, strength, weakness, 
						resistance, retreat_cost);
				
				// Add the newly created Pokemon object to the pokemon ArrayList
				pokemon.add(buffer);
				
				System.out.print(pokemon_id + " // " + name + " // " + hp + " // " + pokemon_power + " // ");
				System.out.print(attack1 + " // " + attack2 + " // " + strength + " // " + weakness + " // ");
				System.out.print(resistance + " // " + retreat_cost);
				System.out.println();
				
				//testPokeList();
			}
			System.out.println(pokemon.size());
			
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return pokemon;
	}
	
	/*
	public void testPokeList()
	{
		int counter = 1;
		System.out.println("Testing list");
		for (Pokemon pk : pokemon)
		{
			if (pk.getID() == 1)
			{
				System.out.println("First one found");
				System.out.println("Counter = " + counter);
				counter++;
			}
			else
			{
				System.out.println("Keep searching");
				System.out.println("Counter = " + counter);
				counter++;
			}
		}
	}
	*/

	
/*
	public static ArrayList<Pokemon> getPokemon() {
		// This is where the magic should happen. Im going to put stuff here for
		// testing reasons
		// but it can all be changed
		Pokemon bulbasaur = new Pokemon();
		bulbasaur.setName("Bulbasaur");
		bulbasaur.setHP(50);
		// pokemon.add(bulbasaur);
		Pokemon charmander = new Pokemon();
		bulbasaur.setName("charmander");
		bulbasaur.setHP(60);
		// pokemon.add(charmander);
		return pokemon;
	}
	*/

	// Close connections
	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (resultSet2 != null) {
				resultSet2.close();
			}
			if (statement2 != null) {
				statement2.close();
			}
			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {
			
		}
	}
}

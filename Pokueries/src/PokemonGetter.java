import java.util.ArrayList;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import java.util.Date;

public class PokemonGetter

{

	// This class exists for the sole purpose of getting all pokemon

	// from the database and returning them in an Array List

	private Connection connect = null;

	private Statement statement = null;

	private PreparedStatement preparedStatement = null;

	private ResultSet resultSet = null;

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

	final private String database = "Pokeuries";

	final private String sslSwitch = "useSSL=false&";

	public void connectToDB() throws Exception {

		try {

			// Load the MySQL driver

			Class.forName("com.mysql.jdbc.Driver");

			// Establish DB connection

			connect = DriverManager.getConnection("jdbc:mysql://" + host + "/"

					+ database + "?" + sslSwitch + "user=" + user + "&password=" + passwd);

		} catch (Exception e) {

			throw e;

		}

	}

	// For testing purposes. Displays the number, name and HP of all 151 Pokemon

	public void displayPokemon() throws Exception {

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

				System.out.print(pokemon_id + " // " + name + " // " + hp + " // " + pokemon_power + " // ");

				System.out.print(attack1 + " // " + attack2 + " // " + strength + " // " + weakness + " // ");

				System.out.print(resistance + " // " + retreat_cost);

				System.out.println();

			}

		} catch (Exception e) {

		}

	}

	static ArrayList<Pokemon> pokemon = new ArrayList<>();

	public static ArrayList<Pokemon> getPokemon()

	{

		// This is where the magic should happen. Im going to put stuff here for
		// testing reasons

		// but it can all be changed

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

	// Close connections

	public void close() {

		try {

			if (resultSet != null) {

				resultSet.close();

			}

			if (statement != null) {

				statement.close();

			}

			if (connect != null) {

				connect.close();

			}

		} catch (Exception e) {

		}

	}

}


public class Pokemon {
	// Refer to comments above accessor and mutator methods for variable
	// descriptions.
	// Attributes from pokemon table
	private int pokemon_id;
	private String name;
	private int hp;
	private int currentHP;
	private int pokemon_power;
	// end pokemon table
	// Attributes from attacks table
	private int attack1_id;
	private String attack1_name;
	private int attack1_damage;
	// Energy needed to use the attack
	private int attack1_energy1;
	private int attack1_energy1_required;
	private int attack1_energy2;
	private int attack1_energy2_required;
	// end attacks table
	// Attributes from effects table
	private int effect1ID;
	private String effect1_type;
	private int effect1CoinFlips;
	// end effects table
	// Attributes from attacks table
	private int attack2_id;
	private String attack2_name;
	private int attack2_damage;
	// Energy needed to use the attack
	private int attack2_energy1;
	private int attack2_energy1_required;
	private int attack2_energy2;
	private int attack2_energy2_required;
	// end attacks table
	// Attributes from effects table
	private int effect2ID;
	private String effect2_type;
	private int effect2CoinFlips;
	// end effects table
	// Attributes from pokemon table
	private int strength;
	private int weakness;
	private int resistance;
	private int retreat_cost;
	
	public Pokemon()
	{	
	}
	
	// Big nasty constructor
	public Pokemon(int pId, String pName, int pHP, int pHPC, int pPwr, int pAtt1Id, String pAtt1Name, int pAtt1Dmg,
			int pAtt1Enrg1, int pAtt1Enrg1Rq, int pAtt1Enrg2, int pAtt1Enrg2Rq, int pEff1Id, String pEff1Typ,
			int pEff1Flips, int pAtt2Id, String pAtt2Name, int pAtt2Dmg, int pAtt2Enrg1, int pAtt2Enrg1Rq,
			int pAtt2Enrg2, int pAtt2Enrg2Rq, int pEff2Id, String pEff2Typ, int pEff2Flips, int pStrng, int pWeak,
			int pResist, int pRetreat) {
		// Stats
		pokemon_id = pId;
		name = pName;
		hp = pHP;
		currentHP = pHP;
		// Unique to some
		pokemon_power = pPwr;
		// Attack 1
		attack1_id = pAtt1Id;
		attack1_name = pAtt1Name;
		attack1_damage = pAtt1Dmg;
		attack1_energy1 = pAtt1Enrg1;
		attack1_energy1_required = pAtt1Enrg1Rq;
		attack1_energy2 = pAtt1Enrg2;
		attack1_energy2_required = pAtt1Enrg2Rq;
		effect1ID = pEff1Id;
		effect1_type = pEff1Typ;
		effect1CoinFlips = pEff1Flips;
		// Attack 2
		attack2_id = pAtt2Id;
		attack2_name = pAtt2Name;
		attack2_damage = pAtt2Dmg;
		attack2_energy1 = pAtt2Enrg1;
		attack2_energy1_required = pAtt2Enrg1Rq;
		attack2_energy2 = pAtt2Enrg2;
		attack2_energy2_required = pAtt2Enrg2Rq;
		effect2ID = pEff2Id;
		effect2_type = pEff2Typ;
		effect2CoinFlips = pEff2Flips;
		// Stats
		strength = pStrng;
		weakness = pWeak;
		resistance = pResist;
		retreat_cost = pRetreat;
	}

	// end pokemon table
	public void setID(int idIn) {
		pokemon_id = idIn;
	}

	public int getID() {
		return pokemon_id;
	}

	public void setName(String nameIn) {
		name = nameIn;
	}

	public String getName() {
		return name;
	}

	// Initial, unmodified HP value for each value will be stored in the hp
	// variable.
	public void setHP(int HPIn) {
		hp = HPIn;
	}

	public int getHP() {
		return hp;
	}

	// Any damage incurred will be used to update the currentHP variable.
	public void setCurrentHP(int HPIn) {
		currentHP = HPIn;
	}

	public int getCurrentHP() {
		return currentHP;
	}
	// Pokemon Power more than likely will not be implemented in our program.

	// Most Pokemon will have a default value of 0 associated with this
	// attribute.
	public void setPokemonPower(int PKIn) {
		pokemon_power = PKIn;
	}

	public int getPokemonPower() {
		return pokemon_power;
	}
	// Each Pokemon has a maximum of two possible attacks they can use in
	// battle.
	// If a Pokemon has only one attack, then the second attack will be
	// populated with null

	// or 0 values accordingly.
	public void setAttack1(int attack1In) {
		attack1_id = attack1In;
	}

	public int getAttack1() {
		return attack1_id;
	}

	public void setAttack1Name(String nameIn) {
		attack1_name = nameIn;
	}

	public String getAttack1Name() {
		return attack1_name;
	}

	public void setAttack1Damage(int damage1In) {
		attack1_damage = damage1In;
	}

	public int getAttack1Damage() {
		return attack1_damage;
	}

	public void setAttack1Energy1(int attack1Energy1In) {
		attack1_energy1 = attack1Energy1In;
	}

	public int getAttack1Energy1() {
		return attack1_energy1;
	}

	public void setAttack1Energy1Required(int attack1Energy1RequiredIn) {
		attack1_energy1_required = attack1Energy1RequiredIn;
	}

	public int getAttack1Energy1Required() {
		return attack1_energy1_required;
	}

	public void setAttack1Energy2(int attack1Energy2In) {
		attack1_energy2 = attack1Energy2In;
	}

	public int getAttack1Energy2() {
		return attack1_energy2;
	}

	public void setAttack1Energy2Required(int attack1Energy2RequiredIn) {
		attack1_energy2_required = attack1Energy2RequiredIn;
	}

	public int getAttack1Energy2Required() {
		return attack1_energy2_required;
	}
	// public void setAttack1Energy
	// Assuming that an attack causes a status effect, its values will be
	// obtained from the effects
	// table in the database. Most attacks do not cause status ailments, and
	// will therefore be set

	// to a default value of 0.
	public void setEffect1ID(int effects1In) {
		effect1ID = effects1In;
	}

	public int getEffects1() {
		return effect1ID;
	}

	public void setEffect1Type(String effect1TypeIn) {
		effect1_type = effect1TypeIn;
	}

	public String getEffect1Type(String effect1Type) {
		return effect1_type;
	}

	public void setEffect1CoinFLips(int effect1FlipsIn) {
		effect1CoinFlips = effect1FlipsIn;
	}

	public int getEffect1CoinFLips() {
		return effect1CoinFlips;
	}
	// Each Pokemon can use varying types of energy, but no Pokemon has more
	// than two different
	// types of energy associated with it. So, for each attack, assume that
	// there will be two
	// different energy types needed to perform it. If there is only 1, then the
	// values for
	// attack 2 will be set to null or 0's accordingly.

	// Start setter/getter methods for attack2
	public void setAttack2(int attack2In) {
		attack1_id = attack2In;
	}

	public int getAttack2() {
		return attack2_id;
	}

	public void setAttack2Name(String nameIn) {
		attack2_name = nameIn;
	}

	public String getAttack2Name() {
		return attack2_name;
	}

	public void setAttack2Damage(int damage2In) {
		attack1_damage = damage2In;
	}

	public int getAttack2Damage() {
		return attack2_damage;
	}

	public void setAttack2Energy1(int attack2Energy1In) {
		attack2_energy1 = attack2Energy1In;
	}

	public int getAttack2Energy1() {
		return attack2_energy1;
	}

	public void setAttack2Energy1Required(int attack2Energy1RequiredIn) {
		attack2_energy1_required = attack2Energy1RequiredIn;
	}

	public int getAttack2Energy1Required() {
		return attack2_energy1_required;
	}

	public void setAttack2Energy2(int attack2Energy2In) {
		attack2_energy2 = attack2Energy2In;
	}

	public int getAttack2Energy2() {
		return attack2_energy2;
	}

	public void setAttack2Energy2Required(int attack2Energy2RequiredIn) {
		attack2_energy2_required = attack2Energy2RequiredIn;
	}

	public int getAttack2Energy2Required() {
		return attack2_energy2_required;
	}

	// end setter/getter for attack 2
	public void setEffect2ID(int effects2In) {
		effect2ID = effects2In;
	}

	public int getEffects2() {
		return effect2ID;
	}

	public void setEffect2Type(String effect2TypeIn) {
		effect2_type = effect2TypeIn;
	}

	public String getEffect2Type(String effect2Type) {
		return effect2_type;
	}

	public void setEffect2CoinFLips(int effect2FlipsIn) {
		effect2CoinFlips = effect2FlipsIn;
	}

	public int getEffect2CoinFLips() {
		return effect2CoinFlips;
	}

	// Remaining attributes that are retrieved from the pokemon table.
	public void setStrength(int strengthIn) {
		strength = strengthIn;
	}

	public int getStrength() {
		return strength;
	}

	public void setWeakness(int weaknessIn) {
		weakness = weaknessIn;
	}

	public int getWeakness() {
		return weakness;
	}

	public void setResistance(int resistanceIn) {
		resistance = resistanceIn;
	}

	public int getResistance() {
		return resistance;
	}

	public void setRetreatCost(int retreatCostIn) {
		retreat_cost = retreatCostIn;
	}

	public int getRetreatCost() {
		return retreat_cost;
	}

	public void summon() {
		System.out.println(name + "is summoned");
	}

	public void recall() {
		System.out.println(name + "is recalled");
	}

	public void faint() {
		System.out.println(name + "has fainted");
	}

	public void takedamage(int damage) {
		currentHP = hp - damage;
		if (currentHP <= 0) {
			faint();
		} else if (currentHP <= currentHP * .2) {
			// go critical
		}
	}
	// abstract public void ability1();
	// abstract public void ability2();
	// abstract public void ability3();
}

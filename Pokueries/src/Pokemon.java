
public class Pokemon 
{

	String name;
	public String[] type;
	
	String ability1Name;
	String ability2Name;
	String ability3Name;
	
	public int maxHp;
	public int hp;
	
	public void setName(String nameIn)
	{
		name = nameIn;
	}
	
	public String getName()
	{
		return name;
	}
	public void setHP(int HPIn)
	{
		hp = HPIn;
	}
	
	public int getHP()
	{
		return hp;
	}
	
	public void summon()
	{
		System.out.println(name + "is summoned");
	}
	
	public void recall()
	{
		System.out.println(name + "is recalled");
	}
	
	public void faint()
	{
		System.out.println(name + "has fainted");
	}
	
	public void takedamage(int damage)
	{
		hp = hp - damage;
		if(hp <= 0)
		{
			faint();
		}
		else if (hp <= maxHp * .2)
		{
			//go critical
		}
	}
	
	//abstract public void ability1();
	
	//abstract public void ability2();
	
	//abstract public void ability3();
	
	
	
	
}
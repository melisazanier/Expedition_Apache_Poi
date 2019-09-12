import java.util.Random;

@SuppressWarnings("unused")
public class Excursionist {
	
    private static  float backpackWeight;
    private static int age;
	private static int experienceLevel;


	public Excursionist()
	{
		Random rand = new Random();
		
		backpackWeight = rand.nextFloat() * (15 - 0) + 0;
		System.out.println("Backpack weight= "+backpackWeight);
		
		age = rand.nextInt(50) + 18;
		System.out.println("Age= "+ age);
		
		experienceLevel = rand.nextInt(10) + 1;
		System.out.println("Experience level= "+ experienceLevel);
	}
	

	public static int valid(int linie, int coloana, int valBefore,int index1, int index2)
	{
		if((linie >= 0) && (linie < Map.count1) && (coloana >= 0) && (coloana < Map.count2))
		{
			
			if(((backpackWeight >= 0) && (backpackWeight <=5)) && ((age >= 18) && (age <= 25)) && ((experienceLevel >= 8)&&(experienceLevel <= 10)))
				return 1;
			
			if((((backpackWeight >= 0) && (backpackWeight <=10)) || ((age >= 18) && (age <= 40)) || ((experienceLevel >= 4)&&(experienceLevel <= 10))) && (valBefore >= Map.mapMatrix[linie][coloana])&&(index1!=linie)&&(index2!=coloana) )
			   return 1;
			
			if(valBefore > Map.mapMatrix[linie][coloana])
				return 1;	
		}
		return 0;
	}


	public static String failure()
	{  
		String s="";
		
		if(age>=40)
			  s="I am too old for this !! :(";
		else
		   if(backpackWeight>10)
			  s="My backpack is too heavy !! :(";
		else
		   if(experienceLevel<4)
			  s="I am not that experienced !! :(";
		else
			  s="I can't do this !!!";
		
		return s;
			
	}

	
	public static void main (String [] argv)throws Exception
	{
		Map map = new Map();
		
		map.readMap();
		map.printInitialMatrix();
		
	    Excursionist excursionist= new Excursionist();
	    
	    map.getTheStartPoint(Map.count1, Map.count2);
	    if(Map.start1==Map.finish1 && Map.start2==Map.finish2)
	    	map.getTheStartPoint(Map.count1, Map.count2);

	    map.coverTheMatrix(1);
	    
	    if(Map.succes==0)
	    	System.out.println(failure());
	    
	     Map.writeMap();

	}
}


package game.pkmn;

import java.io.*;
import java.util.Random;


public class Pokemon {
    public static int x = 0;
    public static int y = 0;
    public static int enemyHealth = 100;
    public static int pikachuHealth = 100;
    public static boolean game = true;
	public static String one = "1";
	public static String two = "2";
	public static String three = "3";
	public static String four = "4";
	public static Boolean onekey;
	public static Boolean twokey;
	public static Boolean threekey;
    public static Boolean fourkey;
    public static double pikachuDefense;
    public static double enemyDefense;

    public static String eh1 = "*"; public static String eh2 = "*"; public static String eh3 = "*"; public static String eh4 = "*"; public static String eh5 = "*"; public static String eh6 = "*"; public static String eh7 = "*"; public static String eh8 = "*";
    public static String ph1 = "*"; public static String ph2 = "*"; public static String ph3 = "*"; public static String ph4 = "*"; public static String ph5 = "*"; public static String ph6 = "*"; public static String ph7 = "*"; public static String ph8 = "*";

    public static void main(String[] args) {
        pokeBall(0);
		pause(3000);
		while (game==true)
        {

			drawLandscape();
			input();
			checkForGrass();
		}
	}

    public static void pokemonBattle(int pokemonNumber)
    {
		battleAnimation();
		String wildPokemonName = "";
		if (pokemonNumber==7)
		{
			wildPokemonName = "Rattata";
		}
		if (pokemonNumber==8)
		{
			wildPokemonName = "Weedle";

		}
		if (pokemonNumber==9)
		{
			wildPokemonName = "Mankey";
		}
		String[][] landscape = new String[][] {
		{"|"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
		{"|"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
		{"-","-","-","-","-","-","-","-","-","-"," "," "," "," "," "," "," "," "," "," "},
		{" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
		{" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
		{" "," "," "," "," "," "," "," "," "," ","|"," "," "," "," "," "," "," "," "," "},
		{" "," "," "," "," "," "," "," "," "," ","|"," "," "," "," "," "," "," "," "," "},
		{" "," "," "," "," "," "," "," "," "," ","-","-","-","-","-","-","-","-","-","-"}};

		String spacer = "";
		print(20,8,landscape);
		for (int spacing = (int)((20 - wildPokemonName.length() - 5)/2); spacing >= 0; spacing--)
		{
			spacer = spacer + " ";
		}

		System.out.println(spacer + "Wild " + wildPokemonName + spacer);
		System.out.println("      Appeared      ");
		pause(4000);
		enemyDefense = 1;
                pikachuDefense = 1;
		boolean pokemonDead = false;
		boolean battleOver = false;
		double deltadefense = 0;
		boolean sleep = false;
		while (battleOver == false)
		{


		String[][] header = new String[][] {
		{"|"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
		{"|",eh1,eh2,eh3,eh4,eh5,eh6,eh7,eh8," "," "," "," "," "," "," "," "," "," "," "},
		{"-","-","-","-","-","-","-","-","-","-"," "," "," "," "," "," "," "," "," "," "}};
		print(20,3,header);
		System.out.println(" " + wildPokemonName);
		String[][] footer = new String[][] {
		{" "," "," "," "," "," "," "," "," "," "," ","P","i","k","a","c","h","u"," "," "},
		{" "," "," "," "," "," "," "," "," "," ","|"," "," "," "," "," "," "," "," "," "},
		{" "," "," "," "," "," "," "," "," "," ","|",ph1,ph2,ph3,ph4,ph5,ph6,ph7,ph8," "},
		{" "," "," "," "," "," "," "," "," "," ","-","-","-","-","-","-","-","-","-","-"}};
		print(20,4,footer);
		System.out.println("1.Fight    3.Pokemon");
		System.out.println("2.Pack     4.Run    ");

            //battleInput(pokemonNumber);
            int damage = 0;
            boolean inputerror;
            BufferedReader br;
            String b1 = " "; String b2 = " "; String b3 = " "; String b4 = " "; String b5 = " "; String b6 = " "; String b7 = " "; String b8 = " "; String b9 = " "; String b10 = " ";  String b11 = " ";

            boolean menuview = false;

            inputerror = true;
                br = new BufferedReader(new InputStreamReader(System.in));
                String input = null;
                try
                {
                        input = br.readLine();
                }
                catch (IOException ioe)
                {
                        System.out.println("IO error!");
                        System.exit(1);
                }
                one = "1";
                onekey = one.equalsIgnoreCase(input);
                if (onekey == true)
                {
                    Random r = new Random();
                    int  chance = (r.nextInt(5));
                    System.out.println(chance);
                        if (chance==4)
                        {
                            if (sleep == true)
                            {
								print(20,3,header);
								System.out.println(" " + wildPokemonName);
								print(20,4,footer);
								System.out.println("                    ");
								System.out.println("  Pikachu Woke Up   ");
								pause(2000);
							}
                            sleep = false;

                        }
                    if (sleep == true)
                    {
						print(20,3,header);
						System.out.println(" " + wildPokemonName);
						print(20,4,footer);
						System.out.println("                    ");
						System.out.println(" Pikachu Is Sleeping");
						pause(2000);
                    }
                    if (sleep == false)
                    {
						print(20,3,header);
						System.out.println(" " + wildPokemonName);
						print(20,4,footer);
						System.out.println("1.Tackle     3.Growl");
						System.out.println("2.Thunder    4.Sleep");

                    //attackChooser();
                    br = new BufferedReader(new InputStreamReader(System.in));
                    input = null;
                    try
                    {
                            input = br.readLine();
                    }
                    catch (IOException ioe)
                    {
                            System.out.println("IO error!");
                            System.exit(1);
                    }
                    onekey = false;
                    twokey = false;
                    threekey = false;
                    fourkey = false;
                    onekey = one.equalsIgnoreCase(input);
                    String attack = "";
                    if (onekey == true)
                    {
                    attack = "Tackle";
                    damage = 15;
                    deltadefense = 0;
                    }
                    twokey = two.equalsIgnoreCase(input);
                    if (twokey == true)
                    {
                    attack = "Thunder";
                    damage = 25;
                    deltadefense = 0;
                    }
                    threekey = three.equalsIgnoreCase(input);
                    if (threekey == true)
                    {
                    attack = "Growl";
                    damage = 0;
                    deltadefense = -0.1;
                    }
                    fourkey = four.equalsIgnoreCase(input);
                    if (fourkey == true)
                    {
                    attack = "Sleep";
                    damage = 0;
                    deltadefense = 0;
                    pikachuHealth = 100;
                    sleep = true;
                    }
					print(20,3,header);
					System.out.println(" " + wildPokemonName);
					print(20,4,footer);
					System.out.println("    Pikachu Used    ");
					for (int spacing = (int)((20 - attack.length())/2); spacing >= 0; spacing--)
					{
						spacer = spacer + " ";
					}

					System.out.println(spacer + attack + spacer);
                    healthCheck();
					pause(2000);

                    if (enemyDefense + deltadefense > 0)
                    {
                    enemyDefense = enemyDefense + deltadefense;
                    }
                    else
                    {
                        enemyDefense = 0.1;
                    }
                    enemyHealth = (int)(enemyHealth - damage/enemyDefense);
                    }
                    healthCheck();
                    if (enemyHealth <=0)
                    {
                        enemyHealth = 100; enemyDefense = 1; pikachuDefense = 1; battleOver = true; healthCheck();
                    }
                    else
                    {
                        enemyAttack(pokemonNumber);
                    }
                    healthCheck();
                    if (pikachuHealth <=0)
                    {
                        battleOver = true; //blackOut();
                    }
                    onekey = false;
                    twokey = false;
                    threekey = false;
                    fourkey = false;

                    input = null;
                }
                four = "4";
                fourkey = four.equalsIgnoreCase(input);
                if (fourkey == true)
                {
                    for(int i = 0; i<5; i++){System.out.println();}
                    System.out.print("  Got away safely!"); enemyHealth = 100; enemyDefense = 1; pikachuDefense = 1;
                    for(int i = 0; i<6; i++){System.out.println();}
                    try {
                    Thread.sleep(3000);
                    }
                    catch (Exception e) {}
                    battleOver = true;
                }
                two = "2";
                twokey = two.equalsIgnoreCase(input);
                if (twokey == true)
                {
                    landscape = new String[][] {
                    {"-","-","-","-","-","-","-","-","P","A","C","K","-","-","-","-","-","-","-","-"},
                    {" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
                    {"1","P","o","t","i","o","n"," ","-"," ","H","e","a","l","s"," ","2","0","H","P"},
                    {" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
                    {" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
                    {" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
                    {" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
                    {" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
                    {" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
                    {" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "}};

                    for (int i =0; i < 10; i++)
                    {
                            for (int j = 0; j < 20; j++)
                            {
                                    System.out.print( landscape[i][j]);
                            }
                            System.out.println("");
                    }
                    br = new BufferedReader(new InputStreamReader(System.in));
                    input = null;
                    try
                    {
                            input = br.readLine();
                    }
                    catch (IOException ioe)
                    {
                            System.out.println("IO error!");
                            System.exit(1);
                    }
                    onekey = false;
                    twokey = false;
                    threekey = false;
                    fourkey = false;
                    onekey = one.equalsIgnoreCase(input);
                    if (onekey == true)
                    {
                        pikachuHealth = pikachuHealth + 20;
						print(10,3,header);
						System.out.println(" " + wildPokemonName);
						print(10,4,footer);
						System.out.println("Pikachu Gained 20 HP");
						System.out.println("                    ");
						pause(2000);
                    }
                    healthCheck();
                    if (enemyHealth <=0)
                    {
                        enemyHealth = 100; enemyDefense = 1; pikachuDefense = 1; battleOver = true; healthCheck();
                    }
                    else
                    {
                        enemyAttack(pokemonNumber);
                    }
                    healthCheck();
                    if (pikachuHealth <=0)
                    {
                        battleOver = true; //blackOut();
                    }

                }
            }
        }
        public static void enemyAttack(int pokemonNumber)
        {
            int rows = 10;
            int cols = 10;
            String a1 = " "; String a2 = " "; String a3 = " "; String a4 = " "; String a5 = " "; String a6 = " "; String a7 = " "; String a8 = " "; String a9 = " "; String a10 = " ";  String a11 = " ";
            String b1 = " "; String b2 = " "; String b3 = " "; String b4 = " "; String b5 = " "; String b6 = " "; String b7 = " "; String b8 = " "; String b9 = " "; String b10 = " ";  String b11 = " ";
            int damage = 0;
            Random r = new Random();
            int  chance;
            double deltaDefense = 0;

            if(pokemonNumber == 7)
            {
                //Rattata
                a11 = "a"; a10 = "t"; a9 = "a"; a8 = "t"; a7 = "t"; a6 = "a"; a5 = "R";
                chance = (r.nextInt(2));
                if (chance==0)
                {

                    b1 = "T"; b2 = "a"; b3 = "c"; b4 = "k"; b5 = "l"; b6 = "e";
                    damage = 15;
                    deltaDefense = 0;
                }
                if (chance==1)
                {

                    b1 = "G"; b2 = "r"; b3 = "o"; b4 = "w"; b5 = "l";
                    damage = 0;
                    deltaDefense = -0.1;
                }
            }
            if(pokemonNumber == 8)
            {
                //Weedle
                a11 = "e"; a10 = "l"; a9 = "d"; a8 = "e"; a7 = "e"; a6 = "W";
                chance = (r.nextInt(1));
                if (chance==0)
                {
                    b1 = "T"; b2 = "a"; b3 = "c"; b4 = "k"; b5 = "l"; b6 = "e";
                    damage = 15;
                    deltaDefense = 0;
                }


            }
            if (pokemonNumber==9)
            {
                //Mankey
                a11 = "y"; a10 = "e"; a9 = "k"; a8 = "n"; a7 = "a"; a6 = "M";
                chance = (r.nextInt(2));
                if (chance==0)
                {
                    b1 = "S"; b2 = "c"; b3 = "r"; b4 = "a"; b5 = "t"; b6 = "c"; b7 = "h";
                    damage = 20;
                    deltaDefense = 0;
                }
                if (chance==1)
                {
                    b1 = "T"; b2 = "a"; b3 = "c"; b4 = "k"; b5 = "l"; b6 = "e";
                    damage = 15;
                    deltaDefense = 0;
                }

            }
            String [][] landscape = new String[][] {
            {"|"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {"|",eh1,eh2,eh3,eh4,eh5,eh6,eh7,eh8," "," "," "," "," "," "," "," "," "," "," "},
            {"-","-","-","-","-","-","-","-","-","-"," "," "," "," "," "," "," "," "," "," "},
            {" ",a4 ,a5 ,a6 ,a7 ,a8 ,a9 ,a10,a11," "," "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "," ","P","i","k","a","c","h","u"," "," "},
            {"|"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," ","|",ph1,ph2,ph3,ph4,ph5,ph6,ph7,ph8," "},
            {" "," "," "," "," "," "," "," "," "," ","-","-","-","-","-","-","-","-","-","-"},
            {" "," ",a4 ,a5 ,a6 ,a7 ,a8 ,a9 ,a10,a11," ","U","s","e","d"," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," ",b1 ,b2 ,b3 ,b4 ,b5 ,b6 ,b7 ,b8 ," "," "," "}};

            for (int i =0; i < 10; i++)
            {
                    for (int j = 0; j < 20; j++)
                    {
                            System.out.print( landscape[i][j]);
                    }
                    System.out.println("");
            }

            try {
            Thread.sleep(2000);
            }
            catch (Exception e) {}
            pikachuDefense = pikachuDefense + deltaDefense;
            pikachuHealth = (int)(pikachuHealth - damage/pikachuDefense);





        }
        public static void healthCheck()
        {
            if (enemyHealth <= 0)
            {
                eh1 = " ";
            }
            else
            {
                eh1 = "*";
            }
            if (enemyHealth <= 12.5)
            {
                eh2 = " ";
            }
            else
            {
                eh2 = "*";
            }
            if (enemyHealth <= 25)
            {
                eh3 = " ";
            }
            else
            {
                eh3 = "*";
            }
            if (enemyHealth <= 37.5)
            {
                eh4 = " ";
            }
            else
            {
                eh4 = "*";
            }
            if (enemyHealth <= 50)
            {
                eh5 = " ";
            }
            else
            {
                eh5 = "*";
            }
            if (enemyHealth <= 62.5)
            {
                eh6 = " ";
            }
            else
            {
                eh6 = "*";
            }
            if (enemyHealth <= 75)
            {
                eh7 = " ";
            }
            else
            {
                eh7 = "*";
            }
            if (enemyHealth <= 87.5)
            {
                eh8 = " ";
            }
            else
            {
                eh8 = "*";
            }
            if (pikachuHealth <= 0)
            {
                ph1 = " ";
            }
            else
            {
                ph1 = "*";
            }
            if (pikachuHealth <= 12.5)
            {
                ph2 = " ";
            }
            else
            {
                ph2 = "*";
            }
            if (pikachuHealth <= 25)
            {
                ph3 = " ";
            }
            else
            {
                ph3 = "*";
            }
            if (pikachuHealth <= 37.5)
            {
                ph4 = " ";
            }
            else
            {
                ph4 = "*";
            }
            if (pikachuHealth <= 50)
            {
                ph5 = " ";
            }
            else
            {
                ph5 = "*";
            }
            if (pikachuHealth <= 62.5)
            {
                ph6 = " ";
            }
            else
            {
                ph6 = "*";
            }
            if (pikachuHealth <= 75)
            {
                ph7 = " ";
            }
            else
            {
                ph7 = "*";
            }
            if (pikachuHealth <= 87.5)
            {
                ph8 = " ";
            }
            else
            {
                ph8 = "*";
            }
        }
        public static void battleInput(int pokemonNumber)
        {


        }
	public static void checkForGrass()
	{
		if ((x<4) && (y>5))
		{
			Random r = new Random();
			int  chance = (r.nextInt(10));
                        if (chance>6)
                        {
                            pokemonBattle(chance);
                        }
 		}
                if ((x==9) && (y==9))
		{
                        pokeCenter();
 		}
	}

	public static void pokeCenter()
	{
		boolean inPokecenter = true;
		int bX = 8;
		int bY = 2;
		while (inPokecenter == true)
		{

		int rows = 10;
		int cols = 10;
		String[][] landscape = new String[][] {
		{"|"," ","X"," ","|"," "," "," ","+","|"},
		{"|","-","-","-","|"," "," "," "," ","|"},
		{"|"," "," "," "," "," "," "," "," ","|"},
		{"|"," "," "," "," "," "," "," "," ","|"},
		{"|"," "," "," "," "," "," "," "," ","|"},
		{"|"," "," "," "," "," "," "," "," ","|"},
		{"|"," "," "," "," "," "," "," "," ","|"},
		{"|"," "," "," "," "," "," ","[","]","|"},
		{"|"," "," "," "," "," "," ","[","]","|"},
		{"|"," ","_","_"," "," "," "," "," ","|"}};
		landscape[bX][bY] = "X";
		System.out.println("bX = " + bX + ", bY = " + bY);
		for (int i =0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				System.out.print(" " + landscape[i][j]);
			}
			System.out.println("");
		}

				BufferedReader br;
		        boolean menuview = false;
				boolean inputerror = true;
				while(inputerror == true)
				{
					br = new BufferedReader(new InputStreamReader(System.in));
					String input = null;
					try
					{
						input = br.readLine();
					}
					catch (IOException ioe)
					{
						System.out.println("IO error!");
						System.exit(1);
					}
					String d = "d";
					boolean dkey = d.equalsIgnoreCase(input);
					if (dkey == true)
					{
						if(bY+1<9)
						{
							bY++;
							inputerror = false;
						}
						if(bY+1==9)
						{
							inputerror = false;
						}
					}
					String a = "a";
					boolean akey = a.equalsIgnoreCase(input);
					if (akey == true)
					{
		            	if(bY-1==0)
						{
							inputerror = false;

						}
		                else if(bY-1>=1)
						{
							if ((bY ==5) && (bX < 2))
							{
							}
							else
							{
							bY--;
							}
							inputerror = false;
						}

					}
					String w = "w";
					boolean wkey = w.equalsIgnoreCase(input);
					if (wkey == true)
					{
						if(bX-1>=0)
						{
							if ((bY <5) && (bX ==2))
							{
							}
							else
							{
							bX--;
							}
							inputerror = false;
							if ((bY == 2) && (bX ==2))
							{
								pokeCenterHeal();
							}
						}
		                else if(bX-1<0)
						{
							inputerror = false;
						}
					}
					String s = "s";
					boolean skey = s.equalsIgnoreCase(input);
					if (skey == true)
					{
						if(bX+1<10)
						{
							bX++;
							inputerror = false;
						}
						if(bX+1==10)
						{

						}
					}
					if (((bY == 2)||(bY == 3)) & (bX == 9))
					{
						inPokecenter = false;
						y--;
					}

			}
		}
	}

	public static void pokeCenterHeal()
	{
		String[][] landscape = new String[][] {
		{"|"," ","X"," ","|"," "," "," ","+","|"},
		{"|","-","-","-","|"," "," "," "," ","|"},
		{"|"," ","X"," "," "," "," "," "," ","|"},
		{"|"," "," "," "," "," "," "," "," ","|"},
		{"|"," "," "," "," "," "," "," "," ","|"},
		{"|"," "," "," "," "," "," "," "," ","|"},
		{"|"," "," "," "," "," "," "," "," ","|"},
		{"|"," "," "," "," "," "," ","[","]","|"}};

		print(8,10, landscape);
		System.out.println(" |  Welcome to the |");
		System.out.println(" |    PokeCenter   |");
		pause(1000);
		print(8,10,landscape);
		System.out.println(" | We can heal your|");
		System.out.println(" |     Pokemon!    |");
		pause(1000);
		print(8,10, landscape);
		System.out.println(" |  Please Give Me |");
		System.out.println(" |   Your Pikachu  |");
		pause(2000);
        pikachuHealth = 100;
        healthCheck();
        print(8,10, landscape);
		System.out.println(" | Your Pikachu Is |");
		System.out.println(" |   Fully Healed  |");
		pause(1000);
        print(8,10, landscape);
		System.out.println(" | Hope to See You |");
		System.out.println(" |      Again!     |");
		pause(1000);
	}
	public static void pause(int milliseconds)
	{
		try {Thread.sleep(milliseconds);}
        catch (Exception e) {}
	}

	public static void print(int rows, int columns, String[][] landscape)
	{
		for (int i =0; i < columns; i++)
		{
			for (int j = 0; j < rows; j++)
			{
				System.out.print(landscape[i][j]);
			}
			System.out.println("");
		}
	}
	public static void drawLandscape()
	{
		int rows = 10;
        int cols = 10;
		String[][] landscape = new String[][] {
		{"_","_","_","_","_","_",".",".",".","."},
		{"_","_","_","_","_","_",".",".",".","."},
		{"_","_","_","_","_","_",".",".",".","."},
		{"_","_","_","_","_","_",".",".",".","."},
		{"_","_","_","_","_","_","_","_","_","_"},
		{"_","_","_","_","_","_","_","_","_","_"},
		{"_","_","_","_","_","_","_","_","_","_"},
		{"_","_","_","_","_","_","_","_","_","_"},
		{"_","_","_","_","_","_","_","_","_","_"},
		{"_","_","_","_","_","_","_","_","_","A"}};
		landscape[x][y] = "X";
		for (int i =0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				System.out.print(" " + landscape[i][j]);
			}
			System.out.println("");
		}
	}

	public static void drawMenu()
	{
            	boolean inputerror;
		BufferedReader br;
                String input = null;

		String[][] menu = new String[][] {
		{"-","-","-","-","-","-","-","-","-","-"},
		{"|","1"," ","P","o","k","e","d","e","x"},
		{"|"," "," "," "," "," "," "," "," "," "},
		{"|","2"," ","P","o","k","e","m","o","n"},
		{"|"," "," "," "," "," "," "," "," "," "},
		{"|","3"," ","I","t","e","m","s"," "," "},
		{"|"," "," "," "," "," "," "," "," "," "},
		{"|","4"," ","E","x","i","t"," "," "," "},
		{"|"," "," "," "," "," "," "," "," "," "},
		{"|"," "," "," "," "," "," "," "," "," "}};

		String[][] landscape = new String[][] {
		{"_","_","_","_","_","_",".",".",".","."},
		{"_","_","_","_","_","_",".",".",".","."},
		{"_","_","_","_","_","_",".",".",".","."},
		{"_","_","_","_","_","_",".",".",".","."},
		{"_","_","_","_","_","_","_","_","_","_"},
		{"_","_","_","_","_","_","_","_","_","_"},
		{"_","_","_","_","_","_","_","_","_","_"},
		{"_","_","_","_","_","_","_","_","_","_"},
		{"_","_","_","_","_","_","_","_","_","_"},
		{"_","_","_","_","_","_","_","_","_","_"}};
		landscape[x][y] = "X";
		int rows = 10;
                int cols = 10;

		for (int i =0; i < rows; i++)
		{
			for (int j = 0; j < cols/2; j++)
			{
				System.out.print(" " + landscape[i][j]);
			}
			for (int j = 0; j < cols; j++)
			{
				System.out.print(menu[i][j]);
			}
			System.out.println("");
		}
                br = new BufferedReader(new InputStreamReader(System.in));
                String four = "4";
                boolean fourkey = four.equalsIgnoreCase(input);
                if (fourkey == true)
                {
                }
                String one = "1";
                boolean onekey = one.equalsIgnoreCase(input);
                if (onekey == true)
                {
                        drawPokedex();
                }
    }

    public static void drawPokedex()
    {

    }
    public static void input()
    {
		BufferedReader br;
        boolean menuview = false;
		boolean inputerror = true;
		while(inputerror == true)
		{
			br = new BufferedReader(new InputStreamReader(System.in));
			String input = null;
			try
			{
				input = br.readLine();
			}
			catch (IOException ioe)
			{
				System.out.println("IO error!");
				System.exit(1);
			}
			String d = "d";
			boolean dkey = d.equalsIgnoreCase(input);
			if (dkey == true)
			{
				if(y+1<=10)
				{
					y++;
					inputerror = false;
				}
				if(y+1==11)
				{
					y=0;
					inputerror = false;
				}
			}
			String a = "a";
			boolean akey = a.equalsIgnoreCase(input);
			if (akey == true)
			{
                            	if(y-1==-1)
				{
					y=9;
					inputerror = false;
                                        System.out.println(y + "2");
				}
                                else if(y-1>=0)
				{
					y--;
					inputerror = false;
                                        System.out.println(y);
				}

			}
			String w = "w";
			boolean wkey = w.equalsIgnoreCase(input);
			if (wkey == true)
			{
				if(x-1>=0)
				{
					x--;
					inputerror = false;
				}
                                else if(x-1<0)
				{
					x=9;
					inputerror = false;
				}
			}
			String s = "s";
			boolean skey = s.equalsIgnoreCase(input);
			if (skey == true)
			{
				if(x+1<=10)
				{
					x++;
					inputerror = false;
				}
				if(x+1==11)
				{
					x=0;
					inputerror = false;
				}
			}
			String m = "m";
			boolean mkey = m.equalsIgnoreCase(input);
			if (mkey == true)
			{
				menuview = true;
			}
			if(menuview==true)
			{
				drawMenu();
                                inputerror = false;
			}
		}
	}
    public static void battleAnimation()
    {
        for(int i=0; i<4; i++)
        {

        bLank();
        try {
            Thread.sleep(500);
        }
        catch (Exception e) {}
        pokeBall(1);
        try {
            Thread.sleep(500);
        }
        catch (Exception e) {}
        }
        sliderAnimation(1);
        try {
            Thread.sleep(300);
        }
        catch (Exception e) {}
        sliderAnimation(2);
        try {
            Thread.sleep(300);
        }
        catch (Exception e) {}
        sliderAnimation(3);
        try {
            Thread.sleep(300);
        }
        catch (Exception e) {}
        sliderAnimation(4);
        try {
            Thread.sleep(300);
        }
        catch (Exception e) {}
        sliderAnimation(5);
        try {
            Thread.sleep(300);
        }
        catch (Exception e) {}
        sliderAnimation(6);
        try {
            Thread.sleep(300);
        }
        catch (Exception e) {}
        sliderAnimation(7);
        try {
            Thread.sleep(300);
        }
        catch (Exception e) {}
        sliderAnimation(8);
        try {
            Thread.sleep(300);
        }
        catch (Exception e) {}
        sliderAnimation(9);
        try {
            Thread.sleep(300);
        }
        catch (Exception e) {}
        sliderAnimation(10);
        try {
            Thread.sleep(300);
        }
        catch (Exception e) {}
    }
    public static void bLank()
    {

                int rows = 10;
        int cols = 10;
        String[][] landscape = new String[][] {
        {" "," "," "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "," "," "}};
        for (int i =0; i < rows; i++)
        {
                for (int j = 0; j < cols; j++)
                {
                        System.out.print(" " + landscape[i][j]);
                }
                System.out.println("");
        }
    }
    public static void pokeBall(int welcome)
    {
        String[][] landscape = new String[10][10];
                int rows = 10;
        int cols = 10;
        if (welcome ==1)
        {
        landscape = new String[][] {
        {" "," "," ","-","-","-","-"," "," "," "},
        {" "," ","-"," "," "," "," ","-"," "," "},
        {" ","-"," "," "," "," "," "," ","-"," "},
        {"-"," "," "," "," "," "," "," "," ","-"},
        {"-"," "," ","-","-","-","-"," "," ","-"},
        {"-","-","-","-"," "," ","-","-","-","-"},
        {"-"," "," ","-","-","-","-"," "," ","-"},
        {" ","-"," "," "," "," "," "," ","-"," "},
        {" "," ","-"," "," "," "," ","-"," "," "},
        {" "," "," ","-","-","-","-"," "," "," "}};
        }
        if (welcome==0)
        {
        landscape = new String[][] {
        {" "," "," ","-","-","-","-"," "," "," "},
        {" "," ","-"," "," "," "," ","-"," "," "},
        {" ","-"," "," "," "," "," "," ","-"," "},
        {"-","W","E","L","C","O","M","E"," ","-"},
        {"-"," "," ","-","-","-","-"," "," ","-"},
        {"-","-","-","-","T","O","-","-","-","-"},
        {"-"," "," ","-","-","-","-"," "," ","-"},
        {" ","-","P","O","K","E","M","O","N"," "},
        {" "," ","-"," "," "," "," ","-"," "," "},
        {" "," "," ","-","-","-","-"," "," "," "}};
        }
        for (int i =0; i < rows; i++)
        {
                for (int j = 0; j < cols; j++)
                {
                        System.out.print(" " + landscape[i][j]);
                }
                System.out.println("");
        }

    }
    public static void sliderAnimation(int step)
    {
        if (step==1)
        {
            int rows = 10;
            int cols = 10;
            String[][] landscape = new String[][] {
            {" "," "," "," "," "," "," "," "," ","|"},
            {" "," "," "," "," "," "," "," "," ","|"},
            {" "," "," "," "," "," "," "," "," ","-"},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {"-"," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "}};
            for (int i =0; i < rows; i++)
            {
                    for (int j = 0; j < cols; j++)
                    {
                            System.out.print(" " + landscape[i][j]);
                    }
                    System.out.println("");
            }
        }
        if (step==2)
        {
            int rows = 10;
            int cols = 10;
            String[][] landscape = new String[][] {
            {" "," "," "," "," "," "," "," ","|"," "},
            {" "," "," "," "," "," "," "," ","|"," "},
            {" "," "," "," "," "," "," "," ","-","-"},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {"-","-"," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "}};
            for (int i =0; i < rows; i++)
            {
                    for (int j = 0; j < cols; j++)
                    {
                            System.out.print(" " + landscape[i][j]);
                    }
                    System.out.println("");
            }
        }
        if (step==3)
        {
            int rows = 10;
            int cols = 10;
            String[][] landscape = new String[][] {
            {" "," "," "," "," "," "," ","|"," "," "},
            {" "," "," "," "," "," "," ","|"," "," "},
            {" "," "," "," "," "," "," ","|","-","-"},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {"-","-","-"," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "}};
            for (int i =0; i < rows; i++)
            {
                    for (int j = 0; j < cols; j++)
                    {
                            System.out.print(" " + landscape[i][j]);
                    }
                    System.out.println("");
            }
        }
        if (step==4)
        {
            int rows = 10;
            int cols = 10;
            String[][] landscape = new String[][] {
            {" "," "," "," "," "," ","|"," "," "," "},
            {" "," "," "," "," "," ","|"," "," "," "},
            {" "," "," "," "," "," ","-","-","-","-"},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {"-","-","-","-"," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "}};
            for (int i =0; i < rows; i++)
            {
                    for (int j = 0; j < cols; j++)
                    {
                            System.out.print(" " + landscape[i][j]);
                    }
                    System.out.println("");
            }
        }
        if (step==5)
        {
            int rows = 10;
            int cols = 10;
            String[][] landscape = new String[][] {
            {" "," "," "," "," ","|"," "," "," "," "},
            {" "," "," "," "," ","|"," "," "," "," "},
            {" "," "," "," "," ","-","-","-","-","-"},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {"|"," "," "," "," "," "," "," "," "," "},
            {"|"," "," "," "," "," "," "," "," "," "},
            {"-","-","-","-","-"," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "}};
            for (int i =0; i < rows; i++)
            {
                    for (int j = 0; j < cols; j++)
                    {
                            System.out.print(" " + landscape[i][j]);
                    }
                    System.out.println("");
            }
        }
        if (step==6)
        {
            int rows = 10;
            int cols = 10;
            String[][] landscape = new String[][] {
            {" "," "," "," ","|"," "," "," "," "," "},
            {" "," "," "," ","|"," "," "," "," "," "},
            {" "," "," "," ","-","-","-","-","-"," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" ","|"," "," "," "," "," "," "," "," "},
            {" ","|"," "," "," "," "," "," "," "," "},
            {" ","-","-","-","-","-"," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "}};
            for (int i =0; i < rows; i++)
            {
                    for (int j = 0; j < cols; j++)
                    {
                            System.out.print(" " + landscape[i][j]);
                    }
                    System.out.println("");
            }
        }
        if (step==7)
        {
            int rows = 10;
            int cols = 10;
            String[][] landscape = new String[][] {
            {" "," "," ","|"," "," "," "," "," "," "},
            {" "," "," ","|"," "," "," "," "," "," "},
            {" "," "," ","-","-","-","-","-"," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," ","|"," "," "," "," "," "," "," "},
            {" "," ","|"," "," "," "," "," "," "," "},
            {" "," ","-","-","-","-","-"," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "}};
            for (int i =0; i < rows; i++)
            {
                    for (int j = 0; j < cols; j++)
                    {
                            System.out.print(" " + landscape[i][j]);
                    }
                    System.out.println("");
            }
        }
        if (step==8)
        {
            int rows = 10;
            int cols = 10;
            String[][] landscape = new String[][] {
            {" "," ","|"," "," "," "," "," "," "," "},
            {" "," ","|"," "," "," "," "," "," "," "},
            {" "," ","-","-","-","-","-"," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," ","|"," "," "," "," "," "," "},
            {" "," "," ","|"," "," "," "," "," "," "},
            {" "," "," ","-","-","-","-","-"," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "}};
            for (int i =0; i < rows; i++)
            {
                    for (int j = 0; j < cols; j++)
                    {
                            System.out.print(" " + landscape[i][j]);
                    }
                    System.out.println("");
            }
        }
        if (step==9)
        {
            int rows = 10;
            int cols = 10;
            String[][] landscape = new String[][] {
            {" ","|"," "," "," "," "," "," "," "," "},
            {" ","|"," "," "," "," "," "," "," "," "},
            {" ","-","-","-","-","-"," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," ","|"," "," "," "," "," "},
            {" "," "," "," ","|"," "," "," "," "," "},
            {" "," "," "," ","-","-","-","-","-"," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "}};
            for (int i =0; i < rows; i++)
            {
                    for (int j = 0; j < cols; j++)
                    {
                            System.out.print(" " + landscape[i][j]);
                    }
                    System.out.println("");
            }
        }
        if (step==10)
        {
            int rows = 10;
            int cols = 10;
            String[][] landscape = new String[][] {
            {"|"," "," "," "," "," "," "," "," "," "},
            {"|"," "," "," "," "," "," "," "," "," "},
            {"-","-","-","-","-"," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," ","|"," "," "," "," "},
            {" "," "," "," "," ","|"," "," "," "," "},
            {" "," "," "," "," ","-","-","-","-","-"},
            {" "," "," "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "," "," "}};
            for (int i =0; i < rows; i++)
            {
                    for (int j = 0; j < cols; j++)
                    {
                            System.out.print(" " + landscape[i][j]);
                    }
                    System.out.println("");
            }
        }
    }
}




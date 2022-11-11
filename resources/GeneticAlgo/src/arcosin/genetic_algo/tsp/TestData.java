package arcosin.genetic_algo.tsp;

public class TestData
{
	public static final POI [] map1 = 
		{		// 21 cities where x:[0,500] and y:[0,500].
				new POI(200, 100),
				new POI(100, 0),
				new POI(10, 5),
				new POI(0, 0),
				new POI(0, 25),
				new POI(0, 10),
				new POI(40, 0),
				new POI(200, 200),
				new POI(345, 200),
				new POI(68, 40),
				new POI(55, 195),
				new POI(250, 140),
				new POI(44, 8),
				new POI(20, 180),
				new POI(66, 55),
				new POI(66, 195),
				new POI(44, 66),
				new POI(22, 300),
				new POI(500, 500),
				new POI(500, 0),
				new POI(0, 500)
		};
	
	
	public static final POI [] map2 = 
		{		// 29 cities from Western Sahara. Optimum distance 27603. 
				new POI(20833, 17100),	//1
				new POI(20900, 17067),	//2
				new POI(21300, 13017),	//3
				new POI(21600, 14150),	//4
				new POI(21600, 14967),	//5
				new POI(21600, 16500),	//6
				new POI(22183, 13133),	//7
				new POI(22583, 14300),	//8
				new POI(22683, 12717),	//9
				new POI(23617, 15867),	//10
				new POI(23700, 15933),	//11
				new POI(23883, 14533),	//12
				new POI(24167, 13250),	//13
				new POI(25149, 12366),	//14
				new POI(26133, 14500),	//15
				new POI(26150, 10550),	//16
				new POI(26283, 12766),	//17
				new POI(26433, 13433),	//18
				new POI(26550, 13850),	//19
				new POI(26733, 11683),	//20
				new POI(27026, 13052),	//21
				new POI(27154, 13416),	//22
				new POI(20833, 13203),	//23
				new POI(27167, 9833),	//24
				new POI(27233, 10450),	//25
				new POI(27233, 11783),	//26
				new POI(27267, 10383),	//27
				new POI(27433, 12400),	//28
				new POI(27463, 12992)	//29
		};
	public static final POI [] map3 = 
		{		// 29 cities from Western Sahara. Optimum distance 27603. 
				new POI(38, -7),	//Évora
				new POI(38, -9),	//Lisboa
				new POI(41, -8),	//Porto
				new POI(37, -7),	//Faro
				new POI(39, -7),	//Castelo Branco
				new POI(41, -6),	//Bragança
				new POI(43, -2),	//Bilbao
				new POI(41, 2),	//Barcelona
				new POI(40, -3),	//Madrid
		};
}

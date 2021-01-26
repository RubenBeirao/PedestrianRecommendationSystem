package org.quasar.route.graphhopper;

	/**
	 * Represents an Effort Calculator
	 * @author Rúben Beirão
	 *This calculator was adapted from the treadmill calculator available on 
	 *(http://www.csecho.ca/wp-content/themes/twentyeleven-csecho/cardiomath/?eqnHD=stress&eqnDisp=mvo2tm). It allows 
	 *you to estimate the METs and VO2 given the treadmill speed and the grade.
	 *In this case allows to  estimate the METs given the walking speed and the slope of the streets.
	 *
	 *Speed = WalkingSpeed * 26.8
	 *Horizontal Component (HC) = Speed * 0.1
	 *
	 *The slope is equivalent to the treadmill grade, which is basically a measure of the height distance for every 
	 *100 horizontal distance. e.g. A one in 100 gradient = 1%, and a rise of 15 meters for every 100 meters is a 15 % grade.
	 *To calculate the percent grade, simply divide the rise by the run and multiply by 100. For example, 
	 *if you raise the belt so that the front is 0.5 feet higher than the back, and the front and the back are separated
	 *by 8 feet along the floor, then the percent grade is (0.5/8) * 100 = 6.25 percent.
	 *
	 *Slope = raise/distance*100
	 *
	 *Vertical Component (VC) =	Speed * 1.8 * Slope
	 *VO2 =	HC + VC + Rest = HC + VC + 3.5 mL/kg/min
	 *Mets = (VO2/3.5)
	 *
	 *Speed [mph]
	 *Slope [%]
	 */

	public class Effort {
		private double MET;
		private double distance; //in meters
		private double time; //in seconds
		private double height0; //in meters
		private double height1; //in meters
		private double slope;
		private double wSpeed; //equivalent to the walking speed in miles per hours
		private double speed; //wSpeed * 26.8
		private double horizontalComponent;
		private double verticalComponent;
		private double vO2;
		
		/**
		 * Creates an Effort Calculator with the specified distance, time and heights
		 * @param distance A double representing the distance of travelling from place0 to place1
		 * @param time A double representing the time of travelling from place0 to place1
		 * @param height0 A double representing the elevation of place0
		 * @param height1 A double representing the elevation of place1 
		 */
		public Effort(double distance, double time, double height0, double height1) {
			this.distance = distance;
			this.time = time;
			this.height0 = height0;
			this.height1 = height1;
			wSpeed = calcWalkSpeed();
//			System.out.println("Walking speed is " + wSpeed + " mph");
			speed = wSpeed*26.8;
//			System.out.println("Speed is " + speed);
			horizontalComponent = calcHorizontalComponent();
//			System.out.println("HC is " + horizontalComponent);
			slope = caclSlope();
//			System.out.println("Slope is " + slope + "%");
			verticalComponent = calcVerticalComponent();
			vO2 = calcVO2();
//			System.out.println("VO2 is " + vO2);
			MET = calcMET();
			
		}
		
		private double calcVO2() {
			double rest = 3.5;
			return (horizontalComponent+verticalComponent+rest);
		}

		private double calcVerticalComponent() {
			return (speed*1.8*slope);
		}

		private double calcHorizontalComponent() {
			return (speed*0.1);
		}

		//calc slope in percentage (%)
		private double caclSlope() {
			return (((height1-height0)/distance));
		}
		
		//calc walking speed in mph
		private double calcWalkSpeed() {
			//first calculate the speed in meters per second (m/s)
			//then transform it from m/s to mph
			//miles per hour = meters per second × 2.236936
			return ((distance/time)*2.236936);
		}
		
//		private double calcMET() {
//			return (((distance/time)*0.1) + ((distance/time)*1.8*slope) + 3.5 )/3.5;
//		}
		
		private double calcMET() {
			return (vO2/3.5);
		}
		
		/**
		 * Gets the MET of travelling from place0 to place1
		 * @return A double corresponding to the MET of travelling from place0 to place1
		 */
		public double getMET() {
			System.out.println("MET = " + MET);
			return MET;
		}
		
		/**
		 * Calories estimation of travelling from place0 to place1
		 * EE = (0.1*S+1.8*S*G+3.5)*BM*t*0.005
		 * where EE is walking energy expenditure (kilocalories) 
		 * S is walking speed (meters/minute)
		 * G is grade (slope) in decimal form (e.g., 0.02 for 2% grade)
		 * BM is traveller’s body weight (kilograms) 
		 * t is walking time (minutes)
		 * @return EE
		 */	
		public double calorieEstimation() {
			double S = (distance/time)*60;
			double G = caclSlope();
			double BM = 65;
			double t = (time/60);
			
			double EE = (0.1*S+1.8*S*G+3.5)*BM*t*0.005;
			
			return EE;
		}
		
		public static void main(String[] args) {
			Effort e = new Effort(759, 547.096, 85, 59.9);
			System.out.println(e.getMET());
			
			System.out.println("Kilocalories " + e.calorieEstimation());
		}
}

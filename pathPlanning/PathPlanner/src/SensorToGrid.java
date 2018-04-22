import java.util.ArrayList;
import java.math.*;

public class SensorToGrid {
	
	private static int[] PATH;
	//private static ArrayList[][] GRID;
	private static int[][] GRID;
	
	private static double TILE_SIZE = 6.86; //the square dimension of a grid tile, measured in cm
	private static double CAMERA_WIDTH_CLOSE = 10; //the width of the area the camera can see at the camera, measured in cm
	private static double CAMERA_WIDTH_FAR = 50; //the width of the area the camera can see at the max range, measured in cm
	private static double CAMERA_RANGE = 200; //how far the camera can see, measured in cm
	
	private static int GRIDS_VERTICAL = (int) Math.ceil(CAMERA_RANGE/TILE_SIZE);
	private static int GRIDS_HORIZONTAL_CLOSE  = (int) Math.ceil(CAMERA_WIDTH_CLOSE/TILE_SIZE);
	private static int GRIDS_HORIZONTAL_FAR  = (int) Math.ceil(CAMERA_WIDTH_FAR/TILE_SIZE);
	
	
	
	public static void main(String arg[]){
	
//		double start = System.currentTimeMillis();
//		getPath();
//		double end = System.currentTimeMillis();
//		
//		System.out.println("Time Elapsed From Sensor to Grid: " + (end-start)/1000);
		//int[] tiles = unitsToTiles(6.86, 45.23);
		//System.out.println("xTiles: " + tiles[0] + '\n' + "yTiles: " + tiles[1] );
		newImageGrids();
	}
	
	private void cameraToGrid(){
		
	}
	
	private static void newImageGrids(){
		int numGridsVertical = (int) Math.ceil(CAMERA_RANGE/TILE_SIZE);
		int numGridsHorizontal_close  = (int) Math.ceil(CAMERA_WIDTH_CLOSE/TILE_SIZE);
		int numGridsHorizontal_far  = (int) Math.ceil(CAMERA_WIDTH_FAR/TILE_SIZE);
		double angle_rads = Math.tan(CAMERA_RANGE/((CAMERA_WIDTH_FAR-CAMERA_WIDTH_CLOSE)/2));
		double angle_deg = (180*angle_rads)/(Math.PI);
		
		int rowLength = numGridsHorizontal_close; //figure out how to change rowLength with angle of robot
		int rowPadding = (numGridsHorizontal_far-rowLength)/2;
		//int[][] test = new int[numGridsVertical][numGridsHorizontal_far];
		GRID = new int[numGridsVertical][numGridsHorizontal_far];
		for(int i=0; i < numGridsVertical; i++){
			
			if(i<7){/* do nothing */}
			else if(i<19){rowLength = 4;}
			else if(i<27){rowLength = 6;}
			else{rowLength = 8;}
			
			rowPadding = (numGridsHorizontal_far-rowLength)/2;
			
			for(int j=0; j<numGridsHorizontal_far; j++){
				if(j<rowPadding){GRID[i][j] = 0;}
				else if(j<rowPadding + rowLength){GRID[i][j] = 1;}
				else{GRID[i][j] = 0;}
			}
		}
		
		
		
//		System.out.println(angle_rads);
//		System.out.println(angle_deg);
//		System.out.println(numGridsVertical);
//		System.out.println(numGridsHorizontal_close);
//		System.out.println(numGridsHorizontal_far);
//		System.out.println();
//		System.out.println();
		
		GRID[3][4]=2;
		//GRID[6][4]=2;
		GRID[12][2]=3;
		GRID[28][6]=4;
		printGrid(GRID);
		/*
		 * 		0	1	2	3 . . .
		 * 	0
		 * 	1
		 * 	2
		 * 	3
		 * 	.
		 * 	.
		 * 	.
		 * 
		 * 
		 * */
		
		int[][] ballLocations = {{0,0}, {0,1}, {0,2}, {0,3}, {0,4}, {0,6}};
		generateDistanceArray(ballLocations);

		
	}
	
	/**
	 * Given the ball locations on the grid, this function calculates the distance between each ball and creates an array with those distances
	 * @param ballLocations int array containing tuples of x and y (row and column) numbers
	 */
	private static void generateDistanceArray(int[][] ballLocations){
		int[][] distanceArray = new int[ballLocations.length][ballLocations.length];
				
		for(int i=0; i<ballLocations.length; i++){
			for(int j=0; j<ballLocations.length; j++){
				
				if(i==j){distanceArray[i][j]=0;}
				else{
					
					//ballLocations[i][0] => y-value |
					//								 |---> of ball that we're testing other balls against
					//ballLocations[i][1] => x-value |
					
					//ballLocations[j][0] => y-value |
					//								 |---> of 'other' ball
					//ballLocations[j][1] => x-value |
					
					//Pythagorean Theorem to find straight line distance between two balls
					distanceArray[i][j] = (int) Math.rint(Math.sqrt(((ballLocations[i][0]-ballLocations[j][0])*TILE_SIZE)*((ballLocations[i][0]-ballLocations[j][0])*TILE_SIZE) + ((ballLocations[i][1]-ballLocations[j][1])*TILE_SIZE)*((ballLocations[i][1]-ballLocations[j][1])*TILE_SIZE)));
					
				}
			}
		}
		
		printDistanceArray(distanceArray);
		getPath(distanceArray);
	}
	
	private static void printDistanceArray(int[][] distanceArray){
		for(int i=0; i<distanceArray.length; i++){
			for(int j=0; j<distanceArray.length; j++){
				System.out.print(distanceArray[i][j] + "\t");
			}
			System.out.println();
			
		}
		
	}
	
	private static void printGrid(int[][] grid){
		int gridLength = grid.length;
		int gridWidth = grid[0].length;
		
		for(int i=gridLength-1; i>=0; i--){
			for(int j=0; j<gridWidth; j++){
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}
	
	/**
	 * 
	 * This function converts the real world measurements of x and y into tile amounts
	 * In our case every tile will be the size of one tennis ball (represented as 6.86cm x 6.86cm)
	 * Also need to know the angle the robot is relative to the GRID --> this is because at the start we
	 * say that we are perpendicular to the GRID and we need to ensure that all other measurements align
	 * @param x the x coordinate of the object
	 * @param y the y coordinate of the object
	 */
	private static int[] unitsToTiles(double x, double y){
		int xTiles = (int) Math.ceil(x/TILE_SIZE);
		int yTiles = (int) Math.ceil(y/TILE_SIZE);
		int[] tiles = {xTiles, yTiles};
		return tiles;
	}
	
	private static void getPath(int[][] distanceArray){
		System.out.println();
		System.out.println("HELLO....");
		//ShortestPath.getPath(generateRandomGrid(10));
		PATH = ShortestPath.getPath(distanceArray);
	
        for(int cnt=0; cnt<PATH.length; cnt++){
        	System.out.println("PATH:" + PATH[cnt]);
        }
		System.out.println();
		System.out.println();
		System.out.println("GOODBYE...");
	}
	private static int[][] generateRandomGrid(int numBalls){
		int distance[][]=new int[numBalls][numBalls];
		
		for(int i=0; i < numBalls; i++){
			for(int j=0; j<numBalls; j++){
				distance[i][j] = (int)(Math.random()*50);
			}
		}
		
		return distance;
		
	}
}

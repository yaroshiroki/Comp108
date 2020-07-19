/*

Name: Yaroslav Shiroki
Student ID: 201458436
University email address: Y.Shiroki@student.liverpool.ac.uk

Time Complexity and explanation:
n denotes the number of vertices
You can use any of the above notations or define additional notations as you wish.


neighbourhood(): The time complexity for this function (based on what is complete) is (n)^3.
	
degreeSeparation(): The time complexity for this function (based on what is complete) is (n)^2.

*/

import java.util.*;
import java.io.*;

class COMP108A2Graph {

	private static final int MaxVertex = 100;
	private static final int MinVertex = 2;
	private static Scanner keyboardInput = new Scanner (System.in);
	// adjacency matrix, adjMatrix[i][j] = 1 means i and j are adjacent, 0 otherwise
	public static int[][] adjMatrix = new int[MaxVertex][MaxVertex];
	public static int numVertex; // total number of vertices

	// DO NOT change the main method
	public static void main(String[] args) throws Exception {
		boolean userContinue=true;
		int distance=1;
		int[][] neighbourMatrix = new int[MaxVertex][MaxVertex];
		
		input();

		try {
//			System.out.print("Enter a distance (" + MinVertex + "--" + numVertex + ", -1 to exit): ");
			distance = keyboardInput.nextInt();
		}
		catch (Exception e) {
			keyboardInput.next();
		}
		if (distance < MinVertex || distance > numVertex)
			System.out.println("incorrect range");
		else { 
			neighbourhood(distance, neighbourMatrix, numVertex);
			printSquareArray(neighbourMatrix, numVertex);
		}
		
		degreeSeparation();
	}

	// find the degree of separation of the graph using the method neighbourhood()
	static void degreeSeparation() {
		//used resources from labs to produce this.
		int degreeG = 0;
		int degreeV = 0;
		for (int i=0; i<numVertex; i++) {
			degreeV = 0;
			for (int j=0; j<numVertex; j++) {
				degreeV += adjMatrix[i][j];
			}
			if (degreeV > degreeG)
				degreeG = degreeV;
		}
		//degree of a vertex is the number of neighbours it has.
		System.out.println("Degree of seperation is " + degreeG);
	}

	// input parameter: an integer distance
	// output: compute neighbourhood matrix for distance 
	static void neighbourhood(int distance, int result[][], int size) {
		//declaring variables for the loops.
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 2;
		while (j < size) {
			while (i < size) {
				//symmetry comparrison.
				if (i == j) {
					result[i][j] = 0;
				}
				//compare the adjMatrix with 0 and any integer greater than 1.
				if (adjMatrix[j][i] == 0 || adjMatrix[j][i] > 1) {
					//act accordingly.
					adjMatrix[j][i] = adjMatrix[i][j];
				}
				// compare the result with 1.
				if (adjMatrix[j][i] == 1) {
					result[j][i] = 1;
				}
				// since a node cannot have a connection with it self, we make sure that this is checked.
				else if (i != j) {
					// looping to check the distances in the row.
					while (k < size) {
						if (adjMatrix[i][k] == 1 && adjMatrix[j][k] > 0){
							// calculating the distances.
							if (i != k) {
								result[j][i] = adjMatrix[i][k] + 1;
							}
							// if not then breaks out of loop.
							else {
								break;
							}
						}
						//increase the count by 1.
						k = k + 1;
					}
					if (result[j][i] > distance) {
						result[j][i] = 0;
					}
				}
				while (l < distance) {
					//I could not figure out a way to make this work, appologies.
					l = l + 1;
				}
				i = i + 1;
			}
			j = j + 1;
			//reset i so that it can keep going through the following rows in the matrix.
			i = 0;
		}
	}

	// DO NOT change this method
	static void printSquareArray(int array[][], int size) {
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}

	
	// DO NOT change this method
	static void input() {
		int i, j;
		boolean success = false;

		try {
			success = true;
//			System.out.print("How many vertices (" + MinVertex + "--" + MaxVertex + ")? ");
			numVertex = keyboardInput.nextInt();
			if (numVertex > MaxVertex || numVertex < MinVertex) {
				success = false;
			}
			if (success) {
//				System.out.println("Enter adjacency matrix: ");
				for (i=0; i<numVertex; i++)
					for (j=0; j<numVertex; j++)
						adjMatrix[i][j] = keyboardInput.nextInt();
				for (i=0; i<numVertex && success; i++) {
					if (adjMatrix[i][i] != 0)
						success = false;
					for (j=0; j<numVertex; j++) {
						if (adjMatrix[i][j] != adjMatrix[j][i])
							success = false;
					}
				}
			}
			if (!success) {
				System.out.print("Incorrect range ");
				System.out.print("or adjacency matrix not symmetric ");
				System.out.println("or vertex connected to itself");
				System.exit(0);
			}
		}
		catch (Exception e) {
			keyboardInput.next();
		}
	}

}


/*
 * @nama: George Albert
 * @npm: 1406569781
 * @title: Tugas 3 - Gomoku | ArtificialIntelligence
 *
 */

import java.util.Random;
import java.util.Arrays;

public class ArtificialIntelligence {

	/*
	 * 
	 * ArtificialIntelligence ini masih bersifat eksperimen, sehingga dokumentasi yang ada hanya
	 * dari debug2 yang tersisa. Kemungkinan besar masih ada beberapa bagian yang menyebabkan
	 * error pada program.
	 * 
	 */
	
	
	private Random rand = new Random();

	public int[] begin(Papan papanAsli, int yCart, int xCart, int xsLastYCart, int xsLastXCart, int attMet) {
		//System.out.println("-----------BEGIN-----------");

		int[] tempEmpty = {0, 0, 0, attMet};
		//System.out.println("attMet in begin: " + attMet);

		for (int i = 4; i >= 3; i--) {
			//System.out.println("In loop [" + i + "] now.");
			if (!Arrays.equals(testHorizontal(i, papanAsli, yCart, xCart, attMet), tempEmpty)) {
				//System.out.println("Defend Horizontal");
				return testHorizontal(i, papanAsli, yCart, xCart, attMet);
			}
			else if (!Arrays.equals(testVertical(i, papanAsli, yCart, xCart, attMet), tempEmpty)) {
				//System.out.println("Defend Vertical");
				return testVertical(i, papanAsli, yCart, xCart, attMet);
			}
			else if (!Arrays.equals(testDiagonalF(i, papanAsli, yCart, xCart, attMet), tempEmpty)) {
				//System.out.println("Defend DiagonalF");
				return testDiagonalF(i, papanAsli, yCart, xCart, attMet);
			}
			else if (!Arrays.equals(testDiagonalB(i, papanAsli, yCart, xCart, attMet), tempEmpty)) {
				//System.out.println("Defend DiagonalB");
				return testDiagonalB(i, papanAsli, yCart, xCart, attMet);
			}
		}

		//System.out.println("-------NO DEFENSE REQUIRED--------");

		if (!Arrays.equals(attackHorizontal(papanAsli, xsLastYCart, xsLastXCart, attMet), tempEmpty) && attMet == 0) {
			//System.out.println("Attack Horizontal");
			attMet = attackHorizontal(papanAsli, xsLastYCart, xsLastXCart, attMet)[3];
			return attackHorizontal(papanAsli, xsLastYCart, xsLastXCart, attMet);
		}
		else if (!Arrays.equals(attackVertical(papanAsli, xsLastYCart, xsLastXCart, attMet), tempEmpty) && attMet == 1) {
			//System.out.println("Attack Vertical");
			attMet = attackVertical(papanAsli, xsLastYCart, xsLastXCart, attMet)[3];
			return attackVertical(papanAsli, xsLastYCart, xsLastXCart, attMet);
		}
		else if (!Arrays.equals(attackDiagonalF(papanAsli, xsLastYCart, xsLastXCart, attMet), tempEmpty) && attMet == 2) {
			//System.out.println("Attack DiagonalF");
			attMet = attackDiagonalF(papanAsli, xsLastYCart, xsLastXCart, attMet)[3];
			return attackDiagonalF(papanAsli, xsLastYCart, xsLastXCart, attMet);
		}
		else attMet = rand.nextInt(3) + 0;

		//System.out.println("-------NO DEFENSE OR ATTACK | attMet: "+ attMet + " ---------");

		int[] coord = {rand.nextInt(19), rand.nextInt(19), 0, attMet};
		return coord;
	}

	public int[] testDiagonalB(int danger, Papan papanAsli, int yCart, int xCart, int attMet) {
		for (int j = -4, cou = 0; j <= 4; j++) {
			if ( xCart - j >= 0 && xCart - j < 19 && yCart - j >= 0 && yCart - j < 19 && papanAsli.getPiece(yCart - j, xCart - j).equals("O ")) {
				if (++cou == danger) {
					//System.out.println("DANGER");
					int coord[] = {xCart, yCart, cou, attMet};
					return defendDiagonalB(papanAsli, coord, attMet);
				}
			} 
			else cou = 0;
		}
		int[] coord = {0, 0, 0, attMet};
		return coord;
	}

	public int[] defendDiagonalB(Papan papanAsli, int[] coord, int attMet) {
		System.out.println("Co[0]: " + coord[0] + " | Co[1]: " + coord[1] + " | Co[2]: " + coord[2]);
		if (coord[0]-coord[2] >= 0 && coord[1]-coord[2] >= 0 && papanAsli.getPiece(coord[1]-coord[2], coord[0]-coord[2]).equals(". ")) {
			//System.out.println("1st case");
			coord[0] -= coord[2];
			coord[1] -= coord[2];
			return coord;
		}
		else if (coord[0]+1 < 19 && coord[1]-1 >= 0 && papanAsli.getPiece(coord[1]+1, coord[0]+1).equals(". ")) {
			//System.out.println("2ndcase");
			coord[0] += 1;
			coord[1] += 1;
			return coord;
		}
		int[] doItRandom = {0, 0, 0, attMet};
		return doItRandom;
	}
	
	public int[] testDiagonalF(int danger, Papan papanAsli, int yCart, int xCart, int attMet) {
		for (int j = -4, cou = 0; j <= 4; j++) {
			if ( xCart + j >= 0 && xCart + j < 19 && yCart - j >= 0 && yCart - j < 19 && papanAsli.getPiece(yCart - j, xCart + j).equals("O ")) {
				if (++cou == danger) {
					int coord[] = {xCart, yCart, cou, attMet};
					return defendDiagonalF(papanAsli, coord, attMet);
				}
			} 
			else cou = 0;
		}
		int[] coord = {0, 0, 0, attMet};
		return coord;
	}

	public int[] defendDiagonalF(Papan papanAsli, int[] coord, int attMet) {
		//System.out.println("Co[0]: " + coord[0] + " | Co[1]: " + coord[1] + " | Co[2]: " + coord[2]);
		if (coord[0]-coord[2] >= 0 && coord[1]-coord[2] >= 0 && papanAsli.getPiece(coord[1]+coord[2], coord[0]-coord[2]).equals(". ")) {
			coord[0] -= coord[2];
			coord[1] += coord[2];
			return coord;
		}
		else if (coord[0]+1 < 19 && coord[1]-1 >= 0 && papanAsli.getPiece(coord[1]-1, coord[0]+1).equals(". ")) {
			coord[0] += 1;
			coord[1] -= 1;
			return coord;
		}
		int[] doItRandom = {0, 0, 0, attMet};
		return doItRandom;
	}

	public int[] attackDiagonalF(Papan papanAsli, int yCart, int xCart, int attMet) {
		int[] coord = {xCart, yCart, 0, attMet};
		if (yCart == 0 && xCart == 0) {
			return coord;
		}
		else if (xCart + 3 < 19 && yCart - 3 >= 0 && papanAsli.getPiece(yCart, xCart+1).equals(". ") && !papanAsli.getPiece(yCart, xCart+3).equals("O ") && !papanAsli.getPiece(yCart, xCart+2).equals("O ") && yCart + 3 < 19 && papanAsli.getPiece(yCart+1, xCart).equals(". ") && !papanAsli.getPiece(yCart+3, xCart).equals("O ") && !papanAsli.getPiece(yCart+2, xCart).equals("O ")) {
			//System.out.println("attackDiagonalF 2ndBlock");
			coord[0] += 1;
			coord[1] -= 1;
			return coord;
		}
		else if (yCart + 3 < 19 && xCart - 3 >= 0 && papanAsli.getPiece(yCart, xCart-1).equals(". ") && !papanAsli.getPiece(yCart, xCart-3).equals("O ") && !papanAsli.getPiece(yCart, xCart-2).equals("O ") && yCart - 3 >= 0 && papanAsli.getPiece(yCart-1, xCart).equals(". ") && !papanAsli.getPiece(yCart-3, xCart).equals("O ") && !papanAsli.getPiece(yCart-2, xCart).equals("O ")) {
			//System.out.println("attackDiagonalF 3rdBlock");
			coord[0] -= 1;
			coord[1] += 1;
			return coord;
		}
		else {
			/*
			System.out.println("attackDiagonalF else for loop");
			for (int i = 1; i <= 4; i++) {
				if (xCart - i >= 0 && papanAsli.getPiece(yCart, xCart - i).equals(". ")) {
					coord[0] -= i;
					coord[0] -= i;
					return coord;
				}
			}*/

		}

		coord[0] = rand.nextInt(19);
		coord[1] = rand.nextInt(19);
		coord[3] = rand.nextInt(3) + 0;
		//System.out.println("Final end attackDiagonalF attMet: " + coord[3]);
		return coord;
	}

	public int[] attackHorizontal(Papan papanAsli, int yCart, int xCart, int attMet) {
		int[] coord = {xCart, yCart, 0, attMet};
		if (yCart == 0 && xCart == 0) {
			return coord;
		}
		else if (xCart + 3 < 19 && papanAsli.getPiece(yCart, xCart+1).equals(". ") && !papanAsli.getPiece(yCart, xCart+3).equals("O ") && !papanAsli.getPiece(yCart, xCart+2).equals("O ")) {
			//System.out.println("attackHorizontal 2ndBlock");
			coord[0] += 1;
			return coord;
		}
		else if (xCart - 3 >= 0 && papanAsli.getPiece(yCart, xCart-1).equals(". ") && !papanAsli.getPiece(yCart, xCart-3).equals("O ") && !papanAsli.getPiece(yCart, xCart-3).equals("O ")) {
			//System.out.println("attackHorizontal 3rdBlock");
			coord[0] -= 1;
			return coord;
		}
		else {
			/*
			System.out.println("attackHorizontal else for");
			for (int i = 1; i <= 4; i++) {
				if (xCart - i >= 0 && papanAsli.getPiece(yCart, xCart - i).equals(". ")) {
					coord[0] -= i;
					return coord;
				}
			}*/

		}
		coord[0] = rand.nextInt(19);
		coord[1] = rand.nextInt(19);
		coord[3] = rand.nextInt(3) + 0;
		//System.out.println("Final end attackVertical attMet: " + coord[3]);
		return coord;
	}

	public int[] testHorizontal(int danger, Papan papanAsli, int yCart, int xCart, int attMet) {
		for (int j = -4, cou = 0; j <= 4; j++) {
			if ( xCart + j >= 0 && xCart + j < 19 && papanAsli.getPiece(yCart, xCart + j).equals("O ")) {
				if (++cou == danger) {
					int coord[] = {xCart, yCart, cou, attMet};
					return defendHorizontal(papanAsli, coord, attMet);
				}
			} 
			else cou = 0;
		}
		int[] coord = {0, 0, 0, attMet};
		return coord;
	}

	public int[] defendHorizontal(Papan papanAsli, int[] coord, int attMet) {
		if (coord[0]-coord[2] >= 0 && papanAsli.getPiece(coord[1], coord[0]-coord[2]).equals(". ")) {
			coord[0] -= coord[2];
			return coord;
		}
		else if (coord[0]+1 < 19 && papanAsli.getPiece(coord[1], coord[0]+1).equals(". ")) {
			coord[0] += 1;
			return coord;
		}
		int[] doItRandom = {0, 0, 0, attMet};
		return doItRandom;
	}

	public int[] attackVertical(Papan papanAsli, int yCart, int xCart, int attMet) {
		int[] coord = {xCart, yCart, 0, attMet};
		if (yCart == 0 && xCart == 0) {
			return coord;
		}
		else if (yCart + 3 < 19 && papanAsli.getPiece(yCart+1, xCart).equals(". ") && !papanAsli.getPiece(yCart+3, xCart).equals("O ") && !papanAsli.getPiece(yCart+2, xCart).equals("O ")) {
			//System.out.println("attackVertical 2ndBlock");
			coord[1] += 1;
			return coord;
		}
		else if (yCart - 3 >= 0 && papanAsli.getPiece(yCart-1, xCart).equals(". ") && !papanAsli.getPiece(yCart-3, xCart).equals("O ") && !papanAsli.getPiece(yCart-2, xCart).equals("O ")) {
			coord[3] = rand.nextInt(3) + 0;
			//System.out.println("attackVertical 3rdBlock");
			coord[1] -= 1;
			return coord;
		}
		else {
			/*
			System.out.println("attackVertical else block");
			for (int i = 1; i <= 4; i++) {
				if (yCart - i >= 0 && papanAsli.getPiece(yCart - i, xCart).equals(". ")) {
					coord[1] -= i;
					return coord;
				}
			}*/
		}
		coord[0] = rand.nextInt(19);
		coord[1] = rand.nextInt(19);
		coord[3] = rand.nextInt(3) + 0;
		//System.out.println("Final end attackVertical attMet: " + coord[3]);
		return coord; 
	}

	public int[] testVertical(int danger, Papan papanAsli, int yCart, int xCart, int attMet) {
		for (int j = -4, cou = 0; j <= 4; j++) {
			if ( yCart + j >= 0 && yCart + j < 19 && papanAsli.getPiece(yCart + j, xCart).equals("O ")) {
				if (++cou == danger) {
					int coord[] = {xCart, yCart, cou, attMet};
					return defendVertical(papanAsli, coord, attMet);
				}
			} 
			else cou = 0;
		}
		int[] coord = {0, 0, 0, attMet};
		return coord;
	}

	public int[] defendVertical(Papan papanAsli, int[] coord, int attMet) {
		if (coord[1]-coord[2] >= 0 && papanAsli.getPiece(coord[1]-coord[2], coord[0]).equals(". ")) {
			coord[1] -= coord[2];
			return coord;
		}
		else if (coord[1]+1 < 19 && papanAsli.getPiece(coord[1]+1, coord[0]).equals(". ")) {
			coord[1] += 1;
			return coord;
		}
		int[] doItRandom = {0, 0, 0, attMet};
		return doItRandom;
	}

}
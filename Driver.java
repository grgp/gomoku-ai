/*
 * @nama: George Albert
 * @npm: 1406569781
 * @title: Tugas 3 - Gomoku | Driver
 *
 */

public class Driver {
	public static void main(String[] args) {
		
		Game game = new Game();				// karena tidak direkomendasikan dalam OOP untuk menggunakan static
		game.runGame();						// maka untuk Game-nya sendiri dijadikan sebuah objek

	}
}
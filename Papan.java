/*
 * @nama: George Albert
 * @npm: 1406569781
 * @title: Tugas 3 - Gomoku | Papan
 *
 */

public class Papan {

	private String[][] papan;
	private final int SIZE = 19;

	public Papan() {							
		papan = new String[SIZE][SIZE];			// membuat papan kosong

		for (int i = 0; i < SIZE; i++) {		// untuk baris2/rows
			for (int j = 0; j < SIZE; j++) {	// untuk kolom2/columns
				papan[i][j] = ". ";
			}
		}
	}

	//-------------------------------------------- tidak dipakai dalam implementasi GUI

	public void displayPapan() {				// menampilkan papan dengan iterasi array2D
		System.out.print("\n   ");
		for (int j = 0; j < SIZE; j++) {
			System.out.print((j < 10 ? "  " : "")+(j >= 10 ? "1 " : ""));
		}
		System.out.print("\n  ");
		for (int j = 0; j < SIZE; j++) {
			System.out.print(" "+(j+1)%10);		// menampilkan angka urutan kolom
		}
		System.out.println();
		for (int i = 0; i < SIZE; i++) {
			System.out.printf("%2d ", i+1);		// menampilkan angka urutan baris
			for (int j = 0; j < SIZE; j++) {
				System.out.print(papan[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	public String getPiece(int i, int j) {
		return papan[i][j];						// return pion karena papan diset private
	}

	public void setPiece(int i, int j, String player) {
		papan[i][j] = player;					// set pion karena papan diset private
	}

	public int getSize() {
		return SIZE;							// return size dari papan
	}
	
}
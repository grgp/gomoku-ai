/*
 * @nama: George Albert
 * @npm: 1406569781
 * @title: Tugas 3 - Gomoku | PapanGame
 *
 */

import java.awt.Color;						// diperlukan untuk pengunaan warna
import java.awt.Dimension;					// diperlukan untuk mengatur resolusi
import java.awt.Graphics;					// diperlukan untuk menampilkan elemen
import javax.swing.JPanel;					// diperlukan sebagai komponen utama GUI

public class PapanGame extends JPanel {

	private final int UNIT_SIZE = 30;		// ukuran panjang dan lebar dari tiap tile/kotak
	private Papan papan;					// variabel untuk objek papan yang akan dijadikan referensi
	
	public PapanGame(Papan papan) {
		this.papan = papan;					// papan disini diambil dari input parameter dari class Game
		Dimension d = new Dimension(papan.getSize()*UNIT_SIZE+1, papan.getSize()*UNIT_SIZE+1);	// dimensi dari window
		this.setMinimumSize(d);				// ukuran minimum dan preferred dari frame
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);	// sementara karena akan diganti selanjutnya
	}

	public int[] getGridPosition(int x, int y) {
		int gridPos[] = new int[2];			// array untuk menyimpan koordinat
		gridPos[0] = x / UNIT_SIZE;			// koordinat papan dalam pixel dibagi dengan ukuran kotak akan mereturn
		gridPos[1] = y / UNIT_SIZE;			// koordinat papan dalam hitungan tile, karena integer maka akan pembulatan kebawah
		return gridPos;
	}

	public void paintComponent(Graphics g) {// method yang akan mendisplay dan mengatur display dari papan Gomoku beserta pionnya
		
		super.paintComponent(g);

		for (int i = 0; i < papan.getSize(); i++) {
			for (int j = 0; j < papan.getSize(); j++) {
				
				if ((j+i) % 2 == 0) {											// sehingga pewarnaan dari kotak selang seling
					g.setColor(new Color(108, 142, 66));						// menset warna dari kotak
					g.drawRect(i*UNIT_SIZE, j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);	// menggambar kotak, dgn parameter koordinat dan ukurannya
					g.fillRect(i*UNIT_SIZE, j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);	// mengisi kotak dengan warna tersebut
				} else {
					g.setColor(new Color(134, 172, 70));
					g.drawRect(i*UNIT_SIZE, j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
					g.fillRect(i*UNIT_SIZE, j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				}
				
				g.setColor(Color.BLACK);										// antisipasi, mengembalikan ke warna default
				
				if (papan.getPiece(j, i).equals("X ")) {						// pion putih dilambangkan sebagai X
					g.setColor(new Color(243, 236, 216));
					g.fillOval(i*UNIT_SIZE, j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);	// mengisi lingkaran tersebut untuk membuat pion putih
				} else if (papan.getPiece(j, i).equals("O ")) {					// pion hitam dilambangkan sebagai O
					g.setColor(new Color(40, 44, 45));
					g.fillOval(i*UNIT_SIZE, j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE); // mengisi lingkaran tersebut untuk membuat pion hitam
				}
			}
		}
	}

}

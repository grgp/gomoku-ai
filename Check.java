/*
 * @nama: George Albert
 * @npm: 1406569781
 * @title: Tugas 3 - Gomoku | Check
 *
 */

public class Check {
	
	// jika ada yang menang maka akan mereturn true, dimana di class Game akan menjadikan gameover true
	public boolean checkPlayer(Papan papanAsli, int yCart, int xCart, boolean playerOsTurn) {
		if (playerOsTurn) {
			if (checkAll("O ", papanAsli, yCart, xCart)) {
				//System.out.println("O wins!");
				return true;
			}
		}
		else {
			if (checkAll("X ", papanAsli, yCart, xCart)) {
				//System.out.println("X wins!");
				return true;
			}
		}
		return false;
	}

	// jika salah satu benar, berarti pasti sudah ada yang menang
	// method dibawah melakukan pengecekan ke semua arah
	public boolean checkAll(String player, Papan papanAsli, int yCart, int xCart) {
		if ( checkHorizontal(player, papanAsli, yCart, xCart) || checkVertical(player, papanAsli, yCart, xCart) || checkDiagonalF(player, papanAsli, yCart, xCart) || checkDiagonalB(player, papanAsli, yCart, xCart) )
			return true;
		else return false;
	}

	// mencek 4 kotak dari kiri dan kanan lokasi terakhir piece ditaruh untuk efisiensi pengecekan
	// setiap kali piece yang di cek adalah sama secara berurutan, maka variabel sementara cou akan diincrement
	// jika piece yang dicek berganti berarti urutannya terpotong, sehingga variabel cou kembali menghitung dari 0
	public boolean checkHorizontal(String player, Papan papanAsli, int yCart, int xCart) {
		for (int j = -4, cou = 0; j <= 4; j++) {
			if ( xCart + j >= 0 && xCart + j < 19 && papanAsli.getPiece(yCart, xCart + j).equals(player)) {
				if (++cou >= 5)
					return true;
			} 
			else cou = 0;
		}
		return false;
	}

	public boolean checkVertical(String player, Papan papanAsli, int yCart, int xCart) {
		for (int i = -4, cou = 0; i <= 4; i++) {
			if ( yCart + i >= 0 && yCart + i < 19 && papanAsli.getPiece(yCart + i, xCart).equals(player)) {
				if (++cou >= 5)
					return true;
			} 
			else cou = 0;
		}
		return false;
	}

	public boolean checkDiagonalB(String player, Papan papanAsli, int yCart, int xCart) {
		for (int i = -4, cou = 0; i <= 4; i++) {
			if ( yCart + i >= 0 && yCart + i < 19 && xCart + i >= 0 && xCart + i < 19 && papanAsli.getPiece(yCart + i, xCart + i).equals(player)) {
				if (++cou >= 5)
					return true;
			} 
			else cou = 0;
		}
		return false;
	}

	public boolean checkDiagonalF(String player, Papan papanAsli, int yCart, int xCart) {
		for (int i = -4, cou = 0; i <= 4; i++) {
			if ( yCart - i >= 0 && yCart - i < 19 && xCart + i >= 0 && xCart + i < 19 && papanAsli.getPiece(yCart - i, xCart + i).equals(player)) {
				if (++cou >= 5)
					return true;
			} 
			else cou = 0;
		}
		return false;
	}

} 
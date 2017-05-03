/*
 * @nama: George Albert
 * @npm: 1406569781
 * @title: Tugas 3 - Gomoku | Game
 *
 */

import java.util.*;							// diperlukan untuk exception dsb.
import javax.swing.*;						// diperlukan sebagai komponen utama GUI
import java.awt.*;							// diperlukan sebagai komponen utama GUI
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

public class Game {

	//---------------------------------------- base variables
	private int row;						// variabel untuk menentukan baris
	private int col;						// variabel untuk menentukan kolom

	private boolean playerOsTurn;			// player hanya direpresentasikan dengan boolean, dimana bergantung nilai boolean ini
											// bagian program akan dijalankan dengan beda

	private boolean gameover;				// gameover secara default tidak true dan baru akan true ketika checks.checkPlayer...
											// bernilai true, yang berarti pemenang sudah didapatkan

	private Papan papans;					// objek Papan adalah objek uyanma yang akan dimanipulasi di program
	private Check checks;					// objek Check digunakan untuk mengetahui apakah sudah dapat ditentukan pemenang

	//---------------------------------------- ai variables
	private ArtificialIntelligence ai;		// objek ArtificialIntelligence masih bersifat percobaan dan belum sempurna
	private boolean aiEnabled;				// ditentukan diawal program berjalan, jika true maka AI akan dijalankan

	private int osLastXCart;				// menyimpan koordinat X dari player "O" (sulit untuk menggunakan warna untuk menidentifikasi pemain)
	private int osLastYCart;				// menyimpan koordinat Y dari player "O"
	private int xsLastXCart;				// menyimpan koordinat X dari player "X"
	private int xsLastYCart;				// menyimpan koordinat Y dari player "X"
	private int attMet;						// singkatan dari attack method, variabel untuk "mengingatkan" bagaimana AI akan menyerang
											// terakhir sehingga gerakannya konsisten

	//---------------------------------------- gui variables
	private PapanGame papanGame;			// objek untuk papan Gomoku
	private JFrame f;						// jFrame yang akan mengandung semua elemen
	private JPanel mainPanel;				// mainPanel yang akan menampung objek papanGame
	private JPanel contentPane;				// contentPane jPanel yang menampung semua component
	private int[] gridPos;					// untuk koordinat kotak yang diklik
	private JLabel lblFivePiecesIn;			// label yang pada akhirnya akan mendeklarasi jika player menang

	public Game() {
		papans = new Papan();				// objek Papan akan digunakan untuk menyimpan lokasi tiap pion/piece
		checks = new Check();				// objek Check berisi2 method2 untuk mengecek apabila ada pemain yang menang
		playerOsTurn = true;				// defaultnya semua pemain pertama adalah pemain "O", atau menggunakan pion hitam

		ai = new ArtificialIntelligence();
		Random rand = new Random();
		attMet = rand.nextInt(3) + 0;

		papanGame 	= new PapanGame(papans);// papanGame mengextend JPanel, dan merupakan implementasi GUI dari papan
		f 			= new JFrame("Gomoku");	// f adalah JFrame utama dari program Gomoku ini
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.getContentPane().add(new JLabel("Gomoku Borderless"));	// label ini tidak terlihat karena program tidak mempunyai border
	    f.setUndecorated(true);										// method ini membuat JFrame tidak mempunyai window border
		f.setVisible(true);											// frame dibuat visible
		f.setSize(new Dimension(1120, 630));						// dimensi ukuran frame, yaitu 1120x630 pixels
		f.setLocationRelativeTo(null);								// karena tidak ada window border, maka frame tidak bisa didrag
																	// method diatas menset lokasi frame terpusat di layar

		mainPanel = new JPanel();									// mainPanel adalah panel yang menampung papanGame
		mainPanel.setBackground(new Color(90, 119, 55));
		mainPanel.add(papanGame);
		mainPanel.setBounds(500, 24, 610, 610);

		contentPane = new JPanel();									// contentPane adalah panel yang mencakup keseluruhan window
		contentPane.setBackground(new Color(90, 119, 55));
		f.setContentPane(contentPane);
		contentPane.setLayout(null);								// disebut juga sebagai AbsoluteLayout, yang tidak menggunakan
																	// template tertentu namun berdasarkan koordinat langsung

		// label ini sebenarnya memiliki fungsi layaknya button
		// dipilih label karena tampilannya yang lebih flat dan
		// minimalis daripada JButton
		JLabel lblPlayWithHuman = new JLabel("play with human player");
		JLabel lblPlayWithAi = new JLabel("play with computer\r\n");

		lblPlayWithHuman.addMouseListener(new MouseAdapter() {		// adanya MouseListener membuat dapat merespons input mouse jika diklik
			public void mouseClicked(MouseEvent e) {
				setAIEnabled(false);								// memanggil method yg akan membuat boolean aiEnabled menjadi false
																	// karena jika assignment langsung dilakukan disini bermasalah
				lblPlayWithHuman.setVisible(false);
				lblPlayWithAi.setVisible(false);
				contentPane.add(mainPanel);							// memasukkan mainPanel yang berisi papan ke dalam frame
			}
		});
		lblPlayWithHuman.setHorizontalAlignment(SwingConstants.CENTER);	// alignment text menjadi center
		lblPlayWithHuman.setForeground(new Color(243, 236, 216));		// menentukan warna text
		lblPlayWithHuman.setFont(new Font("Arial", Font.PLAIN, 18));	// menentukan font-style dan ukuran
		lblPlayWithHuman.setBounds(686, 322-70, 231, 61);				// menentukan lokasi dan batas-batas dari area label
		contentPane.add(lblPlayWithHuman);								// add label ke dalam

		lblPlayWithAi.addMouseListener(new MouseAdapter() {				// serupa dengan JLabel yang diatas
			public void mouseClicked(MouseEvent e) {
				setAIEnabled(true);
				lblPlayWithAi.setVisible(false);
				lblPlayWithHuman.setVisible(false);
				contentPane.add(mainPanel);
			}
		});
		lblPlayWithAi.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayWithAi.setForeground(new Color(243, 236, 216));
		lblPlayWithAi.setFont(new Font("Arial", Font.PLAIN, 18));
		lblPlayWithAi.setBounds(686, 389-70, 231, 61);
		contentPane.add(lblPlayWithAi);

		JLabel lblGomoku = new JLabel("Gomoku");						// serupa dengan JLabel diatas, namun hanya untuk
		lblGomoku.setForeground(new Color(243, 236, 216));
		lblGomoku.setFont(new Font("Arial", Font.PLAIN, 82));
		lblGomoku.setBounds(69, 150, 318, 129);
		contentPane.add(lblGomoku);

		lblFivePiecesIn = new JLabel("Five pieces in a row to win");	// serupa dengan JLabel diatas juga, namun label ini
		lblFivePiecesIn.setForeground(new Color(243, 236, 216));		// akan berubah dalam beberapa situasi, contoh untuk
		lblFivePiecesIn.setFont(new Font("Arial", Font.PLAIN, 18));		// peringatan jika pemain klik ke kotak yang sudah ada
		lblFivePiecesIn.setBounds(75, 265, 328, 41);					// pionnya, ataupun untuk deklarasi pemenang
		contentPane.add(lblFivePiecesIn);

		JLabel lblQuit = new JLabel("quit");							// serupa dengan JLabel diatas, namun digunakan untuk
		lblQuit.addMouseListener(new MouseAdapter() {					// quit program
			@Override
			public void mouseClicked(MouseEvent arg0) {
				f.dispose();											// menghilangkan frame
				System.exit(0);											// menghentikan program secara keseluruhan
			}
		});
		lblQuit.setForeground(new Color(243, 236, 216));
		lblQuit.setFont(new Font("Arial", Font.PLAIN, 18));
		lblQuit.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuit.setBounds(222, 412, 71, 28);
		contentPane.add(lblQuit);

	}

	public void runGame() {
		getInput(this.playerOsTurn);									// pada kode tugas 2 method ini adalah method rekursif
																		// untuk tugas 3 fungsi rekursif sudah tergantikan oleh
																		// sifat MouseListener yang selalu aktif, sekarang method
																		// ini digunakan hanya agar bukan class Driver yang
																		// menentukan player default
	}

	public void getInput(boolean playerOsTurn) {
		papanGame.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {

				if (!getGameover()) {											// jika game masih berjalan, block ini akan terus
					lblFivePiecesIn.setText("Five pieces in a row to win");		// mereset label ini ke kalimat ini karena ada kasus
					lblFivePiecesIn.setForeground(new Color(243, 236, 216));	// dimana label ini akan berubah
				}

				// block ini adalah yang akan selalu dijalankan jika pemain memilih untuk bermain melawan komputer
				if (getAIEnabled()) {

					// jika player adalah player "O", atau pion hitam, maka player adalah user dan boolean akan true
					if (getPlayer()) {

						// array ini akan menyimpan kotak yang diklik player
						gridPos = papanGame.getGridPosition(e.getX(), e.getY());

						// melakukan repaint ke papan
						update();

						// nilai koordinat tersebut akan dimasukan ke variabelnya masing-masing
						int xCart = gridPos[0];
						int yCart = gridPos[1];

						// checkInput(y,x) mencek apakah input tersebut sudah valid
						if (checkInput(yCart, xCart)) {

							osLastXCart = xCart;					// koordinat terakhir yang dijalankan user akan
							osLastYCart = yCart;					// diingat untuk pertimbangan langkah AI selanjutnya
							addToBoard(yCart, xCart, playerOsTurn);	// memasukan pion ke papan yang berupa array dua dimensi

								// block untuk check apabila salah seorang pemain sudah menang
								if (checks.checkPlayer(papans, yCart, xCart, playerOsTurn)) {
									lblFivePiecesIn.setText((playerOsTurn ? "Black " : "White ") + "wins the game!");
									setGameover();					// assignment ke variable bermasalah jika langsung tanpa method
								}

							changePlayer();							// jika input sudah berhasil giliran player selanjutnya
							AIRun();								// AIRun akan menjalankan AI di gilirannya
						} else changePlayer();						// jika input tidak valid maka akan dikembalikan ke player terakhir
					} else AIRun();									// jika player bukan user, AI akan berjalan

					if (getGameover()) stopGame();					// jika sudah ada yang menang game dihentikan

				}

				else {

					// kode dibawah serupa dengan kode diatas, dengan beda AIRun() tidak dijalankan
					// karena block ini untuk permainan 2 orang user

					try {
					gridPos = papanGame.getGridPosition(e.getX(), e.getY());
					} catch (NullPointerException ex) {}

					int xCart = gridPos[0];
					int yCart = gridPos[1];

					if (checkInput(yCart, xCart)) {
						addToBoard(yCart, xCart, getPlayer());
						update();
						if (checks.checkPlayer(papans, yCart, xCart, getPlayer())) {
							lblFivePiecesIn.setText((getPlayer() ? "Black " : "White ") + "wins the game!");
							setGameover();
						}
					}

					if (getGameover()) stopGame();
					changePlayer();
				}
			}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
		});
	}

	public void AIRun() {
		// melakukan repaint ke papan
		update();

		// array ini akan mereceive perhitungan dari class ArtificialIntelligence berupa koordinat gerakan
		// selanjutnya, beserta variable attMet atau attackMethod yang mengingat cara terakhir AI menyerang
		int[] receive = ai.begin(papans, osLastYCart, osLastXCart, xsLastYCart, xsLastXCart, attMet);
		int xCart = receive[0];
		int yCart = receive[1];

		// koordinat AI juga akan disimpan untuk pertimbangan gerakan AI selanjutnya
		xsLastXCart = xCart;
		xsLastYCart = yCart;
		attMet = receive[3];

		// serupa dengan block-block diatas, karena ada kemungkinan pula AI melakukan kesalahan, sehingga input
		// -nya juga tetap harus dilakukan validasi

		// random wait
		// Random rand = new Random();
		// try {
		// 	TimeUnit.SECONDS.sleep(rand.nextInt(2)+1);
		// } catch (Exception ex) {}

		if (checkInput(yCart, xCart)) {
			addToBoard(yCart, xCart, playerOsTurn);
			if (checks.checkPlayer(papans, yCart, xCart, playerOsTurn)) {
				setGameover();
				lblFivePiecesIn.setText((playerOsTurn ? "Black " : "White ") + "wins the game!");
			}
			changePlayer();
		} else AIRun();
	}

	public boolean checkInput(int yCart, int xCart) {
		if (row < 0 || row > 19 || col < 0 || col > 19);					// sisa dari kode tugas 2, yang validasi
																			// apa input masih dalam board, dibiarkan
																			// untuk antisipasi AI salah dalam input

		else if (!papans.getPiece(yCart,xCart).equals(". ")) {				// jika sudah ada pion didalam kotak
			if (!getGameover()) {
				lblFivePiecesIn.setText("There's already a piece there");	// jika game masih berjalan label ke-2 akan
				lblFivePiecesIn.setForeground(new Color(227, 196, 149));	// sementara mengeluarkan pesan
				playerOsTurn = !playerOsTurn;								// giliran player dikembalikan ke yang terakhir
			}
		}
		else {
			return true;													// jika tidak ada masalah method mengeluarkan true
		}
		return false;														// jika tidak tercapai return true maka return false
	}

	public void addToBoard(int row, int col, boolean playerOsTurn) {		// menambahkan pion ke dalam papan tergantung player
		if (playerOsTurn)
			papans.setPiece(row, col, "O ");
		else
			papans.setPiece(row, col, "X ");
	}

	public void update() {
		f.repaint();														// repaint keseluruhan frame
	}

	public void changePlayer() {
		this.playerOsTurn = !playerOsTurn;									// menganti pemain, diperlukan karena tidak dapat dilakukan
																			// langsung pada beberapa situasi
		update();
	}

	public boolean getPlayer() {
		return playerOsTurn;												// mereturn player yang sedang aktif dalam bentuk boolean
	}

	public void setGameover() {
		gameover = true;													// dipanggil setelah ada player yang menang
	}

	public boolean getGameover() {
		return gameover;													// dapat dipanggil untuk memastikan apa game masih berjalan/tidak
	}

	public void stopGame() {
		papanGame = null;													// sehingga papan tidak dapat diubah lagi setelah game selesai
	}

	public boolean getAIEnabled() {											// return apakah game melawan AI atau tidak dalam bentuk boolean
		return aiEnabled;
	}

	public void setAIEnabled(boolean aiEnabled) {							// set apakah game melawan AI atau tidak
		this.aiEnabled = aiEnabled;
	}

}

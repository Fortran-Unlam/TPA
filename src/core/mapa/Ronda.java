package core.mapa;

import java.awt.Color;
import java.awt.HeadlessException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import config.Param;
import core.Score;
import input.GestorInput;
import javax.swing.JList;
import java.awt.Font;
import java.awt.SystemColor;

public class Ronda extends JFrame {

	private static final long serialVersionUID = 48427270007064034L;

	private JPanel contentPane;

	private Mapa mapa;
	private JLabel lblScore;
	private JLabel lblVib;
	private JLabel lblFrutas;

	public Ronda() throws HeadlessException {
		super("Snake");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, Param.VENTANA_WIDTH, Param.VENTANA_HEIGHT);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(null);
		setUndecorated(true);
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setBackground(Color.black);
		contentPane.setLayout(null);

		mapa = new MapaRandom();
		getContentPane().add(mapa);
		
		lblScore = new JLabel("SCORE");
		lblScore.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblScore.setBounds(16, 7, 67, 21);
		contentPane.add(lblScore);
		
		JList<String> list = new JList<String>();
		list.setBackground(SystemColor.control);
		list.setBorder(null);
		list.setBounds(10, 54, 87, 262);
		list.setOpaque(false);
		list.setEnabled(false);
		
		contentPane.add(list);
		
		lblVib = new JLabel("Vib");
		lblVib.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblVib.setBounds(10, 39, 22, 14);
		contentPane.add(lblVib);
		
		lblFrutas = new JLabel("Frutas");
		lblFrutas.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFrutas.setBounds(51, 35, 42, 21);
		contentPane.add(lblFrutas);

		addKeyListener(GestorInput.teclado);
		setVisible(true);

		while (true) {
			mapa.actualizar();
			list.setModel(Score.ScoreToModel(mapa.getScore()));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

//	public void paint(Graphics g) {
//		super.paint(g);
//		Graphics2D g2d = (Graphics2D) g;
//		g2d.setColor(Color.DARK_GRAY);
//		g2d.fillRect(0, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);
//	}
}

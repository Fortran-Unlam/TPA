package core.mapa;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import config.Param;
import core.Score;
import input.GestorInput;

public class GestorMapa extends JFrame {

	private static final long serialVersionUID = 48427270007064034L;

	private JPanel contentPane;

	private Mapa mapa;
	private JLabel lblScore;
	private JLabel lblVib;
	private JLabel lblFrutas;

	public GestorMapa() throws HeadlessException {
		super("Snake");

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(null);
		contentPane.setLayout(null);
		setContentPane(contentPane);

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

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, Param.VENTANA_WIDTH, Param.VENTANA_HEIGHT);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setBackground(Color.black);
		addKeyListener(GestorInput.teclado);
		setVisible(true);

		while (true) {
			mapa.actualizar();
			//puntaje.calc();
			list.setModel(Score.ScoreToModel(mapa.getScore()));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import config.Param;
import config.Posicion;
import input.GestorInput;

public class GestorMapa extends JFrame {

	private static final long serialVersionUID = 48427270007064034L;

	private JPanel contentPane;

	private Mapa mapa;

	public GestorMapa() throws HeadlessException {
		super("Snake");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);

		contentPane = new JPanel();
		contentPane.setBorder(null);
		setUndecorated(true);
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setBackground(Color.black);
		contentPane.setLayout(null);

		mapa = new Mapa(Param.MAPA_WIDTH / 5, Param.MAPA_HEIGHT / 5);
		mapa.setBounds(0, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);
		mapa.add(new Fruta(1, 1));
		mapa.add(new Fruta(58, 1));
		Random a = new Random();
		for (int i = 0; i < 25; i++) {
			mapa.add(new Fruta(a.nextInt(Param.MAPA_WIDTH / 5), a.nextInt(Param.MAPA_HEIGHT / 5)));
		}

		// Agrego obstaculos
		Random b = new Random();
		for (int i = 0; i < 10; i++) {
			mapa.add(new Obstaculo(b.nextInt(Param.MAPA_WIDTH / 5), b.nextInt(Param.MAPA_HEIGHT / 5)));
		}

		Vibora vibora = new Vibora(new Coordenada(30, 20), 10, Posicion.ESTE);
		mapa.add(vibora);
		
		ViboraBot viboraBot = new ViboraBot(new Coordenada(50, 50));
		mapa.add(viboraBot);
		
		getContentPane().add(mapa);

		addKeyListener(GestorInput.teclado);
		setVisible(true);

		while (true) {
			mapa.actualizar();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
	}

}

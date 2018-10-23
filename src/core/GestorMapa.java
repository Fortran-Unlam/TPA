package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.util.LinkedList;
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
		setBounds(0, 0, Param.VENTANA_WIDTH, Param.VENTANA_HEIGHT);

		contentPane = new JPanel();
		contentPane.setBorder(null);
		setUndecorated(true);
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setBackground(Color.black);
		contentPane.setLayout(null);

		mapa = new Mapa(Param.MAPA_WIDTH / 5, Param.MAPA_HEIGHT / 5);
		mapa.setBounds(Param.VENTANA_WIDTH - Param.MAPA_WIDTH, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);

		Random random = new Random();
		for (int i = 0; i < 25; i++) {
			mapa.add(new Fruta(random.nextInt(Param.MAPA_WIDTH / 5), random.nextInt(Param.MAPA_HEIGHT / 5)));
		}

		// Agrego obstaculos
		for (int i = 0; i < 10; i++) {
			mapa.add(new Obstaculo(random.nextInt(Param.MAPA_WIDTH / 5), random.nextInt(Param.MAPA_HEIGHT / 5)));
		}

		// Agrego muro
		LinkedList<Obstaculo> piedras = new LinkedList<>();
		for (int i = 30; i <= 160; i++) {
			piedras.add(new Obstaculo(new Coordenada(i, 50)));
		}
		Muro pared = new Muro(piedras);
		mapa.add(pared);

		Vibora vibora = new Vibora(new Coordenada(30, 20), 10, Posicion.ESTE);
		mapa.add(vibora);

		ViboraBot viboraBot = new ViboraBot(new Coordenada(50, 100));
		mapa.add(viboraBot);

		ViboraBot viboraBot2 = new ViboraBot(new Coordenada(100, 70));
		mapa.add(viboraBot2);

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
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);
	}

}

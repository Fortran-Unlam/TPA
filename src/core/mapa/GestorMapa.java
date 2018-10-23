package core.mapa;

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
import core.Coordenada;
import core.Fruta;
import core.Muro;
import core.Obstaculo;
import core.Vibora;
import core.ViboraBot;
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

		mapa = new MapaRandom();
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

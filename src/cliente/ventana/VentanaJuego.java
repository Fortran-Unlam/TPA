package cliente.ventana;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import cliente.Main;
import cliente.Sonido;
import cliente.input.GestorInput;
import config.Param;
import core.Jugador;
import core.JugadorBot;
import core.Puntaje;
import core.entidad.CuerpoVibora;
import core.entidad.Fruta;
import core.entidad.Obstaculo;
import core.mapa.Juego;
import core.mapa.Mapa;

public class VentanaJuego extends JFrame {

	private static final long serialVersionUID = 595619242686756871L;

	private JPanel contentPane;

	public Mapa mapa;
	private JLabel lblScore;
	private JLabel lblVib;
	private JLabel lblFrutas;

	private JList<String> jListScore;

	private JButton button;
	private Juego juego;
	private JPanel panelMapa;
	private VentanaJuego ventanaJuego = this;

	public VentanaJuego(Juego juego) {
		super("Snake");
//		this.juego = juego;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, Param.VENTANA_JUEGO_WIDTH, Param.VENTANA_JUEGO_HEIGHT);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(null);
		this.setUndecorated(true);
		this.setContentPane(contentPane);
		this.setLocationRelativeTo(null);
//		setBackground(Color.black);
		contentPane.setLayout(null);

		lblScore = new JLabel("SCORE");
		lblScore.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblScore.setBounds(16, 7, 67, 21);
		contentPane.add(lblScore);

		jListScore = new JList<String>();
		jListScore.setBackground(SystemColor.control);
		jListScore.setBorder(null);
		jListScore.setBounds(10, 54, 87, 262);
		jListScore.setOpaque(false);
		jListScore.setEnabled(false);

		contentPane.add(jListScore);

		lblVib = new JLabel("Vib");
		lblVib.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblVib.setBounds(10, 39, 22, 14);
		contentPane.add(lblVib);

		lblFrutas = new JLabel("Frutas");
		lblFrutas.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFrutas.setBounds(51, 35, 42, 21);
		this.contentPane.add(lblFrutas);

		JButton btnNewButton = new JButton("Stop");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// stop();
			}
		});
		btnNewButton.setBounds(0, 312, 100, 25);
		this.contentPane.add(btnNewButton);

		this.button = new JButton("Exit");
		this.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		button.setBounds(0, 350, 100, 25);
		this.contentPane.add(button);

		this.panelMapa = new JPanel();
		this.panelMapa.setBounds(Param.VENTANA_JUEGO_WIDTH - Param.MAPA_WIDTH, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);
		this.contentPane.add(panelMapa);
		this.addKeyListener(new GestorInput().teclado);
		this.setFocusable(true);
		this.setVisible(true);

		Thread thread = new Thread() {
			public synchronized void run() {
				Main.getConexionServidor().recibirMapa(ventanaJuego);
			}
		};
		thread.start();
		
		Sonido musicaFondo = new Sonido(Param.MUSICA_FONDO_PATH);
		musicaFondo.repetir();
	}

	public void dibujarMapa(Mapa mapa) {
		this.mapa = mapa;
		BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();

		if (this.mapa != null) {
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);

			for (Fruta fruta : this.mapa.getfrutas()) {
				g2d.setColor(Color.RED);
				g2d.fillRect(fruta.getX() * Param.PIXEL_RESIZE, fruta.getY() * Param.PIXEL_RESIZE, Param.PIXEL_RESIZE,
						Param.PIXEL_RESIZE);
			}

			for (Jugador jugador : this.mapa.getJugadores()) {
				g2d.setColor(Color.YELLOW);
				g2d.fillRect(jugador.getVibora().getX() * Param.PIXEL_RESIZE,
						jugador.getVibora().getY() * Param.PIXEL_RESIZE, Param.PIXEL_RESIZE, Param.PIXEL_RESIZE);

				g2d.setColor(Color.BLUE);
				if (jugador instanceof JugadorBot) {
					g2d.setColor(Color.GREEN);					
				}
				for (CuerpoVibora cuerpoVibora : jugador.getVibora().getCuerpos()) {
					g2d.fillRect(cuerpoVibora.getX() * Param.PIXEL_RESIZE, cuerpoVibora.getY() * Param.PIXEL_RESIZE,
							Param.PIXEL_RESIZE, Param.PIXEL_RESIZE);
				}
			}

			for (Obstaculo obstaculo : this.mapa.getObstaculos()) {
				g2d.setColor(Color.WHITE);
				g2d.fillRect(obstaculo.getX() * 5, obstaculo.getY() * 5, 5, 5);
			}

			this.panelMapa.getGraphics().drawImage(bufferedImage, 0, 0, null);

			ArrayList<Puntaje> score = this.mapa.getScore();
			score.sort(null);
			String[] listModel = new String[score.size()];
			for (int i = 0; i < score.size(); i++) {
				listModel[i] = score.get(i).toString();
			}
			jListScore.setListData(listModel);
		}
	}
}

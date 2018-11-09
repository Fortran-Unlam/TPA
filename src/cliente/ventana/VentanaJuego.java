package cliente.ventana;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.StringReader;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import cliente.Cliente;
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

	private JLabel lblScore;
	private JLabel lblVib;
	private JLabel lblFrutas;

	private JList<String> jListScore;

	private JButton btnSalirJuego;
	private JPanel panelMapa;
	private VentanaJuego ventanaJuego = this;

	private Sonido musicaFondo;

	private JButton btnNewButton;

	public VentanaJuego(Juego juego) {
		super("Snake");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, Param.VENTANA_JUEGO_WIDTH, Param.VENTANA_JUEGO_HEIGHT);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(null);
		this.setUndecorated(true);
		this.setContentPane(contentPane);
		this.setLocationRelativeTo(null);
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

		btnNewButton = new JButton("Pausa");
		btnNewButton.setBounds(0, 312, 100, 25);
		this.contentPane.add(btnNewButton);

		this.btnSalirJuego = new JButton("Salir juego");
		btnSalirJuego.setBounds(0, 350, 100, 25);
		this.contentPane.add(btnSalirJuego);

		this.panelMapa = new JPanel();
		this.panelMapa.setBounds(Param.VENTANA_JUEGO_WIDTH - Param.MAPA_WIDTH, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);
		this.contentPane.add(panelMapa);
		this.setFocusable(true);
		this.setVisible(true);

		Thread thread = new Thread() {
			public synchronized void run() {
				Cliente.getConexionServidor().recibirMapa(ventanaJuego);
			}
		};
		thread.start();

		addListener();

		musicaFondo = new Sonido(Param.MUSICA_FONDO_PATH);
		musicaFondo.repetir();
	}

	public void dibujarMapa(Juego juego) {

		Mapa mapa = juego.getMapa();
		BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();

		if (mapa != null) {
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);

			for (Fruta fruta : mapa.getfrutas()) {
				g2d.setColor(Color.RED);
				g2d.fillRect(fruta.getX() * Param.PIXEL_RESIZE, fruta.getY() * Param.PIXEL_RESIZE, Param.PIXEL_RESIZE,
						Param.PIXEL_RESIZE);
			}

			for (Jugador jugador : mapa.getJugadores()) {
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

			for (Obstaculo obstaculo : mapa.getObstaculos()) {
				g2d.setColor(Color.WHITE);
				g2d.fillRect(obstaculo.getX() * 5, obstaculo.getY() * 5, 5, 5);
			}

			ArrayList<Puntaje> score = mapa.scoring();

			String[] listModel = new String[score.size()];
			for (int i = 0; i < score.size(); i++) {
				listModel[i] = score.get(i).toString();
			}
			jListScore.setListData(listModel);
		}

		g2d.setColor(Color.WHITE);
		g2d.drawString(String.valueOf(juego.getSegundosTranscurridos()), Param.MAPA_WIDTH - 30, 30);

		if (juego.terminado()) {
			g2d.setColor(Color.WHITE);
			g2d.drawString("Juego terminado", (Param.MAPA_WIDTH / 2) - 100, Param.MAPA_HEIGHT / 2);
			musicaFondo.stop();
		}
		this.panelMapa.getGraphics().drawImage(bufferedImage, 0, 0, null);

		if (juego.getMapa().getMurioUnJugador()) {
			new Sonido(Param.GOLPE_PATH).reproducir();
		}
	}

	public void dibujarMapaJson(String jsonString) {
		System.err.println(jsonString);
		JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
		JsonObject json = jsonReader.readObject();
		jsonReader.close();

		System.out.println(json);
		BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();

		JsonObject mapa = json.getJsonObject("mapa");
		if (!mapa.isEmpty()) {
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);

			JsonArray frutas = mapa.getJsonArray("frutas");
			System.out.println(frutas);
			System.out.println(frutas.getJsonObject(0));
			for (int i = 0; i < frutas.size(); i++) {
				g2d.setColor(Color.RED);
				g2d.fillRect(frutas.getJsonObject(i).getInt("x") * Param.PIXEL_RESIZE,
						frutas.getJsonObject(i).getInt("y") * Param.PIXEL_RESIZE, Param.PIXEL_RESIZE,
						Param.PIXEL_RESIZE);
			}

			JsonArray jugadores = mapa.getJsonArray("jugadores");

			for (int i = 0; i < jugadores.size(); i++) {
				g2d.setColor(Color.RED);
				JsonObject vibora = jugadores.getJsonObject(i).getJsonObject("vibora");
				g2d.setColor(Color.YELLOW);
				g2d.fillRect(vibora.getInt("x") * Param.PIXEL_RESIZE, vibora.getInt("y") * Param.PIXEL_RESIZE,
						Param.PIXEL_RESIZE, Param.PIXEL_RESIZE);

				g2d.setColor(Color.BLUE);
				if (vibora.getBoolean("bot")) {
					g2d.setColor(Color.GREEN);
				}

				JsonArray cuerpo = vibora.getJsonArray("cuerpo");

				for (int j = 0; j < cuerpo.size(); j++) {
					g2d.fillRect(cuerpo.getJsonObject(j).getInt("x") * Param.PIXEL_RESIZE,
							cuerpo.getJsonObject(j).getInt("y") * Param.PIXEL_RESIZE, Param.PIXEL_RESIZE,
							Param.PIXEL_RESIZE);
				}
			}

			JsonArray obstaculos = mapa.getJsonArray("obstaculos");

			for (int i = 0; i < obstaculos.size(); i++) {
				g2d.setColor(Color.WHITE);
				g2d.fillRect(obstaculos.getJsonObject(i).getInt("x") * 5, obstaculos.getJsonObject(i).getInt("y") * 5,
						5, 5);
			}

			JsonArray score = mapa.getJsonArray("score");

			String[] listModel = new String[score.size()];
			for (int i = 0; i < score.size(); i++) {
				listModel[i] = score.get(i).toString();
			}
			jListScore.setListData(listModel);
		}

		g2d.setColor(Color.WHITE);
		g2d.drawString(String.valueOf(json.getInt("tiempoTranscurrido")), Param.MAPA_WIDTH - 30, 30);

		if (json.getBoolean("terminado")) {
			g2d.setColor(Color.WHITE);
			g2d.drawString("Juego terminado", (Param.MAPA_WIDTH / 2) - 100, Param.MAPA_HEIGHT / 2);
			musicaFondo.stop();
		}
		this.panelMapa.getGraphics().drawImage(bufferedImage, 0, 0, null);

		if (mapa.getBoolean("murioUnJugador")) {
			new Sonido(Param.GOLPE_PATH).reproducir();
		}
	}

	private void addListener() {
		this.addKeyListener(new GestorInput().teclado);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// stop();
			}
		});
		this.btnSalirJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}

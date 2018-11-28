package cliente.ventana;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.StringReader;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cliente.Cliente;
import cliente.Imagen;
import cliente.Sonido;
import cliente.input.GestorInput;
import config.Param;
import config.Posicion;

public class VentanaJuego extends JFrame {

	private static final long serialVersionUID = 595619242686756871L;

	private JPanel contentPane;

	private JLabel lblScore;
	private JLabel lblVib;
	private JLabel lblFrutas;

	private JList<String> jListJugadores;
	private JList<String> jListFrutas;

	private JButton btnSalirJuego;
	private JPanel panelMapa;
	private VentanaJuego ventanaJuego = this;
	private JTextField textRonda;

	private Sonido musicaFondo;
	private int totalRondas;

	private JLabel lblReferencia;
	private JLabel lblObstaculoRef;
	private JLabel lblFrutasRef;
	private JSeparator separatorTop;
	private JSeparator separatorBottom;
	private JLabel lblJugador;
	private JLabel lblOtrosJugadores;

	private BufferedImage imagenCabeza;
	private BufferedImage imagenCuerpo;
	private BufferedImage imagenCuerpoBot;
	private BufferedImage imagenFruta;
	private boolean musicaEncendida = false;

	VentanaJuego v; // Fix para tener una referencia a la VentanaJuego y utilizarla en los eventos
					// de los botones.
	Thread thread = null; // Fix para tener una referencia al thread de la VentanaJuego y finalizar su
							// ejecucion.

	public VentanaJuego(int totalRondas) {
		super("Snake");

		this.totalRondas = totalRondas;

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
		lblScore.setBounds(10, 43, 67, 21);
		contentPane.add(lblScore);

		jListJugadores = new JList<String>();
		jListJugadores.setBackground(SystemColor.control);
		jListJugadores.setBorder(null);
		jListJugadores.setBounds(10, 90, 50, 247);
		jListJugadores.setOpaque(false);
		jListJugadores.setEnabled(false);
		contentPane.add(jListJugadores);

		jListFrutas = new JList<String>();
		jListFrutas.setOpaque(false);
		jListFrutas.setEnabled(false);
		jListFrutas.setBorder(null);
		jListFrutas.setBackground(SystemColor.menu);
		jListFrutas.setBounds(70, 90, 50, 247);
		contentPane.add(jListFrutas);

		lblVib = new JLabel("Viborita");
		lblVib.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblVib.setBounds(10, 75, 50, 14);
		contentPane.add(lblVib);

		lblFrutas = new JLabel("Frutas");
		lblFrutas.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFrutas.setBounds(70, 75, 50, 14);
		this.contentPane.add(lblFrutas);

		this.btnSalirJuego = new JButton("Salir juego");
		btnSalirJuego.setBounds(10, 348, 180, 25);
		this.contentPane.add(btnSalirJuego);

		this.panelMapa = new JPanel();
		this.panelMapa.setBounds(Param.VENTANA_JUEGO_WIDTH - Param.MAPA_WIDTH, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);
		this.contentPane.add(panelMapa);

		lblReferencia = new JLabel("Referencias:");
		lblReferencia.setBounds(10, 397, 180, 21);
		contentPane.add(lblReferencia);

		lblObstaculoRef = new JLabel("Obstaculos");
		lblObstaculoRef.setBounds(10, 441, 150, 21);
		contentPane.add(lblObstaculoRef);

		lblFrutasRef = new JLabel("Frutas");
		lblFrutasRef.setBounds(10, 473, 150, 21);
		contentPane.add(lblFrutasRef);

		lblJugador = new JLabel("Jugador");
		lblJugador.setBounds(10, 505, 150, 21);
		contentPane.add(lblJugador);

		lblOtrosJugadores = new JLabel("Otros jugadores");
		lblOtrosJugadores.setBounds(10, 537, 150, 21);
		contentPane.add(lblOtrosJugadores);

		separatorTop = new JSeparator();
		separatorTop.setBounds(10, 335, 180, 2);
		contentPane.add(separatorTop);

		separatorBottom = new JSeparator();
		separatorBottom.setBounds(10, 384, 180, 2);
		contentPane.add(separatorBottom);

		textRonda = new JTextField();
		textRonda.setForeground(Color.WHITE);
		textRonda.setHorizontalAlignment(SwingConstants.CENTER);
		textRonda.setBackground(Color.BLACK);
		textRonda.setFont(new Font("Tahoma", Font.BOLD, 13));
		textRonda.setEditable(false);
		textRonda.setBounds(88, 14, 72, 20);
		contentPane.add(textRonda);
		textRonda.setColumns(10);

		JLabel lblRonda = new JLabel("RONDA");
		lblRonda.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblRonda.setBounds(10, 11, 67, 21);
		contentPane.add(lblRonda);

		this.setFocusable(true);
		this.setVisible(true);

		imagenCabeza = Imagen.cargar(Param.IMG_CABEZA_PATH, true);
		imagenCuerpo = Imagen.cargar(Param.IMG_CUERPO_PATH, true);
		imagenCuerpoBot = Imagen.cargar(Param.IMG_CUERPO_BOT_PATH, true);
		imagenFruta = Imagen.cargar(Param.IMG_FRUTA_PATH, true);

		thread = new Thread() {
			public synchronized void run() {
				Cliente.getConexionServidor().recibirMapa(ventanaJuego);
			}
		};

		musicaFondo = new Sonido(Param.SONIDO_FONDO_PATH);
		// musicaFondo.repetir();

		thread.start();

		addListener();

		v = this;

	}

	public void dibujarMapaJson(String jsonString) {

		long msActual = System.currentTimeMillis();

		if (!this.musicaEncendida) {
			this.musicaEncendida = true;
			musicaFondo.repetir();
		} // Para que la musica de fondo se active al empezar una nueva ronda.
		JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
		JsonObject json = jsonReader.readObject();
		jsonReader.close();

		BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();

		JsonObject mapa = json.getJsonObject("mapa");
		if (!mapa.isEmpty()) {
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);

			JsonArray frutas = mapa.getJsonArray("frutas");
			for (int i = 0; i < frutas.size(); i++) {
				g2d.setColor(Color.RED);
				g2d.drawImage(imagenFruta, frutas.getJsonObject(i).getInt("x") * Param.PIXEL_RESIZE,
						frutas.getJsonObject(i).getInt("y") * Param.PIXEL_RESIZE, null);
			}

			JsonArray jugadores = mapa.getJsonArray("jugadores");

			for (int i = 0; i < jugadores.size(); i++) {
				JsonObject jugadorJson = jugadores.getJsonObject(i);
				JsonObject vibora = jugadorJson.getJsonObject("vibora");

				Color color = new Color(jugadorJson.getInt("color_red"), jugadorJson.getInt("color_green"),
						jugadorJson.getInt("color_blue"));

				g2d.setColor(color);
				g2d.setFont(new Font("default", Font.BOLD, 12));
				g2d.drawString(jugadorJson.getString("nombre").toUpperCase(), vibora.getInt("x") * Param.PIXEL_RESIZE,
						(vibora.getInt("y")) * Param.PIXEL_RESIZE - 5);

				g2d.setFont(null);
				g2d.setColor(Color.RED);
				AffineTransform at = new AffineTransform();
				int xAnterior = vibora.getInt("x");
				int yAnterior = vibora.getInt("y");
				at.translate(xAnterior * Param.PIXEL_RESIZE, yAnterior * Param.PIXEL_RESIZE);

				Posicion posicion = Posicion.values()[vibora.getInt("sentido") % Posicion.values().length];

				at.rotate(Posicion.rotacion(posicion.ordinal()), imagenCabeza.getWidth() / 2,
						imagenCabeza.getHeight() / 2);

				g2d.drawImage(imagenCabeza, at, null);

				JsonArray cuerpo = vibora.getJsonArray("cuerpo");
				int x;
				int y;
				for (int j = 0; j < cuerpo.size(); j++) {
					x = cuerpo.getJsonObject(j).getInt("x");
					y = cuerpo.getJsonObject(j).getInt("y");

					if (vibora.getBoolean("bot")) {
						this.dibujarCuerpo(g2d, imagenCuerpoBot, xAnterior, yAnterior, x, y);
					} else {
						this.dibujarCuerpo(g2d, imagenCuerpo, xAnterior, yAnterior, x, y);
					}
					xAnterior = x;
					yAnterior = y;
				}
			}

			JsonArray obstaculos = mapa.getJsonArray("obstaculos");

			for (int i = 0; i < obstaculos.size(); i++) {
				g2d.setColor(Color.WHITE);
				g2d.fillRect(obstaculos.getJsonObject(i).getInt("x") * Param.PIXEL_RESIZE,
						obstaculos.getJsonObject(i).getInt("y") * Param.PIXEL_RESIZE, Param.PIXEL_RESIZE,
						Param.PIXEL_RESIZE);
			}

			JsonArray score = mapa.getJsonArray("score");
			String[] listModelJugadores = new String[score.size()];
			String[] listModelFrutas = new String[score.size()];

			for (int i = 0; i < score.size(); i++) {
				String[] jugadorFrutas = score.get(i).toString().split(":");
				// listModel[i] = score.get(i).toString();
				listModelJugadores[i] = jugadorFrutas[0];
				listModelFrutas[i] = jugadorFrutas[1];
			}
			jListJugadores.setListData(listModelJugadores);
			jListFrutas.setListData(listModelFrutas);
		}

		g2d.setColor(Color.WHITE);
		g2d.drawString("Time: " + String.valueOf(json.getInt("tiempoTranscurrido")) + "seg", Param.MAPA_WIDTH - 90, 30);

		long diff = json.getJsonNumber("currentTimeMillis").longValue();
		g2d.drawString("Ping: " + (msActual - diff) + "ms", Param.MAPA_WIDTH - 90, 60);

		this.textRonda.setText(String.valueOf(json.getInt("numeroRonda")) + " / " + String.valueOf(this.totalRondas));

		if (json.getBoolean("terminado")) {
			g2d.setColor(Color.WHITE);
			g2d.drawString("Juego terminado", (Param.MAPA_WIDTH / 2) - 100, Param.MAPA_HEIGHT / 2);

			if (json.getInt("numeroRonda") < this.totalRondas) {
				// Termino la ronda
				g2d.drawString("Proxima Ronda en 2 segundos", (Param.MAPA_WIDTH / 2) - 150, Param.MAPA_HEIGHT - 50);
			} else {
				musicaFondo.stop();
				this.musicaEncendida = false;

				// Traer Ganador Partida.
				String[] datosGanador = Cliente.getConexionServidor().recibirGanador(true);
				String mensaje = "El ganador es " + datosGanador[0] + "con " + datosGanador[1] + " frutas comidas"
						+ " y " + datosGanador[2] + " puntos";
				if (JOptionPane.showConfirmDialog(panelMapa, mensaje, "Felicitaciones!!!", JOptionPane.PLAIN_MESSAGE,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
					this.dispose();
				}
			}

		}

		if (mapa.getBoolean("murioUnJugador")) {
			new Sonido(Param.SONIDO_MUERE_PATH).reproducir();
		}

		if (mapa.getBoolean("comioFruta")) {
			new Sonido(Param.SONIDO_FRUTA_PATH).reproducir();
		}
		if (this.panelMapa.getGraphics() != null) {
			this.panelMapa.getGraphics().drawImage(bufferedImage, 0, 0, null);
		}
	}

	private void dibujarCuerpo(Graphics2D g2d, BufferedImage imagen, int xAnterior, int yAnterior, int x, int y) {

		AffineTransform at = new AffineTransform();
		Random r = new Random();
		int numero = r.nextInt();

		Posicion posicion = Posicion.getPosicion(xAnterior, yAnterior, x, y);
		switch (posicion) {
		case ESTE:
		case OESTE:
			if (numero % 3 == 0 || numero % 4 == 0) {
				at.translate(x * Param.PIXEL_RESIZE, y * Param.PIXEL_RESIZE);
			} else {
				at.translate(x * Param.PIXEL_RESIZE + 1, y * Param.PIXEL_RESIZE);
			}
			break;
		default:
			if (numero % 3 == 0 || numero % 4 == 0) {
				at.translate(x * Param.PIXEL_RESIZE, y * Param.PIXEL_RESIZE + 1);
			} else {
				at.translate(x * Param.PIXEL_RESIZE, y * Param.PIXEL_RESIZE);
			}
			break;
		}
		at.rotate(Posicion.rotacion(posicion.ordinal()), imagen.getWidth() / 2, imagen.getHeight() / 2);

		g2d.drawImage(imagen, at, null);
	}

	private void addListener() {
		this.addKeyListener(new GestorInput().teclado);
		this.btnSalirJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cliente.getConexionServidor().detenerJuego(); // Detengo la accion iniciado por ComenzarJuego.
				thread.interrupt();
				/*
				 * Cuando apreto el boton salir, debo finalizar el thread que esta pendiente de
				 * recibir el mapa porque a mi ya no me importa recibir el mapa, hasta ahi todo
				 * ok.
				 * 
				 */
				musicaFondo.stop(); // Se para la musica.
				v.dispose(); // Cierre la ventana del juego. Y queda el focus en la VentanaSala pudiendo
								// volver para atras.
			}
		});
	}
}

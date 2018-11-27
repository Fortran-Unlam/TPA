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

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import cliente.Cliente;
import cliente.Imagen;
import cliente.Sonido;
import cliente.input.GestorInput;
import config.Param;
import config.Posicion;
import core.mapa.Juego;

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

	private Sonido musicaFondo;

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
	
	VentanaJuego v; //Fix para tener una referencia a la VentanaJuego y utilizarla en los eventos de los botones.
	Thread thread = null; //Fix para tener una referencia al thread de la VentanaJuego y finalizar su ejecucion.

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

		jListJugadores = new JList<String>();
		jListJugadores.setBackground(SystemColor.control);
		jListJugadores.setBorder(null);
		jListJugadores.setBounds(10, 54, 50, 247);
		jListJugadores.setOpaque(false);
		jListJugadores.setEnabled(false);
		contentPane.add(jListJugadores);

		jListFrutas = new JList<String>();
		jListFrutas.setOpaque(false);
		jListFrutas.setEnabled(false);
		jListFrutas.setBorder(null);
		jListFrutas.setBackground(SystemColor.menu);
		jListFrutas.setBounds(70, 54, 50, 247);
		contentPane.add(jListFrutas);

		lblVib = new JLabel("Viborita");
		lblVib.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblVib.setBounds(10, 39, 50, 14);
		contentPane.add(lblVib);

		lblFrutas = new JLabel("Frutas");
		lblFrutas.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFrutas.setBounds(70, 39, 50, 14);
		this.contentPane.add(lblFrutas);

		this.btnSalirJuego = new JButton("Salir juego");
		btnSalirJuego.setBounds(10, 331, 180, 25);
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
		separatorTop.setBounds(10, 299, 180, 2);
		contentPane.add(separatorTop);

		separatorBottom = new JSeparator();
		separatorBottom.setBounds(10, 384, 180, 2);
		contentPane.add(separatorBottom);

		this.setFocusable(true);
		this.setVisible(true);

		imagenCabeza = Imagen.cargar(Param.IMG_CABEZA_PATH);
		imagenCuerpo = Imagen.cargar(Param.IMG_CUERPO_PATH);
		imagenCuerpoBot = Imagen.cargar(Param.IMG_CUERPO_BOT_PATH);
		imagenFruta = Imagen.cargar(Param.IMG_FRUTA_PATH);

		thread = new Thread() {
			public synchronized void run() {
				Cliente.getConexionServidor().recibirMapa(ventanaJuego);
			}
		};
		
		musicaFondo = new Sonido(Param.SONIDO_FONDO_PATH);
		musicaFondo.repetir();
		
		thread.start();

		addListener();
		
		v = this;

	}

	public void dibujarMapaJson(String jsonString) {
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
				g2d.setColor(Color.RED);
				JsonObject vibora = jugadores.getJsonObject(i).getJsonObject("vibora");

				AffineTransform at = new AffineTransform();
				at.translate(vibora.getInt("x") * Param.PIXEL_RESIZE, vibora.getInt("y") * Param.PIXEL_RESIZE);
				at.rotate(Posicion.rotacion(vibora.getInt("sentido")), imagenCabeza.getWidth() / 2,
						imagenCabeza.getHeight() / 2);

				g2d.drawImage(imagenCabeza, at, null);

				JsonArray cuerpo = vibora.getJsonArray("cuerpo");

				for (int j = 0; j < cuerpo.size(); j++) {
					if (vibora.getBoolean("bot")) {
						g2d.drawImage(imagenCuerpoBot, cuerpo.getJsonObject(j).getInt("x") * Param.PIXEL_RESIZE,
								cuerpo.getJsonObject(j).getInt("y") * Param.PIXEL_RESIZE, null);
					} else {
						g2d.drawImage(imagenCuerpo, cuerpo.getJsonObject(j).getInt("x") * Param.PIXEL_RESIZE,
								cuerpo.getJsonObject(j).getInt("y") * Param.PIXEL_RESIZE, null);
					}
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
		g2d.drawString(String.valueOf(json.getInt("tiempoTranscurrido")), Param.MAPA_WIDTH - 30, 30);

		if (json.getBoolean("terminado")) {
			g2d.setColor(Color.WHITE);
			g2d.drawString("Juego terminado", (Param.MAPA_WIDTH / 2) - 100, Param.MAPA_HEIGHT / 2);
			
			//Se deberia mandar de alguna manera el numero de ronda y poner este mensaje en base a eso.
			//Pendiente.
			g2d.drawString("Próxima Ronda en 3 segundos", (Param.MAPA_WIDTH / 2) - 150, Param.MAPA_HEIGHT - 50);
			musicaFondo.stop();
		}
		this.panelMapa.getGraphics().drawImage(bufferedImage, 0, 0, null);

		if (mapa.getBoolean("murioUnJugador")) {
			new Sonido(Param.SONIDO_MUERE_PATH).reproducir();
		}
		
		if (mapa.getBoolean("comioFruta")) {
			new Sonido(Param.SONIDO_FRUTA_PATH).reproducir();
		}
		long diff = json.getJsonNumber("currentTimeMillis").longValue();
//		System.out.println("ms: " + (diff - System.currentTimeMillis()));
	}

	private void addListener() {
		this.addKeyListener(new GestorInput().teclado);
		this.btnSalirJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Cliente.getConexionServidor().detenerJuego(); //Detengo la accion iniciado por ComenzarJuego.
				thread.stop(); //Esto esta deprecado pero por ahora funciona.
				/* Cuando apreto el boton salir, debo finalizar el thread que esta pendiente de recibir
				 * el mapa porque a mi ya no me importa recibir el mapa, hasta ahi todo ok.
				 * 
				 */
				musicaFondo.stop(); //Se para la musica.
				v.dispose(); // Cierre la ventana del juego. Y queda el focus en la VentanaSala pudiendo volver para atras.
			}
		});
	}
}

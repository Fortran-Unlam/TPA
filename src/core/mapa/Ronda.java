package core.mapa;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import config.Param;
import config.Posicion;
import core.Coordenada;
import core.Jugador;
import core.Score;
import core.entidad.Vibora;
import core.entidad.ViboraBot;
import input.GestorInput;

public class Ronda extends JFrame {

	private static final long serialVersionUID = 48427270007064034L;

	private JPanel contentPane;

	private Mapa mapa;
	private JLabel lblScore;
	private JLabel lblVib;
	private JLabel lblFrutas;

	private List<Jugador> jugadores;

	private JList<String> list;

	private boolean run;

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

		
		lblScore = new JLabel("SCORE");
		lblScore.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblScore.setBounds(16, 7, 67, 21);
		contentPane.add(lblScore);
		
		list = new JList<String>();
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

		mapa = new MapaRandom();
		getContentPane().add(mapa);
		
		addKeyListener(GestorInput.teclado);
		setVisible(true);
	}
	
	public void add(Jugador jugador) {
		this.jugadores.add(jugador);
	}
	
	public void start() {
	
		ViboraBot viboraBot = new ViboraBot(new Coordenada(50, 100));
		mapa.add(viboraBot);

		ViboraBot viboraBot2 = new ViboraBot(new Coordenada(100, 70));
		mapa.add(viboraBot2);
		System.out.println(mapa.viboras.size());
		this.run = true;
		
		while (this.run) {
			mapa.actualizar();
			list.setModel(Score.ScoreToModel(mapa.getScore()));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stop() {
		this.run = false;
	}

	public Vibora crearVibora() {
		Vibora vibora = null;
		for (int intento = 0; intento < 10; intento++) {
			
			vibora = new Vibora(new Coordenada(10*intento, 10*intento), 10, Posicion.ESTE);
			if (mapa.add(vibora)) {
				return vibora;
			}
		}
		return null;
	}

//	public void paint(Graphics g) {
//		super.paint(g);
//		Graphics2D g2d = (Graphics2D) g;
//		g2d.setColor(Color.DARK_GRAY);
//		g2d.fillRect(0, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);
//	}
}

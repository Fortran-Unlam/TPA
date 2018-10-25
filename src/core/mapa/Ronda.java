package core.mapa;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
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
	private JButton button;

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

		JButton btnNewButton = new JButton("Stop");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		btnNewButton.setBounds(0, 312, 100, 25);
		contentPane.add(btnNewButton);

		button = new JButton("Exit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		button.setBounds(0, 350, 100, 25);
		contentPane.add(button);

		addKeyListener(GestorInput.teclado);
		setFocusable(true);
		setVisible(true);
	}

	public void add(final Jugador jugador) {
		this.jugadores.add(jugador);
	}

	/**
	 * Le da comienzo a  la ronda actualizando el mapa cada cierto tiempo
	 */
	public void start() {

		ViboraBot viboraBot = new ViboraBot(new Coordenada(50, 100));
		mapa.add(viboraBot);

		ViboraBot viboraBot2 = new ViboraBot(new Coordenada(100, 70));
		mapa.add(viboraBot2);
		
		this.run = true;
		Score score = new Score();
		score.add(mapa.getViboras());

		while (this.run) {
			mapa.actualizar();
			score.calcularScore();
			list.setModel(score.ScoreToModel());
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

	/**
	 * Intenta n veces crear una vibora en el mapa y la agrega al mapa. Osea que
	 * cuando el mapa se actualiza, se actualiza la vibora
	 * 
	 * @return La vibora si se pudo crear sino null-
	 */
	public Vibora crearVibora() {
		Vibora vibora = null;
		for (int intento = 0; intento < 10; intento++) {

			vibora = new Vibora(new Coordenada(10 * intento, 10 * intento), 10, Posicion.ESTE);
			if (mapa.add(vibora)) {
				return vibora;
			}
		}
		return null;
	}
}

package cliente.ventana;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import cliente.Main;
import cliente.input.GestorInput;
import config.Param;
import core.Jugador;
import core.Obstaculo;
import core.entidad.Fruta;
import core.mapa.Juego;
import core.mapa.Mapa;

public class VentanaJuego extends JFrame {

	private static final long serialVersionUID = 595619242686756871L;

	private JPanel contenedor;

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
		this.juego = juego;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, Param.VENTANA_MAPA_WIDTH, Param.VENTANA_MAPA_HEIGHT);

		contenedor = new JPanel();
		contenedor.setBackground(SystemColor.control);
		contenedor.setBorder(null);
		setUndecorated(true);
		setContentPane(contenedor);
		setLocationRelativeTo(null);
		setBackground(Color.black);
		contenedor.setLayout(null);

		lblScore = new JLabel("SCORE");
		lblScore.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblScore.setBounds(16, 7, 67, 21);
		contenedor.add(lblScore);

		jListScore = new JList<String>();
		jListScore.setBackground(SystemColor.control);
		jListScore.setBorder(null);
		jListScore.setBounds(10, 54, 87, 262);
		jListScore.setOpaque(false);
		jListScore.setEnabled(false);

		contenedor.add(jListScore);

		lblVib = new JLabel("Vib");
		lblVib.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblVib.setBounds(10, 39, 22, 14);
		contenedor.add(lblVib);

		lblFrutas = new JLabel("Frutas");
		lblFrutas.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFrutas.setBounds(51, 35, 42, 21);
		contenedor.add(lblFrutas);

//		contenedor().add(mapa);

		JButton btnNewButton = new JButton("Stop");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// stop();
			}
		});
		btnNewButton.setBounds(0, 312, 100, 25);
		contenedor.add(btnNewButton);

		button = new JButton("Exit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		button.setBounds(0, 350, 100, 25);
		contenedor.add(button);

		panelMapa = new JPanel();
		panelMapa.setBounds(Param.VENTANA_MAPA_WIDTH - Param.MAPA_WIDTH, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);
		contenedor.add(panelMapa);

		addKeyListener(GestorInput.teclado);
		setFocusable(true);
		setVisible(true);

		// this.juego.start(); //EMPIEZA EL JUEGO!!!
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Main.getConexionServidor().recibirMapa(ventanaJuego);
			}
		});
	}

	public void dibujarMapa(Mapa mapa) {
		this.add(mapa);
		this.mapa = mapa;
		
		paint(getGraphics());
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);
		if (this.mapa != null) {
			
			for (Fruta fruta : this.mapa.frutas) {
				fruta.paint(g2d);
			}
			
			for (Jugador jugador: this.mapa.jugadores) {
				jugador.getVibora().paint(g2d);
			}
			
			for (Obstaculo obstaculo : this.mapa.obstaculos) {
				obstaculo.paint(g2d);
			}
		}
	}
}

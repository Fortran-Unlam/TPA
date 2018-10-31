package cliente.ventana;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import cliente.input.GestorInput;
import config.Param;
import core.Jugador;
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
	
	public VentanaJuego(List<Jugador> jugadores, Mapa mapa) {
		super("Snake");
		this.mapa = mapa;
		
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
				//stop();
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
		
		this.juego.start();  //EMPIEZA EL JUEGO!!!
	}
}

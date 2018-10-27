package core.mapa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
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
import javax.swing.border.EmptyBorder;

import config.Param;
import core.Jugador;
import input.GestorInput;

public class VentanaJuego extends JFrame {
	private JPanel contentPane;

	private Mapa mapa;
	private JLabel lblScore;
	private JLabel lblVib;
	private JLabel lblFrutas;

	private JList<String> jListScore;

	private JButton button;
	private Juego juego;
	
	public VentanaJuego(List<Jugador> jugadores, Mapa mapa) {
		super("Snake");
		this.juego = new Juego(jugadores, mapa, jListScore);
		
		
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
		contentPane.add(lblFrutas);

		getContentPane().add(mapa);
		
		JButton btnNewButton = new JButton("Stop");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//stop();
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
		
		this.juego.start();  //EMPIEZA EL JUEGO!!!
	}

}

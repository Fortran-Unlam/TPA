package view.sala;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import pool.Partida;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.border.BevelBorder;

public class VentanaSala extends JFrame {

	private static final long serialVersionUID = -788139998457766457L;
	private JComboBox comboBoxMapa;
	private JTextField textFieldCantidadRondas;

	public VentanaSala() {
		getContentPane().setLayout(null);

		JLabel lblSala = new JLabel("SALA");
		lblSala.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSala.setForeground(Color.MAGENTA);
		lblSala.setBounds(218, 10, 53, 15);
		getContentPane().add(lblSala);

		JLabel lblMapa = new JLabel("Mapa");
		lblMapa.setBounds(50, 56, 70, 15);
		getContentPane().add(lblMapa);

		comboBoxMapa = new JComboBox();
		comboBoxMapa.setBounds(149, 51, 122, 24);
		getContentPane().add(comboBoxMapa);

		JButton btnSalirDeSala = new JButton("Salir de sala");
		btnSalirDeSala.setBounds(397, 371, 122, 25);
		getContentPane().add(btnSalirDeSala);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(249, 371, 117, 25);
		getContentPane().add(btnCancelar);

		JButton btnCrearPartida = new JButton("Empezar juego");
		btnCrearPartida.setBounds(50, 371, 141, 25);
		getContentPane().add(btnCrearPartida);
		
		JLabel lblRondas = new JLabel("Cantidad de rondas");
		lblRondas.setBounds(33, 97, 105, 15);
		getContentPane().add(lblRondas);
		
		textFieldCantidadRondas = new JTextField();
		textFieldCantidadRondas.setBounds(148, 94, 86, 20);
		getContentPane().add(textFieldCantidadRondas);
		textFieldCantidadRondas.setColumns(10);
		
		JList listUsuarios = new JList();
		listUsuarios.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listUsuarios.setBounds(30, 161, 489, 187);
		listUsuarios.setEnabled(false);
		listUsuarios.setOpaque(false);
		getContentPane().add(listUsuarios);
		
		JLabel lblUsuariosConectados = new JLabel("Usuarios en la sala");
		lblUsuariosConectados.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsuariosConectados.setBounds(218, 137, 161, 24);
		getContentPane().add(lblUsuariosConectados);
		
		btnCrearPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					createSubmit();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		setBounds(297, 183, 587, 446);
		setVisible(true);
	}
	
	/**
	 * Evento que se ejecuta cuando se envia el formulario para crear la sala
	 * @throws Exception 
	 */
	protected void createSubmit() throws Exception {
				
		Partida partida = new Partida(1);
		
		partida.agregarRonda();
		
		partida.crearJugador("Player 1");
		
		partida.start();
	}
}

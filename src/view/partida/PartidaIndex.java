package view.partida;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import pool.Partida;

public class PartidaIndex extends JFrame {

	private static final long serialVersionUID = -788139998457766457L;
	private JComboBox comboBox;

	public PartidaIndex() {
		getContentPane().setLayout(null);

		JLabel lblCrearPartida = new JLabel("Crear Partida");
		lblCrearPartida.setBounds(185, 12, 114, 15);
		getContentPane().add(lblCrearPartida);

		JLabel lblMapa = new JLabel("Mapa");
		lblMapa.setBounds(49, 41, 70, 15);
		getContentPane().add(lblMapa);

		comboBox = new JComboBox();
		comboBox.setBounds(195, 39, 122, 24);
		getContentPane().add(comboBox);

		JButton btnSalirDeSala = new JButton("Salir de sala");
		btnSalirDeSala.setBounds(30, 183, 122, 25);
		getContentPane().add(btnSalirDeSala);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(168, 183, 117, 25);
		getContentPane().add(btnCancelar);

		JButton btnCrearPartida = new JButton("Crear Partida");
		btnCrearPartida.setBounds(297, 183, 141, 25);
		getContentPane().add(btnCrearPartida);
		
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
		
		setBounds(297, 183, 470, 259);
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
		// TODO: hacer un new sala y poner el jframe
	}
}

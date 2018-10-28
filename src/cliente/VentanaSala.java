package cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import config.Param;

public class VentanaSala extends JFrame {

	private static final long serialVersionUID = 1L;	
	private JList listUsuarios;
	private JLabel lblMaxUsuarios;
	private JPanel contentPane;
	
	public VentanaSala() {
		
		setTitle("Sala");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, Param.VENTANA_SALA_WIDTH, Param.VENTANA_SALA_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		getContentPane().setLayout(null);

		JLabel lblMapa = new JLabel("");
		lblMapa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMapa.setBounds(33, 48, 207, 23);
		getContentPane().add(lblMapa);

		JButton btnSalirDeSala = new JButton("Salir de sala");
		btnSalirDeSala.setBounds(399, 357, 162, 40);
		getContentPane().add(btnSalirDeSala);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(213, 357, 168, 40);
		getContentPane().add(btnCancelar);

		JButton btnCrearPartida = new JButton("Empezar juego");
		btnCrearPartida.setBounds(33, 357, 168, 40);
		getContentPane().add(btnCrearPartida);
		
		JLabel lblRondas = new JLabel("");
		lblRondas.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblRondas.setBounds(32, 96, 198, 22);
		getContentPane().add(lblRondas);
		
		this.listUsuarios = new JList();
		this.listUsuarios.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.listUsuarios.setBounds(33, 83, 528, 169);
		this.listUsuarios.setEnabled(false);
		this.listUsuarios.setOpaque(false);
		getContentPane().add(this.listUsuarios);
		
		JLabel lblUsuariosConectados = new JLabel("Usuarios en la sala");
		lblUsuariosConectados.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsuariosConectados.setBounds(30, 48, 210, 24);
		getContentPane().add(lblUsuariosConectados);
		
		this.lblMaxUsuarios = new JLabel("");
		lblMaxUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 16));
		this.lblMaxUsuarios.setBounds(327, 51, 192, 23);
		getContentPane().add(this.lblMaxUsuarios);
		
		JLabel lblNewLabel = new JLabel("SALA");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(249, 10, 69, 30);
		getContentPane().add(lblNewLabel);
		
		JLabel lblJugabilidad = new JLabel("");
		lblJugabilidad.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblJugabilidad.setBounds(327, 107, 155, 23);
		getContentPane().add(lblJugabilidad);
		
	}
	

}

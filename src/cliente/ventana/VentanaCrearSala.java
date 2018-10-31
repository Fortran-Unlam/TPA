package cliente.ventana;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import cliente.Main;
import config.Param;
import looby.Sala;

public class VentanaCrearSala extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField cantidadRondaField;
	private JTextField nombreField;
	private VentanaMenu ventanaMenu;
	public String nombreSala;

	public VentanaCrearSala(VentanaMenu ventanaMenu) {
		this.ventanaMenu = ventanaMenu;
		this.ventanaMenu.setVisible(false);

		setTitle("Nueva Sala");

		// Ver evento WindowListener para poder hacer un DISPOSE_ON_CLOSED
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, Param.VENTANA_CLIENTE_WIDTH, Param.VENTANA_CLIENTE_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);

		JLabel lblCantidadDeRondas = new JLabel("Cantidad de rondas:");
		lblCantidadDeRondas.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCantidadDeRondas.setBounds(66, 154, 175, 33);
		contentPane.add(lblCantidadDeRondas);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(98, 294, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				crearSala();
			}
		});
		contentPane.add(btnAceptar);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ventanaMenu.setVisible(true);
				dispose();
			}
		});
		btnVolver.setBounds(259, 294, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnVolver);
		setLocationRelativeTo(this.ventanaMenu);

		cantidadRondaField = new JTextField();
		cantidadRondaField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cantidadRondaField.setBounds(239, 157, 40, 25);
		contentPane.add(cantidadRondaField);
		cantidadRondaField.setColumns(10);

		JLabel lblCreacionDeSala = new JLabel("Creacion de sala nueva");
		lblCreacionDeSala.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreacionDeSala.setForeground(Color.ORANGE);
		lblCreacionDeSala.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblCreacionDeSala.setBounds(56, 0, 384, 69);
		contentPane.add(lblCreacionDeSala);

		JLabel lblNewLabel = new JLabel("Nombre de la sala:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(66, 118, 175, 25);
		contentPane.add(lblNewLabel);

		nombreField = new JTextField();
		nombreField.setBounds(239, 118, 151, 25);
		contentPane.add(nombreField);
		nombreField.setColumns(10);

	}

	protected void crearSala() {
		// Falta cantidad de usuarios
		Sala sala = Main.getConexionServidor().craerSala(this.nombreField.getText(),
				Integer.valueOf(this.cantidadRondaField.getText()));
		
		new VentanaSala(this, sala).setVisible(true);

	}
}

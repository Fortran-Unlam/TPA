package cliente.ventana;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import config.Param;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaCrearSala extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField txtNombre;
	private VentanaMenu ventanaMenu;
	public String nombreSala;
	
	public VentanaCrearSala(VentanaMenu ventanaMenu) {
		this.ventanaMenu = ventanaMenu;
		this.ventanaMenu.setVisible(false);
		
		setTitle("Nueva Sala");
		
		//Ver evento WindowListener para poder hacer un DISPOSE_ON_CLOSED
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
		lblCantidadDeRondas.setBounds(86, 126, 175, 33);
		contentPane.add(lblCantidadDeRondas);
		
		JLabel lblTipoDeJugabilidad = new JLabel("Tipo de jugabilidad:");
		lblTipoDeJugabilidad.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTipoDeJugabilidad.setBounds(86, 163, 165, 33);
		contentPane.add(lblTipoDeJugabilidad);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(259, 169, 151, 25);
		contentPane.add(comboBox);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(98, 294, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nombreSala = txtNombre.getText();
				abrirVentanaSala();
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
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField.setBounds(259, 129, 40, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblCreacionDeSala = new JLabel("Creacion de sala nueva");
		lblCreacionDeSala.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreacionDeSala.setForeground(Color.ORANGE);
		lblCreacionDeSala.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblCreacionDeSala.setBounds(56, 0, 384, 69);
		contentPane.add(lblCreacionDeSala);
		
		JLabel lblMapa = new JLabel("Mapa:");
		lblMapa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMapa.setBounds(86, 207, 165, 20);
		contentPane.add(lblMapa);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(259, 207, 151, 25);
		contentPane.add(comboBox_1);
		
		JLabel lblNewLabel = new JLabel("Nombre de la sala:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(86, 90, 175, 25);
		contentPane.add(lblNewLabel);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(259, 90, 151, 25);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
	}
	
	private void abrirVentanaSala() {
		new VentanaSala(this, nombreSala).setVisible(true);
	}
	
}

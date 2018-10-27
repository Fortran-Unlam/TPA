package cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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

public class VentanaCrearSala extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	
	public VentanaCrearSala() {
		setTitle("Nueva Sala");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, Param.VENTANA_MAPA_WIDTH, Param.VENTANA_CLIENTE_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCantidadDeRondas = new JLabel("Cantidad de rondas:");
		lblCantidadDeRondas.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCantidadDeRondas.setBounds(103, 125, 175, 33);
		contentPane.add(lblCantidadDeRondas);
		
		JLabel lblTipoDeJugabilidad = new JLabel("Tipo de jugabilidad:");
		lblTipoDeJugabilidad.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTipoDeJugabilidad.setBounds(103, 174, 151, 33);
		contentPane.add(lblTipoDeJugabilidad);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(263, 179, 151, 25);
		contentPane.add(comboBox);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(122, 298, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnAceptar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(297, 294, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnVolver);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField.setBounds(264, 130, 40, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblCreacionDeSala = new JLabel("Creacion de sala nueva");
		lblCreacionDeSala.setForeground(Color.ORANGE);
		lblCreacionDeSala.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblCreacionDeSala.setBounds(121, 0, 326, 69);
		contentPane.add(lblCreacionDeSala);
		
		JLabel lblMapa = new JLabel("Mapa:");
		lblMapa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMapa.setBounds(155, 230, 43, 20);
		contentPane.add(lblMapa);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(263, 224, 151, 25);
		contentPane.add(comboBox_1);
		
		JLabel lblNewLabel = new JLabel("Nombre de la sala:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(103, 80, 151, 25);
		contentPane.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(264, 80, 151, 25);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
	}
}

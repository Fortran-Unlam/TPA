package cliente.ventana;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import cliente.Main;
import config.Param;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class VentanaCrearSala extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nombreField;
	private VentanaMenu ventanaMenu;
	private JTextField maxUsuarioField;
	private VentanaSala ventanaSala;
	
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

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					crearSala();
				}
			}
		});
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
		nombreField.setBounds(239, 119, 151, 25);
		contentPane.add(nombreField);
		nombreField.setColumns(10);
		
		JLabel lblMaxUsuarios = new JLabel("Cantidad m\u00E1xima de usuarios:");
		lblMaxUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMaxUsuarios.setBounds(10, 154, 218, 33);
		contentPane.add(lblMaxUsuarios);
		
		maxUsuarioField = new JTextField();
		maxUsuarioField.setBounds(239, 155, 40, 26);
		contentPane.add(maxUsuarioField);
		maxUsuarioField.setColumns(10);

	}

	protected void crearSala() {
		ArrayList<String> datosSala = new ArrayList<String>();
		//0: nombre, 1: cantUsuariosMax
		datosSala.add(this.nombreField.getText());
		datosSala.add(this.maxUsuarioField.getText());
		
		if(Main.getConexionServidor().crearSala(datosSala)) {
			//Aguante boca
			this.ventanaSala = new VentanaSala(this.ventanaMenu, datosSala, Param.CREACION_SALA_ADMIN);
			this.dispose();
		}else {
			JOptionPane.showMessageDialog(null, "Hubo un error en la creacion de la sala. Puede que el nombre ya exista", "Error creacion sala",
					JOptionPane.WARNING_MESSAGE);
		}
			

	}
}

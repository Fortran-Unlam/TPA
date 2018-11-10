package cliente.ventana;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import cliente.Cliente;
import cliente.Sonido;
import config.Param;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class VentanaCrearSala extends JFrame {

	private static final long serialVersionUID = 1876694895241653019L;
	private JPanel contentPane;
	private JTextField nombreField;
	private VentanaMenu ventanaMenu;
	private JTextField maxUsuarioField;
	private VentanaSala ventanaSala;
	private JButton btnAceptar;
	private JButton btnVolver;
	
	public VentanaCrearSala(VentanaMenu ventanaMenu) {
		this.ventanaMenu = ventanaMenu;
		this.ventanaMenu.setVisible(false);

		setTitle("Nueva Sala");

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, Param.VENTANA_CLIENTE_WIDTH, Param.VENTANA_CLIENTE_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(98, 281, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnAceptar);

		btnVolver = new JButton("Volver");
		btnVolver.setBounds(259, 281, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnVolver);
		setLocationRelativeTo(this.ventanaMenu);

		JLabel lblCreacionDeSala = new JLabel("Creacion de sala nueva");
		lblCreacionDeSala.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreacionDeSala.setForeground(Color.GRAY);
		lblCreacionDeSala.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblCreacionDeSala.setBounds(49, 23, 384, 52);
		contentPane.add(lblCreacionDeSala);

		JLabel lblNewLabel = new JLabel("Nombre de la sala:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(27, 118, 175, 25);
		contentPane.add(lblNewLabel);

		nombreField = new JTextField();
		/*Bloquea el control c y control v*/
		InputMap mapNombreField = nombreField.getInputMap(nombreField.WHEN_FOCUSED);
		mapNombreField.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
		/*Limita cantidad de caracteres a ingresar en el campo sala*/
		nombreField.addKeyListener(new KeyAdapter() {
		public void keyTyped(KeyEvent e) {
			if (nombreField.getText().length() >= Param.LIMITE_CARACTERES_NOMBRE_SALA) {
				e.consume();
				Toolkit.getDefaultToolkit().beep();
	     		}
			}
	    });
		nombreField.setToolTipText("Ingrese el nombre de la sala que desea. Solo pueden contener letras y numeros (sin espacios). Maximo 20 caracteres.");
		nombreField.setBounds(289, 120, 151, 25);
		contentPane.add(nombreField);
		nombreField.setColumns(10);
		
		JLabel lblMaxUsuarios = new JLabel("Cantidad m\u00E1xima de usuarios:");
		lblMaxUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMaxUsuarios.setBounds(27, 154, 218, 33);
		contentPane.add(lblMaxUsuarios);
		
		maxUsuarioField = new JTextField();
		/*Bloquea el control c y control v*/
		InputMap MapMaxUsuarioField = maxUsuarioField.getInputMap(maxUsuarioField.WHEN_FOCUSED);
		MapMaxUsuarioField.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
		/*Limita cantidad de caracteres a ingresar en el campo usuarios maximos*/
		maxUsuarioField.addKeyListener(new KeyAdapter() {
		public void keyTyped(KeyEvent e) {
			if (maxUsuarioField.getText().length() >= Param.LIMITE_CARACTERES_USUARIOS_MAX) {
				e.consume();
				Toolkit.getDefaultToolkit().beep();
	     		}
			}
	    });
		maxUsuarioField.setToolTipText("Ingrese la cantidad m\u00E1xima de usuarios. Debe ser num\u00E9rico. Maximo 99 usuarios.");
		maxUsuarioField.setBounds(400, 159, 40, 26);
		contentPane.add(maxUsuarioField);
		maxUsuarioField.setColumns(10);
		
		addListener();
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	if (JOptionPane.showConfirmDialog(contentPane, Param.MENSAJE_CERRAR_VENTANA, Param.TITLE_CERRAR_VENTANA, 
		                JOptionPane.YES_NO_OPTION,
		                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		    	Cliente.getConexionServidor().cerrarSesionUsuario(ventanaMenu.getUsuario());
		    	System.exit(0);
		    	}
		    }
		});
	}

	protected void crearSala() {

		if(this.nombreField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "El nombre de la sala no puede estar vacio.",
					"Aviso", JOptionPane.WARNING_MESSAGE);
			this.nombreField.setText("");
			this.nombreField.setFocusable(true);
			this.maxUsuarioField.setFocusable(true);
			return;
		}
		
		if(this.maxUsuarioField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "La cantidad de usuarios máximos no puede estar vacio.",
					"Aviso", JOptionPane.WARNING_MESSAGE);
			this.maxUsuarioField.setText("");
			this.nombreField.setFocusable(true);
			this.maxUsuarioField.setFocusable(true);
			return;
		}
		
		if(!this.maxUsuarioField.getText().matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null, "La cantidad de usuarios máximos debe ser numérico",
					"Aviso", JOptionPane.WARNING_MESSAGE);
			this.maxUsuarioField.setText("");
			this.nombreField.setFocusable(true);
			this.maxUsuarioField.setFocusable(true);
			return;
		}
		
		
		if (this.nombreField.getText().matches("[a-zA-Z0-9]+")) {

			ArrayList<String> datosSala = new ArrayList<String>();
			// 0: nombre, 1: cantUsuariosMax
			datosSala.add(this.nombreField.getText());
			datosSala.add(this.maxUsuarioField.getText());
			
			if (Cliente.getConexionServidor().crearSala(datosSala)) {
				Sonido musicaFondo = new Sonido(Param.GOLPE_PATH);
				musicaFondo.reproducir();
				
				Cliente.getconexionServidorBackOff().avisarAlSvQueMandeActualizacionSalas(Param.NOTICE_CREACION_SALA);
				this.ventanaSala = new VentanaSala(this.ventanaMenu, datosSala, Param.CREACION_SALA_ADMIN);
				Sincronismo.setVentanaSala(ventanaSala);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null,
						"Hubo un error en la creacion de la sala. Puede que el nombre ya exista", "Error creacion sala",
						JOptionPane.WARNING_MESSAGE);
			}

		} else {
			JOptionPane.showMessageDialog(null, "Los nombres de sala solo pueden contener letras y numeros (sin espacios).",
					"Aviso", JOptionPane.WARNING_MESSAGE);
			this.nombreField.setText("");
			this.maxUsuarioField.setText("");
			this.nombreField.setFocusable(true);
			this.maxUsuarioField.setFocusable(true);
		}
	}
	
	private void addListener() {		
		btnAceptar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					crearSala();
				}
			}
		});
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				crearSala();
			}
		});
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sonido musicaFondo = new Sonido(Param.GOLPE_PATH);
				musicaFondo.reproducir();
				ventanaMenu.setVisible(true);
				dispose();
			}
		});
		
		btnVolver.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Sonido musicaFondo = new Sonido(Param.GOLPE_PATH);
					musicaFondo.reproducir();
					ventanaMenu.setVisible(true);
					dispose();
				}
			}
		});
		
		nombreField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					crearSala();
				}
			}
		});
		
		maxUsuarioField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					crearSala();
				}
			}
		});
	}
}

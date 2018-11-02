package cliente.ventana;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import cliente.Main;
import config.Param;
import looby.Sala;
import javax.swing.JComboBox;

public class VentanaSala extends JFrame {

	private static final long serialVersionUID = 1L;	
	private JList<String> listUsuarios;
	private JLabel lblMaxUsuarios;
	private JPanel contentPane;
	private JFrame ventanaPrevia;
	//private VentanaUnirSala ventanaUnirSala;
	private String nombreSala;
	private Sala sala;
		
	public VentanaSala(JFrame ventanaPrevia, Sala sala) {
		this.ventanaPrevia = ventanaPrevia;
		this.ventanaPrevia.setVisible(false);
		this.sala = sala;
		this.nombreSala = sala.getNombre();
		dibujarSala();
	}
	
	public void dibujarSala() {
		setTitle("Sala: " + this.nombreSala);
		
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
		btnSalirDeSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ventanaPrevia.setVisible(true);
				dispose();
			}
		});
		btnSalirDeSala.setBounds(327, 357, 162, 40);
		getContentPane().add(btnSalirDeSala);

		JButton btnEmpezarJuego = new JButton("Empezar juego");
		btnEmpezarJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empezarJuego();
			}
		});
		btnEmpezarJuego.setBounds(110, 357, 168, 40);
		getContentPane().add(btnEmpezarJuego);
		btnEmpezarJuego.setEnabled(false);
		
		JLabel lblRondas = new JLabel("");
		lblRondas.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblRondas.setBounds(32, 96, 198, 22);
		getContentPane().add(lblRondas);
		
		this.listUsuarios = new JList<String>();
		this.listUsuarios.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.listUsuarios.setBounds(33, 83, 121, 215);
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
		
		JLabel lblNewLabel = new JLabel("Sala");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(249, 10, 69, 30);
		getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("Tipo de jugabilidad:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setBounds(195, 90, 165, 33);
		contentPane.add(label);
		
		JComboBox<String> tipoJugabilidad = new JComboBox<String>();
		tipoJugabilidad.setBounds(368, 96, 151, 25);
		tipoJugabilidad.addItem("Supervivencia");
		tipoJugabilidad.addItem("Fruta");
		tipoJugabilidad.addItem("Tiempo");
		contentPane.add(tipoJugabilidad);
		tipoJugabilidad.setEnabled(false);
		
		JLabel label_1 = new JLabel("Mapa:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_1.setBounds(195, 134, 165, 20);
		contentPane.add(label_1);
		
		JComboBox<Object> mapa = new JComboBox<Object>();
		mapa.setBounds(368, 134, 151, 25);
		mapa.addItem("Mapa 1");
		mapa.addItem("Mapa 2");
		mapa.addItem("Mapa 3");
		contentPane.add(mapa);
		mapa.setEnabled(false);
		
		/*Visibilidad unica para el admin
		 * 
		 * Seleccionar tipo de jugabilidad
		 * Seleccionar mapaa jugar
		 * Empezar el juego
		 * 
		 */
		if(sala.getAdministrador().getId() == (Main.getConexionServidor().getUsuario().getId())) {
			tipoJugabilidad.setEnabled(true);
			mapa.setEnabled(true);
			btnEmpezarJuego.setEnabled(true);
		}
		
	}
	
	protected void empezarJuego() {
		this.sala = Main.getConexionServidor().comenzarJuego(this.sala);
		
		VentanaJuego ventanaJuego = new VentanaJuego(null);
	}
}

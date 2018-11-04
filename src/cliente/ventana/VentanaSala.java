package cliente.ventana;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import cliente.Main;
import config.Param;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

public class VentanaSala extends JFrame {

	private static final long serialVersionUID = 1L;	
	private JList<String> listUsuarios;
	private JLabel lblMaxUsuarios;
	private JPanel contentPane;
	private JFrame ventanaMenu;
	private String nombreSala;
	private String creacionUnionSala;
		
	public VentanaSala(JFrame ventanaMenu, ArrayList<String> datosSala, String creacionUnionSala) {
		this.ventanaMenu = ventanaMenu;
		this.ventanaMenu.setVisible(false);
		this.creacionUnionSala = creacionUnionSala;
		this.nombreSala = datosSala.get(0);
		
		setTitle("Sala de juego");
		
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

		JButton btnSalirDeSala = new JButton("Salir de sala");
		btnSalirDeSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ventanaMenu.setVisible(true);
				salirSala();
				dispose();
			}
		});
		btnSalirDeSala.setBounds(326, 346, 162, 40);
		getContentPane().add(btnSalirDeSala);

		JButton btnEmpezarJuego = new JButton("Empezar juego");
		btnEmpezarJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empezarJuego();
			}
		});
		btnEmpezarJuego.setBounds(111, 346, 168, 40);
		getContentPane().add(btnEmpezarJuego);
		btnEmpezarJuego.setEnabled(false);
		
		this.listUsuarios = new JList<String>();
		this.listUsuarios.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.listUsuarios.setBounds(33, 83, 121, 215);
		this.listUsuarios.setEnabled(false);
		this.listUsuarios.setOpaque(false);
		getContentPane().add(this.listUsuarios);
		
		JLabel lblUsuariosConectados = new JLabel("Usuarios en la sala");
		lblUsuariosConectados.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsuariosConectados.setBounds(30, 48, 124, 24);
		getContentPane().add(lblUsuariosConectados);
		
		this.lblMaxUsuarios = new JLabel("");
		lblMaxUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 14));
		//Reflejo 04/11 pinchaba por el 1, iria un size-1?
		this.lblMaxUsuarios.setText("(X/" + datosSala.get(datosSala.size()-1) + ")");
		//this.lblMaxUsuarios.setText("(X/" + datosSala.get(1) + ")");
		this.lblMaxUsuarios.setBounds(159, 48, 39, 23);
		getContentPane().add(this.lblMaxUsuarios);
		
		JLabel lblSala = new JLabel(this.nombreSala, SwingConstants.CENTER);
		lblSala.setForeground(Color.GRAY);
		lblSala.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSala.setBounds(33, 11, 551, 30);
		getContentPane().add(lblSala);
		
		JLabel label = new JLabel("Tipo de jugabilidad:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(195, 90, 165, 33);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Mapa:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_1.setBounds(198, 197, 165, 20);
		contentPane.add(label_1);

		JComboBox<Object> mapa = new JComboBox<Object>();
		mapa.setBounds(368, 192, 151, 25);
		mapa.addItem("Mapa 1");
		mapa.addItem("Mapa 2");
		mapa.addItem("Mapa 3");
		contentPane.add(mapa);
		mapa.setEnabled(false);
		
		JCheckBox chckbxSupervivencia = new JCheckBox("Supervivencia");
		chckbxSupervivencia.setBounds(366, 95, 130, 23);
		contentPane.add(chckbxSupervivencia);
		chckbxSupervivencia.setEnabled(false);
		
		JCheckBox chckbxFruta = new JCheckBox("Fruta");
		chckbxFruta.setBounds(366, 121, 130, 23);
		contentPane.add(chckbxFruta);
		chckbxFruta.setEnabled(false);
		
		JCheckBox chckbxTiempo = new JCheckBox("Tiempo");
		chckbxTiempo.setBounds(366, 147, 130, 23);
		contentPane.add(chckbxTiempo);
		chckbxTiempo.setEnabled(false);		
		
		/*Visibilidad unica para el admin
		 * Seleccionar tipo de jugabilidad
		 * Seleccionar mapaa jugar
		 * Empezar el juego
		 */
		if(this.creacionUnionSala == Param.CREACION_SALA_ADMIN) {
			chckbxSupervivencia.setEnabled(true);
			chckbxFruta.setEnabled(true);
			chckbxTiempo.setEnabled(true);
			mapa.setEnabled(true);
			btnEmpezarJuego.setEnabled(true);
		}
		
	}
	
	protected void salirSala() 
	{
		Main.getConexionServidor().SalirSala(this.nombreSala);
	}
	
	protected void empezarJuego() {
		if (Main.getConexionServidor().comenzarJuego() == false) {
			System.out.println("no pudo crear el juego");
			return;
		}
		
		new VentanaJuego(null);
	}
}

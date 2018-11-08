package cliente.ventana;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import cliente.Main;
import cliente.Sonido;
import config.Param;

public class VentanaUnirSala extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaMenu ventanaMenu;
	private JList<String> listSalas;
	private String salaSeleccionada;
	private boolean ingresoaSalaOSeFue = false;
	private DefaultListModel<String> modelDeSalasDisponibles = new DefaultListModel<>();
	
	public VentanaUnirSala(VentanaMenu ventanaMenu) {
		this.ventanaMenu = ventanaMenu;
		ventanaMenu.setVisible(false);

		setTitle("Unirse a sala");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setBounds(0, 0, Param.VENTANA_CLIENTE_WIDTH, Param.VENTANA_CLIENTE_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);

		JLabel lblSalasDisponibles = new JLabel("Salas disponibles:");
		lblSalasDisponibles.setForeground(Color.MAGENTA);
		lblSalasDisponibles.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblSalasDisponibles.setBounds(10, 54, 190, 27);
		contentPane.add(lblSalasDisponibles);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(12, 98, 454, 209);
		contentPane.add(scrollPane);

		this.listSalas = new JList<>();
		scrollPane.setViewportView(listSalas);
		listSalas.setBackground(SystemColor.control);
		listSalas.setBorder(null);
		listSalas.setEnabled(true);

		listSalas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 1) {
					salaSeleccionada = ((String) listSalas.getSelectedValue());
				}
			}
		});

		JButton btnRefrescarSalas = new JButton("Refrescar");
		btnRefrescarSalas.setBounds(267, 48, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnRefrescarSalas);

		btnRefrescarSalas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Ver como refrescar salas cuando tengamos eso listo.
			}
		});

		JButton btnUnirse = new JButton("Unirse");
		btnUnirse.setBounds(68, 309, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnUnirse);
		btnUnirse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					if (!salaSeleccionada.equals(null)) {
						ingresoaSalaOSeFue = true;
						abrirVentanaSala(salaSeleccionada);
					}
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Por favor, seleccionar sala","Sala no seleccionada",JOptionPane.WARNING_MESSAGE);
				}
				//Pequeno fix, porque el titulo de la sala es nombresala no nombresala(1/5) por ejemplo.
				/*El formateo del dato debe ser responsabilidad del controlador, o de la vista, no del modelo
				en este caso el modelo seria el servidor.*/
				salaSeleccionada = salaSeleccionada.substring(0, salaSeleccionada.indexOf('('));
				String datos = ingresarASala(salaSeleccionada); //Envio peticion de ingreso a la sala.
				ingresoaSalaOSeFue = true;
				abrirVentanaSala(datos, salaSeleccionada);
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(267, 309, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnVolver);
		setLocationRelativeTo(this.ventanaMenu);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sonido click = new Sonido(Param.GOLPE_PATH);
				click.reproducir();
				
				ingresoaSalaOSeFue = true;
				ventanaMenu.setVisible(true);
				dispose();
			}
		});

		//Pido las salas
		Main.getconexionServidorBackOff().avisarAlServerActualizacionSalas(Param.REQUEST_INGRESO_VENTANA_UNIR_SALA);
		//Se supone que en esta instancia las salas ya las tiene almacenadas el Main del cliente
		ArrayList<String> salas = Main.getDatosDeSalas();
		
		if(salas!=null)
		{
			for (String s : salas) 
			{
				String[] campos = s.split(Param.SEPARADOR_EN_MENSAJES);
				String salida = campos[0] + "(" + campos[1] + "/" + campos[2] + ")";
				this.modelDeSalasDisponibles.addElement(salida);
			}
		}


		if (this.modelDeSalasDisponibles.isEmpty()) {
			DefaultListModel<String> noHaySalas = new DefaultListModel<>();
			noHaySalas.addElement("No hay niguna sala");
			this.listSalas.setModel(noHaySalas);
			this.listSalas.setEnabled(false);
		} else {
			this.listSalas.setModel(this.modelDeSalasDisponibles);
			this.listSalas.setEnabled(true);
		}
		
		this.setVisible(true);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	if (JOptionPane.showConfirmDialog(contentPane, Param.MENSAJE_CERRAR_VENTANA, Param.TITLE_CERRAR_VENTANA, 
		                JOptionPane.YES_NO_OPTION,
		                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		    	Main.getConexionServidor().cerrarSesionUsuario(ventanaMenu.getUsuario());
		    	System.exit(0);
		    	}
		    }
		});

	}
	
	private String ingresarASala(String salaSeleccionada)
	{
		return Main.getConexionServidor().unirseASala(salaSeleccionada);
	}
	
	//Creo otro metodo por si las dudas.
	//Datos es la respuesta que contiene "cantidadUsuariosSala;CantidadUsuariosMaximoSala;usuario1,usuario2,usuario3.
	private void abrirVentanaSala(String datos, String salaSeleccionada) {
		String[] datosArray = datos.split(";");
		ArrayList<String> datosSala = new ArrayList<>();
		datosSala.add(salaSeleccionada);
		datosSala.add(datosArray[0]);
		datosSala.add(datosArray[1]);
		datosSala.add(datosArray[2]);
		
		Sonido musicaFondo = new Sonido(Param.GOLPE_PATH);
		musicaFondo.reproducir();
		new VentanaSala(this, datosSala, Param.UNION_SALA).setVisible(true);
	}

	private void abrirVentanaSala(String salaSeleccionada) {
		ArrayList<String> datosSala = new ArrayList<>();
		datosSala.add(salaSeleccionada);
		
		Sonido musicaFondo = new Sonido(Param.GOLPE_PATH);
		musicaFondo.reproducir();
		
		new VentanaSala(this, datosSala, Param.UNION_SALA).setVisible(true);
	}

}

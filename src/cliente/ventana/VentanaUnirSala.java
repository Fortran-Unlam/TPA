package cliente.ventana;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import cliente.ConexionServidor;
import cliente.Main;
import config.Param;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class VentanaUnirSala extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaMenu ventanaMenu;
	private JList<String> listSalas;
	private String salaSeleccionada;
	private boolean ingresoaSalaOSeFue = false;
	private DefaultListModel<String> modelDeListas;
	
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
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(267, 309, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnVolver);
		setLocationRelativeTo(this.ventanaMenu);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ingresoaSalaOSeFue = true;
				ventanaMenu.setVisible(true);
				dispose();
			}
		});


		this.modelDeListas = pedirSalas();

		if (this.modelDeListas.isEmpty()) {
			DefaultListModel<String> noHaySalas = new DefaultListModel<>();
			noHaySalas.addElement("No hay niguna sala");
			this.listSalas.setModel(noHaySalas);
			this.listSalas.setEnabled(false);
		} else {
			this.listSalas.setModel(this.modelDeListas);
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
		
		//Ac� debemos crear el thread de sincronizacion para refrescar las salas
//		Thread threadSync = new Thread() {
//			public void run() {
//
//				String salaNueva;
//				while(true && !ingresoaSalaOSeFue) {
//					salaNueva = Main.getConexionServidor().recibirActualizacionDeSala();
//					if(salaNueva != null) {
//						String[] datosSalaNueva =  salaNueva.split(Param.SEPARADOR_EN_MENSAJES);
//						String estoVaAlModelDelList = datosSalaNueva[0] + "(" + datosSalaNueva[1] + "/" + datosSalaNueva[2] + ")";
//						modelDeListas.addElement(estoVaAlModelDelList);	
//						System.out.println("Che me enter� que crearon una sala nueva");
//						System.out.println("Fue: " + estoVaAlModelDelList);
//					}
//						
//				}
//			}
//		};
//		threadSync.start();
		

	}

	private void abrirVentanaSala(String salaSeleccionada) {
		ArrayList<String> datosSala = new ArrayList<>();
		datosSala.add(salaSeleccionada);
		new VentanaSala(this, datosSala, Param.UNION_SALA).setVisible(true);
	}

	public DefaultListModel<String> pedirSalas() {
		ArrayList<String> salas = Main.getConexionServidor().getAllSalas();
		DefaultListModel<String> modelSalasActivas = new DefaultListModel<String>();

		/* Reflejo 04/11 si se hace la secuencia crear una sala, salir de la sala e intentar
		 * unirse a una sala, todo esto con un mismo usuario, salta la excepcion
		 * java.lang.NullPointerException se tiene que comprobar al momento de pedirSalas
		 * que realmente existan salas, antes de iterar por ellas.
		 * Quiza esta mal solucionado el error, revisar.
		 */
		if(salas!=null)
		{
			for (String s : salas) 
			{
				String[] campos = s.split(Param.SEPARADOR_EN_MENSAJES);
				String salida = campos[0] + "(" + campos[1] + "/" + campos[2] + ")";
				modelSalasActivas.addElement(salida);
			}
		}
		

		return modelSalasActivas;
	}

}

package cliente.ventana;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import cliente.Cliente;
import cliente.Sonido;
import config.Param;
import javax.swing.JTable;

public class VentanaUnirSala extends JFrame {

	private static final long serialVersionUID = -7041821010096850636L;
	private JPanel contentPane;
	private VentanaMenu ventanaMenu;
	private String nombreSalaSeleccionada;
	private TableModelSalas tableModelSalas = new TableModelSalas();
	private JTable tableSalas;
	private JButton btnUnirse;
	private JButton btnVolver;

	public VentanaUnirSala(VentanaMenu ventanaMenu) {
		this.ventanaMenu = ventanaMenu;

		setTitle("Unirse a sala");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setBounds(0, 0, 456, 400);
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

		btnUnirse = new JButton("Unirse");
		btnUnirse.setBounds(68, 309, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnUnirse);
		setLocationRelativeTo(this.ventanaMenu);

		btnVolver = new JButton("Volver");
		btnVolver.setBounds(267, 309, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnVolver);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 92, 403, 207);
		contentPane.add(scrollPane);

		tableSalas = new JTable();
		scrollPane.setViewportView(tableSalas);

		this.addListener();

	}

	private void addListener() {
		btnUnirse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!(nombreSalaSeleccionada == null)) {
					unirseASala(nombreSalaSeleccionada);
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, seleccionar sala", "Sala no seleccionada",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		tableSalas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					if (!(nombreSalaSeleccionada == null)) {
						unirseASala(nombreSalaSeleccionada);
					} else {
						JOptionPane.showMessageDialog(null, "Por favor, seleccionar sala", "Sala no seleccionada",
								JOptionPane.WARNING_MESSAGE);
					}
				} else {
					int filaSeleccionada = tableSalas.rowAtPoint(event.getPoint());
					nombreSalaSeleccionada = (String) tableSalas.getValueAt(filaSeleccionada, 0);
					
				}
			}
			
		});
		
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sonido click = new Sonido(Param.SONIDO_GOLPE_PATH);
				click.reproducir();
				ventanaMenu.setVisible(true);
				setVisible(false);
			}
		});
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(contentPane, Param.MENSAJE_CERRAR_VENTANA, Param.TITLE_CERRAR_VENTANA,
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					Cliente.getConexionServidor().cerrarSesionUsuario(ventanaMenu.getUsuario());
					System.exit(0);
				}
			}
		});
	}

	private void unirseASala(String nombreSala) {
		// le paso el nombre
		if (Cliente.getConexionServidor().unirseASala(nombreSala)) {
			VentanaSala ventanaSala = new VentanaSala(ventanaMenu, false, nombreSala);
			Sincronismo.setVentanaSala(ventanaSala);
			
			JsonObject paqueteUnirSala = Json.createObjectBuilder().add("type", Param.NOTICE_UNION_SALA)
					.add("sala", nombreSala).build();
			
			Cliente.getconexionServidorBackOff().enviarAlServer(paqueteUnirSala);

			this.setVisible(false);
			Sonido musicaFondo = new Sonido(Param.SONIDO_GOLPE_PATH);
			musicaFondo.reproducir();
			ventanaSala.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "Sala llena o con partida en curso, por favor seleccione otra.",
					"Error al ingresar", JOptionPane.WARNING_MESSAGE);
		}
	}

	// Metodo que usa el Thread para refrescarle las salas a la ventana.
	public void refrescarListaDeSalas(JsonArray datosDeSalasDisponibles) {
		String data[][] = new String[datosDeSalasDisponibles.size()][3];

		for (int i = 0; i < datosDeSalasDisponibles.size(); i++) {

			data[i][0] = datosDeSalasDisponibles.getJsonObject(i).getString("nombre");
			data[i][1] = (datosDeSalasDisponibles.getJsonObject(i).getString("cantidadUsuariosActivos") + "/"
					+ datosDeSalasDisponibles.getJsonObject(i).getString("cantidadUsuariosMaximos"));
			data[i][2] = datosDeSalasDisponibles.getJsonObject(i).getString("administrador");
		}

		if (datosDeSalasDisponibles.isEmpty()) {
			this.tableModelSalas.setTableEmpty();
			this.tableModelSalas.fireTableStructureChanged();
		} else {
			this.tableModelSalas.setData(data);
		}

		this.tableModelSalas.fireTableDataChanged();
		this.tableSalas.setModel(this.tableModelSalas);
	}
}

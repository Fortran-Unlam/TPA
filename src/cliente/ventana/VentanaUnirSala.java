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

		JButton btnUnirse = new JButton("Unirse");
		btnUnirse.setBounds(68, 309, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnUnirse);
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
		setLocationRelativeTo(this.ventanaMenu);

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(267, 309, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnVolver);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 92, 403, 207);
		contentPane.add(scrollPane);

		tableSalas = new JTable();
		tableSalas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int filaSeleccionada = tableSalas.rowAtPoint(e.getPoint());
				nombreSalaSeleccionada = (String) tableSalas.getValueAt(filaSeleccionada, 0);
			}
		});
		scrollPane.setViewportView(tableSalas);

		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sonido click = new Sonido(Param.GOLPE_PATH);
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
		JsonObject paqueteIngresoVentanaUnirSala = Json.createObjectBuilder()
				.add("type", Param.REQUEST_INGRESO_VENTANA_UNIR_SALA).build();
		// Le aviso al sv que me actualice las salas, el cliente se las auto-actualiza
		Cliente.getconexionServidorBackOff().enviarAlServer(paqueteIngresoVentanaUnirSala);

	}

	private void unirseASala(String nombreSala) {
		// le paso el nombre
		Cliente.getConexionServidor().unirseASala(nombreSala);
		
		JsonObject paqueteUnirSala = Json.createObjectBuilder().add("type", Param.NOTICE_UNION_SALA)
				.add("nombreSala", nombreSala).build();
		Cliente.getconexionServidorBackOff().enviarAlServer(paqueteUnirSala);
		VentanaSala ventanaSala = new VentanaSala(this, false, nombreSala);
		Sincronismo.setVentanaSala(ventanaSala);
		
		JsonObject paqueteActualizarSalaParticular = Json.createObjectBuilder().add("type", Param.NOTICE_REFRESCAR_USUARIOS_PARTICULAR)
				.add("sala", nombreSala).build();
		Cliente.getconexionServidorBackOff().enviarAlServer(paqueteActualizarSalaParticular);
		Sonido musicaFondo = new Sonido(Param.GOLPE_PATH);
		musicaFondo.reproducir();

		ventanaSala.setVisible(true);
	}

	// Metodo que usa el Thread para refrescarle las salas a la ventana.
	public void refrescarListaDeSalas(JsonArray datosDeSalasDisponibles) {
		String data[][] = new String[datosDeSalasDisponibles.size()][3];

		System.out.println(data);
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

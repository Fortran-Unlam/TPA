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

import cliente.Cliente;
import cliente.Sonido;
import config.Param;
import javax.swing.JSeparator;
import javax.swing.JTable;

public class VentanaUnirSala extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaMenu ventanaMenu;
	private String salaSeleccionada;
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

				if (!salaSeleccionada.equals(null)) {
					// unirseASala(salaSeleccionada);
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

		// Le aviso al sv que me actualice las salas, el cliente se las auto-actualiza
		Cliente.getconexionServidorBackOff()
				.avisarAlSvQueMandeActualizacionSalas(Param.REQUEST_INGRESO_VENTANA_UNIR_SALA);

	}

//	private void unirseASala(String salaSeleccionada) {
//		Cliente.getConexionServidor().unirseASala(salaSeleccionada);
//		datosSala.add(salaSeleccionada);
//		datosSala.add(datosArray[0]);
//		datosSala.add(datosArray[1]);
//		datosSala.add(datosArray[2]);
//
//		Sonido musicaFondo = new Sonido(Param.GOLPE_PATH);
//		musicaFondo.reproducir();
//		new VentanaSala(this, datosSala, Param.UNION_SALA).setVisible(true);
//	}

	// Metodo que usa el Thread para refrescarle las salas a la ventana.
	public void refrescarListaDeSalas(ArrayList<String> datosDeSalasDisponibles) {

		Object data[][] = new Object[datosDeSalasDisponibles.size()][3];

		for (int i = 0; i < datosDeSalasDisponibles.size(); i++) {
			String[] campos = datosDeSalasDisponibles.get(i).split(Param.SEPARADOR_EN_MENSAJES);
			data[i][0] = campos[0]; // nombre sala
			data[i][1] = campos[1]; // usrConectados/UsrMax
			data[i][2] = campos[2]; // admin
		}

		if (datosDeSalasDisponibles.isEmpty()) {
			this.tableModelSalas.setTableEmpty();
		} else {
			this.tableModelSalas.setData(data);
			this.tableModelSalas.fireTableDataChanged();
		}
		
		this.tableSalas.setModel(this.tableModelSalas);
	}
}

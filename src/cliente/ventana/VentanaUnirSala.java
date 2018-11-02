package cliente.ventana;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import cliente.Main;
import config.Param;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.JList;
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
	public String salaSeleccionada;

	public VentanaUnirSala(VentanaMenu ventanaMenu) {
		this.ventanaMenu = ventanaMenu;
		ventanaMenu.setVisible(false);

		setTitle("Unirse a sala");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(0, 0, Param.VENTANA_CLIENTE_WIDTH, Param.VENTANA_CLIENTE_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);

		this.listSalas = new JList<>();
		listSalas.setBackground(SystemColor.control);
		listSalas.setBorder(null);
		listSalas.setBounds(10, 98, 438, 209);
		listSalas.setEnabled(true);
		contentPane.add(listSalas);

		listSalas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 1) {
					salaSeleccionada = ((String) listSalas.getSelectedValue());
				}
			}
		});

		JLabel lblSalasDisponibles = new JLabel("Salas disponibles:");
		lblSalasDisponibles.setForeground(Color.MAGENTA);
		lblSalasDisponibles.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblSalasDisponibles.setBounds(10, 54, 190, 27);
		contentPane.add(lblSalasDisponibles);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(12, 98, 454, 209);
		contentPane.add(scrollPane);

		JButton btnRefrescarSalas = new JButton("Refrescar");
		btnRefrescarSalas.setBounds(267, 48, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnRefrescarSalas);

		JButton btnUnirse = new JButton("Unirse");
		btnUnirse.setBounds(68, 309, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnUnirse);

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(267, 309, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnVolver);
		setLocationRelativeTo(this.ventanaMenu);

		btnRefrescarSalas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Ver como refrescar salas cuando tengamos eso listo.
			}
		});

		btnUnirse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaSala(salaSeleccionada);
			}
		});

		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ventanaMenu.setVisible(true);
				dispose();
			}
		});

		// Apenas se construye, mando a pedir las salas al server.
		DefaultListModel<String> modelDeSalasActivasQueMeDioElServer = pedirSalas();

		if (modelDeSalasActivasQueMeDioElServer.isEmpty()) {
			DefaultListModel<String> noHaySalas = new DefaultListModel<>();
			noHaySalas.addElement("No hay niguna sala");
			this.listSalas.setModel(noHaySalas);
			this.listSalas.setEnabled(false);
		} else {
			this.listSalas.setModel(modelDeSalasActivasQueMeDioElServer);
			this.listSalas.setEnabled(true);
		}

		this.setVisible(true);

	}

	private void abrirVentanaSala(String salaSeleccionada) {
		new VentanaSala(this, null).setVisible(true);
	}

	public DefaultListModel<String> pedirSalas() {
		ArrayList<String> salas = Main.getConexionServidor().getAllSalas();
		DefaultListModel<String> modelSalasActivas = new DefaultListModel<String>();

		for (int i = 0; i < salas.size(); i++) {
			modelSalasActivas.addElement(salas.get(i));
		}

		return modelSalasActivas;
	}

}

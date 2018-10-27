package view.sala;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import looby.Sala;

public class VentanaCreacionSala extends JFrame {

	private static final long serialVersionUID = 490509587271361339L;
	private JTextField nombreSala;
	private JTextField maxJugadores;
	private VentanaSala ventanaSala;
	
	
	public VentanaCreacionSala() {
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(12, 80, 189, 251);
		getContentPane().add(panel);

		JList list = new JList();
		list.setValueIsAdjusting(true);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] { "as", "j", "h" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setSize(100, 100);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel.add(list);

		this.nombreSala = new JTextField();
		this.nombreSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createSubmit();
			}
		});
		this.nombreSala.setBounds(297, 154, 131, 20);
		getContentPane().add(this.nombreSala);
		this.nombreSala.setColumns(10);

		JButton btnCrearSala = new JButton("Crear Sala");
		btnCrearSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createSubmit();
			}
		});
		btnCrearSala.setBounds(213, 282, 201, 23);
		getContentPane().add(btnCrearSala);

		this.maxJugadores = new JTextField();
		this.maxJugadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createSubmit();
			}
		});
		this.maxJugadores.setBounds(297, 198, 60, 20);
		getContentPane().add(this.maxJugadores);
		this.maxJugadores.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(219, 153, 60, 20);
		getContentPane().add(lblNombre);

		JLabel lblJugMax = new JLabel("Jug. Max");
		lblJugMax.setBounds(219, 199, 60, 17);
		getContentPane().add(lblJugMax);
		
		JLabel lblCrearSala = new JLabel("Crear Sala");
		lblCrearSala.setBounds(179, 12, 100, 15);
		getContentPane().add(lblCrearSala);
		
		setVisible(true);
		setBounds(121, 111, 456, 373);
	}

	/**
	 * Evento que se ejecuta cuando se envia el formulario para crear la sala
	 */
	protected void createSubmit() {
		if (this.nombreSala.getText().isEmpty()) {
			System.out.println("falta nombre");
			return;
		}

		if (this.maxJugadores.getText().isEmpty()) {
			System.out.println("falta maximo de jugadores");
			return;
		}
		
		this.dispose();//Cierro la ventana de creacion de sala
		
		this.ventanaSala = new VentanaSala(); //Creo una nueva ventana
		ventanaSala.setNombreYMaxUsuarios(this.nombreSala.getText(),this.maxJugadores.getText()); //le paso el nombres y el maxJugadores
		
	}
}

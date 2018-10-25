package view.sala;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Index extends JFrame {

	private static final long serialVersionUID = 490509587271361339L;
	private JTextField nombre;
	private JTextField maxJugadores;

	public Index() {
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(21, 11, 189, 251);
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

		this.nombre = new JTextField();
		this.nombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createSubmit();
			}
		});
		this.nombre.setBounds(290, 11, 131, 20);
		getContentPane().add(this.nombre);
		this.nombre.setColumns(10);

		JButton btnCrearSala = new JButton("Crear Sala");
		btnCrearSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createSubmit();
			}
		});
		btnCrearSala.setBounds(220, 118, 201, 23);
		getContentPane().add(btnCrearSala);

		this.maxJugadores = new JTextField();
		this.maxJugadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createSubmit();
			}
		});
		this.maxJugadores.setBounds(290, 42, 60, 20);
		getContentPane().add(this.maxJugadores);
		this.maxJugadores.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(220, 11, 60, 20);
		getContentPane().add(lblNombre);

		JLabel lblJugMax = new JLabel("Jug. Max");
		lblJugMax.setBounds(220, 44, 60, 17);
		getContentPane().add(lblJugMax);
	}

	/**
	 * Evento que se ejecuta cuando se envia el formulario para crear la sala
	 */
	protected void createSubmit() {
		if (this.nombre.getText().isEmpty()) {
			System.out.println("falta nombre");
			return;
		}

		if (this.maxJugadores.getText().isEmpty()) {
			System.out.println("falta maximo de jugadores");
			return;
		}
		// TODO: hacer un new sala y poner el jframe
	}
}

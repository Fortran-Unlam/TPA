package view.sala;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

public class Index extends JFrame {
	public Index() {
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(21, 11, 189, 251);
		getContentPane().add(panel);
		
		JList list = new JList();
		list.setValueIsAdjusting(true);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"as", "j", "h"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel.add(list);
		
		textField = new JTextField();
		textField.setBounds(290, 11, 131, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnCrearSala = new JButton("Crear Sala");
		btnCrearSala.setBounds(220, 118, 201, 23);
		getContentPane().add(btnCrearSala);
		
		textField_1 = new JTextField();
		textField_1.setBounds(290, 42, 60, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(220, 11, 60, 20);
		getContentPane().add(lblNombre);
		
		JLabel lblJugMax = new JLabel("Jug. Max");
		lblJugMax.setBounds(220, 44, 60, 17);
		getContentPane().add(lblJugMax);
	}

	private static final long serialVersionUID = 490509587271361339L;
	private JTextField textField;
	private JTextField textField_1;
}

package cliente.ventana;

import javax.swing.table.AbstractTableModel;

public class TableModelSalas extends AbstractTableModel {

	private String[] columnNames;
	private String[][] data;
	
	public TableModelSalas() {
		
	}
	/**
	 * Usar este setter para mostrar un mensaje de lista vacia
	 * @param messageEmptyData
	 */
	public void setTableEmpty() {
		String noHaySalas = "NO HAY SALAS DISPONIBLES";
		this.columnNames = new String[1];
		this.columnNames[0] = noHaySalas;
		this.data = null;
	}
	
	public void setData(String[][] data) {
		this.columnNames = new String[3];
		this.columnNames[0] = "Sala";
		this.columnNames[1] = "Disponibilidad";
		this.columnNames[2] = "Admin";
		this.data = data;
	}
		
	@Override
	public int getColumnCount() {
		if(columnNames == null)
			return 0;
		
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		if(data == null)
			return 0;
		
		return data.length;
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	
	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	public boolean isEmpty() {
		if(getRowCount() ==0)
			return true;
		return false;
	}
	

}

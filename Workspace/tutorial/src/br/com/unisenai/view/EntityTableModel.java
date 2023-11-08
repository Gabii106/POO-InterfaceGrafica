package br.com.unisenai.view;

import java.util.List;

import javax.swing.table.DefaultTableModel;

public abstract class EntityTableModel<E> extends DefaultTableModel {
	
private static final long serialVersionUID = 1L;
	
	public EntityTableModel(List<E> elements, List<String> columnNames) {
		setColumnIdentifiers(columnNames.toArray());
		insertRows(elements);
	
	}
	
	private  void insertRows(List<E> elements){
		elements.forEach(element ->{
			addRow(element);
		});
	}
	
	
	public void addRow(E element) {
		Object[] row = new Object[getColumnCount()];
		for(int col = 0; col < getColumnCount(); col++) {
			row[col] = getValue(element, getColumnName(col));
		}
		addRow(row);
	}
	
	public  abstract Object getValue(E element,String columnName);
	
	public abstract void editRow(E element, int position);

}

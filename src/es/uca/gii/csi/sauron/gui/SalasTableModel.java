package es.uca.gii.csi.sauron.gui;

import java.util.ArrayList;

import es.uca.gii.csi.sauron.data.Sala;

public class SalasTableModel extends javax.swing.table.AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private ArrayList<Sala> _aData;
	public SalasTableModel(ArrayList<Sala> aData){
		_aData = aData;
	}
	
	@Override
	public int getRowCount() {
		return _aData.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Object getValueAt(int iRow, int iCol) {
		switch(iCol){
			case 0:return _aData.get(iRow).getNombre();
			case 1:return _aData.get(iRow).getCapacidadActual();
			case 2:return _aData.get(iRow).getEslogan();
			default:throw new IllegalStateException("Already connected");
		}
	}

	public Sala getData(int iRow) throws Exception{
		return _aData.get(iRow);
	}
}
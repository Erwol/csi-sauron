package es.uca.gii.csi.sauron.gui;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import es.uca.gii.csi.sauron.data.TipoSala;

public class TipoSalaListModel 
	extends AbstractListModel<TipoSala>
		implements ComboBoxModel<TipoSala>{

	private static final long serialVersionUID = 1L;
	private List<TipoSala> _aData;
	private Object _selection = null;
	
	public TipoSalaListModel(List<TipoSala> aData){
		_aData = aData;
	}
	
	@Override
	public TipoSala getElementAt(int iRow) {
		// TODO Auto-generated method stub
		return _aData.get(iRow);
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return _aData.size();
	}
	
	public void setSelectedItem(Object o) { _selection = o; }
	public Object getSelectedItem() { return _selection; }

}

package es.uca.gii.csi.sauron.data;

import java.sql.Connection;
import java.sql.Types;

public abstract class Entity {
	private int _iId;
	private boolean _bIsDeleted;
	private String _sTable;
	
	public Entity(int iId, String sTable){
		_iId = iId;
		_sTable = sTable;
		_bIsDeleted = false;
	}
	
	protected int getId(){
		return _iId;
	}
	
	protected void setId(int iId){
		_iId = iId;
	}
	
	// Getter públicos para permitir a los tests acceder a bIsDeleted
	public boolean getIsDeleted(){
		return _bIsDeleted;
	}
	
	protected void setIsDeleted(boolean bIsDeleted){
		_bIsDeleted = bIsDeleted;
	}
	
	protected void Update(String sQuery) throws Exception{
		Connection con = null;
		try{
			con = Data.Connection();
			con.createStatement().executeUpdate(sQuery);
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(con != null)
				con.close();
		}
	}
	
	public void Delete() throws Exception{
		if (_bIsDeleted)
			throw new Exception("Este elemento ya ha sido eliminado de la base de datos.");
		Connection con = null;
		try{
			con = Data.Connection();
			String sIns = "DELETE FROM " + _sTable + " WHERE Id = " + _iId + ";";
			con.createStatement().executeUpdate(sIns);
			_bIsDeleted = true;
		}catch(Exception e){
			throw e;
		}
		finally{
			if(con != null)
				con.close();
		}
	}
	
    protected static String Where(String[] sCampos, int[] iTipos, Object[] oValores){
    	StringBuilder sBuilder = new StringBuilder();
    	String sQuery = "";
    	for(int i = 0; i < sCampos.length; i++)
			if(iTipos[i] == Types.INTEGER && Integer.parseInt(oValores[i].toString()) != -1)
				sBuilder.append(sCampos[i]).append("= ").append(oValores[i]).append(" AND");
			else if(iTipos[i] == Types.VARCHAR && oValores[i] != null)
				sBuilder.append(sCampos[i]).append("LIKE ").append(
						Data.String2Sql(oValores[i].toString(), true, true)).append(" AND");
    	if(sBuilder.length() != 0)
			sQuery = "WHERE" + sBuilder.substring(0, sBuilder.length() - 4);
		return oValores[3] == null ? sQuery + ";" : sQuery;
	}
}

package es.uca.gii.csi.sauron.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TipoSala extends Entity{
	private int _iCapacidadTotal;
	private String _sNombre, _sDescripcion;
	
	private void Initialize(int iId, Connection con) throws Exception{
		ResultSet rs = null;
		try{
			String sQuery = "SELECT CapacidadTotal, Nombre, Descripcion "
					+ "FROM tiposala WHERE Id = " + iId + ";";
			rs = con.createStatement().executeQuery(sQuery);
			rs.next();
			setCapacidadTotal(rs.getInt("CapacidadTotal"));
			setNombre(rs.getString("Nombre"));
			setDescripcion(rs.getString("Descripcion"));
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(rs != null)
				rs.close();
		}
	}
	
	public TipoSala(int iId, Connection con) throws Exception{
		super(iId, "tiposala");
		Initialize(iId, con);
	}
	
	public static TipoSala Create(int iCapacidadTotal, String sNombre, String sDescripcion)throws Exception{
		Connection con = null;
		try{
			con = Data.Connection();
			String sQuery = "INSERT INTO tiposala (CapacidadTotal, Nombre, Descripcion) VALUES (" +
				iCapacidadTotal + ", " + Data.String2Sql(sNombre, true, false) + 
				", " + Data.String2Sql(sDescripcion, true, false) + ");";
			con.createStatement().executeUpdate(sQuery);
			return new TipoSala(Data.LastId(con), con);
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(con != null)
				con.close();
		}
	}
	
	public String toString(){
    	return _sNombre;
    }
	
	public void Update()throws Exception{
		if(getIsDeleted())
			throw new Exception("No puede actualizar esta sala, ya que ha sido eliminada.");
		String sQuery = "UPDATE tiposala SET CapacidadTotal = " + getCapacidadTotal() + 
			", Nombre = " + Data.String2Sql(getNombre(), true, false) + 
			", Descripcion = " + Data.String2Sql(getDescripcion(), true, false) + 
			" Where Id = " + super.getId() + ";";
		super.Update(sQuery);
	}
	
	public static ArrayList<TipoSala> Select()throws Exception{
		ArrayList<TipoSala> aTipoSala = new ArrayList<TipoSala>();
		Connection con = null;
		ResultSet rs = null;
		try{
			con = Data.Connection();
			String sQuery = "SELECT Id FROM tiposala ORDER BY Nombre;";
			rs = con.createStatement().executeQuery(sQuery);
			while(rs.next())
				aTipoSala.add(new TipoSala(rs.getInt("Id"), con));		
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(con != null)
				con.close();
			if(rs != null)
				rs.close();
		}
		return aTipoSala;
	}
	
	public static ArrayList<TipoSala> Select(String sNombre, 
			String sDescripcion, int iCapacidadTotal)throws Exception{
		ArrayList<TipoSala> aTipoSala = new ArrayList<TipoSala>();
		Connection con = null;
		ResultSet rs = null;
		try{
			con = Data.Connection();
			String sQuery = "SELECT Id FROM tiposala";
			if(sNombre != null || sDescripcion != null || iCapacidadTotal != -1){
				sQuery += " WHERE";
				if(sNombre != null)
					sQuery += " Nombre LIKE " + Data.String2Sql(sNombre, true, true) + " AND";
				if(sDescripcion != null)
					sQuery += " Descripcion LIKE " + Data.String2Sql(sDescripcion, true, true) + (" AND");
				if(iCapacidadTotal != -1)
					sQuery += " CapacidadTotal LIKE " + iCapacidadTotal;
				else
					sQuery = sQuery.substring(0, sQuery.length() - 5);
			}
			
			sQuery += ";";
			rs = con.createStatement().executeQuery(sQuery);
			while(rs.next())
				aTipoSala.add(new TipoSala(rs.getInt("Id"), con));
			return aTipoSala;
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(con != null)
				con.close();
			if(rs != null)
				rs.close();
		}
	}

	public int getCapacidadTotal() {
		return _iCapacidadTotal;
	}

	public void setCapacidadTotal(int icapTotal) {
		_iCapacidadTotal = icapTotal;
	}

	public String getNombre() {
		return _sNombre;
	}

	public void setNombre(String snombreTipo) {
		_sNombre = snombreTipo;
	}

	public String getDescripcion() {
		return _sDescripcion;
	}

	public void setDescripcion(String sDescripcion) {
		_sDescripcion = sDescripcion;
	}
}

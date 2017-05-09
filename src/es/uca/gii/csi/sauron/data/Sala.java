package es.uca.gii.csi.sauron.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

public class Sala extends Entity{
	private int _iCapacidadActual;
    private String _sEslogan, _sNombre;
    private TipoSala _tipoSala;
    
    private void Initialize(int iId, Connection con) throws Exception{
    	ResultSet rs = null;
    	String sQuery = null;
    	try{
    		sQuery = "SELECT Id_TipoSala, Nombre, Eslogan, "
    				+ "CapacidadActual FROM sala WHERE Id = " + super.getId() + ";";
    		rs = con.createStatement().executeQuery(sQuery);
    		rs.next();
    		setNombre(rs.getString("Nombre"));
    		setEslogan(rs.getString("Eslogan"));
    		setCapacidadActual(rs.getInt("CapacidadActual"));
    		setTipoSala(new TipoSala(rs.getInt("Id_TipoSala"), con));
    	}
    	catch(Exception e){
    		throw e;
    	}
    	finally{
    		if(rs != null)
    			rs.close();
    	}
    }
    
    public Sala(int iId, Connection con)throws Exception{
    	super(iId, "sala");
    	Initialize(iId, con);
    }
    
    private Sala(int iId, int iCapacidadTotal, String sNombre, String sEslogan, 
    		int Id_TipoSala, Connection con) throws Exception{
    	super(iId, "sala");
    	setNombre(sNombre);
    	setEslogan(sEslogan);
    	setCapacidadActual(iCapacidadTotal);
    	_tipoSala = new TipoSala(Id_TipoSala, con);
    }
    
    public static Sala Create(int iCapacidadActual, String sNombre, String sEslogan, 
    		TipoSala tipoSala)throws Exception{
    	String sQuery = "INSERT INTO sala (Id_TipoSala, Nombre, Eslogan, CapacidadActual)" + 
    					"VALUES (" + tipoSala.getId() + ", " + Data.String2Sql(sNombre, true, false) + "," + 
    					Data.String2Sql(sEslogan, true, false) + "," + iCapacidadActual + ");";
    	Connection con = null;
    	try{
    		con = Data.Connection();
    		con.createStatement().executeUpdate(sQuery);
    		return new Sala(Data.LastId(con), con);
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
    	return super.toString() + ":" + _sNombre + ":" + _sEslogan;
    }
    
    public void Update()throws Exception{
    	if(super.getIsDeleted())
    		throw new Exception("La sala " + _sNombre + " ya ha sido eliminada y por tanto no se puede actualizar.");
    	String sQuery = "Update sala SET Id_TipoSala = " + _tipoSala.getId() + 
			", CapacidadActual = " + _iCapacidadActual + " , Nombre = " +
			Data.String2Sql(_sNombre, true, false) + ", Eslogan = " + 
			Data.String2Sql(_sEslogan, true, false) + " WHERE Id = " + super.getId() + ";";
    	super.Update(sQuery);
    }
    
	public static ArrayList<Sala> Select(int iCapacidadActual, String sNombre, 
			String sEslogan, String sTipoSala)throws Exception{
		ArrayList<Sala> aSala = new ArrayList<Sala>();
		Connection con = null;
		ResultSet rs = null;
		String sQuery = null;
		try{
			con = Data.Connection();
			if(sTipoSala != null)
				sQuery = "SELECT sala.Id, sala.Id_TipoSala, sala.Nombre, sala.Eslogan, "
						+ "sala.CapacidadActual FROM sala INNER JOIN tiposala "
						+ "ON sala.Id_TipoSala = tiposala.Id "
						+ Entity.Where(
							new String[] { " sala.CapacidadActual ", " sala.Nombre ",
									" sala.Eslogan ", " tiposala.Nombre "},
							new int[] { Types.INTEGER, Types.VARCHAR,
									Types.VARCHAR, Types.VARCHAR},
							new Object[] {iCapacidadActual, sNombre, sEslogan, sTipoSala});
			else
				sQuery = "SELECT sala.Id, sala.Id_TipoSala, sala.Nombre, sala.Eslogan, "
						+ "sala.CapacidadActual FROM sala " 
						+ Entity.Where(
							new String[] { " sala.CapacidadActual ", " sala.Nombre ",
									" sala.Eslogan ", " tiposala.Nombre "},
							new int[] { Types.INTEGER, Types.VARCHAR,
									Types.VARCHAR, Types.VARCHAR},
							new Object[] {iCapacidadActual, sNombre, sEslogan, sTipoSala});
			rs = con.createStatement().executeQuery(sQuery);
			while(rs.next())
				aSala.add(new Sala(rs.getInt("Id"), rs.getInt("CapacidadActual"), rs.getString("Nombre"), 
					rs.getString("Eslogan"), rs.getInt("Id_TipoSala"), con));
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(rs != null)
				rs.close();
			if(con != null)
				con.close();
		}
		return aSala;
	}
	
	public int getCapacidadActual() {
		return _iCapacidadActual;
	}
	
	public void setCapacidadActual(int icapActual) {
		_iCapacidadActual = icapActual;
	}
	
	public String getEslogan() {
		return _sEslogan;
	}
	
	public void setEslogan(String sEslogan) {
		_sEslogan = sEslogan;
	}
	
	public int getId(){
		return super.getId();
	}
	
	public String getNombre() {
		return _sNombre;
	}
	
	public void setNombre(String sNombre) {
		_sNombre = sNombre;
	}
	
	public TipoSala getTipo() {
		return _tipoSala;
	}
	
	public void setTipoSala(TipoSala tipoSala) {
		_tipoSala = tipoSala;
	}
}
package es.uca.gii.csi.sauron.data;
 
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
 
import es.uca.gii.csi.sauron.util.Config;
 
public class Data {
	
    public static String getPropertiesUrl() { 
    	//Devuelve la ruta del alchivo donde almacenamos las propiedads de nuestra DB
    	return "./db.properties"; 
    }
    
    public static Connection Connection() throws Exception {
        try {
        	//Establecemos conexión con la DB dando uso a la configuración que tenemos almacenada en db.properties
            Properties properties = Config.Properties(getPropertiesUrl());
            return DriverManager.getConnection(
                properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.username"),
                properties.getProperty("jdbc.password"));
       }
       catch (Exception ee) { throw ee; }
    }
   
    public static void LoadDriver()
        throws InstantiationException, IllegalAccessException,
        ClassNotFoundException, IOException {
            Class.forName(Config.Properties(Data.getPropertiesUrl()
            ).getProperty("jdbc.driverClassName")).newInstance();
    }
    
    public static String String2Sql(String s, boolean bAddQuotes, boolean bAddWildcards){
        s = s.replace("'", "''");       //Reemplaza todas las comillas simples
        if(bAddWildcards)
                s = "%" + s + "%";		//Añade % a los extremos
        if(bAddQuotes)
                s = "'" + s + "'";		//Añade ' a los extremos
        return s;
    }
   
    public static int Boolean2Sql(boolean b){
        return b ? 1 : 0;
    }
    
    public static int LastId(Connection con)throws Exception{
		ResultSet rs = null;
		try{
			Properties properties = Config.Properties(getPropertiesUrl());
			PreparedStatement getLastInsertId = con.prepareStatement(properties.getProperty("jdbc.lastIdSentence"));
			rs = getLastInsertId.executeQuery();
			rs.next();
			return rs.getInt(1);            
		}
    	
		catch(Exception e){
			throw e;
		}
		finally{
    		if(rs != null)
    			rs.close();
    	}
    }
}
package es.uca.gii.csi.sauron.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Ignore;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import es.uca.gii.csi.sauron.data.Data;

public class DataTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Data.LoadDriver();
	}
	@Ignore
	@Test
	public void testTableAccess() throws Exception {
		Connection con = null;
		ResultSet rs = null;
		try {
		         
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT count(*) FROM sala,"
					+ " tiposala WHERE sala.Id_TipoSala = tiposala.Id;");
			rs.next();
			int c = Integer.parseInt(rs.getString(1));
			System.out.println("Numero de Tuplas " + c);
			rs.close();
			rs = con.createStatement().executeQuery("SELECT * FROM sala,"
					+ " tiposala WHERE sala.Id_TipoSala = tiposala.Id;");
			int i = 0;
			int col = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				for(int j = 1; j <= col; j++)
					System.out.print(rs.getString(j)+" ");
				System.out.println();
				i++;
            }
            Assert.assertEquals(i,c);
        }
        catch (SQLException ee) { throw ee; }
        finally {
            if (rs != null) rs.close();
            if (con != null) con.close();
        }
	}
}

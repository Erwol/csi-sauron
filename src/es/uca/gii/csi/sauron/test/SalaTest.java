package es.uca.gii.csi.sauron.test;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import java.sql.Connection;

import es.uca.gii.csi.sauron.data.Data;
import es.uca.gii.csi.sauron.data.Sala;
import es.uca.gii.csi.sauron.data.TipoSala;

public class SalaTest {
	@BeforeClass
	public static void setUpBeforeClass()throws Exception{
		Data.LoadDriver();
	}
	@Ignore
	@Test
	public void testUpdate()throws Exception{
		Connection con = null;
		try{
			con = Data.Connection();
			TipoSala tipoSala = new TipoSala(1, con);
			Sala sala = Sala.Create(10, "NombreTest", "EsloganTest", tipoSala);
			//Comprobamos creaciÃ³n correcta
			Assert.assertEquals(sala.getNombre(), "NombreTest");
			Assert.assertEquals(sala.getEslogan(), "EsloganTest");
			Assert.assertEquals(sala.getCapacidadActual(), 10);
			//Modificamos variables
			sala.setNombre("Nombre actualizado");
			sala.setEslogan("Nuevo eslogan");
			sala.setCapacidadActual(23);
			//Actualizamos
			sala.Update();
			//Comprobamos la correcta actualizaciÃ³n
			Assert.assertEquals(sala.getNombre(), "Nombre actualizado");
			Assert.assertEquals(sala.getEslogan(), "Nuevo eslogan");
			Assert.assertEquals(sala.getCapacidadActual(), 23);
			sala.Delete();
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(con != null)
				con.close();
		}
	}
	
	@Test
	public void testConstructor()throws Exception{
		Connection con = null;
		try{
			con = Data.Connection();
			Sala sala = new Sala(214, con);	// Test sujeto a cambios en la BD
			Assert.assertEquals(sala.getNombre(), "Sangría Tortura");
			Assert.assertEquals(sala.getEslogan(), "Donde lo más pesado es tu alma");
			Assert.assertEquals(sala.getCapacidadActual(), 32);
			Assert.assertEquals(sala.getTipo().getCapacidadTotal(), 2000);	
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(con != null)
				con.close();
		}
	}
	
	@Test
	public void testDelete()throws Exception{
		Connection con = null;
		try{
			con = Data.Connection();
			TipoSala tipoSala = new TipoSala(1, con);
			Sala sala = Sala.Create(10, "NombreTest", "EsloganTest", tipoSala);
			Assert.assertEquals(sala.getNombre(), "NombreTest");
			Assert.assertEquals(sala.getEslogan(), "EsloganTest");
			Assert.assertEquals(sala.getCapacidadActual(), 10);
			sala.Delete();
			Assert.assertEquals(true, sala.getIsDeleted());
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(con != null)
				con.close();
		}
	}
	
	@Test
	public void testSelect()throws Exception{
        ArrayList<Sala> aSalas = new ArrayList<Sala>();
		aSalas = Sala.Select(32, "Sangría Tortura", 
				"Donde lo más pesado es tu alma", "Bailes volcánicos");
		Assert.assertEquals(aSalas.get(0).getNombre(), "Sangría Tortura");
		Assert.assertEquals(aSalas.get(0).getEslogan(), "Donde lo más pesado es tu alma");
		Assert.assertEquals(aSalas.get(0).getCapacidadActual(), 32);
		Assert.assertEquals(aSalas.get(0).getTipo().getCapacidadTotal(), 2000);
		
		aSalas = Sala.Select(32, "Sangría Tortura", 
				"Donde lo más pesado es tu alma", null);
		Assert.assertEquals(aSalas.get(0).getNombre(), "Sangría Tortura");
		Assert.assertEquals(aSalas.get(0).getEslogan(), "Donde lo más pesado es tu alma");
		Assert.assertEquals(aSalas.get(0).getCapacidadActual(), 32);
		Assert.assertEquals(aSalas.get(0).getTipo().getCapacidadTotal(), 2000);
		
		aSalas = Sala.Select(32, "Sangría Tortura", null, null);
		Assert.assertEquals(aSalas.get(0).getNombre(), "Sangría Tortura");
		Assert.assertEquals(aSalas.get(0).getEslogan(), "Donde lo más pesado es tu alma");
		Assert.assertEquals(aSalas.get(0).getCapacidadActual(), 32);
		Assert.assertEquals(aSalas.get(0).getTipo().getCapacidadTotal(), 2000);
		
		aSalas = Sala.Select(32, null, null, null);
		Assert.assertEquals(aSalas.get(0).getNombre(), "Sangría Tortura");
		Assert.assertEquals(aSalas.get(0).getEslogan(), "Donde lo más pesado es tu alma");
		Assert.assertEquals(aSalas.get(0).getCapacidadActual(), 32);
		Assert.assertEquals(aSalas.get(0).getTipo().getCapacidadTotal(), 2000);
		
		aSalas = Sala.Select(32, null, null, null);
		Assert.assertEquals(aSalas.get(0).getNombre(), "Sangría Tortura");
		Assert.assertEquals(aSalas.get(0).getEslogan(), "Donde lo más pesado es tu alma");
		Assert.assertEquals(aSalas.get(0).getCapacidadActual(), 32);
		Assert.assertEquals(aSalas.get(0).getTipo().getCapacidadTotal(), 2000);
		
		
		
	}
	
}

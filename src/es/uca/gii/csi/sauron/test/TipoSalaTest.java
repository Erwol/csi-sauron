package es.uca.gii.csi.sauron.test;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

import es.uca.gii.csi.sauron.data.Data;
import es.uca.gii.csi.sauron.data.TipoSala;

public class TipoSalaTest {
	@BeforeClass
	public static void setUpBeforeClass()throws Exception{
		Data.LoadDriver();
	}
	
	@Test
	public void testCreate()throws Exception{
		TipoSala tipoSala = TipoSala.Create(4356, "Otro tipo", "Descripci√≥n del tipo de Sala");
		Assert.assertEquals(4356, tipoSala.getCapacidadTotal());
		Assert.assertEquals("Otro tipo", tipoSala.getNombre());
		Assert.assertEquals("Descripci√≥n del tipo de Sala", tipoSala.getDescripcion());
		tipoSala.Delete();
	}
	
	@Test
	public void testSelect()throws Exception{
		ArrayList<TipoSala> aTipoSala = new ArrayList<TipoSala>();
		aTipoSala = TipoSala.Select("Bailes volc·nicos", "Baila hasta consumir tus cenizas", 2000);
		Assert.assertEquals("Bailes volc·nicos", aTipoSala.get(0).getNombre());
		Assert.assertEquals("Baila hasta consumir tus cenizas", aTipoSala.get(0).getDescripcion());
		Assert.assertEquals(2000, aTipoSala.get(0).getCapacidadTotal());
	}
	
	@Test
	public void testUpdate()throws Exception{
		Connection con = null;
		try{
			con = Data.Connection();
			TipoSala tipoSala = new TipoSala(1, con);
			tipoSala.setCapacidadTotal(2000);
			tipoSala.setNombre("Bailes volc·nicos");
			tipoSala.setDescripcion("Baila hasta consumir tus cenizas");
			tipoSala.Update();
			Assert.assertEquals(tipoSala.getCapacidadTotal(), 2000);
			Assert.assertEquals(tipoSala.getNombre(), "Bailes volc·nicos");
			Assert.assertEquals(tipoSala.getDescripcion(), "Baila hasta consumir tus cenizas");
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
			TipoSala tipoSala = TipoSala.Create(4356, "Otro tipo para borrar", "Descripci√≥n del tipo de Sala");
			tipoSala.Delete();
			Assert.assertEquals(true, tipoSala.getIsDeleted());
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(con != null)
				con.close();
		}
	}
}

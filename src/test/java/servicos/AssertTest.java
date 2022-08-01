package servicos;

import entidades.Usuario;
import org.junit.Test;

import static org.junit.Assert.*;

public class AssertTest {

	@Test
	public void test() {
		assertTrue(true);
		assertFalse(false);
		assertEquals("Mensagem Quando estourar um erro" , 1, 1);

		assertEquals(0.51234, 0.512, 0.001);
		assertEquals(Math.PI, 3.14, 0.01);

		int i = 5;
		Integer i2 = 5;
		assertEquals(Integer.valueOf(i), i2);
		assertEquals(i, i2.intValue());

		assertEquals("bola", "bola");
		assertNotEquals("bola", "casa");
		assertTrue("bola".equalsIgnoreCase("Bola"));
		assertTrue("bola".startsWith("b"));

		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");
		Usuario u3 = u1;
		Usuario u4 = null;


		assertEquals(u1, u2);
		assertSame(u1, u3);

		assertTrue(u3 != null);
		assertNull(u4);
	}
}

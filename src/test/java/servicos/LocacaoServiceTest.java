package servicos;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import exceptions.FilmeSemEstoqueException;
import exceptions.LocadoraException;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import utils.DateUtils;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class LocacaoServiceTest {

	private LocacaoService service;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void before() {
		System.out.println("Before");
		service = new LocacaoService();
	}

	@After
	public void after() {
		System.out.println("After");
	}

	@BeforeClass
	public static void beforeClass() {
		System.out.println("Before class");
	}

	@AfterClass
	public static void afterClass() {
		System.out.println("After class");
	}

	@Test
	public void testeLocacao() throws Exception {
		// cenario
		Usuario usuario = new Usuario("William");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		System.out.println("Teste");

		//acao
		Locacao locacao = service.alugarFilme(usuario, filme);
		//verificacao
		assertEquals(5.0, locacao.getValor(), 0.01);
		assertTrue(DateUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		assertTrue(DateUtils.isMesmaData(locacao.getDataRetorno(), DateUtils.obterDataComDiferencaDias(1)));
	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void deveLancarExceptionCasoFilmeNaoTenhaEstoque_Elegante() throws Exception {
		// cenario
		Usuario usuario = new Usuario("William");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		//acao
		service.alugarFilme(usuario, filme);
	}

	@Test
	public void deveLancarExceptionCasoFilmeNaoTenhaEstoque_Robusta() {
		// cenario
		Usuario usuario = new Usuario("William");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		//acao
		try {
			service.alugarFilme(usuario, filme);
			fail("Deveria ter lançado uma excessão");
		} catch (Exception e) {
			//Verificacao
			assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}

	@Test
	public void deveLancarExceptionCasoFilmeNaoTenhaEstoque_FormaNova() throws Exception {
		// cenario
		Usuario usuario = new Usuario("William");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");

		//acao
		service.alugarFilme(usuario, filme);

	}

	@Test
	public void teste2UsandoFluentInterface() {
		// cenario
		Usuario usuario = new Usuario("William");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		//acao
		Locacao locacao = null;
		try {
			locacao = service.alugarFilme(usuario, filme);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		//verificacao
		assertThat(locacao.getValor(), is(equalTo(5.0)));
		assertThat(DateUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		assertThat(DateUtils.isMesmaData(locacao.getDataRetorno(), DateUtils.obterDataComDiferencaDias(1)), is(true));
	}

	@Test
	public void testeLocacaoUsandoErrorCollector() {
		// cenario
		Usuario usuario = new Usuario("William");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		//acao
		Locacao locacao = null;
		try {
			locacao = service.alugarFilme(usuario, filme);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		//verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(DateUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DateUtils.isMesmaData(locacao.getDataRetorno(), DateUtils.obterDataComDiferencaDias(1)), is(true));
	}

	@Test
	public void deveLancarLocadoraExceptionSeUsuarioEstiverVazio() throws FilmeSemEstoqueException {
		//cenario
		Filme filme = new Filme("Filme 1", 2, 5.0);

		//acao
		try {
			Locacao locacao = service.alugarFilme(null, filme);
			fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage() , is("Usuário vazio"));
		}

	}

	@Test
	public void naoDeveLancarLocadoraExceptionSeUsuarioNaoEstiverVazio() throws FilmeSemEstoqueException {
		//cenario
		Usuario usuario = new Usuario("William");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		//acao
		try {
			Locacao locacao = service.alugarFilme(usuario, filme);
		} catch (LocadoraException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void deveLancarLocadoraExceptionSeFilmeEstiverVazio() throws FilmeSemEstoqueException {
		//cenario
		Usuario usuario = new Usuario("William");

		//acao
		try {
			Locacao locacao = service.alugarFilme(usuario, null);
			fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage() , is("Filme vazio"));
		}

	}

	@Test
	public void deveLancarLocadoraExceptionSeFilmeEstiverVazio_FormaRobusta() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = new Usuario("William");

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		//acao
		service.alugarFilme(usuario, null);

	}

}
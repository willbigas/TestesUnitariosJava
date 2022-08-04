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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
		service = new LocacaoService();
	}

	@Test
	public void deveAlugarUmFilmeSeOsParametrosForemPreenchidosCorretamente() throws Exception {
		// cenario
		Usuario usuario = new Usuario("William");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		//acao
		Locacao locacao = service.alugarFilme(usuario, filme);
		//verificacao
		assertEquals(5.0, locacao.getValor(), 0.01);
		assertTrue(DateUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		assertTrue(DateUtils.isMesmaData(locacao.getDataRetorno(), DateUtils.obterDataComDiferencaDias(1)));
	}

	@Test
	public void deveLocarUmaListaDeFilmesCasoTenhaTodosOsParametros() throws Exception {
		// cenario
		Usuario usuario = new Usuario("William");
		Filme filme = new Filme("Filme 1", 2, 5.0);
		Filme filme2 = new Filme("Filme 2", 4, 5.0);

		List<Filme> filmes = Arrays.asList(filme, filme2);

		//acao
		Locacao locacao = service.alugarFilmes(usuario, filmes);
		//verificacao
		assertEquals(10, locacao.getValor(), 0.01);
		assertTrue(DateUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		assertTrue(DateUtils.isMesmaData(locacao.getDataRetorno(), DateUtils.obterDataComDiferencaDias(1)));
	}

	@Test
	public void deveAplicarUmDescontoDe25PorCentoNoTerceiroFilme() throws Exception {
		// cenario
		Usuario usuario = new Usuario("William");
		Filme filme1 = new Filme("Filme 1", 2, 40.0);
		Filme filme2 = new Filme("Filme 2", 4, 20.0);
		Filme filme3 = new Filme("Filme 3", 6, 100.0);

		Double descontoTerceiroFilme = 25.0;

		Double precoTotalMenosDesconto = filme1.getPrecoLocacao() + filme2.getPrecoLocacao() + (filme3.getPrecoLocacao() - descontoTerceiroFilme);

		List<Filme> filmes = Arrays.asList(filme1, filme2, filme3);

		//acao
		Locacao locacao = service.alugarFilmes(usuario, filmes);
		//verificacao
		assertEquals(precoTotalMenosDesconto, locacao.getValor(), 0.01);
	}

	@Test
	public void deveAplicarUmDescontoDe50PorCentoNoQuartoFilme() throws Exception {
		// cenario
		Usuario usuario = new Usuario("William");
		Filme filme1 = new Filme("Filme 1", 2, 40.0);
		Filme filme2 = new Filme("Filme 2", 4, 20.0);
		Filme filme3 = new Filme("Filme 3", 6, 100.0);
		Filme filme4 = new Filme("Filme 4", 7, 100.0);

		Double descontoQuartoFilme = 50.0;

		Double precoTotalMenosDesconto = filme1.getPrecoLocacao() + filme2.getPrecoLocacao() + filme3.getPrecoLocacao() + (filme4.getPrecoLocacao() - descontoQuartoFilme);

		List<Filme> filmes = Arrays.asList(filme1, filme2, filme3, filme4);

		//acao
		Locacao locacao = service.alugarFilmes(usuario, filmes);
		//verificacao
		assertEquals(precoTotalMenosDesconto, locacao.getValor(), 0.01);
	}

	@Test
	public void deveAplicarUmDescontoDe75PorCentoNoQuintoFilme() throws Exception {
		// cenario
		Usuario usuario = new Usuario("William");
		Filme filme1 = new Filme("Filme 1", 2, 40.0);
		Filme filme2 = new Filme("Filme 2", 4, 20.0);
		Filme filme3 = new Filme("Filme 3", 6, 100.0);
		Filme filme4 = new Filme("Filme 4", 7, 100.0);
		Filme filme5 = new Filme("Filme 5", 7, 100.0);

		Double descontoQuintoFilme = 75.0;

		Double precoTotalMenosDesconto = filme1.getPrecoLocacao() + filme2.getPrecoLocacao() + filme3.getPrecoLocacao() + filme4.getPrecoLocacao() + (filme5.getPrecoLocacao() - descontoQuintoFilme);

		List<Filme> filmes = Arrays.asList(filme1, filme2, filme3, filme4, filme5);

		//acao
		Locacao locacao = service.alugarFilmes(usuario, filmes);
		//verificacao
		assertEquals(precoTotalMenosDesconto, locacao.getValor(), 0.01);
	}

	@Test
	public void deveAplicarUmDescontoDe100PorCentoNoSextoFilme() throws Exception {
		// cenario
		Usuario usuario = new Usuario("William");
		Filme filme1 = new Filme("Filme 1", 2, 40.0);
		Filme filme2 = new Filme("Filme 2", 4, 20.0);
		Filme filme3 = new Filme("Filme 3", 6, 100.0);
		Filme filme4 = new Filme("Filme 4", 7, 100.0);
		Filme filme5 = new Filme("Filme 5", 7, 100.0);
		Filme filme6 = new Filme("Filme 6", 7, 100.0);

		Double descontoSextoFilme = 100.0;

		Double valorTotalMenosDesconto = filme1.getPrecoLocacao() + filme2.getPrecoLocacao() + filme3.getPrecoLocacao()
				+ filme4.getPrecoLocacao() + filme5.getPrecoLocacao() + (filme6.getPrecoLocacao() - descontoSextoFilme);

		List<Filme> filmes = Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6);

		//acao
		Locacao locacao = service.alugarFilmes(usuario, filmes);
		//verificacao
		assertEquals(valorTotalMenosDesconto, locacao.getValor(), 0.01);
	}


	@Test
	public void deveLancarLocadoraExceptionSeListaDeFilmesEstiverNull() throws FilmeSemEstoqueException {
		//cenario
		Usuario usuario = new Usuario("William");

		//acao
		try {
			Locacao locacao = service.alugarFilme(usuario, null);
			fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Filme vazio"));
		}

	}

	@Test
	public void deveLancarLocadoraExceptionSeListaDeFilmesEstiverVazia() throws FilmeSemEstoqueException {
		//cenario
		Usuario usuario = new Usuario("William");

		//acao
		try {
			Locacao locacao = service.alugarFilmes(usuario, new ArrayList<>());
			fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Filme vazio"));
		}

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
	public void deveAlugarFilmeUsandoFluentInterface() {
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
	public void deveAlugarFilmeUsandoCollector() {
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
			assertThat(e.getMessage(), is("Usuário vazio"));
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
			assertThat(e.getMessage(), is("Filme vazio"));
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
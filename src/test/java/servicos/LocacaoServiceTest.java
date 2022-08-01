package servicos;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import utils.DateUtils;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class LocacaoServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testeLocacao() throws Exception {
		// cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("William");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		//acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);
		//verificacao
		assertEquals(5.0, locacao.getValor(), 0.01);
		assertTrue(DateUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		assertTrue(DateUtils.isMesmaData(locacao.getDataRetorno(), DateUtils.obterDataComDiferencaDias(1)));
	}

	@Test(expected = Exception.class)
	public void deveLancarExceptionCasoFilmeNaoTenhaEstoque_Elegante() throws Exception {
		// cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("William");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		//acao
		locacaoService.alugarFilme(usuario, filme);
	}

	@Test
	public void deveLancarExceptionCasoFilmeNaoTenhaEstoque_Robusta() {
		// cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("William");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		//acao
		try {
			locacaoService.alugarFilme(usuario, filme);
			fail("Deveria ter lançado uma excessão");
		} catch (Exception e) {
			//Verificacao
			assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}

	@Test
	public void deveLancarExceptionCasoFilmeNaoTenhaEstoque_FormaNova() throws Exception {
		// cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("William");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");

		//acao
		locacaoService.alugarFilme(usuario, filme);

	}

	@Test
	public void teste2UsandoFluentInterface() {
		// cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("William");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		//acao
		Locacao locacao = null;
		try {
			locacao = locacaoService.alugarFilme(usuario, filme);
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
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("William");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		//acao
		Locacao locacao = null;
		try {
			locacao = locacaoService.alugarFilme(usuario, filme);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		//verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(DateUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DateUtils.isMesmaData(locacao.getDataRetorno(), DateUtils.obterDataComDiferencaDias(1)), is(true));
	}
}
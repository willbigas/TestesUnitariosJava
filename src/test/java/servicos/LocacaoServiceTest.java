package servicos;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import org.junit.Test;
import utils.DateUtils;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class LocacaoServiceTest {

	@Test
	public void teste() {
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

	@Test
	public void teste2UsandoFluentInterface() {
		// cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("William");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		//acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);

		//verificacao
		assertThat(locacao.getValor(), is(equalTo(5.0)));
		assertThat(locacao.getValor(), is(not(5.0)));
		assertThat(DateUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		assertThat(DateUtils.isMesmaData(locacao.getDataRetorno(), DateUtils.obterDataComDiferencaDias(1)), is(true));
	}
}
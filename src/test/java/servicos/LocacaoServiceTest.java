package servicos;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import org.junit.Assert;
import org.junit.Test;
import utils.DateUtils;

import java.util.Date;

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
		Assert.assertTrue(locacao.getValor() == 5.0);
		Assert.assertTrue(DateUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DateUtils.isMesmaData(locacao.getDataRetorno(), DateUtils.obterDataComDiferencaDias(1)));
	}
}
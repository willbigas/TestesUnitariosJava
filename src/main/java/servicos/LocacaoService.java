package servicos;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import exceptions.FilmeSemEstoqueException;
import exceptions.LocadoraException;
import utils.DateUtils;

import java.util.*;

public class LocacaoService {

	public static final String MENSAGEM_USUARIO_VAZIO = "Usuário vazio";
	public static final String MENSAGEM_FILME_VAZIO = "Filme vazio";
	public static final String MENSAGEM_FILME_SEM_ESTOQUE = "Filme sem estoque";

	public static final int TERCEIRO_FILME = 2;
	public static final int QUARTO_FILME = 3;
	public static final int QUINTO_FILME = 4;
	public static final int SEXTO_FILME = 5;

	public static final double VINTE_CINCO_POR_CENTO = 25.0 / 100.0;
	public static final double CINQUENTA_POR_CENTO = 50.0 / 100.0;
	public static final double SETENTA_E_CINCO_POR_CENTO = 75.0 / 100.0;

	public Locacao alugarFilme(Usuario usuario, Filme filme) throws LocadoraException, FilmeSemEstoqueException {

		if (filme == null) {
			throw new LocadoraException(MENSAGEM_FILME_VAZIO);
		}

		return alugarFilmes(usuario, Collections.singletonList(filme));
	}

	public Locacao alugarFilmes(Usuario usuario, List<Filme> filmes) throws LocadoraException, FilmeSemEstoqueException {

		if (usuario == null) {
			throw new LocadoraException(MENSAGEM_USUARIO_VAZIO);
		}

		if (filmes == null || filmes.isEmpty()) {
			throw new LocadoraException(MENSAGEM_FILME_VAZIO);
		}

		for (Filme filme : filmes) {
			if (filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException(MENSAGEM_FILME_SEM_ESTOQUE);
			}
		}

		filmes = calcularDescontosProgressivos(filmes);

		Double precoLocacaoTotal = filmes.stream().mapToDouble(Filme::getPrecoLocacao).sum();

		Locacao locacao = new Locacao();
		locacao.getFilmes().addAll(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(precoLocacaoTotal);

		Date dataEntrega = new Date();
		dataEntrega = DateUtils.adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);

		// SALVANDO LOCACAO
		//Todo	Adicionar metodo para salvar

		return locacao;
	}

	private List<Filme> calcularDescontosProgressivos(List<Filme> filmes) {

		int quantidadeDeFilmes = filmes.size();

		if (quantidadeDeFilmes == 3) {
			Double precoLocacao = filmes.get(TERCEIRO_FILME).getPrecoLocacao();
			filmes.get(TERCEIRO_FILME).setPrecoLocacao(precoLocacao - (VINTE_CINCO_POR_CENTO * precoLocacao));
		}

		if (quantidadeDeFilmes == 4) {
			Double valorOriginal = filmes.get(QUARTO_FILME).getPrecoLocacao();
			filmes.get(QUARTO_FILME).setPrecoLocacao(valorOriginal - (CINQUENTA_POR_CENTO * valorOriginal));
		}

		if (quantidadeDeFilmes == 5) {
			Double valorOriginal = filmes.get(QUINTO_FILME).getPrecoLocacao();
			filmes.get(QUINTO_FILME).setPrecoLocacao(valorOriginal - (SETENTA_E_CINCO_POR_CENTO * valorOriginal));
		}

		if (quantidadeDeFilmes == 6) {
			filmes.get(SEXTO_FILME).setPrecoLocacao(0.0);
		}
		return filmes;
	}
}

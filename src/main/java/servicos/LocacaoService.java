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

	public Locacao alugarFilme(Usuario usuario, Filme filme) throws LocadoraException, FilmeSemEstoqueException {

		if (filme == null) {
			throw new LocadoraException(MENSAGEM_FILME_VAZIO);
		}

		return alugarFilmes(usuario , Collections.singletonList(filme));
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
}

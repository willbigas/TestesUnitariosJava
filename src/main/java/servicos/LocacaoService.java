package servicos;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import exceptions.FilmeSemEstoqueException;
import exceptions.LocadoraException;
import utils.DateUtils;

import java.util.Date;

public class LocacaoService {

	public Locacao alugarFilme(Usuario usuario, Filme filme) throws LocadoraException, FilmeSemEstoqueException {

		if (usuario == null) {
			throw new LocadoraException("Usuário vazio");
		}

		if (filme == null) {
			throw new LocadoraException("Filme vazio");
		}

		if (filme.getEstoque() == 0) {
			throw new FilmeSemEstoqueException("Filme sem estoque");
		}

		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		Date dataEntrega = new Date();
		dataEntrega = DateUtils.adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);

		// SALVANDO LOCACAO
		//Todo	Adicionar metodo para salvar

		return locacao;
	}
}

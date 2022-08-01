package servicos;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import utils.DateUtils;

import java.util.Date;

public class LocacaoService {

	public Locacao alugarFilme(Usuario usuario, Filme filme) {
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

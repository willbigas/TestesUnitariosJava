package entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Locacao {

	private Usuario usuario;
	private List<Filme> filmes;
	private LocalDate dataLocacao;
	private LocalDate dataRetorno;
	private Double valor;

	public Locacao() {
		filmes = new ArrayList<>();
	}

	public Locacao(Usuario usuario, List<Filme> filmes, LocalDate dataLocacao, LocalDate dataRetorno, Double valor) {
		this.usuario = usuario;
		this.filmes = filmes;
		this.dataLocacao = dataLocacao;
		this.dataRetorno = dataRetorno;
		this.valor = valor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Filme> getFilmes() {
		return filmes;
	}

	public void setFilmes(List<Filme> filmes) {
		this.filmes = filmes;
	}

	public LocalDate getDataLocacao() {
		return dataLocacao;
	}

	public void setDataLocacao(LocalDate dataLocacao) {
		this.dataLocacao = dataLocacao;
	}

	public LocalDate getDataRetorno() {
		return dataRetorno;
	}

	public void setDataRetorno(LocalDate dataRetorno) {
		this.dataRetorno = dataRetorno;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
}

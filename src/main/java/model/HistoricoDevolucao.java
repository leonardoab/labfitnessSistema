package model;

import java.util.Date;

public class HistoricoDevolucao {
	
	private String nomeUsuarioInsercao;
	private Date dataAtualizacao;
	
	private Diu diu;
	
	
	
	
	
	public Diu getDiu() {
		return diu;
	}
	public void setDiu(Diu diu) {
		this.diu = diu;
	}
	public String getNomeUsuarioInsercao() {
		return nomeUsuarioInsercao;
	}
	public void setNomeUsuarioInsercao(String nomeUsuarioInsercao) {
		this.nomeUsuarioInsercao = nomeUsuarioInsercao;
	}
	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}
	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	
	

}

package model;

public class Prestador {
	
	private String nome;
	private Integer cdPrestador;
	private Integer unidade;
	private String cnpj;
	private Integer cdContratante;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getCdPrestador() {
		return cdPrestador;
	}
	public void setCdPrestador(Integer cdPrestador) {
		this.cdPrestador = cdPrestador;
	}
	public Integer getUnidade() {
		return unidade;
	}
	public void setUnidade(Integer unidade) {
		this.unidade = unidade;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public Integer getCdContratante() {
		return cdContratante;
	}
	public void setCdContratante(Integer cdContratante) {
		this.cdContratante = cdContratante;
	}
	
	public String getCdPrestadorString() {
		
		if (cdPrestador == null || cdPrestador == 0) return "";
		
		return cdPrestador.toString();
	}
	
	
}

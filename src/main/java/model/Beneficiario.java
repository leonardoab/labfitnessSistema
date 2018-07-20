package model;

import java.util.Date;


public class Beneficiario {


	private Integer cdModalidade;
	private Integer nrTerAdesao;
	private Integer cdUsuario;
	private String unidade;
	private String cdCarteiraInteira;
	private String nomeUnimedUnidade;
	private String nome;
	private Date dtExclusao;

	public static String completeToLeft(String value, char c, int size) {
		String result = value;
		while (result.length() < size) {
			result = c + result;
		}
		return result;
	}

	public Integer getCdModalidade() {
		return cdModalidade;
	}
	public void setCdModalidade(Integer cdModalidade) {
		this.cdModalidade = cdModalidade;
	}
	public Integer getNrTerAdesao() {
		return nrTerAdesao;
	}
	public void setNrTerAdesao(Integer nrTerAdesao) {
		this.nrTerAdesao = nrTerAdesao;
	}
	public Integer getCdUsuario() {
		return cdUsuario;
	}
	public void setCdUsuario(Integer cdUsuario) {
		this.cdUsuario = cdUsuario;
	}

	public String getUnidade() {
		if (unidade != null)  unidade = completeToLeft(unidade,'0',4);
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public String getCdCarteiraInteira() {
		if (cdCarteiraInteira != null)  cdCarteiraInteira = completeToLeft(cdCarteiraInteira,'0',13);
		return cdCarteiraInteira;
	}
	public void setCdCarteiraInteira(String cdCarteiraInteira) {
		this.cdCarteiraInteira = cdCarteiraInteira;
	}

	public String getCdCarteiraInteiraString() {
		String carteiraUnidade;
		carteiraUnidade = unidade + cdCarteiraInteira;
		
		if (unidade == null || unidade.equals("0000")) return "";
		
		
		return carteiraUnidade;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDtExclusao() {
		return dtExclusao;
	}
	public void setDtExclusao(Date dtExclusao) {
		this.dtExclusao = dtExclusao;
	}

	public String getNomeUnimedUnidade() {
		return nomeUnimedUnidade;
	}

	public void setNomeUnimedUnidade(String nomeUnimedUnidade) {
		this.nomeUnimedUnidade = nomeUnimedUnidade;
	}



















}

package model;

import java.util.Date;

public class OpmeQtBipap {
	
	private Integer tpInsumo;
	private Integer cdInsumo;
	private String cdAnvisa;
	private Integer quantidade;
	private String nomeInsumo;
	private Float valor;
	private String usuarioAtualizacao;
	private Date dtAtualizacao;
	private Long id;
	private String observacao;
	
	public Integer getTpInsumo() {
		return tpInsumo;
	}
	public void setTpInsumo(Integer tpInsumo) {
		this.tpInsumo = tpInsumo;
	}
	public Integer getCdInsumo() {
		return cdInsumo;
	}
	public void setCdInsumo(Integer cdInsumo) {
		this.cdInsumo = cdInsumo;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Float getValor() {
		return valor;
	}
	public void setValor(Float valor) {
		this.valor = valor;
	}
	public String getUsuarioAtualizacao() {
		return usuarioAtualizacao;
	}
	public void setUsuarioAtualizacao(String usuarioAtualizacao) {
		this.usuarioAtualizacao = usuarioAtualizacao;
	}
	public Date getDtAtualizacao() {
		return dtAtualizacao;
	}
	public void setDtAtualizacao(Date dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeInsumo() {
		return nomeInsumo;
	}
	public void setNomeInsumo(String nomeInsumo) {
		this.nomeInsumo = nomeInsumo;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getCdAnvisa() {
		return cdAnvisa;
	}
	public void setCdAnvisa(String cdAnvisa) {
		this.cdAnvisa = cdAnvisa;
	}
	
	
	
	
	
	

}

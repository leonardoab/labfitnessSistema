package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Diu {

	private String tipo;
	private Float valor;
	private Date dtSolicitacao ;
	private Date dtLancamento ;
	private String usuarioAtualizacao;
	private Date dtAtualizacao = new Date();
	private Long id;
	private boolean retirado = true;
	private String situacao;
	private Long idPrincipal;
	private Integer sequencia;
	private String observacao;
	private boolean devolvido;
	private String status = "Aberta";
	private String competenciaBaixa;
	private boolean travado;

	private Prestador medico = new Prestador();;
	private Beneficiario beneficiario = new Beneficiario();
	private GuiaAutorizacao guiaAutorizacao = new GuiaAutorizacao();
	private Integer habilitaDetalhe;

	private ArrayList<HistoricoDevolucao> listaHistoricoDevolucao;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public void setDevolvido(boolean devolvido) {
		this.devolvido = devolvido;
	}

	public Date getDtSolicitacao() {
		return dtSolicitacao;
	}

	public void setDtSolicitacao(Date dtSolicitacao) {
		this.dtSolicitacao = dtSolicitacao;
	}

	public Date getDtLancamento() {
		return dtLancamento;
	}

	public void setDtLancamento(Date dtLancamento) {
		this.dtLancamento = dtLancamento;
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

	public boolean isRetirado() {
		return retirado;
	}

	public void setRetirado(boolean retirado) {
		this.retirado = retirado;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Long getIdPrincipal() {
		return idPrincipal;
	}

	public void setIdPrincipal(Long idPrincipal) {
		this.idPrincipal = idPrincipal;
	}

	public Integer getSequencia() {
		return sequencia;
	}

	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Prestador getMedico() {
		return medico;
	}

	public void setMedico(Prestador medico) {
		this.medico = medico;
	}

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	public GuiaAutorizacao getGuiaAutorizacao() {
		return guiaAutorizacao;
	}

	public void setGuiaAutorizacao(GuiaAutorizacao guiaAutorizacao) {
		this.guiaAutorizacao = guiaAutorizacao;
	}

	public Integer getHabilitaDetalhe() {
		return habilitaDetalhe;
	}

	public void setHabilitaDetalhe(Integer habilitaDetalhe) {
		this.habilitaDetalhe = habilitaDetalhe;
	}

	public ArrayList<HistoricoDevolucao> getListaHistoricoDevolucao() {
		return listaHistoricoDevolucao;
	}

	public void setListaHistoricoDevolucao(
			ArrayList<HistoricoDevolucao> listaHistoricoDevolucao) {
		this.listaHistoricoDevolucao = listaHistoricoDevolucao;
	}

	public boolean isDevolvido() {
		return !retirado;
	}


	public String getDtSolicitacaoString() {
		
		if (dtSolicitacao == null){

			return "";
		}


		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");		
		String reportDate = df.format(dtSolicitacao);	

		if (reportDate.equals("01/01/1900")){

			return "";
		}

		return reportDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCompetenciaBaixa() {
		return competenciaBaixa;
	}

	public void setCompetenciaBaixa(String competenciaBaixa) {
		this.competenciaBaixa = competenciaBaixa;
	}

	public boolean isTravado() {
		
		if (competenciaBaixa == null || competenciaBaixa.equals("")) return false;
		else return true;
		
		
	}

	public void setTravado(boolean travado) {
		this.travado = travado;
	}



}

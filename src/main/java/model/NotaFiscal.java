package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NotaFiscal {

	private Long id;
	private String numero;
	private String nrPedido;
	private Date dataEmissao;
	private Date dataVencimento;
	private float valor;
	private int qtdItens;
	private String usuarioAtualizacao;
	private String usuarioInsercao;
	private Date dtAtualizacao;
	private Date dtInsercao;
	private Date dtRealizacao;
	private float frete;
	private float ipi;
	private float desconto;
	private Date dataTransacao;
	private float valorDetalhe;
	private String status = "Aberta";
	private String senhaAutorizacao =  "";
	private String dataTransacaoString;
	private String competenciaBaixa;
	private String carteirasInternas;
	private boolean travadoParcial;

	private Prestador hospital = new Prestador();
	private Prestador medico = new Prestador();
	private Prestador fornecedor = new Prestador();
	private Beneficiario beneficiario = new Beneficiario();
	private GuiaAutorizacao guiaAutorizacao = new GuiaAutorizacao();
	private String obs;
	private String TipoRegistro;

	private ArrayList<OpmeQtBipap> listaOpmeQtBipap;
	private ArrayList<Diu> listaDiu;




	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public GuiaAutorizacao getGuiaAutorizacao() {
		return guiaAutorizacao;
	}
	public void setGuiaAutorizacao(GuiaAutorizacao guiaAutorizacao) {
		this.guiaAutorizacao = guiaAutorizacao;
	}
	public Prestador getHospital() {
		return hospital;
	}
	public void setHospital(Prestador hospital) {
		this.hospital = hospital;
	}
	public Prestador getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Prestador fornecedor) {
		this.fornecedor = fornecedor;
	}
	public Beneficiario getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}
	public ArrayList<OpmeQtBipap> getListaOpmeQtBipap() {
		return listaOpmeQtBipap;
	}
	public void setListaOpmeQtBipap(ArrayList<OpmeQtBipap> listaOpmeQtBipap) {
		this.listaOpmeQtBipap = listaOpmeQtBipap;
	}
	public ArrayList<Diu> getListaDiu() {
		return listaDiu;
	}
	public void setListaDiu(ArrayList<Diu> listaDiu) {
		this.listaDiu = listaDiu;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNrPedido() {
		return nrPedido;
	}
	public void setNrPedido(String nrPedido) {
		this.nrPedido = nrPedido;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public int getQtdItens() {
		return qtdItens;
	}
	public void setQtdItens(int qtdItens) {
		this.qtdItens = qtdItens;
	}
	public String getObs() {
		return obs;
	}
	public String getTipoRegistro() {
		return TipoRegistro;
	}
	public void setTipoRegistro(String tipoRegistro) {
		TipoRegistro = tipoRegistro;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public Date getDtRealizacao() {
		return dtRealizacao;
	}
	public void setDtRealizacao(Date dtRealizacao) {
		this.dtRealizacao = dtRealizacao;
	}
	public Prestador getMedico() {
		return medico;
	}
	public void setMedico(Prestador medico) {
		this.medico = medico;
	}
	public String getUsuarioInsercao() {
		return usuarioInsercao;
	}
	public void setUsuarioInsercao(String usuarioInsercao) {
		this.usuarioInsercao = usuarioInsercao;
	}
	public Date getDtInsercao() {
		return dtInsercao;
	}
	public void setDtInsercao(Date dtInsercao) {
		this.dtInsercao = dtInsercao;
	}

	public float getFrete() {
		return frete;
	}
	public void setFrete(float frete) {
		this.frete = frete;
	}
	public float getIpi() {
		return ipi;
	}
	public void setIpi(float ipi) {
		this.ipi = ipi;
	}
	public float getDesconto() {
		return desconto;
	}
	public void setDesconto(float desconto) {
		this.desconto = desconto;
	}
	public void setDesconto(int desconto) {
		this.desconto = desconto;
	}



	public Date getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}
	public float getValorDetalhe() {
		return valorDetalhe;
	}
	public void setValorDetalhe(float valorDetalhe) {
		this.valorDetalhe = valorDetalhe;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSenhaAutorizacao() {
		return senhaAutorizacao;
	}
	public void setSenhaAutorizacao(String senhaAutorizacao) {
		this.senhaAutorizacao = senhaAutorizacao;
	}

	public String getDataTransacaoString() {

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");		
		
		if (dataTransacao == null){
			return null;
			
		}
		
		String reportDate = df.format(dataTransacao);	

		if (reportDate.equals("01/01/1900")){

			return "";
		}

		return reportDate;
	}
	
	
	public String getDataInsercaoString() {

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");		
		
		if (dtInsercao == null){
			return null;
			
		}
		
		String reportDate = df.format(dtInsercao);	

		if (reportDate.equals("01/01/1900")){

			return "";
		}

		return reportDate;
	}
	
	public String getCompetenciaBaixa() {
		return competenciaBaixa;
	}
	public void setCompetenciaBaixa(String competenciaBaixa) {
		this.competenciaBaixa = competenciaBaixa;
	}
	public String getCarteirasInternas() {
		return carteirasInternas;
	}
	public void setCarteirasInternas(String carteirasInternas) {
		this.carteirasInternas = carteirasInternas;
	}
	public boolean isTravadoParcial() {

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		String reportDate = null;
		if (dataTransacao != null){
			reportDate = df.format(dataTransacao);	
		}
		else {

			return false;
		}


		if (dataTransacao != null && !reportDate.equals("01/01/1900")){
			return true;
		}
		else {

			return false;
		}
	}








}

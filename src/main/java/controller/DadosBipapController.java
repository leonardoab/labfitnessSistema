package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Beneficiario;
import model.NotaFiscal;
import model.OpmeQtBipap;
import model.Prestador;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import services.NotaFiscalService;
import dao.BeneficiarioDAO;
import dao.DetalheNotaDAO;
import dao.GuiaAutorizacaoDAO;
import dao.InsumoDAO;
import dao.NotaFiscalDAO;
import dao.PrestardorDAO;

@Named
@ViewScoped
public class DadosBipapController implements Serializable{

	private static final long serialVersionUID = 1L;
	private NotaFiscal notaFiscalSelecionada;	
	private boolean validou = false;
	private boolean lgFornecedorTravado = false;
	private boolean lgHospitalTravado = false;
	private boolean lgBeneficiarioTravado = false;	
	private boolean lgInsumoTravado = false;
	private boolean lgInsumoTravadoAlteracao = true;
	private float valorDetalheTotal;
	private boolean habilitaLimpar;
	private NotaFiscal notaFiscal = new NotaFiscal();	
	private OpmeQtBipap opmeQtBipapItem = new OpmeQtBipap();
	private OpmeQtBipap opmeQtBipapSelecionado;
	private List<OpmeQtBipap> listaInsumos = new ArrayList<OpmeQtBipap>();
	private List<String> listaPrestadorString = new ArrayList<String>();
	private List<String> listaNrGuiasDisponivel = new ArrayList<String>();
	private List<String> listaAnoNrGuiasDisponivel = new ArrayList<String>();
	private List<Prestador> listaPrestador = new ArrayList<Prestador>();	
	private String listaNotaFiscal;
	private ArrayList<NotaFiscal> listaNotaFiscalFiltradas;
	private List<OpmeQtBipap> listaopmeQtBipap= new ArrayList<OpmeQtBipap>();
	private List<Beneficiario> listaBeneficiario = new ArrayList<Beneficiario>();
	private String situacaoFiltro;

	private NotaFiscalService notaFiscalService = new NotaFiscalService();

	@Inject
	LoginController loginController;


	@PostConstruct
	public void iniciar() {
		NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();		
		listaNotaFiscal = notaFicalDAO.buscarTeste();
	}

	



	/*******************************************************************GET-SET************************/


	
	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}
	public String getListaNotaFiscal() {
		return listaNotaFiscal;
	}





	public void setListaNotaFiscal(String listaNotaFiscal) {
		this.listaNotaFiscal = listaNotaFiscal;
	}





	public NotaFiscalService getNotaFiscalService() {
		return notaFiscalService;
	}





	public void setNotaFiscalService(NotaFiscalService notaFiscalService) {
		this.notaFiscalService = notaFiscalService;
	}





	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public boolean isValidou() {
		return validou;
	}
	public void setValidou(boolean validou) {
		this.validou = validou;
	}
	public boolean isLgFornecedorTravado() {
		return lgFornecedorTravado;
	}
	public void setLgFornecedorTravado(boolean lgFornecedorTravado) {
		this.lgFornecedorTravado = lgFornecedorTravado;
	}
	public boolean isLgBeneficiarioTravado() {
		return lgBeneficiarioTravado;
	}
	public void setLgBeneficiarioTravado(boolean lgBeneficiarioTravado) {
		this.lgBeneficiarioTravado = lgBeneficiarioTravado;
	}
	public List<String> getListaPrestadorString() {
		return listaPrestadorString;
	}
	public void setListaPrestadorString(List<String> listaPrestadorString) {
		this.listaPrestadorString = listaPrestadorString;
	}
	public List<Prestador> getListaPrestador() {
		return listaPrestador;
	}
	public void setListaPrestador(List<Prestador> listaPrestador) {
		this.listaPrestador = listaPrestador;
	}
	public List<String> getListaNrGuiasDisponivel() {
		return listaNrGuiasDisponivel;
	}
	public void setListaNrGuiasDisponivel(List<String> listaNrGuiasDisponivel) {
		this.listaNrGuiasDisponivel = listaNrGuiasDisponivel;
	}
	public List<String> getListaAnoNrGuiasDisponivel() {
		return listaAnoNrGuiasDisponivel;
	}
	public void setListaAnoNrGuiasDisponivel(List<String> listaAnoNrGuiasDisponivel) {
		this.listaAnoNrGuiasDisponivel = listaAnoNrGuiasDisponivel;
	}
	public List<Beneficiario> getListaBeneficiario() {
		return listaBeneficiario;
	}
	public void setListaBeneficiario(List<Beneficiario> listaBeneficiario) {
		this.listaBeneficiario = listaBeneficiario;
	}
	public NotaFiscal getNotaFiscalSelecionada() {
		return notaFiscalSelecionada;
	}
	public void setNotaFiscalSelecionada(NotaFiscal notaFiscalSelecionada) {
		this.notaFiscalSelecionada = notaFiscalSelecionada;
	}
	public OpmeQtBipap getOpmeQtBipapItem() {
		return opmeQtBipapItem;
	}
	public void setOpmeQtBipapItem(OpmeQtBipap opmeQtBipapItem) {
		this.opmeQtBipapItem = opmeQtBipapItem;
	}
	public boolean isLgHospitalTravado() {
		return lgHospitalTravado;
	}
	public void setLgHospitalTravado(boolean lgHospitalTravado) {
		this.lgHospitalTravado = lgHospitalTravado;
	}
	public List<OpmeQtBipap> getListaopmeQtBipap() {
		return listaopmeQtBipap;
	}
	public void setListaopmeQtBipap(List<OpmeQtBipap> listaopmeQtBipap) {
		this.listaopmeQtBipap = listaopmeQtBipap;
	}
	public OpmeQtBipap getOpmeQtBipapSelecionado() {
		return opmeQtBipapSelecionado;
	}
	public void setOpmeQtBipapSelecionado(OpmeQtBipap opmeQtBipapSelecionado) {
		this.opmeQtBipapSelecionado = opmeQtBipapSelecionado;
	}
	public List<OpmeQtBipap> getListaInsumos() {
		return listaInsumos;
	}
	public void setListaInsumos(List<OpmeQtBipap> listaInsumos) {
		this.listaInsumos = listaInsumos;
	}
	public boolean isLgInsumoTravado() {
		return lgInsumoTravado;
	}
	public void setLgInsumoTravado(boolean lgInsumoTravado) {
		this.lgInsumoTravado = lgInsumoTravado;
	}
	public boolean isHabilitaLimpar() {
		return habilitaLimpar;
	}
	public void setHabilitaLimpar(boolean habilitaLimpar) {
		this.habilitaLimpar = habilitaLimpar;
	}
	public boolean isLgInsumoTravadoAlteracao() {
		return lgInsumoTravadoAlteracao;
	}
	public void setLgInsumoTravadoAlteracao(boolean lgInsumoTravadoAlteracao) {
		this.lgInsumoTravadoAlteracao = lgInsumoTravadoAlteracao;
	}
	public float getValorDetalheTotal() {
		return valorDetalheTotal;
	}
	public void setValorDetalheTotal(float valorDetalheTotal) {
		this.valorDetalheTotal = valorDetalheTotal;
	}
	public boolean isHabilitaDetalhe() {
		return !habilitaLimpar;
	}

	public ArrayList<NotaFiscal> getListaNotaFiscalFiltradas() {
		return listaNotaFiscalFiltradas;
	}

	public void setListaNotaFiscalFiltradas(
			ArrayList<NotaFiscal> listaNotaFiscalFiltradas) {
		this.listaNotaFiscalFiltradas = listaNotaFiscalFiltradas;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public String getSituacaoFiltro() {
		return situacaoFiltro;
	}

	public void setSituacaoFiltro(String situacaoFiltro) {
		this.situacaoFiltro = situacaoFiltro;
	}


}


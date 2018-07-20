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
public class DadosQtOralController implements Serializable{

	private static final long serialVersionUID = 1L;
	private NotaFiscal notaFiscalSelecionada;	
	private boolean validou = false;
	private boolean lgFornecedorTravado = false;
	private boolean lgHospitalTravado = false;
	private boolean lgBeneficiarioTravado = false;
	private boolean lgInsumoTravadoAlteracao = true;
	private boolean habilitaLimpar;
	private float valorDetalheTotal;
	private boolean lgInsumoTravado = false;
	private List<OpmeQtBipap> listaInsumos = new ArrayList<OpmeQtBipap>();
	private NotaFiscal notaFiscal = new NotaFiscal();	
	private OpmeQtBipap opmeQtBipapItem = new OpmeQtBipap();
	private OpmeQtBipap opmeQtBipapSelecionado;
	private List<String> listaPrestadorString = new ArrayList<String>();
	private List<String> listaNrGuiasDisponivel = new ArrayList<String>();
	private List<String> listaAnoNrGuiasDisponivel = new ArrayList<String>();
	private List<Prestador> listaPrestador = new ArrayList<Prestador>();	
	private ArrayList<NotaFiscal> listaNotaFiscal = new ArrayList<NotaFiscal>();
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
		//listaNotaFiscal = notaFicalDAO.buscarNotaTipo("QTORAL");
		listaNotaFiscal = notaFicalDAO.buscarNotaTipoStatus("QTORAL","Aberta");
	}

	public void inserirNotaFiscal(){

		boolean modificou = notaFiscalService.inserirNotaFiscal("QTORAL", notaFiscal, notaFiscalSelecionada, listaopmeQtBipap, valorDetalheTotal,loginController.getColaborador().getMatricula().toString(),null);		
		
		if (notaFiscalSelecionada == null && modificou == true){						
			notaFiscal = new NotaFiscal();
			listaopmeQtBipap = new ArrayList<OpmeQtBipap>();
			lgBeneficiarioTravado = false;
			lgFornecedorTravado = false;
			lgHospitalTravado = false;			
		}	
		
		else if (modificou == true){			
			iniciar();
		}
	}

	public void carregarNota(){
		habilitaLimpar = true;
		GuiaAutorizacaoDAO guiaAutorizacaoDAO = new GuiaAutorizacaoDAO();
		BuscarGuiaBenef(notaFiscalSelecionada.getBeneficiario().getUnidade(),notaFiscalSelecionada.getBeneficiario().getCdCarteiraInteira());
		listaNrGuiasDisponivel = guiaAutorizacaoDAO.buscarGuiaPorBeneficiario(notaFiscalSelecionada.getBeneficiario().getUnidade(), notaFiscalSelecionada.getBeneficiario().getCdCarteiraInteira().toString(),notaFiscalSelecionada.getGuiaAutorizacao().getAno());
		notaFiscal = notaFiscalSelecionada;
		iniciar();
		carregarDetalhesNota();
		lgBeneficiarioTravado = true;
		lgFornecedorTravado = true;
		lgHospitalTravado = true;
	}	

	

	public void carregarDetalhesNota(){
		DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();

		if (notaFiscalSelecionada != null){
			notaFiscal = notaFiscalSelecionada;
		}

		opmeQtBipapItem = new OpmeQtBipap();

		List<OpmeQtBipap> listaopmeQtBipapAux = null;
		if (notaFiscalSelecionada != null){
			listaopmeQtBipapAux = detalheNotaDAO.buscarDetalhesNotaOpmeQtBipap(notaFiscalSelecionada.getId());		
		}
		if (listaopmeQtBipapAux != null) listaopmeQtBipap = listaopmeQtBipapAux;

		valorDetalheTotal = 0;
		int quantidadeInsumos = 0;
		for (OpmeQtBipap opmeQtBipapSelecionado : listaopmeQtBipap){
			valorDetalheTotal = valorDetalheTotal + opmeQtBipapSelecionado.getValor(); 
			quantidadeInsumos = quantidadeInsumos + opmeQtBipapSelecionado.getQuantidade();
		}

		notaFiscal.setQtdItens(quantidadeInsumos);

		if (notaFiscalSelecionada != null){
			notaFiscalSelecionada.setQtdItens(quantidadeInsumos);			

		}

	}

	public void excluirOpme(){
		if (notaFiscalSelecionada != null){
			DetalheNotaDAO opmeQtBipapDAO = new DetalheNotaDAO();	
			opmeQtBipapDAO.deletarPorId(opmeQtBipapSelecionado.getId());
			listaopmeQtBipap.remove(opmeQtBipapSelecionado);
			notaFiscalSelecionada.setQtdItens(notaFiscalSelecionada.getQtdItens() - opmeQtBipapSelecionado.getQuantidade());
			valorDetalheTotal = valorDetalheTotal - opmeQtBipapSelecionado.getValor();
			NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();
			notaFicalDAO.persistir(notaFiscalSelecionada);	
		}
		else{
			notaFiscal.setQtdItens(notaFiscal.getQtdItens() - opmeQtBipapSelecionado.getQuantidade());			
			listaopmeQtBipap.remove(opmeQtBipapSelecionado);
			valorDetalheTotal = valorDetalheTotal - opmeQtBipapSelecionado.getValor();
		}
		carregarDetalhesNota();
	}

	public void gravaEdicaoInsumo(){
		DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();
		if (notaFiscalSelecionada != null) opmeQtBipapItem = detalheNotaDAO.persistirAlteracaoOpmeQtBipap(opmeQtBipapItem,notaFiscalSelecionada.getId());
		carregarDetalhesNota();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Detalhe da Nota Fiscal Salva!", ""));

	}		

	

	public void BuscarPorStatus(){
		
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("QtOralForm:gridNotas");
		dataTable.clearInitialState();
		dataTable.reset();
		
		listaNotaFiscalFiltradas = null;		
		NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();	
		if (situacaoFiltro.equals("Aberta")) listaNotaFiscal = notaFicalDAO.buscarNotaTipoStatus("QTORAL","Aberta");
		else if (situacaoFiltro.equals("Todas")) listaNotaFiscal = notaFicalDAO.buscarNotaTipo("QTORAL");

	}	

	public void ValidaInsumo(){				
		InsumoDAO insumoDAO = new InsumoDAO();
		OpmeQtBipap insumoValida;
		insumoValida = insumoDAO.buscarCodigoInsumo(opmeQtBipapItem);
		if (insumoValida.getNomeInsumo() == null ){	
			insumoValida.setCdInsumo(null);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Insumo não encontrado!", ""));
			return;			
		}	
		else {
			lgInsumoTravado = true;
			lgInsumoTravadoAlteracao = true;
		}
	}

	public void ValidaFornecedor(){				
		PrestardorDAO preservDAO = new PrestardorDAO();
		Prestador fornecedor;
		fornecedor = preservDAO.buscarCodigoFornecedor(notaFiscal.getFornecedor().getCdPrestador());
		if (fornecedor == null ){			
			notaFiscal.getFornecedor().setCdPrestador(null);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fornecedor não encontrado!", ""));
			return;			
		}
		else {
			notaFiscal.setFornecedor(fornecedor); 
			lgFornecedorTravado = true;
		}
	}

	public void ValidaHospital(){		
		PrestardorDAO preservDAO = new PrestardorDAO();
		Prestador fornecedor;
		fornecedor = preservDAO.buscarCodigoHospital(notaFiscal.getHospital().getCdPrestador());
		if (fornecedor == null){	
			notaFiscal.getHospital().setCdPrestador(null);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hospital não encontrado!", ""));
			return;					
		}	
		else {
			notaFiscal.setHospital(fornecedor); 
			lgHospitalTravado = true;
		}
	}

	public void ValidaBeneficiario(){		

		BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
		Beneficiario beneficiarioNota = notaFiscal.getBeneficiario();
		if (beneficiarioNota == null || beneficiarioNota.getCdCarteiraInteira() == null || beneficiarioNota.getCdCarteiraInteira() == null ){
			beneficiarioNota.setUnidade(null);
			beneficiarioNota.setCdCarteiraInteira( null);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Carteira não encontrada!", ""));
			return;
		}

		if (!beneficiarioNota.getUnidade().equals("0049")) { //ajustar para outuni
			Beneficiario beneficiarioAuxiliar = beneficiarioDAO.buscarPorBeneficiarioInter(notaFiscal.getBeneficiario().getUnidade(),notaFiscal.getBeneficiario().getCdCarteiraInteira().toString());
			beneficiarioNota.setNome(beneficiarioAuxiliar.getNome());
			if (notaFiscal.getBeneficiario().getNome() == null){
				beneficiarioNota.setUnidade(null);
				beneficiarioNota.setCdCarteiraInteira( null);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Carteira não encontrada!", ""));
				return;
			}
		}
		else {
			Beneficiario beneficiarioAuxiliar = beneficiarioDAO.buscarPorBeneficiario(notaFiscal.getBeneficiario().getCdCarteiraInteira().toString());
			beneficiarioNota.setNome(beneficiarioAuxiliar.getNome());
			if (notaFiscal.getBeneficiario().getNome() == null){
				beneficiarioNota.setUnidade(null);
				beneficiarioNota.setCdCarteiraInteira( null);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Carteira não encontrada!", ""));
				return;
			}
		}
		notaFiscal.setBeneficiario(beneficiarioNota);	
		BuscarGuiaBenef(notaFiscal.getBeneficiario().getUnidade(),notaFiscal.getBeneficiario().getCdCarteiraInteira().toString());
		lgBeneficiarioTravado = true;		
	}

	public void limparFormulario(){		
		habilitaLimpar = false;
		notaFiscalSelecionada = null;
		listaNrGuiasDisponivel = new ArrayList<String>();
		listaAnoNrGuiasDisponivel = new ArrayList<String>();
		listaopmeQtBipap= new ArrayList<OpmeQtBipap>();
		notaFiscal = new NotaFiscal();	
		lgBeneficiarioTravado = false;
		lgFornecedorTravado = false;
		lgHospitalTravado = false;
		valorDetalheTotal = 0;
	}

	public void limpaFornecedor(){
		if (habilitaLimpar == false){
			Prestador fornecedor = new Prestador();
			notaFiscal.setFornecedor(fornecedor);
			lgFornecedorTravado = false;
		}
		else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Não é possível alterar fornecedor!", ""));
			return;
		}
	}

	public void limpaBeneficiario(){

		if (notaFiscalSelecionada.isTravadoParcial() == false){
			Beneficiario beneficiario = new Beneficiario();
			notaFiscal.setBeneficiario(beneficiario);
			lgBeneficiarioTravado = false;
			notaFiscal.getGuiaAutorizacao().setAno(null);
			notaFiscal.getGuiaAutorizacao().setNumero(null);
			listaNrGuiasDisponivel = new ArrayList<String>();
			listaAnoNrGuiasDisponivel = new ArrayList<String>();
		}
		else{

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Data de transação gerada!", ""));
			return;
		}
	}

	public void limpaHospital(){

		if (notaFiscalSelecionada.isTravadoParcial() == false){
			Prestador hospital = new Prestador();
			notaFiscal.setHospital(hospital);
			lgHospitalTravado = false;
		}
		else{

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Data de transação gerada!", ""));
			return;

		}
	}		

	public void limpaInsumo(){
		OpmeQtBipap opmeQtBipapItemNovo = new OpmeQtBipap();
		opmeQtBipapItem = opmeQtBipapItemNovo;		
		lgInsumoTravado = false;
	}

	public void limpaInsumoAlteracao(){

		opmeQtBipapItem.setCdInsumo(null);
		opmeQtBipapItem.setNomeInsumo(null);
		opmeQtBipapItem.setTpInsumo(null);
		lgInsumoTravadoAlteracao = false;
	}	
	
	public void inserirNotaFiscalDetalhe(){

		boolean modificou = notaFiscalService.inserirNotaFiscalDetalhe(notaFiscal, notaFiscalSelecionada, opmeQtBipapItem, listaopmeQtBipap, valorDetalheTotal);
		if (modificou == true){
			opmeQtBipapItem = new OpmeQtBipap();
			lgInsumoTravado = false;		
			carregarDetalhesNota();
		}
	}
	
	public void liberaAnoGuia(){
		GuiaAutorizacaoDAO guiaAutorizacaoDAO = new GuiaAutorizacaoDAO();
		listaNrGuiasDisponivel = guiaAutorizacaoDAO.buscarGuiaPorBeneficiario(notaFiscal.getBeneficiario().getUnidade(), notaFiscal.getBeneficiario().getCdCarteiraInteira().toString(),notaFiscal.getGuiaAutorizacao().getAno());
	}

	public void BuscarGuiaBenef(String unidade,String carteira){
		GuiaAutorizacaoDAO guiaAutorizacaoDAO = new GuiaAutorizacaoDAO();
		listaAnoNrGuiasDisponivel = guiaAutorizacaoDAO.buscarAnoGuiaPorBeneficiario(unidade, carteira);
	}

	public void selecionaInsumo(){		
		opmeQtBipapItem = opmeQtBipapSelecionado;
		lgInsumoTravadoAlteracao = true;
	}	

	public void ValidaBeneficiarioUnidade(){}	
	
	public List<Prestador> BucarFornecedorNome(String codigo){			
		return notaFiscalService.BucarFornecedorNome(codigo);
	}

	public List<Prestador> BucarHospitalNome(String codigo){		
		return notaFiscalService.BucarHospitalNome(codigo);		
	}

	public List<OpmeQtBipap> BuscaInsumoNome(String codigo){			
		return notaFiscalService.BuscaInsumoNome(codigo, opmeQtBipapItem);		
	}

	public void onItemSelectFornecedor(SelectEvent event) {
		lgFornecedorTravado = true;
	}

	public void onItemSelectHospital(SelectEvent event) {		
		lgHospitalTravado = true;
	}

	public void onItemSelectInsumo(SelectEvent event) {		
		lgInsumoTravado = true;
		lgInsumoTravadoAlteracao = true;
	}

	public ArrayList<NotaFiscal> getListaNotaFiscal() {
		return listaNotaFiscal;
	}
	public void setListaNotaFiscal(ArrayList<NotaFiscal> listaNotaFiscal) {
		this.listaNotaFiscal = listaNotaFiscal;
	}
	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
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
	public boolean isHabilitaLimpar() {
		return habilitaLimpar;
	}
	public void setHabilitaLimpar(boolean habilitaLimpar) {
		this.habilitaLimpar = habilitaLimpar;
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
	public boolean isLgInsumoTravado() {
		return lgInsumoTravado;
	}
	public void setLgInsumoTravado(boolean lgInsumoTravado) {
		this.lgInsumoTravado = lgInsumoTravado;
	}
	public List<OpmeQtBipap> getListaInsumos() {
		return listaInsumos;
	}
	public void setListaInsumos(List<OpmeQtBipap> listaInsumos) {
		this.listaInsumos = listaInsumos;
	}
	public boolean isLgInsumoTravadoAlteracao() {
		return lgInsumoTravadoAlteracao;
	}
	public void setLgInsumoTravadoAlteracao(boolean lgInsumoTravadoAlteracao) {
		this.lgInsumoTravadoAlteracao = lgInsumoTravadoAlteracao;
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
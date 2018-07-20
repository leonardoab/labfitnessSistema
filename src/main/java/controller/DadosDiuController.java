package controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Beneficiario;
import model.Diu;
import model.NotaFiscal;
import model.Prestador;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import services.NotaFiscalService;
import dao.BeneficiarioDAO;
import dao.DetalheNotaDAO;
import dao.GuiaAutorizacaoDAO;
import dao.NotaFiscalDAO;
import dao.PrestardorDAO;

@Named
@ViewScoped
public class DadosDiuController implements Serializable{

	private static final long serialVersionUID = 1L;

	private NotaFiscal notaFiscalSelecionada;	

	private boolean validou = false;
	private boolean lgFornecedorTravado = false;
	private boolean lgHospitalTravado = false;
	private boolean lgBeneficiarioTravado = false;	
	private Integer quantidadeDiu;
	private int quantidadeValor;
	private float valorDetalheTotal;	
	private boolean habilitaLimpar;
	private boolean lgInsumoTravadoAlteracao = true;
	private NotaFiscal notaFiscal = new NotaFiscal();	
	private Diu diuItem = new Diu();
	private Diu diuSelecionado = new Diu();
	private Diu diuBaixa;
	private List<String> listaPrestadorString = new ArrayList<String>();
	private List<String> listaNrGuiasDisponivel = new ArrayList<String>();
	private List<String> listaAnoNrGuiasDisponivel = new ArrayList<String>();
	private List<Prestador> listaPrestador = new ArrayList<Prestador>();	
	private ArrayList<NotaFiscal> listaNotaFiscal = new ArrayList<NotaFiscal>();
	private ArrayList<NotaFiscal> listaNotaFiscalFiltradas;
	private List<Diu> listadiu= new ArrayList<Diu>();
	private List<Diu> listadiuHistorico= new ArrayList<Diu>();
	private List<Beneficiario> listaBeneficiario = new ArrayList<Beneficiario>();
	private int ultimaSequencia; 
	private String situacaoFiltro;
	private boolean travaDiu;

	private NotaFiscalService notaFiscalService = new NotaFiscalService();

	@Inject
	LoginController loginController;

	@PostConstruct
	public void iniciar() {
		NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();		
		listaNotaFiscal = notaFicalDAO.buscarNotaTipoStatus("DIU","Aberta");

		for (NotaFiscal notaSelecionada : listaNotaFiscal ){

			List<Diu> listadiuCarteiras = new ArrayList<Diu>();
			DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();
			if (notaSelecionada != null){
				listadiuCarteiras = detalheNotaDAO.buscarDetalhesNotaDiu(notaSelecionada.getId());
				String carteiras = "";
				for (Diu diuSelecionado : listadiuCarteiras){
					if(diuSelecionado.getBeneficiario().getUnidade() != null && !diuSelecionado.getBeneficiario().getUnidade().equals("0000")){
						carteiras = diuSelecionado.getBeneficiario().getCdCarteiraInteiraString() + " ; " + carteiras ;
					}
				}
				notaSelecionada.setCarteirasInternas(carteiras);
			}
		}
	}


	public void BuscarPorStatus(){
		listaNotaFiscalFiltradas = null;

		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("DiuForm:gridNotas");
		dataTable.clearInitialState();
		dataTable.reset();

		NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();	
		if (situacaoFiltro.equals("Aberta")) listaNotaFiscal = notaFicalDAO.buscarNotaTipoStatus("DIU","Aberta");
		else if (situacaoFiltro.equals("Todas")) listaNotaFiscal = notaFicalDAO.buscarNotaTipo("DIU");

		for (NotaFiscal notaSelecionada : listaNotaFiscal ){

			listadiu = new ArrayList<Diu>();
			DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();
			if (notaSelecionada != null){
				listadiu = detalheNotaDAO.buscarDetalhesNotaDiu(notaSelecionada.getId());
				String carteiras = "";
				for (Diu diuSelecionado : listadiu){
					if(diuSelecionado.getBeneficiario().getUnidade() != null && !diuSelecionado.getBeneficiario().getUnidade().equals("0000")){
						carteiras = diuSelecionado.getBeneficiario().getCdCarteiraInteiraString() + " ; " + carteiras ;
					}
				}
				notaSelecionada.setCarteirasInternas(carteiras);
			}
		}

	}	

	public void inserirNotaFiscal(){
		boolean modificou = notaFiscalService.inserirNotaFiscal("DIU", notaFiscal, notaFiscalSelecionada, null, valorDetalheTotal,loginController.getColaborador().getMatricula().toString(),listadiu);		

		if (notaFiscalSelecionada == null && modificou == true){						
			notaFiscal = new NotaFiscal();
			listadiu = new ArrayList<Diu>();
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
		BuscarGuiaBenef(notaFiscalSelecionada.getBeneficiario().getUnidade(),notaFiscalSelecionada.getBeneficiario().getCdCarteiraInteira().toString());
		listaNrGuiasDisponivel = guiaAutorizacaoDAO.buscarGuiaPorBeneficiarioDiu(notaFiscalSelecionada.getBeneficiario().getUnidade(), notaFiscalSelecionada.getBeneficiario().getCdCarteiraInteira().toString(),notaFiscalSelecionada.getGuiaAutorizacao().getAno());
		notaFiscal = notaFiscalSelecionada;	
		iniciar();
		carregarDetalhesNota();
		lgBeneficiarioTravado = true;
		lgFornecedorTravado = true;
		lgHospitalTravado = true;
	}

	public void inserirNotaFiscalDetalhe() throws ParseException{
		if(diuItem.getTipo() == ""){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Preencha o campo Tipo DIU", ""));
			return;
		}
		else if (diuItem.getValor() == 0 || diuItem.getValor() < 0 ){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Preencha o campo Valor Total DIU!", ""));
			return;
		}
		else if (quantidadeDiu == 0 || quantidadeDiu < 0 ){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Preencha o campo Quantidade DIU!", ""));
			return;
		}
		verificaUltimoDiu();
		int contador;
		float total = 0;
		List<Diu> listaDiuAuxiliar = new ArrayList<Diu>();
		for (contador = 0;contador < quantidadeDiu ;contador++){
			Diu diuAux = new Diu();
			diuAux.setSequencia((contador + ultimaSequencia));
			float valorItem = diuItem.getValor()/quantidadeDiu;
			valorItem = testRound(valorItem);
			diuAux.setValor(valorItem);
			diuAux.setTipo(diuItem.getTipo());
			total = testRound(total);
			total = total + valorItem;		
			listaDiuAuxiliar.add(diuAux);	
		}
		if (notaFiscalSelecionada != null){
			for (Diu diuAux:listaDiuAuxiliar){	
				if (total != diuItem.getValor()){
					diuAux.setValor((float) (diuAux.getValor() + 0.01));
					total = (float) (total + 0.01);
					total = testRound(total);
				}
				DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();		
				diuAux.setSituacao("A");
				Date data = new Date();
				diuAux.setDtLancamento(data);
				diuAux = detalheNotaDAO.persistirDiu(diuAux, notaFiscalSelecionada.getId());
			}

			carregarDetalhesNota();
			notaFiscalSelecionada.setQtdItens(notaFiscalSelecionada.getQtdItens() + quantidadeDiu);
			NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();
			notaFicalDAO.persistir(notaFiscalSelecionada);
			diuItem = new Diu();
			quantidadeDiu = null;
		}
		else {
			for (Diu diuAux:listaDiuAuxiliar){	
				quantidadeValor ++;
				if (total != diuItem.getValor()){
					diuAux.setValor((float) (diuAux.getValor() + 0.01));
					total = (float) (total + 0.01);
					total = testRound(total);
				}
				diuAux.setSituacao("A");
				Date data = new Date();
				diuAux.setDtLancamento(data);
				listadiu.add(diuAux);
				valorDetalheTotal = (float) (valorDetalheTotal + diuAux.getValor());
			}
			notaFiscal.setQtdItens(quantidadeValor);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item da nota fiscal cadastrada!", ""));
			diuItem = new Diu();
			quantidadeDiu = null;
		}
	}

	public void carregarDetalhesNota(){
		listadiu = new ArrayList<Diu>();
		if (notaFiscalSelecionada != null){
			notaFiscal = notaFiscalSelecionada;
		}
		DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();
		if (notaFiscalSelecionada != null){
			listadiu = detalheNotaDAO.buscarDetalhesNotaDiu(notaFiscalSelecionada.getId());
		}
		if (notaFiscalSelecionada != null){
			valorDetalheTotal = 0;
			for (Diu diu : listadiu){
				valorDetalheTotal = (float) (valorDetalheTotal + diu.getValor()); 	
			}
		}


		verificaUltimoDiu();
	}

	public void excluirDiu(){
		if (notaFiscalSelecionada != null){
			Diu diuProcura;
			for (Diu diuQuestao : listadiu){
				DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();
				diuProcura = detalheNotaDAO.RecuperarPorIdPrincipal(diuQuestao.getId());
				if (diuProcura != null || !diuQuestao.getBeneficiario().getUnidade().equals("0000") ){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exite movimentos nesta nota não é possivel excluir!", ""));
					return;
				} 
			}
			DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();
			for (Diu diuQuestao : listadiu){
				detalheNotaDAO.deletarPorId(diuQuestao.getId());
			}
			notaFiscalSelecionada.setQtdItens(0);
			listadiu= new ArrayList<Diu>();
			NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();
			notaFicalDAO.persistir(notaFiscalSelecionada);
			valorDetalheTotal = 0;
		}
		else{
			listadiu = new ArrayList<Diu>();
			valorDetalheTotal = 0;
		}		
		iniciar();
	}

	//public void gravaEdicaoInsumo(){

	public void verificaUltimoDiu(){
		if (listadiu != null && listadiu.size() > 0){
			ultimaSequencia = listadiu.get((listadiu.size() - 1)).getSequencia() + 1;
		}
		else {
			ultimaSequencia = 1;
		}
	}

	public void carregaHistoricoDiu(){
		if (notaFiscalSelecionada == null){
			return;
		}
		listadiuHistorico = new ArrayList<Diu>();
		Diu diuProcura;
		DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();	
		diuProcura = detalheNotaDAO.RecuperarPorIdPrincipal(diuSelecionado.getId()); 
		while(diuProcura != null){
			listadiuHistorico.add(diuProcura);
			diuProcura = detalheNotaDAO.RecuperarPorIdPrincipal(diuProcura.getId()); 
		}
	}

	public void devolverDiu(){
		DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();
		Diu diuSelecionadoPosterior = new Diu();
		diuSelecionadoPosterior.setTipo(diuSelecionado.getTipo());
		diuSelecionadoPosterior.setValor(diuSelecionado.getValor());
		diuSelecionadoPosterior.setDtLancamento(diuSelecionado.getDtLancamento());
		diuSelecionadoPosterior.setSequencia(diuSelecionado.getSequencia());
		diuSelecionadoPosterior.setSituacao("A");
		diuSelecionadoPosterior = detalheNotaDAO.persistirDiu(diuSelecionadoPosterior, notaFiscalSelecionada.getId());
		Long valorId = diuSelecionadoPosterior.getId();
		diuSelecionado.setSituacao("H");	
		detalheNotaDAO.persistirDiu(diuSelecionado, valorId);
		carregarDetalhesNota();
		diuSelecionado = diuSelecionadoPosterior;


		listadiu = new ArrayList<Diu>();
		listadiu = detalheNotaDAO.buscarDetalhesNotaDiu(notaFiscalSelecionada.getId());
		String carteiras = "";
		for (Diu diuSelecionado : listadiu){
			if(diuSelecionado.getBeneficiario().getUnidade() != null && !diuSelecionado.getBeneficiario().getUnidade().equals("0000")){
				carteiras = diuSelecionado.getBeneficiario().getCdCarteiraInteiraString() + " ; " + carteiras ;
			}
		}
		notaFiscalSelecionada.setCarteirasInternas(carteiras);



	}

	public void salvarRetirada(){		

		if (notaFiscalSelecionada == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Só é possivel realizar a entrega DIU se a cabeça da nota estiver efetivada!", ""));
			return;
		}
		diuSelecionado.setValor(diuBaixa.getValor());
		diuSelecionado.setTipo(diuBaixa.getTipo());
		diuSelecionado.setId(diuBaixa.getId());
		diuSelecionado.setSequencia(diuBaixa.getSequencia());
		diuSelecionado.setSituacao("A");
		diuBaixa = diuSelecionado;
		Date data = new Date();
		diuBaixa.setDtLancamento(data);
		DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();	
		detalheNotaDAO.persistirDiu(diuBaixa, notaFiscalSelecionada.getId());
		carregarDetalhesNota();

		listadiu = new ArrayList<Diu>();
		listadiu = detalheNotaDAO.buscarDetalhesNotaDiu(notaFiscalSelecionada.getId());
		String carteiras = "";
		for (Diu diuSelecionado : listadiu){
			if(diuSelecionado.getBeneficiario().getUnidade() != null && !diuSelecionado.getBeneficiario().getUnidade().equals("0000")){
				carteiras = diuSelecionado.getBeneficiario().getCdCarteiraInteiraString() + " ; " + carteiras ;
			}
		}
		notaFiscalSelecionada.setCarteirasInternas(carteiras);	

	}

	public void limparDadosDiuDetalhe(){
		diuSelecionado = new Diu();
		lgBeneficiarioTravado = false;
		lgFornecedorTravado = false;
	}	

	public List<Prestador> BucarFornecedorNome(String codigo){			
		return notaFiscalService.BucarFornecedorNome(codigo);
	}

	public List<Prestador> BucarMedicoNome(String codigo){
		return notaFiscalService.BucarMedicoNome(codigo);		
	}

	public List<Prestador> BucarHospitalNome(String codigo){		
		return notaFiscalService.BucarHospitalNome(codigo);		
	}

	public void onItemSelectFornecedor(SelectEvent event) {
		lgFornecedorTravado = true;
	}

	public void onItemSelectHospital(SelectEvent event) {		
		lgHospitalTravado = true;
	}

	public void BuscarGuiaBenef(String unidade,String carteira){
		GuiaAutorizacaoDAO guiaAutorizacaoDAO = new GuiaAutorizacaoDAO();
		listaAnoNrGuiasDisponivel = guiaAutorizacaoDAO.buscarAnoGuiaPorBeneficiarioDiu(unidade, carteira);
	}

	public void liberaAnoGuia(){
		GuiaAutorizacaoDAO guiaAutorizacaoDAO = new GuiaAutorizacaoDAO();
		listaNrGuiasDisponivel = guiaAutorizacaoDAO.buscarGuiaPorBeneficiarioDiu(diuSelecionado.getBeneficiario().getUnidade(), diuSelecionado.getBeneficiario().getCdCarteiraInteira().toString(),diuSelecionado.getGuiaAutorizacao().getAno());
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

	public void ValidaMedico(){				
		PrestardorDAO preservDAO = new PrestardorDAO();
		Prestador fornecedor;
		fornecedor = preservDAO.buscarCodigoMedico(diuSelecionado.getMedico().getCdPrestador());
		if (fornecedor == null ){			
			notaFiscal.getFornecedor().setCdPrestador(null);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Médico não encontrado!", ""));
			return;			
		}	
		else {
			diuSelecionado.setMedico(fornecedor); 
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

	public void ValidaBeneficiarioUnidade(){}	

	public void AlteraDiu(){

		diuBaixa = diuSelecionado;
		GuiaAutorizacaoDAO guiaAutorizacaoDAO = new GuiaAutorizacaoDAO();
		listaAnoNrGuiasDisponivel = guiaAutorizacaoDAO.buscarAnoGuiaPorBeneficiarioDiu(diuBaixa.getBeneficiario().getUnidade(), diuBaixa.getBeneficiario().getCdCarteiraInteira());
		listaNrGuiasDisponivel = guiaAutorizacaoDAO.buscarGuiaPorBeneficiarioDiu(diuBaixa.getBeneficiario().getUnidade(), diuBaixa.getBeneficiario().getCdCarteiraInteira().toString(),diuBaixa.getGuiaAutorizacao().getAno());
		lgBeneficiarioTravado = true;
		lgFornecedorTravado = true;

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

	public void ValidaBeneficiarioDiu(){
		BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
		Beneficiario beneficiarioNota = diuSelecionado.getBeneficiario();
		if (beneficiarioNota == null || beneficiarioNota.getCdCarteiraInteira() == null || beneficiarioNota.getCdCarteiraInteira() == null ){
			beneficiarioNota.setUnidade(null);
			beneficiarioNota.setCdCarteiraInteira( null);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Carteira não encontrada!", ""));
			return;
		}
		if (!beneficiarioNota.getUnidade().equals("0049")) { //ajustar para outuni
			Beneficiario beneficiarioAuxiliar = beneficiarioDAO.buscarPorBeneficiarioInter(diuSelecionado.getBeneficiario().getUnidade(),diuSelecionado.getBeneficiario().getCdCarteiraInteira().toString());
			beneficiarioNota.setNome(beneficiarioAuxiliar.getNome());
			if (diuSelecionado.getBeneficiario().getNome() == null){
				beneficiarioNota.setUnidade(null);
				beneficiarioNota.setCdCarteiraInteira( null);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Carteira não encontrada!", ""));
				return;
			}
		}
		else {
			Beneficiario beneficiarioAuxiliar = beneficiarioDAO.buscarPorBeneficiario(diuSelecionado.getBeneficiario().getCdCarteiraInteira().toString());
			beneficiarioNota.setNome(beneficiarioAuxiliar.getNome());
			if (diuSelecionado.getBeneficiario().getNome() == null){
				beneficiarioNota.setUnidade(null);
				beneficiarioNota.setCdCarteiraInteira( null);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Carteira não encontrada!", ""));
				return;
			}
		}
		diuSelecionado.setBeneficiario(beneficiarioNota);	
		BuscarGuiaBenef(diuSelecionado.getBeneficiario().getUnidade(),diuSelecionado.getBeneficiario().getCdCarteiraInteira().toString());
		lgBeneficiarioTravado = true;		
	}

	public void limparFormulario(){		
		habilitaLimpar = false;
		listaNrGuiasDisponivel = new ArrayList<String>();
		listaAnoNrGuiasDisponivel = new ArrayList<String>();
		listadiu= new ArrayList<Diu>();
		listadiuHistorico = new ArrayList<Diu>();
		notaFiscalSelecionada = null;
		quantidadeValor = 0;
		valorDetalheTotal = 0;
		notaFiscal = new NotaFiscal();	
		notaFiscalSelecionada = null;
		lgBeneficiarioTravado = false;
		lgFornecedorTravado = false;
		lgHospitalTravado = false;
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

	public void limpaMedico(){
		Prestador medico = new Prestador();
		diuSelecionado.setMedico(medico);
		lgFornecedorTravado = false;
	}

	public void limpaBeneficiario(){
		Beneficiario beneficiario = new Beneficiario();
		diuSelecionado.setBeneficiario(beneficiario);
		lgBeneficiarioTravado = false;
		diuSelecionado.getGuiaAutorizacao().setAno(null);
		diuSelecionado.getGuiaAutorizacao().setNumero(null);
		listaNrGuiasDisponivel = new ArrayList<String>();
		listaAnoNrGuiasDisponivel = new ArrayList<String>();		
	}

	public void limpaHospital(){
		Prestador hospital = new Prestador();
		notaFiscal.setHospital(hospital);
		lgHospitalTravado = false;
	}

	/*public float arredondar(double valor, int casas, int ceilOrFloor) {  
		float arredondado = valor;  
		arredondado *= (Math.pow(10, casas));  
		if (ceilOrFloor == 0) {  
			arredondado = Math.ceil(arredondado);             
		} else {  
			arredondado = Math.floor(arredondado);  
		}  
		arredondado /= (Math.pow(10, casas));  
		return arredondado;  
	}  	*/

	public Float testRound(Float numero) {
		int decimalPlace = 2;
		BigDecimal bd = new BigDecimal(numero);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		numero = (float) bd.doubleValue();
		return numero;
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
	public boolean isLgHospitalTravado() {
		return lgHospitalTravado;
	}
	public void setLgHospitalTravado(boolean lgHospitalTravado) {
		this.lgHospitalTravado = lgHospitalTravado;
	}
	public Diu getDiuItem() {
		return diuItem;
	}
	public void setDiuItem(Diu diuItem) {
		this.diuItem = diuItem;
	}
	public Diu getDiuSelecionado() {
		return diuSelecionado;
	}
	public void setDiuSelecionado(Diu diuSelecionado) {
		this.diuSelecionado = diuSelecionado;
	}
	public List<Diu> getListadiu() {
		return listadiu;
	}
	public void setListadiu(List<Diu> listadiu) {
		this.listadiu = listadiu;
	}
	public Integer getQuantidadeDiu() {
		return quantidadeDiu;
	}
	public void setQuantidadeDiu(Integer quantidadeDiu) {
		this.quantidadeDiu = quantidadeDiu;
	}
	public Diu getDiuBaixa() {
		return diuBaixa;
	}
	public void setDiuBaixa(Diu diuBaixa) {
		this.diuBaixa = diuBaixa;
	}
	public List<Diu> getListadiuHistorico() {
		return listadiuHistorico;
	}
	public void setListadiuHistorico(List<Diu> listadiuHistorico) {
		this.listadiuHistorico = listadiuHistorico;
	}
	public int getQuantidadeValor() {
		return quantidadeValor;
	}
	public void setQuantidadeValor(int quantidadeValor) {
		this.quantidadeValor = quantidadeValor;
	}
	public float getValorDetalheTotal() {
		return valorDetalheTotal;
	}
	public void setValorDetalheTotal(float valorDetalheTotal) {
		this.valorDetalheTotal = valorDetalheTotal;
	}
	public int getUltimaSequencia() {
		return ultimaSequencia;
	}
	public void setUltimaSequencia(int ultimaSequencia) {
		this.ultimaSequencia = ultimaSequencia;
	}
	public boolean isHabilitaLimpar() {
		return habilitaLimpar;
	}
	public void setHabilitaLimpar(boolean habilitaLimpar) {
		this.habilitaLimpar = habilitaLimpar;
	}	
	public boolean isHabilitaDetalhe() {
		return !habilitaLimpar;
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


	public boolean isTravaDiu() {

		if(loginController.getColaborador().getPerfilAcesso().equals("DIU")) return true;
		else return false;


	}


	public void setTravaDiu(boolean travaDiu) {
		this.travaDiu = travaDiu;
	}



}
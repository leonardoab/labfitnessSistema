package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Competencia;
import model.Diu;
import model.NotaFiscal;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.component.datatable.DataTable;

import dao.CompetenciaDAO;
import dao.DetalheNotaDAO;
import dao.NotaFiscalDAO;

@Named
@ViewScoped
public class BaixasController implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<NotaFiscal> listaNotaFiscal = new ArrayList<NotaFiscal>();
	private ArrayList<NotaFiscal> listaNotaFiscalIntervalo = new ArrayList<NotaFiscal>();
	private ArrayList<NotaFiscal> listaNotaFiscalFiltrada;
	private String dataInicio;
	private String dataFinal;
	private String situacaoFiltro;
	private NotaFiscal notaFiscalSelecionada;
	private NotaFiscal notaFiscal = new NotaFiscal();	
	private ArrayList<Competencia> listaCompetencias;
	private String status;
	private String competenciaBaixa;

	@Inject
	LoginController loginController;

	@PostConstruct
	public void iniciar() {


		buscarNotas("Aberta");
		//listaNotaFiscal = notaFicalDAO.buscarTodasNotas();


	}


	public void buscarNotas(String status){
		NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();	

		listaNotaFiscal = notaFicalDAO.buscarTodasNotasStatus(status,"01/01/1900","31/12/9999",true);

		ArrayList<NotaFiscal> listaNotaFiscalInteracao = new ArrayList<NotaFiscal>();
		listaNotaFiscalInteracao.addAll(listaNotaFiscal);		

		for (NotaFiscal notaFiscalSelecionado : listaNotaFiscalInteracao){			

			if(notaFiscalSelecionado.getTipoRegistro().equals("DIU")){

				listaNotaFiscal.remove(notaFiscalSelecionado);

				List<Diu> listadiu= new ArrayList<Diu>();
				DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();	
				listadiu = detalheNotaDAO.buscarDetalhesNotaDiu(notaFiscalSelecionado.getId());
				boolean fechar = true;

				for (Diu diu : listadiu){

					if (diu.getCompetenciaBaixa() == null || diu.getCompetenciaBaixa().equals("")){

						fechar = false;

					}




					if (diu.getDtSolicitacao() == null) continue;
					if (diu.getBeneficiario().getUnidade().equals("0000")) continue;					
					if(status.equals("Aberta") && !diu.getStatus().equals("Aberta")) continue;
					if(status.equals("Baixadas") && (diu.getStatus().equals("Aberta") || diu.getStatus().equals("Cancelada"))) continue;





					NotaFiscal notaFiscalNova = new NotaFiscal();
					notaFiscalNova.setTipoRegistro(notaFiscalSelecionado.getTipoRegistro());
					notaFiscalNova.setNumero(notaFiscalSelecionado.getNumero());
					float b = Float.parseFloat( (diu.getValor().toString())) ;
					notaFiscalNova.setValor(b);
					notaFiscalNova.setFornecedor(notaFiscalSelecionado.getFornecedor());
					notaFiscalNova.setStatus(diu.getStatus());
					notaFiscalNova.setBeneficiario(diu.getBeneficiario());
					notaFiscalNova.setDataTransacao(notaFiscalSelecionado.getDataTransacao());
					notaFiscalNova.setCompetenciaBaixa(diu.getCompetenciaBaixa());
					notaFiscalNova.setGuiaAutorizacao(diu.getGuiaAutorizacao());
					notaFiscalNova.setId(notaFiscalSelecionado.getId());

					ArrayList<Diu> listaDiu = new ArrayList<Diu>();
					listaDiu.add(diu);
					notaFiscalNova.setListaDiu(listaDiu);

					listaNotaFiscal.add(notaFiscalNova);



				}
				if (fechar == true){



					if (listadiu == null || listadiu.size() == 0) continue;

					notaFiscalSelecionado.setUsuarioAtualizacao(loginController.getColaborador().getMatricula().toString());
					notaFiscalSelecionado.setUsuarioInsercao(loginController.getColaborador().getMatricula().toString());
					notaFiscalSelecionado.setStatus("Baixa Completa");
					notaFicalDAO.persistir(notaFiscalSelecionado);			


				}


			}	

		}

	}

	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);       

		HSSFRow header = sheet.getRow(0);

		Iterator rowIter = sheet.rowIterator();

		while(rowIter.hasNext()) {

			HSSFRow r = (HSSFRow)rowIter.next();
			HSSFCell cell = r.getCell(2);

			if (!cell.getStringCellValue().equals("Valor Nota")){        		

				cell.setCellValue(Double.parseDouble(cell.getStringCellValue().replace(",", ".")));
			}        	
		}
	}

	public void BuscarPorStatus(){

		listaNotaFiscalFiltrada = null;

		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("baixasForm:gridNotas");
		dataTable.clearInitialState();
		dataTable.reset();


		if (situacaoFiltro.equals("Aberta")) buscarNotas("Aberta");
		else if (situacaoFiltro.equals("Baixadas")) buscarNotas("Baixadas");
		else if (situacaoFiltro.equals("Todas"))  buscarNotas("Todas");

	}	

	public void carregaDatasTrasacao(){
		NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();

		for (NotaFiscal notaFiscalSelecionado : listaNotaFiscal){			
			notaFicalDAO.persistir(notaFiscalSelecionado);				
		}			
		listaNotaFiscal = new ArrayList<NotaFiscal>();
		listaNotaFiscal = notaFicalDAO.buscarTodasNotas();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Datas de transação atualizadas!", ""));
	}

	public void inserirNotaFiscal(){

		if (competenciaBaixa == null){
			competenciaBaixa = "";			
		}

		situacaoFiltro = "Aberta";

		if (!notaFiscalSelecionada.getStatus().equals("Aberta") && !notaFiscalSelecionada.getStatus().equals("Cancelada")){

			if (notaFiscal.getCompetenciaBaixa() == null || notaFiscal.getCompetenciaBaixa().equals("")){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Preencha o campo competência!", ""));
				notaFiscal.setStatus(status);
				notaFiscal.setCompetenciaBaixa(competenciaBaixa);
				return;

			}

		} else if (notaFiscalSelecionada.getStatus().equals("Aberta") && notaFiscalSelecionada.getCompetenciaBaixa() != null && !notaFiscalSelecionada.getCompetenciaBaixa().equals("")){

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Para esta situação o campo competência não deve estar preenchido!", ""));
			notaFiscal.setStatus(status);
			notaFiscal.setCompetenciaBaixa(competenciaBaixa);
			return;



		}





		CompetenciaDAO competenciaDAO = new CompetenciaDAO();



		listaCompetencias = competenciaDAO.buscarCompetencias();
		for(Competencia competencia : listaCompetencias){
			
			/*System.out.println(competencia.getDescritivo().equals(notaFiscalSelecionada.getCompetenciaBaixa()));
			System.out.println(competenciaBaixa.equals(competencia.getDescritivo()));
			System.out.println(competenciaBaixa.equals(""));*/
			

			if ( (competencia.getDescritivo().equals(notaFiscalSelecionada.getCompetenciaBaixa()) ||  
				 (competenciaBaixa.equals(competencia.getDescritivo()  ) && !competenciaBaixa.equals("")   )) && 
				 competencia.getTravado() == 1){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Competencia Travada Contabilidade!", ""));
				notaFiscal.setStatus(status);
				notaFiscal.setCompetenciaBaixa(competenciaBaixa);
				return;

			}

		}

		if (notaFiscal.getTipoRegistro().equals("DIU")){

			notaFiscal.getListaDiu().get(0).setCompetenciaBaixa(notaFiscal.getCompetenciaBaixa());
			notaFiscal.getListaDiu().get(0).setStatus(notaFiscal.getStatus());
			notaFiscal.getListaDiu().get(0).setSituacao("A");

			DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();	
			notaFiscal.getListaDiu().get(0).setUsuarioAtualizacao(loginController.getColaborador().getMatricula().toString());
			detalheNotaDAO.persistirDiu(notaFiscal.getListaDiu().get(0), notaFiscal.getId());


		}
		else {
			NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();
			notaFiscal.setUsuarioAtualizacao(loginController.getColaborador().getMatricula().toString());
			notaFiscal.setUsuarioInsercao(loginController.getColaborador().getMatricula().toString());
			notaFicalDAO.persistir(notaFiscal);			
		}



		iniciar();

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nota Fiscal Salva!", ""));

	}


	public void carregarNota(){

		notaFiscal = notaFiscalSelecionada;	
		status = notaFiscalSelecionada.getStatus();
		competenciaBaixa  = notaFiscalSelecionada.getCompetenciaBaixa();


	}

	public void ValidaBeneficiarioUnidade(){}

	public void carregarTabela(){
		NotaFiscalDAO notaFiscalDAO = new NotaFiscalDAO();
		listaNotaFiscalFiltrada = notaFiscalDAO.buscarNotaIntervalo(dataInicio, dataFinal);
	}

	public ArrayList<NotaFiscal> getListaNotaFiscal() {
		return listaNotaFiscal;
	}
	public void setListaNotaFiscal(ArrayList<NotaFiscal> listaNotaFiscal) {
		this.listaNotaFiscal = listaNotaFiscal;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public ArrayList<NotaFiscal> getListaNotaFiscalFiltrada() {
		return listaNotaFiscalFiltrada;
	}
	public void setListaNotaFiscalFiltrada(
			ArrayList<NotaFiscal> listaNotaFiscalFiltrada) {
		this.listaNotaFiscalFiltrada = listaNotaFiscalFiltrada;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

	public ArrayList<NotaFiscal> getListaNotaFiscalIntervalo() {
		return listaNotaFiscalIntervalo;
	}

	public void setListaNotaFiscalIntervalo(ArrayList<NotaFiscal> listaNotaFiscalIntervalo) {
		this.listaNotaFiscalIntervalo = listaNotaFiscalIntervalo;
	}


	public String getSituacaoFiltro() {
		return situacaoFiltro;
	}


	public void setSituacaoFiltro(String situacaoFiltro) {
		this.situacaoFiltro = situacaoFiltro;
	}

	public NotaFiscal getNotaFiscalSelecionada() {
		return notaFiscalSelecionada;
	}

	public void setNotaFiscalSelecionada(NotaFiscal notaFiscalSelecionada) {
		this.notaFiscalSelecionada = notaFiscalSelecionada;
	}

	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}


}

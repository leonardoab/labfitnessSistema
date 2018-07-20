package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import model.Diu;
import model.NotaFiscal;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.component.datatable.DataTable;

import dao.DetalheNotaDAO;
import dao.NotaFiscalDAO;

@Named
@ViewScoped
public class RelatorioTransacaoController implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<NotaFiscal> listaNotaFiscal = new ArrayList<NotaFiscal>();
	private ArrayList<NotaFiscal> listaNotaFiscalIntervalo = new ArrayList<NotaFiscal>();
	private ArrayList<NotaFiscal> listaDiu = new ArrayList<NotaFiscal>();
	private ArrayList<NotaFiscal> listaNotaFiscalFiltrada;
	private String dataInicio;
	private String dataFinal;
	private String situacaoFiltro;

	@PostConstruct
	public void iniciar() {

		dataInicio = "01/01/1900";
		dataFinal = "31/12/9999";
		situacaoFiltro = "Aberta";

		carregarTabela();
		/*buscarNotas("Aberta");*/
		//listaNotaFiscal = notaFicalDAO.buscarTodasNotas();


	}


	/*public void buscarNotas(String status){
		NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();	

		listaNotaFiscal = notaFicalDAO.buscarTodasNotasStatus(status,"01/01/1900","31/12/9999",false);


	}*/

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

		/*for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
        }*/
	}

	/*public void BuscarPorStatus(){

		listaNotaFiscalFiltrada = null;		
		if (situacaoFiltro.equals("Aberta")) buscarNotas("Aberta");
		else if (situacaoFiltro.equals("Baixadas")) buscarNotas("Baixadas");
		else if (situacaoFiltro.equals("Todas"))  buscarNotas("Todas");

	}	*/

	public void carregaDatasTrasacao(){
		NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();

		for (NotaFiscal notaFiscalSelecionado : listaNotaFiscal){	

			if(!notaFiscalSelecionado.getTipoRegistro().equals("DIU")){
				notaFicalDAO.persistir(notaFiscalSelecionado);	
			}
		}	

		for (NotaFiscal notaFiscalSelecionado : listaDiu){

			notaFicalDAO.persistir(notaFiscalSelecionado);	

		}	
		listaNotaFiscal = new ArrayList<NotaFiscal>();



		listaNotaFiscal = notaFicalDAO.buscarTodasNotasStatus(situacaoFiltro,dataInicio,dataFinal,false);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Datas de transação atualizadas!", ""));
	}

	public void carregarTabela(){

		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("relatorioForm:gridNotas");
		dataTable.clearInitialState();
		dataTable.reset();
		
		listaNotaFiscalFiltrada = null;


		NotaFiscalDAO notaFiscalDAO = new NotaFiscalDAO();
		listaNotaFiscal = new ArrayList<NotaFiscal>();
		listaNotaFiscal = notaFiscalDAO.buscarTodasNotasStatus(situacaoFiltro,dataInicio,dataFinal,false);

		ArrayList<NotaFiscal> listaNotaFiscalInteracao = new ArrayList<NotaFiscal>();
		listaNotaFiscalInteracao.addAll(listaNotaFiscal);
		listaDiu = new ArrayList<NotaFiscal>();

		for (NotaFiscal notaFiscalSelecionado : listaNotaFiscalInteracao){			
			if(notaFiscalSelecionado.getTipoRegistro().equals("DIU")){

				listaNotaFiscal.remove(notaFiscalSelecionado);
				listaDiu.add(notaFiscalSelecionado);

				List<Diu> listadiu= new ArrayList<Diu>();
				DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();	
				listadiu = detalheNotaDAO.buscarDetalhesNotaDiu(notaFiscalSelecionado.getId());

				for (Diu diu : listadiu){


					if (situacaoFiltro.equals("Aberta") &&
							(diu.getStatus().equals("Ajuste Contabil") || 
									diu.getStatus().equals("Baixa Ajuste") || 
									diu.getStatus().equals("Baixa Completa") || 
									diu.getStatus().equals("Baixa Parcial") || 
									diu.getStatus().equals("Cancelada") 
									)) continue;

					if (situacaoFiltro.equals("Baixadas") &&
							(diu.getStatus().equals("Aberta") || 
									diu.getStatus().equals("Cancelada") 
									)) continue;


					
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
					notaFiscalNova.setHospital(diu.getMedico());

					listaNotaFiscal.add(notaFiscalNova);

				}
			}				
		}


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



}
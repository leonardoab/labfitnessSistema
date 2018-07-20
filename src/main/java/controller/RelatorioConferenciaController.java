package controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import model.Diu;
import model.NotaFiscal;
import model.OpmeQtBipap;
import dao.DetalheNotaDAO;
import dao.NotaFiscalDAO;

@Named
@ViewScoped
public class RelatorioConferenciaController implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<NotaFiscal> listaNotaFiscal = new ArrayList<NotaFiscal>();
	private ArrayList<NotaFiscal> listaNotaFiscalTodas = new ArrayList<NotaFiscal>();


	@PostConstruct
	public void iniciar() {
		NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();		
		listaNotaFiscalTodas = notaFicalDAO.buscarTodasNotas();


		for (NotaFiscal notaFiscalAnalisada :listaNotaFiscalTodas){

			float valorDetalhe = 0;
			DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();

			if (notaFiscalAnalisada.getTipoRegistro().equals("DIU")){
				List<Diu> listadiu= new ArrayList<Diu>();
				listadiu = detalheNotaDAO.buscarDetalhesNotaDiu(notaFiscalAnalisada.getId());

				for(Diu diuAnalisado : listadiu){

					valorDetalhe = (float) (diuAnalisado.getValor() + valorDetalhe);

				}
				
				valorDetalhe = testRound(valorDetalhe);

				if (valorDetalhe != notaFiscalAnalisada.getValor() && valorDetalhe != 0){

					notaFiscalAnalisada.setValorDetalhe(valorDetalhe);
					listaNotaFiscal.add(notaFiscalAnalisada);

				}

			}
			else{


				List<OpmeQtBipap> listaopmeQtBipap= new ArrayList<OpmeQtBipap>();
				listaopmeQtBipap = detalheNotaDAO.buscarDetalhesNotaOpmeQtBipap(notaFiscalAnalisada.getId());

				for(OpmeQtBipap opmeQtBipapAnalisado : listaopmeQtBipap){

					valorDetalhe = (opmeQtBipapAnalisado.getValor() + valorDetalhe);

				}
				
				valorDetalhe = testRound(valorDetalhe);

				if (valorDetalhe != notaFiscalAnalisada.getValor() && valorDetalhe != 0){

					notaFiscalAnalisada.setValorDetalhe(valorDetalhe);
					listaNotaFiscal.add(notaFiscalAnalisada);

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
        	HSSFCell cell = r.getCell(4);
        	
        	if (!cell.getStringCellValue().equals("Valor Nota")){
				cell.setCellValue(Double.parseDouble(cell.getStringCellValue().replace(",", ".")));
			}

			cell = r.getCell(5);

			if (!cell.getStringCellValue().equals("Valor Detalhe")){
				cell.setCellValue(Double.parseDouble(cell.getStringCellValue().replace(",", ".")));
			}   	
        }        
    }
	
	public Float testRound(Float numero) {
		int decimalPlace = 2;
		BigDecimal bd = new BigDecimal(numero);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		numero = (float) bd.doubleValue();
		return numero;
	}
	
	public void carregaDatasTrasacao(){
		NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();

		for (NotaFiscal notaFiscalSelecionado : listaNotaFiscal){

			notaFicalDAO.persistir(notaFiscalSelecionado);			

		}	

		listaNotaFiscal = new ArrayList<NotaFiscal>();
		listaNotaFiscal = notaFicalDAO.buscarTodasNotas();
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
	public ArrayList<NotaFiscal> getListaNotaFiscalTodas() {
		return listaNotaFiscalTodas;
	}
	public void setListaNotaFiscalTodas(ArrayList<NotaFiscal> listaNotaFiscalTodas) {
		this.listaNotaFiscalTodas = listaNotaFiscalTodas;
	}



}
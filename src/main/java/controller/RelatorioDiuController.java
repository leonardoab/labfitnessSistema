package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import model.Diu;
import model.NotaDetalhe;
import model.NotaFiscal;
import dao.DetalheNotaDAO;
import dao.NotaFiscalDAO;

@Named
@ViewScoped
public class RelatorioDiuController implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<NotaFiscal> listaNotaFiscal = new ArrayList<NotaFiscal>();
	private ArrayList<NotaDetalhe> listaNotaDetalhe = new ArrayList<NotaDetalhe>();
	private ArrayList<NotaDetalhe> listaNotaDetalheFiltrada;
	private List<Diu> listadiu= new ArrayList<Diu>();
	private String competencia;
	private String competenciaAlterada;
	private String dtinicio ;
	private String dtfinal ;

	@PostConstruct
	public void iniciar() {
		carregaDados();
	}


	public void carregaDados(){

		NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();	
		listaNotaFiscal = notaFicalDAO.buscarNotaTipo("DIU");


		for (NotaFiscal notaFiscalSelecionado : listaNotaFiscal){

			DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();	
			listadiu = detalheNotaDAO.buscarDetalhesNotaDiu(notaFiscalSelecionado.getId());

			for (Diu diu : listadiu){

				NotaDetalhe notaDetalhe = new NotaDetalhe();
				notaDetalhe.setNotaFiscal(notaFiscalSelecionado);
				notaDetalhe.setDiu(diu);
				listaNotaDetalhe.add(notaDetalhe);

			}

		}	



		/*System.out.println();*/	

	}
	public ArrayList<NotaFiscal> getListaNotaFiscal() {
		return listaNotaFiscal;
	}
	public void setListaNotaFiscal(ArrayList<NotaFiscal> listaNotaFiscal) {
		this.listaNotaFiscal = listaNotaFiscal;
	}
	public ArrayList<NotaDetalhe> getListaNotaDetalhe() {
		return listaNotaDetalhe;
	}
	public void setListaNotaDetalhe(ArrayList<NotaDetalhe> listaNotaDetalhe) {
		this.listaNotaDetalhe = listaNotaDetalhe;
	}
	public List<Diu> getListadiu() {
		return listadiu;
	}
	public void setListadiu(List<Diu> listadiu) {
		this.listadiu = listadiu;
	}
	public String getCompetencia() {
		return competencia;
	}
	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}
	public String getCompetenciaAlterada() {
		return competenciaAlterada;
	}
	public void setCompetenciaAlterada(String competenciaAlterada) {
		this.competenciaAlterada = competenciaAlterada;
	}
	public String getDtinicio() {
		return dtinicio;
	}
	public void setDtinicio(String dtinicio) {
		this.dtinicio = dtinicio;
	}
	public String getDtfinal() {
		return dtfinal;
	}
	public void setDtfinal(String dtfinal) {
		this.dtfinal = dtfinal;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public ArrayList<NotaDetalhe> getListaNotaDetalheFiltrada() {
		return listaNotaDetalheFiltrada;
	}
	public void setListaNotaDetalheFiltrada(
			ArrayList<NotaDetalhe> listaNotaDetalheFiltrada) {
		this.listaNotaDetalheFiltrada = listaNotaDetalheFiltrada;
	}



}
package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import model.NotaDetalhe;
import model.NotaFiscal;
import model.OpmeQtBipap;
import dao.DetalheNotaDAO;
import dao.NotaFiscalDAO;

@Named
@ViewScoped
public class RelatorioQtOralController implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<NotaFiscal> listaNotaFiscal = new ArrayList<NotaFiscal>();
	private ArrayList<NotaDetalhe> listaNotaDetalhe = new ArrayList<NotaDetalhe>();
	private List<OpmeQtBipap> listaopmeQtBipap= new ArrayList<OpmeQtBipap>();
	private String competencia;
	private String competenciaAlterada;
	private String dtinicio ;
	private String dtfinal ;
	
	@PostConstruct
	public void iniciar() {
		
	}
	public void carregaDados(){
		
		NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();
		listaNotaFiscal = notaFicalDAO.buscarNotaTipoIntervalo("QTORAL", dtinicio, dtfinal);
		
		listaNotaDetalhe = new ArrayList<NotaDetalhe>();
		
		if (listaNotaFiscal == null){
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Período Inválido!", ""));
			return;
		}
		
		
		
		for (NotaFiscal notaFiscalSelecionado : listaNotaFiscal){
			
			DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();	
			listaopmeQtBipap = detalheNotaDAO.buscarDetalhesNotaOpmeQtBipap(notaFiscalSelecionado.getId());
			
			for (OpmeQtBipap opmeQtBipap : listaopmeQtBipap){
				
				NotaDetalhe notaDetalhe = new NotaDetalhe();
				notaDetalhe.setNotaFiscal(notaFiscalSelecionado);
				notaDetalhe.setOpmeQtBipap(opmeQtBipap);
				notaDetalhe.setValorUnitario(opmeQtBipap.getValor()/opmeQtBipap.getQuantidade());
				
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public ArrayList<NotaDetalhe> getListaNotaDetalhe() {
		return listaNotaDetalhe;
	}
	public void setListaNotaDetalhe(ArrayList<NotaDetalhe> listaNotaDetalhe) {
		this.listaNotaDetalhe = listaNotaDetalhe;
	}
	public List<OpmeQtBipap> getListaopmeQtBipap() {
		return listaopmeQtBipap;
	}
	public void setListaopmeQtBipap(List<OpmeQtBipap> listaopmeQtBipap) {
		this.listaopmeQtBipap = listaopmeQtBipap;
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

	
	
	
}
package controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Competencia;
import dao.CompetenciaDAO;

@Named
@ViewScoped
public class CompetenciasController implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<Competencia> listaCompetencias;
	private Competencia competenciaSelecionada;
	
	@Inject
	LoginController loginController;


	


	@PostConstruct
	public void iniciar() {	
		
		CompetenciaDAO competenciaDAO = new CompetenciaDAO();
		
		listaCompetencias = competenciaDAO.buscarCompetencias();	
		
		
		
		
		
	}
	
	public void travarNotas(Competencia competencia){
		
		CompetenciaDAO competenciaDAO = new CompetenciaDAO();
		
		if (competencia.getTravado() == 0) competencia.setTravado(1);
		else competencia.setTravado(0);
		
		
		
		competenciaDAO.travarDestravar(competencia,loginController.getColaborador().getMatricula().toString());
		
		
		
	}







	public ArrayList<Competencia> getListaCompetencias() {
		return listaCompetencias;
	}







	public void setListaCompetencias(ArrayList<Competencia> listaCompetencias) {
		this.listaCompetencias = listaCompetencias;
	}







	public static long getSerialversionuid() {
		return serialVersionUID;
	}







	public Competencia getCompetenciaSelecionada() {
		return competenciaSelecionada;
	}







	public void setCompetenciaSelecionada(Competencia competenciaSelecionada) {
		this.competenciaSelecionada = competenciaSelecionada;
	}
	

		
	







}
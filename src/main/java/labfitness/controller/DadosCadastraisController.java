package labfitness.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import labfitness.DAO.AlunoDAO;
import labfitness.DAO.AnamneseQuestionamentoDAO;
import labfitness.model.Aluno;
import labfitness.model.AnamneseQuestionamento;
import labfitness.services.GeralService;
import controller.LoginController;

@ManagedBean(name = "dadosCadastraisController")
@ViewScoped
public class DadosCadastraisController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	LoginController loginController;

	GeralService geralService = new GeralService();

	AlunoDAO alunoDAO = new AlunoDAO();
	AnamneseQuestionamentoDAO anamneseQuestionamentoDAO = new AnamneseQuestionamentoDAO();

	Aluno alunoSelecionado;

	List<AnamneseQuestionamento> listaAnamneseQuestionamento;
	List<AnamneseQuestionamento> listaAnamneseQuestionamentoUm;
	List<AnamneseQuestionamento> listaAnamneseQuestionamentoDois;
	List<AnamneseQuestionamento> listaAnamneseQuestionamentoTres;
	List<AnamneseQuestionamento> listaAnamneseQuestionamentoQuatro;
	List<AnamneseQuestionamento> listaAnamneseQuestionamentoCinco;

	@PostConstruct
	public void iniciar() {

		alunoSelecionado = alunoDAO.BuscarAlunosPorId(1);
		listaAnamneseQuestionamento = anamneseQuestionamentoDAO
				.BuscarAnamnesePorAlunoId(1);
		
		
		
		listaAnamneseQuestionamentoUm = geralService.BucarItensListaTipo(1,listaAnamneseQuestionamento);
		listaAnamneseQuestionamentoDois = geralService.BucarItensListaTipo(2,listaAnamneseQuestionamento);
		listaAnamneseQuestionamentoTres = geralService.BucarItensListaTipo(3,listaAnamneseQuestionamento);
		listaAnamneseQuestionamentoQuatro = geralService.BucarItensListaTipo(4,listaAnamneseQuestionamento);
		listaAnamneseQuestionamentoCinco = geralService.BucarItensListaTipo(5,listaAnamneseQuestionamento);
		
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Cell Changed", "Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void onRowEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Car Edited");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public Aluno getAlunoSelecionado() {
		return alunoSelecionado;
	}

	public void setAlunoSelecionado(Aluno alunoSelecionado) {
		this.alunoSelecionado = alunoSelecionado;
	}

	public List<AnamneseQuestionamento> getListaAnamneseQuestionamento() {
		return listaAnamneseQuestionamento;
	}

	public void setListaAnamneseQuestionamento(
			List<AnamneseQuestionamento> listaAnamneseQuestionamento) {
		this.listaAnamneseQuestionamento = listaAnamneseQuestionamento;
	}

	public List<AnamneseQuestionamento> getListaAnamneseQuestionamentoUm() {
		return listaAnamneseQuestionamentoUm;
	}

	public void setListaAnamneseQuestionamentoUm(
			List<AnamneseQuestionamento> listaAnamneseQuestionamentoUm) {
		this.listaAnamneseQuestionamentoUm = listaAnamneseQuestionamentoUm;
	}

	public List<AnamneseQuestionamento> getListaAnamneseQuestionamentoDois() {
		return listaAnamneseQuestionamentoDois;
	}

	public void setListaAnamneseQuestionamentoDois(
			List<AnamneseQuestionamento> listaAnamneseQuestionamentoDois) {
		this.listaAnamneseQuestionamentoDois = listaAnamneseQuestionamentoDois;
	}

	public List<AnamneseQuestionamento> getListaAnamneseQuestionamentoTres() {
		return listaAnamneseQuestionamentoTres;
	}

	public void setListaAnamneseQuestionamentoTres(
			List<AnamneseQuestionamento> listaAnamneseQuestionamentoTres) {
		this.listaAnamneseQuestionamentoTres = listaAnamneseQuestionamentoTres;
	}

	public List<AnamneseQuestionamento> getListaAnamneseQuestionamentoQuatro() {
		return listaAnamneseQuestionamentoQuatro;
	}

	public void setListaAnamneseQuestionamentoQuatro(
			List<AnamneseQuestionamento> listaAnamneseQuestionamentoQuatro) {
		this.listaAnamneseQuestionamentoQuatro = listaAnamneseQuestionamentoQuatro;
	}

	public List<AnamneseQuestionamento> getListaAnamneseQuestionamentoCinco() {
		return listaAnamneseQuestionamentoCinco;
	}

	public void setListaAnamneseQuestionamentoCinco(
			List<AnamneseQuestionamento> listaAnamneseQuestionamentoCinco) {
		this.listaAnamneseQuestionamentoCinco = listaAnamneseQuestionamentoCinco;
	}
	
	

}

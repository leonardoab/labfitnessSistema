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
import controller.LoginController;

@ManagedBean(name="dadosCadastraisController")
@ViewScoped
public class DadosCadastraisController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	LoginController loginController;

	AlunoDAO alunoDAO = new AlunoDAO();
	AnamneseQuestionamentoDAO anamneseQuestionamentoDAO = new AnamneseQuestionamentoDAO();
	Aluno alunoSelecionado;
	List<AnamneseQuestionamento> listaAnamneseQuestionamento;

	public List<AnamneseQuestionamento> getListaAnamneseQuestionamento() {
		return listaAnamneseQuestionamento;
	}

	public void setListaAnamneseQuestionamento(
			List<AnamneseQuestionamento> listaAnamneseQuestionamento) {
		this.listaAnamneseQuestionamento = listaAnamneseQuestionamento;
	}

	@PostConstruct
	public void iniciar() {

		alunoSelecionado = alunoDAO.BuscarAlunosPorId(1);
		listaAnamneseQuestionamento = anamneseQuestionamentoDAO
				.BuscarAnamnesePorAlunoId(1);
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

}

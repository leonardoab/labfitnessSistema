package labfitness.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;







import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import labfitness.DAO.AlunoDAO;
import labfitness.DAO.AntropometriaUnidadeDAO;
import labfitness.model.Aluno;
import labfitness.model.AntropometriaUnidade;
import labfitness.services.GeralService;
import controller.LoginController;

@ManagedBean(name = "antropometriaController")
@ViewScoped
public class AntropometriaController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	LoginController loginController;

	GeralService geralService = new GeralService();

	AlunoDAO alunoDAO = new AlunoDAO();
	AntropometriaUnidadeDAO antropometriaUnidadeDAO = new AntropometriaUnidadeDAO();

	Aluno alunoSelecionado;

	List<AntropometriaUnidade> listaAntropometriaUnidade;
	

	@PostConstruct
	public void iniciar() {

		alunoSelecionado = alunoDAO.BuscarAlunosPorId(1);
		listaAntropometriaUnidade = antropometriaUnidadeDAO.BuscarAnamnesePorAlunoId(1);
		
		
		
		
		
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

	
	public List<AntropometriaUnidade> getListaAntropometriaUnidade() {
		return listaAntropometriaUnidade;
	}

	public void setListaAntropometriaUnidade(
			List<AntropometriaUnidade> listaAntropometriaUnidade) {
		this.listaAntropometriaUnidade = listaAntropometriaUnidade;
	}

	public String editAction(String a) {
		  return  a;
		}
	
	

}

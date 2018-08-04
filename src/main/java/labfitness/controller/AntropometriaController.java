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
	
	List<AntropometriaUnidade> listaAntropometriaGeral;
	List<AntropometriaUnidade> listaAntropometriaDobras;
	List<AntropometriaUnidade> listaAntropometriaCircunferencia;
	List<AntropometriaUnidade> listaAntropometriaComposicao;
	List<AntropometriaUnidade> listaAntropometriaPressao;
	List<AntropometriaUnidade> listaAntropometriaZona;
	List<AntropometriaUnidade> listaAntropometriaVo;
	

	@PostConstruct
	public void iniciar() {

		alunoSelecionado = alunoDAO.BuscarAlunosPorId(1);
		listaAntropometriaUnidade = antropometriaUnidadeDAO.BuscarAnamnesePorAlunoId(1);
		
		listaAntropometriaGeral = geralService.BucarMedicaoTipoMedicao(4,listaAntropometriaUnidade);
		listaAntropometriaDobras = geralService.BucarMedicaoTipoMedicao(1,listaAntropometriaUnidade);
		listaAntropometriaCircunferencia = geralService.BucarMedicaoTipoMedicao(2,listaAntropometriaUnidade);
		listaAntropometriaComposicao = geralService.BucarMedicaoTipoMedicao(3,listaAntropometriaUnidade);		
		listaAntropometriaPressao = geralService.BucarMedicaoTipoMedicao(7,listaAntropometriaUnidade);		
		listaAntropometriaZona = geralService.BucarMedicaoTipoMedicao(5,listaAntropometriaUnidade);		
		listaAntropometriaVo = geralService.BucarMedicaoTipoMedicao(6,listaAntropometriaUnidade);		
		
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

	public List<AntropometriaUnidade> getListaAntropometriaGeral() {
		return listaAntropometriaGeral;
	}

	public void setListaAntropometriaGeral(
			List<AntropometriaUnidade> listaAntropometriaGeral) {
		this.listaAntropometriaGeral = listaAntropometriaGeral;
	}

	public List<AntropometriaUnidade> getListaAntropometriaDobras() {
		return listaAntropometriaDobras;
	}

	public void setListaAntropometriaDobras(
			List<AntropometriaUnidade> listaAntropometriaDobras) {
		this.listaAntropometriaDobras = listaAntropometriaDobras;
	}

	public List<AntropometriaUnidade> getListaAntropometriaCircunferencia() {
		return listaAntropometriaCircunferencia;
	}

	public void setListaAntropometriaCircunferencia(
			List<AntropometriaUnidade> listaAntropometriaCircunferencia) {
		this.listaAntropometriaCircunferencia = listaAntropometriaCircunferencia;
	}

	public List<AntropometriaUnidade> getListaAntropometriaComposicao() {
		return listaAntropometriaComposicao;
	}

	public void setListaAntropometriaComposicao(
			List<AntropometriaUnidade> listaAntropometriaComposicao) {
		this.listaAntropometriaComposicao = listaAntropometriaComposicao;
	}

	public List<AntropometriaUnidade> getListaAntropometriaPressao() {
		return listaAntropometriaPressao;
	}

	public void setListaAntropometriaPressao(
			List<AntropometriaUnidade> listaAntropometriaPressao) {
		this.listaAntropometriaPressao = listaAntropometriaPressao;
	}

	public List<AntropometriaUnidade> getListaAntropometriaZona() {
		return listaAntropometriaZona;
	}

	public void setListaAntropometriaZona(
			List<AntropometriaUnidade> listaAntropometriaZona) {
		this.listaAntropometriaZona = listaAntropometriaZona;
	}

	public List<AntropometriaUnidade> getListaAntropometriaVo() {
		return listaAntropometriaVo;
	}

	public void setListaAntropometriaVo(
			List<AntropometriaUnidade> listaAntropometriaVo) {
		this.listaAntropometriaVo = listaAntropometriaVo;
	}
	
	

}

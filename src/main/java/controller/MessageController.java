/*ATUALIZAÇÕES*/
/*DATA = 15/05/2016   MÉTODO = completeText 					ANALISTA = Tliner Friaça Castro			*/
/*DATA = 			  MÉTODO = completeText 					ANALISTA = 								*/
/*DATA = 			  MÉTODO = completeText 					ANALISTA = 								*/
/*DATA = 			  MÉTODO = completeText 					ANALISTA = 								*/


package controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class MessageController {
	
//============================================================ METHODS =================================================================================//
	
	public void messageValidacao(String classe) {
		FacesContext context = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, classe + " Validada!!!", "");

		context.addMessage("destinoErro", msg);
	}
	
	public void messageExcluir() {
		FacesContext context = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, " Excluido com sucesso!!!", "");

		context.addMessage("destinoErro", msg);
	}
	
	public void messageGravar() {
		FacesContext context = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO," Salvo com sucesso!!!", "");

		context.addMessage("destinoErro", msg);
	}
	
	public void messageAtualizar() {
		FacesContext context = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, " Atualizado com sucesso!!!", "");

		context.addMessage("destinoErro", msg);
	}

	public void messageErroValidacao(String classe) {
		FacesContext context = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, classe  + " Não Validada!!!", "");

		context.addMessage("destinoAviso", msg);
	}
	
	public void messageGuiaCancelada() {
		FacesContext context = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Guia Cancelada!!!", " ");

		context.addMessage("destinoAviso", msg);
	}
	
	public void messageGuiaNegada() {
		FacesContext context = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Guia Negada!!!", " ");

		context.addMessage("destinoAviso", msg);
	}
	
	public void teste(){
		System.out.println("TESTE");
	}

}

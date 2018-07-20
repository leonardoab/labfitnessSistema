package controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class PerfilAcessoController implements Serializable{

	private static final long serialVersionUID = 1L;

	private String perfilTemplete;


	@Inject
	LoginController loginController;


	@PostConstruct
	public void iniciar() {

		if (1==1){
			perfilTemplete = "Templete.xhtml";
		}
		else if (loginController.getColaborador().getPerfilAcesso().equals("OPME")){
			perfilTemplete = "TempleteOPME.xhtml";
		}
		else if (loginController.getColaborador().getPerfilAcesso().equals("QTORAL")){
			perfilTemplete = "TempleteQT.xhtml";
		}
		else if (loginController.getColaborador().getPerfilAcesso().equals("BIPAP")){
			perfilTemplete = "TempleteBIPAP.xhtml";
		}
		else if (loginController.getColaborador().getPerfilAcesso().equals("BAIXAS")){
			perfilTemplete = "TempleteBaixas.xhtml";
		}
		else if (loginController.getColaborador().getPerfilAcesso().equals("CONTABILIDADE")){
			perfilTemplete = "TempleteContabilidade.xhtml";
		}
		else if (loginController.getColaborador().getPerfilAcesso().equals("DIU")){
			perfilTemplete = "TempleteDIU.xhtml";
		}
		else {
			perfilTemplete = "TempleteAutenticado.xhtml";
		}



	}


	public String getPerfilTemplete() {
		return perfilTemplete;
	}


	public void setPerfilTemplete(String perfilTemplete) {
		this.perfilTemplete = perfilTemplete;
	}


	public LoginController getLoginController() {
		return loginController;
	}


	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}









}
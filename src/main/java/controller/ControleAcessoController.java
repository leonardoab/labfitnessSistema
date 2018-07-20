package controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Colaborador;
import model.NotaFiscal;
import dao.ColaboradorDAOImpl;

@Named
@ViewScoped
public class ControleAcessoController implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<Colaborador> listaColaboradores;
	private Colaborador colaboradorSelecionada;
	private Colaborador novoColaborador = new Colaborador();
	private ColaboradorDAOImpl colaboradorDAO = new ColaboradorDAOImpl();

	@Inject
	LoginController loginController;

	@PostConstruct
	public void iniciar() {			

		listaColaboradores = colaboradorDAO.buscarColaboradoresPerfil();		

	}

	public void incluirAcesso(){

		boolean achou = false;
		for (Colaborador colaboradorSelecionado : listaColaboradores){			

			if (novoColaborador.getUsuarioRede().equals(colaboradorSelecionado.getUsuarioRede())){
				achou = true;
			}


		}		
		if (achou == true){		
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Colaborador já cadastrado!", ""));
			return;
		}

		listaColaboradores.add(novoColaborador);
		colaboradorDAO.inserirColaboradorPerfil(novoColaborador, loginController.getColaborador().getMatricula().toString());
		novoColaborador = new Colaborador();
		iniciar();

	}
	
	
	
	public void removeColaborador(){
		
		colaboradorDAO.excluirColaboradorPerfil(colaboradorSelecionada);
		iniciar();
		
	}


	public ArrayList<Colaborador> getListaColaboradores() {
		return listaColaboradores;
	}

	public void setListaColaboradores(ArrayList<Colaborador> listaColaboradores) {
		this.listaColaboradores = listaColaboradores;
	}

	public Colaborador getColaboradorSelecionada() {
		return colaboradorSelecionada;
	}

	public void setColaboradorSelecionada(Colaborador colaboradorSelecionada) {
		this.colaboradorSelecionada = colaboradorSelecionada;
	}

	public Colaborador getNovoColaborador() {
		return novoColaborador;
	}

	public void setNovoColaborador(Colaborador novoColaborador) {
		this.novoColaborador = novoColaborador;
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
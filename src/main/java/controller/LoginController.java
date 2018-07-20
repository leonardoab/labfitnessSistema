/*ATUALIZAÇÕES*/
/*DATA = 15/05/2016   MÉTODO = completeText 					ANALISTA = Tliner Friaça Castro			*/
/*DATA = 			  MÉTODO = completeText 					ANALISTA = 								*/
/*DATA = 			  MÉTODO = completeText 					ANALISTA = 								*/
/*DATA = 			  MÉTODO = completeText 					ANALISTA = 								*/


package controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Colaborador;
import dao.ColaboradorDAOImpl;


@Named
@SessionScoped
public class LoginController implements Serializable {

//========================================================= VARIABLES ==================================================================================//

	private static final long serialVersionUID = 1L;
	private String login;
	private String senha;
	private Colaborador colaborador = new Colaborador();
	
//======================================================================================================================================================//
//============================================================ METHODS =================================================================================//
	
	@Inject
	private ColaboradorDAOImpl colaboradorDAO = new ColaboradorDAOImpl();
	
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private HttpServletRequest request;
	
	@Inject
	private HttpServletResponse response;
	
	public void login() throws ServletException, IOException {
		
		if(validarLogin()==true){

		RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward(request, response);
		facesContext.responseComplete();}
	}	
	
	public boolean validarLogin() {
	

		Hashtable<String, String> authEnv = new Hashtable<String, String>(11);
		/*authEnv.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		authEnv.put(Context.PROVIDER_URL, "ldap://172.16.4.120:389");
		authEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
		authEnv.put(Context.SECURITY_PRINCIPAL, login + "@unimedjf.com.br");
		authEnv.put(Context.SECURITY_CREDENTIALS, senha);*/

		try {
			@SuppressWarnings("unused")
			DirContext authContext = new InitialDirContext(authEnv);
			Colaborador funcionario = new Colaborador();
			funcionario.setUsuarioRede("lbezerra");
			setColaborador(funcionario);

			// System.out.println("Autenticado!");

		} catch (AuthenticationException authEx) {
			System.out.println("Erro na autenticação! ");
			// authEx.getCause().printStackTrace();
			GerarConfirmacao();
			return false;

		} catch (NamingException namEx) {
			System.out.println("Problemas na conexão! ");
			// namEx.getCause().printStackTrace();
			GerarErroConexao();
			return false;
		}

		return true;

	}
	
	public void GerarConfirmacao() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
				"Usuário/Senha inválidos", "Mensagem de erro completa");
		context.addMessage("destinoErro", msg);
	}

	public void GerarErroConexao() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
				"Falha ao conectar com Active Directory",
				"Mensagem de erro completa");
		context.addMessage("destinoErro", msg);
	}
	
//======================================================================================================================================================//
//===================================================== GETTERS AND SETTERS ============================================================================//
		
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public FacesContext getFacesContext() {
		return facesContext;
	}
	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}






}

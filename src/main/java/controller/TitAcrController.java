package controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import dao.TitAcrDAO;
import model.TitAcr;

@Named
@ViewScoped
public class TitAcrController implements Serializable {

	private static final long serialVersionUID = -1811329021816886653L;
	private ArrayList<TitAcr> listaTitAcr = new ArrayList<TitAcr>();
	private Integer cdnCliente = null;
	
	public void preencherListaTitAcr(){
		
		listaTitAcr = TitAcrDAO.getInstance().buscarPorCliente(cdnCliente);
		
	}

	
	
	public Integer getCdnCliente() {
		return cdnCliente;
	}



	public void setCdnCliente(Integer cdnCliente) {
		this.cdnCliente = cdnCliente;
	}



	public ArrayList<TitAcr> getListaTitAcr() {
		return listaTitAcr;
	}
	public void setListaTitAcr(ArrayList<TitAcr> listaTitAcr) {
		this.listaTitAcr = listaTitAcr;
	}
	
}

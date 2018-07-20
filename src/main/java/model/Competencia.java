package model;

public class Competencia {

	private String descritivo;
	private Integer travado ;
	private Integer quatidadeNotas;
	private boolean travadoLogico ;


	public String getDescritivo() {
		return descritivo;
	}
	public void setDescritivo(String descritivo) {
		this.descritivo = descritivo;
	}
	public Integer getTravado() {
		return travado;
	}
	public void setTravado(Integer travado) {
		this.travado = travado;
	}


	/*public boolean getTravadoLogico() {
		if (travado == 0){

			return false;
		} 
		else 	return true;
	}

	public boolean SetTravadoLogico() {
		if (getTravadoLogico() == false){

			return false;
		} 
		else 	return true;
	}*/


	public Integer getQuatidadeNotas() {
		return quatidadeNotas;
	}
	public void setQuatidadeNotas(Integer quatidadeNotas) {
		this.quatidadeNotas = quatidadeNotas;
	}


	public boolean isTravadoLogico() {
		if (travado == 0){

			return false;
		} 
		else 	return true;
	}
	public void setTravadoLogico(boolean travadoLogico) {
		this.travadoLogico = travadoLogico;
	}










}

package model;

public class GuiaAutorizacao {
	
	private Integer ano;
	private Long numero;
	
	public static String completeToLeft(String value, char c, int size) {
		String result = value;
		while (result.length() < size) {
			result = c + result;
		}
		return result;
	}
	
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	
	public String getNumeroGuiaCompleto() {
		
		if (ano == null || ano == 0) return ""; 
		
		return (completeToLeft(ano.toString(),'0',4) + completeToLeft(numero.toString(),'0',8));
	}
	
	
	
	
	
	

}

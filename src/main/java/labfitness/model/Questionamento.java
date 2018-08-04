package labfitness.model;



public class Questionamento {
	
	private Integer id_questionamento;
	private TipoQuestionamento tipoQuestionamento;
	private String dsc_questionamento;
	private String tp_campo;
	private boolean check;
	private boolean inputext;
	
	public Integer getId_questionamento() {
		return id_questionamento;
	}
	public void setId_questionamento(Integer id_questionamento) {
		this.id_questionamento = id_questionamento;
	}
	public TipoQuestionamento getTipoQuestionamento() {
		return tipoQuestionamento;
	}
	public void setTipoQuestionamento(TipoQuestionamento tipoQuestionamento) {
		this.tipoQuestionamento = tipoQuestionamento;
	}
	public String getDsc_questionamento() {
		return dsc_questionamento;
	}
	public void setDsc_questionamento(String dsc_questionamento) {
		this.dsc_questionamento = dsc_questionamento;
	}
	public String getTp_campo() {
		return tp_campo;
	}
	public void setTp_campo(String tp_campo) {
		this.tp_campo = tp_campo;
	}
	public boolean isCheck() {
		if (tp_campo.equals("check")) return true;
		else return false;		
	}
	
	public boolean isInputext() {
		if (tp_campo.equals("inputText")) return true;
		else return false;	
	}
	
	public boolean isBotton() {
		if (tp_campo.equals("Botton")) return true;
		else return false;	
	}
	
	
	
	
}

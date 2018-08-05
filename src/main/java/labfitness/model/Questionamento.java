package labfitness.model;



public class Questionamento {
	
	private Integer id_questionamento;
	private TipoQuestionamento tipoQuestionamento;
	private String dsc_questionamento;
	private Integer id_tipo_campo;

	
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
	
	
	
	
	public Integer getId_tipo_campo() {
		return id_tipo_campo;
	}
	public void setId_tipo_campo(Integer id_tipo_campo) {
		this.id_tipo_campo = id_tipo_campo;
	}
	public boolean isCheck() {
		if (id_tipo_campo == 3) return true;
		else return false;		
	}
	
	public boolean isInputext() {
		if (id_tipo_campo == 1) return true;
		else return false;	
	}
	
	public boolean isBotton() {
		if (id_tipo_campo == 2) return true;
		else return false;	
	}
	
	
	
	
}

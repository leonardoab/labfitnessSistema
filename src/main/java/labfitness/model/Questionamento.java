package labfitness.model;



public class Questionamento {
	
	private Integer id_questionamento;
	private TipoQuestionamento tipoQuestionamento;
	private String dsc_questionamento;
	
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
}

package labfitness.model;

public class AnamneseQuestionamento {

	private Integer id_anamnese_questionamento;
	private Questionamento questionamento;
	private Aluno aluno;
	private String dsc_resposta;	
	
	public Integer getId_anamnese_questionamento() {
		return id_anamnese_questionamento;
	}
	public void setId_anamnese_questionamento(Integer id_anamnese_questionamento) {
		this.id_anamnese_questionamento = id_anamnese_questionamento;
	}
	public Questionamento getQuestionamento() {
		return questionamento;
	}
	public void setQuestionamento(Questionamento questionamento) {
		this.questionamento = questionamento;
	}
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public String getDsc_resposta() {
		return dsc_resposta;
	}
	public void setDsc_resposta(String dsc_resposta) {
		this.dsc_resposta = dsc_resposta;
	}
}

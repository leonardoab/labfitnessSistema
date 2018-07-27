package labfitness.model;

import java.util.Date;

public class Antropometria {

	private Integer id_antropometria;
	private Date dt_realizacao;
	private Aluno aluno;
	
	public Integer getId_antropometria() {
		return id_antropometria;
	}
	public void setId_antropometria(Integer id_antropometria) {
		this.id_antropometria = id_antropometria;
	}
	public Date getDt_realizacao() {
		return dt_realizacao;
	}
	public void setDt_realizacao(Date dt_realizacao) {
		this.dt_realizacao = dt_realizacao;
	}
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	
	
}

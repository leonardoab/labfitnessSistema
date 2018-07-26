package labfitness.model;

import java.util.Date;

public class Aluno {

	private Integer id_Aluno;
	private String dsc_Nome;
	private Date dt_cadastro;
	private Date dt_nascimento;
	private String dsc_email;
	private boolean flg_sexo;	
	
	public Integer getId_Aluno() {
		return id_Aluno;
	}
	public void setId_Aluno(Integer id_Aluno) {
		this.id_Aluno = id_Aluno;
	}
	public String getDsc_Nome() {
		return dsc_Nome;
	}
	public void setDsc_Nome(String dsc_Nome) {
		this.dsc_Nome = dsc_Nome;
	}
	public Date getDt_nascimento() {
		return dt_nascimento;
	}
	public void setDt_nascimento(Date dt_nascimento) {
		this.dt_nascimento = dt_nascimento;
	}
	public String getDsc_email() {
		return dsc_email;
	}
	public void setDsc_email(String dsc_email) {
		this.dsc_email = dsc_email;
	}
	public boolean isFlg_sexo() {
		return flg_sexo;
	}
	public void setFlg_sexo(boolean flg_sexo) {
		this.flg_sexo = flg_sexo;
	}
	public Date getDt_cadastro() {
		return dt_cadastro;
	}
	public void setDt_cadastro(Date dt_cadastro) {
		this.dt_cadastro = dt_cadastro;
	}
}

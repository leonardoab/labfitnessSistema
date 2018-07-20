/*ATUALIZAÇÕES*/
/*DATA = 15/05/2016   MÉTODO = completeText 					ANALISTA = Tliner Friaça Castro			*/
/*DATA = 			  MÉTODO = completeText 					ANALISTA = 								*/
/*DATA = 			  MÉTODO = completeText 					ANALISTA = 								*/
/*DATA = 			  MÉTODO = completeText 					ANALISTA = 								*/


package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SEUNI.V_SSF_COLABORADOR_LOTACAO")
public class Colaborador {

// ========================================================= VARIABLES ==============================================================================//
	private String usuarioRede;
	private Long matricula;
	private String nome;
	private String email;
	private String cargo;
	private String siglaOrgao;
	private String descricaoOrgao;
	private String nomeGestor;
	private String siglaOrgaoPai;
	private String descricaoOrgaoPai;
	private Long matriculaGestor;
	private String perfilAcesso;

// ==================================================================================================================================================//
// ===================================================== GETTERS AND SETTERS ========================================================================//

	@Column(name = "USUARIO_REDE")
	public String getUsuarioRede() {
		return usuarioRede;
	}

	public void setUsuarioRede(String usuarioRede) {
		this.usuarioRede = usuarioRede;
	}

	@Column(name = "MATRICULA")
	@Id
	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}

	@Column(name = "NOME")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "CARGO")
	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	@Column(name = "SIGLA_ORGAO")
	public String getSiglaOrgao() {
		return siglaOrgao;
	}

	public void setSiglaOrgao(String siglaOrgao) {
		this.siglaOrgao = siglaOrgao;
	}

	@Column(name = "DESCRICAO_ORGAO")
	public String getDescricaoOrgao() {
		return descricaoOrgao;
	}

	public void setDescricaoOrgao(String descricaoOrgao) {
		this.descricaoOrgao = descricaoOrgao;
	}

	@Column(name = "NOME_GESTOR")
	public String getNomeGestor() {
		return nomeGestor;
	}

	public void setNomeGestor(String nomeGestor) {
		this.nomeGestor = nomeGestor;
	}

	@Column(name = "SIGLA_ORGAO_PAI")
	public String getSiglaOrgaoPai() {
		return siglaOrgaoPai;
	}

	public void setSiglaOrgaoPai(String siglaOrgaoPai) {
		this.siglaOrgaoPai = siglaOrgaoPai;
	}

	@Column(name = "DESCRICAO_ORGAO_PAI")
	public String getDescricaoOrgaoPai() {
		return descricaoOrgaoPai;
	}

	public void setDescricaoOrgaoPai(String descricaoOrgaoPai) {
		this.descricaoOrgaoPai = descricaoOrgaoPai;
	}

	@Column(name = "MATRICULA_GESTOR_ORGAO_PAI")
	public Long getMatriculaGestor() {
		return matriculaGestor;
	}

	public void setMatriculaGestor(Long matriculaGestor) {
		this.matriculaGestor = matriculaGestor;
	}

	public String getPerfilAcesso() {
		return perfilAcesso;
	}

	public void setPerfilAcesso(String perfilAcesso) {
		this.perfilAcesso = perfilAcesso;
	}
	
	
	
// ==================================================================================================================================================//
}

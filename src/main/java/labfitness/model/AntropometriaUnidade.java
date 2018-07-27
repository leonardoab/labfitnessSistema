package labfitness.model;

public class AntropometriaUnidade {

	private Integer id_antropometria_unidade;
	private Antropometria antropometria;
	private Medicao medicao;
	private Float vl_medicao;
	
	public Integer getId_antropometria_unidade() {
		return id_antropometria_unidade;
	}
	public void setId_antropometria_unidade(Integer id_antropometria_unidade) {
		this.id_antropometria_unidade = id_antropometria_unidade;
	}
	public Antropometria getAntropometria() {
		return antropometria;
	}
	public void setAntropometria(Antropometria antropometria) {
		this.antropometria = antropometria;
	}
	public Medicao getMedicao() {
		return medicao;
	}
	public void setMedicao(Medicao medicao) {
		this.medicao = medicao;
	}
	public Float getVl_medicao() {
		return vl_medicao;
	}
	public void setVl_medicao(Float vl_medicao) {
		this.vl_medicao = vl_medicao;
	}	
	
	
}

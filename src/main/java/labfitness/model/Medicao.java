package labfitness.model;

import java.util.Date;

public class Medicao {

	private Integer id_medicao;
	private String dsc_medicao;
	private Unidade unidade;
	private TipoMedicao tipoMedicao;
	
	
	public Integer getId_medicao() {
		return id_medicao;
	}
	public void setId_medicao(Integer id_medicao) {
		this.id_medicao = id_medicao;
	}
	public String getDsc_medicao() {
		return dsc_medicao;
	}
	public void setDsc_medicao(String dsc_medicao) {
		this.dsc_medicao = dsc_medicao;
	}
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	public TipoMedicao getTipoMedicao() {
		return tipoMedicao;
	}
	public void setTipoMedicao(TipoMedicao tipoMedicao) {
		this.tipoMedicao = tipoMedicao;
	}
	
	
}

package labfitness.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import labfitness.model.Aluno;
import labfitness.model.AnamneseQuestionamento;
import labfitness.model.Antropometria;
import labfitness.model.AntropometriaUnidade;
import labfitness.model.Medicao;
import labfitness.model.Questionamento;
import labfitness.model.TipoMedicao;
import labfitness.model.TipoQuestionamento;
import labfitness.model.Unidade;
import labfitness.util.ConexaoMySql;

public class AntropometriaUnidadeDAO {

	public List<AntropometriaUnidade> BuscarAnamnesePorAlunoId(Integer id) {

		List<AntropometriaUnidade> listaAntropometriaUnidade = new ArrayList<AntropometriaUnidade>();

		String sql = "SELECT m.*,tm.*,u.*, "
				+ "(case when au.id_antropometria_unidade IS NULL then 0 else au.id_antropometria_unidade end) AS id_antropometria_unidade , "
				+ "(case when au.id_antropometria IS NULL then 0 else au.id_antropometria end) AS id_antropometria , "
				+ "(case when au.id_medicao IS NULL then 0 else au.id_medicao end) AS id_medicao , "
				+ "(case when au.vl_medicao IS NULL then '' else au.vl_medicao end) AS vl_medicao , "
				+ "(case when au.id_antropometria IS NULL then 0 else au.id_antropometria end) AS id_antropometria, "
				+ "(case when a.id_antropometria IS NULL then 0 else a.id_antropometria end) AS id_antropometria , "
				+ "(case when a.id_aluno IS NULL then 0 else a.id_aluno end) AS id_aluno , "
				+ "(case when a.dt_realizacao IS NULL then '1900-01-01' else a.dt_realizacao end) AS dt_realizacao  "
				+ "FROM basedados.labfitness.medicao m   "
				+ "left join basedados.labfitness.tipo_medicao tm on tm.id_tipo_medicao = m.id_tipo_medicao "
				+ "left join basedados.labfitness.unidade u on u.id_unidade = m.id_unidade "
				+ "left join basedados.labfitness.antropometria_unidade au on au.id_medicao = m.id_medicao "
				+ "left join basedados.labfitness.antropometria a on a.id_antropometria = au.id_antropometria "
				+ "where id_aluno is null or a.id_aluno = " + id.toString();

		Statement State;

		try {
			State = ConexaoMySql.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {

				AntropometriaUnidade antropometriaUnidade = new AntropometriaUnidade();
				Antropometria antropometria = new Antropometria();
				Medicao medicao = new Medicao();
				Unidade unidade = new Unidade();
				TipoMedicao tipoMedicao = new TipoMedicao();

				tipoMedicao.setDsc_tipo_medicao(rs
						.getString("dsc_tipo_medicao"));
				tipoMedicao.setId_tipo_medicao(rs.getInt("id_tipo_medicao"));

				unidade.setDsc_unidade(rs.getString("dsc_unidade"));
				unidade.setId_unidade(rs.getInt("id_unidade"));

				medicao.setDsc_medicao(rs.getString("dsc_medicao"));
				medicao.setId_medicao(rs.getInt("id_medicao"));
				medicao.setTipoMedicao(tipoMedicao);
				medicao.setUnidade(unidade);

				antropometria.setDt_realizacao(rs.getDate("dt_realizacao"));
				antropometria
						.setId_antropometria(rs.getInt("id_antropometria"));

				antropometriaUnidade.setAntropometria(antropometria);
				antropometriaUnidade.setId_antropometria_unidade(rs
						.getInt("id_antropometria_unidade"));
				antropometriaUnidade.setMedicao(medicao);
				antropometriaUnidade.setVl_medicao(rs.getFloat("vl_medicao"));

				

				listaAntropometriaUnidade.add(antropometriaUnidade);

			}
			State.close();
			return listaAntropometriaUnidade;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
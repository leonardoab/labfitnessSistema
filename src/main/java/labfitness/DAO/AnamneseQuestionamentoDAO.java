	package labfitness.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import labfitness.model.Aluno;
import labfitness.model.AnamneseQuestionamento;
import labfitness.model.Questionamento;
import labfitness.model.TipoQuestionamento;
import labfitness.util.ConexaoMySql;

public class AnamneseQuestionamentoDAO {

	public List<AnamneseQuestionamento> BuscarAnamnesePorAlunoId(Integer id) {

		List<AnamneseQuestionamento> listaAnamneseQuestionamento = new ArrayList<AnamneseQuestionamento>();

		

		String sql = "select  q.*, t.*, "
				+ "(case when id_anamnese_questionamento IS NULL then 0 else aq.id_anamnese_questionamento end) AS id_anamnese_questionamento, "
				+ "(case when id_anamnese_questionamento IS NULL then 0 else aq.id_questionamento end) AS id_questionamento, "
				+ "(case when id_anamnese_questionamento IS NULL then 0 else aq.id_aluno end) AS id_aluno, "
				+ "(case when id_anamnese_questionamento IS NULL then '' else aq.dsc_resposta end) AS dsc_resposta "
				+ "from questionamento q "
				+ "left join anamnese_questionamento aq on aq.id_questionamento = q.id_questionamento "
				+ "left join tipo_questionamento t on t.id_tipo_questionamento = q.id_tipo_questionamento "
				+ "where  aq.id_questionamento is null or aq.id_aluno = "
				+ id.toString();

		Statement State;

		try {
			State = ConexaoMySql.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {
				
				Questionamento questionamento = new Questionamento();
				TipoQuestionamento tipoQuestionamento = new TipoQuestionamento();
				AnamneseQuestionamento anamneseQuestionamento = new AnamneseQuestionamento();

				anamneseQuestionamento.setDsc_resposta(rs.getString(
						"dsc_resposta").toUpperCase());
				anamneseQuestionamento.setId_anamnese_questionamento(rs
						.getInt("id_anamnese_questionamento"));				

				tipoQuestionamento.setDsc_tipo_questionamento(rs.getString(
						"dsc_tipo_questionamento").toUpperCase());
				tipoQuestionamento.setId_tipo_questionamento(rs
						.getInt("id_tipo_questionamento"));

				questionamento.setDsc_questionamento(rs.getString(
						"dsc_questionamento").toUpperCase());
				questionamento.setId_questionamento(rs
						.getInt("id_questionamento"));
				
				questionamento.setTp_campo(rs.getString(
						"tp_campo"));

				questionamento.setTipoQuestionamento(tipoQuestionamento);
				

				anamneseQuestionamento.setQuestionamento(questionamento);
				

				listaAnamneseQuestionamento.add(anamneseQuestionamento);

			}
			State.close();
			return listaAnamneseQuestionamento;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
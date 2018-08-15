package labfitness.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import labfitness.model.Aluno;
import labfitness.model.AnamneseQuestionamento;
import labfitness.util.ConexaoMySql;

public class AlunoDAO {

	public Aluno BuscarAlunosPorId(Integer id) {

		Aluno aluno = new Aluno();

		String sql = "SELECT  * FROM basedados.labfitness.aluno where id_aluno = "
				+ id.toString();

		Statement State;

		try {
			State = ConexaoMySql.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {

				aluno.setId_Aluno(rs.getInt("id_aluno"));
				aluno.setDsc_Nome(rs.getString("dsc_nome").toUpperCase());
				aluno.setDt_cadastro(rs.getDate("dt_cadastro"));
				aluno.setDt_nascimento(rs.getDate("dt_nascimento"));
				aluno.setDsc_email(rs.getString("dsc_email").toUpperCase());

				if (rs.getBoolean("flg_sexo") == true)
					aluno.setDsc_Sexo("Masculino");
				else if (rs.getBoolean("flg_sexo") == false)
					aluno.setDsc_Sexo("Feminino");
				else
					aluno.setDsc_Sexo("");

			}
			State.close();
			return aluno;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	public List<Aluno> BuscarAlunosPorNome(String nome) {

		Aluno aluno = new Aluno();
		List<Aluno> listaAluno = new ArrayList<Aluno>();
		String sql = "SELECT  * FROM basedados.labfitness.aluno where dsc_nome like '%"
				+ nome + "%'";

		Statement State;

		try {
			State = ConexaoMySql.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {

				aluno.setId_Aluno(rs.getInt("id_aluno"));
				aluno.setDsc_Nome(rs.getString("dsc_nome").toUpperCase());
				aluno.setDt_cadastro(rs.getDate("dt_cadastro"));
				aluno.setDt_nascimento(rs.getDate("dt_nascimento"));
				aluno.setDsc_email(rs.getString("dsc_email").toUpperCase());

				if (rs.getBoolean("flg_sexo") == true)
					aluno.setDsc_Sexo("Masculino");
				else if (rs.getBoolean("flg_sexo") == false)
					aluno.setDsc_Sexo("Feminino");
				else
					aluno.setDsc_Sexo("");

				listaAluno.add(aluno);
			}
			State.close();
			return listaAluno;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

}
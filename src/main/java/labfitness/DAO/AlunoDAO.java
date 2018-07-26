package labfitness.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import labfitness.model.Aluno;
import labfitness.util.ConexaoMySql;



public class AlunoDAO {
	
	


	public Aluno BuscarAlunosPorId(Integer id){		
		
		Aluno aluno = new Aluno();

		String sql = "SELECT  * FROM aluno where id_aluno = " + id.toString();	
				
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
				aluno.setFlg_sexo(rs.getBoolean("flg_sexo"));				
			}
			State.close();
			return aluno;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}


}
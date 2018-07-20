package labfitness.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import labfitness.util.ConexaoMySql;


public class NotaFiscalDAO {


	public String buscarTeste(){			

		String sql = "SELECT  * FROM aluno "; 		
		String retorno = "";
				
		Statement State;
		
		try {
			State = ConexaoMySql.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {					
				retorno = rs.getString("id_aluno");						
			}
			State.close();
			return retorno;

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			return null;
		}
	}


}
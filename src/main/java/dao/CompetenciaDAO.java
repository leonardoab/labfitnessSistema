package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import labfitness.util.ConexaoMySql;
import model.Competencia;

public class CompetenciaDAO {

	public ArrayList<Competencia> buscarCompetencias() {			
		ArrayList<Competencia> listaCompetencia = new ArrayList<Competencia>();			

		String sql = "SELECT  * FROM SRUNI.SCO_COMPETENCIAS ";	
		Statement State;
		try {		

			State = ConexaoMySql.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {
				Competencia competencia = new Competencia();
				competencia.setDescritivo(rs.getString("COMPETENCIA_BAIXA"));
				competencia.setTravado(rs.getInt("SITUACAO"));
				listaCompetencia.add(competencia);	
			}
			State.close();
			return listaCompetencia;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}	


	public void travarDestravar(Competencia competencia,String usuario) {			
		String sql;				
		PreparedStatement state;

		try {	
			sql = "update SRUNI.SCO_COMPETENCIAS "
					+ "SET SITUACAO = ?,"
					+ "USUARIO_ATUALIZACAO = ?"
					+ "where COMPETENCIA_BAIXA = ?";

			state = ConexaoMySql.getConexao().prepareStatement(sql);
			state.setInt(1, competencia.getTravado());
			state.setString(2, usuario);
			state.setString(3, competencia.getDescritivo());
			state.execute();
			state.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}	




}

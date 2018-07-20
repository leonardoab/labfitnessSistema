/*ATUALIZAÇÕES*/
/*DATA = 15/05/2016   MÉTODO = completeText 					ANALISTA = Tliner Friaça Castro			*/
/*DATA = 			  MÉTODO = completeText 					ANALISTA = 								*/
/*DATA = 			  MÉTODO = completeText 					ANALISTA = 								*/
/*DATA = 			  MÉTODO = completeText 					ANALISTA = 								*/


package dao;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Colaborador;
import model.Competencia;
import util.ConexaoOracle;

public class ColaboradorDAOImpl  implements  Serializable{
	private static final long serialVersionUID = 1L;

	//============================================================ METHODS =================================================================================//

	public Colaborador buscarUsuarioRede(String usuarioRede) {

		String sql = "SELECT * FROM sv.v_rh3_supravizio V "
				+ "left join SRUNI.Sco_Acessos SA on Upper(Sa.Login_Rede) = Upper(V.Usuario_Rede)"
				+ " WHERE UPPER(USUARIO_REDE) = '"
				+ usuarioRede.toUpperCase() + "'"
				+ " order by 1 asc";

		Colaborador funcionario = new Colaborador();

		
		
		funcionario.setUsuarioRede("lbezerra");
		funcionario.setPerfilAcesso("TOTAL");
		
			return funcionario;

		 
		

	}

	private Colaborador getColaborador(ResultSet rs) throws SQLException {

		Colaborador colaborador = new Colaborador();
		colaborador.setUsuarioRede(rs.getString("USUARIO_REDE").toLowerCase());
		colaborador.setMatricula(rs.getLong("MATRICULA"));
		colaborador.setNome(rs.getString("NOME"));
		colaborador.setEmail(rs.getString("EMAIL"));
		colaborador.setCargo(rs.getString("CARGO"));
		colaborador.setSiglaOrgao(rs.getString("SIGLA_ORGAO"));
		colaborador.setDescricaoOrgao(rs.getString("DESCRICAO_ORGAO"));
		colaborador.setNomeGestor(rs.getString("NOME_GESTOR"));
		colaborador.setSiglaOrgaoPai(rs.getString("SIGLA_ORGAO_PAI"));
		colaborador.setDescricaoOrgaoPai(rs.getString("DESCRICAO_ORGAO_PAI"));
		colaborador.setMatriculaGestor(rs.getLong("MATRICULA_GESTOR_ORGAO_PAI"));
		colaborador.setPerfilAcesso(rs.getString("PERFIL"));
		return colaborador;
	}



	public ArrayList<Colaborador> buscarColaboradoresPerfil (){

		ArrayList<Colaborador> listaColaboradores = new ArrayList<Colaborador>();			

		String sql = "SELECT  * FROM SRUNI.SCO_ACESSOS order by 1";	
		Statement State;
		try {		

			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {
				Colaborador colaborador = new Colaborador();
				colaborador.setUsuarioRede(rs.getString("LOGIN_REDE"));
				colaborador.setPerfilAcesso(rs.getString("PERFIL"));
				listaColaboradores.add(colaborador);	
			}
			State.close();
			return listaColaboradores;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;


	}
	
	
	public void excluirColaboradorPerfil (Colaborador colaborador){

		String sql;				
		PreparedStatement state;
		
		
		sql = "DELETE FROM SRUNI.SCO_ACESSOS WHERE LOGIN_REDE = ?";	
		
		try {		

			state = ConexaoOracle.getConexao().prepareStatement(sql);
			state.setString(1, colaborador.getUsuarioRede());
			state.execute();
			state.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		


	}
	
	public void inserirColaboradorPerfil (Colaborador colaborador,String usuarioAtualizacao){

		String sql;				
		PreparedStatement state;
		
		
		sql = "Insert into SRUNI.SCO_ACESSOS ("
				+ "LOGIN_REDE,"
				+ "PERFIL,"
				+ "USUARIO_ATUALIZACAO)"	 
				+ "values (?,?,?)";	
		
		try {		

			state = ConexaoOracle.getConexao().prepareStatement(sql);
			state.setString(1, colaborador.getUsuarioRede().toLowerCase());
			state.setString(2, colaborador.getPerfilAcesso());
			state.setString(3, usuarioAtualizacao);
			state.execute();
			state.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		


	}

	//======================================================================================================================================================//

}

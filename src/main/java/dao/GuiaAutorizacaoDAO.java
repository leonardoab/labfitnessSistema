package dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import labfitness.util.ConexaoMySql;

public class GuiaAutorizacaoDAO implements Serializable{

	private static final long serialVersionUID = 1L;

	private static GuiaAutorizacaoDAO guiaAutorizacaoDAO;

	public static GuiaAutorizacaoDAO getInstance() {
		if (guiaAutorizacaoDAO == null) {
			guiaAutorizacaoDAO = new GuiaAutorizacaoDAO();
		}
		return guiaAutorizacaoDAO;
	}

	public List<String> buscarGuiaPorBeneficiarioDiu(String unidade,String carteira,Integer ano){			
		List<String> listaGuiasDisponiveis = new ArrayList<String>();				
		String sql = "SELECT distinct g.nr_guia_atendimento "
				+ "FROM SRCADGER.GUIAUTOR G "
				+ "left join SRCADGER.PROCGUIA P on P.CD_UNIDADE = G.CD_UNIDADE " 
				                 + "and P.AA_GUIA_ATENDIMENTO = G.AA_GUIA_ATENDIMENTO "
				                 + "and P.NR_GUIA_ATENDIMENTO = G.NR_GUIA_ATENDIMENTO "
				+ "where G.CD_UNIDADE_CARTEIRA = " +  unidade  
				+ " and G.CD_CARTEIRA_USUARIO = " +  carteira
				+ " and G.aa_guia_atendimento = " +  ano 
				/*+ " and G.in_liberado_guias <> 3 "*/				
				+ " and G.in_liberado_guias <> 8 "
				+ "and ( "
				    + "(P.cd_esp_amb = 31 and P.CD_GRUPO_PROC_AMB = 30 and P.cd_procedimento = 326  and P.dv_procedimento = 9) or "
				    + "(P.cd_esp_amb = 31 and P.CD_GRUPO_PROC_AMB = 30 and P.cd_procedimento = 329  and P.dv_procedimento = 3) or " 
				    + "(P.cd_esp_amb = 31 and P.CD_GRUPO_PROC_AMB = 30 and P.cd_procedimento = 360  and P.dv_procedimento = 9) "
				    + ") "
				+ "order by 1 desc";		
		Statement State;		
		try {
			State = ConexaoMySql.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {				
				listaGuiasDisponiveis.add(rs.getString("nr_guia_atendimento"));				
			}

			State.close();
			return listaGuiasDisponiveis;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<String> buscarGuiaPorBeneficiario(String unidade,String carteira,Integer ano){			
		List<String> listaGuiasDisponiveis = new ArrayList<String>();				
		String sql = "SELECT nr_guia_atendimento "
				+ "FROM SRCADGER.GUIAUTOR "
				+ "where CD_UNIDADE_CARTEIRA = " +	unidade 
				+ " and CD_CARTEIRA_USUARIO = " +  carteira 
				+ " and aa_guia_atendimento = " +  ano 
				/*+ " and in_liberado_guias <> 3"*/
				+ " and in_liberado_guias <> 8";
		Statement State;		
		try {
			State = ConexaoMySql.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {				
				listaGuiasDisponiveis.add(rs.getString("nr_guia_atendimento"));				
			}

			State.close();
			return listaGuiasDisponiveis;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public List<String> buscarAnoGuiaPorBeneficiario(String unidade,String carteira){		
		List<String> listaGuiasDisponiveis = new ArrayList<String>();		
		String sql = "SELECT DISTINCT aa_guia_atendimento "
				+ "FROM SRCADGER.GUIAUTOR "
				+ "where CD_UNIDADE_CARTEIRA = " +  unidade  
				+ " and CD_CARTEIRA_USUARIO = " +  carteira  
				/*+ " and in_liberado_guias <> 3 "*/
				+ " and in_liberado_guias <> 8 "
				+ "order by 1 desc";
		Statement State;	
		try {
			State = ConexaoMySql.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {			
				listaGuiasDisponiveis.add(rs.getString("aa_guia_atendimento"));			
			}

			State.close();
			return listaGuiasDisponiveis;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public List<String> buscarAnoGuiaPorBeneficiarioDiu(String unidade,String carteira){		
		List<String> listaGuiasDisponiveis = new ArrayList<String>();		
		String sql = "SELECT distinct g.aa_guia_atendimento "
				+ "FROM SRCADGER.GUIAUTOR G "
				+ "left join SRCADGER.PROCGUIA P on P.CD_UNIDADE = G.CD_UNIDADE " 
				                 + "and P.AA_GUIA_ATENDIMENTO = G.AA_GUIA_ATENDIMENTO "
				                 + "and P.NR_GUIA_ATENDIMENTO = G.NR_GUIA_ATENDIMENTO "
				+ "where G.CD_UNIDADE_CARTEIRA = " +  unidade  
				+ " and G.CD_CARTEIRA_USUARIO = " +  carteira  
				/*+ " and G.in_liberado_guias <> 3 "*/
				+ " and G.in_liberado_guias <> 8 "
				+ "and ( "
				    + "(P.cd_esp_amb = 31 and P.CD_GRUPO_PROC_AMB = 30 and P.cd_procedimento = 326  and P.dv_procedimento = 9) or "
				    + "(P.cd_esp_amb = 31 and P.CD_GRUPO_PROC_AMB = 30 and P.cd_procedimento = 329  and P.dv_procedimento = 3) or " 
				    + "(P.cd_esp_amb = 31 and P.CD_GRUPO_PROC_AMB = 30 and P.cd_procedimento = 360  and P.dv_procedimento = 9) "
				    + ") "
				+ "order by 1 desc";		
		
		Statement State;	
		try {
			State = ConexaoMySql.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {			
				listaGuiasDisponiveis.add(rs.getString("aa_guia_atendimento"));			
			}

			State.close();
			return listaGuiasDisponiveis;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



}

package dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Beneficiario;
import util.ConexaoOracle;

public class BeneficiarioDAO implements Serializable{

	private static final long serialVersionUID = 1L;

	private static BeneficiarioDAO beneficiarioDAO;

	public static BeneficiarioDAO getInstance() {
		if (beneficiarioDAO == null) {
			beneficiarioDAO = new BeneficiarioDAO();
		}
		return beneficiarioDAO;
	}

	public Beneficiario buscarPorBeneficiario(String carteira){	
		Beneficiario beneficiario = new Beneficiario();
		
		String sql = "select distinct U.NM_USUARIO from SRCADGER.CAR_IDE C" +   
						" left join SRCADGER.USUARIO U "
						+ "on U.CD_MODALIDADE = C.CD_MODALIDADE" +  
                        " and U.NR_TER_ADESAO  =  C.NR_TER_ADESAO" +
                        " and U.CD_USUARIO     = C.CD_USUARIO" +
                        " where C.CD_CARTEIRA_INTEIRA = '" + carteira + "'" ;
                        		
		Statement State;
		try {
			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {	
				beneficiario.setNome(rs.getString("NM_USUARIO").toUpperCase());
				beneficiario.setNomeUnimedUnidade(buscarPorNomeUnimed(49));
			}
			State.close();
			return beneficiario;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String buscarPorNomeUnimed(Integer unidade){	
		String nomeUnimed = null;
		
		String sql = "select NM_UNIMED from SRCADGER.unimed U where U.CD_UNIMED = " + unidade;
                        
		Statement State;
		try {
			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {	
				nomeUnimed = rs.getString("NM_UNIMED");
			}
			State.close();
			return nomeUnimed;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Beneficiario buscarPorBeneficiarioInter(String unidade,String carteira){
		Beneficiario beneficiario = new Beneficiario();
		String sql = "SELECT NM_USUARIO FROM SRCADGER.OUT_UNI where cd_unidade = " + unidade + " AND cd_carteira_usuario = " + carteira;
		Statement State;
		try {
			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {		
				beneficiario.setNome(rs.getString("NM_USUARIO").toUpperCase());
				beneficiario.setNomeUnimedUnidade(buscarPorNomeUnimed(Integer.valueOf(unidade)));
			}			
			State.close();
			return beneficiario;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Beneficiario> buscarNomeBeneficiarioObjeto(String codigo){	
		List<Beneficiario> listaBeneficiario = new ArrayList<Beneficiario>();	
		String sql = "SELECT DISTINCT USUARIO.CD_MODALIDADE,USUARIO.NR_TER_ADESAO,USUARIO.CD_USUARIO,USUARIO.NM_USUARIO,USUARIO.DT_EXCLUSAO_PLANO "
				+ "FROM SRCADGER.USUARIO "
				+ "where USUARIO.NM_USUARIO like '%" + codigo + "%'";	
		Statement State;
		try {
			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {
				Beneficiario beneficiario = new Beneficiario();
				beneficiario.setCdModalidade(rs.getInt("CD_MODALIDADE"));
				beneficiario.setNrTerAdesao(rs.getInt("NR_TER_ADESAO"));
				beneficiario.setCdUsuario(rs.getInt("CD_USUARIO"));
				beneficiario.setDtExclusao(rs.getDate("DT_EXCLUSAO_PLANO"));
				beneficiario.setNome(rs.getString("NM_USUARIO"));
				listaBeneficiario.add(beneficiario);	
			}
			State.close();
			return listaBeneficiario;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

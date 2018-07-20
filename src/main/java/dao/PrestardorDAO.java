package dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import labfitness.util.ConexaoMySql;
import model.Prestador;

public class PrestardorDAO implements Serializable {


	private static final long serialVersionUID = 1L;

	private static PrestardorDAO preservDAO;

	public static PrestardorDAO getInstance() {
		if (preservDAO == null) {
			preservDAO = new PrestardorDAO();
		}
		return preservDAO;
	}


	public Prestador buscarCodigoFornecedor(Integer codigo){			
		Prestador prestador = null;	
		String sql = "SELECT CD_UNIDADE,CD_PRESTADOR,NM_PRESTADOR,nr_cgc_cpf,CD_CONTRATANTE FROM SRCADGER.PRESERV where cd_grupo_prestador = 38 AND CD_UNIDADE = 49  and CD_PRESTADOR = " +  codigo  ;
		Statement State;		
		try {
			State = ConexaoMySql.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			
			if (codigo == 99998888){
				prestador = new Prestador();
				prestador.setUnidade(0);
				prestador.setCdPrestador(99998888);
				prestador.setNome("GENÉRICO OUTRAS UNIDADES");

			}
			
			while (rs.next()) {

				prestador = new Prestador();

				
					prestador.setUnidade(rs.getInt("CD_UNIDADE"));
					prestador.setCdPrestador(rs.getInt("CD_PRESTADOR"));
					prestador.setNome(rs.getString("NM_PRESTADOR"));	
					prestador.setCnpj(rs.getString("nr_cgc_cpf"));
					prestador.setCdContratante(rs.getInt("CD_CONTRATANTE"));
				

			}
			State.close();
			return prestador;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

	public Prestador buscarCodigoMedico(Integer codigo){			
		Prestador prestador = null;	
		String sql = "SELECT CD_UNIDADE,CD_PRESTADOR,NM_PRESTADOR,nr_cgc_cpf FROM SRCADGER.PRESERV where CD_UNIDADE = 49  and cd_grupo_prestador = 11 AND CD_PRESTADOR = " +  codigo  ;
		Statement State;		
		try {
			State = ConexaoMySql.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			
			if (codigo == 99998888){
				prestador = new Prestador();
				prestador.setUnidade(0);
				prestador.setCdPrestador(99998888);
				prestador.setNome("GENÉRICO OUTRAS UNIDADES");

			}
			
			while (rs.next()) {

				prestador = new Prestador();

				
				
					prestador.setUnidade(rs.getInt("CD_UNIDADE"));
					prestador.setCdPrestador(rs.getInt("CD_PRESTADOR"));
					prestador.setNome(rs.getString("NM_PRESTADOR"));
					prestador.setCnpj(rs.getString("nr_cgc_cpf"));
				
			}
			State.close();
			return prestador;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

	public Prestador buscarCodigoHospital(Integer codigo){		

		Prestador prestador = null;	

		String sql = "SELECT CD_UNIDADE,CD_PRESTADOR,NM_PRESTADOR,nr_cgc_cpf FROM SRCADGER.PRESERV where "
				+ "(cd_grupo_prestador = 22 or cd_grupo_prestador = 13 or cd_grupo_prestador = 15 or cd_grupo_prestador = 16 or cd_grupo_prestador = 17 or cd_grupo_prestador = 18 or cd_grupo_prestador = 19) "
				+ "AND CD_PRESTADOR = " +  codigo  ;
		Statement State;

		try {
			State = ConexaoMySql.getConexao().createStatement();

			ResultSet rs = State.executeQuery(sql);
			
			if (codigo == 99998888){
				prestador = new Prestador();
				prestador.setUnidade(0);
				prestador.setCdPrestador(99998888);
				prestador.setNome("GENÉRICO OUTRAS UNIDADES");

			}

			while (rs.next()) {				

				prestador = new Prestador();

				

					prestador.setUnidade(rs.getInt("CD_UNIDADE"));
					prestador.setCdPrestador(rs.getInt("CD_PRESTADOR"));
					prestador.setNome(rs.getString("NM_PRESTADOR"));		
					prestador.setCnpj(rs.getString("nr_cgc_cpf"));
				
			}

			State.close();
			return prestador;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;

		}

	}


	public List<Prestador> buscarNomeFornecedor(String codigo){			

		List<Prestador> listaPrestador = new ArrayList<Prestador>();		

		String sql = "SELECT * FROM SRCADGER.PRESERV where CD_UNIDADE = 49  and cd_grupo_prestador = 38 AND NM_PRESTADOR like '%" +  codigo + "%' order by NM_PRESTADOR " ;
		Statement State;

		try {
			State = ConexaoMySql.getConexao().createStatement();

			ResultSet rs = State.executeQuery(sql);

			while (rs.next()) {

				Prestador prestador = new Prestador();

				prestador.setUnidade(rs.getInt("CD_UNIDADE"));
				prestador.setCdPrestador(rs.getInt("CD_PRESTADOR"));
				prestador.setNome(rs.getString("NM_PRESTADOR"));
				prestador.setCnpj(rs.getString("nr_cgc_cpf"));

				listaPrestador.add(prestador);				

			}

			State.close();
			return listaPrestador;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	public List<Prestador> buscarNomeMedico(String codigo){			

		List<Prestador> listaPrestador = new ArrayList<Prestador>();		

		String sql = "SELECT * FROM SRCADGER.PRESERV where CD_UNIDADE = 49  and cd_grupo_prestador = 11 AND NM_PRESTADOR like '%" +  codigo + "%' order by NM_PRESTADOR " ;
		Statement State;

		try {
			State = ConexaoMySql.getConexao().createStatement();

			ResultSet rs = State.executeQuery(sql);

			while (rs.next()) {

				Prestador prestador = new Prestador();

				prestador.setUnidade(rs.getInt("CD_UNIDADE"));
				prestador.setCdPrestador(rs.getInt("CD_PRESTADOR"));
				prestador.setNome(rs.getString("NM_PRESTADOR"));
				prestador.setCnpj(rs.getString("nr_cgc_cpf"));

				listaPrestador.add(prestador);				

			}

			State.close();
			return listaPrestador;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	public List<Prestador> buscarNomeHospital(String codigo){			

		List<Prestador> listaPrestador = new ArrayList<Prestador>();		

		String sql = "SELECT * FROM SRCADGER.PRESERV where "
				+ "(cd_grupo_prestador = 22 or cd_grupo_prestador = 13 or cd_grupo_prestador = 15 or cd_grupo_prestador = 16 or cd_grupo_prestador = 17 or cd_grupo_prestador = 18 or cd_grupo_prestador = 19) "
				+ "AND NM_PRESTADOR like '%" +  codigo + "%' order by NM_PRESTADOR " ;
		Statement State;

		try {
			State = ConexaoMySql.getConexao().createStatement();

			ResultSet rs = State.executeQuery(sql);

			while (rs.next()) {

				Prestador prestador = new Prestador();

				prestador.setUnidade(rs.getInt("CD_UNIDADE"));
				prestador.setCdPrestador(rs.getInt("CD_PRESTADOR"));
				prestador.setNome(rs.getString("NM_PRESTADOR"));
				prestador.setCnpj(rs.getString("nr_cgc_cpf"));

				listaPrestador.add(prestador);				

			}

			State.close();
			return listaPrestador;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}



}

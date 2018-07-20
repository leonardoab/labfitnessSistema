package dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.OpmeQtBipap;
import util.ConexaoOracle;

public class InsumoDAO implements Serializable {


	private static final long serialVersionUID = 1L;

	private static InsumoDAO insumoDAO;

	public static InsumoDAO getInstance() {
		if (insumoDAO == null) {
			insumoDAO = new InsumoDAO();
		}
		return insumoDAO;
	}


	public List<OpmeQtBipap> buscarNomeInsumo(Integer tipoInsumo , String codigo){			

		List<OpmeQtBipap> listaInsumos = new ArrayList<OpmeQtBipap>();		

		
		
		String sql = "select I.CD_TIPO_INSUMO,I.CD_INSUMO,I.DS_INSUMO,I.cd_anvisa " + 
                     "from SRCADGER.INSUMOS I " +
                     "where I.CD_TIPO_INSUMO = " + tipoInsumo +
                     "and I.DS_INSUMO like '%" +  codigo + "%'";
		
		
		
		
		Statement State;

		try {
			State = ConexaoOracle.getConexao().createStatement();

			ResultSet rs = State.executeQuery(sql);

			while (rs.next()) {

				OpmeQtBipap opmeQtBipap = new OpmeQtBipap();

				opmeQtBipap.setTpInsumo(rs.getInt("CD_TIPO_INSUMO"));
				opmeQtBipap.setCdInsumo(rs.getInt("CD_INSUMO"));
				opmeQtBipap.setNomeInsumo(rs.getString("DS_INSUMO"));
				opmeQtBipap.setCdAnvisa(rs.getString("cd_anvisa"));
				
						

				listaInsumos.add(opmeQtBipap);				

			}

			State.close();
			return listaInsumos;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}
	
	public OpmeQtBipap buscarCodigoInsumo(OpmeQtBipap insumo){			
		
		
		String sql = "select I.DS_INSUMO,I.cd_anvisa " + 
                "from SRCADGER.INSUMOS I " +
                "where I.CD_TIPO_INSUMO = " + insumo.getTpInsumo() +
                " and I.CD_INSUMO = " +  insumo.getCdInsumo() ;
		
				
		Statement State;		
		try {
			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {
				
				
				insumo.setNomeInsumo(rs.getString("DS_INSUMO"));
				insumo.setCdAnvisa(rs.getString("cd_anvisa"));
				
				
			}
			State.close();
			return insumo;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
	
	



}

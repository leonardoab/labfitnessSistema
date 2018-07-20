package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import labfitness.util.ConexaoMySql;
import model.TitAcr;

public class TitAcrDAO {
	
	private static TitAcrDAO titAcrDAO;

	public static TitAcrDAO getInstance() {
		if (titAcrDAO == null) {
			titAcrDAO = new TitAcrDAO();
		}
		return titAcrDAO;
	}
	
	
	private TitAcr getTitAcr(ResultSet rs) throws SQLException {
		
		TitAcr titAcr = new TitAcr();		
		titAcr.setCdnCliente(rs.getInt("CDN_CLIENTE"));
		titAcr.setCodEspecDocto(rs.getString("COD_ESPEC_DOCTO"));
		titAcr.setCodEstab(rs.getString("COD_ESTAB"));
		titAcr.setCodParcela(rs.getString("COD_PARCELA"));		
		titAcr.setCodSerDocto(rs.getString("COD_SER_DOCTO"));
		titAcr.setCodTitAcr(rs.getString("COD_TIT_ACR"));
		
		return titAcr;
	}	
	
	// ==========================MÉTODOS==================================================================================================================//
		public ArrayList<TitAcr> buscarPorCliente(int cdnCliente) {
			
			String sql = "SELECT COD_ESTAB, COD_ESPEC_DOCTO, COD_SER_DOCTO, COD_PARCELA, COD_TIT_ACR, CDN_CLIENTE FROM EMS5MOV.TIT_ACR T WHERE VAL_SDO_TIT_ACR <> 0 and CDN_CLIENTE =  "+ cdnCliente;
			
			ArrayList<TitAcr> TitAcrs = new ArrayList<TitAcr>();
			Statement State;
			try {
				State = ConexaoMySql.getConexao().createStatement();

				ResultSet rs;
				rs = State.executeQuery(sql);

				while (rs.next()) {
					TitAcrs.add(getTitAcr(rs));
				}
				State.close();
				return TitAcrs;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

}

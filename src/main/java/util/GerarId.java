package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GerarId {


	public static Long scoDadoCentralizadoDetSeq() {
		try {
			String sql = "SELECT SRUNI.sco_dado_centralizado_det_seq.nextVal AS PROXIMO FROM DUAL";
			long sequence = 0;

			Statement state = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = state.executeQuery(sql);

			while (rs.next()) {
				sequence = (rs.getLong("PROXIMO"));
			}

			state.close();
			return sequence;

		} catch (SQLException ex) {
			return (Long) null;
		}
	}
	
	public static Long scoDadoCentralizadoSeq() {
		try {
			String sql = "SELECT SRUNI.sco_dado_centralizado_seq.nextVal AS PROXIMO FROM DUAL";
			long sequence = 0;

			Statement state = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = state.executeQuery(sql);

			while (rs.next()) {
				sequence = (rs.getLong("PROXIMO"));
			}

			state.close();
			return sequence;

		} catch (SQLException ex) {
			return (Long) null;
		}
	}





}

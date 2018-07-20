package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexaoOracle {

	private static Connection vConPostGree;

	private ConexaoOracle() {
	}

	public static Connection getConexao() {
		try {
			if ((vConPostGree == null) || vConPostGree.isClosed()) {
				Class.forName("com.mysql.jdbc.Driver");
				vConPostGree = DriverManager.getConnection("jdbc:mysql://localhost/labfitness?user=root");
				// vConPostGree =
				// DriverManager.getConnection("jdbc:oracle:thin:@bdrac1-vip.unimedjf.com.br:1521:BDUNIJF1",
				// "SEUNI", "SEUNI");
				//vConPostGree = DriverManager.getConnection("jdbc:oracle:thin:@bdprot11.unimedjf.com.br:1521:BDUNIJF","APWEB", "APWEB_PROT");
				System.out.println("Conectou");

			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao conectar ao banco de dados.", "Erro",
					JOptionPane.ERROR_MESSAGE);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao conectar ao banco de dados.", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
		return vConPostGree;
	}

	public static void setConPostGree(Connection pConnection) {
		vConPostGree = pConnection;
	}
}

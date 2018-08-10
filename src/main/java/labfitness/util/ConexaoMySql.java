package labfitness.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexaoMySql {

	private static Connection vConPostGree;

	private ConexaoMySql() {
	}

	public static Connection getConexao() {
		try {
			if ((vConPostGree == null) || vConPostGree.isClosed()) {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				
				
				String hostName = "basedados.database.windows.net";
		        String dbName = "basedados";
		        String user = "lbezerra";
		        String password = "Fernando210738!!";
		        String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
		      
				
				vConPostGree = DriverManager.getConnection(url);
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

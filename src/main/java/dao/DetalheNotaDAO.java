package dao;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Diu;
import model.GuiaAutorizacao;
import model.OpmeQtBipap;
import model.Prestador;
import util.ConexaoOracle;
import util.GerarId;

public class DetalheNotaDAO implements Serializable{

	private static final long serialVersionUID = 1L;

	private static DetalheNotaDAO detalheNotaDAO;

	public static DetalheNotaDAO getInstance() {
		if (detalheNotaDAO == null) {
			detalheNotaDAO = new DetalheNotaDAO();
		}
		return detalheNotaDAO;
	}

	public void deletarPorId(Long id){

		String sql;		
		sql = "delete from SRUNI.SCO_DADO_CENTRALIZADO_DET where id = ?";

		PreparedStatement state;
		try {


			state = ConexaoOracle.getConexao().prepareStatement(sql);
			state.setLong(1, id);			
			state.execute();
			state.clearBatch();
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{


		}

	}

	public Diu RecuperarPorIdPrincipal(Long id){

		String sql;		
		sql = "select * from SRUNI.SCO_DADO_CENTRALIZADO_DET where id_principal = "+ id ;

		Statement State;
		Diu diu = null;

		try {
			BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);

			while (rs.next()) {
				diu = new Diu();
				diu.setId(rs.getLong("ID"));
				diu.setValor(rs.getFloat("VL_ITEM"));
				diu.setTipo(rs.getString("TP_REGISTRO"));
				diu.setDtLancamento(rs.getDate("DT_LANCAMENTO"));
				diu.setDtSolicitacao(rs.getDate("DT_SOLICITACAO"));
				diu.getBeneficiario().setUnidade(rs.getString("CD_UNIDADE"));
				diu.getBeneficiario().setCdCarteiraInteira(rs.getString("NR_CARTEIRA"));				
				GuiaAutorizacao guiaAutorizacao = new GuiaAutorizacao();
				diu.setGuiaAutorizacao(guiaAutorizacao);				
				diu.getGuiaAutorizacao().setAno(rs.getInt("ANO_GUIA_AUTORIZACAO"));
				diu.getGuiaAutorizacao().setNumero(rs.getLong("NR_GUIA_AUTORIZACAO"));				 
				Prestador medico = new Prestador();
				diu.setMedico(medico);				
				diu.getMedico().setCdPrestador(rs.getInt("CD_MEDICO"));			
				diu.getMedico().setUnidade(rs.getInt("CD_UNIDADE_MEDICO"));
				diu.setIdPrincipal(rs.getLong("ID_PRINCIPAL"));
				diu.setSituacao(rs.getString("SITUACAO"));
				diu.setSequencia(rs.getInt("SEQUENCIA"));
				diu.setObservacao(rs.getString("OBS"));
				diu.setStatus(rs.getString("STATUS"));
				diu.setCompetenciaBaixa(rs.getString("COMPETENCIA_BAIXA"));


				if (diu.getBeneficiario().getUnidade() != null && Integer.parseInt(diu.getBeneficiario().getUnidade()) == 49){

					diu.getBeneficiario().setNome(	(beneficiarioDAO.buscarPorBeneficiario(String.valueOf(diu.getBeneficiario().getCdCarteiraInteira())).getNome())	);
				}
				else {

					diu.getBeneficiario().setNome(	(beneficiarioDAO.buscarPorBeneficiarioInter(diu.getBeneficiario().getUnidade(),diu.getBeneficiario().getCdCarteiraInteira()).getNome())	);
					//beneficiarioDAO.buscarPorBeneficiarioInter(notaFiscal.getBeneficiario().getUnidade(),notaFiscal.getBeneficiario().getCdCarteiraInteira().toString())

				}


			}
			State.close();






		} catch (SQLException e) {
			return diu;
		}
		return diu;

	}





	public OpmeQtBipap persistirOpmeQtBipap(OpmeQtBipap opmeQtBipap,Long idPrincipal) {

		String sql;		
		sql = "Insert into SRUNI.SCO_DADO_CENTRALIZADO_DET ("

				+ "ID_PRINCIPAL,"
				+ "VL_ITEM,"
				+ "QT_ITENS,"
				+ "TP_INSUMO,"
				+ "CD_INSUMO,"
				+ "OBS,"
				+ "DT_INCLUSAO,"
				+ "ID) "
				+ "values (?,?,?,?,?,?,?,?)";

		PreparedStatement state;
		try {

			state = ConexaoOracle.getConexao().prepareStatement(sql);
			state.setLong(1, idPrincipal);
			state.setFloat(2, opmeQtBipap.getValor());						
			state.setInt(3,  opmeQtBipap.getQuantidade());
			state.setInt(4, opmeQtBipap.getTpInsumo());
			state.setInt(5, opmeQtBipap.getCdInsumo());
			state.setString(6, opmeQtBipap.getObservacao());

			Date hoje = new Date();

			state.setDate(7,  new java.sql.Date((hoje).getTime()));


			Long id = GerarId.scoDadoCentralizadoDetSeq();
			state.setLong(8, id );
			state.execute();
			opmeQtBipap.setId(id);
			state.close();
			return opmeQtBipap;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;	
		}

	}
	
	
	public OpmeQtBipap persistirAlteracaoOpmeQtBipap(OpmeQtBipap opmeQtBipap,Long idPrincipal) {

		String sql;		
		sql = "update SRUNI.SCO_DADO_CENTRALIZADO_DET "
				+ "SET ID_PRINCIPAL = ?,"
				+ "VL_ITEM = ?,"
				+ "QT_ITENS = ?,"
				+ "TP_INSUMO = ?,"
				+ "CD_INSUMO = ?,"
				+ "OBS = ?,"				
				+ "DT_ATUALIZACAO = ? "
				+ "where id = ?";
		
			

		PreparedStatement state;
		try {

			state = ConexaoOracle.getConexao().prepareStatement(sql);
			state.setLong(1, idPrincipal);
			state.setFloat(2, opmeQtBipap.getValor());						
			state.setInt(3,  opmeQtBipap.getQuantidade());
			state.setInt(4, opmeQtBipap.getTpInsumo());
			state.setInt(5, opmeQtBipap.getCdInsumo());
			state.setString(6, opmeQtBipap.getObservacao());
			
			

			Date hoje = new Date();

			state.setDate(7,  new java.sql.Date((hoje).getTime()));


			
			state.setLong(8, opmeQtBipap.getId() );
			state.execute();
			
			state.close();
			return opmeQtBipap;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;	
		}

	}

	public Diu persistirDiu(Diu diu,Long idPrincipal) {

		String sql;		

		if (diu.getId() == null){
			sql = "Insert into SRUNI.SCO_DADO_CENTRALIZADO_DET ("
					+ "ID_PRINCIPAL,"
					+ "VL_ITEM,"
					+ "TP_REGISTRO,"
					+ "SITUACAO,"
					+ "DT_SOLICITACAO, "
					+ "CD_UNIDADE ,"
					+ "NR_CARTEIRA ,"
					+ "ANO_GUIA_AUTORIZACAO ,"
					+ "NR_GUIA_AUTORIZACAO ,"
					+ "CD_MEDICO  ,"
					+ "CD_UNIDADE_MEDICO ,"
					+ "DT_LANCAMENTO,"
					+ "SEQUENCIA,"
					+ "OBS,"
					+ "STATUS ,"
					+ "COMPETENCIA_BAIXA ,"
					+ "DT_INCLUSAO,"
					+ "ID"
					+ ")"				
					+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		}
		else {

			sql = "update SRUNI.SCO_DADO_CENTRALIZADO_DET "
					+ "SET ID_PRINCIPAL = ?,"
					+ "VL_ITEM = ?,"
					+ "TP_REGISTRO = ?,"
					+ "SITUACAO = ?,"
					+ "DT_SOLICITACAO = ?,"
					+ "CD_UNIDADE = ?,"
					+ "NR_CARTEIRA = ?,"
					+ "ANO_GUIA_AUTORIZACAO = ?,"
					+ "NR_GUIA_AUTORIZACAO = ?,"
					+ "CD_MEDICO = ?,"
					+ "CD_UNIDADE_MEDICO = ?,"
					+ "DT_LANCAMENTO = ?,"
					+ "SEQUENCIA = ?,"
					+ "OBS = ?,"
					+ "STATUS = ?,"
					+ "COMPETENCIA_BAIXA = ?,"
					+ "DT_ATUALIZACAO = ?"
					+ "where id = ?";			

		}

		PreparedStatement state;
		try {

			state = ConexaoOracle.getConexao().prepareStatement(sql);
			state.setLong(1, idPrincipal);
			state.setDouble(2, diu.getValor());						
			state.setString(3,  diu.getTipo());
			state.setString(4, diu.getSituacao());


			if (diu.getDtSolicitacao() == null){

				state.setDate(5,null  );

			}
			else{

				state.setDate(5,  new java.sql.Date(diu.getDtSolicitacao().getTime()));

			}

			if (diu.getBeneficiario().getUnidade() == null){
				state.setString(6,"0");
				state.setString(7, "0");
			}
			else{
				state.setString(6, diu.getBeneficiario().getUnidade());
				state.setString(7, diu.getBeneficiario().getCdCarteiraInteira());

				BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();

				if (diu.getBeneficiario().getUnidade().equals("0049")){

					diu.getBeneficiario().setNome(	(beneficiarioDAO.buscarPorBeneficiario(String.valueOf(diu.getBeneficiario().getCdCarteiraInteira())).getNome())	);
				}
				else {

					diu.getBeneficiario().setNome(	(beneficiarioDAO.buscarPorBeneficiarioInter(diu.getBeneficiario().getUnidade(),diu.getBeneficiario().getCdCarteiraInteira()).getNome())	);
					//beneficiarioDAO.buscarPorBeneficiarioInter(notaFiscal.getBeneficiario().getUnidade(),notaFiscal.getBeneficiario().getCdCarteiraInteira().toString())

				}


			}

			if (diu.getGuiaAutorizacao().getAno() == null){

				state.setInt(8,0);
				state.setLong(9, 0);

			}
			else{
				state.setInt(8, diu.getGuiaAutorizacao().getAno());
				state.setLong(9, diu.getGuiaAutorizacao().getNumero());


			}
			if (diu.getMedico().getCdPrestador() == null){

				state.setInt(10,0);
				state.setLong(11, 0);

			}

			else {
				state.setInt(10, diu.getMedico().getCdPrestador());			
				state.setInt(11, diu.getMedico().getUnidade());	


			}

			if (diu.getDtLancamento() == null){

				state.setDate(12,null  );

			}
			else{

				state.setDate(12,  new java.sql.Date(diu.getDtLancamento().getTime()));

			}

			state.setInt(13, diu.getSequencia());
			state.setString(14, diu.getObservacao());
			
			state.setString(15, diu.getStatus());
			state.setString(16, diu.getCompetenciaBaixa());

			Date hoje = new Date();

			state.setDate(17,  new java.sql.Date((hoje).getTime()));

			if (diu.getId() == null){
				Long id = GerarId.scoDadoCentralizadoDetSeq();
				state.setLong(18, id );
				diu.setId(id);
			}
			else {

				state.setLong(18, diu.getId() );

			}

			state.execute();
			state.close();
			return diu;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;	
		}

	}

	public List<OpmeQtBipap> buscarDetalhesNotaOpmeQtBipap(Long codigo){	
		List<OpmeQtBipap> listaOpmeQtBipap = new ArrayList<OpmeQtBipap>();	
		String sql = "SELECT  * FROM SRUNI.SCO_DADO_CENTRALIZADO_DET " + "where SCO_DADO_CENTRALIZADO_DET.ID_PRINCIPAL = "+ codigo + " order by 8";	
		Statement State;
		try {
			InsumoDAO insumoDAO = new InsumoDAO();
			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {
				OpmeQtBipap opmeQtBipap = new OpmeQtBipap();
				opmeQtBipap.setId(rs.getLong("ID"));
				opmeQtBipap.setValor(rs.getFloat("VL_ITEM"));
				opmeQtBipap.setQuantidade(rs.getInt("QT_ITENS"));
				opmeQtBipap.setTpInsumo(rs.getInt("TP_INSUMO"));
				opmeQtBipap.setCdInsumo(rs.getInt("CD_INSUMO"));
				opmeQtBipap.setObservacao(rs.getString("OBS"));
				

				insumoDAO.buscarCodigoInsumo(opmeQtBipap);

				listaOpmeQtBipap.add(opmeQtBipap);	
			}
			State.close();
			return listaOpmeQtBipap;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}



	}


	public List<Diu> buscarDetalhesNotaDiu(Long codigo){	
		List<Diu> listaDiu = new ArrayList<Diu>();	
		String sql = "SELECT  * FROM SRUNI.SCO_DADO_CENTRALIZADO_DET " + "where SCO_DADO_CENTRALIZADO_DET.ID_PRINCIPAL = "+ codigo +" and SITUACAO = 'A' order by sequencia";	
		Statement State;
		try {
			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {
				Diu diu = new Diu();
				diu.setId(rs.getLong("ID"));
				diu.setValor(rs.getFloat("VL_ITEM"));
				diu.setTipo(rs.getString("TP_REGISTRO"));
				diu.setDtLancamento(rs.getDate("DT_LANCAMENTO"));
				diu.setDtSolicitacao(rs.getDate("DT_SOLICITACAO"));
				diu.getBeneficiario().setUnidade(rs.getString("CD_UNIDADE"));
				diu.getBeneficiario().setCdCarteiraInteira(rs.getString("NR_CARTEIRA"));				
				GuiaAutorizacao guiaAutorizacao = new GuiaAutorizacao();
				diu.setGuiaAutorizacao(guiaAutorizacao);				
				diu.getGuiaAutorizacao().setAno(rs.getInt("ANO_GUIA_AUTORIZACAO"));
				diu.getGuiaAutorizacao().setNumero(rs.getLong("NR_GUIA_AUTORIZACAO"));				 
				Prestador medico = new Prestador();
				diu.setMedico(medico);				
				diu.getMedico().setCdPrestador(rs.getInt("CD_MEDICO"));			
				diu.getMedico().setUnidade(rs.getInt("CD_UNIDADE_MEDICO"));
				diu.setSequencia(rs.getInt("SEQUENCIA"));
				diu.setObservacao(rs.getString("OBS"));
				diu.setStatus(rs.getString("STATUS"));
				diu.setCompetenciaBaixa(rs.getString("COMPETENCIA_BAIXA"));

				if(diu.getMedico().getCdPrestador() == 0){				
					diu.setRetirado(true);
				}

				else {
					diu.setRetirado(false);	
					PrestardorDAO preservDAO = new PrestardorDAO();
					diu.setMedico(preservDAO.buscarCodigoMedico(diu.getMedico().getCdPrestador()));
				}

				BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();

				if (diu.getBeneficiario().getUnidade() != null && Integer.parseInt(diu.getBeneficiario().getUnidade()) == 49){

					diu.getBeneficiario().setNome(	(beneficiarioDAO.buscarPorBeneficiario(String.valueOf(diu.getBeneficiario().getCdCarteiraInteira())).getNome())	);
				}
				else {

					diu.getBeneficiario().setNome(	(beneficiarioDAO.buscarPorBeneficiarioInter(diu.getBeneficiario().getUnidade(),diu.getBeneficiario().getCdCarteiraInteira()).getNome())	);
					//beneficiarioDAO.buscarPorBeneficiarioInter(notaFiscal.getBeneficiario().getUnidade(),notaFiscal.getBeneficiario().getCdCarteiraInteira().toString())

				}

				listaDiu.add(diu);	
			}
			State.close();
			return listaDiu;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}



}

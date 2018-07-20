package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import model.Beneficiario;
import model.NotaFiscal;
import model.Prestador;
import util.ConexaoOracle;
import util.GerarId;

public class NotaFiscalDAO {

	public NotaFiscal persistir(NotaFiscal notaFiscal)  {			
		String sql;				
		PreparedStatement state;


		try {			
			if (notaFiscal.getId() == null){				
				sql = "Insert into SRUNI.SCO_DADO_CENTRALIZADO ("
						+ "CD_FORNECEDOR,"
						+ "CD_UNIDADE_FORNECEDOR,"
						+ "NR_NOTA_FISCAL,"
						+ "DT_EMISSAO,"
						+ "TP_REGISTRO,"
						+ "VL_NOTA_FISCAL,"
						+ "QT_ITENS_NOTA_FISCAL,"
						+ "NR_PEDIDO,"
						+ "CD_UNIDADE,"
						+ "NR_CARTEIRA,"
						+ "ANO_GUIA_AUTORIZACAO,"
						+ "NR_GUIA_AUTORIZACAO,"
						+ "CD_HOSPITAL,"
						+ "CD_UNIDADE_HOSPITAL,"
						+ "DT_EXECUCAO ,"
						+ "DT_VENCIMENTO ,"
						+ "CD_MEDICO  ,"
						+ "CD_UNIDADE_MEDICO ,"
						+ "OBS,"
						+ "FRETE,"
						+ "IPI,"
						+ "DESCONTO,"
						+ "DT_INCLUSAO,"
						+ "DT_TRANSACAO,"
						+ "STATUS,"
						+ "SENHA_AUTORIZACAO,"
						+ "COMPETENCIA_BAIXA,"
						+ "USUARIO_INCLUSAO,"
						+ "ID) "
						+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";				
			}
			else {

				sql = "update SRUNI.SCO_DADO_CENTRALIZADO "
						+ "SET CD_FORNECEDOR = ?,"
						+ "CD_UNIDADE_FORNECEDOR = ?,"
						+ "NR_NOTA_FISCAL = ?,"
						+ "DT_EMISSAO = ?,"
						+ "TP_REGISTRO = ?,"
						+ "VL_NOTA_FISCAL = ?,"
						+ "QT_ITENS_NOTA_FISCAL = ?,"
						+ "NR_PEDIDO = ?,"
						+ "CD_UNIDADE = ?,"
						+ "NR_CARTEIRA = ?,"
						+ "ANO_GUIA_AUTORIZACAO = ?,"
						+ "NR_GUIA_AUTORIZACAO = ?,"
						+ "CD_HOSPITAL = ?,"
						+ "CD_UNIDADE_HOSPITAL = ?,"
						+ "DT_EXECUCAO = ?,"
						+ "DT_VENCIMENTO = ?,"
						+ "CD_MEDICO  = ?,"
						+ "CD_UNIDADE_MEDICO = ?,"
						+ "OBS = ?,"
						+ "FRETE = ?,"
						+ "IPI = ?,"
						+ "DESCONTO = ?,"
						+ "DT_ATUALIZACAO = ? ,"
						+ "DT_TRANSACAO = ? ,"
						+ "STATUS = ? ,"
						+ "SENHA_AUTORIZACAO = ?,"
						+ "COMPETENCIA_BAIXA = ? ,"
						+ "USUARIO_ATUALIZACAO = ? "
						+ "where id = ?";			

			}

			if (notaFiscal.getTipoRegistro().equals("DIU")){

				notaFiscal.getBeneficiario().setUnidade("0");
				notaFiscal.getBeneficiario().setCdCarteiraInteira( "0");
				notaFiscal.getGuiaAutorizacao().setAno(0);
				notaFiscal.getGuiaAutorizacao().setNumero((long) 0 );
				Prestador prestador = new Prestador();
				notaFiscal.setHospital(prestador);
				notaFiscal.getHospital().setCdPrestador(0);
				notaFiscal.getHospital().setUnidade(0);
				notaFiscal.setMedico(prestador);
				notaFiscal.getMedico().setCdPrestador(0);
				notaFiscal.getMedico().setUnidade(0);

			}else if (notaFiscal.getTipoRegistro().equals("BIPAP")  || notaFiscal.getTipoRegistro().equals("QTORAL") ){

				Prestador prestador = new Prestador();
				notaFiscal.setMedico(prestador);
				notaFiscal.getMedico().setCdPrestador(0);
				notaFiscal.getMedico().setUnidade(0);

				if (notaFiscal.getTipoRegistro().equals("QTORAL"))	notaFiscal.setHospital(prestador);

			}

			state = ConexaoOracle.getConexao().prepareStatement(sql);
			state.setInt(1, notaFiscal.getFornecedor().getCdPrestador());
			state.setInt(2, notaFiscal.getFornecedor().getUnidade());						
			state.setString(3, notaFiscal.getNumero());
			state.setDate(4,  new java.sql.Date(notaFiscal.getDataEmissao().getTime()));
			state.setString(5, notaFiscal.getTipoRegistro());
			state.setFloat(6, notaFiscal.getValor());
			state.setInt(7, notaFiscal.getQtdItens());
			state.setString(8, notaFiscal.getNrPedido());
			state.setString(9, notaFiscal.getBeneficiario().getUnidade());
			state.setString(10, notaFiscal.getBeneficiario().getCdCarteiraInteira());
			state.setInt(11, notaFiscal.getGuiaAutorizacao().getAno());
			state.setLong(12, notaFiscal.getGuiaAutorizacao().getNumero());
			state.setInt(13, notaFiscal.getHospital().getCdPrestador());			
			state.setInt(14, notaFiscal.getHospital().getUnidade());

			if (notaFiscal.getTipoRegistro().equals("OPME")){

				state.setDate(15,  new java.sql.Date(notaFiscal.getDtRealizacao().getTime()));

			}
			else
			{

				state.setDate(15,  new java.sql.Date(notaFiscal.getDataEmissao().getTime()));

			}




			state.setDate(16,  new java.sql.Date(notaFiscal.getDataVencimento().getTime()));
			state.setInt(17, notaFiscal.getMedico().getCdPrestador());			
			state.setInt(18, notaFiscal.getMedico().getUnidade());
			state.setString(19, notaFiscal.getObs());
			state.setFloat(20, notaFiscal.getFrete());
			state.setFloat(21, notaFiscal.getIpi());
			state.setFloat(22, notaFiscal.getDesconto());

			java.util.Date date = new java.util.Date();

			state.setTimestamp(23, new java.sql.Timestamp(date.getTime()));

			String string = "January 1, 1900";
			DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

			Date dataSelecionado = null;
			try {
				dataSelecionado = format.parse(string);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (notaFiscal.getDataTransacaoString() == null || notaFiscal.getDataTransacaoString().equals("") ){
				buscarDataTransacao(notaFiscal);
			}

			if (notaFiscal.getDataTransacao() == null){				

				notaFiscal.setDataTransacao(dataSelecionado);
			}

			state.setDate(24,  new java.sql.Date(notaFiscal.getDataTransacao().getTime()));

			state.setString(25, notaFiscal.getStatus());
			state.setString(26, notaFiscal.getSenhaAutorizacao());
			state.setString(27, notaFiscal.getCompetenciaBaixa());




			if (notaFiscal.getId() != null) {
				state.setString(28, notaFiscal.getUsuarioAtualizacao());
				state.setLong(29, notaFiscal.getId());
				notaFiscal.setDtAtualizacao(date);

			}
			else {
				Long id = GerarId.scoDadoCentralizadoSeq();
				notaFiscal.setId(id);
				state.setString(28, notaFiscal.getUsuarioInsercao());
				state.setLong(29, id);
				notaFiscal.setDtInsercao(date);
			}


			state.execute();
			state.close();
			return notaFiscal;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}	

	public ArrayList<NotaFiscal> buscarNotaTipo(String codigo){	
		ArrayList<NotaFiscal> listaNotaFiscal = new ArrayList<NotaFiscal>();			

		String sql = "SELECT  * FROM SRUNI.SCO_DADO_CENTRALIZADO " + "where SCO_DADO_CENTRALIZADO.TP_REGISTRO = '"+ codigo +"'";	
		Statement State;
		try {		

			BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
			PrestardorDAO preservDAO = new PrestardorDAO();

			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {
				NotaFiscal notaFiscal = new NotaFiscal();
				notaFiscal.getFornecedor().setCdPrestador(rs.getInt("CD_FORNECEDOR"));				
				notaFiscal.getFornecedor().setUnidade(rs.getInt("CD_UNIDADE_FORNECEDOR"));						
				notaFiscal.setNumero(rs.getString("NR_NOTA_FISCAL"));
				notaFiscal.setDataEmissao(rs.getTimestamp("DT_EMISSAO"));
				notaFiscal.setTipoRegistro(rs.getString("TP_REGISTRO"));
				notaFiscal.setValor(rs.getFloat("VL_NOTA_FISCAL"));
				notaFiscal.setQtdItens(rs.getInt("QT_ITENS_NOTA_FISCAL"));
				notaFiscal.setNrPedido(rs.getString("NR_PEDIDO"));
				notaFiscal.getBeneficiario().setUnidade(rs.getString("CD_UNIDADE"));
				notaFiscal.getBeneficiario().setCdCarteiraInteira(rs.getString("NR_CARTEIRA"));
				notaFiscal.getGuiaAutorizacao().setAno(rs.getInt("ANO_GUIA_AUTORIZACAO"));
				notaFiscal.getGuiaAutorizacao().setNumero(rs.getLong("NR_GUIA_AUTORIZACAO"));
				notaFiscal.getHospital().setCdPrestador(rs.getInt("CD_HOSPITAL"));			
				notaFiscal.getHospital().setUnidade(rs.getInt("CD_UNIDADE_HOSPITAL"));
				notaFiscal.setDtRealizacao(rs.getTimestamp("DT_EXECUCAO"));
				notaFiscal.setDataVencimento(rs.getTimestamp("DT_VENCIMENTO"));
				notaFiscal.getMedico().setCdPrestador(rs.getInt("CD_MEDICO"));			
				notaFiscal.getMedico().setUnidade(rs.getInt("CD_UNIDADE_MEDICO"));				
				notaFiscal.setUsuarioAtualizacao(rs.getString("USUARIO_ATUALIZACAO"));
				notaFiscal.setUsuarioInsercao(rs.getString("USUARIO_INCLUSAO"));
				notaFiscal.setFrete(rs.getInt("FRETE"));
				notaFiscal.setIpi(rs.getInt("IPI"));
				notaFiscal.setDesconto(rs.getInt("DESCONTO"));
				notaFiscal.setStatus(rs.getString("STATUS"));
				notaFiscal.setSenhaAutorizacao(rs.getString("SENHA_AUTORIZACAO"));				
				notaFiscal.setDtAtualizacao(rs.getTimestamp("DT_ATUALIZACAO"));
				notaFiscal.setDtInsercao(rs.getTimestamp("DT_INCLUSAO"));
				notaFiscal.setDataTransacao(rs.getTimestamp("DT_TRANSACAO"));
				notaFiscal.setObs(rs.getString("OBS"));	
				notaFiscal.setId(rs.getLong("ID"));
				notaFiscal.setCompetenciaBaixa(rs.getString("COMPETENCIA_BAIXA"));



				notaFiscal.setFornecedor(preservDAO.buscarCodigoFornecedor(notaFiscal.getFornecedor().getCdPrestador())); 
				notaFiscal.setHospital(preservDAO.buscarCodigoHospital(notaFiscal.getHospital().getCdPrestador()));
				notaFiscal.setMedico(preservDAO.buscarCodigoMedico(notaFiscal.getMedico().getCdPrestador()));

				if (notaFiscal.getMedico() == null){

					Prestador medicoNovo = new Prestador();
					medicoNovo.setCdPrestador(0);
					medicoNovo.setUnidade(0);
					notaFiscal.setMedico(medicoNovo);


				}


				if (!notaFiscal.getTipoRegistro().equals("DIU")){

					if (notaFiscal.getBeneficiario().getUnidade().equals("0049")){

						notaFiscal.getBeneficiario().setNome(	(beneficiarioDAO.buscarPorBeneficiario(String.valueOf(notaFiscal.getBeneficiario().getCdCarteiraInteira())).getNome())	);
					}
					else {

						notaFiscal.getBeneficiario().setNome(	(beneficiarioDAO.buscarPorBeneficiarioInter(notaFiscal.getBeneficiario().getUnidade(),notaFiscal.getBeneficiario().getCdCarteiraInteira()).getNome())	);
						//beneficiarioDAO.buscarPorBeneficiarioInter(notaFiscal.getBeneficiario().getUnidade(),notaFiscal.getBeneficiario().getCdCarteiraInteira().toString())
					}
				}

				listaNotaFiscal.add(notaFiscal);	
			}
			State.close();
			return listaNotaFiscal;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<NotaFiscal> buscarNotaTipoStatus(String codigo,String status){	
		ArrayList<NotaFiscal> listaNotaFiscal = new ArrayList<NotaFiscal>();			

		String sql = "SELECT  * FROM SRUNI.SCO_DADO_CENTRALIZADO " 
				+ "where SCO_DADO_CENTRALIZADO.TP_REGISTRO = '"+ codigo +"'"
				+ " AND STATUS = '" + status + "'";	
		Statement State;
		try {			

			BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
			PrestardorDAO preservDAO = new PrestardorDAO();

			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {
				NotaFiscal notaFiscal = new NotaFiscal();
				notaFiscal.getFornecedor().setCdPrestador(rs.getInt("CD_FORNECEDOR"));				
				notaFiscal.getFornecedor().setUnidade(rs.getInt("CD_UNIDADE_FORNECEDOR"));						
				notaFiscal.setNumero(rs.getString("NR_NOTA_FISCAL"));
				notaFiscal.setDataEmissao(rs.getTimestamp("DT_EMISSAO"));
				notaFiscal.setTipoRegistro(rs.getString("TP_REGISTRO"));
				notaFiscal.setValor(rs.getFloat("VL_NOTA_FISCAL"));
				notaFiscal.setQtdItens(rs.getInt("QT_ITENS_NOTA_FISCAL"));
				notaFiscal.setNrPedido(rs.getString("NR_PEDIDO"));
				notaFiscal.getBeneficiario().setUnidade(rs.getString("CD_UNIDADE"));
				notaFiscal.getBeneficiario().setCdCarteiraInteira(rs.getString("NR_CARTEIRA"));
				notaFiscal.getGuiaAutorizacao().setAno(rs.getInt("ANO_GUIA_AUTORIZACAO"));
				notaFiscal.getGuiaAutorizacao().setNumero(rs.getLong("NR_GUIA_AUTORIZACAO"));
				notaFiscal.getHospital().setCdPrestador(rs.getInt("CD_HOSPITAL"));			
				notaFiscal.getHospital().setUnidade(rs.getInt("CD_UNIDADE_HOSPITAL"));
				notaFiscal.setDtRealizacao(rs.getTimestamp("DT_EXECUCAO"));
				notaFiscal.setDataVencimento(rs.getTimestamp("DT_VENCIMENTO"));
				notaFiscal.getMedico().setCdPrestador(rs.getInt("CD_MEDICO"));			
				notaFiscal.getMedico().setUnidade(rs.getInt("CD_UNIDADE_MEDICO"));				
				notaFiscal.setUsuarioAtualizacao(rs.getString("USUARIO_ATUALIZACAO"));
				notaFiscal.setUsuarioInsercao(rs.getString("USUARIO_INCLUSAO"));
				notaFiscal.setFrete(rs.getInt("FRETE"));
				notaFiscal.setIpi(rs.getInt("IPI"));
				notaFiscal.setDesconto(rs.getInt("DESCONTO"));
				notaFiscal.setStatus(rs.getString("STATUS"));
				notaFiscal.setSenhaAutorizacao(rs.getString("SENHA_AUTORIZACAO"));				
				notaFiscal.setDtAtualizacao(rs.getTimestamp("DT_ATUALIZACAO"));
				notaFiscal.setDtInsercao(rs.getTimestamp("DT_INCLUSAO"));
				notaFiscal.setDataTransacao(rs.getTimestamp("DT_TRANSACAO"));
				notaFiscal.setObs(rs.getString("OBS"));	
				notaFiscal.setId(rs.getLong("ID"));
				notaFiscal.setCompetenciaBaixa(rs.getString("COMPETENCIA_BAIXA"));


				notaFiscal.setFornecedor(preservDAO.buscarCodigoFornecedor(notaFiscal.getFornecedor().getCdPrestador())); 
				notaFiscal.setHospital(preservDAO.buscarCodigoHospital(notaFiscal.getHospital().getCdPrestador()));
				notaFiscal.setMedico(preservDAO.buscarCodigoMedico(notaFiscal.getMedico().getCdPrestador()));

				if (notaFiscal.getMedico() == null){

					Prestador medicoNovo = new Prestador();
					medicoNovo.setCdPrestador(0);
					medicoNovo.setUnidade(0);
					notaFiscal.setMedico(medicoNovo);


				}

				if (!notaFiscal.getTipoRegistro().equals("DIU")){

					if (notaFiscal.getBeneficiario().getUnidade().equals("0049")){

						notaFiscal.getBeneficiario().setNome(	(beneficiarioDAO.buscarPorBeneficiario(String.valueOf(notaFiscal.getBeneficiario().getCdCarteiraInteira())).getNome())	);
					}
					else {

						notaFiscal.getBeneficiario().setNome(	(beneficiarioDAO.buscarPorBeneficiarioInter(notaFiscal.getBeneficiario().getUnidade(),notaFiscal.getBeneficiario().getCdCarteiraInteira()).getNome())	);
						//beneficiarioDAO.buscarPorBeneficiarioInter(notaFiscal.getBeneficiario().getUnidade(),notaFiscal.getBeneficiario().getCdCarteiraInteira().toString())
					}
				}

				listaNotaFiscal.add(notaFiscal);	
			}
			State.close();
			return listaNotaFiscal;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<NotaFiscal> buscarNotaTipoIntervalo(String codigo,String dtInicio,String dtFinal){	
		ArrayList<NotaFiscal> listaNotaFiscal = new ArrayList<NotaFiscal>();			

		String sql = "SELECT  * FROM SRUNI.SCO_DADO_CENTRALIZADO " + 
				"where SCO_DADO_CENTRALIZADO.TP_REGISTRO = '" + codigo + "'" +
				" and DT_INCLUSAO BETWEEN TO_DATE ('" + dtInicio + "', 'dd/mm/yyyy')" +
				" AND TO_DATE ('" + dtFinal + "', 'dd/mm/yyyy')";		

		Statement State;
		try {			

			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {
				NotaFiscal notaFiscal = new NotaFiscal();
				notaFiscal.getFornecedor().setCdPrestador(rs.getInt("CD_FORNECEDOR"));				
				notaFiscal.getFornecedor().setUnidade(rs.getInt("CD_UNIDADE_FORNECEDOR"));						
				notaFiscal.setNumero(rs.getString("NR_NOTA_FISCAL"));
				notaFiscal.setDataEmissao(rs.getTimestamp("DT_EMISSAO"));
				notaFiscal.setTipoRegistro(rs.getString("TP_REGISTRO"));
				notaFiscal.setValor(rs.getFloat("VL_NOTA_FISCAL"));
				notaFiscal.setQtdItens(rs.getInt("QT_ITENS_NOTA_FISCAL"));
				notaFiscal.setNrPedido(rs.getString("NR_PEDIDO"));
				notaFiscal.getBeneficiario().setUnidade(rs.getString("CD_UNIDADE"));
				notaFiscal.getBeneficiario().setCdCarteiraInteira(rs.getString("NR_CARTEIRA"));
				notaFiscal.getGuiaAutorizacao().setAno(rs.getInt("ANO_GUIA_AUTORIZACAO"));
				notaFiscal.getGuiaAutorizacao().setNumero(rs.getLong("NR_GUIA_AUTORIZACAO"));
				notaFiscal.getHospital().setCdPrestador(rs.getInt("CD_HOSPITAL"));			
				notaFiscal.getHospital().setUnidade(rs.getInt("CD_UNIDADE_HOSPITAL"));
				notaFiscal.setDtRealizacao(rs.getTimestamp("DT_EXECUCAO"));
				notaFiscal.setDataVencimento(rs.getTimestamp("DT_VENCIMENTO"));
				notaFiscal.getMedico().setCdPrestador(rs.getInt("CD_MEDICO"));			
				notaFiscal.getMedico().setUnidade(rs.getInt("CD_UNIDADE_MEDICO"));
				notaFiscal.setStatus(rs.getString("STATUS"));
				notaFiscal.setUsuarioAtualizacao(rs.getString("USUARIO_ATUALIZACAO"));
				notaFiscal.setUsuarioInsercao(rs.getString("USUARIO_INCLUSAO"));
				notaFiscal.setCompetenciaBaixa(rs.getString("COMPETENCIA_BAIXA"));

				notaFiscal.setFrete(rs.getInt("FRETE"));
				notaFiscal.setIpi(rs.getInt("IPI"));
				notaFiscal.setDesconto(rs.getInt("DESCONTO"));

				notaFiscal.setDtAtualizacao(rs.getTimestamp("DT_ATUALIZACAO"));
				notaFiscal.setDtInsercao(rs.getTimestamp("DT_INCLUSAO"));
				notaFiscal.setDataTransacao(rs.getTimestamp("DT_TRANSACAO"));

				notaFiscal.setObs(rs.getString("OBS"));	
				notaFiscal.setId(rs.getLong("ID"));

				BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
				PrestardorDAO preservDAO = new PrestardorDAO();

				notaFiscal.setFornecedor(preservDAO.buscarCodigoFornecedor(notaFiscal.getFornecedor().getCdPrestador())); 
				notaFiscal.setHospital(preservDAO.buscarCodigoHospital(notaFiscal.getHospital().getCdPrestador()));
				notaFiscal.setMedico(preservDAO.buscarCodigoMedico(notaFiscal.getMedico().getCdPrestador()));

				if (notaFiscal.getMedico() == null){

					Prestador medicoNovo = new Prestador();
					medicoNovo.setCdPrestador(0);
					medicoNovo.setUnidade(0);
					notaFiscal.setMedico(medicoNovo);


				}


				if (!notaFiscal.getTipoRegistro().equals("DIU")){

					if (notaFiscal.getBeneficiario().getUnidade().equals("0049")){

						Beneficiario beneficiarioAuxiliar = beneficiarioDAO.buscarPorBeneficiario(notaFiscal.getBeneficiario().getCdCarteiraInteira());
						notaFiscal.getBeneficiario().setNome(	beneficiarioAuxiliar.getNome()	);
						notaFiscal.getBeneficiario().setNomeUnimedUnidade(beneficiarioAuxiliar.getNomeUnimedUnidade());

						//notaFiscal.getBeneficiario().setNome(	(beneficiarioDAO.buscarPorBeneficiario(String.valueOf(notaFiscal.getBeneficiario().getCdCarteiraInteira())).getNome())	);
					}
					else {
						Beneficiario beneficiarioAuxiliar = beneficiarioDAO.buscarPorBeneficiarioInter(notaFiscal.getBeneficiario().getUnidade(),notaFiscal.getBeneficiario().getCdCarteiraInteira());
						notaFiscal.getBeneficiario().setNome(	beneficiarioAuxiliar.getNome()	);
						notaFiscal.getBeneficiario().setNomeUnimedUnidade(beneficiarioAuxiliar.getNomeUnimedUnidade());
						//beneficiarioDAO.buscarPorBeneficiarioInter(notaFiscal.getBeneficiario().getUnidade(),notaFiscal.getBeneficiario().getCdCarteiraInteira().toString())
					}
				}

				listaNotaFiscal.add(notaFiscal);	
			}
			State.close();
			return listaNotaFiscal;

		} catch (SQLException e) {
			// TODO Auto-generated catch block

		}
		return null;
	}

	public void buscarDataTransacao(NotaFiscal notaFiscal){	

		notaFiscal.getNumero().replace('\'', ' ');

		String sql = "select t.dat_transacao from EMS5MOV.TIT_AP T " +
				"where t.cod_tit_ap = " + notaFiscal.getNumero() +
				" and upper(t.cod_espec_docto) = 'DP' " +
				" and t.cdn_fornecedor = " + notaFiscal.getFornecedor().getCdContratante() +
				" and T.Val_Origin_Tit_Ap = " + notaFiscal.getValor();




		Statement State;
		try {			

			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {		

				notaFiscal.setDataTransacao(rs.getTimestamp("dat_transacao"));

			}
			State.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<NotaFiscal> buscarTodasNotas(){	
		ArrayList<NotaFiscal> listaNotaFiscal = new ArrayList<NotaFiscal>();			

		String sql = "SELECT  * FROM SRUNI.SCO_DADO_CENTRALIZADO ";
		Statement State;
		try {			

			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {
				NotaFiscal notaFiscal = new NotaFiscal();
				notaFiscal.getFornecedor().setCdPrestador(rs.getInt("CD_FORNECEDOR"));				
				notaFiscal.getFornecedor().setUnidade(rs.getInt("CD_UNIDADE_FORNECEDOR"));						
				notaFiscal.setNumero(rs.getString("NR_NOTA_FISCAL"));
				notaFiscal.setDataEmissao(rs.getTimestamp("DT_EMISSAO"));
				notaFiscal.setTipoRegistro(rs.getString("TP_REGISTRO"));
				notaFiscal.setValor(rs.getFloat("VL_NOTA_FISCAL"));
				notaFiscal.setQtdItens(rs.getInt("QT_ITENS_NOTA_FISCAL"));
				notaFiscal.setNrPedido(rs.getString("NR_PEDIDO"));
				notaFiscal.getBeneficiario().setUnidade(rs.getString("CD_UNIDADE"));
				notaFiscal.getBeneficiario().setCdCarteiraInteira(rs.getString("NR_CARTEIRA"));
				notaFiscal.getGuiaAutorizacao().setAno(rs.getInt("ANO_GUIA_AUTORIZACAO"));
				notaFiscal.getGuiaAutorizacao().setNumero(rs.getLong("NR_GUIA_AUTORIZACAO"));
				notaFiscal.getHospital().setCdPrestador(rs.getInt("CD_HOSPITAL"));			
				notaFiscal.getHospital().setUnidade(rs.getInt("CD_UNIDADE_HOSPITAL"));
				notaFiscal.setDtRealizacao(rs.getTimestamp("DT_EXECUCAO"));
				notaFiscal.setDataVencimento(rs.getTimestamp("DT_VENCIMENTO"));
				notaFiscal.getMedico().setCdPrestador(rs.getInt("CD_MEDICO"));			
				notaFiscal.getMedico().setUnidade(rs.getInt("CD_UNIDADE_MEDICO"));
				notaFiscal.setStatus(rs.getString("STATUS"));
				notaFiscal.setCompetenciaBaixa(rs.getString("COMPETENCIA_BAIXA"));

				notaFiscal.setFrete(rs.getInt("FRETE"));
				notaFiscal.setIpi(rs.getInt("IPI"));
				notaFiscal.setDesconto(rs.getInt("DESCONTO"));

				notaFiscal.setDtAtualizacao(rs.getTimestamp("DT_ATUALIZACAO"));
				notaFiscal.setDtInsercao(rs.getTimestamp("DT_INCLUSAO"));
				notaFiscal.setDataTransacao(rs.getTimestamp("DT_TRANSACAO"));
				notaFiscal.setUsuarioAtualizacao(rs.getString("USUARIO_ATUALIZACAO"));
				notaFiscal.setUsuarioInsercao(rs.getString("USUARIO_INCLUSAO"));

				notaFiscal.setObs(rs.getString("OBS"));	
				notaFiscal.setId(rs.getLong("ID"));

				BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
				PrestardorDAO preservDAO = new PrestardorDAO();

				notaFiscal.setFornecedor(preservDAO.buscarCodigoFornecedor(notaFiscal.getFornecedor().getCdPrestador())); 
				notaFiscal.setHospital(preservDAO.buscarCodigoHospital(notaFiscal.getHospital().getCdPrestador()));
				notaFiscal.setMedico(preservDAO.buscarCodigoMedico(notaFiscal.getMedico().getCdPrestador()));

				if (notaFiscal.getMedico() == null){

					Prestador medicoNovo = new Prestador();
					medicoNovo.setCdPrestador(0);
					medicoNovo.setUnidade(0);
					notaFiscal.setMedico(medicoNovo);


				}


				if (!notaFiscal.getTipoRegistro().equals("DIU")){

					if (notaFiscal.getBeneficiario().getUnidade().equals("0049")){

						notaFiscal.getBeneficiario().setNome(	(beneficiarioDAO.buscarPorBeneficiario(String.valueOf(notaFiscal.getBeneficiario().getCdCarteiraInteira())).getNome())	);
					}
					else {

						notaFiscal.getBeneficiario().setNome(	(beneficiarioDAO.buscarPorBeneficiarioInter(notaFiscal.getBeneficiario().getUnidade(),notaFiscal.getBeneficiario().getCdCarteiraInteira()).getNome())	);
						//beneficiarioDAO.buscarPorBeneficiarioInter(notaFiscal.getBeneficiario().getUnidade(),notaFiscal.getBeneficiario().getCdCarteiraInteira().toString())
					}
				}

				listaNotaFiscal.add(notaFiscal);	
			}
			State.close();
			return listaNotaFiscal;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<NotaFiscal> buscarTodasNotasStatus(String status , String dtInicio,String dtFinal,boolean lgDiu){	
		ArrayList<NotaFiscal> listaNotaFiscal = new ArrayList<NotaFiscal>();			

		String sql;
		
		if (status.equals("Baixadas") && lgDiu)
			sql = "SELECT  * FROM SRUNI.SCO_DADO_CENTRALIZADO "
					+ "where (TP_REGISTRO = 'DIU' OR (STATUS <> 'Aberta' "
					+ "and STATUS <> 'Cancelada'))" + 
					" and (DT_TRANSACAO BETWEEN TO_DATE ('" + dtInicio + "', 'dd/mm/yyyy')" +
					" AND TO_DATE ('" + dtFinal + "', 'dd/mm/yyyy') or DT_TRANSACAO is null)";	
		
		else if (status.equals("Baixadas"))
			sql = "SELECT  * FROM SRUNI.SCO_DADO_CENTRALIZADO "
					+ "where STATUS <> 'Aberta' "
					+ "and STATUS <> 'Cancelada'" + 
					" and (DT_TRANSACAO BETWEEN TO_DATE ('" + dtInicio + "', 'dd/mm/yyyy')" +
					" AND TO_DATE ('" + dtFinal + "', 'dd/mm/yyyy') or DT_TRANSACAO is null)";		

		else if (status.equals("Todas"))
			sql = "SELECT  * FROM SRUNI.SCO_DADO_CENTRALIZADO " +
					"where (DT_TRANSACAO BETWEEN TO_DATE ('" + dtInicio + "', 'dd/mm/yyyy')" +
					" AND TO_DATE ('" + dtFinal + "', 'dd/mm/yyyy') or DT_TRANSACAO is null)";	


		else 
			sql = "SELECT  * FROM SRUNI.SCO_DADO_CENTRALIZADO "
					+ "where STATUS = '" + status + "'" +
					" and (DT_TRANSACAO BETWEEN TO_DATE ('" + dtInicio + "', 'dd/mm/yyyy')" +
					" AND TO_DATE ('" + dtFinal + "', 'dd/mm/yyyy') or DT_TRANSACAO is null)";		

		Statement State;
		try {			

			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {
				NotaFiscal notaFiscal = new NotaFiscal();
				notaFiscal.getFornecedor().setCdPrestador(rs.getInt("CD_FORNECEDOR"));				
				notaFiscal.getFornecedor().setUnidade(rs.getInt("CD_UNIDADE_FORNECEDOR"));						
				notaFiscal.setNumero(rs.getString("NR_NOTA_FISCAL"));
				notaFiscal.setDataEmissao(rs.getTimestamp("DT_EMISSAO"));
				notaFiscal.setTipoRegistro(rs.getString("TP_REGISTRO"));
				notaFiscal.setValor(rs.getFloat("VL_NOTA_FISCAL"));
				notaFiscal.setQtdItens(rs.getInt("QT_ITENS_NOTA_FISCAL"));
				notaFiscal.setNrPedido(rs.getString("NR_PEDIDO"));
				notaFiscal.getBeneficiario().setUnidade(rs.getString("CD_UNIDADE"));
				notaFiscal.getBeneficiario().setCdCarteiraInteira(rs.getString("NR_CARTEIRA"));
				notaFiscal.getGuiaAutorizacao().setAno(rs.getInt("ANO_GUIA_AUTORIZACAO"));
				notaFiscal.getGuiaAutorizacao().setNumero(rs.getLong("NR_GUIA_AUTORIZACAO"));
				notaFiscal.getHospital().setCdPrestador(rs.getInt("CD_HOSPITAL"));			
				notaFiscal.getHospital().setUnidade(rs.getInt("CD_UNIDADE_HOSPITAL"));
				notaFiscal.setDtRealizacao(rs.getTimestamp("DT_EXECUCAO"));
				notaFiscal.setDataVencimento(rs.getTimestamp("DT_VENCIMENTO"));
				notaFiscal.getMedico().setCdPrestador(rs.getInt("CD_MEDICO"));			
				notaFiscal.getMedico().setUnidade(rs.getInt("CD_UNIDADE_MEDICO"));
				notaFiscal.setStatus(rs.getString("STATUS"));
				notaFiscal.setCompetenciaBaixa(rs.getString("COMPETENCIA_BAIXA"));

				notaFiscal.setFrete(rs.getInt("FRETE"));
				notaFiscal.setIpi(rs.getInt("IPI"));
				notaFiscal.setDesconto(rs.getInt("DESCONTO"));

				notaFiscal.setDtAtualizacao(rs.getTimestamp("DT_ATUALIZACAO"));
				notaFiscal.setDtInsercao(rs.getTimestamp("DT_INCLUSAO"));
				notaFiscal.setDataTransacao(rs.getTimestamp("DT_TRANSACAO"));
				notaFiscal.setUsuarioAtualizacao(rs.getString("USUARIO_ATUALIZACAO"));
				notaFiscal.setUsuarioInsercao(rs.getString("USUARIO_INCLUSAO"));

				notaFiscal.setObs(rs.getString("OBS"));	
				notaFiscal.setId(rs.getLong("ID"));

				BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
				PrestardorDAO preservDAO = new PrestardorDAO();

				notaFiscal.setFornecedor(preservDAO.buscarCodigoFornecedor(notaFiscal.getFornecedor().getCdPrestador())); 
				notaFiscal.setHospital(preservDAO.buscarCodigoHospital(notaFiscal.getHospital().getCdPrestador()));
				notaFiscal.setMedico(preservDAO.buscarCodigoMedico(notaFiscal.getMedico().getCdPrestador()));

				if (notaFiscal.getMedico() == null){

					Prestador medicoNovo = new Prestador();
					medicoNovo.setCdPrestador(0);
					medicoNovo.setUnidade(0);
					notaFiscal.setMedico(medicoNovo);


				}


				if (!notaFiscal.getTipoRegistro().equals("DIU")){

					if (notaFiscal.getBeneficiario().getUnidade().equals("0049")){

						notaFiscal.getBeneficiario().setNome(	(beneficiarioDAO.buscarPorBeneficiario(String.valueOf(notaFiscal.getBeneficiario().getCdCarteiraInteira())).getNome())	);
					}
					else {

						notaFiscal.getBeneficiario().setNome(	(beneficiarioDAO.buscarPorBeneficiarioInter(notaFiscal.getBeneficiario().getUnidade(),notaFiscal.getBeneficiario().getCdCarteiraInteira()).getNome())	);
						//beneficiarioDAO.buscarPorBeneficiarioInter(notaFiscal.getBeneficiario().getUnidade(),notaFiscal.getBeneficiario().getCdCarteiraInteira().toString())
					}
				}

				listaNotaFiscal.add(notaFiscal);	
			}
			State.close();
			return listaNotaFiscal;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public NotaFiscal buscarCodigoFornecedorNrNota(Integer codigo,String nrNota){			

		String sql = "SELECT  * FROM SRUNI.SCO_DADO_CENTRALIZADO " 
				+ "where SCO_DADO_CENTRALIZADO.NR_NOTA_FISCAL = '"+ nrNota +"' " + "and SCO_DADO_CENTRALIZADO.CD_FORNECEDOR = " + codigo ;
		Statement State;
		NotaFiscal notaFiscal = null;
		try {
			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {	
				notaFiscal = new NotaFiscal();
				notaFiscal.getFornecedor().setCdPrestador(rs.getInt("CD_FORNECEDOR"));				
				notaFiscal.getFornecedor().setUnidade(rs.getInt("CD_UNIDADE_FORNECEDOR"));						
				notaFiscal.setNumero(rs.getString("NR_NOTA_FISCAL"));
				notaFiscal.setDataEmissao(rs.getDate("DT_EMISSAO"));
				notaFiscal.setTipoRegistro(rs.getString("TP_REGISTRO"));
				notaFiscal.setValor(rs.getFloat("VL_NOTA_FISCAL"));
				notaFiscal.setQtdItens(rs.getInt("QT_ITENS_NOTA_FISCAL"));
				notaFiscal.setNrPedido(rs.getString("NR_PEDIDO"));
				notaFiscal.getBeneficiario().setUnidade(rs.getString("CD_UNIDADE"));
				notaFiscal.getBeneficiario().setCdCarteiraInteira(rs.getString("NR_CARTEIRA"));
				notaFiscal.getGuiaAutorizacao().setAno(rs.getInt("ANO_GUIA_AUTORIZACAO"));
				notaFiscal.getGuiaAutorizacao().setNumero(rs.getLong("NR_GUIA_AUTORIZACAO"));
				notaFiscal.getHospital().setCdPrestador(rs.getInt("CD_HOSPITAL"));			
				notaFiscal.getHospital().setUnidade(rs.getInt("CD_UNIDADE_HOSPITAL"));		
				notaFiscal.setDataTransacao(rs.getTimestamp("DT_TRANSACAO"));
				notaFiscal.setFrete(rs.getInt("FRETE"));
				notaFiscal.setIpi(rs.getInt("IPI"));
				notaFiscal.setDesconto(rs.getInt("DESCONTO"));				
				notaFiscal.setObs(rs.getString("OBS"));	
				notaFiscal.setId(rs.getLong("ID"));
				notaFiscal.setStatus(rs.getString("STATUS"));
				notaFiscal.setUsuarioAtualizacao(rs.getString("USUARIO_ATUALIZACAO"));
				notaFiscal.setUsuarioInsercao(rs.getString("USUARIO_INCLUSAO"));
				notaFiscal.setCompetenciaBaixa(rs.getString("COMPETENCIA_BAIXA"));
			}
			State.close();
			return notaFiscal;

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			return null;
		}
	}
	
	public String buscarTeste(){			

		String sql = "SELECT  * FROM aluno "; 
		
		String retorno = "";
				
		Statement State;
		NotaFiscal notaFiscal = null;
		try {
			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {	
				notaFiscal = new NotaFiscal();
				retorno = rs.getString("id_aluno");		
				
			}
			State.close();
			return retorno;

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			return null;
		}
	}

	public ArrayList<NotaFiscal> buscarNotaIntervalo(String dtInicio,String dtFinal){	
		ArrayList<NotaFiscal> listaNotaFiscal = new ArrayList<NotaFiscal>();			

		String sql = "SELECT  * FROM SRUNI.SCO_DADO_CENTRALIZADO " + 
				"where DT_TRANSACAO BETWEEN TO_DATE ('" + dtInicio + "', 'dd/mm/yyyy')" +
				" AND TO_DATE ('" + dtFinal + "', 'dd/mm/yyyy')";		

		Statement State;
		try {			

			State = ConexaoOracle.getConexao().createStatement();
			ResultSet rs = State.executeQuery(sql);
			while (rs.next()) {
				NotaFiscal notaFiscal = new NotaFiscal();
				notaFiscal.getFornecedor().setCdPrestador(rs.getInt("CD_FORNECEDOR"));				
				notaFiscal.getFornecedor().setUnidade(rs.getInt("CD_UNIDADE_FORNECEDOR"));						
				notaFiscal.setNumero(rs.getString("NR_NOTA_FISCAL"));
				notaFiscal.setDataEmissao(rs.getTimestamp("DT_EMISSAO"));
				notaFiscal.setTipoRegistro(rs.getString("TP_REGISTRO"));
				notaFiscal.setValor(rs.getFloat("VL_NOTA_FISCAL"));
				notaFiscal.setQtdItens(rs.getInt("QT_ITENS_NOTA_FISCAL"));
				notaFiscal.setNrPedido(rs.getString("NR_PEDIDO"));
				notaFiscal.getBeneficiario().setUnidade(rs.getString("CD_UNIDADE"));
				notaFiscal.getBeneficiario().setCdCarteiraInteira(rs.getString("NR_CARTEIRA"));
				notaFiscal.getGuiaAutorizacao().setAno(rs.getInt("ANO_GUIA_AUTORIZACAO"));
				notaFiscal.getGuiaAutorizacao().setNumero(rs.getLong("NR_GUIA_AUTORIZACAO"));
				notaFiscal.getHospital().setCdPrestador(rs.getInt("CD_HOSPITAL"));			
				notaFiscal.getHospital().setUnidade(rs.getInt("CD_UNIDADE_HOSPITAL"));
				notaFiscal.setDtRealizacao(rs.getTimestamp("DT_EXECUCAO"));
				notaFiscal.setDataVencimento(rs.getTimestamp("DT_VENCIMENTO"));
				notaFiscal.getMedico().setCdPrestador(rs.getInt("CD_MEDICO"));			
				notaFiscal.getMedico().setUnidade(rs.getInt("CD_UNIDADE_MEDICO"));
				notaFiscal.setStatus(rs.getString("STATUS"));
				notaFiscal.setUsuarioAtualizacao(rs.getString("USUARIO_ATUALIZACAO"));
				notaFiscal.setUsuarioInsercao(rs.getString("USUARIO_INCLUSAO"));
				notaFiscal.setCompetenciaBaixa(rs.getString("COMPETENCIA_BAIXA"));

				notaFiscal.setFrete(rs.getInt("FRETE"));
				notaFiscal.setIpi(rs.getInt("IPI"));
				notaFiscal.setDesconto(rs.getInt("DESCONTO"));

				notaFiscal.setDtAtualizacao(rs.getTimestamp("DT_ATUALIZACAO"));
				notaFiscal.setDtInsercao(rs.getTimestamp("DT_INCLUSAO"));
				notaFiscal.setDataTransacao(rs.getTimestamp("DT_TRANSACAO"));

				notaFiscal.setObs(rs.getString("OBS"));	
				notaFiscal.setId(rs.getLong("ID"));

				BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
				PrestardorDAO preservDAO = new PrestardorDAO();

				notaFiscal.setFornecedor(preservDAO.buscarCodigoFornecedor(notaFiscal.getFornecedor().getCdPrestador())); 
				notaFiscal.setHospital(preservDAO.buscarCodigoHospital(notaFiscal.getHospital().getCdPrestador()));
				notaFiscal.setMedico(preservDAO.buscarCodigoMedico(notaFiscal.getMedico().getCdPrestador()));

				if (notaFiscal.getMedico() == null){

					Prestador medicoNovo = new Prestador();
					medicoNovo.setCdPrestador(0);
					medicoNovo.setUnidade(0);
					notaFiscal.setMedico(medicoNovo);


				}


				if (!notaFiscal.getTipoRegistro().equals("DIU")){

					if (notaFiscal.getBeneficiario().getUnidade().equals("0049")){

						Beneficiario beneficiarioAuxiliar = beneficiarioDAO.buscarPorBeneficiario(notaFiscal.getBeneficiario().getCdCarteiraInteira());
						notaFiscal.getBeneficiario().setNome(	beneficiarioAuxiliar.getNome()	);
						notaFiscal.getBeneficiario().setNomeUnimedUnidade(beneficiarioAuxiliar.getNomeUnimedUnidade());

						//notaFiscal.getBeneficiario().setNome(	(beneficiarioDAO.buscarPorBeneficiario(String.valueOf(notaFiscal.getBeneficiario().getCdCarteiraInteira())).getNome())	);
					}
					else {
						Beneficiario beneficiarioAuxiliar = beneficiarioDAO.buscarPorBeneficiarioInter(notaFiscal.getBeneficiario().getUnidade(),notaFiscal.getBeneficiario().getCdCarteiraInteira());
						notaFiscal.getBeneficiario().setNome(	beneficiarioAuxiliar.getNome()	);
						notaFiscal.getBeneficiario().setNomeUnimedUnidade(beneficiarioAuxiliar.getNomeUnimedUnidade());
						//beneficiarioDAO.buscarPorBeneficiarioInter(notaFiscal.getBeneficiario().getUnidade(),notaFiscal.getBeneficiario().getCdCarteiraInteira().toString())
					}
				}

				listaNotaFiscal.add(notaFiscal);	
			}
			State.close();
			return listaNotaFiscal;

		} catch (SQLException e) {
			// TODO Auto-generated catch block

		}
		return null;
	}


}
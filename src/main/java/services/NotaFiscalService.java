package services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import model.Diu;
import model.NotaFiscal;
import model.OpmeQtBipap;
import model.Prestador;
import dao.DetalheNotaDAO;
import dao.InsumoDAO;
import dao.NotaFiscalDAO;
import dao.PrestardorDAO;


public class NotaFiscalService {

	public List<Prestador> BucarFornecedorNome(String codigo){
		PrestardorDAO preservDAO = new PrestardorDAO();
		List<Prestador> listaPrestador = new ArrayList<Prestador>();
		codigo = codigo.toUpperCase();
		listaPrestador = preservDAO.buscarNomeFornecedor(codigo);
		return listaPrestador;
	}

	public List<Prestador> BucarHospitalNome(String codigo){		
		List<Prestador> listaPrestador = new ArrayList<Prestador>();
		PrestardorDAO preservDAO = new PrestardorDAO();
		codigo = codigo.toUpperCase();
		listaPrestador = preservDAO.buscarNomeHospital(codigo);
		return listaPrestador;
	}

	public List<Prestador> BucarMedicoNome(String codigo){
		List<Prestador> listaPrestador = new ArrayList<Prestador>();
		PrestardorDAO preservDAO = new PrestardorDAO();
		codigo = codigo.toUpperCase();
		listaPrestador = preservDAO.buscarNomeMedico(codigo);
		return listaPrestador;
	}

	public List<OpmeQtBipap> BuscaInsumoNome(String codigo,OpmeQtBipap opmeQtBipapItem){		
		List<OpmeQtBipap> listaInsumos = new ArrayList<OpmeQtBipap>();
		InsumoDAO insumoDAO = new InsumoDAO();
		codigo = codigo.toUpperCase();
		if(opmeQtBipapItem.getTpInsumo() == null || opmeQtBipapItem.getTpInsumo() == 0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo Insumo Inválido!", ""));
			return listaInsumos;		
		}
		listaInsumos = insumoDAO.buscarNomeInsumo(opmeQtBipapItem.getTpInsumo(), codigo);
		return listaInsumos;
	}

	public boolean inserirNotaFiscal(String tipoNota,NotaFiscal notaFiscal,NotaFiscal notaFiscalSelecionada,List<OpmeQtBipap> listaopmeQtBipap,float valorDetalheTotal,String matricula,List<Diu> listadiu){

		notaFiscal.setTipoRegistro(tipoNota);		

		if ( notaFiscal.getDataEmissao().after(notaFiscal.getDataVencimento())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Data Vencimento menor que Data Emissão!", ""));
			return false;
		}

		if ( tipoNota.equals("OPME") && notaFiscal.getDtRealizacao().after(notaFiscal.getDataEmissao()) ){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Data Vencimento menor que Data Realização!", ""));
			return false;
		}	

		NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();
		DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();

		if (notaFiscalSelecionada == null){
			valorDetalheTotal = testRound(valorDetalheTotal);
			NotaFiscal notaFicalProcura = notaFicalDAO.buscarCodigoFornecedorNrNota(notaFiscal.getFornecedor().getCdPrestador(), notaFiscal.getNumero());
			if (notaFicalProcura == null && ((notaFiscal.getValor() - notaFiscal.getIpi() - notaFiscal.getFrete() + notaFiscal.getDesconto()) == valorDetalheTotal) ){
				notaFiscal.setUsuarioAtualizacao(matricula);
				notaFiscal.setUsuarioInsercao(matricula);
				notaFiscal.setStatus("Aberta");
				notaFicalDAO.persistir(notaFiscal);


				if (!tipoNota.equals("DIU")){
					for (OpmeQtBipap opmeQtBipapSelecionado : listaopmeQtBipap){					
						detalheNotaDAO.persistirOpmeQtBipap(opmeQtBipapSelecionado,notaFiscal.getId());
					}
				}
				else {
					for (Diu diu : listadiu){
						detalheNotaDAO.persistirDiu(diu, notaFiscal.getId());						
					}					
				}


			}
			else if(notaFicalProcura != null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nota já cadastrada para este fornecedor!", ""));
				return false;
			}
			else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Valor Total da Nota diferente da soma dos detalhes!", ""));
				return false;				
			}			
		}		

		else {
			valorDetalheTotal = 0;
			if (!tipoNota.equals("DIU")){
				List<OpmeQtBipap> listaopmeQtBipapConferencia = new ArrayList<OpmeQtBipap>();
				listaopmeQtBipapConferencia = detalheNotaDAO.buscarDetalhesNotaOpmeQtBipap(notaFiscal.getId());

				for(OpmeQtBipap opmeQtBipapConf : listaopmeQtBipapConferencia){
					valorDetalheTotal = valorDetalheTotal + opmeQtBipapConf.getValor();
				}

			}
			else {				
				List<Diu> listaDiuConferencia = new ArrayList<Diu>();
				listaDiuConferencia = detalheNotaDAO.buscarDetalhesNotaDiu(notaFiscal.getId());

				for(Diu diu : listaDiuConferencia){
					valorDetalheTotal = valorDetalheTotal + diu.getValor();
				}				
			}


			valorDetalheTotal = testRound(valorDetalheTotal);
			if ((notaFiscal.getValor() - notaFiscal.getIpi() - notaFiscal.getFrete() + notaFiscal.getDesconto()) == valorDetalheTotal ){
				notaFiscal.setUsuarioAtualizacao(matricula);
				notaFiscal.setUsuarioInsercao(matricula);			
				notaFicalDAO.persistir(notaFiscal);				
			}
			else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Valor Total da Nota diferente da soma dos detalhes!", ""));
				return false;	
			}			
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nota Fiscal Salva!", ""));
		return true;
	}

	public Float testRound(Float numero) {
		int decimalPlace = 2;
		BigDecimal bd = new BigDecimal(numero);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		numero = (float) bd.doubleValue();
		return numero;
	}

	public boolean inserirNotaFiscalDetalhe(NotaFiscal notaFiscal,NotaFiscal notaFiscalSelecionada,OpmeQtBipap opmeQtBipapItem,List<OpmeQtBipap> listaopmeQtBipap,float valorDetalheTotal){

		if((opmeQtBipapItem.getTpInsumo() == 12 && opmeQtBipapItem.getCdInsumo() == 94301239) ||
				(opmeQtBipapItem.getTpInsumo() == 12 && opmeQtBipapItem.getCdInsumo() == 94301247) ||
				(opmeQtBipapItem.getTpInsumo() == 12 && opmeQtBipapItem.getCdInsumo() == 94301255) ||
				(opmeQtBipapItem.getTpInsumo() == 12 && opmeQtBipapItem.getCdInsumo() == 99999927) ||
				(opmeQtBipapItem.getTpInsumo() == 13 && opmeQtBipapItem.getCdInsumo() == 99999943) ){
			if (opmeQtBipapItem.getObservacao() == "" || opmeQtBipapItem.getObservacao() == null){			    	
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Preencha o campo observação!", ""));
				return false;
			}			
		}
		
		NotaFiscalDAO notaFicalDAO = new NotaFiscalDAO();
		DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();		
		opmeQtBipapItem.setValor(opmeQtBipapItem.getValor() * opmeQtBipapItem.getQuantidade());
		listaopmeQtBipap.add(opmeQtBipapItem);

		if (notaFiscalSelecionada != null){			
					
			opmeQtBipapItem = detalheNotaDAO.persistirOpmeQtBipap(opmeQtBipapItem,notaFiscalSelecionada.getId());
			notaFiscalSelecionada.setQtdItens(notaFiscalSelecionada.getQtdItens() + opmeQtBipapItem.getQuantidade());			
			notaFicalDAO.persistir(notaFiscalSelecionada);				
		}
		else {			
			
			notaFiscal.setQtdItens(notaFiscal.getQtdItens() + opmeQtBipapItem.getQuantidade());
			opmeQtBipapItem.setValor((float) (Math.floor(opmeQtBipapItem.getValor() * 100) / 100));
			valorDetalheTotal = valorDetalheTotal + opmeQtBipapItem.getValor();			
			
		}		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item da nota fiscal cadastrada!", ""));	
		return true;
	}
	
	
	public List<OpmeQtBipap> carregarDetalhesNota(NotaFiscal notaFiscal,NotaFiscal notaFiscalSelecionada,OpmeQtBipap opmeQtBipapItem,List<OpmeQtBipap> listaopmeQtBipap,float valorDetalheTotal){
		DetalheNotaDAO detalheNotaDAO = new DetalheNotaDAO();
		if (notaFiscalSelecionada != null){
			notaFiscal = notaFiscalSelecionada;
		}
		opmeQtBipapItem = new OpmeQtBipap();
		List<OpmeQtBipap> listaopmeQtBipapAux = null;
		if (notaFiscalSelecionada != null){
			listaopmeQtBipapAux = detalheNotaDAO.buscarDetalhesNotaOpmeQtBipap(notaFiscalSelecionada.getId());		
		}
		if (listaopmeQtBipapAux != null) listaopmeQtBipap = listaopmeQtBipapAux;

		valorDetalheTotal = 0;
		int quantidadeInsumos = 0;
		for (OpmeQtBipap opmeQtBipapSelecionado : listaopmeQtBipap){
			valorDetalheTotal = valorDetalheTotal + opmeQtBipapSelecionado.getValor(); 	
			quantidadeInsumos = quantidadeInsumos + opmeQtBipapSelecionado.getQuantidade();
		}

		notaFiscal.setQtdItens(quantidadeInsumos);

		if (notaFiscalSelecionada != null){
			notaFiscalSelecionada.setQtdItens(quantidadeInsumos);			

		}
		
		return listaopmeQtBipap;
	}
	
}

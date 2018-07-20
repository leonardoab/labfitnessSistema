/*ATUALIZAÇÕES*/
/*DATA = 15/05/2016   MÉTODO = completeText 					ANALISTA = Tliner Friaça Castro			*/
/*DATA = 			  MÉTODO = completeText 					ANALISTA = 								*/
/*DATA = 			  MÉTODO = completeText 					ANALISTA = 								*/
/*DATA = 			  MÉTODO = completeText 					ANALISTA = 								*/


package interfaceDAO;

import java.util.ArrayList;

import model.Colaborador;

public interface ColaboradorDAO extends GenericDAO<Colaborador, Long> {
	
public String buscarDescriaoSetor(String siglaOrgao);
	
	public Colaborador buscarPorMatricula(Long matricula);
	
	public ArrayList<Colaborador> buscarSetor(Long matricula);

	public Colaborador buscarUsuarioRede(String usuarioRede);
	
	public ArrayList<String> buscarSetores();
	
	public ArrayList<String> buscarNomesColaboradores(String nome,String setor);
	
	public Colaborador buscarNomeIndividual(String nome);
	
	public ArrayList<Colaborador> buscarColaboradorSetor(String setor);

}

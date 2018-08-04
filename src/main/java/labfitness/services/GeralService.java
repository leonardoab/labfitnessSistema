package labfitness.services;

import java.util.ArrayList;
import java.util.List;

import labfitness.model.AnamneseQuestionamento;
import labfitness.model.AntropometriaUnidade;

public class GeralService {

	public List<AnamneseQuestionamento> BucarItensListaTipo(Integer tipo,
			List<AnamneseQuestionamento> listaParametro) {

		List<AnamneseQuestionamento> listaAnamneseQuestionamentoRetorno = new ArrayList<AnamneseQuestionamento>();

		for (AnamneseQuestionamento anamneseQuestionamento : listaParametro) {

			if (anamneseQuestionamento.getQuestionamento()
					.getTipoQuestionamento().getId_tipo_questionamento() == tipo) {

				listaAnamneseQuestionamentoRetorno.add(anamneseQuestionamento);

			}

		}

		return listaAnamneseQuestionamentoRetorno;

	}

	public List<AntropometriaUnidade> BucarMedicaoTipoMedicao(Integer tipo,
			List<AntropometriaUnidade> listaParametro) {

		List<AntropometriaUnidade> listaAntropometriaUnidadeRetorno = new ArrayList<AntropometriaUnidade>();

		for (AntropometriaUnidade antropometriaUnidade : listaParametro) {

			if (antropometriaUnidade.getMedicao().getTipoMedicao()
					.getId_tipo_medicao() == tipo) {

				listaAntropometriaUnidadeRetorno.add(antropometriaUnidade);

			}

		}

		return listaAntropometriaUnidadeRetorno;

	}

}

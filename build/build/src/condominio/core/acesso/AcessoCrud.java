package condominio.core.acesso;

import java.util.List;

import condominio.core.acesso.model.VagasPreenchidasModel;
import condominio.server.modelo.HISTORICO_ACESSO;
import condominio.server.modelo.dao.HistoricoAcessoJpaController;

public class AcessoCrud {
	
	HistoricoAcessoJpaController historico = new HistoricoAcessoJpaController();

	public void createHistoricoAcesso(HISTORICO_ACESSO historico) {
		this.historico.create(historico);		
	}
	
	public List<HISTORICO_ACESSO> findHistoricoAcesso(Long id) {
		return this.historico.findHistoricoVisitante(id);	
	}
	
	public HISTORICO_ACESSO findHistorico(Long id) {
		return this.historico.findHistoricoAcesso(id);
	}
	
	public List<VagasPreenchidasModel> findStillInside() {
		return VagasPreenchidasModel.create(this.historico.findStillInside());	
	}
}

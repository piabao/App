package condominio.server.modelo.php;

import java.sql.SQLException;
import java.util.List;

import condominio.server.modelo.ANIMAIS;
import condominio.server.modelo.HISTORICO_ACESSO;
import condominio.server.modelo.dao.exceptions.NonexistentEntityException;
import condominio.server.modelo.php.dao.RequestFactory;
import condominio.server.modelo.php.js.AnimaisHelper;
import condominio.server.modelo.php.js.HistoricoAcessoHelper;

public class HistoricoAcessoPhpController {
	
	private static final String TABLE_NAME = "historico_acesso";

	private String getValues(HISTORICO_ACESSO val) {
		return "'"+val.getIdVisitante()+"', '"+val.getId()+"', '"+val.getIdVeiculo()+"', '"+val.getDataEntrada()+"', '"+val.getDataSaida()+"', '"+val.getResponsavel()+"', '"+val.getCasa()+"'";
	}

	private String getEditValues(HISTORICO_ACESSO val) {
		return "IDVISITANTE = '"+val.getIdVisitante()+"', ID = '"+val.getId()+"', IDVEICULO = '"+val.getIdVeiculo()+"', RESPONSAVEL = '"+val.getResponsavel()+"', CASA = '"+val.getCasa()+"', DATAENTRADA = '"+val.getDataEntrada()+"', DATASAIDA = '"+val.getDataSaida()+"'";
	}

	public List<ANIMAIS> findAnimaisByMorador(String morador) {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, TABLE_NAME, "IDMORADOR = '"+morador+"'");
		return AnimaisHelper.toAnimaisList(rf.doPost());
	}

	public void destroy(String identificador) throws SQLException{
		new RequestFactory(RequestFactory.removerUrl, TABLE_NAME, "IDMORADOR = '"+identificador+"'").doPost();		
	}

	public void destroy(Long id) throws NonexistentEntityException{
		new RequestFactory(RequestFactory.removerUrl, TABLE_NAME, "ID = '"+id+"'").doPost();		
	}

	public void create(HISTORICO_ACESSO valores) {
		RequestFactory rf;
		if(valores.getId() != null){
			rf = new RequestFactory(RequestFactory.editarUrl, TABLE_NAME, getEditValues(valores), "ID = '"+valores.getId()+"'");
		}else{
			rf = new RequestFactory(RequestFactory.salvarUrl, TABLE_NAME, valores.getAtribNames(), getValues(valores));			
		}
		rf.doPost();
	}

	public List<HISTORICO_ACESSO> findHistoricoVisitante(Long idVisitante) {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, TABLE_NAME, "IDVISITANTE = '"+idVisitante+"'");
		return HistoricoAcessoHelper.toHistoricoAcessoList(rf.doPost());
	}

	public HISTORICO_ACESSO findHistoricoAcesso(Long id) {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, TABLE_NAME, "ID = '"+id+"'");
		List<HISTORICO_ACESSO> historicoAcessoList = HistoricoAcessoHelper.toHistoricoAcessoList(rf.doPost());
		if(historicoAcessoList != null && !historicoAcessoList.isEmpty()){
			return historicoAcessoList.get(0);
		}
		return new HISTORICO_ACESSO();
	}

}

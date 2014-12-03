package condominio.server.modelo.php;

import java.util.List;

import condominio.server.modelo.TELEFONES;
import condominio.server.modelo.dao.exceptions.NonexistentEntityException;
import condominio.server.modelo.php.dao.RequestFactory;
import condominio.server.modelo.php.js.TelefonesHelper;

public class TelefonesPhpController {
	
	private static final String TABLE_NAME = "telefones";

	public void create(TELEFONES valores) {
		RequestFactory rf;
		if(valores.getId() != null){
			rf = new RequestFactory(RequestFactory.editarUrl, TABLE_NAME, getEditValues(valores), "ID = '"+valores.getId()+"'");
		}else{
			rf = new RequestFactory(RequestFactory.salvarUrl, TABLE_NAME, valores.getAtribNames(), getValues(valores));			
		}
		rf.doPost();		
	}

	private String getValues(TELEFONES val) {
		return "'"+val.getId()+"', '"+val.getIdMorador()+"', '"+val.getNumero()+"', '"+val.getOperadora()+"'";
	}

	private String getEditValues(TELEFONES val) {
		return "IDMORADOR = '"+val.getIdMorador()+"', NUMERO = '"+val.getNumero()+"', OPERADORA = '"+val.getOperadora()+"'";
	}

	public List<TELEFONES> findTelefonesByIdMorador(Long moradorID) {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, TABLE_NAME, "IDMORADOR = '"+moradorID+"'");
		return TelefonesHelper.toTelefonesList(rf.doPost());
	}

	public void destroy(Long id) throws NonexistentEntityException {
		new RequestFactory(RequestFactory.removerUrl, TABLE_NAME, "IDMORADOR = '"+id+"'").doPost();				
	}

	public void remove(Long id) throws NonexistentEntityException {
		new RequestFactory(RequestFactory.removerUrl, TABLE_NAME, "ID = '"+id+"'").doPost();
	}

}

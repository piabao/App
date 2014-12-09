package condominio.server.modelo.php;

import java.sql.SQLException;
import java.util.List;

import condominio.server.modelo.ANIMAIS;
import condominio.server.modelo.dao.exceptions.NonexistentEntityException;
import condominio.server.modelo.php.dao.RequestFactory;
import condominio.server.modelo.php.js.AnimaisHelper;

public class AnimaisPhpController {
	
	private static final String TABLE_NAME = "animais";

	public void create(ANIMAIS valores) {
		RequestFactory rf;
		if(valores.getId() != null){
			rf = new RequestFactory(RequestFactory.editarUrl, TABLE_NAME, getEditValues(valores), "ID = '"+valores.getId()+"'");
		}else{
			rf = new RequestFactory(RequestFactory.salvarUrl, TABLE_NAME, valores.getAtribNames(), getValues(valores));			
		}
		rf.doPost();
	}

	private String getValues(ANIMAIS val) {
		return "'"+val.getCor()+"', '"+val.getId()+"', '"+val.getIdMorador()+"', '"+val.getNome()+"', '"+val.getPorte()+"', '"+val.getTipo()+"'";
	}

	private String getEditValues(ANIMAIS val) {
		return "COR = '"+val.getCor()+"', IDMORADOR = '"+val.getIdMorador()+"', NOME = '"+val.getNome()+"', PORTE = '"+val.getPorte()+"', TIPO = '"+val.getTipo()+"'";
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

}

package condominio.server.modelo.php;

import java.sql.SQLException;
import java.util.List;

import javafx.util.Callback;
import condominio.server.modelo.CADASTRO_MORADOR;
import condominio.server.modelo.php.dao.RequestFactory;
import condominio.server.modelo.php.js.CadastroMoradorHelper;

public class CadastroMoradorPhpController {

	private static final String TABLE_NAME = "cadastro_morador";
	private Callback<List<CADASTRO_MORADOR>, Void> callbackRetorno;
	
	public List<CADASTRO_MORADOR> searchMorador(String value, Callback<List<CADASTRO_MORADOR>, Void> callback) {
		if(value == null || value.isEmpty()){
			return null;
		}
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, "", "", value, "1");
		if(callback == null){
			return CadastroMoradorHelper.toMoradorList(rf.doPost());
		}
		this.callbackRetorno = callback;
		rf.doPost(new Callback<String, Void>() {
			
			@Override
			public Void call(String param) {
			try{
				callbackRetorno.call(CadastroMoradorHelper.toMoradorList(param));
			} catch (IllegalStateException e) {
				System.out.println("Deu um erro???" + callbackRetorno.toString() + param);
			}
			
			return null;
			}
		});
		return null;
	}

	public CADASTRO_MORADOR create(CADASTRO_MORADOR valores) throws SQLException{
		RequestFactory rf;
		if(valores.getId() != null){
			rf = new RequestFactory(RequestFactory.editarUrl, TABLE_NAME, getEditValues(valores), "ID = '"+valores.getId()+"'");
		}else{
			rf = new RequestFactory(RequestFactory.salvarUrl, TABLE_NAME, valores.getAtribNames(), getValues(valores));			
		}
		String response = rf.doPost();
		try {
			valores.setId(Long.parseLong(response));			
		} catch (Exception e) {}
		
		return valores;
	}
	
	private String getEditValues(CADASTRO_MORADOR val) {
		return "CPF = '"+val.getCpf()+"', EMAIL = '"+val.getEmail()+"', IDENTIFICADOR = '"+val.getIdentificador()+"', NOME = '"+val.getNome()+"', OBSERVACAO = '"+val.getObservacao()+"', TIPO_MORADOR = '"+val.getTipo_morador()+"', VAGA = '"+val.getVaga()+"'";
	}

	private String getValues(CADASTRO_MORADOR val) {
		return "'"+val.getCpf()+"', '"+val.getEmail()+"', '"+val.getId()+"', '"+val.getIdentificador()+"', '"+val.getNome()+"', '"+val.getObservacao()+"', '"+val.getTipo_morador()+"', '"+val.getVaga()+"'";
	}

	public List<CADASTRO_MORADOR> searchMoradoresByID(String value) {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, "", "", "'"+value+"'", "2");
		return CadastroMoradorHelper.toMoradorList(rf.doPost());
	}

	public void destroy(Long id) {
		RequestFactory rf = new RequestFactory(RequestFactory.removerUrl, TABLE_NAME, "ID = '"+id+"'");
		rf.doPost();
	}

	public List<CADASTRO_MORADOR> findCADASTRO_MORADOREntities() {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, TABLE_NAME, "ID = ID");
		return CadastroMoradorHelper.toMoradorList(rf.doPost());
	}

	public List<CADASTRO_MORADOR> searchWhereMorador(String where) {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, TABLE_NAME, where);
		return CadastroMoradorHelper.toMoradorList(rf.doPost());
	}

	public List<CADASTRO_MORADOR> searchByIdentification(String identificador) {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, TABLE_NAME, "IDENTIFICADOR ='"+identificador+"'");
		return CadastroMoradorHelper.toMoradorList(rf.doPost());
	}

	public List<CADASTRO_MORADOR> searchVisitante(String value, Callback<List<CADASTRO_MORADOR>, Void> callback) {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, "", "", "'"+value+"'", "3");
		if(callback == null){
			return CadastroMoradorHelper.toMoradorList(rf.doPost());
		}
		rf.doPost(new Callback<String, Void>() {
			
			@Override
			public Void call(String param) {
				callback.call(CadastroMoradorHelper.toMoradorList(param));
				return null;
			}
		});
		return null;
	}
	
}

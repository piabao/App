package condominio.server.modelo.php;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Callback;
import condominio.server.modelo.PRIVILEGIOS;
import condominio.server.modelo.USUARIO;
import condominio.server.modelo.VEICULOS;
import condominio.server.modelo.dao.exceptions.NonexistentEntityException;
import condominio.server.modelo.php.dao.RequestFactory;
import condominio.server.modelo.php.js.FuncionariosHelper;
import condominio.server.modelo.php.js.PrivilegiosHelper;
import condominio.server.modelo.php.js.UsuarioHelper;
import condominio.server.modelo.php.js.VeiculosHelper;

public class UsuarioPhpController {

	private static final String TABLE_NAME = "usuario";

	public void create(USUARIO user, Callback<USUARIO, Void> call) throws SQLException{
		PrivilegiosPhpController privilegio = new PrivilegiosPhpController();
		RequestFactory rf;
		if(user.getId() != null){
			rf = new RequestFactory(RequestFactory.editarUrl, TABLE_NAME, getEditValues(user), "ID = '"+user.getId()+"'");
		}else{
			rf = new RequestFactory(RequestFactory.salvarUrl, TABLE_NAME, user.getAtribNames(), getValues(user));			
		}
		rf.doPost(new Callback<String, Void>() {
			
			@Override
			public Void call(String param) {
				user.setId(Long.parseLong(param));
				for(PRIVILEGIOS pr : user.getPrivilegios()){
					pr.setUsuario(user);
				}
				privilegio.create(user.getPrivilegios());
				call.call(user);
				return null;
			}
		});
	}

	private String getValues(USUARIO user) {
		return "'"+user.getSenha()+"', '"+user.getId()+"', '"+user.getFuncionario().getId()+"', '"+user.getUsuario()+"'";
	}

	private String getEditValues(USUARIO user) {
		return "SENHA = '"+user.getSenha()+"', ID = '"+user.getId()+"', FUNCIONARIOS = '"+user.getFuncionario().getId()+"', USER = '"+user.getUsuario()+"'";
	}

	public USUARIO findUSUARIO(String user) {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, TABLE_NAME, "USER = '"+user+"'");
		List<USUARIO> usuarioList = UsuarioHelper.toUsuarioList(rf.doPost());
		if(usuarioList == null || usuarioList.isEmpty()){
			return new USUARIO();
		}
		return usuarioList.get(0);
	}

	public List<USUARIO> findUSUARIOEntities() {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, TABLE_NAME, "ID = ID");
		return UsuarioHelper.toUsuarioList(rf.doPost());
	}

	public void destroy(USUARIO user) throws NonexistentEntityException{
		PrivilegiosPhpController privilegio = new PrivilegiosPhpController();
		RequestFactory rf = new RequestFactory(RequestFactory.removerUrl, TABLE_NAME, "ID = '"+user.getId()+"'");
		rf.doPost();
		privilegio.destroy(user);
	}
}

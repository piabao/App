package condominio.server.modelo.php;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public USUARIO create(USUARIO user) throws SQLException{
		RequestFactory rf;
		if(user.getId() != null){
			rf = new RequestFactory(RequestFactory.editarUrl, TABLE_NAME, getEditValues(user), "ID = '"+user.getId()+"'");
		}else{
			rf = new RequestFactory(RequestFactory.salvarUrl, TABLE_NAME, user.getAtribNames(), getValues(user));			
		}
		rf.doPost();
		
		return findUSUARIO(user.getUsuario());
	}

	private String getValues(USUARIO user) {
		return "'"+user.getSenha()+"', '"+user.getId()+"', '"+user.getFuncionario()+"', '"+user.getUsuario()+"'";
	}

	private String getEditValues(USUARIO user) {
		return "SENHA = '"+user.getSenha()+"', ID = '"+user.getId()+"', FUNCIONARIOS = '"+user.getFuncionario()+"', USER = '"+user.getUsuario()+"'";
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

	public void destroy(Long id) throws NonexistentEntityException{
		RequestFactory rf = new RequestFactory(RequestFactory.removerUrl, TABLE_NAME, "ID = '"+id+"'");
		rf.doPost();
	}
}

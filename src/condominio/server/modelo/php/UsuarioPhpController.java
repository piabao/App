package condominio.server.modelo.php;

import java.sql.SQLException;
import java.util.List;

import condominio.server.modelo.USUARIO;
import condominio.server.modelo.VEICULOS;
import condominio.server.modelo.dao.exceptions.NonexistentEntityException;
import condominio.server.modelo.php.dao.RequestFactory;
import condominio.server.modelo.php.js.VeiculosHelper;

public class UsuarioPhpController {

	private static final String TABLE_NAME = "usuarios";

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
		return "SENHA = '"+user.getSenha()+"', ID = '"+user.getId()+"', FUNCIONARIOS = '"+user.getFuncionario()+"', USUARIO = '"+user.getUsuario()+"'";
	}

	public USUARIO findUSUARIO(String user) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<USUARIO> findUSUARIOEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	public void destroy(Long id) throws NonexistentEntityException{
		// TODO Auto-generated method stub
		
	}

}

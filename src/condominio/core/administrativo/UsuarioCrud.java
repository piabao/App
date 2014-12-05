package condominio.core.administrativo;

import java.sql.SQLException;
import java.util.List;

import condominio.server.modelo.PRIVILEGIOS;
import condominio.server.modelo.USUARIO;
import condominio.server.modelo.dao.PrivilegiosJpaController;
import condominio.server.modelo.dao.UsuarioJpaController;
import condominio.server.modelo.dao.exceptions.NonexistentEntityException;
import condominio.server.modelo.php.PrivilegiosPhpController;
import condominio.server.modelo.php.UsuarioPhpController;

public class UsuarioCrud {
	
//	private UsuarioJpaController usuario;
//	private PrivilegiosJpaController privilegio;
	private PrivilegiosPhpController privilegio;
	private UsuarioPhpController usuario;
	
	public UsuarioCrud(){
//		usuario = new UsuarioJpaController();
//		privilegio = new PrivilegiosJpaController();
		usuario = new UsuarioPhpController();
		privilegio = new PrivilegiosPhpController();
	}

	public USUARIO create(USUARIO user) {
		try {
			return usuario.create(user);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void create(List<PRIVILEGIOS> prvList) {
		privilegio.create(prvList);		
	}

	public USUARIO processaLogin(String user) {
		USUARIO usuarios = usuario.findUSUARIO(user);
		return 	usuarios == null ? new USUARIO() : usuarios;
	}

	public List<PRIVILEGIOS> getPrivilegios(Long id) {		
		return privilegio.findPrivilegiosByUser(id);
	}

	public List<USUARIO> listaUsuarios() {
		return usuario.findUSUARIOEntities();
	}

	public void removerUsuario(USUARIO user) {
		try {
			usuario.destroy(user.getId());
		} catch (NonexistentEntityException e) {
			e.printStackTrace();
		}
	}

}

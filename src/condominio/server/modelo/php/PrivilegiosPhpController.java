package condominio.server.modelo.php;

import java.util.List;

import condominio.server.modelo.ANIMAIS;
import condominio.server.modelo.PRIVILEGIOS;
import condominio.server.modelo.USUARIO;
import condominio.server.modelo.VEICULOS;
import condominio.server.modelo.dao.exceptions.NonexistentEntityException;
import condominio.server.modelo.php.dao.RequestFactory;
import condominio.server.modelo.php.js.AnimaisHelper;
import condominio.server.modelo.php.js.PrivilegiosHelper;
import condominio.server.modelo.php.js.VeiculosHelper;

public class PrivilegiosPhpController {

	public static final String TABLE_NAME = "privilegios";

	public void create(List<PRIVILEGIOS> prvList) {
		for (PRIVILEGIOS privilegios : prvList) {
			RequestFactory rf;
			if(privilegios.getId() != null){
				rf = new RequestFactory(RequestFactory.editarUrl, TABLE_NAME, getEditValues(privilegios), "ID = '"+privilegios.getId()+"'");				
			}else{
				rf = new RequestFactory(RequestFactory.salvarUrl, TABLE_NAME, privilegios.getAtribNames(), getValues(privilegios));
			}
			rf.doPost();
		}
	}

	private String getEditValues(PRIVILEGIOS prv) {
		return "DESCRICAO = '"+prv.getDescricao()+"', ID = '"+prv.getId()+"', IDUSUARIO = '"+prv.getUsuario()+"'";
	}

	private String getValues(PRIVILEGIOS prv) {
			return "'"+prv.getId()+"', '"+prv.getDescricao()+"', '"+prv.getUsuario().getId()+"', '"+prv.getPermissao()+"'";
	}

	public List<PRIVILEGIOS> findPrivilegiosByUser(Long id) {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, TABLE_NAME, "IDUSUARIO = '"+id+"'");
		return PrivilegiosHelper.toPrivilegioList(rf.doPost());
	}

	public void destroy(USUARIO user) {
		RequestFactory rf = new RequestFactory(RequestFactory.removerUrl, TABLE_NAME, "IDUSUARIO = '"+user.getId()+"'");
		rf.doPost();
	}

}

package condominio.server.modelo.php;

import java.util.List;

import condominio.server.modelo.TIPO_MORADOR;
import condominio.server.modelo.php.dao.RequestFactory;
import condominio.server.modelo.php.js.TipoMoradorHelper;

public class TipoMoradorPhpController {

	private static final String TABLE_NAME = "tipo_morador";

	public void create(TIPO_MORADOR valores) {
		// TODO Auto-generated method stub		
	}

	public List<TIPO_MORADOR> findTIPO_MORADOREntities() {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, TABLE_NAME, "ID = ID");
		return TipoMoradorHelper.toTipoMoradorList(rf.doPost());
	}

	public TIPO_MORADOR findTIPO_MORADOR(Long tipo_morador) {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, TABLE_NAME, "ID = '"+tipo_morador+"'");
		return TipoMoradorHelper.toTipoMoradorList(rf.doPost()).get(0);
	}
	
}

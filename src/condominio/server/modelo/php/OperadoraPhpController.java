package condominio.server.modelo.php;

import java.util.List;

import condominio.server.modelo.OPERADORA;
import condominio.server.modelo.php.dao.RequestFactory;
import condominio.server.modelo.php.js.OperadoraHelper;

public class OperadoraPhpController {

	private static final String TABLE_NAME = "operadora";

	public List<OPERADORA> findOperadoraEntities() {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, TABLE_NAME, "ID = ID");
		return OperadoraHelper.toOperadoraList(rf.doPost());
	}
	
}

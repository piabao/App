package condominio.server.modelo.php;

import java.util.List;

import condominio.server.modelo.VEICULOS;
import condominio.server.modelo.dao.exceptions.NonexistentEntityException;
import condominio.server.modelo.php.dao.RequestFactory;
import condominio.server.modelo.php.js.VeiculosHelper;

public class VeiculosPhpController {

	private static final String TABLE_NAME = "veiculos";

	public VEICULOS create(VEICULOS valores) {
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
	
	private String getValues(VEICULOS val) {
		return "'"+val.getCor()+"', '"+val.getId()+"', '"+val.getIdMorador()+"', '"+val.getModelo()+"', '"+val.getPlaca()+"', '"+val.getTipo()+"', '"+val.getVisitante()+"'";
	}

	private String getEditValues(VEICULOS val) {
		return "COR = '"+val.getCor()+"', IDMORADOR = '"+val.getIdMorador()+"', MODELO = '"+val.getModelo()+"', PLACA = '"+val.getPlaca()+"', TIPO = '"+val.getTipo()+"', VISITANTE = '"+val.getVisitante()+"'";
	}

	public List<VEICULOS> findVeiculosByMorador(String morador) {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, TABLE_NAME, "IDMORADOR = '"+morador+"' AND VISITANTE IS NULL");
		return VeiculosHelper.toVeiculosList(rf.doPost());
	}

	public void destroy(String identificador) {
		new RequestFactory(RequestFactory.removerUrl, TABLE_NAME, "IDMORADOR = '"+identificador+"'").doPost();		
	}

	public void destroy(Long id) throws NonexistentEntityException {
		new RequestFactory(RequestFactory.removerUrl, TABLE_NAME, "ID = '"+id+"'").doPost();		
	}

	public List<VEICULOS> findVeiculosByVisitante(Long id) {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, TABLE_NAME, "VISITANTE = '"+id+"'");
		return VeiculosHelper.toVeiculosList(rf.doPost());
	}

}

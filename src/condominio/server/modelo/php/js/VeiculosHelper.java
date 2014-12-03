package condominio.server.modelo.php.js;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import condominio.server.modelo.ANIMAIS;
import condominio.server.modelo.CADASTRO_MORADOR;
import condominio.server.modelo.VEICULOS;
import netscape.javascript.JSObject;

public class VeiculosHelper {
	
	public static final String ID_MORADOR = "IDMORADOR";
	public static final String MODELO = "MODELO";
	public static final String TIPO = "TIPO";
	public static final String COR = "COR";
	public static final String PLACA = "PLACA";
	private static final String ID = "ID";

	public static List<VEICULOS> toVeiculosList(String json) {
		System.out.println("VEIVULO: "+json);
		List<VEICULOS> values = new ArrayList<VEICULOS>();
		try {
			JSONArray jsonArray = new JSONArray(json);			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject veiculos = jsonArray.getJSONObject(i);
				VEICULOS cadastro = new VEICULOS();
				cadastro.setCor(verify(veiculos.get(COR)));
				cadastro.setIdMorador(verify(veiculos.get(ID_MORADOR)));
				cadastro.setId(convertToLong(veiculos.get(ID)));
				cadastro.setModelo(verify(veiculos.get(MODELO)));
				cadastro.setPlaca(verify(veiculos.get(PLACA)));
				cadastro.setTipo(verify(veiculos.get(TIPO)));
				values.add(cadastro);
			}
		} catch (JSONException e) {
			return values;
		}
		return values;	
	}

	private static String verify(Object valor) {
		if(valor == null){
			return "";
		}
		return valor.toString();
	}

	private static Long convertToLong(Object chave) {
		if(chave == null){
			return null;
		}
		return Long.parseLong(chave.toString());
	}

}

package condominio.server.modelo.php.js;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import condominio.server.modelo.OPERADORA;
import condominio.server.modelo.TIPO_MORADOR;

public class OperadoraHelper {

	private static final String OPERADORA = "OPERADORA";
	private static final String ID = "ID";

	public static List<OPERADORA> toOperadoraList(String json) {
		List<OPERADORA> values = new ArrayList<OPERADORA>();
		try {
			JSONArray jsonArray = new JSONArray(json);			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject operadora = jsonArray.getJSONObject(i);
				OPERADORA cadastro = new OPERADORA();
				cadastro.setOperadora(verify(operadora.get(OPERADORA)));
				cadastro.setId(convertToLong(operadora.get(ID)));
				values.add(cadastro);
			}
		} catch (JSONException e) {
			e.printStackTrace();
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

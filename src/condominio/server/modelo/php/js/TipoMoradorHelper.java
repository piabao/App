package condominio.server.modelo.php.js;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import condominio.server.modelo.TIPO_MORADOR;

public class TipoMoradorHelper {

	private static final String DESCRICAO = "DESCRICAO";
	private static final String ID = "ID";

	public static List<TIPO_MORADOR> toTipoMoradorList(String json) {
		System.out.println("TIPO_MORADOR "+json);
		List<TIPO_MORADOR> values = new ArrayList<TIPO_MORADOR>();
		try {
			JSONArray jsonArray = new JSONArray(json);			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tipoMorador = jsonArray.getJSONObject(i);
				TIPO_MORADOR cadastro = new TIPO_MORADOR();
				cadastro.setDescricao(verify(tipoMorador.get(DESCRICAO)));
				cadastro.setId(convertToLong(tipoMorador.get(ID)));
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

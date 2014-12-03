package condominio.server.modelo.php.js;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import condominio.server.modelo.ANIMAIS;
import condominio.server.modelo.CADASTRO_MORADOR;
import condominio.server.modelo.TELEFONES;
import netscape.javascript.JSObject;

public class TelefonesHelper {
	
	public static final String ID_MORADOR = "IDMORADOR";
	public static final String NUMERO = "NUMERO";
	public static final String OPERADORA = "OPERADORA";
	private static final String ID = "ID";

	public static List<TELEFONES> toTelefonesList(String json) {
		List<TELEFONES> values = new ArrayList<TELEFONES>();
		try {
			JSONArray jsonArray = new JSONArray(json);			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject telefones = jsonArray.getJSONObject(i);
				TELEFONES cadastro = new TELEFONES();
				cadastro.setIdMorador(convertToLong(telefones.get(ID_MORADOR)));
				cadastro.setId(convertToLong(telefones.get(ID)));
				cadastro.setNumero(verify(telefones.get(NUMERO)));
				cadastro.setOperadora(verify(telefones.get(OPERADORA)));
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

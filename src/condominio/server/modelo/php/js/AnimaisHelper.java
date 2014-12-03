package condominio.server.modelo.php.js;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import condominio.server.modelo.ANIMAIS;
import condominio.server.modelo.CADASTRO_MORADOR;
import netscape.javascript.JSObject;

public class AnimaisHelper {

	public static final String ID_MORADOR = "IDMORADOR";
	public static final String NOME = "NOME";
	public static final String TIPO = "TIPO";
	public static final String COR = "COR";
	public static final String PORTE = "PORTE";
	private static final String ID = "ID";

	public static List<ANIMAIS> toAnimaisList(String json) {
		List<ANIMAIS> values = new ArrayList<ANIMAIS>();
		try {
			JSONArray jsonArray = new JSONArray(json);			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject animais = jsonArray.getJSONObject(i);
				ANIMAIS cadastro = new ANIMAIS();
				cadastro.setCor(verify(animais.get(COR)));
				cadastro.setIdMorador(verify(animais.get(ID_MORADOR)));
				cadastro.setId(convertToLong(animais.get(ID)));
				cadastro.setNome(verify(animais.get(NOME)));
				cadastro.setPorte(verify(animais.get(PORTE)));
				cadastro.setTipo(verify(animais.get(TIPO)));
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

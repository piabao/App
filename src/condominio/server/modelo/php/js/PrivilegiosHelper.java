package condominio.server.modelo.php.js;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import condominio.server.modelo.ANIMAIS;
import condominio.server.modelo.CADASTRO_MORADOR;
import condominio.server.modelo.PRIVILEGIOS;
import condominio.server.modelo.USUARIO;
import netscape.javascript.JSObject;

public class PrivilegiosHelper {
	
	public static final String ID = "ID";
	public static final String DESCRICAO = "DESCRICAO";
	public static final String PERMISSAO = "PERMISSAO";
	public static final String USER = "USER";

	public static List<PRIVILEGIOS> toPrivilegioList(String json) {
		List<PRIVILEGIOS> values = new ArrayList<PRIVILEGIOS>();
		try {
			JSONArray jsonArray = new JSONArray(json);			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject privilegios = jsonArray.getJSONObject(i);
				PRIVILEGIOS cadastro = new PRIVILEGIOS();
				cadastro.setDescricao(verify(privilegios.get(DESCRICAO)));
				cadastro.setPermissao(convertToBoolean(privilegios.get(PERMISSAO)));
				cadastro.setId(convertToLong(privilegios.get(ID)));
				//TODO: cadastro.setUsuario(recuperarUsuario(privilegios.get(USER)));
				values.add(cadastro);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return values;	
	}

	private static boolean convertToBoolean(Object object) {
		if(object == null){
			return false;
		}
		return Boolean.getBoolean(object.toString());
	}

	private static USUARIO recuperarUsuario(Object object) {
		// TODO Auto-generated method stub
		return null;
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

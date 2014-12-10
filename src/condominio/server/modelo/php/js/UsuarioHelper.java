package condominio.server.modelo.php.js;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import condominio.server.modelo.ANIMAIS;
import condominio.server.modelo.CADASTRO_MORADOR;
import condominio.server.modelo.FUNCIONARIOS;
import condominio.server.modelo.USUARIO;
import netscape.javascript.JSObject;

public class UsuarioHelper {

	public static final String USER = "USER";
	public static final String SENHA = "SENHA";
	public static final String FUNCIONARIO = "FUNCIONARIO";
	private static final String ID = "ID";

	public static List<USUARIO> toUsuarioList(String json) {
		List<USUARIO> values = new ArrayList<USUARIO>();
		try {
			JSONArray jsonArray = new JSONArray(json);			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject usuario = jsonArray.getJSONObject(i);
				USUARIO cadastro = new USUARIO();
				cadastro.setUsuario(verify(usuario.get(USER)));
				cadastro.setSenha(verify(usuario.get(SENHA)));
				cadastro.setId(convertToLong(usuario.get(ID)));
				//TODO: cadastro.setFuncionario(convertToLong(usuario.get(FUNCIONARIO)));
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

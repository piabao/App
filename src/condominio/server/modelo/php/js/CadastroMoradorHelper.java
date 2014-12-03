package condominio.server.modelo.php.js;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import condominio.server.modelo.CADASTRO_MORADOR;
import netscape.javascript.JSObject;

public class CadastroMoradorHelper {

	public static final String MORADORES = "moradores";
	public static final String CPF = "CPF";
	public static final String EMAIL = "EMAIL";
	public static final String ID = "ID";
	public static final String IDENTIFICADOR = "IDENTIFICADOR";
	public static final String NOME = "NOME";
	public static final String TIPO = "TIPO_MORADOR";
	public static final String VAGA = "VAGA";
	public static final String OBSERVACAO = "OBSERVACAO";
	private static final String ROWS = "rows";

	public static List<CADASTRO_MORADOR> toMoradorList(JSObject objetos) {		
		JSObject rows = (JSObject) objetos.getMember(ROWS);
		
		List<CADASTRO_MORADOR> moradores = new ArrayList<CADASTRO_MORADOR>();
		int i = 0;
		while (!rows.getSlot(i).equals("undefined")) {
			JSObject morador = (JSObject) rows.getSlot(i);
			CADASTRO_MORADOR cadastro = new CADASTRO_MORADOR();
			cadastro.setCpf((String)morador.getMember(CPF));
			cadastro.setEmail((String)morador.getMember(EMAIL));
			cadastro.setId(convertToLong(morador, ID));
			cadastro.setIdentificador((String)morador.getMember(IDENTIFICADOR));
			cadastro.setNome((String)morador.getMember(NOME));
			cadastro.setTipo_morador(convertToLong(morador, TIPO));
			cadastro.setVaga((String)morador.getMember(VAGA));
			moradores.add(cadastro);
			i++;
		}
		return moradores;
	}

	public static List<CADASTRO_MORADOR> toMoradorList(String json) {
		List<CADASTRO_MORADOR> moradores = new ArrayList<CADASTRO_MORADOR>();
		try {
			JSONArray jsonArray = new JSONArray(json);			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject morador = jsonArray.getJSONObject(i);
				CADASTRO_MORADOR cadastro = new CADASTRO_MORADOR();
				cadastro.setCpf(verify(morador.get(CPF)));
				cadastro.setEmail(verify(morador.get(EMAIL)));
				cadastro.setId(convertToLong(morador.get(ID)));
				cadastro.setIdentificador(verify(morador.get(IDENTIFICADOR)));
				cadastro.setNome(verify(morador.get(NOME)));
				cadastro.setObservacao(verify(morador.get(OBSERVACAO)));
				cadastro.setTipo_morador(convertToLong(morador.get(TIPO)));
				cadastro.setVaga(verify(morador.get(VAGA)));
				moradores.add(cadastro);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return moradores;	
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
	
	private static Long convertToLong(JSObject morador, String chave) {
		if(morador.getMember(chave).equals("undefined")){
			return null;
		}
		return Long.parseLong(morador.getMember(chave).toString());
	}

}

package condominio.server.modelo.php.js;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import condominio.server.modelo.ANIMAIS;
import condominio.server.modelo.CADASTRO_MORADOR;
import condominio.server.modelo.HISTORICO_ACESSO;
import netscape.javascript.JSObject;

public class HistoricoAcessoHelper {

	public static final String ID_VISITANTE = "IDVISITANTE";
	public static final String ID_VEICULO = "IDVEICULO";
	public static final String DATA_ENTRADA = "DATAENTRADA";
	public static final String DATA_SAIDA = "DATASAIDA";
	public static final String CASA = "CASA";
	public static final String RESPONSAVEL = "RESPONSAVEL";
	private static final String ID = "ID";

	public static List<HISTORICO_ACESSO> toHistoricoAcessoList(String json) {
		List<HISTORICO_ACESSO> values = new ArrayList<HISTORICO_ACESSO>();
		try {
			JSONArray jsonArray = new JSONArray(json);			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject valor = jsonArray.getJSONObject(i);
				HISTORICO_ACESSO acesso = new HISTORICO_ACESSO();
				acesso.setIdVisitante(convertToLong(valor.get(ID_VISITANTE)));
				acesso.setIdVeiculo(convertToLong(valor.get(ID_VEICULO)));
				acesso.setId(convertToLong(valor.get(ID)));
				acesso.setDataEntrada(convertToLong(valor.get(DATA_ENTRADA)));
				acesso.setDataSaida(convertToLong(valor.get(DATA_SAIDA)));
				acesso.setCasa(verify(valor.get(CASA)));
				acesso.setResponsavel(verify(valor.get(RESPONSAVEL)));
				values.add(acesso);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return values;	
	}

	private static String verify(Object valor) {
		if(valor == null || valor.equals(null)){
			return "";
		}
		return valor.toString();
	}

	private static Long convertToLong(Object chave) {
		if(chave == null || chave.equals(null)){
			return null;
		}
		return Long.parseLong(chave.toString());
	}

}

package condominio.server.modelo.php.js;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import condominio.server.modelo.ANIMAIS;
import condominio.server.modelo.CADASTRO_MORADOR;
import condominio.server.modelo.FUNCIONARIOS;
import netscape.javascript.JSObject;

public class FuncionariosHelper {

	public static final String ID = "ID";
	public static final String NOME = "NOME";
	public static final String ENDERECO = "ENDERECO";
	public static final String BAIRRO = "BAIRRO";
	public static final String CEP = "CEP";
	private static final String CIDADE = "CIDADE";
	public static final String UF = "UF";
	private static final String TELEFONE = "TELEFONE";
	public static final String CELULAR = "CELULAR";
	private static final String EMAIL = "EMAIL";
	public static final String FUNCAO = "FUNCAO";
	private static final String CARGAHORARIA = "CARGAHORARIA";
	private static final String DATAADMICAO = "DATAADMICAO";

	public static List<FUNCIONARIOS> toFuncionariosList(String json) {
		List<FUNCIONARIOS> values = new ArrayList<FUNCIONARIOS>();
		try {
			JSONArray jsonArray = new JSONArray(json);			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject funcionarios = jsonArray.getJSONObject(i);
				FUNCIONARIOS cadastro = new FUNCIONARIOS();
				cadastro.setBairro(verify(funcionarios.get(BAIRRO)));
				cadastro.setCargaHoraria(verify(funcionarios.get(CARGAHORARIA)));
				cadastro.setId(convertToLong(funcionarios.get(ID)));
				cadastro.setNome(verify(funcionarios.get(NOME)));
				cadastro.setCelular(verify(funcionarios.get(CELULAR)));
				cadastro.setCep(verify(funcionarios.get(CEP)));
				cadastro.setCidade(verify(funcionarios.get(CIDADE)));
				cadastro.setDataAdmicao(convertToLong(funcionarios.get(DATAADMICAO)));
				cadastro.setEmail(verify(funcionarios.get(EMAIL)));
				cadastro.setEndereco(verify(funcionarios.get(ENDERECO)));
				cadastro.setFuncao(verify(funcionarios.get(FUNCAO)));
				cadastro.setTelefone(verify(funcionarios.get(TELEFONE)));
				cadastro.setUf(verify(funcionarios.get(UF)));
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

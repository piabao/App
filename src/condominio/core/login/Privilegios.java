package condominio.core.login;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import condominio.server.modelo.PRIVILEGIOS;

public class Privilegios {
	
	public static final String DASH = "-";
	
	public static final String VISUALIZA = "Visualiza";	
	public static final String REMOVE = "Remove";	
	public static final String EDITA = "Edita";	
	
	public static final String CADASTRO = "Cadastro";
	public static final String BUSCA = "Busca";
	public static final String ACESSO = "Acesso";
	public static final String PERMISSOES = "Permissoes";
	public static final String RELATORIOS = "Relatorios";
	public static final String FUNCIONARIOS = "Funcionarios";
	
	public static final String VISUALIZA_CADASTRO = VISUALIZA+DASH+CADASTRO;
	public static final String REMOVE_CADASTRO = REMOVE+DASH+CADASTRO;
	public static final String EDITA_CADASTRO = EDITA+DASH+CADASTRO;
	public static final String VISUALIZA_BUSCA = VISUALIZA+DASH+BUSCA;
	public static final String VISUALIZA_ACESSO = VISUALIZA+DASH+ACESSO;
	public static final String REMOVE_ACESSO = REMOVE+DASH+ACESSO;
	public static final String EDITA_ACESSO = EDITA+DASH+ACESSO;
	public static final String VISUALIZA_PERMISSOES = VISUALIZA+DASH+PERMISSOES;
	public static final String REMOVE_PERMISSOES = REMOVE+DASH+PERMISSOES;
	public static final String EDITA_PERMISSOES = EDITA+DASH+PERMISSOES;
	public static final String VISUALIZA_FUNCIONARIOS = VISUALIZA+DASH+FUNCIONARIOS;
	public static final String EDITA_FUNCIONARIOS = EDITA+DASH+FUNCIONARIOS;
	public static final String VISUALIZA_RELATORIOS = VISUALIZA+DASH+RELATORIOS;
	public static final String EDITA_RELATORIOS = EDITA+DASH+RELATORIOS;

	
	public static String usuario;
	private static Map<String, Boolean> privilegios = new HashMap<String, Boolean>();
	
	public static String encrypt(String original){
    	try {
    	MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
		
    	byte messageDigest[] = algorithm.digest(original.getBytes("UTF-8"));
    	 
    	StringBuilder hexString = new StringBuilder();
    	for (byte b : messageDigest) {
    	  hexString.append(String.format("%02X", 0xFF & b));
    	}
    	String senha = hexString.toString();
    	return senha;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return original;
    }

	public static void setPermissoes(List<PRIVILEGIOS> permissoes) {
		for (PRIVILEGIOS prv : permissoes) {
			privilegios.put(prv.getDescricao(), prv.hasPermissao());
		}
	}
	
	public static Map<String, Boolean> getPermissoes() {
		return privilegios;
	}
}

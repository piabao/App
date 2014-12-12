package condominio.server.modelo.php.dao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLEncoder;

import javafx.util.Callback;

public class RequestFactory {
	
	private String url;
	private String[] args;
	private String authUser = "marlon.harnisch";
	private String authPassword = "powerful1914";
	
	public static String carregarUrl = "http://www.moradaspalhoca2.com/morp2/carregarObjs.php";
	public static String salvarUrl = "http://www.moradaspalhoca2.com/morp2/salvarObj.php";
	public static String editarUrl = "http://www.moradaspalhoca2.com/morp2/editarObj.php";
	public static String removerUrl = "http://www.moradaspalhoca2.com/morp2/removerObj.php";
	
	public RequestFactory(String url, String... args){
		this.url = url;
		this.args = args;
		
		createProxy();
	}
	
	private void createProxy() {
		System.setProperty("http.proxyHost", "proxy.digitro.com.br");
		System.setProperty("http.proxyPort", "3128");
		System.setProperty("http.proxyUser", authUser);
		System.setProperty("http.proxyPassword", authPassword);
		Authenticator.setDefault(
				  new Authenticator() {
				    public PasswordAuthentication getPasswordAuthentication() {
				      return new PasswordAuthentication(authUser, authPassword.toCharArray());
				    }
				  }
				);
	}

	public String doPost(){
		StringBuffer response = new StringBuffer();		
		return doRequest(response);
	}

	private String doRequest(StringBuffer response) {
		try {
			URL obj = new URL(url);
		    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		    con.setRequestMethod("POST");
		    
		    StringBuffer urlParameters = new StringBuffer();
		    for (int i = 0; i < args.length; i++) {
				if(i > 0){
					urlParameters.append("&");
				}
				urlParameters.append("args"+i+"="+URLEncoder.encode(args[i], "UTF-8"));
			}
	        
	        // Send post request
	        con.setDoOutput(true);
	        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	        wr.writeBytes(urlParameters.toString());
	        wr.flush();
	        wr.close();	        
	        
	        //Get Result
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        
	        while ((inputLine = in.readLine()) != null) {
	        	response.append(inputLine);
	        }
	        in.close();
	        return response.toString();
	        
		} catch (IOException e) {
			System.out.println("Erro na Requisicao : " + e.getMessage());
		}
		return "";
	}

	public void doPost(final Callback<String, Void> callback) {
//		Runnable runnable = new Runnable() {
//			
//			@Override
//			public void run() {
//				try{
//				StringBuffer response = new StringBuffer();
//				String doRequest = doRequest(response);
//				callback.call(doRequest);
//				}finally{
//					try {
//						this.finalize();
//					} catch (Throwable e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		};
//		Thread thread = new Thread(runnable);
//		thread.start();
	}

}

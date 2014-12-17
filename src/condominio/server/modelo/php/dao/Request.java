package condominio.server.modelo.php.dao;

import java.util.concurrent.Callable;

public class Request implements Callable<Response>{
	private RequestFactory rf;
	
	public Request(RequestFactory rf) {
        this.rf = rf;
    }

	@Override
	public Response call() throws Exception {
		return new Response(rf.doPost());
	}

}

package condominio.core.acesso.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import condominio.core.portaria.modelo.RelatorioCadastroMorador;
import condominio.server.modelo.CADASTRO_MORADOR;
import condominio.server.modelo.HISTORICO_ACESSO;

public class HistoricoAcessoModel {
	
	private String data;
	private String hora;
	private String casa;
	
	public HistoricoAcessoModel(){
		
	}

	public static ObservableList<HistoricoAcessoModel> createTableModel(List<HISTORICO_ACESSO> acesso){
		ObservableList<HistoricoAcessoModel> model = FXCollections.observableList(new ArrayList<HistoricoAcessoModel>());
		for (HISTORICO_ACESSO cadastro : acesso) {
			HistoricoAcessoModel historico = new HistoricoAcessoModel();
			Calendar cal = new GregorianCalendar();
			cal.setTime(new Date(cadastro.getDataEntrada()));
			historico.setCasa(cadastro.getCasa());
			historico.setHora(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));
			historico.setData(cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR));
			model.add(historico);
		}
		return model;
	}

	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}


	public String getHora() {
		return hora;
	}


	public void setHora(String hora) {
		this.hora = hora;
	}


	public String getCasa() {
		return casa;
	}


	public void setCasa(String casa) {
		this.casa = casa;
	}	

}

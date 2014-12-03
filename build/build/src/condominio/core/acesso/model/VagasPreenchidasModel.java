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

public class VagasPreenchidasModel {
	
	private String vaga;
	private String data;
	private String casa;
	private String nome;
	private String placa;
	private Long idHistorico;
	
	public Long getIdHistorico() {
		return idHistorico;
	}

	public void setIdHistorico(Long idHistorico) {
		this.idHistorico = idHistorico;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public VagasPreenchidasModel(){
		
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getVaga() {
		return vaga;
	}

	public void setVaga(String vaga) {
		this.vaga = vaga;
	}

	public String getCasa() {
		return casa;
	}

	public void setCasa(String casa) {
		this.casa = casa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public static List<VagasPreenchidasModel> create(List<Object[]> findStillInside) {
		List<VagasPreenchidasModel> model = new ArrayList<VagasPreenchidasModel>();
		for (Object[] object : findStillInside) {
			VagasPreenchidasModel vagas = new VagasPreenchidasModel();
			Calendar cal = new GregorianCalendar();
			cal.setTime(new Date((long) object[1]));
			vagas.setCasa(object[0].toString());
			vagas.setNome(object[3].toString());
			vagas.setVaga(object[2].toString());
			vagas.setPlaca(object[4].toString());
			vagas.setIdHistorico((Long) object[5]);
			String hora =  cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE);
			vagas.setData(cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR)+ " "+hora);
			
			model.add(vagas);
		}
		return model;
	}

}

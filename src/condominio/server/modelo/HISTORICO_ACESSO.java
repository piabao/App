package condominio.server.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HISTORICO_ACESSO {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long idVisitante;
    private Long idVeiculo;
    private Long dataEntrada;
    private Long dataSaida;
    private String casa;
    private String responsavel;
    public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	private String vaga;
	 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdVeiculo() {
		return idVeiculo;
	}

	public void setIdVeiculo(Long idVeiculo) {
		this.idVeiculo = idVeiculo;
	}

	public String getVaga() {
		return vaga;
	}

	public void setVaga(String vaga) {
		this.vaga = vaga;
	}

	public Long getIdVisitante() {
		return idVisitante;
	}

	public void setIdVisitante(Long idVisitante) {
		this.idVisitante = idVisitante;
	}

	public Long getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Long dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Long getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Long dataSaida) {
		this.dataSaida = dataSaida;
	}

	public String getCasa() {
		return casa;
	}

	public void setCasa(String casa) {
		this.casa = casa;
	}

	@Override
	    public int hashCode() {
	        int hash = 0;
	        hash += (id != null ? id.hashCode() : 0);
	        return hash;
	    }

	    @Override
	    public boolean equals(Object object) {
	        // TODO: Warning - this method won't work in the case the id fields are not set
	        if (!(object instanceof HISTORICO_ACESSO)) {
	            return false;
	        }
	        HISTORICO_ACESSO other = (HISTORICO_ACESSO) object;
	        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return casa + " - " + new Date(dataEntrada);
	    }

		public String getAtribNames() {
			return "id, idVisitante, idVeiculo, dataEntrada, dataSaida, casa, responsavel".toUpperCase();
		}

}
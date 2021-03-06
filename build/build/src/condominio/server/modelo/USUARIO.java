package condominio.server.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Marlon Harnisch
 */
@Entity
@Table(name = "USUARIO")
public class USUARIO implements Serializable{
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String user;
    private String senha;
    @OneToOne(cascade=CascadeType.ALL)
    private FUNCIONARIOS funcionario;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<PRIVILEGIOS> privilegios;
    
	public List<PRIVILEGIOS> getPrivilegios() {
		return privilegios;
	}
	
	public FUNCIONARIOS getFuncionario() {
		return funcionario;
	}
	
	public void setFuncionario(FUNCIONARIOS funcionarios) {
		this.funcionario = funcionarios;
	}
	
	public void setPrivilegios(List<PRIVILEGIOS> privilegios) {
		this.privilegios = privilegios;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsuario() {
		return user;
	}
	public void setUsuario(String usuario) {
		this.user = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
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
	        if (!(object instanceof USUARIO)) {
	            return false;
	        }
	        USUARIO other = (USUARIO) object;
	        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return user;
	    }

}

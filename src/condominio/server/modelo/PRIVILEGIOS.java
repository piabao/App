package condominio.server.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
*
* @author Marlon Harnisch
*/
@Entity
@Table(name = "PRIVILEGIOS")
public class PRIVILEGIOS implements Serializable{
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String descricao;
    private boolean permissao;
    @ManyToOne
    @JoinColumn(name="IDUSUARIO")
    private USUARIO usuario;
    
	public USUARIO getUsuario() {
		return usuario;
	}
	public void setUsuario(USUARIO usuario) {
		this.usuario = usuario;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public boolean hasPermissao() {
		return permissao;
	}
	public void setPermissao(boolean permissao) {
		this.permissao = permissao;
	}
    
    public String getAtribNames() {
		return "id, descricao, permissao".toUpperCase();
	}
}

package condominio.core.portaria.modelo;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import condominio.core.portaria.PortariaCrud;
import condominio.server.modelo.CADASTRO_MORADOR;
import condominio.server.modelo.dao.CadastroMoradorJpaController;

public class RelatorioCadastroMorador {
	
	private String identificador;
	private String nome;
	private String tipo;
	private String CPF;
	private String telefones;
	private String veiculos;
	private CADASTRO_MORADOR cadastro;
	
	private static PortariaCrud pCrud = new PortariaCrud(); 
	
	public RelatorioCadastroMorador(){}
	
	public static ObservableList<RelatorioCadastroMorador> createTableModel(List<CADASTRO_MORADOR> cad){
		ObservableList<RelatorioCadastroMorador> model = FXCollections.observableList(new ArrayList<RelatorioCadastroMorador>());
		for (CADASTRO_MORADOR cadastro : cad) {
			RelatorioCadastroMorador rcm = new RelatorioCadastroMorador();
			rcm.identificador = cadastro.getIdentificador();
			rcm.CPF = cadastro.getCpf();
			rcm.nome = cadastro.getNome();
			rcm.tipo = pCrud.getTipoMorador(cadastro.getTipo_morador()).getDescricao();
			rcm.telefones = pCrud.getTelefonesByIdMorador(cadastro.getId()).toString().replace("[", "").replace("]", "");
			rcm.veiculos = pCrud.getVeiculosByMorador(cadastro.getIdentificador()).toString().replace("[", "").replace("]", "");
			rcm.cadastro = cadastro;
			model.add(rcm);
		}
		
		return model;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public CADASTRO_MORADOR getCadastro() {
		return cadastro;
	}

	public void setCadastro(CADASTRO_MORADOR cadastro) {
		this.cadastro = cadastro;
	}

	public String getTelefones() {
		return telefones;
	}

	public void setTelefones(String telefones) {
		this.telefones = telefones;
	}

	public String getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(String veiculos) {
		this.veiculos = veiculos;
	}

}

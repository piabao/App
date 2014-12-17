/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package condominio.core.administrativo.relatorios;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.text.TabExpander;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import condominio.Condominio;
import condominio.core.administrativo.funcionarios.FuncionariosCrud;
import condominio.core.portaria.PortariaCrud;
import condominio.core.portaria.modelo.RelatorioCadastroMorador;
import condominio.core.utils.print.FuncionariosPrinter;
import condominio.core.utils.print.Printer;
import condominio.server.modelo.CADASTRO_MORADOR;

/**
 *
 * @author Marlon Harnisch
 */
public class RelatorioCadastroFxController implements Initializable{
	
	@FXML
    Button teste;
	@FXML
	AnchorPane relat;
	@FXML
	TableView<RelatorioCadastroMorador> listagem;
	@FXML
	ComboBox<String> relatorios;
	@FXML
	ComboBox<String> filtros;
	@FXML
	TextField busca;
	@FXML
	TableColumn identificador;
	@FXML
	TableColumn nome;
	@FXML
	TableColumn tipo;
	@FXML
	TableColumn CPF;
	@FXML
	TableColumn telefones;
	@FXML
	TableColumn automoveis;
	
    private PortariaCrud pCrud = new PortariaCrud();
    private FuncionariosCrud fCrud = new FuncionariosCrud();
	private Condominio main;
	protected FuncionariosGrid funcGrid;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
    	initTable();
    	initFilter();
    }
     
    private void initFilter() {
    	RelatorioFiltro filtro = new RelatorioFiltro(relatorios, filtros);
    	filtro.init(new Callback<String, Void>() {
			
			@Override
			public Void call(String param) {
				if(param.equals(RelatorioFiltro.FUNCIONARIOS)){
					funcGrid = new FuncionariosGrid();
					relat.getChildren().clear();
					relat.getChildren().add(funcGrid);
				}
				if(param.equals(RelatorioFiltro.CAD_MORADORES)){
					relat.getChildren().clear();
					relat.getChildren().add(listagem);
				}
				return null;
			}
		});
	}

	private void initTable() {
    	identificador.setCellValueFactory(new PropertyValueFactory<RelatorioCadastroMorador,String>("identificador"));
        nome.setCellValueFactory(new PropertyValueFactory<RelatorioCadastroMorador,String>("nome"));
        tipo.setCellValueFactory(new PropertyValueFactory<RelatorioCadastroMorador,String>("tipo"));
        CPF.setCellValueFactory(new PropertyValueFactory<RelatorioCadastroMorador,String>("CPF"));
        telefones.setCellValueFactory(new PropertyValueFactory<RelatorioCadastroMorador,String>("telefones"));
        automoveis.setCellValueFactory(new PropertyValueFactory<RelatorioCadastroMorador,String>("veiculos"));		
	}

	private void carregarListagem(List<CADASTRO_MORADOR> allMoradores) {    	   	
    	ObservableList<RelatorioCadastroMorador> tableModel = RelatorioCadastroMorador.createTableModel(allMoradores);
    	    
		listagem.setItems(tableModel);		
	}

	public void atualizarRelatorio(ActionEvent evt){
		if(relatorios.getSelectionModel().getSelectedItem().equals(RelatorioFiltro.FUNCIONARIOS)){
			funcGrid.setItems(FXCollections.observableList(fCrud.buscarTodosFuncionarios()));
			return;
		}
		
		if(filtros.getSelectionModel().getSelectedIndex() == 0){
			carregarListagem(pCrud.searchWhereMorador("IDENTIFICADOR like '%"+busca.getText()+"%'"));			
		}else if(filtros.getSelectionModel().getSelectedIndex() == 1){
			carregarListagem(pCrud.searchWhereMorador("NOME like '%"+busca.getText()+"%'"));
		}else if(filtros.getSelectionModel().getSelectedIndex() == 2){
			carregarListagem(pCrud.searchWhereMorador("TIPO_MORADOR = (SELECT ID FROM tipo_morador WHERE DESCRICAO like '%"+busca.getText()+"%')"));
		}else {
			carregarListagem(pCrud.getAllMoradores());	
		}
    }
	
	public void limpar(ActionEvent ev){
		filtros.getSelectionModel().select(-1);
		busca.setText("");
		listagem.setItems(FXCollections.observableList(new ArrayList<RelatorioCadastroMorador>()));
	}
	
	public void imprimir(ActionEvent ev){
		if(relatorios.getSelectionModel().getSelectedItem().equals(RelatorioFiltro.FUNCIONARIOS)){
			FuncionariosPrinter print = new FuncionariosPrinter();
			print.print(funcGrid.getSelectionModel().getSelectedItem().getId());
			return;
		}
		Printer printer = new Printer();
		printer.print(listagem.getSelectionModel().getSelectedItem().getIdentificador());
	}
	
	public void editar(ActionEvent ev){
		if(relatorios.getSelectionModel().getSelectedItem().equals(RelatorioFiltro.FUNCIONARIOS)){
			main.funcionarios.values().iterator().next().editar(funcGrid.getSelectionModel().getSelectedItem());
			limpar(ev);
			main.relatorios.keySet().iterator().next().setVisible(false);
			main.funcionarios.keySet().iterator().next().setVisible(true);
			return;
		}
		RelatorioCadastroMorador selectedItem = listagem.getSelectionModel().getSelectedItem();
		if(selectedItem == null){
			return;
		}
		main.cadastro.values().iterator().next().editar(selectedItem.getCadastro());
    	main.relatorios.keySet().iterator().next().setVisible(false);
    	main.cadastro.keySet().iterator().next().setVisible(true);
    	limpar(ev);
	}
	
    
	public void setApp(Condominio main) {
        this.main = main;       
        //carregarListagem(pCrud.getAllMoradores());
    	relatorios.getSelectionModel().select(0);
    }    
}

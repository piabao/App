/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package condominio.core.administrativo.permissoes;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.CheckBoxTreeCellBuilder;
import javafx.util.Callback;
import condominio.Condominio;
import condominio.core.administrativo.UsuarioCrud;
import condominio.core.administrativo.funcionarios.FuncionariosCrud;
import condominio.core.login.Privilegios;
import condominio.server.modelo.FUNCIONARIOS;
import condominio.server.modelo.PRIVILEGIOS;
import condominio.server.modelo.USUARIO;

/**
 *
 * @author Marlon Harnisch
 */
public class PrivilegiosFxController implements Initializable{
	
	@FXML
	TextField usuario;
    @FXML
    PasswordField senha;
    @FXML
    PasswordField confirmaSenha;
    @FXML
    Button cancelar;
    @FXML
    Button confirmar;
    @FXML
    ListView<USUARIO> listaUsuarios;
    @FXML
    Button remover;
    @FXML
    TreeView<String> privilegios;
    @FXML
    ListView<FUNCIONARIOS> funcionarios;
    
    private HashMap<String, Boolean> privilegiosMap = new HashMap<>();
	private Condominio main;
	private UsuarioCrud uCrud = new UsuarioCrud();
	private FuncionariosCrud fCrud = new FuncionariosCrud();
   
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
    	listarUsuarios();
    	setarArvore();
    	setFuncionariosList();
    }
    
    private void setarArvore() {
    	CheckBoxTreeItem<String> rootItem = new CheckBoxTreeItem<String>("PRIVIL�GIOS");
    	rootItem.getChildren().add(createDefaultItem(Privilegios.CADASTRO));
    	rootItem.getChildren().add(createDefaultItem(Privilegios.ACESSO));
    	rootItem.getChildren().add(createDefaultItem(Privilegios.PERMISSOES));
    	rootItem.getChildren().add(createDefaultItem(Privilegios.RELATORIOS));
    	rootItem.getChildren().add(createDefaultItem(Privilegios.FUNCIONARIOS));
    	rootItem.setExpanded(true);
    	privilegios.setRoot(rootItem);
		privilegios.setCellFactory(CheckBoxTreeCell.<String>forTreeView());		
	}
    
    private void setFuncionariosList() {
		List<FUNCIONARIOS> funcionarios = fCrud .buscarTodosFuncionarios();
		this.funcionarios.setItems(FXCollections.observableList(funcionarios));
	}
    
    

	private CheckBoxTreeItem<String> createDefaultItem(String item) {
		CheckBoxTreeItem<String> checkBoxTreeItem = new CheckBoxTreeItem<String>(item);
		checkBoxTreeItem.getChildren().add(new CheckBoxTreeItem<String>(Privilegios.VISUALIZA));
		checkBoxTreeItem.getChildren().add(new CheckBoxTreeItem<String>(Privilegios.EDITA));
		return checkBoxTreeItem;
	}

	private void listarUsuarios() {
		List<USUARIO> usuarios = uCrud.listaUsuarios();
		listaUsuarios.setItems(FXCollections.observableArrayList(usuarios));
	}

	private void populateMap() {
		ObservableList<TreeItem<String>> children = privilegios.getRoot().getChildren();
		for (TreeItem<String> treeItem : children) {
			String nomeAplicacao = treeItem.getValue();
			for (TreeItem<String> item : treeItem.getChildren()) {
				CheckBoxTreeItem<String> checkItem = (CheckBoxTreeItem<String>) item;
				privilegiosMap.put(item.getValue()+Privilegios.DASH+nomeAplicacao, checkItem.isSelected());
			}
		}		
	}

	public void cadastrarUsuario(ActionEvent ev){
    	if(!confirmaSenha()){
    		Dialogs.create()
            .owner(null)
            .title("Cadastro")
            .masthead("Validador de Senha")
            .message("Senhas n�o s�o id�nticas!")
            .showInformation();
    		return;
    	}
    	populateMap();
    	FUNCIONARIOS selectFuncionarios = funcionarios.getSelectionModel().getSelectedItem();
    	if(selectFuncionarios == null){
    		Dialogs.create()
            .owner(null)
            .title("Cadastro")
            .masthead("Erro ao salvar")
            .message("Selecione um Funcion�rio")
            .showInformation();
    		return;
    	}
    	USUARIO user = new USUARIO();
    	user.setFuncionario(selectFuncionarios);
    	user.setUsuario(usuario.getText());
    	user.setSenha(senha.getText());    	
    	
    	List<PRIVILEGIOS> prvList = new ArrayList<PRIVILEGIOS>();
    	for (String permissao : privilegiosMap.keySet()) {
    		PRIVILEGIOS privilegios = new PRIVILEGIOS();  
    		privilegios.setDescricao(permissao);
    		privilegios.setPermissao(privilegiosMap.get(permissao));
			prvList.add(privilegios);
		}
    	
    	user.setPrivilegios(prvList);
    	main.carregando(true);
    	uCrud.create(user, new Callback<USUARIO, Void>() {
			
			@Override
			public Void call(USUARIO param) {
				Platform.runLater(salvoComSucesso(param));
				return null;
			}

			private Runnable salvoComSucesso(USUARIO param) {
				return new Runnable() {
					
					@Override
					public void run() {
						main.carregando(false);
						if(param == null){
							return;
						}
						Dialogs.create()
			            .owner(null)
			            .title("Cadastro")
			            .masthead("Usuario "+ user.getUsuario())
			            .message("Cadastro realizado com sucesso!")
			            .showInformation();
			       		listarUsuarios();
			    		limpar();  
					}
				};
			}
		});    	
    }
	
	public void cancelarCadastro(ActionEvent ev){
		limpar();
	}
	
	public void removerUsuario(){
		USUARIO user = listaUsuarios.getSelectionModel().getSelectedItem();
		Action response = Dialogs.create().owner(null).title("Confirmar")
		        .masthead("Remover Usu�rio")
		        .message("Voc� tem certeza que deseja remover?")
		        .showConfirm();
		if(response == Dialog.Actions.YES){
			uCrud.removerUsuario(user);
			listarUsuarios();			
		}
	}
	
	private void limpar(){
		ObservableList<TreeItem<String>> children = privilegios.getRoot().getChildren();
		for (TreeItem<String> treeItem : children) {
			for (TreeItem<String> item : treeItem.getChildren()) {
				CheckBoxTreeItem<String> checkItem = (CheckBoxTreeItem<String>) item;
				checkItem.setSelected(false);	
				checkItem.setExpanded(false);
			}
		}
		
		usuario.setText("");
		senha.setText("");
		confirmaSenha.setText("");
	}

    private boolean confirmaSenha() {		
		return senha.getText().equals(confirmaSenha.getText());
	}
    
	public void setApp(Condominio main) {
        this.main = main;       
    }    
}

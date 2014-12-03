/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package condominio.core;

import condominio.Condominio;
import condominio.core.login.Privilegios;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Marlon Harnisch
 */
public class CondominioFxController implements Initializable{
    
    @FXML
    MenuItem portariaBusca;
    @FXML
    MenuItem portariaCadastro;
    @FXML
    MenuItem permisoes;
    @FXML
    MenuItem controleAcesso;
    @FXML
    MenuItem funcionariosMenu;
    @FXML
    MenuItem relatoriosMenu;
    @FXML
    Label bemVindo;
    @FXML
    Label sair;
    
    
    private AnchorPane busca;
    private AnchorPane cadastro;
    private AnchorPane permissoes;
    private AnchorPane relatorios;
    private AnchorPane acessoControle;
    private AnchorPane funcionarios;
    
    private Condominio main;

    @Override
    public void initialize(URL url, ResourceBundle rb) {} 
    
    private void configuraPermissoes() {
		Map<String, Boolean> prv = Privilegios.getPermissoes();
		if(!prv.get(Privilegios.VISUALIZA_CADASTRO)){
			portariaCadastro.setDisable(true);
		}
		if(!prv.get(Privilegios.VISUALIZA_PERMISSOES)){
			permisoes.setDisable(true);
		}
		if(!prv.get(Privilegios.VISUALIZA_ACESSO)){
			controleAcesso.setDisable(true);
		}
		if(!prv.get(Privilegios.VISUALIZA_FUNCIONARIOS)){
			funcionariosMenu.setDisable(true);
		}
		if(!prv.get(Privilegios.VISUALIZA_RELATORIOS)){
			relatoriosMenu.setDisable(true);
		}
	}

	public void AbrirPermissoes(ActionEvent e){
    	setVisible(permissoes);      
    }
	
	public void AbrirControleAcesso(ActionEvent e){
		main.controleAcesso.get(acessoControle).configuraPermissoes();
    	setVisible(acessoControle);      
    }
    
    public void AbrirBusca(ActionEvent e){
    	main.busca.get(busca).configuraPermissao();
    	setVisible(busca);
    }
    
    public void AbrirCadastro(ActionEvent e){
    	main.cadastro.get(cadastro).configuraPermissoes();
    	setVisible(cadastro);
    }
    
    public void AbrirRelatorios(ActionEvent e){
    	setVisible(relatorios);
    }
    
    public void AbrirFuncionarios(ActionEvent e){
    	setVisible(funcionarios);
    }
    
    
    public void sair(MouseEvent ev){
    	main.carregaLogin();
    }
    
    public void setVisible(AnchorPane pane){
    	cadastro.setVisible(false);
    	busca.setVisible(false);
    	permissoes.setVisible(false);
    	relatorios.setVisible(false);
    	acessoControle.setVisible(false);
    	funcionarios.setVisible(false);
    	
    	pane.setVisible(true);
    }

    public void setApp(Condominio condominio) {
        this.main = condominio;
        sair.setCursor(Cursor.HAND);
        bemVindo.setText("Bem Vindo "+ Privilegios.usuario);
        initComponents();
        configuraPermissoes();
    }

	private void initComponents() {
		this.busca = main.busca.keySet().iterator().next();
		this.cadastro = main.cadastro.keySet().iterator().next();
		this.permissoes = main.permissoes.keySet().iterator().next();
		this.relatorios = main.relatorios.keySet().iterator().next();
		this.acessoControle = main.controleAcesso.keySet().iterator().next();
		this.funcionarios = main.funcionarios.keySet().iterator().next();
		
		main.busca.get(busca).configuraPermissao();
		init(busca, true);
		init(funcionarios, false);
		init(acessoControle, false);
		init(relatorios, false);
		init(cadastro, false);
		init(permissoes, false);
	}
    
	private void init(AnchorPane page, boolean visible){
		page.setLayoutX(10);
		page.setLayoutY(40);
        main.getPane().getChildren().add(page);
        page.setVisible(visible);
	}
}

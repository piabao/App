/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package condominio;

import condominio.core.CondominioFxController;
import condominio.core.acesso.ControleAcessoFxController;
import condominio.core.administrativo.funcionarios.FuncionariosFxController;
import condominio.core.administrativo.permissoes.PrivilegiosFxController;
import condominio.core.administrativo.relatorios.RelatorioCadastroFxController;
import condominio.core.login.LoginController;
import condominio.core.portaria.PortariaFxController;
import condominio.core.portaria.busca.BuscaFxController;
import condominio.core.utils.CarregandoModal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author Marlon Harnish
 */
public class Condominio extends Application {
    
    private Stage stage;
    private FXMLLoader loader;
    private AnchorPane page;
    private CarregandoModal carregando;
    public Map<AnchorPane, PortariaFxController> cadastro = new HashMap<AnchorPane, PortariaFxController>();
    public Map<AnchorPane, RelatorioCadastroFxController> relatorios = new HashMap<AnchorPane, RelatorioCadastroFxController>();
	public Map<AnchorPane, BuscaFxController> busca = new HashMap<AnchorPane, BuscaFxController>();
	public Map<AnchorPane, PrivilegiosFxController> permissoes = new HashMap<AnchorPane, PrivilegiosFxController>();
	public Map<AnchorPane, ControleAcessoFxController> controleAcesso = new HashMap<AnchorPane, ControleAcessoFxController>();
	public Map<AnchorPane, FuncionariosFxController> funcionarios = new HashMap<AnchorPane, FuncionariosFxController>();;
    
	//293441
	
	
    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setTitle("Moradas Palhoça II");
    	primaryStage.setOpacity(0.3);
    	stage = primaryStage;
            carregarPermissoes();            
            carregarBusca();
            carregarCadastro();
            carregarRelatorios();
            carregarControleAcesso();
            carregarFuncionarios();
            //carregarPrimeiraPagina();
            carregaLogin();
            primaryStage.show();
    }
	private void carregarFuncionarios() {
		FXMLLoader fxmlLoader = loadFX("core/administrativo/funcionarios/Funcionarios.fxml");
    	AnchorPane acessControl = fxmlLoader.getRoot();
    	funcionarios.put(acessControl, (FuncionariosFxController)fxmlLoader.getController());
		((FuncionariosFxController) fxmlLoader.getController()).setApp(this); 
	}
	private void carregarControleAcesso() {
		FXMLLoader fxmlLoader = loadFX("core/acesso/ControleAcesso.fxml");
    	AnchorPane acessControl = fxmlLoader.getRoot();
    	controleAcesso.put(acessControl, (ControleAcessoFxController)fxmlLoader.getController());
		((ControleAcessoFxController) fxmlLoader.getController()).setApp(this); 
		
	}
	public void carregaLogin() {
		try {
            LoginController login = (LoginController) replaceSceneContent("core/login/Login.fxml");            
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Condominio.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

	/**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void carregando(boolean sim){
    	if(carregando == null){
    		Node window = stage.getScene().getRoot();
    		stage.centerOnScreen();
    		carregando = new CarregandoModal(window);    		
    	}
    	if(sim){
    		carregando.show();
    	}else{
    		carregando.hide();
    	}
    }

    public void carregarPrimeiraPagina() {
        try {
            CondominioFxController primeiraPg = (CondominioFxController) replaceSceneContent("core/Condominio.fxml");
            primeiraPg.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Condominio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void carregarPermissoes() {
    	FXMLLoader fxmlLoader = loadFX("core/administrativo/permissoes/Permissoes.fxml");
    	AnchorPane root = fxmlLoader.getRoot();
    	permissoes.put(root, (PrivilegiosFxController)fxmlLoader.getController());
		((PrivilegiosFxController) fxmlLoader.getController()).setApp(this);
	}
    private void carregarBusca() {
    	FXMLLoader fxmlLoader = loadFX("core/portaria/busca/Busca.fxml");
    	AnchorPane load = fxmlLoader.getRoot();
    	busca.put(load, (BuscaFxController)fxmlLoader.getController());
		((BuscaFxController) fxmlLoader.getController()).setApp(this);    	
	}
    
    private void carregarCadastro() {
    	FXMLLoader fxmlLoader = loadFX("core/portaria/PortariaCadastro.fxml");
    	AnchorPane cad = fxmlLoader.getRoot();
    	cadastro.put(cad, (PortariaFxController)fxmlLoader.getController());
		((PortariaFxController) fxmlLoader.getController()).setApp(this);        
    }
    
    private void carregarRelatorios() {
    	FXMLLoader fxmlLoader = loadFX("core/administrativo/relatorios/RelatorioCadastro.fxml");
    	AnchorPane relat = fxmlLoader.getRoot();
    	relatorios.put(relat, (RelatorioCadastroFxController)fxmlLoader.getController());
		((RelatorioCadastroFxController) fxmlLoader.getController()).setApp(this);        
    }
    
    private FXMLLoader loadFX(String fxml){
    	try {
    	 URL location = getClass().getResource(fxml);
    	 FXMLLoader fxmlLoader = new FXMLLoader();  
         fxmlLoader.setLocation(getClass().getResource(fxml));
         Parent root = (Parent)fxmlLoader.load(location.openStream());
         return fxmlLoader;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    private Initializable replaceSceneContent(String fxml) throws Exception {
        loader = new FXMLLoader();
        InputStream in = Condominio.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Condominio.class.getResource(fxml));        
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
        
        return (Initializable) loader.getController();
    }  

    public AnchorPane getPane() {
       return page; 
    }
	public Stage getStage() {
		return stage;
	}
 }

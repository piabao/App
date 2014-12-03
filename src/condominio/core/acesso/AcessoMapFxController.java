/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package condominio.core.acesso;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import condominio.Condominio;
import condominio.core.acesso.model.HistoricoAcessoModel;
import condominio.core.acesso.model.VagasPreenchidasModel;
import condominio.core.login.Privilegios;
import condominio.core.portaria.PortariaCrud;
import condominio.core.portaria.modelo.RelatorioCadastroMorador;
import condominio.server.modelo.ANIMAIS;
import condominio.server.modelo.CADASTRO_MORADOR;
import condominio.server.modelo.HISTORICO_ACESSO;
import condominio.server.modelo.TELEFONES;
import condominio.server.modelo.TIPO_MORADOR;
import condominio.server.modelo.VEICULOS;

/**
 *
 * @author Marlon Harnisch
 */
public class AcessoMapFxController implements Initializable{
    
    @FXML
    StackPane map;
    
    private AcessoCrud aCrud = new AcessoCrud();
	private ControleAcessoFxController main;
	private List<VagasPreenchidasModel> stillInside;
	
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
    	stillInside = aCrud.findStillInside();
    	
    	WebView web = new WebView();
    	final WebEngine webEngine = web.getEngine();
     	webEngine.load(getClass().getResource("map/Index.html").toString());
     	webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
			
			@Override
			public void handle(WebEvent<String> event) {
				Action response = Dialogs.create().title("Vagas Disponiveis").masthead("Seleção de Vaga").message("Deseja selecionar esta Vaga?").showConfirm();
				if(response == Dialog.Actions.YES){	
					main.setVaga(event.getData());	
					Stage stage = (Stage) map.getScene().getWindow();
					stage.close();					
				};
			}
		});
     	
     	webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				for (VagasPreenchidasModel acessos : stillInside) {
 	        		webEngine.executeScript("document.changeToBusy('"+acessos.getVaga()+"')");
 	   			}
			}
		});

     	map.getChildren().add(web);
     	
    }    

	public Stage getStage(){
    	return main.getStage();
    }

    public void setApp(ControleAcessoFxController controleAcessoFxController) {
        this.main = controleAcessoFxController; 
       
    }
    
}

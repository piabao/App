/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package condominio.core.acesso;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;

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
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.imageio.ImageIO;

import org.controlsfx.control.action.Action;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.AutoCompletionBinding.AutoCompletionEvent;
import org.controlsfx.control.textfield.AutoCompletionBinding.ISuggestionRequest;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import condominio.Condominio;
import condominio.core.acesso.model.HistoricoAcessoModel;
import condominio.core.acesso.model.VagasPreenchidasModel;
import condominio.core.login.Privilegios;
import condominio.core.portaria.PortariaCrud;
import condominio.core.portaria.VeiculosFxController;
import condominio.core.portaria.modelo.RelatorioCadastroMorador;
import condominio.core.utils.ComponenteBusca;
import condominio.core.utils.ComponenteBusca.BuscaRetorno;
import condominio.core.utils.ComponenteBusca.TipoBusca;
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
public class ControleAcessoFxController implements Initializable{
    
    @FXML
    TextField nome;
    @FXML
    TextField cpf;
    @FXML
    TextField casa;
    @FXML
    ComboBox<String> responsavel;
    @FXML
    TextField vaga;
    @FXML
    TextField modelo;
    @FXML
    ComboBox<String> placa;
    @FXML
    TextField cor;
    @FXML
    TextField observacao;
    
    @FXML
    Button limpar;
    @FXML
    Button entrar;
    
    @FXML
    TableView<HistoricoAcessoModel> historico;
    @FXML
    TableColumn data;
    @FXML
    TableColumn hora;
    @FXML
    TableColumn casaVisitada;
    
    @FXML
    TitledPane patio;
    @FXML
    TextField filtro;
    @FXML
    TableView<VagasPreenchidasModel> vagasPreenchidas;
    @FXML
    TableColumn vagaPlaca;
    @FXML
    TableColumn vagaVaga;
    @FXML
    TableColumn vagaNome;
    @FXML
    TableColumn vagaEntrada;
    @FXML
    Button sair;
    @FXML
    Hyperlink limparVagas;
    @FXML
    ListView<CADASTRO_MORADOR> resultado;
    
    
    private ObservableList<VagasPreenchidasModel> filteredData = FXCollections.observableArrayList();      
    private ObservableList<VagasPreenchidasModel> tableModel = FXCollections.observableArrayList();;
    private PortariaCrud pCrud = new PortariaCrud();
    private AcessoCrud aCrud = new AcessoCrud();
    private Map<Long, HISTORICO_ACESSO> telefonesMap = new HashMap<Long, HISTORICO_ACESSO>();
    private Condominio main;
	private List<VEICULOS> veiculos;
	private CADASTRO_MORADOR visitanteEditando;
	private boolean canClean = true;
	private List<CADASTRO_MORADOR> collection = new ArrayList<CADASTRO_MORADOR>();
	private  AutoCompletionTextFieldBinding<CADASTRO_MORADOR> autoComplete;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
    	autoComplete = (AutoCompletionTextFieldBinding<CADASTRO_MORADOR>) TextFields.bindAutoCompletion(nome, collection);
    	nome.textProperty().addListener(new ChangeListener<String>() {    	    

			@Override
    	    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
    	    	if(newValue.isEmpty()){
    	    		autoComplete.hidePopup();
    	    		return;
    	    	}
    	    	
    	    	collection = pCrud.searchVisitante(newValue);
    	    	if(collection.isEmpty()){
    	    		autoComplete.hidePopup();
    	    		return;
    	    	}
    	    	
    	    	autoComplete.setNewData(collection);    	    		
    	    	setAction();
			}
    	});
    	
    	
    	casa.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
    	    	if(casa.getText().length() > 3){
    	    		responsavel.setItems(FXCollections.observableList(createResponsavelList(casa.getText())));    	    		
    	    	}
    	    }
    	});
    	placa.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {                
            	selectPlate();
            }    
        });
    	filtro.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                updateFilteredData();
            }
        });
    	
    	tableModel.addListener(new ListChangeListener<VagasPreenchidasModel>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends VagasPreenchidasModel> change) {
                updateFilteredData();
            }
        });
    	initTable();
    	initVagasemUsoTable();
    	populateVagasemUsoGrid();
    }    
    
    protected void setAction() {
    	autoComplete.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<CADASTRO_MORADOR>>() {
			
			@Override
			public void handle(AutoCompletionEvent<CADASTRO_MORADOR> event) {
				acessar(event.getCompletion());														
			}
		});
	}

	private void initVagasemUsoTable() {
    	vagaEntrada.setCellValueFactory(new PropertyValueFactory<VagasPreenchidasModel,String>("data"));
        vagaNome.setCellValueFactory(new PropertyValueFactory<VagasPreenchidasModel,String>("nome"));
        vagaPlaca.setCellValueFactory(new PropertyValueFactory<VagasPreenchidasModel,String>("placa"));
        vagaVaga.setCellValueFactory(new PropertyValueFactory<VagasPreenchidasModel,String>("vaga"));
    }
    
    private void updateFilteredData() {
        filteredData.clear();

        for (VagasPreenchidasModel p : tableModel) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }
    
    private boolean matchesFilter(VagasPreenchidasModel vagas) {
        String filterString = filtro.getText();
        if (filterString == null || filterString.isEmpty()) {
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (vagas.getPlaca().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (vagas.getVaga().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (vagas.getNome().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<VagasPreenchidasModel, ?>> sortOrder = new ArrayList<>(vagasPreenchidas.getSortOrder());
        vagasPreenchidas.getSortOrder().clear();
        vagasPreenchidas.getSortOrder().addAll(sortOrder);
    }

	private void initTable() {
    	data.setCellValueFactory(new PropertyValueFactory<RelatorioCadastroMorador,String>("data"));
        hora.setCellValueFactory(new PropertyValueFactory<RelatorioCadastroMorador,String>("hora"));
        casaVisitada.setCellValueFactory(new PropertyValueFactory<RelatorioCadastroMorador,String>("casa"));		
	}
	
	private void populateHistoricoGrid() {
    	List<HISTORICO_ACESSO> findHistoricoAcesso = aCrud.findHistoricoAcesso(visitanteEditando.getId());
    	ObservableList<HistoricoAcessoModel> tableModel = HistoricoAcessoModel.createTableModel(findHistoricoAcesso);
	    
    	historico.setItems(tableModel);
	}
    
    public void setVaga(String vaga){
    	this.vaga.setText(vaga);
    }
    
    private void populateVagasemUsoGrid() {
     	List<VagasPreenchidasModel> findHistoricoAcesso = aCrud.findStillInside();
    	tableModel = FXCollections.observableList(findHistoricoAcesso);
    	filteredData.addAll(tableModel);
    	vagasPreenchidas.setItems(tableModel);
	}
    
    public void configuraPermissoes() {
		Map<String, Boolean> permissoes = Privilegios.getPermissoes();
    		sair.setDisable(!permissoes.get(Privilegios.EDITA_ACESSO));
    		entrar.setDisable(!permissoes.get(Privilegios.EDITA_ACESSO));
	}
    
    public void abrirMapa(MouseEvent ev){
    	((AcessoMapFxController)abrirFXML(ev, "AcessoMap.fxml", "Mapa Condomínio")).setApp(this);
    }

	
    public Initializable abrirFXML(MouseEvent ev, String fxml, String titulo){
        try {  
            URL location = getClass().getResource(fxml);
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = (Parent) fxmlLoader.load(location.openStream());
            
            Stage stage = new Stage();       
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)ev.getSource()).getScene().getWindow());  
            stage.show();
            return (Initializable) fxmlLoader.getController();
            
        } catch (IOException ex) {
            Logger.getLogger(ControleAcessoFxController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Initializable getLoader(FXMLLoader loader){
        return (Initializable) loader.getController();
    }    
    
    public void acessar(ActionEvent event) {
        CADASTRO_MORADOR visitante = new CADASTRO_MORADOR();
        VEICULOS veic = new VEICULOS();
        if(visitanteEditando!= null){
        	visitante.setId(visitanteEditando.getId());
        }
        visitante.setCpf(cpf.getText());
        //visitante.setIdentificador(casa.getText());
        visitante.setNome(nome.getText());
        visitante.setTipo_morador(TIPO_MORADOR.VISITANTE);
        if(observacao.getText() != null){
        	visitante.setObservacao(observacao.getText());        	
        }
        CADASTRO_MORADOR visitanteCadastrado = pCrud.cadastrarMorador(visitante);
        if(visitanteCadastrado == null){
        	Dialogs.create().title("Erro").masthead("Cadastro de visitante").message("Erro ao cadastrar visitante").showError();
        	return;
        }
        if(contemPlaca() == null){
        	veic.setCor(cor.getText());
        	veic.setPlaca(placa.getSelectionModel().getSelectedItem());
        	veic.setModelo(modelo.getText());
        	veic.setIdMorador(visitante.getIdentificador());
        	veic.setVisitante(visitante.getId());
        	veic = pCrud.cadastrarVeiculos(veic);
        }else{
        	veic = contemPlaca();
        }
        HISTORICO_ACESSO historico = new HISTORICO_ACESSO();
        historico.setIdVisitante(visitanteCadastrado.getId());
        historico.setCasa(casa.getText());
        historico.setDataEntrada(new Date().getTime());
        historico.setVaga(vaga.getText());
        historico.setResponsavel(responsavel.getSelectionModel().getSelectedItem());
        historico.setIdVeiculo(veic.getId());
        aCrud.createHistoricoAcesso(historico);
        Dialogs.create().title("Info").masthead("Cadastro de visitante").message("Acesso realizado com sucesso!").showInformation();
        populateVagasemUsoGrid();
        limpar(event);
    }
    
    public void sair(ActionEvent event) {
    	VagasPreenchidasModel selectedItem = vagasPreenchidas.getSelectionModel().getSelectedItem();
    	if(selectedItem == null){
    		return;
    	}
    	Action response = Dialogs.create().title("Confirmação").masthead("Liberar Vaga").message("Deseja remover o veiculo desta Vaga?").showConfirm();
		if(response == Dialog.Actions.YES){				
	    	HISTORICO_ACESSO acesso = aCrud.findHistorico(selectedItem.getIdHistorico());
	    	acesso.setDataSaida(new Date().getTime());
	    	
	    	aCrud.createHistoricoAcesso(acesso);
	    	populateVagasemUsoGrid();
		};
    }
    
    public void sairTodos(ActionEvent event) {
    	//TODO
    }
        
    private VEICULOS contemPlaca() {
    	if(veiculos == null){
    		return null;
    	}
		for (VEICULOS vei : veiculos) {
			if(placa.getSelectionModel().getSelectedItem().equals(vei.getPlaca())){
				return vei;
			}
		}
		return null;
	}

	public void limpar(ActionEvent ev) {
		if(!canClean){
			return;
		}
		vaga.setText("");
    	nome.setText("");
    	cpf.setText("");
    	casa.setText("");
    	responsavel.setItems(FXCollections.observableList(new ArrayList<String>()));
    	veiculos = null;
    	modelo.setText("");
    	placa.setItems(FXCollections.observableList(new ArrayList<String>()));
    	cor.setText(""); 
    	visitanteEditando = null;
    	historico.setItems(FXCollections.observableList(new ArrayList<HistoricoAcessoModel>()));
	}
    
    public void acessar(CADASTRO_MORADOR visitante){
    	if(visitante == null){
    		return;
    	}
    	if(!visitante.getTipo_morador().equals(TIPO_MORADOR.VISITANTE)){
    		limpar(null);
    		casa.setText(visitante.getIdentificador());
    		responsavel.getSelectionModel().select(visitante.getNome());
    		return;
    	}
    	
    	visitanteEditando = visitante;
    	nome.setText(visitante.getNome());
    	cpf.setText(visitante.getCpf());
    	veiculos = pCrud.getVeiculosByVisitante(visitante.getId());
    	placa.setItems(FXCollections.observableList(createPlateList()));
    	placa.getSelectionModel().selectFirst();
    	selectPlate();
    	populateHistoricoGrid();
    }

	private void selectPlate() {
		if(veiculos == null || veiculos.isEmpty()){
			modelo.setText("");
			cor.setText("");
			return;
		}
		for(VEICULOS vei : veiculos){
			if(!placa.getSelectionModel().getSelectedItem().equals(vei.getPlaca())){
				continue;
			}
			modelo.setText(vei.getModelo());
			cor.setText(vei.getCor());			
		}
	}

	private List<String> createResponsavelList(String responsavel) {
		List<CADASTRO_MORADOR> searchMorador = pCrud.searchMoradoresByID(responsavel);
		List<String> respons = new ArrayList<String>();
		for (CADASTRO_MORADOR cadastros : searchMorador) {
			respons.add(cadastros.getNome());
		}
		return respons;
	}

	private List<String> createPlateList() {
		List<String> plates = new ArrayList<String>();
		for(VEICULOS veic : veiculos){
			plates.add(veic.getPlaca());
		}
		return plates;
	}

	public Stage getStage(){
    	return main.getStage();
    }

    public void setApp(Condominio main) {
        this.main = main; 
    }
    
}

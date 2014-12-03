/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package condominio.core.administrativo.funcionarios;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.eclipse.persistence.internal.jpa.metadata.converters.TemporalMetadata;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import condominio.Condominio;
import condominio.core.administrativo.UsuarioCrud;
import condominio.core.login.Privilegios;
import condominio.core.utils.ComponenteModal;
import condominio.core.utils.ErroValidacaoNode;
import condominio.core.utils.InputMaskChecker;
import condominio.core.utils.MascaraDataUtil;
import condominio.server.modelo.FUNCIONARIOS;
import condominio.server.modelo.OCORRENCIAS;
import condominio.server.modelo.PRIVILEGIOS;
import condominio.server.modelo.USUARIO;

/**
 *
 * @author Marlon Harnisch
 */
public class FuncionariosFxController implements Initializable{
	
	@FXML
	TextField nome;
	@FXML
	TextField endereco;
	@FXML
	TextField bairro;
	@FXML
	TextField cidade;
	@FXML
	TextField cep;
	@FXML
	TextField uf;
	@FXML
	TextField telefone;
	@FXML
	TextField celular;
	@FXML
	TextField email;
	
	@FXML
	TextField funcao;
	@FXML
	TextField cargaHoraria;
	@FXML
	DatePicker dataAdmicao;
    @FXML
    Button cancelar;
    @FXML
    Button confirmar;
    @FXML
    ListView<FUNCIONARIOS> listaFuncionarios;
    @FXML
    ListView<OCORRENCIAS> ocorrencias;
    @FXML
    Button removerFuncionario;
    
    private FuncionariosCrud fCrud = new FuncionariosCrud();
	private Condominio main;
	private Button ok;
	private TextArea tx;
	private ComponenteModal ocorrencia;
	private FUNCIONARIOS funcionarioEmEdicao;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	getFuncionariosList();
    	initComponents();
    }
    
    private void initComponents() {
    	//MascaraDataUtil.mask(dataAdmicao);
	}

	private void getFuncionariosList() {
		List<FUNCIONARIOS> funcionarios = fCrud.buscarTodosFuncionarios();
		listaFuncionarios.setItems(FXCollections.observableList(funcionarios));
	}

	public void cadastrar(ActionEvent ev){
    	FUNCIONARIOS func = new FUNCIONARIOS();
    	if(funcionarioEmEdicao != null){
    		func.setId(funcionarioEmEdicao.getId());
    	}
    	func.setBairro(bairro.getText());
    	func.setCargaHoraria(cargaHoraria.getText());
    	func.setCelular(celular.getText());
    	func.setCep(cep.getText());
    	func.setCidade(cidade.getText());
    	if(dataAdmicao.getValue() != null){
    		Instant now = dataAdmicao.getValue().atStartOfDay().toInstant(ZoneOffset.UTC);
    		func.setDataAdmicao(now.toEpochMilli());    		
    	}
    	func.setEmail(email.getText());
    	func.setEndereco(endereco.getText());
    	func.setFuncao(funcao.getText());
    	func.setNome(nome.getText());
    	func.setTelefone(telefone.getText());
    	func.setUf(uf.getText());
    	func.setOcorrencias(ocorrencias.getItems());
    	    	
    	if(fCrud.salvarFuncionario(func) != null){
    		Dialogs.create().title("Salvar Funcionário").masthead("Funcionários").message("Funcionário "+func.toString()+ " salvo com sucesso!").showInformation();
    		limpar();    		
    		getFuncionariosList();
    	}    	
    }
	
	public void editar(FUNCIONARIOS item){
		funcionarioEmEdicao = item;
		bairro.setText(item.getBairro());
    	cargaHoraria.setText(item.getCargaHoraria());
    	celular.setText(item.getCelular());
    	cep.setText(item.getCep());
    	cidade.setText(item.getCidade());
    	if(item.getDataAdmicao() != null){
	    	Instant instant = Instant.ofEpochMilli(item.getDataAdmicao());
	    	LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
	    	dataAdmicao.setValue(LocalDate.from(localDate));
    	}
    	email.setText(item.getEmail());
    	endereco.setText(item.getEndereco());
    	funcao.setText(item.getFuncao());
    	nome.setText(item.getNome());
    	telefone.setText(item.getTelefone());
    	uf.setText(item.getUf());
    	ocorrencias.setItems(FXCollections.observableList(item.getOcorrencias()));
	}
	
	public void editar(ActionEvent ev){
		FUNCIONARIOS item = listaFuncionarios.getSelectionModel().getSelectedItem();
		editar(item);
	}
	
	public void limpar(ActionEvent ev){
		limpar();
	}
	
	public void remover(ActionEvent ev){
		FUNCIONARIOS selectedItem = listaFuncionarios.getSelectionModel().getSelectedItem();
		if(selectedItem == null){
			return;
		}
		Action showConfirm = Dialogs.create().title("Remover Funcionário").masthead("Funcionários").message("Deseja remover o Funcionário? "+ selectedItem).showConfirm();
		if(showConfirm == Dialog.Actions.YES){
			fCrud.removerFuncionario(selectedItem.getId());
			limpar();
			getFuncionariosList();
		}
	}
        
    private void limpar() {
    	bairro.setText("");
    	cargaHoraria.setText("");
    	celular.setText("");
    	cep.setText("");
    	cidade.setText("");
    	dataAdmicao.setValue(null);
    	email.setText("");
    	endereco.setText("");
    	funcao.setText("");
    	nome.setText("");
    	telefone.setText("");
    	uf.setText("");
    	funcionarioEmEdicao = null;
    	ocorrencias.setItems(FXCollections.observableList(new ArrayList<OCORRENCIAS>()));
    }
	public void abrirNovaOcorrencia(ActionEvent ev){    	
    	
    	 Node window = main.getStage().getScene().getRoot();
    	 tx = new TextArea();
    	 tx.setPrefRowCount(5);    	 
    	 ok = new Button("Concluir");
    	 ok.setOnAction(createActionConcluir());
    	 ocorrencia = new ComponenteModal(window, tx, ok);
    	 ocorrencia.show();
    }
    
	private EventHandler<ActionEvent> createActionConcluir() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				OCORRENCIAS oc = new OCORRENCIAS();
				oc.setDescricao(tx.getText());
				ocorrencias.getItems().add(oc);
				ocorrencia.hide();
			}
		};
	}

	public void setApp(Condominio main) {
        this.main = main;       
    }    
}

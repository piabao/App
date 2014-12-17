/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package condominio.core.portaria.busca;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.control.textfield.AutoCompletionBinding.AutoCompletionEvent;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import condominio.Condominio;
import condominio.core.login.Privilegios;
import condominio.core.portaria.PortariaCrud;
import condominio.server.modelo.ANIMAIS;
import condominio.server.modelo.CADASTRO_MORADOR;
import condominio.server.modelo.TELEFONES;
import condominio.server.modelo.TIPO_MORADOR;
import condominio.server.modelo.VEICULOS;

/**
 *
 * @author Marlon Harnisch
 */
public class BuscaFxController implements Initializable{
    
	@FXML
	TextField busca;
    @FXML
    Button exibirDetalhes;
    @FXML
    TextArea detalhes;
    @FXML
    Button editar;
    @FXML
    Button acesso;
    @FXML
    Button novaBusca;
    
	private Condominio main;
	private PortariaCrud pCrud = new PortariaCrud();
	private  AutoCompletionTextFieldBinding<CADASTRO_MORADOR> autoComplete;
	private CADASTRO_MORADOR detalhe;
	protected List<CADASTRO_MORADOR> resultadoList = new ArrayList<CADASTRO_MORADOR>();
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
    	autoComplete = (AutoCompletionTextFieldBinding<CADASTRO_MORADOR>) TextFields.bindAutoCompletion(busca, resultadoList);
    	detalhes.setStyle("-fx-font-weight: bold;"+
    						"-fx-font-size: 16;");
    	busca.requestFocus();
    	//acesso.setDisable(true);
    	
    	busca.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
    	    	if(newValue.isEmpty()){
    	    		autoComplete.hidePopup();
    	    		return;
    	    	}
    	    	
    	    	pCrud.searchMorador(newValue, new Callback<List<CADASTRO_MORADOR>, Void>() {
					
					@Override
					public Void call(List<CADASTRO_MORADOR> result) {
						resultadoList = result;
				    	    	if(resultadoList.isEmpty()){
				    	    		autoComplete.hidePopup();
				    	    		return null;
				    	    	}
				    	    	Platform.runLater(new Runnable() {
				    	    	    @Override
				    	    	    public void run() {
				    	    	    	autoComplete.setNewData(resultadoList);
						    	    	setAction();
				    	    	    }
				    	    	});				    	    	
						return null;
					}
				});
    	    	
    	    	
    	    }
    	});   	    	
    }  
    
    protected void setAction() {
    	autoComplete.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<CADASTRO_MORADOR>>() {
			
			@Override
			public void handle(AutoCompletionEvent<CADASTRO_MORADOR> event) {
				detalhe = event.getCompletion();
				preencherDetalhes(event.getCompletion());														
			}
		});
	}
    private void preencherDetalhes(CADASTRO_MORADOR value) {
    	String tipoMorador = pCrud.getTipoMorador(value.getTipo_morador()).getDescricao();
    	if(value.getTipo_morador().equals(TIPO_MORADOR.VISITANTE)){
    		preencherDetalhesVisitante(value, tipoMorador);
    		return;
    	}
    	List<TELEFONES> telefones = pCrud.getTelefonesByIdMorador(value.getId());
    	List<VEICULOS> veiculos = pCrud.getVeiculosByMorador(value.getIdentificador());
    	List<ANIMAIS> animais = pCrud.getAnimaisByMorador(value.getIdentificador());
    	
    	criarConteudoDetalhes(value, tipoMorador, telefones, veiculos, animais);
	}

	private void criarConteudoDetalhes(CADASTRO_MORADOR value,
			String tipoMorador, List<TELEFONES> telefones,
			List<VEICULOS> veiculos, List<ANIMAIS> animais) {
		StringBuilder content = new StringBuilder();
    	content.append("Nome: "+value.getNome()+ "\n");
    	if(value.getIdentificador() != null && !value.getIdentificador().isEmpty()){
    		content.append("Casa: "+ value.getIdentificador()+"\n");    		
    	}
    	content.append("Residente: "+ tipoMorador+"\n");
    	content.append("\n");
    	if(value.getEmail() != null && !value.getEmail().isEmpty()){
    		content.append("E-mail: "+ value.getEmail()+"\n");    		
    	}
    	if(telefones!= null && !telefones.isEmpty()){
    		for (TELEFONES tel : telefones) {
    			content.append("Telefone: "+ tel.getNumero()+"\n");			
    		}    		
    		content.append("\n");
    	}
    	if(veiculos!= null && !veiculos.isEmpty()){
	    	for (VEICULOS vei : veiculos) {
	    		content.append("Veiculo: "+ vei.getModelo()+" Placa: "+vei.getPlaca()+"\n");			
			}
	    	content.append("\n");
    	}
    	if(animais!= null && !animais.isEmpty()){
	    	for (ANIMAIS anim : animais) {
	    		content.append("Animal: "+ anim+"\n");			
			}
    	}
    	if(value.getVaga() != null && !value.getVaga().isEmpty()){
    		content.append("Numero da vaga: "+ value.getVaga()+"\n");	
    	}
    	if(value.getObservacao() != null && !value.getObservacao().isEmpty()){
    		content.append("\n OBSERVA��ES: "+ value.getObservacao()+"\n");    		
    	}    		
    	
		detalhes.setText(content.toString());
	}
	
	public void configuraPermissao() {
		acesso.setDisable(!Privilegios.getPermissoes().get(Privilegios.VISUALIZA_ACESSO));
	}
    
    private void preencherDetalhesVisitante(CADASTRO_MORADOR value, String tipoMorador) {
    	List<VEICULOS> veiculos = pCrud.getVeiculosByVisitante(value.getId());
    	criarConteudoDetalhes(value, tipoMorador, null, veiculos, null);
	}

	public void limpar(ActionEvent ev){
    	busca.setText("");
    	detalhes.setText("");
    	detalhe = null;
    }
    
    public void acesso(ActionEvent ev){
    	main.controleAcesso.values().iterator().next().acessar(detalhe);
    	main.busca.keySet().iterator().next().setVisible(!Privilegios.getPermissoes().get(Privilegios.VISUALIZA_ACESSO));
    	main.controleAcesso.keySet().iterator().next().setVisible(Privilegios.getPermissoes().get(Privilegios.VISUALIZA_ACESSO));
    }
    
    public void editar(ActionEvent ev){
    	main.cadastro.values().iterator().next().editar(detalhe);
    	main.busca.keySet().iterator().next().setVisible(!Privilegios.getPermissoes().get(Privilegios.VISUALIZA_CADASTRO));
    	main.cadastro.keySet().iterator().next().setVisible(Privilegios.getPermissoes().get(Privilegios.VISUALIZA_CADASTRO));
    	limpar(ev);
    }    

    public void setApp(Condominio main) {
        this.main = main;       
    }    
}

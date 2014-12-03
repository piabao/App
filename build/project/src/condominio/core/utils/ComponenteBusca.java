package condominio.core.utils;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import condominio.core.portaria.PortariaCrud;
import condominio.server.modelo.CADASTRO_MORADOR;

public class ComponenteBusca {
	
	public interface BuscaRetorno{
		public void preencherDetalhes(CADASTRO_MORADOR value);
	}
	
	public enum TipoBusca{
		CADASTRO_TODOS,
		VISITANTES
	}
	
	private TextField busca;
	private ListView<CADASTRO_MORADOR> resultado;
	private boolean canChange = false;
	//private CADASTRO_MORADOR detalhe;
	private boolean navegando = false;
	private BuscaRetorno retorno;
	private List<CADASTRO_MORADOR> collection;
	protected TipoBusca tipoBusca;
	private PortariaCrud pCrud = new PortariaCrud();
	
	public ComponenteBusca(TextField busca, ListView<CADASTRO_MORADOR> resultado, TipoBusca tipoBusca, BuscaRetorno retorno){
		this.busca = busca;
		this.tipoBusca = tipoBusca;
		this.resultado = resultado;
		this.retorno = retorno;		
		createComponents();
	}

	private void createComponents() {
		busca.requestFocus();
		
    	resultado.setVisible(false);
    	resultado.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CADASTRO_MORADOR>() {

			@Override
    	    public void changed(ObservableValue<? extends CADASTRO_MORADOR> observable, CADASTRO_MORADOR oldValue, CADASTRO_MORADOR newValue) {
    	    	if(newValue == null){
    	    		return;
    	    	}
    	    	if(navegando){
    	    		return;
    	    	}
    	    	busca.setText(newValue.toString());
    	    	canChange = true;
    	    	if(retorno != null){
    	    		retorno.preencherDetalhes(newValue);    	    		
    	    	}
    	    	resultado.setVisible(false);
    	    }
    	});
    	busca.textProperty().addListener(new ChangeListener<String>() {    	    

			@Override
    	    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
    	    	if(newValue.isEmpty()){
    	    		resultado.setVisible(false);
    	    		return;
    	    	}
    	    	if(canChange){
    	    		canChange = false;
    	    		return;
    	    	}
    	    	if(tipoBusca.equals(TipoBusca.CADASTRO_TODOS)){
    	    		collection = pCrud.searchMorador(newValue);
    	    	}else if(tipoBusca.equals(TipoBusca.VISITANTES)){
    	    		collection = pCrud.searchVisitante(newValue);
    	    	}
    	    	
    	    	ObservableList<CADASTRO_MORADOR> resultadoList = FXCollections.observableList(collection);
    	    	//TODO 
    	    	if(resultadoList.isEmpty()){
    	    		resultado.setVisible(false);
    	    		return;
    	    	}
    	    	resultado.setVisible(true);
    	        resultado.setItems(resultadoList);
    	    }
    	});
    	
    	busca.setOnKeyPressed(new EventHandler<KeyEvent>()
    		    {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.DOWN)){
                	resultado.requestFocus();                	
                }
            }
        });
    	
    	resultado.setOnKeyPressed(new EventHandler<KeyEvent>()
    		    {
            @Override
            public void handle(KeyEvent ke) {            	
                if (ke.getCode().equals(KeyCode.DOWN)){
                	navegando = true;
                }
                if (ke.getCode().equals(KeyCode.ENTER)){
                	CADASTRO_MORADOR mor = resultado.getSelectionModel().getSelectedItem();
                	if(mor == null){
                		return;
                	}
                	busca.setText(mor.toString());
                	if(retorno != null){
                		retorno.preencherDetalhes(mor);                		
                	}
                	navegando = false;
                	resultado.setVisible(false);
                }
            }
        });
	}

}

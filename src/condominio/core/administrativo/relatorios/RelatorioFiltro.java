package condominio.core.administrativo.relatorios;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.Callback;

public class RelatorioFiltro {
	
	public static final String FUNCIONARIOS = "Funcionários";
	public static final String CAD_MORADORES = "Cadastro de Moradores";
	private ComboBox<String> filtros;
	private ComboBox<String> relatorios;

	private ObservableList<String> funcionariosFiltros;
	private ObservableList<String> moradoresFiltros;
	
	public RelatorioFiltro(ComboBox<String> relatorios, ComboBox<String> filtros){
		this. relatorios = relatorios;
		this.filtros = filtros;
		relatorios.getItems().clear();
		relatorios.setItems(FXCollections.observableArrayList(CAD_MORADORES, FUNCIONARIOS));
		createListaFiltros();
		//init();
	}

	private void createListaFiltros() {
		funcionariosFiltros = FXCollections.observableArrayList("Todos");
		moradoresFiltros = FXCollections.observableArrayList("Numero da Casa","Nome do Morador","Tipo de Morador");
	}

	public void init(final Callback<String, Void> call) {
		relatorios.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				mudarFiltros(newValue);
				call.call(newValue);
			}
		});
	}

	private void mudarFiltros(String newValue) {
		//filtros.getItems().clear();
		if(newValue.equals(FUNCIONARIOS)){
			System.out.println(newValue);
			filtros.setItems(funcionariosFiltros);			
		}
		if(newValue.equals(CAD_MORADORES)){
			System.out.println(newValue);
			filtros.setItems(moradoresFiltros);
		}
	}
}

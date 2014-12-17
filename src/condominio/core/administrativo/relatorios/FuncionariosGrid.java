package condominio.core.administrativo.relatorios;

import java.util.List;

import condominio.core.portaria.modelo.RelatorioCadastroMorador;
import condominio.server.modelo.FUNCIONARIOS;
import condominio.server.modelo.OCORRENCIAS;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class FuncionariosGrid extends TableView<FUNCIONARIOS> {
	
	public FuncionariosGrid(){
		initComponents();
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
		this.setPrefHeight(443);
		this.setPrefWidth(749);
		this.setLayoutY(7);
		
		TableColumn nome = new TableColumn("Nome");
		nome.setCellValueFactory(new PropertyValueFactory<FUNCIONARIOS,String>("nome"));
		nome.setPrefWidth(180.0);
		
		TableColumn funcao = new TableColumn("funcao");
		funcao.setCellValueFactory(new PropertyValueFactory<FUNCIONARIOS,String>("funcao"));
		funcao.setPrefWidth(100.0);
		
		TableColumn endereco = new TableColumn("endereco");
		endereco.setCellValueFactory(new PropertyValueFactory<FUNCIONARIOS,String>("endereco"));
		endereco.setPrefWidth(120.0);
		
		TableColumn celular = new TableColumn("celular");
		celular.setCellValueFactory(new PropertyValueFactory<FUNCIONARIOS,String>("celular"));		
		
		TableColumn ocorrencias = new TableColumn("ocorrencias");
		ocorrencias.setCellValueFactory(new PropertyValueFactory("ocorrencias"));
		ocorrencias.setPrefWidth(230.0);
		
		ocorrencias.setCellFactory(new Callback<TableColumn, TableCell>() {

			@Override
			public TableCell call(TableColumn param) {
				TableCell cell = new TableCell<FUNCIONARIOS, List<OCORRENCIAS>>(){
					@Override
					protected void updateItem(List<OCORRENCIAS> item, boolean empty) {
						super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
					}

					private String getString() {
						if(getItem() == null || getItem().isEmpty()){
							return "";
						}
                        return getItem().toString().replace("[", "").replace("]", "").replace("{", "").replace("}", "");
                    }
				};
				return cell;
			}
		});
		
		this.getColumns().addAll(nome, funcao, endereco, celular, ocorrencias);
	}
}

package condominio.core.utils;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class ComponenteModal {
	
	private Stage stg;
	private Node window;
	final Image close;
	private Stage stage;
	private Node[] components;
	
	public ComponenteModal(Node window, Node... comp){
		this.components = comp;
		this.window = window;
		this.close = new Image(getClass().getResourceAsStream("close.png"), 23, 23, true, true);
		stg = initcomponents(window.getScene().getWindow());
	}
	
	public void addNode(Node child){
		stage.getScene().getRoot().getChildrenUnmodifiable().add(child);
	}
	
	public void show() {
		stg.show();
		GaussianBlur blurEffect = new GaussianBlur(5);
		window.setEffect(blurEffect);
	}
	
	public void hide() {
		stg.hide();
		window.setEffect(null);
	}
	
	public Stage initcomponents(Window owner){
		stage = new Stage();
	    stage.initOwner(owner);
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initStyle(StageStyle.TRANSPARENT);
	    Button okButton = new Button("X");
	    
	    okButton.getStyleClass().add("okButton");
	    okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent arg0) {
				hide();
			}
		});
	    VBox dialogRoot = new VBox(10);
	    dialogRoot.getChildren().add(okButton);
	    dialogRoot.getChildren().addAll(components);
	    dialogRoot.setAlignment(Pos.TOP_RIGHT);
	    dialogRoot.setStyle("-fx-background-color: derive(white, 25%) ; -fx-border-color: green;"
	                + "-fx-background-radius: 8px; -fx-border-radius: 8px; -fx-border-width: 3px;");
	    final Scene scene = new Scene(dialogRoot, 250, 250,
	            Color.TRANSPARENT);
	    stage.setScene(scene);
	    return stage;
	}
	
}

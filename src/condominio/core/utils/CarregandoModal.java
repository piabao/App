package condominio.core.utils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class CarregandoModal {
	
	private Stage stg;
	private Node window;
	private Stage stage;
	
	public CarregandoModal(Node window){
		this.window = window;
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
	    Image img = new Image(getClass().getResourceAsStream("ajax-loader.gif"), 32, 32, true, true);
	    ImageView imgView = new ImageView(img);
	    HBox dialogRoot = new HBox(10);
	    Label label = new Label();
	    label.setText("Carregando. . .");
	    dialogRoot.getChildren().add(label);
	    dialogRoot.getChildren().add(imgView);
	    dialogRoot.setAlignment(Pos.CENTER);
	    dialogRoot.setStyle("-fx-background-color: derive(white, 25%) ;"
	                + "-fx-background-radius: 8px; -fx-border-radius: 8px; -fx-border-width: 3px;");
	    final Scene scene = new Scene(dialogRoot, 150, 50,
	            Color.TRANSPARENT);
	    stage.setScene(scene);
	    stage.centerOnScreen();
	    return stage;
	}
	
}

package condominio.core.utils;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ErroValidacaoNode extends TextField{

	private static final String VALUE = "Campo obrigatório não preenchido";
	private static final String DEFAULT_STYLE = "-fx-background-color: red,-fx-control-inner-background;";

	private Stage stage;
	private Node window;
	
	public ErroValidacaoNode() {
		initComponent();
	}

	private void initComponent() {
		stage = new Stage();		
		stage.initStyle(StageStyle.TRANSPARENT);		
		this.setEditable(false);
		this.setText(VALUE);
		this.setWidth(50);
		this.setStyle(DEFAULT_STYLE);
		final Scene scene = new Scene(this, 200, 20,
	            Color.TRANSPARENT);
	    stage.setScene(scene);
	}
	
	public void show(Node component){
		this.window = component;
		stage.initOwner(window.getScene().getWindow());
		setPosition();
		Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

		    @Override
		    public void handle(ActionEvent event) {
		        stage.hide();
		    }
		}));
		fiveSecondsWonder.setCycleCount(1);
		fiveSecondsWonder.play();
		stage.show();
	}
	
	private void setPosition() {
		Scene scene = window.getScene();
		Point2D windowCoord = new Point2D(scene.getWindow().getX(), scene.getWindow().getY());
        Point2D sceneCoord = new Point2D(scene.getX(), scene.getY());
        Point2D nodeCoord = window.localToScene(0.0, 0.0);
        double clickX = Math.round(windowCoord.getX() + sceneCoord.getX() + nodeCoord.getX());
		double clickY = Math.round(windowCoord.getY() + sceneCoord.getY() + nodeCoord.getY());
		stage.setX(clickX);
		stage.setY(clickY+20);
	}

	public void hide() {
		stage.hide();
	}
}

package condominio.core.utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableBooleanValue;
import javafx.scene.control.TextField;

public class InputMaskChecker implements ChangeListener<String> {

    public static final String DATE      = "^[0-9\\/]*$";
    public static final String NUMERIC      = "^[0-9]*$";
    public static final String TEXTONLY     = "^\\w*$";
    public static final String PASSWORD     = "^[\\w\\+\\!\\?\\-\\$\\&\\%£]+$";
    public static final String DATASOURCE   = "^([a-zA-Z]+:){3}@([a-zA-Z0-9]+:)+[a-zA-Z0-9]+$";
    public static final String TCPPORT      = "^(6553[0-5]|655[0-2]\\d|65[0-4]\\d\\d|6[0-4]\\d{3}|[1-5]\\d{4}|[2-9]\\d{3}|1[1-9]\\d{2}|10[3-9]\\d|102[4-9])$";

    private static final String STYLE = "-fx-background-color: red,-fx-control-inner-background;";//,-fx-box-border,-fx-control-inner-background; -fx-background-insets: -1.4, 0, 1; -fx-background-radius: 1.4, 0, 0; -fx-padding: 1;";

    public final BooleanProperty erroneous = new SimpleBooleanProperty(true);
    private ErroValidacaoNode valida;

    private final String mask;
    private final int max_lenght;
    private final int min_lenght;
    private final TextField control;

    public InputMaskChecker(String mask, TextField control) {
    	this.mask = mask;
        this.max_lenght = 0;
        this.min_lenght = 0;
        this.control = control;
    }

    public InputMaskChecker(String mask, int max_lenght, int min_lenght, TextField control) {
        this.mask = mask;
        this.min_lenght = min_lenght;
        this.max_lenght = max_lenght;
        this.control = control;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {    	
    	erroneous.setValue(!newValue.matches(mask) || ((max_lenght > 0) ? newValue.length() > max_lenght : false) || (newValue.length() < 10 && newValue.length()>0));
        control.setStyle( erroneous.get() ? STYLE : "-fx-effect: null;");
    }
}
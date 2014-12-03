package condominio.core.utils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public enum MascaraDataUtil {

;

	public static void mask(final TextField textField) {
		textField.textProperty().addListener(new ChangeListener<String>() {
		
		@Override
		public void changed(ObservableValue<? extends String> ov, String antigo, String novo) {
			if (!novo.isEmpty() && novo.length() > antigo.length()) {
				try {
					Integer.parseInt(novo.replace("/", ""));
					switch (novo.length()) {
						case 2:
						textField.setText(novo + "/");
						Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
						textField.end();
						
						}
						});
						
						break;
						case 5:
						textField.setText(novo + "/");
						Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
						textField.end();
						
						}
						});
						
						break;
						case 11:
						textField.setText(antigo);
						break;
					}
					
					} catch (NumberFormatException e) {
					desfazAlteracao(antigo, textField);
					}
				}
			}
		});
	
}

private static void desfazAlteracao(String antigo, TextField textField) {
if (antigo != null && !antigo.isEmpty()) {
textField.setText(antigo);
} else {
textField.clear();
}
}

}

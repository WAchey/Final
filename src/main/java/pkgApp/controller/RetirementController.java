package pkgApp.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.poi.ss.formula.functions.FinanceLib;

import com.sun.prism.paint.Color;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import javafx.beans.value.*;

import pkgApp.RetirementApp;
import pkgCore.Retirement;

public class RetirementController implements Initializable {

	private RetirementApp mainApp = null;
	@FXML
	private TextField txtSaveEachMonth;
	@FXML
	private TextField txtYearsToWork;
	@FXML
	private TextField txtAnnualReturnWorking;
	@FXML
	private TextField txtWhatYouNeedToSave;
	@FXML
	private TextField txtYearsRetired;
	@FXML
	private TextField txtAnnualReturnRetired;
	@FXML
	private TextField txtRequiredIncome;
	@FXML
	private TextField txtMonthlySSI;

	private HashMap<TextField, String> hmTextFieldRegEx = new HashMap<TextField, String>();

	public RetirementApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(RetirementApp mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {


		hmTextFieldRegEx.put(txtYearsToWork, "(^[0-9]$|^[1-3][0-9]$|40)");
		hmTextFieldRegEx.put(txtAnnualReturnWorking, "(^\\d\\.\\d$|^10\\.\\d$|^\\d$|10|\\.\\d)");
		hmTextFieldRegEx.put(txtYearsRetired, "(^[0-9]$|^[1][0-9]$|20)");
		hmTextFieldRegEx.put(txtAnnualReturnRetired, "(^\\d\\.\\d$|^10\\.\\d$|^\\d$|10|\\.\\d)");
		hmTextFieldRegEx.put(txtRequiredIncome, "(^264[2-9]$^|26[5-9][0-9]$|^2[7-9][0-9]{2}$|^[3-9][0-9]{3}$|10000)");
		hmTextFieldRegEx.put(txtMonthlySSI,
				"(^[0-9]$|^[1-8][0-9]$|^9[0-9]$|^[1-8][0-9]{2}$|^9[0-8][0-9]$|^99[0-9]|1[0-9]{3}$|^2[0-5][0-9]{2}$|^26[0-3][0-9]$|^264[0-2]$)");

		Iterator it = hmTextFieldRegEx.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			TextField txtField = (TextField) pair.getKey();
			String strRegEx = (String) pair.getValue();

			txtField.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
						Boolean newPropertyValue) {
					// If newPropertyValue = true, then the field HAS FOCUS
					// If newPropertyValue = false, then field HAS LOST FOCUS
					if (!newPropertyValue) {
						if (!txtField.getText().matches(strRegEx)) {
							txtField.setText("");
							txtField.requestFocus();
						}
					}
				}
			});
		}

		
	}

	@FXML
	public void btnClear(ActionEvent event) {
		System.out.println("Clear pressed");
		// disable read-only controls
		txtSaveEachMonth.setDisable(true);
		txtWhatYouNeedToSave.setDisable(true);

		Iterator it = hmTextFieldRegEx.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			TextField currentField = (TextField) pair.getKey();
			currentField.clear();
			currentField.setDisable(false);
		}
		
	}

	@FXML
	public void btnCalculate() {

		System.out.println("calculating");
		Retirement r = new Retirement(Integer.parseInt(txtYearsToWork.getText()),
				Double.parseDouble(txtAnnualReturnWorking.getText()), Integer.parseInt(txtYearsRetired.getText()),
				Double.parseDouble(txtAnnualReturnRetired.getText()), Double.parseDouble(txtRequiredIncome.getText()),
				Double.parseDouble(txtMonthlySSI.getText()));

		txtWhatYouNeedToSave.setText(Double.toString(r.TotalAmountToSave()));
		txtSaveEachMonth.setText(Double.toString(r.MonthlySavings()));

		txtSaveEachMonth.setDisable(false);
		txtWhatYouNeedToSave.setDisable(false);

	}
}

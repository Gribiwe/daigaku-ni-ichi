package sample;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TextField percent;
    public TextField start;
    public TextField months;

    public Label result;

    public Label percentError;
    public Label startError;
    public Label monthsError;

    private Map<TextField, Label> errorLabelsForFields = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.errorLabelsForFields.put(this.percent, this.percentError);
        this.errorLabelsForFields.put(this.start, this.startError);
        this.errorLabelsForFields.put(this.months, this.monthsError);
    }

    public void handleEnterEvent() {
        if (isOnlyNumbers() && allIsNotEmpty()) {
            clearErrors();
            calculateAndDisplayResult();
        }
    }

    private void calculateAndDisplayResult() {
        double percentNumber = getNumberValue(this.percent);
        double moneyNumber = getNumberValue(this.start);
        double monthsNumber = getNumberValue(this.months);

        for (int i = 0; i < monthsNumber; i++) {
            moneyNumber = moneyNumber + moneyNumber * percentNumber / 100;
        }

        this.result.setText(String.format("%.2f", moneyNumber)); // 2 numbers after point
    }

    private boolean allIsNotEmpty() {
        return !isNotEmpty(this.percent) && !isNotEmpty(this.start) && !isNotEmpty(this.months);
    }

    private boolean isNotEmpty(TextField textField) {
        return textField.getText().equals("");
    }

    private double getNumberValue(TextField textField) {
        return Double.parseDouble(textField.getText());
    }

    private boolean isOnlyNumbers() {
        return isOnlyDigits(this.percent) && isOnlyDigits(this.start) && isOnlyDigits(this.months);
    }

    private void clearErrors() {
        this.errorLabelsForFields.values().forEach(label -> label.setVisible(false));
    }

    private boolean isOnlyDigits(TextField textField) {
        for (int character : textField.getText().chars().toArray()) {
            if (!Character.isDigit(character)) {
                this.errorLabelsForFields.get(textField).setVisible(true);
                return false;
            }
        }

        return true;
    }
}

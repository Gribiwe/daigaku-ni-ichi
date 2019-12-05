package gribiwe;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public ToggleGroup program;
    public RadioButton firstProg;
    public RadioButton secondProg;
    public RadioButton thirdProg;
    public TableView changeTable;
    public TableColumn changeColumn;
    public TableView resultTable;
    public NumberField changeMounth;
    public NumberField changeSum;
    public NumberField mounthCount;
    public NumberField startSumm;
    public Label changeLabel;
    public Label changeSumLabel;

    private DepositProgram selectedDepositProgram;

    private Map<String, DepositProgram> depositProgramMap = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        program = new ToggleGroup();

        firstProg.setToggleGroup(program);
        DepositProgram depositProgramToInitialize = new DepositProgram(1, 18.0F, 0.0F,
                ReplenishBehaviour.CANNOT_REPLENISH, WithdrawBehaviour.PERCENT_ONLY);
        depositProgramMap.put(firstProg.getId(), depositProgramToInitialize);

        secondProg.setToggleGroup(program);
        depositProgramToInitialize = new DepositProgram(2, 15.0F, 0.5F,
                ReplenishBehaviour.NO_LIMITS, WithdrawBehaviour.CANNOT_WITHDRAW);
        depositProgramMap.put(secondProg.getId(), depositProgramToInitialize);

        thirdProg.setToggleGroup(program);
        depositProgramToInitialize = new DepositProgram(3, 15.0F, 0.0F,
                ReplenishBehaviour.NO_LIMITS, WithdrawBehaviour.NO_LIMITS);
        depositProgramMap.put(thirdProg.getId(), depositProgramToInitialize);

    }

    private boolean isNotEmpty(TextField textField) {
        return !textField.getText().equals("");
    }

    private double getNumberValue(TextField textField) {
        return Double.parseDouble(textField.getText());
    }

    public void addChange(ActionEvent actionEvent) {
        System.out.println(getProgram());
    }

    public void calculateResult(ActionEvent actionEvent) {

    }

    public DepositProgram getProgram() {
        String radioButtonId = ((RadioButton) program.getSelectedToggle()).getId();
        return depositProgramMap.get(radioButtonId);
    }

    public void selectProgram(ActionEvent actionEvent) {
        selectedDepositProgram = getProgram();
        System.out.println(selectedDepositProgram);

        if(selectedDepositProgram.getId() == 2) {
            changeColumn.setText("Событие");
            changeLabel.setText("Снятие процента");
            changeSumLabel.setVisible(false);
            changeSum.setVisible(false);
        } else {
            changeColumn.setText("Изменение счета");
            changeLabel.setText("Изменение счета");
            changeSumLabel.setVisible(true);
            changeSum.setVisible(true);
        }
    }

    public void updateMounthCount(KeyEvent keyEvent) {
        if (mounthCount.getText().equals("")) {
            mounthCount.setNumber(0);
        }
        changeSum.setNumber(0);
        changeMounth.setNumber(0);
        changeMounth.setMaxLimit(mounthCount.getNumber());


    }

}

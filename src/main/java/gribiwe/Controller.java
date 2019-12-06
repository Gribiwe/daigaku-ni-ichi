package gribiwe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.*;

import static java.lang.String.valueOf;

public class Controller implements Initializable {

    public ToggleGroup program;
    public RadioButton firstProg;
    public RadioButton secondProg;
    public RadioButton thirdProg;

    public NumberField mounthCount;
    public NumberField startSumm;

    public NumberField changeMounth;
    public NumberField changeSum;
    public TableView changeTable;
    public TableColumn changeColumn;
    public TableColumn changeColumnMounth;
    public Label changeLabel;
    public Label changeSumLabel;

    public TableView resultTable;
    public TableColumn resultCash;
    public TableColumn resultMonth;
    public Button addChangeButton;

    private DepositProgram selectedDepositProgram;

    private Map<String, DepositProgram> depositProgramMap = new HashMap<>();

    private List<ChangeRecord> changeRecords = new ArrayList<>();

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

        changeMounth.setMaxLimit(0);

        addChangeButton.setDisable(true);

        initTables();
    }

    private void initTables() {
        initColumnNames();
        initColumnWidths();
        initColumnPropertyFactories();
        addColumnsAndInitItems();
    }

    private void addColumnsAndInitItems() {
        changeTable.getColumns().addAll(changeColumnMounth, changeColumn);
        resultTable.getColumns().addAll(resultMonth, resultCash);

        ObservableList<ChangeRecord> changeRecords = FXCollections.observableArrayList();
        ObservableList<ResultRecord> resultRecords = FXCollections.observableArrayList();

        changeTable.setItems(changeRecords);
        resultTable.setItems(resultRecords);
    }

    private void initColumnPropertyFactories() {
        resultMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        changeColumnMounth.setCellValueFactory(new PropertyValueFactory<>("month"));
        resultCash.setCellValueFactory(new PropertyValueFactory<>("cash"));
        changeColumn.setCellValueFactory(new PropertyValueFactory<>("cash"));
    }

    private void initColumnWidths() {
        changeColumnMounth.prefWidthProperty().bind(changeTable.widthProperty().multiply(0.47));
        changeColumn.prefWidthProperty().bind(changeTable.widthProperty().multiply(0.47));
        resultMonth.prefWidthProperty().bind(resultTable.widthProperty().multiply(0.47));
        resultCash.prefWidthProperty().bind(resultTable.widthProperty().multiply(0.47));

        resultMonth.setResizable(false);
        changeColumnMounth.setResizable(false);
        resultCash.setResizable(false);
        changeColumn.setResizable(false);
    }

    private void initColumnNames() {
        changeColumnMounth = new TableColumn<ChangeRecord, Long>("Месяц");
        changeColumn = new TableColumn<ChangeRecord, String>("Изменение счета");
        resultMonth = new TableColumn<ResultRecord, Long>("Месяц");
        resultCash = new TableColumn<ResultRecord, Double>("Счет");
    }

    private boolean isNotEmpty(TextField textField) {
        return !textField.getText().equals("");
    }

    private double getNumberValue(TextField textField) {
        return Double.parseDouble(textField.getText());
    }

    public void addChange() {
        long mounthNumber = changeMounth.getNumber();
        String cashValue;

        if (getProgram().getId() == 2) {
            cashValue = "снятие процентов";
        } else {
            cashValue = valueOf(changeSum.getNumber());
        }

        ChangeRecord changeRecord = new ChangeRecord(mounthNumber, cashValue);
        changeRecords.add(changeRecord);
        changeTable.setItems(FXCollections.observableArrayList(changeRecords));

        addChangeButton.setDisable(true);
    }

    public void calculateResult() {
        long monthCountValue = Long.parseLong(mounthCount.getText());
        double cashValue = (double) Long.parseLong(startSumm.getText());
        double monthPercent = getProgram().getPercent() / 12.0D;

        List<ResultRecord> resultRecords = new ArrayList<>();

        //todo: calculate with changes
        for (long i = 0; i <= monthCountValue; i++) {
            String formattedCash = String.format("%.3f", cashValue);
            double cash = Double.parseDouble(formattedCash.replace(",", "."));
            ResultRecord resultRecord = new ResultRecord(i, cash);

            resultRecords.add(resultRecord);

            cashValue = cashValue + cashValue * monthPercent / 100D;
        }

        ObservableList<ResultRecord> resultRecordObservableList = FXCollections.observableArrayList(resultRecords);
        resultTable.setItems(resultRecordObservableList);
    }

    public DepositProgram getProgram() {
        String radioButtonId = ((RadioButton) program.getSelectedToggle()).getId();
        return depositProgramMap.get(radioButtonId);
    }

    private void clearChange() {
        changeRecords = new ArrayList<>();
        changeTable.setItems(FXCollections.observableArrayList());
        changeSum.setNumber(0);
        changeMounth.setNumber(0);
        changeMounth.setMaxLimit(mounthCount.getNumber());
        addChangeButton.setDisable(true);
    }

    public void selectProgram() {
        clearChange();
        selectedDepositProgram = getProgram();

        if (selectedDepositProgram.getId() == 3) {
            changeSum.setCanBeNegative(true);
        } else {
            changeSum.setCanBeNegative(false);
        }

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

        clearChange();
    }

    private boolean isMonthAlreadyInChangeRecords(long month) {
        for (ChangeRecord changeRecord : changeRecords) {
            if (changeRecord.getMonth() == month) return true;
        }

        return false;
    }

    public void handleChangeMonth() {
        if (changeMounth.getText().equals("")) {
            changeMounth.setNumber(0);
        } else {
            long limit = mounthCount.getNumber();
            if (changeMounth.getNumber() > limit) {
                changeMounth.setNumber(limit);
            }
        }

        long changeMonthField = changeMounth.getNumber();
        if (changeMonthField == 0 || isMonthAlreadyInChangeRecords(changeMonthField)) {
            addChangeButton.setDisable(true);
        } else {
            addChangeButton.setDisable(false);
        }
    }
}

package gribiwe;

import javafx.scene.control.TextField;
import org.w3c.dom.ls.LSOutput;

public class NumberField extends TextField {

    public NumberField() {
        super();
        setText("0");
        setOnKeyTyped(event -> {
            if (getText().equals("0") || getText().equals("-0") || getText().equals("-") || getNumber() == maxLimit) {
                setText("");
            }
        });
        setOnKeyReleased(event -> {
            if (getText().equals("") || getText().equals("-")) {
                setNumber(0);
            } else if (getNumber() > maxLimit) {
                setNumber(maxLimit);
            }
        });
    }

    private boolean canBeNegative = false;

    public void setCanBeNegative(boolean canBeNegative) {
        this.canBeNegative = canBeNegative;
    }

    private long maxLimit = Long.MAX_VALUE / 100;

    public void setMaxLimit(long maxLimit) {
        this.maxLimit = maxLimit;
    }

    @Override
    public void clear() {
        super.clear();
        setNumber(0);
    }

    @Override
    public void cut() {
        super.cut();
        setNumber(0);
    }

    @Override
    public void replaceText(int start, int end, String text) {
        boolean wasNegate = false;

        if (text.equals("") && start == 0 && getText().contains("-")) {
            setText(getText().replace("-", ""));
            return;
        }

        if (text.contains("-")) {
            if (canBeNegative) {
                if (getText().contains("-")) {
                    setText(getText().replace("-", ""));
                } else {
                    setText("-" + getText());
                    wasNegate = true;
                }
                text = text.replace("-", "");
            } else return;
        }

        if (validate(text.replace("-", ""))) {
            if (start == 0 && getText().contains("-") && !wasNegate) {
                start++;
                end++;
            }
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text) {
        return text.matches("[0-9]*");
    }

    public long getNumber() {
        String text = getText();
        return text.equals("") ? 0 : Long.parseLong(text);
    }

    public void setNumber(long number) {
        setText(String.valueOf(number));
    }
}
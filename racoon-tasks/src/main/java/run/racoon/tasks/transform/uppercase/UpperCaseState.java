package run.racoon.tasks.transform.uppercase;

import run.racoon.commons.domain.TransformerState;

import java.util.List;

public class UpperCaseState extends TransformerState {
    private final List<String> fieldNames;

    public UpperCaseState(List<String> fieldNames) {
        this.fieldNames = fieldNames;
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }
}

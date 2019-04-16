package run.racoon.tasks.transform.uppercase;

import run.racoon.commons.domain.Record;
import run.racoon.commons.tasks.TransformTask;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class UpperCaseTransformTask implements TransformTask<UpperCaseState> {
    private List<String> fields;

    @Override
    public void configure(UpperCaseState state) {
        this.fields = state.getFieldNames();
    }

    @Override
    public List<Record> transform(List<Record> records) {
        return records.stream()
                .map(record -> uppercaseFields(Record.Builder.newBuilder(record), record))
                .collect(Collectors.toList());
    }

    private Record uppercaseFields(Record.Builder builder, Record record) {
        this.fields.forEach(f -> builder.cell(f, upper(record.getByName(f))));
        return builder.build();
    }

    private String upper(Object value) {
        return ofNullable(value)
                        .map(Object::toString)
                        .map(String::toUpperCase).orElse(null);
    }
}

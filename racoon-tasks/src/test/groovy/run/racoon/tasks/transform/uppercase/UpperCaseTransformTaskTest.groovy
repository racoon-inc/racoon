package run.racoon.tasks.transform.uppercase

import run.racoon.commons.domain.Record
import spock.lang.Specification
import spock.lang.Unroll

class UpperCaseTransformTaskTest extends Specification {
    @Unroll
    def "Transforms #value to #transformed"() {
        given:
        def transformation = new UpperCaseTransformTask()
        transformation.start(new UpperCaseState(["value"]), null)
        def record = Record.Builder.newBuilder().cell("value", value).build()
        def result = transformation.transform([record]).get(0)

        expect:
        result.getByName("value") == transformed

        where:
        value | transformed
        "foo" | "FOO"
        null  | null
        1     | "1"
        2L    | "2"
    }
}

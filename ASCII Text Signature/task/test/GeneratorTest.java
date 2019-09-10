import org.hyperskill.hstest.v5.stage.BaseStageTest;
import signature.MainKt;

public abstract class GeneratorTest<T> extends BaseStageTest<T> {
    public GeneratorTest() throws Exception {
        super(MainKt.class);
    }
}

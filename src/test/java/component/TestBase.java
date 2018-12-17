package component;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

/**
 * philo
 * # 12/16/18
 */
public abstract class TestBase {
    protected Parser calculator;

    @BeforeClass
    public void beforeClass() {
        calculator = new Parser(new Lexer());
    }

    @AfterMethod
    public void afterTest() {
        calculator.parseInputText("clear").compute();
    }

    protected String stackString(String data) {
        if (data.isEmpty()) {
            return "stack: \n";
        } else {
            return "stack: " + data + " \n";
        }
    }

    protected String mockInputAndCompute(String inputText){
        return calculator.parseInputText(inputText).compute().toString();
    }
}

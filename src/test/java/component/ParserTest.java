package component;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * philo
 * # 12/16/18
 */
public class ParserTest extends TestBase {

    @Test
    public void scenario1() {
        Assert.assertEquals(
                mockInputAndCompute("5 2"),
                stackString("5 2")
        );
    }

    @Test
    public void scenario2() {
        Assert.assertEquals(
                mockInputAndCompute("2 sqrt"),
                stackString("1.4142135623")
        );
    }

    @Test
    public void scenario3() {
        Assert.assertEquals(
                mockInputAndCompute("5 2 -"),
                stackString("3")
        );
        Assert.assertEquals(
                mockInputAndCompute("3 -"),
                stackString("0")
        );
        Assert.assertEquals(
                mockInputAndCompute("clear"),
                stackString("")
        );
    }

    @Test
    public void scenario4() {
        Assert.assertEquals(
                mockInputAndCompute("5 4 3 2"),
                stackString("5 4 3 2")
        );
        Assert.assertEquals(
                mockInputAndCompute("undo undo *"),
                stackString("20")
        );
        Assert.assertEquals(
                mockInputAndCompute("5 *"),
                stackString("100")
        );
        Assert.assertEquals(
                mockInputAndCompute("undo"),
                stackString("20 5")
        );
    }

    @Test
    public void scenario5() {
        Assert.assertEquals(
                mockInputAndCompute("7 12 2 /"),
                stackString("7 6")
        );
        Assert.assertEquals(
                mockInputAndCompute("*"),
                stackString("42")
        );
        Assert.assertEquals(
                mockInputAndCompute("4 /"),
                stackString("10.5")
        );
    }

    @Test
    public void scenario6() {
        Assert.assertEquals(
                mockInputAndCompute("1 2 3 4 5"),
                stackString("1 2 3 4 5")
        );
        Assert.assertEquals(
                mockInputAndCompute("*"),
                stackString("1 2 3 20")
        );
        Assert.assertEquals(
                mockInputAndCompute("clear 3 4 -"),
                stackString("-1")
        );
    }

    @Test
    public void scenario7() {
        Assert.assertEquals(
                mockInputAndCompute("1 2 3 4 5"),
                stackString("1 2 3 4 5")
        );
        Assert.assertEquals(
                mockInputAndCompute("* * * *"),
                stackString("120")
        );
    }

    @Test
    public void scenario8_1() {
        Assert.assertEquals(
                mockInputAndCompute("1 2 3 * 5 +"),
                stackString("1 11")
        );
    }

    @Test
    public void scenario8_2() {
        Assert.assertEquals(
                mockInputAndCompute("1 2 3 * 5 + * * 6 5"),
                "operator * (position: 15): insucient parameters\nstack: 11 "
        );
    }

    @Test
    public void complexScenario() {
        Assert.assertEquals(
                mockInputAndCompute("-4.3 4 2 / +"),
                stackString("-2.3")
        );
        Assert.assertEquals(
                mockInputAndCompute("  undo *    -1.6 -"),
                stackString("-7")
        );
    }
}

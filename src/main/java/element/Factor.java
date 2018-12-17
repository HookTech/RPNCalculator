package element;

import visitor.TokenElementVisitor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * philo
 * # 12/13/18
 */
public class Factor extends Token implements UNDOAble {
    //TODO consider using double
    private BigDecimal value;

    private UNDOAble onceOperate;

    private List<Factor> factorMemos;

    public Factor(String numberText) {
        value = new BigDecimal(numberText);
    }

    public Factor(BigDecimal v) {
        value = v;
    }

    public void setOnceOperate(UNDOAble onceOperate, Factor... memoFactors) {
        this.onceOperate = onceOperate;
        factorMemos = Arrays.asList(memoFactors);
    }

    public List<Factor> getFactorMemos() {
        return factorMemos;
    }

    @Override
    public void accept(TokenElementVisitor visitor) {
        Stack<Factor> factorStack = visitor.getHandleStack();
        setOnceOperate(new Operator.DummyFactorOp());
        factorStack.push(this);
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public void undoStack(Stack<Factor> stack) {
        onceOperate.undoStack(stack);
    }

    @Override
    public String toString() {
        double dd = value.divide(new BigDecimal(1), 10, RoundingMode.DOWN).doubleValue();
        String ss = String.valueOf(dd);
        if(dd - Math.floor(dd) < 1e-10){
            return ss.substring(0,ss.indexOf('.'));
        }
        else {
            return ss;
        }
    }
}

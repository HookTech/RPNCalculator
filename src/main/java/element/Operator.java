package element;

import errorflow.InsucientParametersException;
import visitor.TokenElementVisitor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * philo
 * # 12/13/18
 */
public abstract class Operator extends Token {

    abstract String getOpName();

    public static class DummyFactorOp extends Operator implements UNDOAble {
        @Override
        public void undoStack(Stack<Factor> stack) {
            stack.pop();
        }

        @Override
        public void accept(TokenElementVisitor visitor) {
        }

        @Override
        String getOpName() {
            return "dummy";
        }
    }

    public static abstract class FundamentalOp extends Operator implements UNDOAble {
        @Override
        public void accept(TokenElementVisitor visitor) throws InsucientParametersException {
            Stack<Factor> factorStack = visitor.getHandleStack();
            Factor factorBehind = null;
            Factor factorAhead = null;
            try {
                factorBehind = factorStack.pop();
            } catch (EmptyStackException e) {
                throw new InsucientParametersException(visitor.getLexerInvalidTokenPos(this), getOpName());
            }
            try {
                factorAhead = factorStack.pop();
            } catch (EmptyStackException e) {
                throw new InsucientParametersException(visitor.getLexerInvalidTokenPos(this), getOpName(), factorBehind.toString());
            }
            Factor result = compute(factorAhead, factorBehind);
            result.setOnceOperate(this, factorAhead, factorBehind);
            factorStack.push(result);
        }

        public void undoStack(Stack<Factor> stack) {
            Factor popFactor = stack.pop();
            for (Factor f : popFactor.getFactorMemos()) {
                stack.push(f);
            }
        }

        abstract Factor compute(Factor factorAhead, Factor factorBehind);
    }

    public static class ADD extends FundamentalOp {
        @Override
        protected Factor compute(Factor factorAhead, Factor factorBehind) {
            return new Factor(factorAhead.getValue().add(factorBehind.getValue()));
        }

        @Override
        String getOpName() {
            return "+";
        }
    }

    public static class SUB extends FundamentalOp {
        @Override
        protected Factor compute(Factor factorAhead, Factor factorBehind) {
            return new Factor(factorAhead.getValue().subtract(factorBehind.getValue()));
        }

        @Override
        String getOpName() {
            return "-";
        }
    }

    public static class MUL extends FundamentalOp {
        @Override
        protected Factor compute(Factor factorAhead, Factor factorBehind) {
            return new Factor(factorAhead.getValue().multiply(factorBehind.getValue()));
        }

        @Override
        String getOpName() {
            return "*";
        }
    }

    public static class DIV extends FundamentalOp {
        @Override
        protected Factor compute(Factor factorAhead, Factor factorBehind) {
            return new Factor(factorAhead.getValue().divide(factorBehind.getValue(), 15, RoundingMode.UP));
        }

        @Override
        String getOpName() {
            return "/";
        }
    }

    public static class SQRT extends Operator implements UNDOAble {
        @Override
        public void accept(TokenElementVisitor visitor) throws InsucientParametersException {
            Stack<Factor> factorStack = visitor.getHandleStack();
            Factor factor = null;
            try {
                factor = factorStack.pop();
            } catch (EmptyStackException e) {
                throw new InsucientParametersException(visitor.getLexerInvalidTokenPos(this), getOpName());
            }
            Factor result = new Factor(new BigDecimal(
                    Math.sqrt(factor.getValue().doubleValue())));
            result.setOnceOperate(this, factor);
            factorStack.push(result);
        }

        @Override
        public void undoStack(Stack<Factor> stack) {
            Factor popFactor = stack.pop();
            for (Factor f : popFactor.getFactorMemos()) {
                stack.push(f);
            }
        }

        @Override
        String getOpName() {
            return "sqrt";
        }
    }

    public static class UNDO extends Operator {
        @Override
        public void accept(TokenElementVisitor visitor) throws InsucientParametersException {
            try {
                Stack<Factor> factorStack = visitor.getHandleStack();
                factorStack.peek().undoStack(factorStack);
            } catch (EmptyStackException e) {
                throw new InsucientParametersException(visitor.getLexerInvalidTokenPos(this), getOpName());
            }
        }

        @Override
        String getOpName() {
            return "undo";
        }
    }

    public static class CLEAR extends Operator {
        @Override
        public void accept(TokenElementVisitor visitor) {
            visitor.getHandleStack().clear();
        }

        @Override
        String getOpName() {
            return "clear";
        }
    }
}

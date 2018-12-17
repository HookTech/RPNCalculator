package visitor;

import element.Factor;
import element.Token;
import errorflow.InsucientParametersException;

import java.util.Stack;

/**
 * philo
 * # 12/16/18
 */
public interface TokenElementVisitor {
    void visit(TokenElement element) throws InsucientParametersException;

    Stack<Factor> getHandleStack();

    int getLexerInvalidTokenPos(Token invalidToken);
}

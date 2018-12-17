package component;

import element.Factor;
import element.Token;
import errorflow.InsucientParametersException;
import visitor.TokenElement;
import visitor.TokenElementVisitor;

import java.util.List;
import java.util.Stack;

/**
 * philo
 * # 12/13/18
 */
public class Parser implements TokenElementVisitor {
    private Lexer lexer;

    private Stack<Factor> factorHeap;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        factorHeap = new Stack<>();
    }

    /**
     * parse input text
     */
    public Parser parseInputText(String inputText) throws Lexer.LexerException {
        lexer.receiveText(inputText);
        lexer.tokenize();
        return this;
    }

    /**
     * refresh inner status
     */
    public Parser compute() {
        Token currentToken = lexer.getNextToken();
        try {
            while (!(currentToken instanceof Token.EOF)) {
                visit(currentToken);
                currentToken = lexer.getNextToken();
            }
        }catch (InsucientParametersException e){
            errorQuit = true;
            StringBuilder stringBuilder = new StringBuilder("operator ");
            stringBuilder.append(e.getOperatorInfo()).append(" (position: ")
                    .append(e.getPosInfo()).append("): insucient parameters").append("\n")
                    .append("stack: ").append(errorStackInfo(e.getInstanceStackState()));
            errorMessage = stringBuilder.toString();
        }
        catch (RuntimeException e){
            errorQuit = true;
            errorMessage = "Inner RunTime ERROR!!";
        }
        catch (Exception e){
            errorQuit = true;
            errorMessage = "Inner ERROR";
        }
        return this;
    }

    private boolean errorQuit = false;
    private String errorMessage;

    @Override
    public String toString() {
        if(!errorQuit) {
            return normalInfo();
        }
        else {
            return errorMessage;
        }
    }

    private String normalInfo(){
        Stack clone = ((Stack) factorHeap.clone());
        return "stack: " + stackReversePrint(clone);
    }

    private String errorStackInfo(List<String> errorStackInfo){
        StringBuilder sb = new StringBuilder();
        for(String ss : errorStackInfo){
            sb.append(ss).append(" ");
        }
        return sb.toString();
    }

    private String stackReversePrint(Stack stack){
        StringBuilder builder = new StringBuilder();
        while (!stack.isEmpty()){
            builder.insert(0,stack.pop().toString() + " ");
        }
        return builder.append("\n").toString();
    }

    @Override
    public void visit(TokenElement element) throws InsucientParametersException {
        element.accept(this);
    }

    @Override
    public Stack<Factor> getHandleStack() {
        return factorHeap;
    }

    @Override
    public int getLexerInvalidTokenPos(Token invalidToken) {
        return lexer.getPos(invalidToken);
    }
}

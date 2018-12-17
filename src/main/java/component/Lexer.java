package component;

import element.Factor;

import element.Operator;
import element.Token;

import java.util.*;

/**
 * philo
 * # 12/13/18
 */
public class Lexer {
    private String currentParseText;

    private Queue<Token> tokenHeap;

    public void receiveText(String text) {
        currentParseText = text;
        tokenHeap = new LinkedList<>();
        recordTokenVsPos = new HashMap<>();
        resetStatus();
    }

    int pos;//parse index
    char currentChar;

    Map<Token,Integer> recordTokenVsPos;

    private final static char ASCIIZERO = 48;
    private final static char ASCIININE = 57;
    private final static char ASCIIDECIMALSEPARATOR = 46;
    private final static char ASCIIWHITESPACE = 32;
    private final static char ENDOFPARSETEXT = 0;

    private final static char ASCIIADDITION = 43;
    private final static char ASCIISUBTRACTION = 45;
    private final static char ASCIIMULTIPLICATION = 42;
    private final static char ASCIIDIVISION = 47;

    /**
     * get next token
     */
    public Token getNextToken() {
        return tokenHeap.poll();
    }

    /**
     * tokenize inner parseText
     */
    public void tokenize() {
        advance();
        ParseChain parseChain = defaultParseChain();
        while (!isEndOfParseText()) {
            if (isWhiteSpace()) {
                skipWhitespace();
            }
            Token parsedToken = parseChain.parseNextToken();
            tokenHeap.add(parsedToken);
            recordTokenVsPos.put(parsedToken,pos);
            parseChain.resetToFirstNode();
        }
        tokenHeap.add(new Token.EOF());
        resetStatus();
    }

    /**
     * skip whitespace char
     */
    private void skipWhitespace() {
        while (isWhiteSpace()) {
            advance();
        }
    }

    /**
     * refresh currentChar & pos
     */
    private void advance() {
        pos++;
        if (pos > currentParseText.length() - 1) {
            currentChar = ENDOFPARSETEXT;
        } else {
            currentChar = currentParseText.charAt(pos);
        }
    }

    private boolean isDigit() {
        return ASCIIZERO <= currentChar && currentChar <= ASCIININE;
    }

    private boolean isDecimalSeparator() {
        return currentChar == ASCIIDECIMALSEPARATOR;
    }

    private boolean isPossibleDigit() {
        return isDigit() || isDecimalSeparator();
    }

    private boolean isWhiteSpace() {
        return currentChar == ASCIIWHITESPACE;
    }

    private boolean isEndOfParseText() {
        return currentChar == ENDOFPARSETEXT;
    }

    private void resetStatus() {
        pos = -1;
        currentChar = 1;
    }

    public ParseChain defaultParseChain() {
        List<ParseNode> parseNodeList = Arrays.asList(
                new ParseNegativeFactor(),
                new ParseFactor(),
                new ParseADD(),
                new ParseSUB(),
                new ParseMUL(),
                new ParseDIV(),
                new ParseSQRT(),
                new ParseUNDO(),
                new ParseCLEAR(),
                new UnIdentify()
        );
        ParseNode head = parseNodeList.get(0), cur = head;
        for (int i = 1; i < parseNodeList.size(); i++) {
            cur.setNext(parseNodeList.get(i));
            cur = cur.next();
        }

        return new ParseChain(head);
    }

    private abstract class ParseNode {
        protected ParseNode nextParseNode;

        public void setNext(ParseNode next) {
            nextParseNode = next;
        }

        public abstract boolean canHandle();

        public abstract Token parse();

        public ParseNode next() {
            return nextParseNode;
        }
    }

    private class ParseChain {
        private ParseNode parseNode;

        private ParseNode firstNode;

        public ParseChain(ParseNode firstNode) {
            parseNode = firstNode;
            this.firstNode = firstNode;
        }

        public Token parseNextToken() {
            while (parseNode != null) {
                if (parseNode.canHandle()) {
                    return parseNode.parse();
                } else {
                    parseNode = parseNode.next();
                }
            }
            throw new LexerException(pos, currentChar, "System Info <unidentified char>");
        }

        public void resetToFirstNode() {
            parseNode = firstNode;
        }
    }

    private class ParseNegativeFactor extends ParseNode {
        @Override
        public boolean canHandle() {
            if (currentChar == ASCIISUBTRACTION && (pos + 1) < currentParseText.length()) {
                char cc = currentParseText.charAt(pos + 1);
                return ASCIIZERO <= cc && cc <= ASCIININE;
            } else {
                return false;
            }
        }

        @Override
        public Token parse() {
            StringBuilder numberTextBuilder = new StringBuilder();
            numberTextBuilder.append(currentChar);
            advance();
            while (!isEndOfParseText() && isPossibleDigit()){
                numberTextBuilder.append(currentChar);
                advance();
            }
            return new Factor(numberTextBuilder.toString());
        }
    }

    /**
     * generate Factor and refresh status
     */
    private class ParseFactor extends ParseNode {
        @Override
        public boolean canHandle() {
            return isPossibleDigit();
        }

        @Override
        public Token parse() {
            StringBuilder numberTextBuilder = new StringBuilder();
            while (!isEndOfParseText() && isPossibleDigit()) {
                numberTextBuilder.append(currentChar);
                advance();
            }
            return new Factor(numberTextBuilder.toString());
        }
    }

    private class ParseADD extends ParseNode {

        @Override
        public boolean canHandle() {
            return currentChar == ASCIIADDITION;
        }

        @Override
        public Token parse() {
            advance();
            return new Operator.ADD();
        }
    }

    private class ParseSUB extends ParseNode {

        @Override
        public boolean canHandle() {
            return currentChar == ASCIISUBTRACTION;
        }

        @Override
        public Token parse() {
            advance();
            return new Operator.SUB();
        }
    }

    private class ParseMUL extends ParseNode {

        @Override
        public boolean canHandle() {
            return currentChar == ASCIIMULTIPLICATION;
        }

        @Override
        public Token parse() {
            advance();
            return new Operator.MUL();
        }
    }

    private class ParseDIV extends ParseNode {

        @Override
        public boolean canHandle() {
            return currentChar == ASCIIDIVISION;
        }

        @Override
        public Token parse() {
            advance();
            return new Operator.DIV();
        }
    }

    private class ParseSQRT extends ParseNode {

        @Override
        public boolean canHandle() {
            //TODO all s q r t char has been assert, need to support pre advance
            return currentChar == 's';
        }

        @Override
        public Token parse() {
            advance();
            advance();
            advance();
            advance();
            return new Operator.SQRT();
        }
    }

    private class ParseUNDO extends ParseNode {
        @Override
        public boolean canHandle() {
            //TODO all u n d o char has been assert, need to support pre advance
            return currentChar == 'u';
        }

        @Override
        public Token parse() {
            advance();
            advance();
            advance();
            advance();
            return new Operator.UNDO();
        }
    }

    private class ParseCLEAR extends ParseNode {
        @Override
        public boolean canHandle() {
            //TODO all c l e a r char has been assert, need to support pre advance
            return currentChar == 'c';
        }

        @Override
        public Token parse() {
            advance();
            advance();
            advance();
            advance();
            advance();
            return new Operator.CLEAR();
        }
    }

    private class UnIdentify extends ParseNode {
        @Override
        public boolean canHandle() {
            return true;
        }

        @Override
        public Token parse() {
            throw new LexerException(pos, currentChar, "System Info <unidentified char>");
        }
    }

    public class LexerException extends RuntimeException {
        private int posInfo;
        private int currentCharInfo;

        public LexerException(int pos, char currentChar, String message) {
            super(message);
            posInfo = pos;
            currentCharInfo = currentChar;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("wrong process at ").append(posInfo)
                    .append("\n")
                    .append("current char is ").append(currentCharInfo);
            return builder.toString();
        }
    }

    public int getPos(Token curToken) {
        return recordTokenVsPos.get(curToken);
    }
}

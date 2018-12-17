package run;

import component.Lexer;
import component.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * philo
 * # 12/13/18
 */
public class EntryClass {

    private static BufferedReader sysReader = new BufferedReader(new InputStreamReader(in));

    public static void main(String... args) {
        Parser calculator = new Parser(new Lexer());
        String text;

        while (true) {
            try {
                text = readRawText("exp>");
            } catch (IOException e) {
                out.print(e.getMessage());
                break;
            }
            if (text == null || text.isEmpty()) {
                continue;
            }
            else if(text.matches("[Ee][Oo][Ff]")){
                break;
            }else {
                try{
                    out.print(calculator.parseInputText(text).compute());
                }
                catch (Lexer.LexerException e){
                    out.print(e);
                }
            }
        }

        out.print("System Info <calculator shutdown>");
    }

    private static String readRawText(String hit) throws IOException {
        out.print(hit);
        return sysReader.readLine();
    }
}

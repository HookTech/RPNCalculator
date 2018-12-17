package errorflow;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;

/**
 * philo
 * # 12/16/18
 */
public class InsucientParametersException extends EmptyStackException {
    private int posInfo;
    private String operatorInfo;
    private List<String> instanceStackState;

    public InsucientParametersException(int pos, String operator, String... stackFactorInfos){
        posInfo = pos;
        operatorInfo = operator;
        instanceStackState = Arrays.asList(stackFactorInfos);
    }

    public int getPosInfo() {
        return posInfo;
    }

    public String getOperatorInfo() {
        return operatorInfo;
    }

    public List<String> getInstanceStackState() {
        return instanceStackState;
    }
}

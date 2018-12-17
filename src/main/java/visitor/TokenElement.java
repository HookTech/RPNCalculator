package visitor;

import errorflow.InsucientParametersException;

/**
 * philo
 * # 12/16/18
 */
public interface TokenElement {
    void accept(TokenElementVisitor visitor) throws InsucientParametersException;
}

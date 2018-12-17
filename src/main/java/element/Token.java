package element;

import visitor.TokenElement;
import visitor.TokenElementVisitor;

/**
 * philo
 * # 12/13/18
 */
public abstract class Token implements TokenElement {

    public static class EOF extends Token{
        @Override
        public void accept(TokenElementVisitor visitor) {

        }
    }

    //TODO more better
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

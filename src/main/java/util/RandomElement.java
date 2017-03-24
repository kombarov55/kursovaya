package util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by nikolaykombarov on 24.03.17.
 */
public class RandomElement {

    List values;

    Random r = new Random();

    public RandomElement(List values) {
        this.values = values;
    }

    public RandomElement(Object... values) {
        this.values = Arrays.asList(values);
    }

    public <T> T get() {
        return (T) values.get(r.nextInt(values.size()));
    }
}

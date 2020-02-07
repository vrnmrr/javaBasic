

package examples.java8.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Util {

    public <T> List<T> newList(T... objects) {
        List<T> result = new ArrayList<>();
        result.addAll(Arrays.asList(objects));
        return result;

    }
}

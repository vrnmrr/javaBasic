


package examples.utilorders;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import examples.java8.beans.others.OrderBy;
import examples.java8.beans.others.OrderBy.Sort;
import lombok.experimental.UtilityClass;

@SuppressWarnings({ "unchecked", "rawtypes" })
@UtilityClass
public class OrderUtil {

    /**
     * Sort list items.
     * IMPORTANT: The result of last function must extend Comparable interface. ANY OTHER CASE is a coding error.
     * WARNING: sortList is a destructive function because maybe make useless mapOrderBy 
     * See OrderUtilTest.testExecNestedFunctions and OrderUtilTest.testSortNestedStrings to check the right use
     *
     * @param <A> the generic type of items
     * @param items list of the beans
     * @param mapOrderBy the map with fieldGetters to sorted by
     * @return the sorted list
     */
    public <A extends Serializable> List<A> sortList(List<A> items //
            , Map<Function<A, ? extends Serializable>, OrderBy.Sort> mapOrderBy) {

        if (!(mapOrderBy instanceof LinkedHashMap<?, ?>)) {
            throw new IllegalArgumentException("Param 'mapOrderBy' must be LinkedHashMap type");
        }

        if (items == null || items.isEmpty()) {
            return items;
        }

        for (Map.Entry<Function<A, ? extends Serializable>, OrderBy.Sort> m : mapOrderBy.entrySet()) {
            Function<A, ? extends Serializable> fieldGetter = m.getKey();
            OrderBy.Sort sort = m.getValue();

            Collections.sort(items, (o1, o2) -> compareLE((Comparable)execFunction(o1, fieldGetter), (Comparable)execFunction(o2, fieldGetter), sort));
        }

        return items;

    }

    /**
     *  Execute a list of nested functions on a bean
     *
     * @param <A> the generic type of outer-bean
     * @param <B> the generic type of inner-bean
     * @param <C> the generic type of result
     * @param bean the outer-bean
     * @param listFunc the list of getters. 
     * @return the nested val
     */
    public static <A extends Serializable, B extends Serializable, C extends Serializable> C execNestedFunctions(A bean,
            List<Function<? extends Serializable, ? extends Serializable>> listFunc) {

        if (bean == null) {
            return null;
        }
        if (listFunc.size() == 1) {
            return execFunction(bean, (Function<A, C>)listFunc.get(0));
        } else {
            Function<A, B> function = (Function<A, B>)listFunc.get(0);
            listFunc.remove(0);
            return execNestedFunctions(function.apply(bean), listFunc);
        }
    }

    /**
     * Execute a function on a bean
     *  
     * @param <B> the type of bean
     * @param <R> the type or result must extend Comparable interface.
     * @param bean the bean
     * @param fun the function
     * @return the value
     */
    public static <B, R> R execFunction(B bean, Function<B, R> funct) {
        return bean == null ? null : funct.apply(bean);
    }

    /**
     * Compares two values depending on the ordenation param.
     *
     * @param <T> the generic type
     * @param a Value A
     * @param b Value B
     * @param sort the sort
     * @return 0 when equals
     * , -1 when A value is greater if ordering is ascending (1 otherwise)
     * , 1 when B value is greater when ordering is ascending (-1 otherwise)
     */
    private static <T> int compareLE(Comparable<T> a, Comparable<T> b, OrderBy.Sort sort) {
        return (sort == Sort.DESC ? -1 : 1) * compare(a, b);
    }

    /**
     * Compares two values 
     * @param <T> the generic type
     * @param a Value A
     * @param b Value B
     * @return 0 when equals, 1 when A value is greater , -1 otherwise  
     */
    private static <T> int compare(Comparable<T> a, Comparable<T> b) {
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return -1;
        }
        if (b == null) {
            return 1;
        }
        return a.compareTo((T)b);
    }

}

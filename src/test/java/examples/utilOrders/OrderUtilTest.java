

package examples.utilOrders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import examples.java8.beans.dummies.DummyInnerBean;
import examples.java8.beans.dummies.DummyOuterBean;
import examples.java8.beans.others.OrderBy;
import examples.java8.beans.others.OrderBy.Sort;
import examples.utilorders.OrderUtil;

public class OrderUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<DummyOuterBean> lista;

    @Before
    public void init() {
        lista = new ArrayList<DummyOuterBean>();

        lista.add(new DummyOuterBean("Ca", "Pe", 4L, false, DummyInnerBean.builder().name("B").build()));
        lista.add(new DummyOuterBean("Am", "Go", 3L, false, null));
        lista.add(new DummyOuterBean("Az", "Ga", 6L, true, null));
        lista.add(new DummyOuterBean("Ol", "Za", 2L, false, DummyInnerBean.builder().name("D").build()));
        lista.add(new DummyOuterBean("Er", "Gu", 5L, false, null));
        lista.add(new DummyOuterBean("Er", "Bi", 1L, true, null));
        lista.add(new DummyOuterBean(null, "Yf", 8L, false, DummyInnerBean.builder().name("C").build()));
        lista.add(new DummyOuterBean("Wo", "", 9L, true, null));
        lista.add(new DummyOuterBean("", "Li", 10L, false, null));
        lista.add(new DummyOuterBean("Ju", null, 7L, true, DummyInnerBean.builder().name("A").build()));
        lista.add(new DummyOuterBean(null, null, null, false, null));

    }

    @Test
    public void testExecFunction() {
        DummyOuterBean bean = new DummyOuterBean("A", "B", null, false, null);

        assertEquals(bean.getName(), OrderUtil.execFunction(bean, DummyOuterBean::getName));
        assertEquals(bean.getSurname(), OrderUtil.execFunction(bean, DummyOuterBean::getSurname));
        assertNull(OrderUtil.execFunction(bean, DummyOuterBean::getNum));
        assertNull(OrderUtil.execFunction(null, DummyOuterBean::getNum));
    }

    @Test
    public void testExecNestedFunctions() {
        //OJO con los DummyInnerBean hay 3 anidados: 
        DummyOuterBean bean = DummyOuterBean.builder().name("Outer-A").surname("Outer-B")//
                .innerBean(DummyInnerBean.builder().name("Inner-1")//
                        .deepInner(DummyInnerBean.builder().name("Inner-2")//
                                .deepInner(DummyInnerBean.builder().name("Inner-3")//
                                        .build())
                                .build())
                        .build())//
                .build();

        //** Prueba execFunction simple:
        assertNull(OrderUtil.execFunction(bean, DummyOuterBean::getNum));

        //** Prueba Inner-1: ordeno la lista por un campo en nivel 1 de anidación
        List<Function<? extends Serializable, ? extends Serializable>> listGetters = new ArrayList<>();
        // CUIDADO!! no puedo añadir el getter así: listGetters.add(DummyOuterBean::getInnerBean); //da problemas de tipado porque estoy usando genéricos
        listGetters.add(p -> ((DummyOuterBean)p).getInnerBean());
        listGetters.add(p -> ((DummyInnerBean)p).getName());
        assertEquals(bean.getInnerBean().getName(), (String)OrderUtil.execNestedFunctions(bean, listGetters));

        //** Prueba Inner-2
        listGetters = new ArrayList<>();
        listGetters.add(p -> ((DummyOuterBean)p).getInnerBean());
        listGetters.add(p -> ((DummyInnerBean)p).getDeepInner());
        listGetters.add(p -> ((DummyInnerBean)p).getName());
        assertEquals(bean.getInnerBean().getDeepInner().getName(), (String)OrderUtil.execNestedFunctions(bean, listGetters));

        //** Prueba Inner-3
        listGetters = new ArrayList<>();
        listGetters.add(p -> ((DummyOuterBean)p).getInnerBean());
        listGetters.add(p -> ((DummyInnerBean)p).getDeepInner());
        listGetters.add(p -> ((DummyInnerBean)p).getDeepInner());
        listGetters.add(p -> ((DummyInnerBean)p).getName());
        assertEquals(bean.getInnerBean().getDeepInner().getDeepInner().getName(), (String)OrderUtil.execNestedFunctions(bean, listGetters));

        //** Prueba con null: consulto Inner-3 pero Inner-2 es null
        listGetters = new ArrayList<>();
        listGetters.add(p -> ((DummyOuterBean)p).getInnerBean());
        listGetters.add(p -> ((DummyInnerBean)p).getDeepInner());
        listGetters.add(p -> ((DummyInnerBean)p).getDeepInner());
        listGetters.add(p -> ((DummyInnerBean)p).getName());
        bean.getInnerBean().setDeepInner(null);
        assertNull((String)OrderUtil.execNestedFunctions(bean, listGetters));

    }

    @Test
    public void testIllegalArgumentException() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Param 'mapOrderBy' must be LinkedHashMap type");

        OrderUtil.sortList(lista, new HashMap<>());
    }

    @Test
    public void testNullList() {

        assertNull(OrderUtil.sortList(null, new LinkedHashMap<>()));
    }

    @Test
    public void testEmptyList() {

        List<String> list = new ArrayList<>();
        OrderUtil.sortList(list, new LinkedHashMap<>());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testEmptyOrders() {

        List<DummyOuterBean> list = new ArrayList<>();
        list.add(new DummyOuterBean("4", "4", 1L, false, null));
        list.add(new DummyOuterBean("2", "2", 2L, false, null));

        OrderUtil.sortList(list, new LinkedHashMap<>());
        assertEquals("4", list.get(0).getName());
    }

    private static List<Function<? extends Serializable, ? extends Serializable>> buildListNestedGettersInner3Name() {
        List<Function<? extends Serializable, ? extends Serializable>> list = new ArrayList<Function<? extends Serializable, ? extends Serializable>>();

        return list;

    }

    /** Recupera el valor del campo anidado Inner*/
    Function<DummyOuterBean, ? extends Serializable> getDeepInnerName = (bean) -> {
        List<Function<? extends Serializable, ? extends Serializable>> listGetters = new ArrayList<>();
        listGetters = new ArrayList<>();
        listGetters.add(p -> ((DummyOuterBean)p).getInnerBean());
        listGetters.add(p -> ((DummyInnerBean)p).getName());
        return OrderUtil.execNestedFunctions(bean, listGetters);
    };

    /** Prueba de ordenación por distintos campos con distinto nivel de anidamiento */
    @Test
    public void testSortNestedStrings() {

        Map<Function<DummyOuterBean, ? extends Serializable>, OrderBy.Sort> mapOrders = new LinkedHashMap<Function<DummyOuterBean, ? extends Serializable>, OrderBy.Sort>();
        mapOrders.put(p -> ((DummyOuterBean)p).getSurname(), Sort.ASC);
        mapOrders.put(p -> ((DummyOuterBean)p).getName(), Sort.ASC);
        mapOrders.put(getDeepInnerName, Sort.DESC);

        OrderUtil.sortList(lista, mapOrders);

        assertTrue(lista.get(0).getInnerBean().getName().equals("D"));
        assertTrue(lista.get(1).getInnerBean().getName().equals("C"));
        assertTrue(lista.get(2).getInnerBean().getName().equals("B"));
        assertTrue(lista.get(3).getInnerBean().getName().equals("A"));

        assertTrue(lista.get(0).getName().equals("Ol"));
        assertTrue(lista.get(1).getName() == null);
        assertTrue(lista.get(2).getName().equals("Ca"));
        assertTrue(lista.get(3).getName().equals("Ju"));
        assertTrue(lista.get(4).getName() == null);
        assertTrue(lista.get(5).getName().equals(""));
        assertTrue(lista.get(6).getName().equals("Am"));
        assertTrue(lista.get(7).getName().equals("Az"));
        assertTrue(lista.get(8).getName().equals("Er"));
        assertTrue(lista.get(9).getName().equals("Er"));
        assertTrue(lista.get(10).getName().equals("Wo"));

        assertTrue(lista.get(0).getSurname().equals("Za"));
        assertTrue(lista.get(1).getSurname().equals("Yf"));
        assertTrue(lista.get(2).getSurname().equals("Pe"));
        assertTrue(lista.get(3).getSurname() == null);
        assertTrue(lista.get(4).getSurname() == null);
        assertTrue(lista.get(5).getSurname().equals("Li"));
        assertTrue(lista.get(6).getSurname().equals("Go"));
        assertTrue(lista.get(7).getSurname().equals("Ga"));
        assertTrue(lista.get(8).getSurname().equals("Bi"));
        assertTrue(lista.get(9).getSurname().equals("Gu"));
        assertTrue(lista.get(10).getSurname().equals(""));

    }

    @Test
    public void testSortStringASCASC() {

        Map<Function<DummyOuterBean, ? extends Serializable>, OrderBy.Sort> mapOrders = new LinkedHashMap<Function<DummyOuterBean, ? extends Serializable>, OrderBy.Sort>();
        mapOrders.put(p -> ((DummyOuterBean)p).getSurname(), Sort.ASC);
        mapOrders.put(p -> ((DummyOuterBean)p).getName(), Sort.ASC);

        OrderUtil.sortList(lista, mapOrders);
        assertNull(lista.get(0).getName());
        assertNull(lista.get(1).getName());
        assertTrue(lista.get(2).getName().equals(""));
        assertTrue(lista.get(3).getName().equals("Am"));
        assertTrue(lista.get(4).getName().equals("Az"));
        assertTrue(lista.get(5).getName().equals("Ca"));
        assertTrue(lista.get(6).getName().equals("Er"));
        assertTrue(lista.get(7).getName().equals("Er"));
        assertTrue(lista.get(8).getName().equals("Ju"));
        assertTrue(lista.get(9).getName().equals("Ol"));
        assertTrue(lista.get(10).getName().equals("Wo"));

        assertNull(lista.get(0).getSurname());
        assertTrue(lista.get(1).getSurname().equals("Yf"));
        assertTrue(lista.get(2).getSurname().equals("Li"));
        assertTrue(lista.get(3).getSurname().equals("Go"));
        assertTrue(lista.get(4).getSurname().equals("Ga"));
        assertTrue(lista.get(5).getSurname().equals("Pe"));
        assertTrue(lista.get(6).getSurname().equals("Bi"));
        assertTrue(lista.get(7).getSurname().equals("Gu"));
        assertNull(lista.get(8).getSurname());
        assertTrue(lista.get(9).getSurname().equals("Za"));
        assertTrue(lista.get(10).getSurname().equals(""));

    }

    @Test
    public void testSortStringDESCASC() {

        Map<Function<DummyOuterBean, ? extends Serializable>, OrderBy.Sort> mapOrders = new LinkedHashMap<Function<DummyOuterBean, ? extends Serializable>, OrderBy.Sort>();
        mapOrders.put(p -> ((DummyOuterBean)p).getSurname(), Sort.ASC);
        mapOrders.put(p -> ((DummyOuterBean)p).getName(), Sort.DESC);

        OrderUtil.sortList(lista, mapOrders);
        assertTrue(lista.get(0).getName().equals("Wo"));
        assertTrue(lista.get(1).getName().equals("Ol"));
        assertTrue(lista.get(2).getName().equals("Ju"));
        assertTrue(lista.get(3).getName().equals("Er"));
        assertTrue(lista.get(4).getName().equals("Er"));
        assertTrue(lista.get(5).getName().equals("Ca"));
        assertTrue(lista.get(6).getName().equals("Az"));
        assertTrue(lista.get(7).getName().equals("Am"));
        assertTrue(lista.get(8).getName().equals(""));
        assertNull(lista.get(9).getName());
        assertNull(lista.get(10).getName());
        assertTrue(lista.get(0).getSurname().equals(""));
        assertTrue(lista.get(1).getSurname().equals("Za"));
        assertNull(lista.get(2).getSurname());
        assertTrue(lista.get(3).getSurname().equals("Bi"));
        assertTrue(lista.get(4).getSurname().equals("Gu"));
        assertTrue(lista.get(5).getSurname().equals("Pe"));
        assertTrue(lista.get(6).getSurname().equals("Ga"));
        assertTrue(lista.get(7).getSurname().equals("Go"));
        assertTrue(lista.get(8).getSurname().equals("Li"));
        assertNull(lista.get(9).getSurname());
        assertTrue(lista.get(10).getSurname().equals("Yf"));

    }

    @Test
    public void testSortBooleanASC() {

        Map<Function<DummyOuterBean, ? extends Serializable>, OrderBy.Sort> mapOrders = new LinkedHashMap<Function<DummyOuterBean, ? extends Serializable>, OrderBy.Sort>();
        mapOrders.put(DummyOuterBean::isBool, Sort.ASC);

        OrderUtil.sortList(lista, mapOrders);
        assertFalse(lista.get(0).isBool());
        assertFalse(lista.get(1).isBool());
        assertFalse(lista.get(2).isBool());
        assertFalse(lista.get(3).isBool());
        assertTrue(lista.get(8).isBool());
        assertTrue(lista.get(9).isBool());

    }

    @Test
    public void testSortNumberDESC() {

        Map<Function<DummyOuterBean, ? extends Serializable>, OrderBy.Sort> mapOrders = new LinkedHashMap<Function<DummyOuterBean, ? extends Serializable>, OrderBy.Sort>();
        mapOrders.put(DummyOuterBean::getNum, Sort.DESC);

        OrderUtil.sortList(lista, mapOrders);

        assertTrue(10L == lista.get(0).getNum());
        assertTrue(9L == lista.get(1).getNum());
        assertTrue(8L == lista.get(2).getNum());
        assertTrue(7L == lista.get(3).getNum());
        assertTrue(6L == lista.get(4).getNum());
        assertTrue(5L == lista.get(5).getNum());
        assertTrue(4L == lista.get(6).getNum());
        assertTrue(3L == lista.get(7).getNum());

    }

}



package examples.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import examples.java8.beans.boxes.BoxDto;

public class SortLists {

    public static void main(String[] args) {

        sortListStringWithStreamSorted();

        sortBoxesByNameWithCollectionsSort();
    }

    public static void sortListStringWithStreamSorted() {
        //from mkyong

        System.out.println("\nEXAMPLE1: reverse sort List<String> with Stream.Sorted.Comparator");

        List<String> list1 = Arrays.asList("9", "c", "A", "Z", "1", "B", "Y", "4", "a");

        List<String> sortedList1 = list1.stream() //
                .sorted(Comparator.reverseOrder()) //
                .collect(Collectors.toList());

        sortedList1.forEach(System.out::println);

        System.out.println("\nEXAMPLE2: reverse sort List<String> with Stream.Sorted.Lambda");

        List<String> list2 = Arrays.asList("9", "c", "A", "Z", "1", "B", "Y", "4", "a");

        List<String> sortedList2 = list2.stream()//
                .sorted((o1, o2) -> o2.compareTo(o1))//
                .collect(Collectors.toList());

        sortedList2.forEach(System.out::println);
    }

    public static void sortBoxesByNameWithCollectionsSort() {
        System.out.println("\nEXAMPLE3:  sort list<BoxDto> by name ASC with Collections.sort");
        List<BoxDto> l = new ArrayList<>();
        l.add(BoxDto.builder().name("c").build());
        l.add(BoxDto.builder().name("a").build());
        l.add(BoxDto.builder().name("Z").build());
        l.add(BoxDto.builder().name("A").build());
        l.add(BoxDto.builder().name("2").build());
        l.add(BoxDto.builder().name("C").build());
        l.add(BoxDto.builder().name("B").build());
        l.add(BoxDto.builder().name("1").build());

        Collections.sort(l, (BoxDto o1, BoxDto o2) -> //
        o1.getName().compareTo(o2.getName()));

        l.stream().forEach(System.out::println);

    }
}

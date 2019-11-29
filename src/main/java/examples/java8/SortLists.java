



package examples.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import examples.java8.beans.BoxDto;

public class SortLists {

    public static void main(String[] args) {

        streamSorted();

        sortBoxesByName();
    }

    public static void streamSorted() {
        //from mkyong

        List<String> list = Arrays.asList("9", "A", "Z", "1", "B", "Y", "4", "a", "c");

        /*
        List<String> sortedList = list.stream()
            .sorted((o1,o2)-> o2.compareTo(o1))
            .collect(Collectors.toList());
        */

        List<String> sortedList = list.stream() //
                .sorted(Comparator.reverseOrder()) //
                .collect(Collectors.toList());

        sortedList.forEach(System.out::println);
    }

    public static void sortBoxesByName() {
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

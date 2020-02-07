




package examples.java8;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import examples.java8.beans.boxes.BoxDto;
import examples.java8.beans.boxes.FeatureDto;
import examples.java8.beans.boxes.StorageDto;
import examples.java8.utils.Util;

public class StreamsExamples {

    Supplier<List<StorageDto>> lista = () -> {
        return Util.newList(//
                StorageDto.builder()//
                        .name("S1")//
                        .listBoxes(Collections.singletonList(BoxDto.builder().name("B1").ft(new FeatureDto("F_B1")).build()))//
                        .ft(new FeatureDto("F_S1"))
                        .build(),
                StorageDto.builder()//
                        .name("S2")//
                        .listBoxes(Collections.singletonList(BoxDto.builder().name("B2").ft(new FeatureDto("F_B1")).build()))//
                        .ft(new FeatureDto("F_S2"))
                        .build(),
                StorageDto.builder()//
                        .name("S3")//
                        .listBoxes(Util.newList(//
                                BoxDto.builder().name("B3a").build(),//
                                BoxDto.builder().name("B3b").ft(new FeatureDto("F_B3b")).build()))//
                        .ft(new FeatureDto("F_S3"))
                        .build());

    };

    public static void main(String[] args) {
        System.out.println("STREAMS EXAMPLES");

        IntStream stream = IntStream.range(1, 5).parallel();

        //* peek: This method exists mainly to support debugging, where you want to see the elements as they flow past a certain point in a pipeline
        stream = stream.peek(i -> System.out.println("starting " + i)).filter(i -> {
            System.out.println("filtering " + i);
            return i % 2 == 0;
        }).peek(i -> System.out.println("post filtering " + i));
        System.out.println("Invoking terminal method count.");
        System.out.println("The count is " + stream.count());

        System.out.println("----------");

    }

}

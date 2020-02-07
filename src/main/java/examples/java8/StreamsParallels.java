

package examples.java8;

import java.util.stream.IntStream;

public class StreamsParallels {

    public static void main(String[] args) {
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

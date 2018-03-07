package streams;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExamples {

    public static void main(String[] args) {

        /* limit with Predicate */
        // Warning unordered stream: The predicate needs to be satisfied only once to change the state of elements being
        // passed to the output
        System.out.println(
                Stream.of("a", "b", "c", "", "e")
                        .takeWhile(s -> !s.isEmpty())
                        .collect(Collectors.joining())); // Print 'abc'

        /* skip with Predicate */
        // Warning unordered stream: The predicate needs to be satisfied only once to change the state of elements being
        // passed to the output
        System.out.println(
                Stream.of("a", "b", "c", "de", "f")
                        .dropWhile(s -> s.length() <= 1)
                        .collect(Collectors.joining())); // Print 'def'

        /* iterate on stream */
        // Dirty
        Stream.iterate(1, i -> 2 * i)
                .limit(10)
                .forEach(System.out::println);
        // output: 1 2 4 8 ... 512

        // Elegant
        // a middle predicate that is used to access each element before it is put into the stream
        Stream.iterate(1, i -> i <= 512, i -> 2 * i)
                .forEach(System.out::println);

        /* ofNullable */
        System.out.println(
                Stream.ofNullable("42")
                        .count()); // one
        System.out.println(
                Stream.ofNullable(null)
                        .count()); // zero

    }

}

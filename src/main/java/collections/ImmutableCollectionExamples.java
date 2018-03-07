package collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Collections examples
 */
public class ImmutableCollectionExamples {

    public static void main(String[] args) {
        // Java 8
        List<Point> myList = new ArrayList<>();
        myList.add(new Point(1, 1));
        myList.add(new Point(2, 2));
        myList.add(new Point(3, 3));
        myList.add(new Point(4, 4));
        myList = Collections.unmodifiableList(myList);
        try {
            myList.add(new Point(5, 5));
            System.out.println("List updated");
        } catch (java.lang.UnsupportedOperationException e) {
            System.err.println("Immutable list");
        }

        // Java 9
        List<Point> list = List.of(new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4));
        try {
            list.add(new Point(5, 5));
            System.out.println("List updated");
        } catch (java.lang.UnsupportedOperationException e) {
            System.err.println("Immutable list");
        }
    }

}

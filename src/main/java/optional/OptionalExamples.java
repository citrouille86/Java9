package optional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Optional examples
 */
public class OptionalExamples {

    public static void main(String[] args) {
        typicalNullPointer();

        emptyOptionalCreation();

        nonEmptyOptional();

        nullableOptional();

        getExample();

        orElseExample();

        orElseThrowExample();

        presentOrNotExamples();

        orExample();

        filterExample();

        mapExample();

        streamExample();

    }

    @SuppressWarnings("null") // Typical NPE, even the compiler is saying something that
    private static void typicalNullPointer() {
        try {
            String strNull0 = null;
            System.out.println(strNull0.contains("something"));
        } catch (NullPointerException ex) {
            System.err.println("expected nullpointer");
        }
    }

    private static void emptyOptionalCreation() {
        try {
            /* empty */
            Optional<String> emptyOptional = Optional.empty();
            System.out.println(emptyOptional.get());
        } catch (NoSuchElementException ex) {
            System.err.println("expected NoSuchElementException");
        }
    }

    private static void nonEmptyOptional() {
        /* non empty */
        String str = "aRealString";
        Optional<String> nonEmptyOptional = Optional.of(str);
        System.out.println(nonEmptyOptional.get());
    }

    private static void nullableOptional() {
        try {/* ofNullable */
            String strNull = null;
            Optional<String> nullableOptional = Optional.ofNullable(strNull);
            System.out.println(nullableOptional.get());
        } catch (NoSuchElementException ex) {
            System.err.println("expected NoSuchElementException");
        }
    }

    private static void getExample() {
        /* get */
        try {
            String strNull = null;
            // cannot be passed, we should use nullable
            Optional<String> optionalString = Optional.of(strNull);
            System.out.println(
                    optionalString.get()
                            .contains("something"));
        } catch (NullPointerException ex) {
            System.err.println("expected nullpointer");
        }
    }

    private static void orElseExample() {
        /* orElse */
        Person personCreated = new Person("66");
        Person defaultPerson = new Person("19");

        // value is there
        Optional<Person> optionalPerson = Optional.of(personCreated);
        String age = optionalPerson.orElse(defaultPerson)
                .getAge();
        System.out.println("Person age: " + age);

        // else
        Optional<Person> optionalPerson2 = Optional.empty();
        age = optionalPerson2.orElse(defaultPerson)
                .getAge();
        System.out.println("Person age: " + age);
    }

    private static void orElseThrowExample() {
        try {
            /* orElseThrow */
            Person personNull = null;
            Optional<Person> optionalPersonNull = Optional.ofNullable(personNull);
            optionalPersonNull.orElseThrow(IllegalStateException::new);
        } catch (IllegalStateException ex) {
            System.err.println("expected IllegalStateException");
        }
    }

    private static void presentOrNotExamples() {
        /* isPresent */
        Optional<String> stringToUse = Optional.of("aString");
        if (stringToUse.isPresent()) {
            System.out.println(stringToUse.get());
        }

        /* ifPresent */
        Optional<String> anotherStringToUse = Optional.of("anotherString");
        anotherStringToUse.ifPresent(System.out::println);

        /* if not present */
        Optional<String> stringToUseNull = Optional.ofNullable(null);
        stringToUseNull.ifPresent(System.out::println);

        /* Java 9 enhancements: ifPresentOrElse */
        Optional<String> anotherStringToUseNull = Optional.ofNullable(null);
        anotherStringToUseNull.ifPresentOrElse(System.out::println, () -> System.out.println("it is not present"));
    }

    private static void orExample() {
        /* Java 9 enhancements: or */
        Optional<String> optString = Optional.of("test");
        Optional<String> newOptString = optString.or(() -> Optional.of("42"));
        System.out.println("a 'test' string: " + newOptString.get());

        // Chained
        Optional<Object> chainedOptString = Optional.empty()
                .or(() -> Optional.empty())
                .or(() -> Optional.of("42"));
        System.out.println("a '42' string: " + chainedOptString.get());
    }

    private static void filterExample() {
        /* filter */

        // if the value is not present
        Optional<Person> personOptionalEmpty = Optional.empty();
        personOptionalEmpty.filter(x -> "19".equals(x.getAge()))
                .ifPresent(x -> System.out.println(x.getAge() + " is ok!"));

        // if the value does not pass the filter
        Optional<Person> personOptionalExpensive = Optional.of(new Person("70"));
        personOptionalExpensive.filter(x -> "19".equals(x.getAge()))
                .ifPresent(x -> System.out.println(x.getAge() + " is ok!"));

        // if the value is present and does pass the filter
        Optional<Person> personOptionalOk = Optional.of(new Person("19"));
        personOptionalOk.filter(x -> "19".equals(x.getAge()))
                .ifPresent(x -> System.out.println(x.getAge() + " is ok!"));

    }

    private static void mapExample() {
        /* map */
        // non empty string map to its length
        Optional<String> stringOptional = Optional.of("loooooooong string");
        Optional<Integer> sizeOptional = stringOptional.map(String::length);

        System.out.println("size of string " + sizeOptional.orElse(0));

        // empty string map to its length -> we get 0 as length
        Optional<String> stringOptionalNull = Optional.ofNullable(null);
        Optional<Integer> sizeOptionalNull = stringOptionalNull.map(x -> x.length()); // we can use Lambdas as we want

        System.out.println("size of string " + sizeOptionalNull.orElse(0));

    }

    private static void streamExample() {
        /* Java 9 enhancements */
        List<Optional<String>> list = List
                .of(Optional.of(""), Optional.ofNullable(null), Optional.of("aString3"), Optional.of("aString4"));
        System.out.println("Optional count: " + list.size());

        // We want to get a stream of String
        // Java 8
        long count = list.stream()
                // here we have a Stream<Optional<String>>
                .filter(Optional::isPresent)
                .map(Optional::get)
                // here we have a Stream<String>
                .count();
        System.out.println("(Java 8) count: " + count);

        // Java 9
        count = list.stream()
                // here we have a Stream<Optional<String>>
                .flatMap(Optional::stream)
                // here we have a Stream<String>
                .count();
        System.out.println("(Java 9) count: " + count);

    }
}
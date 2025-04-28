package academy.javapro;

import java.util.*;
import java.util.function.BiFunction;

/**
 * This class demonstrates various operations for merging and zipping collections
 * using functional programming concepts.
 */
public class Midterm2 {

    /**
     * Represents a person with a name and age.
     */
    static class Person {
        private final String name;
        private final int age;

        /**
         * Constructs a new Person with the given name and age.
         *
         * @param name the name of the person
         * @param age the age of the person
         */
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        /**
         * Returns a string representation of the Person object.
         *
         * @return a string in the format: Person{name='[name]', age=[age]}
         */
        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }
    }

    /**
     * Merges two collections element-wise using a merge function.
     * Both collections must be of the same size.
     *
     * @param <T> the type of elements in the first collection
     * @param <S> the type of elements in the second collection
     * @param <R> the type of elements in the resulting collection
     * @param firstCollection the first collection to merge
     * @param secondCollection the second collection to merge
     * @param mergeFunction the function to apply to each pair of elements
     * @return a new list containing the merged results
     * @throws IllegalArgumentException if the collections have different sizes
     */
    public static <T, S, R> List<R> mergeCollections(
            Collection<T> firstCollection,
            Collection<S> secondCollection,
            BiFunction<T, S, R> mergeFunction) {

        if (firstCollection.size() != secondCollection.size()) {
            throw new IllegalArgumentException(
                    "Collections must have the same size for element-wise merging. " +
                            "First collection size: " + firstCollection.size() +
                            ", Second collection size: " + secondCollection.size());
        }

        List<R> result = new ArrayList<>(firstCollection.size());
        T[] firstArray = (T[]) firstCollection.toArray();
        S[] secondArray = (S[]) secondCollection.toArray();

        for (int i = 0; i < firstArray.length; i++) {
            result.add(mergeFunction.apply(firstArray[i], secondArray[i]));
        }

        return result;
    }

    /**
     * Zips two collections together using a merge function, stopping when the end of
     * the shorter collection is reached.
     *
     * @param <T> the type of elements in the first collection
     * @param <S> the type of elements in the second collection
     * @param <R> the type of elements in the resulting collection
     * @param firstCollection the first collection to zip
     * @param secondCollection the second collection to zip
     * @param mergeFunction the function to apply to each pair of elements
     * @return a new list containing the zipped results
     */
    public static <T, S, R> List<R> zipCollections(
            Collection<T> firstCollection,
            Collection<S> secondCollection,
            BiFunction<T, S, R> mergeFunction) {

        List<R> result = new ArrayList<>(Math.min(firstCollection.size(), secondCollection.size()));
        Iterator<T> firstIterator = firstCollection.iterator();
        Iterator<S> secondIterator = secondCollection.iterator();

        while (firstIterator.hasNext() && secondIterator.hasNext()) {
            result.add(mergeFunction.apply(firstIterator.next(), secondIterator.next()));
        }

        return result;
    }

    /**
     * Demonstrates the functionality of the mergeCollections and zipCollections methods.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Example 1: Merging numbers with their word representations
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<String> words = Arrays.asList("one", "two", "three", "four", "five");
        BiFunction<Integer, String, String> numberWordMerger = (num, word) -> num + " = " + word;
        List<String> combinedNumberWords = mergeCollections(numbers, words, numberWordMerger);

        System.out.println("Example 1: Merging numbers with their word representations");
        combinedNumberWords.forEach(System.out::println);

        // Example 2: Creating Person objects from names and ages
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        List<Integer> ages = Arrays.asList(25, 30, 22);
        BiFunction<String, Integer, Person> personCreator = Person::new;
        List<Person> people = mergeCollections(names, ages, personCreator);

        System.out.println("\nExample 2: Creating Person objects from names and ages");
        people.forEach(System.out::println);

        // Example 3: Mathematical operations
        List<Double> firstNumbers = Arrays.asList(1.5, 2.5, 3.5);
        List<Double> secondNumbers = Arrays.asList(0.5, 1.0, 1.5);
        BiFunction<Double, Double, Double> sum = Double::sum;
        BiFunction<Double, Double, Double> product = (a, b) -> a * b;
        BiFunction<Double, Double, String> comparisonResult =
                (a, b) -> a + " vs " + b + ": " + (a > b ? "First is larger" :
                        a < b ? "Second is larger" :
                                "Both are equal");

        List<Double> sums = zipCollections(firstNumbers, secondNumbers, sum);
        List<Double> products = zipCollections(firstNumbers, secondNumbers, product);
        List<String> comparisons = zipCollections(firstNumbers, secondNumbers, comparisonResult);

        System.out.println("\nExample 3: Mathematical operations");
        System.out.println("Sums: " + sums);
        System.out.println("Products: " + products);
        System.out.println("Comparisons:");
        comparisons.forEach(System.out::println);

        // Example 4: Using different sized collections
        List<Character> letters = Arrays.asList('A', 'B', 'C', 'D', 'E');
        List<Integer> positions = Arrays.asList(1, 2, 3);
        BiFunction<Character, Integer, String> positionedLetter =
                (letter, position) -> position + ". " + letter;

        List<String> lettersWithPositions = zipCollections(letters, positions, positionedLetter);

        System.out.println("\nExample 4: Using different sized collections");
        lettersWithPositions.forEach(System.out::println);
    }
}
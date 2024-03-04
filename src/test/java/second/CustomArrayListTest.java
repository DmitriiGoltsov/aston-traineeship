package second;

import org.goltsov.aston.second.homework.CustomArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomArrayListTest {

    private List<Integer> testList;

    private ListIterator<Integer> testIterator;

    private List<Integer> emptyList;

    private List<Integer> listToAdd;

    @BeforeEach
    void beforeEach() {
        testList = new CustomArrayList<>();
        testIterator = testList.listIterator();
        emptyList = new ArrayList<>();
        listToAdd = new ArrayList<>();
        listToAdd.add(4);
        listToAdd.add(5);
        listToAdd.add(6);
    }


    @Test
    void size() {
        assertThat(testList.size()).isEqualTo(0);

        testList.add(5);
        testList.add(7);

        assertThat(testList.size()).isEqualTo(2);

        testList.remove(0);

        assertThat(testList.size()).isEqualTo(1);
    }

    @Test
    void isEmpty() {
        assertTrue(testList.isEmpty());

        testList.add(7);

        assertFalse(testList.isEmpty());
    }

    @Test
    void contains() {

        int elementToFind = 7;
        int elementNotToFind = 109;

        for (int i = 0; i <= 108; i++) {
            testList.add(i);
        }

        assertTrue(testList.contains(elementToFind));
        assertFalse(testList.contains(elementNotToFind));
    }

    @Test
    void toArray() {
        Object[] expected1 = {};
        Object[] expected2 = {1, 2, 3};

        Object[] actual1 = testList.toArray();

        testList.add(1);
        testList.add(2);
        testList.add(3);

        Object[] actual2 = testList.toArray();

        assertArrayEquals(expected1, actual1);
        assertArrayEquals(expected2, actual2);
    }

    @Test
    void testToArray() {
        // to do
    }

    @Test
    void add() {
        assertTrue(testList.add(5));
        assertTrue(testList.add(7));
        assertThat(testList.get(0)).isEqualTo(5);
        assertThat(testList.get(1)).isEqualTo(7);
    }

    @Test
    void removeByIndex() {

        assertThrows(IndexOutOfBoundsException.class, () -> testList.remove(150));

        for (int i = 0; i < 5; i++) {
            testList.add(i);
        }

        assertThat(testList.remove(0)).isEqualTo(0);
        assertThat(testList).hasSize(4);

        assertThat(testList.remove(0)).isEqualTo(1);
        assertThat(testList).hasSize(3);

        assertThat(testList.remove(0)).isEqualTo(2);
        assertThat(testList).hasSize(2);
    }

    @Test
    void containsAll() {

        assertFalse(testList.containsAll(List.of(4, 3)));

        testList.add(3);
        testList.add(47);
        testList.add(15);
        testList.add(154);

        assertTrue(testList.containsAll(List.of(3, 154, 15)));
        assertFalse(testList.containsAll(List.of()));
    }

    @Test
    void addAll() {

        testList.add(7);
        testList.add(9);

        int initialSize = testList.size();

        assertFalse(testList.addAll(emptyList));
        assertTrue(testList.addAll(listToAdd));
        assertThat(testList).hasSize(initialSize + listToAdd.size());
        assertThat(testList.get(0)).isEqualTo(7);
        assertThat(testList.get(1)).isEqualTo(9);
        assertThat(testList.get(2)).isEqualTo(4);
    }

    @Test
    void testAddAllWithIndex() {

        testList.add(7);
        testList.add(9);

        int initialSize = testList.size();

        assertFalse(testList.addAll(0, emptyList));
        assertTrue(testList.addAll(0, listToAdd));
        assertThat(testList).hasSize(initialSize + listToAdd.size());
        assertThat(testList.get(0)).isEqualTo(4);
        assertThat(testList.get(1)).isEqualTo(5);
        assertThat(testList.get(2)).isEqualTo(6);
        assertThat(testList.get(3)).isEqualTo(7);
        assertThat(testList.get(4)).isEqualTo(9);
    }

    @Test
    void removeAll() {

        testList.addAll(listToAdd);
        assertTrue(testList.removeAll(listToAdd));

        assertThat(testList).isEmpty();
    }

    @Test
    void retainAll() {
        testList.addAll(listToAdd);
        for (int i = 50; i < 53; i++) {
            testList.add(i);
        }

        assertTrue(testList.retainAll(listToAdd));
        assertThat(testList).hasSize(3);
    }

    @Test
    void clear() {
        testList.addAll(listToAdd);
        testList.clear();
        assertThat(testList).isEmpty();
    }

    @Test
    void get() {
        assertThrows(IndexOutOfBoundsException.class, () -> testList.get(0));
        testList.addAll(listToAdd);
        assertThat(testList.get(0)).isEqualTo(4);
        assertThat(testList.get(1)).isEqualTo(5);
        assertThat(testList.get(2)).isEqualTo(6);
    }

    @Test
    void set() {
        int numberToSet = 99;
        int setNumberIndex = 0;
        testList.addAll(listToAdd);
        assertThat(testList.set(setNumberIndex, numberToSet)).isEqualTo(listToAdd.get(setNumberIndex));
        assertThat(testList.get(0)).isEqualTo(numberToSet);
    }

    @Test
    void testAddWithIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> testList.add(6, 12));

        for (int i = 0; i < 10; i++) {
            testList.add(i);
        }

        testList.add(1, 99);

        assertThat(testList).hasSize(11);
        assertThat(testList.get(1)).isEqualTo(99);
        assertThat(testList.get(0)).isEqualTo(0);
        assertThat(testList.get(2)).isEqualTo(1);
    }

    @Test
    void testRemoveWithElementPerSe() {

        Integer elementToRemove = 5;

        testList.addAll(listToAdd);

        assertTrue(testList.remove(elementToRemove));
        assertThat(testList).hasSize(2);
        assertFalse(testList.contains(5));
    }

    @Test
    void indexOf() {

        testList.addAll(listToAdd);
        testList.add(4);

        assertThat(testList.indexOf(6)).isEqualTo(2);
        assertThat(testList.indexOf(99)).isEqualTo(-1);
        assertThat(testList.indexOf(4)).isEqualTo(0);
    }

    @Test
    void lastIndexOf() {

        testList.addAll(listToAdd);
        testList.add(4);

        assertThat(testList.lastIndexOf(99)).isEqualTo(-1);
        assertThat(testList.lastIndexOf(4)).isEqualTo(3);
    }

    @Test
    void listIterator() {
        assertThat(testIterator).isInstanceOf(ListIterator.class);
    }

    @Test
    void subList() {

        testList.addAll(listToAdd);
        List<Integer> expected = List.of(5,6);

        assertThrows(IndexOutOfBoundsException.class, () -> testList.subList(2, 9));
        assertThrows(IndexOutOfBoundsException.class, () -> testList.subList(9, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> testList.subList(-3, 2));

        List<Integer> actual = testList.subList(1, 2);
        assertArrayEquals(actual.toArray(), expected.toArray());
    }
}

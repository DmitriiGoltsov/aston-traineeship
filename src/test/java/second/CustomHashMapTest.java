package second;

import org.goltsov.aston.second.homework.CustomHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomHashMapTest {

    private CustomHashMap<Long, String> emptyHashMap;

    private CustomHashMap<Long, String> hashMap;


    @BeforeEach
    void beforeEach() {
        emptyHashMap = new CustomHashMap<>();
        hashMap = new CustomHashMap<>();
        hashMap.put(1L, "Vladimir");
        hashMap.put(2L, "Fjodor");
        hashMap.put(3L, "Pjotr");
    }

    @Test
    void hasMapPutTest() {
        emptyHashMap.put(1L, "Aleksej");
        emptyHashMap.put(2L, "Ljuka");

        assertThat(emptyHashMap.size()).isEqualTo(2);
        assertThat(emptyHashMap.get(1L)).isEqualTo("Aleksej");
        assertThat(emptyHashMap.get(2L)).isEqualTo("Ljuka");
    }

    @Test
    void hashMapGetTest() {
        assertThat(emptyHashMap.get(2L)).isNull();

        assertThat(hashMap.get(1L)).isEqualTo("Vladimir");
        assertThat(hashMap.get(2L)).isEqualTo("Fjodor");

        assertThat(hashMap.get(4L)).isNull();
    }

    @Test
    void containsKeyTest() {
        assertFalse(hashMap.containsKey(99L));
        assertTrue(hashMap.containsKey(1L));
        assertTrue(hashMap.containsKey(3L));
    }

    @Test
    void removeTest() {
        assertThat(hashMap.remove(99L)).isNull();
        assertThat(hashMap.remove(1L)).isEqualTo("Vladimir");
        assertThat(hashMap.size()).isEqualTo(2);
        assertThat(hashMap.remove(2L)).isEqualTo("Fjodor");
        assertThat(hashMap.size()).isEqualTo(1);
        assertThat(hashMap.remove(3L)).isEqualTo("Pjotr");
        assertThat(hashMap.size()).isEqualTo(0);
    }

    @Test
    void sizeTest() {
        assertThat(emptyHashMap.size()).isEqualTo(0);
        assertThat(hashMap.size()).isEqualTo(3);

        emptyHashMap.put(9L, "Jan");
        assertThat(emptyHashMap.size()).isEqualTo(1);
    }

    @Test
    void putAllTest() {
        assertThrows(NullPointerException.class, () -> hashMap.putAll(null));

        emptyHashMap.put(3L, "Aleksej");
        emptyHashMap.put(4L, "Ljuka");

        hashMap.putAll(emptyHashMap);

        assertThat(hashMap).hasSize(4);
        assertThat(hashMap).containsEntry(3L, "Aleksej");
        assertThat(hashMap).doesNotContainValue("Pjotr");
    }

    @Test
    void clearTest() {
        hashMap.clear();

        assertThat(hashMap).hasSize(0);
        assertThat(hashMap).doesNotContainValue("Fjodor");
        assertThat(hashMap.get(1L)).isNull();
    }

    @Test
    void keySetTest() {
        Set<Long> expected = Set.of(1L, 2L, 3L);
        Set<Long> actual = hashMap.keySet();

        assertThat(actual).containsAll(expected);
    }

    @Test
    void valueTest() {
        Collection<String> expected = List.of("Vladimir", "Fjodor", "Pjotr");
        Collection<String> actual = hashMap.values();

        assertThat(actual).containsAll(expected);
    }


}

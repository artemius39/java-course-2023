package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task3Test {
    private void test(int[] smaller, int[] bigger, boolean expected) {
        assertThat(Task3.isNestable(smaller, bigger)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Basic true case")
    public void basicTestTrue() {
        test(new int[]{1, 2, 3}, new int[]{0, 4}, true);
    }

    @Test
    @DisplayName("Basic false case")
    public void basicTestFalse() {
        test(new int[]{1, 5}, new int[]{0, 4}, false);
    }

    @Test
    @DisplayName("Null array")
    public void nullArrays() {
        assertThrows(NullPointerException.class, () -> Task3.isNestable(null, new int[]{1, 2, 3}));
        assertThrows(NullPointerException.class, () -> Task3.isNestable(new int[]{1, 2, 3}, null));
    }

    @Test
    @DisplayName("Empty array")
    public void emptyArray() {
        test(new int[]{}, new int[]{1, 2, 3}, false);
        test(new int[]{1, 2, 3}, new int[]{}, false);
    }

    @Test
    @DisplayName("Same min")
    public void sameMin() {
        test(new int[]{1, 2, 3}, new int[]{4, 5, 1}, false);
    }

    @Test
    @DisplayName("Same max")
    public void sameMax() {
        test(new int[]{5, 2, 3}, new int[]{4, 5, 1}, false);
    }

    @Test
    @DisplayName("Only one condition is met")
    public void oneCondition() {
        // max(a1) < max(a2), but min(a2) > min(a1)
        test(new int[]{5, 2, 3}, new int[]{4, 10, 4}, false);
        // min(a2) < min(a1), but max(a1) > max(a2)
        test(new int[]{5, 2, 3}, new int[]{1, 1, 4}, false);
    }
}

package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw5.Task6.isSubsequence;
import static org.assertj.core.api.Assertions.assertThat;

class IsSubsequenceTest {
    @Test
    @DisplayName("Searching self")
    void searchingForSelf() {
        assertThat(isSubsequence("bibaboba", "bibaboba")).isTrue();
    }

    @Test
    @DisplayName("Searching substring")
    void searchingForSubstring() {
        assertThat(isSubsequence("xxxabcxxx", "abc")).isTrue();
    }

    @Test
    @DisplayName("Searching subsequence")
    void searchingForSubsequence() {
        assertThat(isSubsequence("1212a1132b1443c1312421", "abc")).isTrue();
    }

    @Test
    @DisplayName("Special characters")
    void specialCharacters() {
        assertThat(isSubsequence("...***...*||||.$^", "**.|$^")).isTrue();
    }
}

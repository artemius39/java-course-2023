package edu.hw7.task4;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

interface PiComputerTest {
   PiComputer getComputer();

   @Test
   @DisplayName("Precision")
   default void precision() {
       PiComputer computer = getComputer();

       double estimate = computer.compute(1_000_000_000);

       assertThat(estimate).isCloseTo(Math.PI, Offset.offset(1e-3));
   }
}

package edu.hw2.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static edu.hw2.task4.CallingInfo.callingInfo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CallingInfoTest {
    private void exception() throws Exception {
        throw new Exception();
    }

    @Test
    @DisplayName("Null throwable")
    void nullThrowable() {
        assertThrows(NullPointerException.class, () -> callingInfo(null));
    }

    @Test
    @DisplayName("Empty stacktrace")
    void emptyStackTrace() {
        Throwable throwable = new Throwable();
        throwable.setStackTrace(new StackTraceElement[0]);

        CallingInfo callingInfo = callingInfo(throwable);

        assertThat(callingInfo).isNull();
    }

    @Test
    @DisplayName("One stack trace element")
    void oneElement() {
        CallingInfo callingInfo = null;

        try {
            exception();
        } catch (Exception e) {
            Throwable throwable = new Throwable();
            throwable.setStackTrace(new StackTraceElement[]{e.getStackTrace()[0]});
            callingInfo = callingInfo(throwable);
        }

        assertThat(callingInfo).isNotNull();
        assertThat(callingInfo.className()).isEqualTo(this.getClass().getName());
        assertThat(callingInfo.methodName()).isEqualTo("exception");
    }

    @Test
    @DisplayName("Multiple stack trace elements")
    void multipleElements() {
        CallingInfo callingInfo = null;

        try {
            exception();
        } catch (Exception e) {
            callingInfo = callingInfo(e);
        }

        assertThat(callingInfo).isNotNull();
        assertThat(callingInfo.className()).isEqualTo(this.getClass().getName());
        assertThat(callingInfo.methodName()).isEqualTo("exception");
    }
}

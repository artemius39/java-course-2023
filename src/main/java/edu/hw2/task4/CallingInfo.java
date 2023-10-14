package edu.hw2.task4;

import java.util.Objects;

public record CallingInfo(String className, String methodName) {
    public static CallingInfo callingInfo(Throwable throwable) {
        Objects.requireNonNull(throwable);

        StackTraceElement[] stackTrace = throwable.getStackTrace();
        if (stackTrace.length == 0) {
            return null;
        }

        StackTraceElement lastCall = stackTrace[0];
        return new CallingInfo(lastCall.getClassName(), lastCall.getMethodName());
    }
}

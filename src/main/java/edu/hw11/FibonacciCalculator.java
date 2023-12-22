package edu.hw11;

import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;

public final class FibonacciCalculator {
    private static final String METHOD_NAME = "fib";

    public static long fib(int n) {
        try (var unloaded = new ByteBuddy()
                .subclass(Object.class)
                .defineMethod(METHOD_NAME, long.class, Opcodes.ACC_PUBLIC)
                .withParameters(int.class)
                .intercept(new Impl())
                .make()) {
            Class<?> clazz = unloaded
                    .load(FibonacciCalculator.class.getClassLoader())
                    .getLoaded();
            Object instance = clazz.getConstructor().newInstance();
            return (long) clazz
                    .getDeclaredMethod(METHOD_NAME, int.class)
                    .invoke(instance, n);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException
                 | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("MagicNumber")
    private static ByteCodeAppender.Size fib(MethodVisitor mv) {
        Label loopEnd = new Label();
        Label loopStart = new Label();
        Label nIsNotZero = new Label();
        mv.visitCode();

        // if n == 0 return 0
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitIntInsn(Opcodes.ILOAD, 1);
        mv.visitJumpInsn(Opcodes.IF_ICMPNE, nIsNotZero);
        mv.visitInsn(Opcodes.LCONST_0);
        mv.visitInsn(Opcodes.LRETURN);

        mv.visitLabel(nIsNotZero);
        mv.visitFrame(
                Opcodes.F_SAME,
                0, null,
                0, null
        );
        // int prev = 1
        mv.visitInsn(Opcodes.LCONST_1);
        mv.visitIntInsn(Opcodes.LSTORE, 2);

        // int curr = 1
        mv.visitInsn(Opcodes.LCONST_1);
        mv.visitIntInsn(Opcodes.LSTORE, 4);

        // int i = 2
        mv.visitInsn(Opcodes.ICONST_2);
        mv.visitIntInsn(Opcodes.ISTORE, 6);

        mv.visitLabel(loopStart);
        mv.visitFrame(
                Opcodes.F_APPEND,
                3, new Object[] {Opcodes.LONG, Opcodes.LONG, Opcodes.INTEGER}, // prev, curr, i
                1, new Object[] {Opcodes.LONG} // next
        );
        // if (i >= n) goto end of loop
        mv.visitIntInsn(Opcodes.ILOAD, 6);
        mv.visitIntInsn(Opcodes.ILOAD, 1);
        mv.visitJumpInsn(Opcodes.IF_ICMPGE, loopEnd);

        // long next = prev + curr
        mv.visitIntInsn(Opcodes.LLOAD, 4);
        mv.visitIntInsn(Opcodes.LLOAD, 2);
        mv.visitInsn(Opcodes.LADD);
        mv.visitIntInsn(Opcodes.LSTORE, 7);

        // prev = curr
        mv.visitIntInsn(Opcodes.LLOAD, 4);
        mv.visitIntInsn(Opcodes.LSTORE, 2);

        // curr = next
        mv.visitIntInsn(Opcodes.LLOAD, 7);
        mv.visitIntInsn(Opcodes.LSTORE, 4);

        // i++
        mv.visitIincInsn(6, 1);

        // goto loop start
        mv.visitJumpInsn(Opcodes.GOTO, loopStart);

        // return curr
        mv.visitLabel(loopEnd);
        mv.visitFrame(
                Opcodes.F_SAME,
                0, null,
                1, new Object[] {Opcodes.LONG} // curr
        );
        mv.visitIntInsn(Opcodes.LLOAD, 4);
        mv.visitInsn(Opcodes.LRETURN);

        mv.visitEnd();
        return new ByteCodeAppender.Size(4, 9);
    }

    private FibonacciCalculator() {
    }

    private static class Impl implements Implementation {

        @Override
        public @NotNull ByteCodeAppender appender(@NotNull Target target) {
            return (methodVisitor, context, methodDescription) -> fib(methodVisitor);
        }

        @Override
        public @NotNull InstrumentedType prepare(@NotNull InstrumentedType instrumentedType) {
            return instrumentedType;
        }
    }
}

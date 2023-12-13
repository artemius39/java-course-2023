package edu.hw10.task2;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

class CacheProxyTest {
    private interface Interface {
        int notCachedMethod(int n);

        @Cache
        int transientlyCachedMethod(int n);

        @Cache(persist = true)
        int persistentlyCachedMethod(int n);
    }

    private static class Impl implements Interface {
        private int notCachedMethodCallCount;
        private int transientlyCachedMethodCallCount;
        private int persistentlyCachedMethodCallCount;

        private Impl() {
            notCachedMethodCallCount = 0;
            transientlyCachedMethodCallCount = 0;
            persistentlyCachedMethodCallCount = 0;
        }


        @Override
        public int notCachedMethod(int n) {
            notCachedMethodCallCount++;
            return 0;
        }

        @Override
        public int transientlyCachedMethod(int n) {
            transientlyCachedMethodCallCount++;
            return 0;
        }

        @Override
        public int persistentlyCachedMethod(int n) {
            persistentlyCachedMethodCallCount++;
            return 0;
        }
    }

    @Test
    @DisplayName("Transient caching")
    void transientCaching() {
        Impl obj = new Impl();
        Interface proxy = CacheProxy.create(obj, obj.getClass(), null);

        int result1 = proxy.transientlyCachedMethod(0);
        int result2 = proxy.transientlyCachedMethod(0);

        assertThat(obj.transientlyCachedMethodCallCount).isOne();
        assertThat(result1).isZero();
        assertThat(result2).isZero();
    }

    @Test
    @DisplayName("Persistent caching")
    void persistentCaching(@TempDir Path tmp) throws NoSuchMethodException {
        Impl obj = new Impl();
        Interface proxy = CacheProxy.create(obj, obj.getClass(), tmp);

        int result1 = proxy.persistentlyCachedMethod(2);
        int result2 = proxy.persistentlyCachedMethod(2);
        Path cacheEntry = tmp.resolve(Interface.class.getMethod("persistentlyCachedMethod", int.class).toString())
                .resolve(URLEncoder.encode(Arrays.toString(new int[] {2}), StandardCharsets.UTF_8));

        assertThat(obj.persistentlyCachedMethodCallCount).isOne();
        assertThat(result1).isZero();
        assertThat(result2).isZero();
        assertThat(cacheEntry)
                .exists()
                .content(StandardCharsets.UTF_8)
                .isEqualTo("0");
    }

    @Test
    @DisplayName("No caching")
    void noCaching() {
        Impl obj = new Impl();
        Interface proxy = CacheProxy.create(obj, obj.getClass(), null);

        int result1 = proxy.notCachedMethod(0);
        int result2 = proxy.notCachedMethod(0);

        assertThat(result1).isZero();
        assertThat(result2).isZero();
        assertThat(obj.notCachedMethodCallCount).isEqualTo(2);
    }
}

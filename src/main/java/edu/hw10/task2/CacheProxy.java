package edu.hw10.task2;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CacheProxy {
    public static <T> T create(T target, Class<? extends T> clazz, Path persistentStoragePath) {
        //noinspection unchecked
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                clazz.getInterfaces(),
                new MyInvocationHandler(target, persistentStoragePath)
        );
    }

    private CacheProxy() {
    }

    private static class MyInvocationHandler implements InvocationHandler {
        private final Object target;
        private final Map<Method, Map<List<Object>, Object>> transientStorage;
        private final Path persistentStoragePath;

        MyInvocationHandler(Object target, Path persistentStoragePath) {
            this.target = target;
            this.transientStorage = new HashMap<>();
            this.persistentStoragePath = persistentStoragePath;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Cache cache = method.getAnnotation(Cache.class);
            if (cache == null) {
                return method.invoke(target, args);
            } else if (cache.persist()) {
                return getFromPersistentStorage(method, args);
            } else {
                return getFromTransientStorage(method, args);
            }
        }

        private Object getFromTransientStorage(Method method, Object[] args)
                throws InvocationTargetException, IllegalAccessException {
            List<Object> argList = List.of(args);
            Map<List<Object>, Object> cache = transientStorage.get(method);
            if (cache == null) {
                cache = new HashMap<>();
                transientStorage.put(method, cache);
            } else {
                Object cachedValue = cache.get(argList);
                if (cachedValue != null) {
                    return cachedValue;
                }
            }

            Object value = method.invoke(target, args);
            cache.put(argList, value);
            return value;
        }

        private Object getFromPersistentStorage(Method method, Object[] args)
                throws InvocationTargetException, IllegalAccessException, IOException {
            Path cachePath = persistentStoragePath.resolve(method.toString());
            try {
                Files.createDirectory(cachePath);
            } catch (FileAlreadyExistsException ignored) {
            }
            Path cachedValuePath = cachePath.resolve(URLEncoder.encode(Arrays.toString(args), StandardCharsets.UTF_8));
            if (Files.exists(cachedValuePath)) {
                return new ObjectMapper().readValue(cachedValuePath.toFile(), method.getReturnType());
            }

            Object result = method.invoke(target, args);
            String resultJson = new ObjectMapper().writeValueAsString(result);
            Files.createFile(cachedValuePath);
            Files.writeString(cachedValuePath, resultJson);

            return result;
        }
    }
}

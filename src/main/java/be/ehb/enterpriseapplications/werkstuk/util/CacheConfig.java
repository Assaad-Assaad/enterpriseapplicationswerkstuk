package be.ehb.enterpriseapplications.werkstuk.util;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean("customKeyGenerator")
    public KeyGenerator customKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return method.getName() + "_" + Arrays.stream(params)
                        .map(p -> p == null ? "null" : p.toString())
                        .collect(Collectors.joining("-"));
            }
        };
    }
}

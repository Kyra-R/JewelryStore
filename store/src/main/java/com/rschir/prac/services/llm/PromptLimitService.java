package com.rschir.prac.services.llm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class PromptLimitService {

    private StringRedisTemplate redis;

    @Autowired
    PromptLimitService(StringRedisTemplate redis)
    {
        this.redis = redis;
    }

    private final int LIMIT = 100;
    private final int WINDOW_SEC = 30;

    public boolean allowRequest(String ip) {
        String key = "rate:" + ip;

        Long count = redis.opsForValue().increment(key);

        if (count == 1) {
            redis.expire(key, Duration.ofSeconds(WINDOW_SEC));
        }

        return count <= LIMIT;
    }
}

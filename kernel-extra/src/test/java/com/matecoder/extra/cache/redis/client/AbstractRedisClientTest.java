package com.matecoder.extra.cache.redis.client;

import com.matecoder.extra.cache.redis.RedisClientBuilder;
import com.matecoder.extra.cache.redis.model.RedisConfigItem;
import org.junit.jupiter.api.Test;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;

import java.io.IOException;

public class AbstractRedisClientTest {

    @Test
    public void evalSimpleLuaTest() throws IOException {
        RedisConfigItem redisConfigItem = new RedisConfigItem();
        String[] address = new String[]{"127.0.0.1:6379"};
        redisConfigItem.setAddress(address);
        AbstractRedisClient redisClient = RedisClientBuilder.buildRedisClient(redisConfigItem);
        RedissonClient redissonClient = redisClient.getRedissonClient();
        RScript script = redissonClient.getScript(StringCodec.INSTANCE);
        Object result = script.eval(RScript.Mode.READ_WRITE, "return redis.call('get', 'test')", RScript.ReturnType.VALUE);
        System.out.println(result);
    }

    @Test
    public void evalLuaTest() throws IOException {
        RedisConfigItem redisConfigItem = new RedisConfigItem();
        String[] address = new String[]{"127.0.0.1:6379"};
        redisConfigItem.setAddress(address);
        AbstractRedisClient redisClient = RedisClientBuilder.buildRedisClient(redisConfigItem);
        RedissonClient redissonClient = redisClient.getRedissonClient();
        RScript script = redissonClient.getScript(StringCodec.INSTANCE);
        String luaScript = """
                redis.call('set','name','hs');
                return redis.call('get', 'test');
                """;
        // 加载脚本
        String sha1 = script.scriptLoad(luaScript);
        Object result = script.evalSha(RScript.Mode.READ_WRITE, sha1, RScript.ReturnType.VALUE);
        System.out.println(result);
    }
}

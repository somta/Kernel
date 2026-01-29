package com.matecoder.common.utils;

import com.matecoder.common.encrypt.Md5Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class SnowflakeIdUtilTest {

    @Test
    public void snowflakeIdTest(){
        long id = SnowflakeIdUtil.generateSnowflakeId();
        System.out.println(id);
    }

    @Test
    public void snowflakeIdBatchTest() {
        int batchSize = 1000;
        Set<Long> idSet = new HashSet<>();

        for (int i = 0; i < batchSize; i++) {
            idSet.add(SnowflakeIdUtil.generateSnowflakeId());
        }

        Assertions.assertEquals(batchSize, idSet.size(), "生成的ID数量应该等于请求的数量，无重复");
    }

}

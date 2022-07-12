package net.somta.demo.web.controller;

import net.somta.cache.impl.ICacheTemplate;
import net.somta.core.base.result.ResponseDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @Date:        2022-07-12
 * @Author:      husong
 * @Version:     1.0.0
 */
@RestController
@RequestMapping("/v1/cache")
public class CacheController {

    @Autowired
    private ICacheTemplate cacheTemplate;

    @GetMapping("/testString")
    public ResponseDataResult<String> testString() throws Exception{
        cacheTemplate.getCache("name");
        return ResponseDataResult.setResponseResult("success");
    }

}

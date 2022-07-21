package net.somta.demo.web.controller;

import net.somta.cache.impl.ICacheTemplate;
import net.somta.core.base.result.ResponseDataResult;
import net.somta.lock.impl.IDistributeLock;
import net.somta.lock.impl.ILockTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/v1/lock")
public class LockController {

    private final static Logger logger = LoggerFactory.getLogger(LockController.class);

    @Autowired
    private ILockTemplate lockTemplate;

    @GetMapping("/testLock")
    public ResponseDataResult<String> testLock() throws Exception{
        String key = "bizLockKey";
        IDistributeLock locker = lockTemplate.getLock(key);
        try {
            locker.lock();
            logger.info("开始执行业务逻辑");
            Thread.sleep(40 * 1000);
            logger.info("业务逻辑执行完成");
        } catch (Exception e) {
            e.getMessage();
        } finally {
            logger.info("业务逻辑执行完成");
            locker.unlock();
        }
        return ResponseDataResult.setResponseResult("success");
    }

}

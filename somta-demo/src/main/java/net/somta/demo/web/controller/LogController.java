package net.somta.demo.web.controller;

import net.somta.core.base.result.ResponseDataResult;
import net.somta.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @Date:        2021-02-03
 * @Author:      husong
 * @Version:     1.0.0
 */
@RestController
@RequestMapping("/v1/log")
public class LogController {

	private final static Logger loger = LoggerFactory.getLogger(LogController.class);


	/**
	 * 验证参数或请求方法错误的异常处理
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/info")
    public ResponseDataResult<String> queryById() throws Exception{
		loger.debug("debug log");
		loger.debug("debug log");
		loger.info("info log");
		return ResponseDataResult.setResponseResult("success");
    }


}




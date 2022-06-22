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
@RequestMapping("/v1/exception")
public class ExceptionController {

	private final static Logger loger = LoggerFactory.getLogger(ExceptionController.class);

	@GetMapping("/info")
    public ResponseDataResult<String> queryById() throws Exception{
		loger.info("方法执行了");
		return ResponseDataResult.setResponseResult("success");
    }

	@GetMapping("/biz")
	public ResponseDataResult<String> getBizException() throws Exception{
		loger.info("方法执行了");
		if(true){
			throw new BizException("biz.exception","这是一个业务异常");
		}
		return ResponseDataResult.setResponseResult("success");
	}

}




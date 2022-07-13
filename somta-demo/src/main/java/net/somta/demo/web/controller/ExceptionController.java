package net.somta.demo.web.controller;

import net.somta.core.base.result.ResponseDataResult;
import net.somta.core.exception.BizException;
import net.somta.core.exception.SysException;
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
    public ResponseDataResult<String> queryById(){
		loger.info("方法执行了");
		return ResponseDataResult.setResponseResult("success");
    }

	@GetMapping("/biz")
	public ResponseDataResult<String> getBizException(){
		loger.info("方法执行了");
		if(true){
			throw new BizException(100002,"这是一个业务异常");
		}
		return ResponseDataResult.setResponseResult("success");
	}

	@GetMapping("/sys")
	public ResponseDataResult<String> getSysException(){
		/*
		// 第一种情况：不处理第三方的异常，交给统一异常处理类处理
		int a = 1;
		int b = 0;
		int c = a/b;*/

		// 第二种情况，知道具体异常，处理掉可预期的异常，返回我们自己的异常
		try {
			int a = 1;
			int b = 0;
			int c = a/b;
		}catch (ArithmeticException exception){
			throw new SysException(1,"处理成自己的异常",exception);
		}

		return ResponseDataResult.setResponseResult("success");
	}

}




package net.somta.demo.web.controller;

import net.somta.core.base.result.ResponseDataResult;
import net.somta.core.exception.BizException;
import net.somta.core.exception.SysException;
import net.somta.demo.pojo.ExceptionDTO;
import net.somta.demo.pojo.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static net.somta.demo.enums.DemoErrorEnum.BIZ_ERROR;
import static net.somta.demo.enums.DemoErrorEnum.SYS_ERROR;

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
			throw new BizException(BIZ_ERROR);
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
			throw new SysException(SYS_ERROR,exception);
		}

		return ResponseDataResult.setResponseResult("success");
	}

	/**
	 * 请求接口时不填userName,测试 BindException异常
	 * 参考：https://juejin.cn/post/6844904003684302861  https://blog.csdn.net/qq_40223688/article/details/105786018
	 * @param exceptionDTO
	 * @return
	 */
	@GetMapping("/testBindException")
	public ResponseDataResult<String> testBindException(@Valid ExceptionDTO exceptionDTO){
		loger.info("方法执行了{}",exceptionDTO);
		return ResponseDataResult.setResponseResult("success");
	}

}




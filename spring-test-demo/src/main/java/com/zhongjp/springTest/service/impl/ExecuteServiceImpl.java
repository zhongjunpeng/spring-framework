package com.zhongjp.springTest.service.impl;

import com.zhongjp.springTest.service.ExecuteService;
import org.springframework.stereotype.Service;

@Service
public class ExecuteServiceImpl implements ExecuteService {

	@Override
	public void execute(){
		System.out.println("执行execute方法");
	}
}

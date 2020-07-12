package com.zhongjp.springTest.service.impl;

import com.zhongjp.springTest.service.DoService;
import org.springframework.stereotype.Service;

@Service
public class DoServiceImpl implements DoService {

	@Override
	public void doMethod(){
		System.out.println("执行doService方法");
	}

}

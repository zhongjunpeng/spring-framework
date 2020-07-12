package com.zhongjp.springTest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan("com.zhongjp.springTest.*")
@EnableAspectJAutoProxy
public class ScanConfig {
}

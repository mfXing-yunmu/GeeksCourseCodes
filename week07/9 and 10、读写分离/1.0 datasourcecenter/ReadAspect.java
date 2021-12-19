package com.yunmu.geek.datasourcecenter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author mfXing
 */
@Aspect
@Component
public class ReadAspect {
    @Autowired
    Management management;

    @Pointcut("@annotation(com.yunmu.geek.datasourcecenter.ReadOnly)")
    public void read(){};

    /**
     * 改变 DataSource 为 slave 节点
     */
    @Around("read()")
    public List<Map<String, Object>> setDatabaseSource(ProceedingJoinPoint point) throws Throwable {
        Object[] argv = point.getArgs();
        argv[0] = management.getSlaveDataSource();
        return (List<Map<String, Object>>) point.proceed(argv);
    }
}

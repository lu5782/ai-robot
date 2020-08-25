package com.cyp.robot.api.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author lyj
 * @description jdk动态代理
 * @date 2019/9/24 15:16
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    // proxy 代理对象 ，一般不会使用
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("动态代理对象 ： " + proxy.getClass());
        System.out.println("正在被调用的方法方法对象 ： " + method);
        System.out.println("正在被调用的方法方法名 ： " + method.getName());
        System.out.println("正在被调用的方法参数 ： " + Arrays.asList(args));
        System.out.println("执行方法前 ");
        //目标对象，调用目标方法 相当于 ArithmeticImpl调用自己的方法
        Object result = method.invoke(target, args);
        System.out.println("执行方法后 result = " + result);
        return result;
    }

}

package com.cyp.robot.api.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyObject {
    //目标对象
    private Object target;

    public ProxyObject(Object target) {
        this.target = target;
    }

    // 生成代理类
    public Object getProxyObject() {
//        ClassLoader loader : 目标对象的类加载器
//        Class<?>[] interfaces : 接口们，目标对象提供的所有接口对象
//        InvocationHandler h :  代理类的调用处理对象需要实现的接口,h是代理对象调用处理执行的方法。
        ClassLoader loader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        InvocationHandler handler = new MyInvocationHandler(target);
        return Proxy.newProxyInstance(loader, interfaces, handler);
    }


    public static void main(String[] args) {
        Arithmetic target = new ArithmeticImpl();
        ProxyObject proxyObject = new ProxyObject(target);
        Object proxy = proxyObject.getProxyObject();
        Arithmetic proxy1 = (Arithmetic) proxy;
        int result = proxy1.add(1, 2);
        System.out.println(result);
    }
}

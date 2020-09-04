package test;

import java.lang.reflect.Method;

/**
 * functional describe:
 *
 * @author luyanliang [765673481@qq.com]
 * @version 1.0 2017/8/31
 */
public class ReflectTest {

    public void test() {
        System.out.println("hello");
    }

    private void test1() {
        System.out.println("private Test");
    }

    public static void main(String[] args) {
        try {
            // 第一种方式
            Class c1 = Class.forName("test.ReflectTest");

            // 第二种方式
            // java 中每个类型都有class属性
            Class c2 = ReflectTest.class;

            // 第三种方式
            // java 中任何一个对象都有getClass方法
            ReflectTest reflectTest = new ReflectTest();
            Class c3 = reflectTest.getClass();

            Method method = c1.getMethod("test");
            method.invoke(c1.newInstance());
            System.out.println(c1);

            Method[] methods = c2.getMethods();
            for (int i = 0; i < methods.length; i ++) {
                Method mt = methods[i];
                String methodName = mt.getName();
                System.out.println(methodName);
            }

            methods = c2.getDeclaredMethods();
            for (int i = 0; i < methods.length; i ++) {
                Method mt = methods[i];
                String methodName = mt.getName();
                System.out.println(methodName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

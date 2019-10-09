package com.work.jy.work.aop.announce;


import com.work.jy.work.aop.model.EmployeeToken;

public class EmployeeThreadLocal {
    private static ThreadLocal<String> threadLocalToken = new ThreadLocal<>();

    private static ThreadLocal<EmployeeToken> threadLocalEmployee = new ThreadLocal<>();

    public static void putToken(String t) {
        threadLocalToken.set(t);
    }

    @SuppressWarnings("unchecked")
    public static String getToken() {
        return threadLocalToken.get();
    }

    public static void removeToken() {
        threadLocalToken.remove();
    }



    public static void putEmployee(EmployeeToken t) {
        threadLocalEmployee.set(t);
    }

    @SuppressWarnings("unchecked")
    public static EmployeeToken getEmployee() {
        return threadLocalEmployee.get();
    }

    public static void removeEmployee() {
        threadLocalEmployee.remove();
    }


}

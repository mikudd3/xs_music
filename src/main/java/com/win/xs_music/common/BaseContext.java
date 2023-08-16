package com.win.xs_music.common;

/**
 * @project: 基于ThreadLocal封装的工具类，用户保存和获取当前登录id
 * @author: mikudd3
 * @version: 1.0
 */
public class BaseContext {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    public static void setCurrentId(Integer id) {
        threadLocal.set(id);
    }
    public static Integer getCurrentId() {
        return threadLocal.get();
    }

}

package com.zw.design.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CompareUtil {

    public static String compare(Object o1, Object o2) {
        StringBuilder sb = new StringBuilder();
        try {
            Class clazz = o1.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                Object obj1 = getMethod.invoke(o1);
                Object obj2 = getMethod.invoke(o2);
                String s1 = obj1 == null ? "" : obj1.toString();//避免空指针异常
                String s2 = obj2 == null ? "" : obj2.toString();//避免空指针异常
                if (obj1 != null && obj2 != null && !s1.equals(s2) && Const.FIELD_NAME.get(field.getName()) != null) {
                    sb.append(Const.FIELD_NAME.get(field.getName()))
                            .append("：【")
                            .append(s1)
                            .append("】 -> 【")
                            .append(s2)
                            .append("】,");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (sb.toString().length() == 0) {
            sb.append("属性无变化");
        }
        return sb.toString();
    }

}

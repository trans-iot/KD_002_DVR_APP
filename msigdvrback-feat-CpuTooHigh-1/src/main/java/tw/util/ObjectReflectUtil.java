package tw.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 物件反射套件
 */
public class ObjectReflectUtil {
    /**
     * 取得物件參數，範例： getExample()
     * @param fieldName 參數名稱
     * @return getExample()
     */
    public static String getGetMethodName(String fieldName) {
        String result = null;
        try {
            result = "get"+ fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1); 
        }catch(Throwable e) {}
        return result;
    }

    /**
     * 取得物件參數，範例： setExample()
     * @param fieldName 參數名稱
     * @return setExample()
     */
    public static String getSetMethodName(String fieldName) {
        String result = null;
        try {
            result = "set"+ fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1); 
        }catch(Throwable e) {}
        return result;
    }

    /**
     * 取得物件
     * @param <T> 泛型T
     * @param clazz 類別
     * @param t 泛型T
     * @param methodName 方法名稱
     * @return 物件
     * @throws Exception 方法處理出現錯誤會拋出
     */
    public static <T>Object getGetMethodStringValue(Class<T> clazz, T t, String methodName) throws Exception{
        Object result = null;
        Method method = clazz.getMethod(methodName);
        Class<?> returnType = method.getReturnType();
        Object object = method.invoke(t);
        
        if(Objects.nonNull(object)) {
            result = returnType.cast((object));

//            if("java.util.Date".equals(returnType.getName())) {
//                result = DateUtil.convertDateWithFormat(DateUtil.DATE_FORMAT2, result);
//            }
        }
        
        return result;
    }

    /**
     * 取得物件集合
     * @param <T> 泛型T
     * @param clazz 類別
     * @param t 泛型T
     * @return 物件集合
     */
    public static <T>Map<String, Object> classToMap(Class<T> clazz ,T t){
        Map<String, Object> map = new HashMap<String, Object>();
        for(Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            String methodName = getGetMethodName(fieldName);
            try {
                map.put(fieldName, getGetMethodStringValue(clazz, t, methodName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
    
    /**
     * 簡化get方法，但只能運用無參數的方法, 如:getXXX()
     * @param object 物件
     * @param fieldName 參數名稱
     * @return getXXX()
     * @throws Exception 方法處理出現錯誤會拋出
     */
    public static Object doGetMethod(Object object, String fieldName) throws Exception {
        
        Class<?> clazz = object.getClass();
        String methodName = getGetMethodName(fieldName);
        Field field = clazz.getDeclaredField(fieldName); 
        Type type =field.getGenericType();
        Class<?> fieldClass = Class.forName(type.getTypeName());
        return fieldClass.cast(clazz.getMethod(methodName).invoke(object));
    }

    /**
     * 簡化set方法，但只能運用單一參數的方法, 如:setXXX(XXX XXX)
     * @param object 物件
     * @param fieldName 參數名稱
     * @param value 值
     * @throws Exception 方法處理出現錯誤會拋出
     */
    public static void doSetMethod(Object object, String fieldName, Object value) throws Exception {
        Class<?> clazz = object.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        Type type =field.getGenericType();
        Class<?> fieldClass = Class.forName(type.getTypeName());
        
        String methodName = getSetMethodName(fieldName);
        clazz.getMethod(methodName, fieldClass).invoke(object, value);
    }

    /**
     * 取得泛型T
     * @param <T> 泛型T
     * @param clazz 類別
     * @param map 集合
     * @return 泛型T
     * @throws Exception 方法處理出現錯誤會拋出
     */
    public static <T> T getObject(Class<T> clazz,Map<String, Object> map) throws Exception{
        T object = clazz.newInstance();
        for(String key : map.keySet()) {
            String fieldName = key;
            Field field = clazz.getDeclaredField(fieldName); 
            Type type =field.getGenericType();
            Class<?> fieldClass = Class.forName(type.getTypeName());
            
            Object value = map.get(key);
            if("java.util.Date".equals(type.getTypeName())) {
//                String dateString = String.valueOf((map.get(key)));
//                Date date = DateUtil.convertStringWithFormat(DateUtil.DATE_FORMAT2, dateString);
//                value = date;
            }else {
                value = fieldClass.cast(map.get(key));
            }
            
            String methodName = getSetMethodName(fieldName);
            clazz.getMethod(methodName, fieldClass).invoke(object, value);
        };
        return object;
    }
}
package com.horizon.component.utilities;


import java.lang.reflect.Constructor;
import java.util.Set;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/6/7
 */
public final class SenderUtil {


    public static <T> T instantiateClass(Class<T> clazz) throws Exception {
        return instantiateClassWithParameters(clazz, null, null);
    }

    public static <T> T instantiateClassWithParameters(Class<T> clazz, Class<?>[] paramsTypes,
                                                       Object[] paramValues) throws Exception {

        if (paramsTypes != null && paramValues != null) {
            if (!(paramsTypes.length == paramValues.length)) {
                throw new IllegalArgumentException("Number of types and values must be equal");
            }

            if (paramsTypes.length == 0 && paramValues.length == 0) {
                return clazz.newInstance();
            }
            Constructor<T> clazzConstructor = clazz.getConstructor(paramsTypes);
            return clazzConstructor.newInstance(paramValues);
        }
        return clazz.newInstance();
    }


    public static Exception handleMissingParameters(Set<String> missingParams) {
        StringBuffer sb = new StringBuffer();
        if (missingParams != null && !missingParams.isEmpty()) {
            for (String missingParam : missingParams) {
                sb.append(missingParam).append(" ");
            }
        }
        return handleRequiredException(sb.toString().trim());
    }

    public static IllegalArgumentException handleRequiredException(String message) {
        return new IllegalArgumentException(message);
    }
}

package com.horizon.component.utilities;


import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.NullArgumentException;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016 /6/7
 */
public final class SenderUtil {


    /**
     * Instantiate class t.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the t
     * @throws Exception the exception
     */
    public static <T> T instantiateClass(Class<T> clazz) throws Exception {
        return instantiateClassWithParameters(clazz, null, null);
    }

    /**
     * Instantiate class with parameters t.
     *
     * @param <T>         the type parameter
     * @param clazz       the clazz
     * @param paramsTypes the params types
     * @param paramValues the param values
     * @return the t
     * @throws Exception the exception
     */
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


    /**
     * Handle missing parameters illegal argument exception.
     *
     * @param missingParams the missing params
     * @return the illegal argument exception
     */
    public static IllegalArgumentException handleMissingParameters(Set<String> missingParams) {
        StringBuffer sb = new StringBuffer("Argument");
        if (missingParams != null && !missingParams.isEmpty()) {
            Set<String> set = new HashSet<String>();
            for (String missingParam : missingParams) {
                set.add(missingParam);
//                sb.append(missingParam).append(" ");
            }
            sb.append(" ").append(ArrayUtils.toString(set));
        }
        return handleRequiredException(sb.toString().trim());
    }

    /**
     * Handle required exception illegal argument exception.
     *
     * @param message the message
     * @return the illegal argument exception
     */
    public static IllegalArgumentException handleRequiredException(String message) {
        return new NullArgumentException(message);
    }
}

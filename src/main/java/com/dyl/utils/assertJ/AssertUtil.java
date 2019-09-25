package com.dyl.utils.assertJ;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.function.Function;

/**
 * 自定义断言工具类
 *
 * @author Dong YL
 * @version V1.0 2019/9/23 17:22
 */
public class AssertUtil {


    public static void isTrue(Function<Object, ? extends Exception> function, @NonNull Object logInfo, boolean expression) throws Exception {
        if (!expression) {
            throw function.apply(logInfo);
        }
    }

    public static void notNull(Function<Object, ? extends Exception> function, @NonNull Object logInfo, @Nullable Object object) throws Exception {
        if (object == null) {
            throw function.apply(logInfo);
        }
    }

    public static void isNull(Function<Object, ? extends Exception> function, @NonNull Object logInfo, @Nullable Object object) throws Exception {
        if (object != null) {
            throw function.apply(logInfo);
        }
    }

    public static void noNullElements(Function<Object, ? extends Exception> function, @NonNull Object logInfo, @Nullable Object[] array) throws Exception {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw function.apply(logInfo);
                }
            }
        }
    }

    public static void notEmpty(Function<Object, ? extends Exception> function, @NonNull Object logInfo, @Nullable Collection<?> collection) throws Exception {
        if (CollectionUtils.isEmpty(collection)) {
            throw function.apply(logInfo);
        }
    }

}
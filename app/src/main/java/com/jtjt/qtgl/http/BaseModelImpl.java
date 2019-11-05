package com.jtjt.qtgl.http;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by 董凯强 on 2018/11/5.
 */

public class BaseModelImpl {

    public static class ErrorReturn<T> implements Function<Throwable, BaseEntity<T>> {

        @Override
        public BaseEntity<T> apply(@NonNull Throwable throwable) throws Exception {
            BaseEntity<T> bean = new BaseEntity<>();
            bean.setStatus(201);
            bean.setMsg(throwable.getMessage());
            return bean;
        }
    }
    public static class ErrorReturns<T> implements Function<Throwable, BaseEntity<T>> {

        @Override
        public BaseEntity<T> apply(@NonNull Throwable throwable) throws Exception {
            BaseEntity<T> bean = new BaseEntity<>();
            bean.setStatus(201);
            bean.setMsg(throwable.getMessage());
            return bean;
        }
    }

}

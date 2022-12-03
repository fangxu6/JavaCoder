package com.gougou.lambda;

/**
 * @author fangxu
 * on date:2022/9/30
 */
@FunctionalInterface
public
interface MyFunction2<T, R> {

    public R calc(T t1, T t2);

}


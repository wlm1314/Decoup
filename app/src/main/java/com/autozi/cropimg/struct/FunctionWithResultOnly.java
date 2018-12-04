package com.autozi.cropimg.struct;

/**
 * Create by on wsy on 2018/12/4 10:13
 */
public abstract class FunctionWithResultOnly<Result> extends Function {
    public FunctionWithResultOnly(String funcName) {
        super(funcName);
    }

    public abstract Result function();
}

package com.autozi.cropimg.struct;

/**
 * Create by on wsy on 2018/12/4 10:13
 */
public abstract class FunctionWithParamAndResult<Result, Param> extends Function {
    public FunctionWithParamAndResult(String funcName) {
        super(funcName);
    }

    public abstract Result function(Param data);
}

package com.autozi.cropimg.struct;

/**
 * Create by on wsy on 2018/12/4 10:13
 */
public abstract class FunctionWithParamOnly<Param> extends Function {
    public FunctionWithParamOnly(String funcName) {
        super(funcName);
    }

    public abstract void function(Param data);
}

package com.autozi.cropimg.struct;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * Create by on wsy on 2018/12/4 10:16
 */
public class FunctionsManager {
    private static FunctionsManager _instance;
    private HashMap<String, FunctionNoParamNoResult> mFunctionNoParamNoResult;
    private HashMap<String, FunctionWithResultOnly> mFunctionWithResultOnly;
    private HashMap<String, FunctionWithParamOnly> mFunctionWithParamOnly;
    private HashMap<String, FunctionWithParamAndResult> mFunctionWithParamAndResult;

    private FunctionsManager() {
        mFunctionNoParamNoResult = new HashMap<>();
        mFunctionWithResultOnly = new HashMap<>();
        mFunctionWithParamOnly = new HashMap<>();
        mFunctionWithParamAndResult = new HashMap<>();
    }

    public static FunctionsManager getInstance() {
        if (_instance == null) {
            _instance = new FunctionsManager();
        }
        return _instance;
    }

    public FunctionsManager addFunction(FunctionNoParamNoResult function) {
        mFunctionNoParamNoResult.put(function.mFunctionName, function);
        return this;
    }

    public void invokeFunc(String funcName) {
        if (TextUtils.isEmpty(funcName)) return;
        if (mFunctionNoParamNoResult != null) {
            FunctionNoParamNoResult function = mFunctionNoParamNoResult.get(funcName);
            if (function != null)
                function.function();
            else
                try {
                    throw new FunctionException("Has no this Function: " + funcName);
                } catch (FunctionException e) {
                    e.printStackTrace();
                }
        }
    }

    public FunctionsManager addFunction(FunctionWithResultOnly function) {
        mFunctionWithResultOnly.put(function.mFunctionName, function);
        return this;
    }

    public <Result> Result invokeFunc(String funcName, Class<Result> c) throws FunctionException {
        if (TextUtils.isEmpty(funcName))
            return null;
        if (mFunctionWithResultOnly != null) {
            FunctionWithResultOnly function = mFunctionWithResultOnly.get(funcName);
            if (function != null) {
                if (c != null) {
                    return c.cast(function.function());
                } else {
                    return (Result) function.function();
                }
            } else
                throw new FunctionException("Has no this Function: " + funcName);
        } else
            return null;
    }

    public FunctionsManager addFunction(FunctionWithParamOnly function) {
        mFunctionWithParamOnly.put(function.mFunctionName, function);
        return this;
    }

    public <Param> void invokeFunc(String funcName, Param param) {
        if (TextUtils.isEmpty(funcName)) return;
        if (mFunctionWithParamOnly != null) {
            FunctionWithParamOnly function = mFunctionWithParamOnly.get(funcName);
            if (function != null)
                function.function(param);
            else
                try {
                    throw new FunctionException("Has no this Function: " + funcName);
                } catch (FunctionException e) {
                    e.printStackTrace();
                }
        }
    }

    public FunctionsManager addFunction(FunctionWithParamAndResult function) {
        mFunctionWithParamAndResult.put(function.mFunctionName, function);
        return this;
    }

    public <Result, Param> Result invokeFunc(String funcName, Class<Result> c, Param param) throws FunctionException {
        if (TextUtils.isEmpty(funcName))
            return null;
        if (mFunctionWithParamAndResult != null) {
            FunctionWithParamAndResult function = mFunctionWithParamAndResult.get(funcName);
            if (function != null) {
                if (c != null) {
                    return c.cast(function.function(param));
                } else {
                    return (Result) function.function(param);
                }
            } else
                throw new FunctionException("Has no this Function: " + funcName);
        } else
            return null;
    }
}

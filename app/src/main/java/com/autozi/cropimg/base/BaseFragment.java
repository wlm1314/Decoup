package com.autozi.cropimg.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.autozi.cropimg.MainActivity10;
import com.autozi.cropimg.struct.FunctionsManager;

/**
 * Create by on wsy on 2018/12/4 10:29
 */
public class BaseFragment extends Fragment {
    protected FunctionsManager mFunctionsManager;
    private MainActivity10 mBaseActivity;

    public void setFunctionsManager(FunctionsManager functionsManager) {
        mFunctionsManager = functionsManager;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity10) {
            mBaseActivity = (MainActivity10) context;
            mBaseActivity.setFunctionsForFragment(getTag());
        }
    }
}

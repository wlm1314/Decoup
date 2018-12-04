package com.autozi.cropimg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.autozi.cropimg.base.BaseFragment;
import com.autozi.cropimg.struct.FunctionException;

/**
 * Create by on wsy on 2018/11/9 15:17
 */
public class TestFragment extends BaseFragment {
    private TextView mTvBt1, mTvBt2, mTvBt3, mTvBt4;
    public static final String INTERFACE_NPNR = TestFragment.class.getName() + "NPNR";
    public static final String INTERFACE_WITHR = TestFragment.class.getName() + "WITHR";
    public static final String INTERFACE_WITHP = TestFragment.class.getName() + "WITHP";
    public static final String INTERFACE_WITHPANDR = TestFragment.class.getName() + "WITHPANDR";

    public static TestFragment getInstance(int count) {
        TestFragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("count", count);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_test, null);
        mTvBt1 = view.findViewById(R.id.tv_bt1);
        mTvBt2 = view.findViewById(R.id.tv_bt2);
        mTvBt3 = view.findViewById(R.id.tv_bt3);
        mTvBt4 = view.findViewById(R.id.tv_bt4);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFunctionsManager.invokeFunc(INTERFACE_NPNR);
            }
        });

        mTvBt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String result = mFunctionsManager.invokeFunc(INTERFACE_WITHR, String.class);
                    Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                } catch (FunctionException e) {
                    e.printStackTrace();
                }
            }
        });

        mTvBt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFunctionsManager.invokeFunc(INTERFACE_WITHP, "哈哈哈");
            }
        });

        mTvBt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String result = mFunctionsManager.invokeFunc(INTERFACE_WITHPANDR, String.class, "啦啦啦");
                    Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                } catch (FunctionException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

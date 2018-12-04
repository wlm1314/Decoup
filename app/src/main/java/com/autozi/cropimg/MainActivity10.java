package com.autozi.cropimg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.autozi.cropimg.base.BaseFragment;
import com.autozi.cropimg.struct.FunctionNoParamNoResult;
import com.autozi.cropimg.struct.FunctionWithParamAndResult;
import com.autozi.cropimg.struct.FunctionWithParamOnly;
import com.autozi.cropimg.struct.FunctionWithResultOnly;
import com.autozi.cropimg.struct.FunctionsManager;

public class MainActivity10 extends AppCompatActivity {
    private TextView tvText, tvText2;
    private DrawerLayout mDrawerLayout;
    private TestFragment mTestFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);
        tvText = findViewById(R.id.tv_text);
        tvText2 = findViewById(R.id.tv_text2);
        mDrawerLayout = findViewById(R.id.drawer);

        tvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                if (mTestFragment == null)
                    mTestFragment = TestFragment.getInstance(1);

                if (mTestFragment.isAdded()) {
                    for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                        getSupportFragmentManager().beginTransaction().hide(fragment).commitAllowingStateLoss();
                    }
                    getSupportFragmentManager().beginTransaction().show(mTestFragment).commitAllowingStateLoss();
                } else
                    getSupportFragmentManager().beginTransaction().add(R.id.flayout_container, mTestFragment, "1").commitAllowingStateLoss();
            }
        });
    }

    public void setFunctionsForFragment(String tag) {
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(tag);
        fragment.setFunctionsManager(FunctionsManager.getInstance()
                .addFunction(new FunctionNoParamNoResult(TestFragment.INTERFACE_NPNR) {
                    @Override
                    public void function() {
                        Toast.makeText(MainActivity10.this, "无参无返回值方法", Toast.LENGTH_SHORT).show();
                    }
                })
                .addFunction(new FunctionWithResultOnly<String>(TestFragment.INTERFACE_WITHR) {
                    @Override
                    public String function() {
                        return "无参有返回值方法";
                    }
                })
                .addFunction(new FunctionWithParamOnly<String>(TestFragment.INTERFACE_WITHP) {
                    @Override
                    public void function(String data) {
                        Toast.makeText(MainActivity10.this, "有参无返回值方法", Toast.LENGTH_SHORT).show();
                    }
                })
                .addFunction(new FunctionWithParamAndResult<String, String>(TestFragment.INTERFACE_WITHPANDR) {
                    @Override
                    public String function(String data) {
                        return data + "有参有返回值方法";
                    }
                })
        );
    }

}

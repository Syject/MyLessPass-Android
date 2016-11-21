package com.syject.lesspass;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LessPassActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return LessPassFragment.newInstance();
    }
}

package com.android.core.sample.ui;

import android.os.Bundle;
import com.android.core.base.BaseCoreActivity;
import com.android.core.sample.R;

public class TestFragmentActivity extends BaseCoreActivity {

    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_test_fragment);

        getSupportFragmentManager ().beginTransaction ()
            .replace (R.id.container, BlankFragment.newInstance ("", ""))
            .commit ();
    }
}

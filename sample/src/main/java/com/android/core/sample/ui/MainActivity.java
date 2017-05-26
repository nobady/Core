package com.android.core.sample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.android.core.base.BaseCoreActivity;
import com.android.core.sample.R;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;

public class MainActivity extends BaseCoreActivity implements EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener{

    //@BindView (R.id.button) Button mButton;
    //@BindView (R.id.button2) Button mButton2;
    //@BindView (R.id.button3) Button mButton3;
    //@BindView (R.id.button4) Button mButton4;
    //@BindView (R.id.button5) Button mButton5;
    //@BindView (R.id.button6) Button mButton6;
    //@BindView (R.id.button7) Button mButton7;
    //@BindView (R.id.button9) Button mButton9;
    //@BindView (R.id.button8) Button mButton8;
    @BindView (R.id.editEmojicon) EditText mEmojiconEditText;

    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        ButterKnife.bind (this);

        setEmojiconFragment(false);

        mEmojiconEditText.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override public void onTextChanged (CharSequence s, int start, int before, int count) {

            }

            @Override public void afterTextChanged (Editable s) {
                Log.e ("TAG","s = "+s.toString ());
            }
        });

    }

    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.emojicons, EmojiconsFragment.newInstance (useSystemDefault))
            .commit();
    }

    //@OnClick ({
    //    R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6,
    //    R.id.button7, R.id.button9, R.id.button8
    //}) public void onClick (View view) {
    //    switch (view.getId ()) {
    //        case R.id.button:
    //            start (ItemClickActivity.class);
    //            break;
    //        case R.id.button2:
    //            start (AnimationActivity.class);
    //            break;
    //        case R.id.button3:
    //            start (HeadAndFooterActivity.class);
    //            break;
    //        case R.id.button4:
    //            start (TestFragmentActivity.class);
    //            break;
    //        case R.id.button5:
    //            break;
    //        case R.id.button6:
    //            start (MultiItemActivity.class);
    //            break;
    //        case R.id.button7:
    //            break;
    //        case R.id.button9:
    //            break;
    //        case R.id.button8:
    //            break;
    //    }
    //}

    public void start(Class c){
        Intent intent = new Intent (this,c);
        startActivity (intent);
    }

    @Override public void onEmojiconClicked (Emojicon emojicon) {
        EmojiconsFragment.input (mEmojiconEditText,emojicon);
    }

    @Override public void onEmojiconBackspaceClicked (View v) {
        EmojiconsFragment.backspace(mEmojiconEditText);
    }
}

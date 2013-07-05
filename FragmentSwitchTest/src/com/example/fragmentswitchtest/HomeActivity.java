package com.example.fragmentswitchtest;

import java.lang.reflect.Constructor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class HomeActivity extends FragmentActivity {
    private static String TAG = HomeActivity.class.getSimpleName();

    private String mCurFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FirstFragment f = FirstFragment.newInstance(null);
            fm.beginTransaction().setCustomAnimations(0, R.anim.push_left_out)
                    .add(R.id.container, f, FirstFragment.TAG).commit();
            mCurFragmentTag = FirstFragment.TAG;
        }
    }

    public void showFragment(Fragment curFragment, Class<?> fragmentClass,
            final String tag, Bundle ags, boolean flag) {
        if (fragmentClass == null) {
            Toast.makeText(this, TAG + "The next fragment class should not be null",
                    Toast.LENGTH_LONG).show();
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (flag) {
            ft.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out);
        } else {
            ft.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
        }

        if (curFragment != null) {
            ft.hide(curFragment);
        }
        mCurFragmentTag = tag;
        Fragment f = fm.findFragmentByTag(tag);
        if (f == null) {
            try {
                Constructor<?> ctor = fragmentClass.getConstructor();
                f = (Fragment) ctor.newInstance();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();;
            }
        }
        if (f.isHidden()) {
            ft.show(f).commit();
        } else {
            ft.add(R.id.container, f, tag).commit();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentByTag(mCurFragmentTag);
        if (f != null && (f instanceof BaseFragment)) {
            ((BaseFragment) f).onBackPressed();
        }
    }
}

package com.example.masterdetailflow_viewpageredition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements
        MasterFragment.MasterFragmentListener, DetailFragment.DetailFragmentListener {

    private static final String TAG = "LOGTAG";
    ViewPager2 mViewPager;
    MyViewPagerAdapter mMyViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.container);          // assign the instance of ViewPager2
        mMyViewPagerAdapter = new MyViewPagerAdapter(this);
        mViewPager.setAdapter(mMyViewPagerAdapter);         // bind the adapter to the viewpager2

        mViewPager.setUserInputEnabled(false);              // disable swiping

    }

    /* --------------------------------------------- */
    /*  This interface method is called when the     */
    /*  user taps on a view in the master fragment   */
    /* --------------------------------------------- */
    @Override
    public void onMasterFragmentData(int i) {

        String res = null;
        switch (i) {
            case 0:
                res = "Task1";
                break;
            case 1:
                res = "Task2";
                break;
            case 2:
                res = "Task3";
                break;
            default:
                break;
        }

        // tell my adapter what the string should be
        mMyViewPagerAdapter.setDetailsString(res);

        // change the position
        mViewPager.setCurrentItem(1, false);

    }


    /* --------------------------------------------- */
    /*  This interface method is called when the     */
    /*  user taps back in the detail fragment        */
    /* --------------------------------------------- */
    @Override
    public void onDetailFragmentAction() {
        // change the position
        mViewPager.setCurrentItem(0, false);
    }



    /* --------------------------------------------- */
    /*  This class is responsible for loading        */
    /*  fragments into the ViewPager2                */
    /* --------------------------------------------- */

    private class MyViewPagerAdapter extends FragmentStateAdapter {

        private String mDetailsString = null;

        // retain references to the fragments in the adapter
        private MasterFragment mMasterFragment = null;
        private DetailFragment mDetailsFragment = null;

        public MyViewPagerAdapter(MainActivity ma) {
            super(ma);
        }

        public void setDetailsString(String s) {
            mDetailsString = s;
            if (mDetailsFragment != null) {
                mDetailsFragment.updateDisplay(mDetailsString);
            }
        }

        @Override
        public Fragment createFragment(int position) {
            Fragment res = null;
            switch (position) {
                case 0:
                    mMasterFragment = MasterFragment.newInstance("foo");
                    res = mMasterFragment;
                    break;
                case 1:
                    mDetailsFragment = DetailFragment.newInstance(mDetailsString);
                    res = mDetailsFragment;
                    break;
                default:
                    mMasterFragment = MasterFragment.newInstance("foo");
                    res = mMasterFragment;
                    break;
            }
            return res;
        }

        @Override
        public int getItemCount() {
            return 2;       // there are only two fragments, the master and the detail
        }
    }
}
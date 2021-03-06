package com.android.moviesapp.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.android.moviesapp.activities.MainActivity;
import com.android.moviesapp.fragments.FavoritesFragment;
import com.android.moviesapp.fragments.HomeFragment;
import com.android.moviesapp.fragments.SearchFragment;

public class PageAdapter extends FragmentPagerAdapter {

    private int mNumOfTabs;
    private SearchFragment mSearchFragment;
    private HomeFragment mHomeFragment;
    private FavoritesFragment mFavoritesFragment;
    private MainActivity mMainActivity;

    public PageAdapter(FragmentManager fm, int numOfTabs, MainActivity mainActivity) {
        super(fm);
        mNumOfTabs = numOfTabs;
        mMainActivity = mainActivity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (mSearchFragment == null) mSearchFragment = new SearchFragment();
                return mSearchFragment;
            case 2:
                if (mFavoritesFragment == null) mFavoritesFragment = new FavoritesFragment();
                return mFavoritesFragment;
            case 1:
            default:
                if (mHomeFragment == null) mHomeFragment = new HomeFragment();
                return mHomeFragment;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}

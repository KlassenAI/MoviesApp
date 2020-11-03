package com.android.moviesapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.moviesapp.R;
import com.android.moviesapp.adapters.PageAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TabItem mSearchTabItem;
    private TabItem mHomeTabItem;
    private TabItem mFavoritesTabItem;
    public PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mTabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        mSearchTabItem = (TabItem) findViewById(R.id.search_tab_item);
        mHomeTabItem = (TabItem) findViewById(R.id.home_tab_item);
        mFavoritesTabItem = (TabItem) findViewById(R.id.favorites_tab_item);
        mViewPager = (ViewPager) findViewById(R.id.main_view_pager);

        mPagerAdapter = new PageAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(), MainActivity.this);
        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2) {
                    mPagerAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }
}
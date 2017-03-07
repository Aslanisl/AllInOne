package ru.mail.aslanisl.allinone;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.mail.aslanisl.allinone.bashPostFragment.BashPostsFragment;
import ru.mail.aslanisl.allinone.newsFragment.NewsFragment;
import ru.mail.aslanisl.allinone.weatherFragment.WeatherFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public static final String PAGE_NUMBER_ONE = "Анектоды";
    public static final String PAGE_NUMBER_TWO = "Погода";
    public static final String PAGE_NUMBER_THREE = "Новости";

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
            case 0:
                return new BashPostsFragment();

            case 1:
                return new WeatherFragment();

            case 2:
                return new NewsFragment();
        }

        return new BashPostsFragment();
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return PAGE_NUMBER_ONE;
            case 1:
                return PAGE_NUMBER_TWO;
            case 2:
                return PAGE_NUMBER_THREE;
        }
        return null;
    }
}

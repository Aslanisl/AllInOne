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

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
//            return PlaceholderFragment.newInstance(position + 1);
        switch (position){
            case 0:
                return BashPostsFragment.newInstance(position);

            case 1:
                return WeatherFragment.newInstance(position);

            case 2:
                return NewsFragment.newInstance(position);
        }

        return BashPostsFragment.newInstance(position);
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
                return "Анектоды";
            case 1:
                return "Погода";
            case 2:
                return "Новости";
        }
        return null;
    }
}

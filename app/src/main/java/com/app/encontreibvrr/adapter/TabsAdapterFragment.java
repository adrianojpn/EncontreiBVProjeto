package com.app.encontreibvrr.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.encontreibvrr.fragments.Fragment_Achados;
import com.app.encontreibvrr.fragments.Fragment_Perdidos;

public class TabsAdapterFragment extends FragmentStatePagerAdapter {

    private Context mContext;

    private String[] titles = {
            "ACHADOS",
            "PERDIDOS"};

    private int heightIcon;

    public TabsAdapterFragment(Context context, FragmentManager fm) {
        super(fm);

        this.mContext = context;

        double scale = context.getResources().getDisplayMetrics().density;
        heightIcon = (int)( 24 * scale + 0.5f );
    }

    @Override
    public Fragment getItem(int position) {

        switch(position){

            case 0:
                return Fragment_Achados.newInstance();
            default:
                return Fragment_Perdidos.newInstance();
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        /*
        Drawable d = mContext.getResources().getDrawable( icons[position] );
        d.setBounds(0, 0, heightIcon, heightIcon);
        ImageSpan is = new ImageSpan( d );
        SpannableString sp = new SpannableString(" ");
        sp.setSpan( is, 0, sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
        return (sp);
        */

        return ( titles[position] );
    }
}

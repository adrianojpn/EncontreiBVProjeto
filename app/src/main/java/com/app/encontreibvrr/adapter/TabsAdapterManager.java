package com.app.encontreibvrr.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.app.encontreibvrr.R;
import com.app.encontreibvrr.fragments.Fragment_01Inicial;
import com.app.encontreibvrr.fragments.Fragment_02Adicionar;
import com.app.encontreibvrr.fragments.Fragment_03Pesquisa;
import com.app.encontreibvrr.fragments.Fragment_04Mapa;
import com.app.encontreibvrr.fragments.Fragment_05Perfil;

public class TabsAdapterManager extends FragmentStatePagerAdapter {

    private Context mContext;

    private String[] titles = {
            "INÍCIO",
            "ADICIONAR",
            "BUSCA",
            "MAPA",
            "PERFIL"};

    String Tab1, Tab2, Tab3, Tab4;

    private int[] icons = new int[]{
            R.drawable.ic_dns_black,
            R.drawable.ic_add_circle_black,
            R.drawable.ic_search_black,
            R.drawable.map,
            R.drawable.ic_person_black};


    private int heightIcon;

    public TabsAdapterManager(Context context, FragmentManager fm) {
        super(fm);

        this.mContext = context;

        double scale = context.getResources().getDisplayMetrics().density;
        heightIcon = (int)( 24 * scale + 0.5f );
    }

    @Override
    public Fragment getItem(int position) {

        switch(position){

            case 0:
                return Fragment_01Inicial.getInstance("FRAGMENT 01");
            case 1:
                return Fragment_02Adicionar.getInstance("FRAGMENT 02");
            case 2:
                return Fragment_03Pesquisa.getInstance("FRAGMENT 03");
            case 3:
                return Fragment_04Mapa.getInstance("FRAGMENT 04");
            default:
                return Fragment_05Perfil.getInstance("FRAGMENT 05");
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {


        Drawable d = mContext.getResources().getDrawable( icons[position] );
        d.setBounds(0, 0, heightIcon, heightIcon);
        ImageSpan is = new ImageSpan( d );
        SpannableString sp = new SpannableString(" ");
        sp.setSpan( is, 0, sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
        return (sp);


        //Tab1 = mContext.getResources().getString(R.string.Tab1);
        //Tab2 = mContext.getResources().getString(R.string.Tab2);
        //Tab3 = mContext.getResources().getString(R.string.Tab3);
        //Tab4 = mContext.getResources().getString(R.string.Tab4);

        //return ( titles[position] );

        /*Tab1 = mContext.getResources().getString(R.string.TabS1);
        Tab2 = mContext.getResources().getString(R.string.TabS2);
        Tab3 = mContext.getResources().getString(R.string.TabS3);
        String[] titles = {Tab1, Tab2, Tab3};
        return ( titles[position] );
        */
    }
}

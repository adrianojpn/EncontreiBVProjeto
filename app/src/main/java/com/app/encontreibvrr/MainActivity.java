package com.app.encontreibvrr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.app.encontreibvrr.activity.ActivityTesteAchados;
import com.app.encontreibvrr.activity.MyDatabaseUtil;
import com.app.encontreibvrr.adapter.TabsAdapterManager;
import com.app.encontreibvrr.firebaseAuth.SignInActivity;
import com.app.encontreibvrr.firebaseStore.MainActivityStorageFire;
import com.app.encontreibvrr.firebaseStore.UploadActivity;
import com.app.encontreibvrr.uploadImagem.MainActivityStorage;
import com.app.encontreibvrr.util.SlidingTabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_main_tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //A FirebaseApp é inicializado por um ContentProvider por isso não é inicializado no
        // momento onCreate () é chamado. Em seguida, chamar Utils.getDatabase () de qualquer atividade
        // de usar o banco de dados. Obtenha seu FirebaseDatabase como este: método de classe
        MyDatabaseUtil.getDatabase();


        MenuLateral (savedInstanceState);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //mViewPager.setAdapter(new TabsAdapterManager(getContext(), getChildFragmentManager()));
        mViewPager.setAdapter(new TabsAdapterManager(getApplicationContext(), getSupportFragmentManager()));
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setCustomTabView(R.layout.tab_layout, R.id.tabText);
        //mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int i) {
                return getResources().getColor(R.color.colorAccent);
            }
        });
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);
    }



    public void MenuLateral (Bundle savedInstanceState){
        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withHeader(R.layout.header_page)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(

                        new PrimaryDrawerItem().withName("Início")
                                //.withDescription("drawer_item_compact_header_desc")
                                .withIdentifier(1).withIcon(getResources()
                                        .getDrawable(R.drawable.ic_movie)),
                        new PrimaryDrawerItem().withName("Achados")
                                //.withDescription("Obejtos e/ou documentos encontrados")
                                .withIdentifier(2).withIcon(getResources()
                                .getDrawable(R.drawable.ic_movie)),
                        new PrimaryDrawerItem().withName("Perdidos")
                                //.withDescription("Obejtos e/ou documentos perdidos")
                                .withIdentifier(3).withIcon(getResources()
                                .getDrawable(R.drawable.ic_movie)),
                        new PrimaryDrawerItem().withName("Adicionar")
                                //.withDescription("Registrar novo documento e/ou objeto")
                                .withIdentifier(4).withIcon(getResources()
                                .getDrawable(R.drawable.ic_movie)),
                        new PrimaryDrawerItem().withName("Procurar")
                                //.withDescription("Pesquisar no banco de dados")
                                .withIdentifier(5).withIcon(getResources()
                                .getDrawable(R.drawable.ic_movie)),
                        new PrimaryDrawerItem().withName("Perfil")
                                //.withDescription("Verificar informações do perfil")
                                .withIdentifier(6).withIcon(getResources()
                                .getDrawable(R.drawable.ic_action_account_circle_40)),

                        new SectionDrawerItem().withName("PREFRÊNCIAS"),

                        new SecondaryDrawerItem().withName("Configurações")
                                .withIcon(getResources()
                                        .getDrawable(R.drawable.tab_more_unselect)),
                        new SecondaryDrawerItem().withName("Sobre")
                                .withIcon(getResources()
                                        .getDrawable(R.drawable.tab_more_unselect))
                        //new SecondaryDrawerItem().withName("Contato").withIcon(getResources().getDrawable(R.drawable.ic_memory)),
                        //new SecondaryDrawerItem().withName("Sobre").withIcon(getResources().getDrawable(R.drawable.ic_memory))



                        /*
                        new PrimaryDrawerItem().withName("drawer_item_compact_header").withDescription("drawer_item_compact_header_desc").withIdentifier(1).withSelectable(false),
                        new PrimaryDrawerItem().withName("drawer_item_action_bar_drawer").withDescription("drawer_item_action_bar_drawer_desc").withIdentifier(2).withSelectable(false),
                        new PrimaryDrawerItem().withName("drawer_item_multi_drawer").withDescription("drawer_item_multi_drawer_desc").withIdentifier(3).withSelectable(false),
                        new PrimaryDrawerItem().withName("drawer_item_non_translucent_status_drawer").withDescription("drawer_item_non_translucent_status_drawer_desc").withIdentifier(4).withSelectable(false).withBadgeStyle(

                        new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                        new PrimaryDrawerItem().withName("drawer_item_advanced_drawer").withDescription("drawer_item_advanced_drawer_desc").withIdentifier(5).withSelectable(false),
                        new PrimaryDrawerItem().withName("drawer_item_embedded_drawer").withDescription("drawer_item_embedded_drawer_desc").withIdentifier(7).withSelectable(false),
                        new PrimaryDrawerItem().withName("drawer_item_fullscreen_drawer").withDescription("drawer_item_fullscreen_drawer_desc").withIdentifier(8).withSelectable(false),
                        new PrimaryDrawerItem().withName("drawer_item_custom_container_drawer").withDescription("drawer_item_custom_container_drawer_desc").withIdentifier(9).withSelectable(false),
                        new PrimaryDrawerItem().withName("drawer_item_menu_drawer").withDescription("drawer_item_menu_drawer_desc").withIdentifier(10).withSelectable(false),
                        new PrimaryDrawerItem().withName("drawer_item_persistent_compact_header").withDescription("drawer_item_persistent_compact_header_desc").withIdentifier(14).withSelectable(false),
                        new PrimaryDrawerItem().withName("drawer_item_crossfade_drawer_layout_drawer").withDescription("drawer_item_crossfade_drawer_layout_drawer_desc").withIdentifier(15).withSelectable(false),
                        new SectionDrawerItem().withName("drawer_item_section_header"),
                        new ExpandableDrawerItem().withName("Collapsable").withIdentifier(19).withSelectable(false).withSubItems(
                        new SecondaryDrawerItem().withName("CollapsableItem").withLevel(2).withIdentifier(2000),
                        new SecondaryDrawerItem().withName("CollapsableItem 2").withIdentifier(2001)
                        ),
                        new SecondaryDrawerItem().withName("drawer item open_source"),
                        new SecondaryDrawerItem().withName("drawer item contact"),
                        new DividerDrawerItem(),
                        new SwitchDrawerItem().withName("Switch"),
                        new SwitchDrawerItem().withName("Switch2"),
                        new ToggleDrawerItem().withName("Toggle"),
                        new DividerDrawerItem(),
                        new SecondarySwitchDrawerItem().withName("Secondary switch"),
                        new SecondarySwitchDrawerItem().withName("Secondary Switch2"),
                        new SecondaryToggleDrawerItem().withName("Secondary toggle")
                        */

                        /*
                        new PrimaryDrawerItem().withName(R.string.drawer_item_compact_header).withDescription(R.string.drawer_item_compact_header_desc).withIcon(GoogleMaterial.Icon.gmd_sun).withIdentifier(1).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_action_bar_drawer).withDescription(R.string.drawer_item_action_bar_drawer_desc).withIcon(FontAwesome.Icon.faw_home).withIdentifier(2).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_multi_drawer).withDescription(R.string.drawer_item_multi_drawer_desc).withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(3).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_non_translucent_status_drawer).withDescription(R.string.drawer_item_non_translucent_status_drawer_desc).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4).withSelectable(false).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_advanced_drawer).withDescription(R.string.drawer_item_advanced_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(5).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_embedded_drawer).withDescription(R.string.drawer_item_embedded_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_battery).withIdentifier(7).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_fullscreen_drawer).withDescription(R.string.drawer_item_fullscreen_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_labels).withIdentifier(8).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_custom_container_drawer).withDescription(R.string.drawer_item_custom_container_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_my_location).withIdentifier(9).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_menu_drawer).withDescription(R.string.drawer_item_menu_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_filter_list).withIdentifier(10).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_mini_drawer).withDescription(R.string.drawer_item_mini_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_battery_charging).withIdentifier(11).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_fragment_drawer).withDescription(R.string.drawer_item_fragment_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_disc_full).withIdentifier(12).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_collapsing_toolbar_drawer).withDescription(R.string.drawer_item_collapsing_toolbar_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_camera_rear).withIdentifier(13).withSelectable(false),
                        new PrimaryDrawerItem().withName("drawer_item_persistent_compact_header").withDescription(R.string.drawer_item_persistent_compact_header_desc).withIcon(GoogleMaterial.Icon.gmd_brightness_5).withIdentifier(14).withSelectable(false),
                        new PrimaryDrawerItem().withName("drawer_item_crossfade_drawer_layout_drawer").withDescription(R.string.drawer_item_crossfade_drawer_layout_drawer_desc).withIcon(GoogleMaterial.Icon.gmd_format_bold).withIdentifier(15).withSelectable(false),
                        new SectionDrawerItem().withName("drawer_item_section_header"),
                        new ExpandableDrawerItem().withName("Collapsable").withIcon(GoogleMaterial.Icon.gmd_collection_case_play).withIdentifier(19).withSelectable(false).withSubItems(
                                new SecondaryDrawerItem().withName("CollapsableItem").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(2000),
                                new SecondaryDrawerItem().withName("CollapsableItem 2").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(2001)
                        ),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github).withIdentifier(20).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withIdentifier(21).withTag("Bullhorn"),
                        new DividerDrawerItem(),
                        new SwitchDrawerItem().withName("Switch").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        new SwitchDrawerItem().withName("Switch2").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener).withSelectable(false),
                        new ToggleDrawerItem().withName("Toggle").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        new DividerDrawerItem(),
                        new SecondarySwitchDrawerItem().withName("Secondary switch").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        new SecondarySwitchDrawerItem().withName("Secondary Switch2").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener).withSelectable(false),
                        new SecondaryToggleDrawerItem().withName("Secondary toggle").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener)

                         */

                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), ActivityTesteAchados.class));
            return true;
        }

        if (id == R.id.action_contaFire){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }
        if (id == R.id.action_MainActivityStorage){
            startActivity(new Intent(this, MainActivityStorage.class));
        }
        if (id == R.id.action_MainActivityStorageFire){
            startActivity(new Intent(this, MainActivityStorageFire.class));
        }

        if (id == R.id.action_UploadActivity){
            startActivity(new Intent(this, UploadActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}

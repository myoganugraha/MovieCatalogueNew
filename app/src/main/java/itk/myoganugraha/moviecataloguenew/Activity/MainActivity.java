package itk.myoganugraha.moviecataloguenew.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import itk.myoganugraha.moviecataloguenew.Fragment.NowPlayingFragment;
import itk.myoganugraha.moviecataloguenew.Fragment.UpcomingFragment;
import itk.myoganugraha.moviecataloguenew.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setupViewPagger(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPagger(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NowPlayingFragment(), getString(R.string.tab1_title));
        adapter.addFragment(new UpcomingFragment(), getString(R.string.tab2_title));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.english){
            Locale appLoc = new Locale("en");
            Locale.setDefault(appLoc);
            Configuration appConfig = new Configuration();
            appConfig.locale = appLoc;
            getResources().updateConfiguration(appConfig,
                    getResources().getDisplayMetrics());
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        else if (item.getItemId() == R.id.indonesia){
            Locale appLoc = new Locale("in");
            Locale.setDefault(appLoc);
            Configuration appConfig = new Configuration();
            appConfig.locale = appLoc;
            getResources().updateConfiguration(appConfig,
                    getResources().getDisplayMetrics());
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}

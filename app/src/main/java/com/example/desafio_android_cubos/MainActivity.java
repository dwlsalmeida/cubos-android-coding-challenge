package com.example.desafio_android_cubos;

import android.os.Bundle;

import com.example.desafio_android_cubos.ui.main.MovieFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.desafio_android_cubos.ui.main.SectionsPagerAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private ViewPager mViewPager = null;
    private MovieFragment mCurrentFragment = null;

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (mCurrentFragment != null) {
            mCurrentFragment.onQueryTextSubmit(query);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (mCurrentFragment != null) {
            mCurrentFragment.onQueryTextChange(newText);
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    private void setupLayout() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(sectionsPagerAdapter);
        mCurrentFragment = (MovieFragment) sectionsPagerAdapter.getItem(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                FragmentPagerAdapter adapter = (FragmentPagerAdapter)mViewPager.getAdapter();
                mCurrentFragment = (MovieFragment) adapter.getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupLayout();
    }
}
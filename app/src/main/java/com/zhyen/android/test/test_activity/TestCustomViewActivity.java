package com.zhyen.android.test.test_activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zhyen.android.R;
import com.zhyen.android.test.test_activity.test_fragment.TestCustomViewFragment;

import java.util.ArrayList;
import java.util.List;

public class TestCustomViewActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    List<PageModel> pageModels = new ArrayList<>();
    private Toolbar testToolBar;

    {
        pageModels.add(new PageModel(R.layout.test_view_practice_color, "画颜色", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_view_practice_circle, "画圆", R.layout.test_view_practice_circle));
//        pageModels.add(new PageModel(R.layout.sample_rect, "", R.layout.practice_rect));
        pageModels.add(new PageModel(R.layout.test_view_practice_point, "画点", R.layout.test_view_practice_point));
//        pageModels.add(new PageModel(R.layout.sample_oval, "", R.layout.practice_oval));
//        pageModels.add(new PageModel(R.layout.sample_line, "", R.layout.practice_line));
//        pageModels.add(new PageModel(R.layout.sample_round_rect, "", R.layout.practice_round_rect));
        pageModels.add(new PageModel(R.layout.test_view_practice_arc, "画圆弧", R.layout.test_view_practice_arc));
//        pageModels.add(new PageModel(R.layout.sample_path, "", R.layout.practice_path));
        pageModels.add(new PageModel(R.layout.test_view_practice_histogram, "直方图", R.layout.test_view_practice_histogram));
        pageModels.add(new PageModel(R.layout.test_view_practice_histogram_2, "直方图2", R.layout.test_view_practice_histogram));
        pageModels.add(new PageModel(R.layout.test_view_practice_histogram_3, "直方图3", R.layout.test_view_practice_histogram));
        pageModels.add(new PageModel(R.layout.test_view_practice_pie_chart, "饼状图", R.layout.test_view_practice_pie_chart));
        pageModels.add(new PageModel(R.layout.test_view_practice_pie_chart_2, "饼状图2", R.layout.test_view_practice_pie_chart));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_custom_view);
        testToolBar = (Toolbar) findViewById(R.id.test_toolbar);
        setSupportActionBar(testToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Draw练习");
        }

        viewPager = findViewById(R.id.test_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.test_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                PageModel pageModel = pageModels.get(position);
                return TestCustomViewFragment.getInstance(pageModel.sampleLayoutRes, pageModel.practiceLayoutRes);
            }

            @Override
            public int getCount() {
                return pageModels.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return pageModels.get(position).titleRes;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private class PageModel {
        @LayoutRes
        int sampleLayoutRes;

        String titleRes;
        @LayoutRes
        int practiceLayoutRes;

        PageModel(@LayoutRes int sampleLayoutRes, String titleRes, @LayoutRes int practiceLayoutRes) {
            this.sampleLayoutRes = sampleLayoutRes;
            this.titleRes = titleRes;
            this.practiceLayoutRes = practiceLayoutRes;
        }
    }
}

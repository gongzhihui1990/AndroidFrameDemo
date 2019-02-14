package com.starkrak.framedemo.postboy;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.starkrak.framedemo.BaseActivity;
import com.starkrak.framedemo.LayoutID;
import com.starkrak.framedemo.R;
import com.starkrak.framedemo.adapter.ViewPagerAdapter;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * @author caroline
 */
@LayoutID(R.layout.activity_post_boy)
public class PostBoyActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabLayout tabs = findViewById(R.id.tabs);
        ViewPager pager = findViewById(R.id.pager);
        String[] tabTitleArray = new String[Content.values().length];

        int i = 0;
        for (Content tab : Content.values()) {
            String tabTitle = tab.getTitle();
            tabs.addTab(tabs.newTab().setText(tabTitle));
            tabTitleArray[i] = tabTitle;
            i++;
        }
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new RequestFragment());
        fragmentList.add(new ResponseFragment());
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getSupportFragmentManager(), this, fragmentList, tabTitleArray);
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

    }

    /**
     * 内容的类型枚举
     */
    enum Content {
        /**
         * Http 请求、返回
         */
        Request("Request"),
        Response("Response");

        private String title;

        Content(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}

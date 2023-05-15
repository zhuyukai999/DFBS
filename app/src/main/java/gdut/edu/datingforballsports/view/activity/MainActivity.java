package gdut.edu.datingforballsports.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.view.adapter.MyFragmentPagerAdapter;
import gdut.edu.datingforballsports.view.fragment.BlankFragment;
import gdut.edu.datingforballsports.view.fragment.ForumListFragment;
import gdut.edu.datingforballsports.view.fragment.HomePageFragment;


public class MainActivity extends BaseActivity{
    ViewPager2 viewPager;
    private LinearLayout lMessage, lForum, lMatch, lHomepage;
    private ImageView ivMessage, ivForum, ivMatch, ivHomepage, ivCorrect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_layout_main);
        initPager();
        initTabView();
    }

    private void initPager() {
        buttonClick(R.id.id_tab_message_button,view->{
            changeTab(view.getId());
        });
        buttonClick(R.id.id_tab_forum_button,view->{
            changeTab(view.getId());
        });
        buttonClick(R.id.id_tab_match_button,view->{
            changeTab(view.getId());
        });
        buttonClick(R.id.id_tab_homepage_button,view->{
            changeTab(view.getId());
        });
        ivMessage = findViewById(R.id.tab_iv_message_button);
        ivForum = findViewById(R.id.tab_iv_forum_button);
        ivMatch = findViewById(R.id.tab_iv_match_button);
        ivHomepage = findViewById(R.id.tab_iv_homepage_button);
        ivHomepage.setSelected(true);
        ivCorrect = ivHomepage;
    }

    private void initTabView() {
        viewPager = findViewById(R.id.button_layout_viewpager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ForumListFragment.newInstance());//"信息"
        fragments.add(ForumListFragment.newInstance());
        fragments.add(ForumListFragment.newInstance());//"匹配"
        fragments.add(HomePageFragment.newInstance());
        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void changeTab(int position) {
        ivCorrect.setSelected(false);
        switch (position){
            case R.id.id_tab_message_button:
                viewPager.setCurrentItem(0);
            case 0:
                ivMessage.setSelected(true);
                ivCorrect = ivMessage;
                break;
            case R.id.id_tab_forum_button:
                viewPager.setCurrentItem(1);
            case 1:
                ivForum.setSelected(true);
                ivCorrect = ivForum;
                break;
            case R.id.id_tab_match_button:
                viewPager.setCurrentItem(2);
            case 2:
                ivMatch.setSelected(true);
                ivCorrect = ivMatch;
                break;
            case R.id.id_tab_homepage_button:
                viewPager.setCurrentItem(3);
            case 3:
                ivHomepage.setSelected(true);
                ivCorrect = ivHomepage;
                break;
        }
    }
}

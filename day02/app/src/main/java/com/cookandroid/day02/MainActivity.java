package com.cookandroid.day02;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout  tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @Override
            public int getItemCount() {
                return 4;
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:  return new DogFragment();
                    case 1:  return new CatFragment();
                    case 2:  return new RabbitFragment();
                    case 3:  return new HorseFragment();
                    default: return new DogFragment();
                }
            }
        });

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0: tab.setIcon(R.drawable.icon_dog); break;
                case 1: tab.setIcon(R.drawable.icon_cat); break;
                case 2: tab.setIcon(R.drawable.icon_rabbit); break;
                case 3: tab.setIcon(R.drawable.icon_horse); break;
            }
        }).attach();
    }
}
package hcmute.nhom1.chatapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import hcmute.nhom1.chatapp.R;
import hcmute.nhom1.chatapp.adapter.ViewPagerAdapter;
import hcmute.nhom1.chatapp.adapter.ViewPagerUserAdapter;


public class UserFragment extends Fragment {

    TabLayout mTabLayout;
    ViewPager2 mViewPage;
    ArrayList<String> tabLayoutList;
    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user, container, false);
        tabLayoutList =new ArrayList<>();
        tabLayoutList.add("Contacts");
        tabLayoutList.add("All Users");
        mTabLayout = view.findViewById(R.id.tabLayout1);
        mViewPage = view.findViewById(R.id.viewPage1);

        ViewPagerUserAdapter viewPagerAdapter = new ViewPagerUserAdapter(getActivity().getSupportFragmentManager() ,getLifecycle());
        mViewPage.setAdapter(viewPagerAdapter);


        new TabLayoutMediator(mTabLayout, mViewPage,
                (tab, position) -> tab.setText(tabLayoutList.get(position))
        ).attach();

        return view;
    }
}
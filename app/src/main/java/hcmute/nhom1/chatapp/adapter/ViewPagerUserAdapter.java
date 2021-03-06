package hcmute.nhom1.chatapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hcmute.nhom1.chatapp.fragment.AllUsersFragment;
import hcmute.nhom1.chatapp.fragment.ContactsFragment;
import hcmute.nhom1.chatapp.fragment.FileFragment;
import hcmute.nhom1.chatapp.fragment.ImageFileFragment;
import hcmute.nhom1.chatapp.fragment.VideoFileFragment;

public class ViewPagerUserAdapter extends FragmentStateAdapter  {

    public ViewPagerUserAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ContactsFragment();
            case 1:
                return new AllUsersFragment();

            default:
                return new ContactsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

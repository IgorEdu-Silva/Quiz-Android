package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PageAdapter extends FragmentStateAdapter {
    public PageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new mathematics();
            case 1:
                return new geography();
            case 2:
                return new history();
            case 3:
                return new entertainment();
            default:
                assert false : "Índice inválido para criar um Fragment";
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }


}

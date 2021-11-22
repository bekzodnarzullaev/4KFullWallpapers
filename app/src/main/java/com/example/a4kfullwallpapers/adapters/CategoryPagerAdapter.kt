package com.example.a4kfullwallpapers.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.a4kfullwallpapers.ui.home.CategoryFragment

class CategoryPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int {
        return 7
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->CategoryFragment("all")
            1->CategoryFragment("new")
            2->CategoryFragment("technology")
            3->CategoryFragment("animal")
            4->CategoryFragment("nature")
            5->CategoryFragment("country")
            6->CategoryFragment("season")
            else -> CategoryFragment("all")
        }

    }

}
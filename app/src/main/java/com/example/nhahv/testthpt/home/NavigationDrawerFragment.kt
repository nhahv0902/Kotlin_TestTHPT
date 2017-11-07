package com.example.nhahv.testthpt.home


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nhahv.testthpt.R
import com.example.nhahv.testthpt.databinding.FragmentNavigationDrawerBinding


/**
 * A simple [Fragment] subclass.
 */
class NavigationDrawerFragment : Fragment() {

    var containerView: View? = null
    private var mDrawerLayout: DrawerLayout? = null
    private var mDrawerToggle: ActionBarDrawerToggle? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentNavigationDrawerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_navigation_drawer, container, false)
        binding.viewModel = (activity as HomeActivity).viewModel
        return binding.root
    }

    fun setUp(fragmentId: Int, drawerLayout: DrawerLayout, toolbar: Toolbar) {
        containerView = activity.findViewById(fragmentId)
        mDrawerLayout = drawerLayout
        mDrawerToggle = object : ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.text_open, R.string.text_close) {
            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
                activity.invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)
                activity.invalidateOptionsMenu()
            }

            override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                toolbar.alpha = 1 - slideOffset / 2
            }
        }
        mDrawerLayout?.setDrawerListener(mDrawerToggle)
        mDrawerLayout?.post({ mDrawerToggle?.syncState() })
    }

}

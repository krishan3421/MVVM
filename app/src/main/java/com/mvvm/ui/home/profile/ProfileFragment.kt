package com.mvvm.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mvvm.R
import com.mvvm.databinding.FragmentProfileBinding
import org.kodein.di.android.x.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class ProfileFragment : Fragment() ,KodeinAware{
    override val kodein by kodein()

    private lateinit var profileViewModel: ProfileViewModel
    private val factory:ProfileViewModelFactory by instance ()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentProfileBinding =DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)
        profileViewModel = ViewModelProvider(this,factory).get(ProfileViewModel::class.java)
       // val root = inflater.inflate(R.layout.fragment_profile, container, false)
        binding.viewmodel=profileViewModel
        binding.lifecycleOwner=this
        return binding.root
    }
}
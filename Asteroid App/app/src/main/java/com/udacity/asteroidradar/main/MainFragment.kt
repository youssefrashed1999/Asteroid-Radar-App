package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidDB
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    lateinit var adapter: AsteroidAdapter
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        adapter = AsteroidAdapter(AsteroidListener {
            findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        })
        binding.asteroidRecycler.adapter = adapter
        viewModel.weekAsteroids.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }

        })
        viewModel.adapter.observe(viewLifecycleOwner, Observer {
            if(it==1){
             viewModel.todayAsteroids.observe(viewLifecycleOwner, Observer {
                 it?.let {
                     adapter.submitList(it)
                 }
             })
            }
            else if(it==2){
                viewModel.weekAsteroids.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        adapter.submitList(it)
                    }
                })
            }
            else if(it==3){
                viewModel.allAsteroids.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        adapter.submitList(it)
                    }
                })
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.show_today_menu ->{

                viewModel.updateAdapter(1)
                true
            }
            R.id.show_week_menu ->{

                viewModel.updateAdapter(2)
                true
            }
            R.id.show_week_menu ->{

                viewModel.updateAdapter(3)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

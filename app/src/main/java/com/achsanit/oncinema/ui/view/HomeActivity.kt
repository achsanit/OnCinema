package com.achsanit.oncinema.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.achsanit.oncinema.data.source.remote.model.Result
import com.achsanit.oncinema.databinding.ActivityHomeBinding
import com.achsanit.oncinema.ui.adapter.BannerAdapter
import com.achsanit.oncinema.ui.adapter.MovieListAdapter
import com.achsanit.oncinema.ui.viewmodel.HomeViewModel
import com.achsanit.oncinema.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setupView()

        observePopularMv()
        observeNowPlayMv()
        observeTopRatedMv()
    }

    private fun observePopularMv() {
        homeViewModel.listPopular.observe(this) {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    it.data?.let { listPopular ->
                        setupRecycler(listPopular,binding.rvPopularMovie)
                    }
                }
                Status.ERROR -> {

                }
            }
        }
    }

    private fun observeNowPlayMv() {
        homeViewModel.listNowPlaying.observe(this) {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    it.data?.let { listNowPlay ->
                        setupNowPlayingBanner(listNowPlay)
                    }
                }
                Status.ERROR -> {

                }
            }
        }
    }

    private fun observeTopRatedMv() {
        homeViewModel.listTopRated.observe(this) {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    it.data?.let { listNowPlay ->
                        setupRecycler(listNowPlay,binding.rvTopRated)
                    }
                }
                Status.ERROR -> {

                }
            }
        }
    }

    private fun setupView() {
        //setBlur
//        val decorView = window.decorView
//        val rootView = decorView.findViewById<ViewGroup>(androidx.appcompat.R.id.content)
//
//        val windowBackground = decorView.background
//
//        blurView.setupWith(rootView,RenderScriptBlur(this))
//            .setFrameClearDrawable(windowBackground)
//            .setBlurRadius(20f)
    }

    private fun setupNowPlayingBanner(data: List<Result>) {
        val adapter = BannerAdapter()
        val viewPager = binding.vpNowPlaying

        adapter.submitData(data)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
//        compositePageTransformer.addTransformer(MarginPageTransformer(10))
        compositePageTransformer.addTransformer(object : ViewPager2.PageTransformer {
            override fun transformPage(page: View, position: Float) {
                val r = 1 - Math.abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
        })
        viewPager.setPageTransformer(compositePageTransformer)
    }

    private fun setupRecycler(data: List<Result>, recyclerView: RecyclerView) {
        val movieListAdapter = MovieListAdapter()
        movieListAdapter.submitListWithFooter(data)

        val recyler = recyclerView
        recyler.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        recyler.layoutManager = layoutManager
        recyler.adapter = movieListAdapter
    }
}
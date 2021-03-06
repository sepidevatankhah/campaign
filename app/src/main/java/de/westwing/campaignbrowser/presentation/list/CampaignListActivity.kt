package de.westwing.campaignbrowser.presentation.list

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import dagger.android.AndroidInjection
import de.westwing.campaignbrowser.databinding.ActivityCampaignListBinding
import de.westwing.campaignbrowser.presentation.base.BaseActivity
import de.westwing.campaignbrowser.presentation.utils.hide
import de.westwing.campaignbrowser.presentation.utils.show
import de.westwing.campaignbrowser.presentation.utils.toastOopsError

class CampaignListActivity : BaseActivity<ListViewState, CampaignViewModel>() {

    lateinit var binding: ActivityCampaignListBinding

    private val campaignAdapter = CampaignListAdapter {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityCampaignListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createViewModel(CampaignViewModel::class.java)
        setupSwipeRefreshLayout()
        viewModel.getCampaignList()
        startObserving()
        initRecyclerView()
    }

    override fun processViewState(state: ListViewState) {
        binding.swipeRefresh.isRefreshing = false
        when (state) {
            is ListViewState.Loading -> {
                if (campaignAdapter.itemCount == 0) {
                    binding.loadingIndicator.show()
                    binding.campaignsRecycler.hide()
                }
            }
            is ListViewState.Loaded -> {
                binding.loadingIndicator.hide()
                binding.campaignsRecycler.show()
                campaignAdapter.submitList(state.campaigns)
            }
            is ListViewState.Error -> {
                binding.loadingIndicator.hide()
                binding.swipeRefresh.isRefreshing = false
                Log.e(
                    "CampaignListActivity",
                    state.throwable.message,
                    state.throwable
                )
                binding.root.toastOopsError()
            }
        }

    }

    private fun initRecyclerView() {
        binding.campaignsRecycler.apply {
            setHasFixedSize(true)
            val colCount =
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 2 else 1
            layoutManager = GridLayoutManager(context, colCount)
            adapter = campaignAdapter
        }
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            viewModel.getCampaignList()
        }
    }
}
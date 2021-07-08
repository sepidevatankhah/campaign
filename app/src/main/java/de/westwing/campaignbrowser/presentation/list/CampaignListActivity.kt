package de.westwing.campaignbrowser.presentation.list

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import de.westwing.campaignbrowser.databinding.ActivityCampaignListBinding
import de.westwing.campaignbrowser.di.ViewModelFactory
import de.westwing.campaignbrowser.presentation.utils.hide
import de.westwing.campaignbrowser.presentation.utils.show
import javax.inject.Inject

class CampaignListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<CampaignViewModel>

    private lateinit var viewModel: CampaignViewModel

    lateinit var binding: ActivityCampaignListBinding

    private val campaignAdapter = CampaignListAdapter {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityCampaignListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CampaignViewModel::class.java)
        viewModel.getCampaignList()

    }

    override fun onStart() {
        super.onStart()
        viewModel.liveData.observe(this, { campaigns -> processViewState(campaigns) })
        initRecyclerView()
    }

    internal fun processViewState(state: ListViewState) {
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
                campaignAdapter.notifyDataSetChanged()
                initRecyclerView()
            }
            is ListViewState.Error -> {
//                binding.swipeRefresh.isRefreshing = false
//                binding.spinner.hide()
                Log.e(
                    "HomeFragment",
                    state.throwable.message,
                    state.throwable
                )
//                binding.root.toastOopsError()
            }
        }

    }

    private fun initRecyclerView() {
        binding.campaignsRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = campaignAdapter
        }
    }
}
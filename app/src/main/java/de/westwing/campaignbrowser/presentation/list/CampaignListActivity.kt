package de.westwing.campaignbrowser.presentation.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import de.westwing.campaignbrowser.databinding.ActivityCampaignListBinding
import de.westwing.campaignbrowser.di.ViewModelFactory
import de.westwing.campaignbrowser.domain.Campaign
import de.westwing.campaignbrowser.presentation.CampaignViewModel
import javax.inject.Inject

class CampaignListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<CampaignViewModel>

    private lateinit var viewModel: CampaignViewModel

    lateinit var binding: ActivityCampaignListBinding

    private val adapter = CampaignListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityCampaignListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CampaignViewModel::class.java)
        binding.campaignsRecycler.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.campaignsData.observe(this, { campaigns -> processViewState(campaigns) })
    }

    internal fun processViewState(campaigns: List<Campaign>) {
        adapter.submitList(campaigns)
    }
}
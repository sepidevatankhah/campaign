package de.westwing.campaignbrowser.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.westwing.campaignbrowser.databinding.ItemCampaignBinding
import de.westwing.campaignbrowser.domain.model.Campaign

typealias ClickListener = (Campaign) -> Unit

class CampaignListAdapter(private val onItemClicked: ClickListener) :
    ListAdapter<Campaign, CampaignViewHolder>(campaignDiff) {

    companion object {
        val campaignDiff = object : DiffUtil.ItemCallback<Campaign>() {
            override fun areItemsTheSame(oldItem: Campaign, newItem: Campaign) = oldItem == newItem
            override fun areContentsTheSame(oldItem: Campaign, newItem: Campaign) =
                oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignViewHolder {
        return CampaignViewHolder(
            binding = ItemCampaignBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClicked = onItemClicked

        )
    }

    override fun onBindViewHolder(holder: CampaignViewHolder, position: Int) {
        val campaign = getItem(position)
        holder.bind(campaign)
    }
}

class CampaignViewHolder(
    val binding: ItemCampaignBinding,
    private val onItemClicked: ClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(model: Campaign) {
        with(binding) {
            model.apply {
                campaignName.text = name
                campaignDescription.text = description
                root.setOnClickListener { onItemClicked.invoke(this) }
            }
        }
    }
}
package com.ahmedvargos.uicomponents.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.uicomponents.custom_views.AgentCellView
import com.ahmedvargos.uicomponents.databinding.ItemAgentCellBinding

class AgentsRecyclerAdapter(
    private var agentsList: MutableList<AgentInfo> = mutableListOf(),
    private val agentCellActionsDelegate: AgentCellView.AgentCellActionsDelegate
) : RecyclerView.Adapter<AgentsRecyclerAdapter.AgentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgentViewHolder {
        val binding = ItemAgentCellBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AgentViewHolder(binding)
    }

    fun addAgents(newAgentsList: List<AgentInfo>) {
        agentsList.clear()
        agentsList.addAll(newAgentsList)

        notifyDataSetChanged()
    }

    override fun getItemCount() = agentsList.size

    override fun onBindViewHolder(holder: AgentViewHolder, position: Int) =
        holder.bind(agentsList[position])

    inner class AgentViewHolder(private val binding: ItemAgentCellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(agent: AgentInfo) {
            binding.agentCellView.initDetails(
                agentInfo = agent,
                actionDelegate = agentCellActionsDelegate
            )
        }
    }
}
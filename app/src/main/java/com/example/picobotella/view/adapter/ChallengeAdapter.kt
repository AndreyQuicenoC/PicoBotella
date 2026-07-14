package com.example.picobotella.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.picobotella.databinding.ItemChallengeBinding
import com.example.picobotella.model.Challenge

class ChallengeAdapter(
    private var challenges: List<Challenge>,
    private val onEditClick: (Challenge) -> Unit,
    private val onDeleteClick: (Challenge) -> Unit
) : RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>() {

    class ChallengeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Usamos bind() manual para evitar problemas de inicialización en el constructor
        fun bind(
            challenge: Challenge,
            onEdit: (Challenge) -> Unit,
            onDelete: (Challenge) -> Unit
        ) {
            val binding = ItemChallengeBinding.bind(itemView)
            binding.txtDescription.text = challenge.description
            binding.btnEditChallenge.setOnClickListener { onEdit(challenge) }
            binding.btnDeleteChallenge.setOnClickListener { onDelete(challenge) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.example.picobotella.R.layout.item_challenge, parent, false)
        return ChallengeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        holder.bind(challenges[position], onEditClick, onDeleteClick)
    }

    override fun getItemCount(): Int = challenges.size

    fun updateList(newChallenges: List<Challenge>) {
        this.challenges = newChallenges
        notifyDataSetChanged()
    }
}

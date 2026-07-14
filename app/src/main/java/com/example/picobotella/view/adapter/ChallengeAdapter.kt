package com.example.picobotella.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.picobotella.databinding.ItemChallengeBinding
import com.example.picobotella.model.Challenge

class ChallengeAdapter(
    private var challenges: List<Challenge>,
    private val onEditClick: (Challenge) -> Unit,
    private val onDeleteClick: (Challenge) -> Unit
) : RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>() {

    inner class ChallengeViewHolder(private val binding: ItemChallengeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(challenge: Challenge) {
            binding.txtDescription.text = challenge.description
            
            // HU 8.0 y 9.0: Mantener funcionalidad de clics para edición y eliminación
            binding.btnEditChallenge.setOnClickListener { onEditClick(challenge) }
            binding.btnDeleteChallenge.setOnClickListener { onDeleteClick(challenge) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        // Adoptamos la inflación con DataBinding proveniente de 'develop'
        val binding = ItemChallengeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChallengeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        holder.bind(challenges[position])
    }

    override fun getItemCount(): Int = challenges.size

    fun updateList(newList: List<Challenge>) {
        this.challenges = newList
        notifyDataSetChanged()
    }
}

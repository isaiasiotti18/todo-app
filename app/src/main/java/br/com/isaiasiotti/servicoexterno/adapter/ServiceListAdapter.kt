package br.com.isaiasiotti.servicoexterno.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.isaiasiotti.servicoexterno.R
import br.com.isaiasiotti.servicoexterno.adapter.ServiceListAdapter.ServiceViewHolder
import br.com.isaiasiotti.servicoexterno.databinding.ItemServiceBinding
import br.com.isaiasiotti.servicoexterno.model.Service

class ServiceListAdapter: ListAdapter<Service, ServiceViewHolder>(DiffCallback()) {

  var listenerEdit: (Service) -> Unit = {}
  var listenerDelete: (Service) -> Unit = {}

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
    val inflate = LayoutInflater.from(parent.context)
    val binding = ItemServiceBinding.inflate(inflate, parent, false)
    return ServiceViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ServiceViewHolder(
    private val binding: ItemServiceBinding
    ): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Service) {
      binding.serviceTitle.text = item?.title
      binding.serviceDate.text = item?.date
      binding.ivMore.setOnClickListener {
        showPopup(item)
      }
    }

    private fun showPopup(item: Service) {
      val ivMore = binding.ivMore
      val popupMenu = PopupMenu(ivMore.context, ivMore)

      popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

      popupMenu.setOnMenuItemClickListener {
        when (it.itemId) {
          R.id.action_edit -> listenerEdit(item)
          R.id.action_delete -> listenerDelete(item)
        }
        return@setOnMenuItemClickListener true
      }
      popupMenu.show()
    }
  }
}

class DiffCallback: DiffUtil.ItemCallback<Service>() {
  override fun areItemsTheSame(oldItem: Service, newItem: Service) = oldItem == newItem
  override fun areContentsTheSame(oldItem: Service, newItem: Service) = oldItem.id == newItem.id
}
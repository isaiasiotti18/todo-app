package br.com.isaiasiotti.servicoexterno.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.isaiasiotti.servicoexterno.adapter.ServiceListAdapter
import br.com.isaiasiotti.servicoexterno.databinding.ActivityMainBinding
import br.com.isaiasiotti.servicoexterno.datasource.ServiceDataSource

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private val adapter by lazy { ServiceListAdapter() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.rvServices.adapter = adapter
    updateList()

    insertListeners()
  }

  private fun insertListeners() {
    binding.fabAddService.setOnClickListener {
      startActivityForResult(Intent(this, AddServiceActivity::class.java), CREATE_NEW_SERVICE)
    }

    adapter.listenerEdit = {
      val intent = Intent(this, AddServiceActivity::class.java)
      intent.putExtra(AddServiceActivity.SERVICE_ID, it.id)
      startActivityForResult(intent, CREATE_NEW_SERVICE)
    }

    adapter.listenerDelete = {
      ServiceDataSource.deleteService(it)
      updateList()
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == CREATE_NEW_SERVICE && resultCode == Activity.RESULT_OK) updateList()
  }

  private fun updateList() {
    val list = ServiceDataSource.getList()

    binding.includeEmptyState.emptyState.visibility = if (list.isEmpty()) View.VISIBLE
    else View.GONE

    adapter.submitList(list)
  }


  companion object {
    private const val CREATE_NEW_SERVICE = 1000
  }
}
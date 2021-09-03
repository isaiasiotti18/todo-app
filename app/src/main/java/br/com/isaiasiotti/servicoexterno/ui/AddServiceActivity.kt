package br.com.isaiasiotti.servicoexterno.ui

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import br.com.isaiasiotti.servicoexterno.databinding.ActivityAddServiceBinding
import br.com.isaiasiotti.servicoexterno.datasource.ServiceDataSource
import br.com.isaiasiotti.servicoexterno.extensions.format
import br.com.isaiasiotti.servicoexterno.extensions.text
import br.com.isaiasiotti.servicoexterno.model.Service
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class AddServiceActivity: AppCompatActivity() {

  private lateinit var binding: ActivityAddServiceBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityAddServiceBinding.inflate(layoutInflater)
    setContentView(binding.root)

    if (intent.hasExtra(SERVICE_ID)) {
      val serviceId = intent.getStringExtra(SERVICE_ID)
      ServiceDataSource.findById(serviceId)?.let {
        binding.serviceTitle.text = it.title
        binding.serviceDescription.text = it.description
        binding.serviceDate.text = it.date
      }
    }

    insertListener()
  }

  private fun insertListener() {
    binding.serviceDate.editText?.setOnClickListener {
      val datePicker = MaterialDatePicker.Builder.datePicker().build()
      datePicker.addOnPositiveButtonClickListener {
        val timezone = TimeZone.getDefault()
        val offset = timezone.getOffset(Date().time) * -1
        binding.serviceDate.text = Date(it + offset).format()
      }
      datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
    }

    binding.btnCancelService.setOnClickListener {
      finish()
    }

    binding.btnNewService.setOnClickListener {
      val service = Service(
        title = binding.serviceTitle.text,
        description = binding.serviceDescription.text,
        date = binding.serviceDate.text,
        id = intent.getStringExtra(SERVICE_ID).toString()
      )
      ServiceDataSource.insertService(service)
      setResult(Activity.RESULT_OK)
      finish()
    }
  }

  companion object {
    const val SERVICE_ID = "service_id"
  }
}
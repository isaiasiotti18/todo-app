package br.com.isaiasiotti.servicoexterno.datasource

import br.com.isaiasiotti.servicoexterno.model.Service
import java.util.*

object ServiceDataSource {
  private val list = arrayListOf<Service>()

  fun getList() = list.toList()

  fun findById(serviceId: String?) = list.find { it.id == serviceId }

  fun insertService(service: Service) {
    val updateService = findById(service.id)

    if (service.id == updateService?.id) {
      list.remove(service)
      list.add(service.copy(id = UUID.randomUUID().toString()))
    } else {
      list.add(service.copy(id = UUID.randomUUID().toString()))
    }
  }

  fun deleteService(service: Service) = list.remove(service)
}
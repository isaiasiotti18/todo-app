package br.com.isaiasiotti.servicoexterno.model

import java.util.*

data class Service(
  val title: String,
  val description: String,
  val date: String,
  val id: String,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Service

    if (id != other.id) return false

    return true
  }

  override fun hashCode(): Int {
    return id.hashCode()
  }
}

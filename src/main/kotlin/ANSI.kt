package ar.edu.algo2

// Simulo un servicio
interface NotificadorANSI{
    fun enviar(interfaz: InfoANSI)
}

data class InfoANSI(val from: String, val body : MutableList<ANSIDTO>)

// DTO - Data Transfer Object - Encapsulamiento de información
data class ANSIDTO(
    val codigo:String,
    val desarrollo: String,
    val nombreDePeriodista: String,
    val prioridad: String)
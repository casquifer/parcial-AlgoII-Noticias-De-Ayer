package ar.edu.algo2

import java.time.LocalDate
import java.time.temporal.ChronoUnit

//TEMPLATE METHOD
// Tengo una clase abstracta que define 2 Template Methods, que establecen el esqueleto del
// algoritmo y delegan parte del comportamiento a métodos que las subclases deben implementar (primitivas).
// TM: esCopada() y esSensacionalista()
abstract class Noticia(
    val codigo: String,
    val fecha: LocalDate,
    val periodista: Periodista,
    var importancia: Int,
    var titulo: String,
    var desarrollo: String
) {
    fun esImportante() = importancia >= 8

    fun esNueva() = ChronoUnit.DAYS.between(fecha, LocalDate.now()).toInt() < 3

    abstract fun copadaEspecifica(): Boolean //primitiva

    fun esCopada() = esImportante() && esNueva() && copadaEspecifica()

    fun esSensacionalista() = tituloSensacional() && tipoSensacionalista()

    val palabrasAComparar = listOf("espectacular","increible","grandioso")

    fun tituloSensacional() = palabrasAComparar.any { titulo.lowercase().contains(it) }

    abstract fun tipoSensacionalista(): Boolean //primitiva

    abstract fun esEspecial(): Boolean

    fun esMedioImportante() = importancia in 5..7

    fun leGustaAlPeriodista() = periodista.leGusta(this)

    fun esDeHoy() = fecha == LocalDate.now()
}

class Articulo(val links: MutableList<String>,
               codigo: String = "02",
               fecha: LocalDate,
               periodista: Periodista,
               importancia: Int,
               titulo: String,
               desarrollo: String): Noticia(codigo,fecha, periodista, importancia, titulo, desarrollo){

    override fun copadaEspecifica() = links.size >= 2

    override fun tipoSensacionalista() = true

    override fun esEspecial() = false
}

class Chivo(var costo:Double,
            codigo: String = "01",
            fecha: LocalDate,
            periodista: Periodista,
            importancia: Int,
            titulo: String,
            desarrollo: String): Noticia(codigo,fecha, periodista, importancia, titulo, desarrollo){

   var montoASuperar: Int = 2_000_000

    override fun copadaEspecifica() = costo > montoASuperar

    override fun tipoSensacionalista() = true

    override fun esEspecial() = copadaEspecifica()
}

class Reportaje(val entrevistado: String,
                val esMusico: Boolean,
                codigo: String = "R",
                fecha: LocalDate,
                periodista: Periodista,
                importancia: Int,
                titulo: String,
                desarrollo: String): Noticia(codigo,fecha, periodista, importancia, titulo, desarrollo){

    val nombreAComparar: String = "Dibu Martinez"

    override fun copadaEspecifica() = entrevistado.length % 2 != 0

    override fun tipoSensacionalista() = entrevistado == nombreAComparar

    override fun esEspecial() = esMusico
}

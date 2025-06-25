package ar.edu.algo2

import java.time.LocalDate

// STRATEGY + INYECCIÓN DE DEPENDENCIA
// Usamos inyección por constructor (Constructor Injection) para recibir una Preferencia (estrategia de publicación)

class Periodista(
    val fechaIngreso: LocalDate,
    val nombre:String,
    var preferencia: Preferencia,
    var saldo: Double
){
    fun leGusta(noticia: Noticia) = preferencia.leGusta(noticia)



    fun cobrar(monto:Double) {
        saldo += monto
    }
}

// STRATEGY
// Encapsulamos la lógica de cada comportamiento (preferencias) y los hacemos intercambiables en tiempo de ejecución.
// El periodista delega en la preferencia sin conocer su implementación concreta.

interface Preferencia {
    fun leGusta(noticia:Noticia): Boolean
}

//Stateless - Los 3 son objetos, no clases, porque no tienen estado (son reutilizables)
object Copada: Preferencia{
    override fun leGusta(noticia: Noticia) = noticia.esCopada()
}

object Sensacionalista: Preferencia{
    override fun leGusta(noticia: Noticia) = noticia.esSensacionalista()
}

object JoseDeZer:Preferencia{
    override fun leGusta(noticia: Noticia) = noticia.titulo.first() == 'T'
}

object Relajada:Preferencia{
    override fun leGusta(noticia: Noticia) = true
}

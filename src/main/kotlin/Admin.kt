package ar.edu.algo2

import java.time.LocalDate

data class Publicacion(
    val fecha: LocalDate,
    val noticias: MutableList<Noticia>)

//STRATEGY + COMPOSITE
interface CriterioEleccion {
    fun cumple(noticia:Noticia): Boolean
}

object GustaAlPeriodista: CriterioEleccion{
    override fun cumple(noticia: Noticia) = noticia.periodista.leGusta(noticia)
}

object NoticiaSensacionalista: CriterioEleccion{
    override fun cumple(noticia: Noticia) = noticia.esSensacionalista()
}

class RangoImportancia(val valorMinimo: Int, val valorMaximo: Int): CriterioEleccion{
    override fun cumple(noticia: Noticia) = noticia.importancia in valorMinimo..valorMaximo
}

class Combineta(val criterios : MutableList<CriterioEleccion>): CriterioEleccion{ //Constructor Inyector
    override fun cumple(noticia: Noticia) = criterios.all{ it.cumple(noticia)}
}

// ADMINISTRADOR COMO SINGLETON (object): reutilizable globalmente.
// Tiene estado mutable: criterio de elección, observers y lista de noticias confirmadas.
// Las noticias a revisar se pasan como parámetro, no pertenecen al administrador.

object Administrador{
    lateinit var criterioEleccion: CriterioEleccion // Setter Injection

    var noticiasConfirmadas = mutableListOf<Noticia>() // Guardo las noticias que cumplen criterios

    val publicacionObservers = mutableListOf<PublicacionObserver>() // Guardo los observers que quiero usar

    fun agregarPublicacionObserver(observer: PublicacionObserver){
        publicacionObservers.add(observer)
    }

    fun removerPublicacionObserver(observer: PublicacionObserver){
        publicacionObservers.remove(observer)
    }

    // Genero la publicacion
    fun generarPublicacion(noticiasArevisar: MutableList<Noticia>){
        noticiasArevisar.forEach {
            if(criterioEleccion.cumple(it)){
                noticiasConfirmadas.add(it)}
        }
    }

    fun agregarNoticiaConfirmada(noticia: Noticia){
        noticiasConfirmadas.add(noticia)
    }

    fun removerNoticiaConfirmada(noticia: Noticia){
        noticiasConfirmadas.remove(noticia)
    }

    fun confirmarPublicacion(): Publicacion{
        val publicacion = Publicacion(LocalDate.now(), noticiasConfirmadas)

        publicacionObservers.forEach{ it.publicacionRealizada(publicacion)}

        noticiasConfirmadas.clear()

        return publicacion
    }
}
package ar.edu.algo2

//OBSERVERS
//Un observer me permite ejecutar acciones cuando sucede un evento en específico.
// En este caso, eso sucede cada vez que se genera una publicación.

interface PublicacionObserver{
    fun publicacionRealizada(publicacion: Publicacion)
}

class PagoAPeriodistaObserver: PublicacionObserver {
    val minimoDePalabras: Int = 1000
    val pagoBase: Double = 50_000.00
    val pagoPorSuperarPalabras: Double = 75_000.00

    fun superaPalabras(noticia: Noticia) = noticia.desarrollo.split(" ").size > minimoDePalabras

    override fun publicacionRealizada(publicacion: Publicacion) {
        publicacion.noticias.forEach {
            if (superaPalabras(it)) {
                it.periodista.cobrar(pagoPorSuperarPalabras)
            } else {
                it.periodista.cobrar(pagoBase)
            }
        }
    }
}

class MailObserver(var mailSender: MailSender, var mailEditor:String): PublicacionObserver { //Constructor Injection

    override fun publicacionRealizada(publicacion: Publicacion){
        publicacion.noticias.forEach {
            if (it.esEspecial()){
                mailSender.sendMail(
                    Mail(from= "noticiasdeayer@gmail.com",
                        to=mailEditor,
                        subject="Noticia Especial",
                        body="Se publicó la noticia especial: ${it.titulo}."
                    )
                )
            }
        }
    }
}

class NotificarANSI(): PublicacionObserver {
    lateinit var notificadorANSI: NotificadorANSI //Uso lateinit para evitar declarar variables null

    fun prioridad(noticia: Noticia): String {
        return when {
            noticia.esImportante() -> "A"
            noticia.esMedioImportante() -> "M"
            else -> "C"
        }
    }

    fun crearLista(noticias: MutableList<Noticia>): MutableList<ANSIDTO>{
        val maxCaracteresDesarrollo = 100
        val listaFormateada = mutableListOf<ANSIDTO>()

        noticias.forEach{
            val ansiDTO =
                ANSIDTO(codigo = it.codigo,
                    desarrollo = it.desarrollo.take(maxCaracteresDesarrollo),
                    nombreDePeriodista = it.periodista.nombre,
                    prioridad = prioridad(it)
                )

            listaFormateada.add(ansiDTO)
        }

        return listaFormateada
    }

    override fun publicacionRealizada(publicacion: Publicacion) {
        val listaANSI = crearLista(publicacion.noticias)

        notificadorANSI.enviar(
            InfoANSI(from="Noticias a publicar del diario Noticias de Ayer:",
                body = listaANSI
            )
        )
    }
}
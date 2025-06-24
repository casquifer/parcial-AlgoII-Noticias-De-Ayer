package ar.edu.algo2

interface MailSender{
    fun sendMail(mail:Mail)
}

data class Mail(val from:String, val to:String, val subject:String, val body:String)

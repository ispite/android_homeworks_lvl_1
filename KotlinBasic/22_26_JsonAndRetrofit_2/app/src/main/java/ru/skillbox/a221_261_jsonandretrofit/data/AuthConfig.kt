package ru.skillbox.a221_261_jsonandretrofit.data

import net.openid.appauth.ResponseTypeValues

object AuthConfig {

    const val AUTH_URI = "https://github.com/login/oauth/authorize"
    const val TOKEN_URI = "https://github.com/login/oauth/access_token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "user,repo"

    const val CLIENT_ID = "995d251ad3e667e03fdb"
    const val CLIENT_SECRET = "4dd3e16bc67e083bdd9acdfbba58690320259331"
    const val CALLBACK_URL = "skillbox://skillbox.ru/callback"
    lateinit var TOKEN : String
}
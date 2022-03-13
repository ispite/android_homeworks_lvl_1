package ru.skillbox.a221_261_jsonandretrofit.ui.auth

import android.app.Application
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import ru.skillbox.a221_261_jsonandretrofit.R
import ru.skillbox.a221_261_jsonandretrofit.data.AuthConfig
import ru.skillbox.a221_261_jsonandretrofit.data.AuthRepository
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteUser
import ru.skillbox.a221_261_jsonandretrofit.ui.current_user.CurrentUserRepository
import ru.skillbox.a221_261_jsonandretrofit.utils.SingleLiveEvent

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepository = AuthRepository()
    private val currentUserRepository = CurrentUserRepository()
    private val authService: AuthorizationService = AuthorizationService(getApplication())
    private val openAuthPageLiveEvent = SingleLiveEvent<Intent>()
    private val toastLiveEvent = SingleLiveEvent<Int>()
    private val loadingMutableLiveData = MutableLiveData(false)
    private val authSuccessLiveEvent = SingleLiveEvent<Unit>()

    val openAuthPageLiveData: LiveData<Intent>
        get() = openAuthPageLiveEvent

    val loadingLiveData: LiveData<Boolean>
        get() = loadingMutableLiveData

    val toastLiveData: LiveData<Int>
        get() = toastLiveEvent

    val authSuccessLiveData: LiveData<Unit>
        get() = authSuccessLiveEvent

    private val userListLiveData = MutableLiveData<List<RemoteUser>>(emptyList())

    fun onAuthCodeFailed(exception: AuthorizationException) {
        toastLiveEvent.postValue(R.string.auth_canceled)
    }

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {
        loadingMutableLiveData.postValue(true)
        authRepository.performTokenRequest(
            authService = authService,
            tokenRequest = tokenRequest,
            onComplete = {
                loadingMutableLiveData.postValue(false)
                authSuccessLiveEvent.postValue(Unit)
            },
            onError = {
                loadingMutableLiveData.postValue(false)
                toastLiveEvent.postValue(R.string.auth_canceled)
            }
        )
    }

    fun openLoginPage() {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(getApplication(), R.color.colorAccent))
            .build()

        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRepository.getAuthRequest(),
            customTabsIntent
        )

        openAuthPageLiveEvent.postValue(openAuthPageIntent)
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }

    fun getAuthenticatedUser() {
        currentUserRepository.getAuthenticatedUser({ user ->
            AuthConfig.USERNAME = user.username
        }, { _ ->
        })
    }
}
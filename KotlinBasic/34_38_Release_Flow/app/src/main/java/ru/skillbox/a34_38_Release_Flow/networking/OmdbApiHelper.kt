package ru.skillbox.a34_38_Release_Flow.networking

import kotlinx.coroutines.flow.Flow
import ru.skillbox.a34_38_Release_Flow.data.OmdbResponse

interface OmdbApiHelper {

    fun getSearchMovieCall(title: String, typeOfVideo: String): Flow<OmdbResponse>
}
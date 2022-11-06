package ru.skillbox.a30_34_flow.networking

import kotlinx.coroutines.flow.Flow
import ru.skillbox.a30_34_flow.data.OmdbResponse

interface OmdbApiHelper {

    fun getSearchMovieCall(title: String, typeOfVideo: String): Flow<OmdbResponse>
}
package ru.skillbox.a30_34_flow.networking

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.skillbox.a30_34_flow.data.OmdbResponse

class OmdbApiHelperImpl(private val omdbApi: OmdbApi) : OmdbApiHelper {

    override fun getSearchMovieCall(
        title: String,
        typeOfVideo: String
    ): Flow<OmdbResponse> = flow {
        emit(omdbApi.getSearchMovieCall(title, typeOfVideo))
    }.flowOn(Dispatchers.IO)
}
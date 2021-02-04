package com.ahmedvargos.base.domain

import com.ahmedvargos.base.data.FailureData
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.base.utils.NetworkCodes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

/**
 * Executes business logic in its execute method and keep posting updates to the result as
 * [Result<R>].
 */
abstract class FlowUseCase<in P, R>() {
    suspend operator fun invoke(parameters: P? = null): Flow<Resource<R>> = execute(parameters)
        .catch { e ->
            emit(
                Resource.error(
                    FailureData(
                        code = NetworkCodes.GENERIC_ERROR,
                        message = e.localizedMessage
                    )
                )
            )
        }

    protected abstract suspend fun execute(parameters: P? = null): Flow<Resource<R>>
}

package hoang.nguyenminh.base.usecase

import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class CoroutinesUseCase<out RESULT, in PARAMS> {

    suspend operator fun invoke(
        context: CoroutineContext,
        params: PARAMS
    ): RESULT = withContext(context) {
        run(params)
    }

    abstract suspend fun run(params: PARAMS): RESULT
}
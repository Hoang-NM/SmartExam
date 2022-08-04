package hoang.nguyenminh.smartexam.interactor.exam

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.smartexam.interactor.doneExams
import hoang.nguyenminh.smartexam.interactor.todoExams
import hoang.nguyenminh.smartexam.model.ResultWrapper
import hoang.nguyenminh.smartexam.model.exam.Exam
import hoang.nguyenminh.smartexam.model.exam.ExamStatus
import hoang.nguyenminh.smartexam.model.exam.GetExamListRequest
import hoang.nguyenminh.smartexam.repository.SmartExamCloudRepository
import javax.inject.Inject

class GetExamListUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<ResultWrapper<List<Exam>>, GetExamListRequest>() {

    override suspend fun run(params: GetExamListRequest): ResultWrapper<List<Exam>> {
        val exams = when (params.status) {
            ExamStatus.IN_PROGRESS.value -> todoExams()
            ExamStatus.DONE.value -> doneExams()
            else -> emptyList()
        }
        return ResultWrapper.Success(exams)
    }
}
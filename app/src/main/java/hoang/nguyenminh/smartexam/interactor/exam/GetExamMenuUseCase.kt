package hoang.nguyenminh.smartexam.interactor.exam

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.model.AppNavigator
import hoang.nguyenminh.smartexam.model.main.MenuItem
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import javax.inject.Inject

class GetExamMenuUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<List<MenuItem>, Unit>() {

    override suspend fun run(params: Unit): List<MenuItem> = getExamMenu()

    private fun getExamMenu(): List<MenuItem> = listOf(
        MenuItem(
            AppNavigator.MENU_EXAM_EXECUTION,
            R.drawable.ic_document,
            R.string.lbl_exam_execution
        ),
        MenuItem(
            AppNavigator.MENU_EXAM_HISTORY,
            R.drawable.ic_exam_history,
            R.string.lbl_exam_history
        )
    )
}
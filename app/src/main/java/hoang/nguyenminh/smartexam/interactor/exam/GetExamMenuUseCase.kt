package hoang.nguyenminh.smartexam.interactor.exam

import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.model.AppNavigator
import hoang.nguyenminh.smartexam.model.main.MenuItem
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import javax.inject.Inject

class GetExamMenuUseCase @Inject constructor(private val repository: SmartExamCloudRepository) {

    fun getExamMenu(): List<MenuItem> = listOf(
        MenuItem(
            AppNavigator.MENU_EXAM_EXECUTION,
            R.drawable.ic_faze,
            R.string.lbl_exam_execution
        ),
        MenuItem(
            AppNavigator.MENU_EXAM_CAPTURE,
            R.drawable.ic_faze,
            R.string.lbl_exam_capture
        )
    )
}
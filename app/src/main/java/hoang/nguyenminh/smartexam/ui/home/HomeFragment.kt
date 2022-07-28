package hoang.nguyenminh.smartexam.ui.home

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.renderer.XAxisRendererHorizontalBarChart
import com.github.mikephil.charting.utils.MPPointF
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.collectLatestOnLifecycle
import hoang.nguyenminh.smartexam.NavigationMainDirections
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentHomeBinding
import hoang.nguyenminh.smartexam.model.exam.Exam
import hoang.nguyenminh.smartexam.model.exam.ExamAction
import hoang.nguyenminh.smartexam.ui.home.adapter.TodoExamAdapter

@AndroidEntryPoint
class HomeFragment : SmartExamFragment<FragmentHomeBinding>() {

    override val viewModel: HomeViewModel by viewModels()

    private var adapter: TodoExamAdapter? = null

    private var colors = listOf(R.color.color_todo, R.color.color_achieve, R.color.color_total)

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
        chart.create()
        recTodoExams.adapter = TodoExamAdapter {
            findNavController().navigate(
                NavigationMainDirections.toExamDetail(it, ExamAction.EXECUTION)
            )
        }.also { adapter = it }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.flowOfHomeContent.collectLatestOnLifecycle(viewLifecycleOwner) {
            it ?: return@collectLatestOnLifecycle
            binding?.apply {
                adapter?.submitList(it.todoExams?.map(Exam::toExamModel))
                setChartData(
                    listOf(
                        it.getTodoExamsCount().toFloat(),
                        it.completedExams.toFloat(),
                        it.totalExams.toFloat(),
                    )
                )
            }
        }
    }

    private fun HorizontalBarChart.create() {
        description.isEnabled = false
        setScaleEnabled(false)
        legend.isEnabled = false
        setXAxisRenderer(object : XAxisRendererHorizontalBarChart(
            viewPortHandler,
            xAxis,
            getTransformer(YAxis.AxisDependency.LEFT),
            this
        ) {
            override fun drawLabel(
                c: Canvas?,
                formattedLabel: String?,
                x: Float,
                y: Float,
                anchor: MPPointF?,
                angleDegrees: Float
            ) {
                val colorIndex = formattedLabel?.toInt()?.minus(1) ?: -1
                if (colorIndex !in colors.indices) return
                mAxisLabelPaint.color =
                    resources.getColor(colors[colorIndex], requireContext().theme)
                mAxisLabelPaint.textSize =
                    resources.getDimension(hoang.nguyenminh.base.R.dimen.font_xxxlarge)
                super.drawLabel(
                    c,
                    resources.getString(R.string.dashboard_dot),
                    x,
                    y,
                    anchor,
                    angleDegrees
                )
            }
        })
        xAxis.apply {
            axisMinimum = 0f
            granularity = 1f
            position = XAxis.XAxisPosition.BOTTOM
            setDrawAxisLine(false)
            enableGridDashedLine(8f, 8f, 0f)
        }
        axisLeft.apply {
            axisMinimum = 0f
            setDrawLabels(false)
            setDrawGridLines(false)
            setDrawAxisLine(false)
        }
        axisRight.apply {
            granularity = 1f
            axisMinimum = 0f
            setDrawAxisLine(false)
            enableGridDashedLine(8f, 8f, 0f)
        }
    }

    private fun setChartData(values: List<Float>) {
        binding?.apply {
            val entries = values.mapIndexed { index, value ->
                BarEntry(index.toFloat() + 1, value)
            }
            val dataset = BarDataSet(entries, "").apply {
                setDrawValues(false)
            }
            chart.apply {
                data = BarData(dataset).apply {
                    barWidth = 0.25f
                    isHighlightEnabled = false
                }
                data.notifyDataChanged()
                notifyDataSetChanged()
                invalidate()
            }
        }
    }
}
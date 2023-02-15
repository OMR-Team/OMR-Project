package com.lok.dev.omrchecker.subject.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.lok.dev.commonbase.BaseDialogFragment
import com.lok.dev.omrchecker.databinding.DialogScoreInputBinding
import com.lok.dev.omrchecker.subject.OmrViewModel

class ScoreInputDialog : BaseDialogFragment<DialogScoreInputBinding, Unit>() {

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DialogScoreInputBinding.inflate(inflater, container, false)

    private var adapter : ScoreInputAdapter? = null

    private val viewModel: ScoreInputViewModel by viewModels()
    private val omrViewModel : OmrViewModel by activityViewModels()

    override fun initDialogFragment(savedInstanceState: Bundle?) {

        initAdapter()
        adapter?.set(omrViewModel.answerInput.value)
        viewModel.scoreInputList = omrViewModel.answerInput.value.toMutableList()

        // TODO 확인 버튼 누르면 adapter 의 리스트 가져와서 db 에 저장
    }

    private fun initAdapter() = with(binding) {
        adapter = ScoreInputAdapter(requireContext())
        scoreList.layoutManager = GridLayoutManager(requireContext(), 3)
        scoreList.adapter = adapter
    }

}
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aviatickets.R
import com.example.aviatickets.databinding.FragmentOfferListBinding
import com.example.aviatickets.model.entity.SortType
import com.example.aviatickets.model.network.ApiClient
import com.example.aviatickets.model.service.FakeService
import kotlinx.coroutines.launch

class OfferListFragment : Fragment() {

    private var _binding: FragmentOfferListBinding? = null
    private val binding get() = _binding!!

    private val adapter: OfferListAdapter by lazy {
        OfferListAdapter()
    }
    companion object {
        fun newInstance() = OfferListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOfferListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupRecyclerView()
        loadOffers()
    }

    private fun setupUI() {
        with(binding) {
            sortRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.sort_by_price -> {
                        sortAndRefreshList(SortType.PRICE)
                    }
                    R.id.sort_by_duration -> {
                        sortAndRefreshList(SortType.DURATION)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.offerList.layoutManager = LinearLayoutManager(requireContext())
        binding.offerList.adapter = adapter
        adapter.submitList(FakeService.offerList)
    }

    private fun sortAndRefreshList(sortType: SortType) {
        val sortedList = when (sortType) {
            SortType.PRICE -> FakeService.offerList.sortedBy { it.price }
            SortType.DURATION -> FakeService.offerList.sortedBy { it.flight.duration }
        }
        adapter.submitList(sortedList)
    }
    private fun loadOffers() {
        lifecycleScope.launch {
            try {
                val apiService = ApiClient.apiService
                val offers = apiService.getOffers()
                adapter.submitList(offers)
            } catch (e: Exception) {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

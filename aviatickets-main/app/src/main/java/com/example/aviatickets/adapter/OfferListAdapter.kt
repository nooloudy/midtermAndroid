import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aviatickets.R
import com.example.aviatickets.databinding.ItemOfferBinding
import com.example.aviatickets.model.entity.Offer
import com.example.aviatickets.adapter.OfferDiffCallback

class OfferListAdapter : ListAdapter<Offer, OfferListAdapter.ViewHolder>(OfferDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOfferBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemOfferBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(offer: Offer) {
            val flight = offer.flight

            with(binding) {
                departureTime.text = flight.departureTimeInfo
                arrivalTime.text = flight.arrivalTimeInfo
                route.text = context.getString(
                    R.string.route_fmt,
                    flight.departureLocation?.code.orEmpty(),
                    flight.arrivalLocation?.code.orEmpty()
                )
                duration.text = context.getString(
                    R.string.time_fmt,
                    getTimeFormat(flight.duration).first.toString(),
                    getTimeFormat(flight.duration).second.toString()
                )
                direct.text = context.getString(R.string.direct)
                price.text = context.getString(R.string.price_fmt, offer.price.toString())
                val imageUrl = getAirlineImageUrl(flight.airline.name)

                Glide.with(context)
                    .load(imageUrl)
                    .into(airlineImage)
            }
        }

        private fun getTimeFormat(minutes: Int): Pair<Int, Int> = Pair(
            first = minutes / 60,
            second = minutes % 60
        )
        private fun getAirlineImageUrl(airlineName: String): String {
            return when (airlineName) {
                "Air Astana" -> "https://logos-world.net/wp-content/uploads/2023/01/Air-Astana-Logo.png"
                "FlyArystan" -> "https://airhex.com/images/airline-logos/alt/flyarystan.png"
                "SCAT Airlines" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/7/75/SCAT_Air_Company_Logo.svg/2560px-SCAT_Air_Company_Logo.svg.png"
                "QazaqAir" -> "https://airhex.com/images/airline-logos/alt/qazaq-air.png"
                else -> ""
            }
        }
    }

}

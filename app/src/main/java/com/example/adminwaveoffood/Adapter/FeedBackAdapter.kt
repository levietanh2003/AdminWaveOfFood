package com.example.adminwaveoffood.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwaveoffood.databinding.FeedbackItemBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FeedBackAdapter(
    private val feedBackCustomerName: ArrayList<String>,
    private val feedBackContent: ArrayList<String>,
    private val timeTamp: ArrayList<String>
) : RecyclerView.Adapter<FeedBackAdapter.FeedBackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedBackViewHolder {
        val binding =
            FeedbackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedBackViewHolder(binding)
    }

    override fun getItemCount(): Int = feedBackCustomerName.size

    override fun onBindViewHolder(holder: FeedBackViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class FeedBackViewHolder(private val binding: FeedbackItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.apply {
                nameCustomerFeedback.text = feedBackCustomerName[position]
                contentFeedback.text = feedBackContent[position]
                timeStampFeedBack.text = timeTamp[position].toString()
            }
        }
    }

    fun String.toVietnameseDateTime(format: String = "dd/MM/yyyy HH:mm"): String {
        val parser = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH)
        val formatter = SimpleDateFormat(format, Locale("vi", "VN"))
        val date = parser.parse(this) ?: return ""
        return formatter.format(date)
    }
}

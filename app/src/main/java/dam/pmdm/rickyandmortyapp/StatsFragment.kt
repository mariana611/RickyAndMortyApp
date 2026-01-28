package dam.pmdm.rickyandmortyapp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class StatsFragment : Fragment(R.layout.fragment_stats) {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val tvCount = view.findViewById<TextView>(R.id.tvStatsCount)
        val tvPercent = view.findViewById<TextView>(R.id.tvStatsPercent)
        val progressBar = view.findViewById<ProgressBar>(R.id.pbStats)

        val userId = auth.currentUser?.uid ?: return
        val totalEpisodes = 51 // Total aproximado de la serie

        db.collection("users").document(userId).collection("watchedEpisodes")
            .get()
            .addOnSuccessListener { result ->
                val watchedCount = result.size()
                val percentage = if (totalEpisodes > 0) (watchedCount * 100) / totalEpisodes else 0

                tvCount.text = "Has visto $watchedCount de $totalEpisodes episodios"
                tvPercent.text = "$percentage%"
                progressBar.max = totalEpisodes
                progressBar.progress = watchedCount
            }
    }
}
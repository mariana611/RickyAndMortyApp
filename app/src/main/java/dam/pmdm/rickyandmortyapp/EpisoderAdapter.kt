package dam.pmdm.rickyandmortyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EpisodeAdapter(
    private var allEpisodes: List<Episode>,  // Lista completa original
    private var watchedIds: Set<Int>,        // IDs de vistos
    private val onClick: (Episode) -> Unit   // Acción al pulsar (Apartado A)
) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    // Lista que se muestra actualmente (filtrada o no)
    private var displayList: List<Episode> = allEpisodes

    class EpisodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvName)
        val code: TextView = view.findViewById(R.id.tvCode)
        val date: TextView = view.findViewById(R.id.tvDate)
        val indicator: View = view.findViewById(R.id.viewWatchedIndicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_episode, parent, false)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = displayList[position]
        holder.name.text = episode.name
        holder.code.text = episode.episode
        holder.date.text = episode.air_date

        // Lógica visual de "Visto" (Apartado A)
        val isWatched = watchedIds.contains(episode.id)
        holder.indicator.visibility = if (isWatched) View.VISIBLE else View.GONE
        holder.itemView.alpha = if (isWatched) 0.6f else 1.0f

        holder.itemView.setOnClickListener { onClick(episode) }
    }

    override fun getItemCount() = displayList.size

    // Función para actualizar la lista desde la API
    fun updateEpisodes(newList: List<Episode>) {
        this.allEpisodes = newList
        this.displayList = newList
        notifyDataSetChanged()
    }

    // Función para actualizar los marcados como vistos
    fun updateWatchedList(newIds: Set<Int>) {
        this.watchedIds = newIds
        notifyDataSetChanged()
    }

    // Función de filtrado (Apartado E: Filtro en Toolbar/Tabs)
    fun filter(onlyWatched: Boolean) {
        displayList = if (onlyWatched) {
            allEpisodes.filter { watchedIds.contains(it.id) }
        } else {
            allEpisodes
        }
        notifyDataSetChanged()
    }
}
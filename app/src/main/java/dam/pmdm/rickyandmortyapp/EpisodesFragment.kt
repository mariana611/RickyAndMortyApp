package dam.pmdm.rickyandmortyapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class EpisodesFragment : Fragment(R.layout.fragment_episodes) {

    private lateinit var adapter: EpisodeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvEpisodes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Inicializamos el adaptador con una lista vacía
        adapter = EpisodeAdapter(emptyList(), emptySet()) { selectedEpisode ->
            val bundle = Bundle().apply {
                putSerializable("episode", selectedEpisode)
            }
            findNavController().navigate(R.id.episodeDetailFragment, bundle)
        }

        recyclerView.adapter = adapter

        // ¡LLAMADA VITAL PARA CARGAR DATOS!
        loadData()
    }

    private fun loadData() {
        // Usamos lifecycleScope para que la petición se detenga si cerramos la app
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getEpisodes()
                if (response.isSuccessful) {
                    val episodes = response.body()?.results ?: emptyList()
                    // Actualizamos el adaptador con los datos de la API
                    adapter.updateEpisodes(episodes)
                } else {
                    Toast.makeText(requireContext(), "Error de API: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Fallo de conexión: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
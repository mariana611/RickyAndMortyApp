package dam.pmdm.rickyandmortyapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class EpisodeDetailFragment : Fragment(R.layout.fragment_episode_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recuperar el objeto con el cast seguro
        val episode = arguments?.getSerializable("episode") as? Episode

        if (episode != null) {
            // Asignar los datos a las vistas (asegúrate de que estos IDs existen en tu XML de detalle)
            view.findViewById<TextView>(R.id.tvDetailName)?.text = episode.name
            view.findViewById<TextView>(R.id.tvDetailCode)?.text = episode.episode
            view.findViewById<TextView>(R.id.tvDetailDate)?.text = episode.air_date
        } else {
            Toast.makeText(requireContext(), "Error: No se pudieron cargar los detalles", Toast.LENGTH_SHORT).show()
        }
    }
}
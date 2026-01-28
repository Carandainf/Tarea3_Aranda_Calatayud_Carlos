package dam.pmdm.tarea3_aranda_calatayud_carlos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import dam.pmdm.tarea3_aranda_calatayud_carlos.R
import dam.pmdm.tarea3_aranda_calatayud_carlos.databinding.ItemCharacterBinding
import dam.pmdm.tarea3_aranda_calatayud_carlos.models.Character

class CharacterAdapter (private val characters: List<Character>) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.binding.characterName.text = character.name
        holder.binding.characterImage.load(character.image){
            crossfade(true) // efecto de aparecer suavizado
            transformations(CircleCropTransformation()) // para que se vea redonda
            placeholder(R.mipmap.ic_launcher) // placeholder mientras descarga
            error(R.mipmap.ic_launcher) // placeholder en caso de error
        }
    }

    override fun getItemCount(): Int {
        return characters.size
    }

}
package com.example.rickywiki.adapter;

import static android.view.LayoutInflater.from;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rickywiki.R;
import com.example.rickywiki.model.Personaje;

import java.util.ArrayList;

//Esta clase unirá nuestro recyclerView con nuestro modelo personalizado de registro, ademas de con los datos que se le tienen que pasar por parametro
//Vamos a implementar onClick porque queremos tener un listener en cada vista que forme el recyclerView
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> implements View.OnClickListener{

    private ArrayList<Personaje> personajes;
    private View.OnClickListener listener;

    private Context contexto;

    public RecyclerAdapter(ArrayList<Personaje> personajes){
        this.personajes = personajes;
    }

    @NonNull
    @Override
    public RecyclerAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        contexto = parent.getContext();
        //Generamos nuestra vista a partir de nuestro modelo
        View view = from(parent.getContext()).inflate(R.layout.custom_character_item_list,parent, false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);
        view.setOnClickListener(this);
        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.RecyclerHolder holder, int position) {
        //Ponemos los valores recibidos en sus sitios
        Personaje p = personajes.get(position);
        holder.name.setText(p.getName());
        holder.gender.setText(p.getGender());
        holder.species.setText(p.getSpecies());
        String status = p.getStatus();
        //Dependiendo del estado se mostrara este TextView de un color diferente
        switch (status){
            case "Alive":
                holder.status.setTextColor(Color.GREEN);
                break;
            case "Dead":
                holder.status.setTextColor(Color.RED);
                break;
            default:
                holder.status.setTextColor(Color.BLACK);
                break;
        }

        holder.status.setText(status);
        //Cargamos la imagen con glide
        Glide.with(contexto).load(p.getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return personajes.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
         if (listener != null){
             listener.onClick(view);
         }
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView gender;
        TextView species;
        TextView status;

        //Unimos nuestras variables con las vistas reales de nuestro modelo
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.characterImage);
            name = (TextView) itemView.findViewById(R.id.txtName);
            gender = (TextView) itemView.findViewById(R.id.txtGender);
            species = (TextView) itemView.findViewById(R.id.txtSpecie);
            status = (TextView) itemView.findViewById(R.id.txtStatus);
        }
    }


}


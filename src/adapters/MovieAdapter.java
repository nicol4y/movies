package com.example.movies_nicolay_nacaro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import com.example.movies_nicolay_nacaro.MainActivity;
import com.example.movies_nicolay_nacaro.databinding.CustomRowsBinding;
import com.example.movies_nicolay_nacaro.db.Ticket;
import com.example.movies_nicolay_nacaro.db.tDatabase;
import com.example.movies_nicolay_nacaro.fragments.NowTickets;
import com.example.movies_nicolay_nacaro.models.Movies;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ItemViewHolder> {
    private  Context context = null;
    private ArrayList<Movies> movieList = null;
    CustomRowsBinding binding;

    public MovieAdapter(Context context, ArrayList<Movies> movies){
        this.movieList = movies;
        this.context = context;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(CustomRowsBinding.inflate(LayoutInflater.from(context), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Movies currentMov = movieList.get(position);
        holder.bind(context, currentMov);
    }





    @Override
    public int getItemCount() {
        return movieList.size();
    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        CustomRowsBinding itemBinding;
        public tDatabase db;
        public ItemViewHolder(@NonNull CustomRowsBinding binding){
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        public void bind(Context context, Movies current){
            db = tDatabase.getDatabase(context);
            itemBinding.title.setText(current.getName());
            itemBinding.desc.setText(current.getDesc());
            itemBinding.rating.setText(current.getRating()+"%");
itemBinding.release.setText(current.getReleased());
            itemBinding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CharSequence text = "Ticket purchased";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    int a = 0;
                    String tempT = (String) itemBinding.title.getText();
                    for(Movies m: MainActivity.realMovies){
                        if(tempT.compareTo(m.getName())==0){
                            a= m.getID();
                        }
                    }
                    Ticket temp = db.TicketDAO().getTicketById(a);
                    if(temp==null){
                        Ticket newTick= new Ticket(tempT,a,1);
                        db.TicketDAO().insertEmployee(newTick);
                    }
                    else{
                        temp.addTick();
                        db.TicketDAO().updateTicket(temp);
                    }


                }
            });
            Glide.with(context).load(current.getUrl()).into(itemBinding.icon);
        }
    }
}

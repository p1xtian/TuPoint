package t.local.tupoint.adapters;

import android.graphics.Bitmap;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import t.local.tupoint.R;
import t.local.tupoint.helpers.HelperBase64;
import t.local.tupoint.models.Reserve;
import t.local.tupoint.models.Restaurant;

public class RecyclerAdapterReservesUser
        extends RecyclerView.Adapter<RecyclerAdapterReservesUser.ViewHolderLogs> {


    ArrayList<Reserve> reserves ;
    ArrayList<Restaurant> restaurants;

    public RecyclerAdapterReservesUser(ArrayList<Reserve> reserves, ArrayList<Restaurant> restaurants)
    {
        this.reserves = reserves;
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolderLogs onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_reserves_users,null,false);
        return new ViewHolderLogs(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLogs viewHolderLogs, int i) {
        try{

            Restaurant rest = FoundRest(reserves.get(i).getRuc());

            HelperBase64 helperBase64 = new HelperBase64();
            viewHolderLogs.Logo.setImageBitmap(helperBase64.Decode(rest.getLogo()));
            viewHolderLogs.Empresa.setText(rest.getRazonsocial());
            viewHolderLogs.Fecha.setText("Fecha De Reserva :" + reserves.get(i).getFechadereserva());
            viewHolderLogs.Personas.setText("Personas :" + reserves.get(i).getPersonas());

        }
        catch (Exception ex)
        {
            Log.d("=TuPoint=>",ex.toString());
        }
        finally {

        }

    }

    @Override
    public int getItemCount() {
        return reserves.size();
    }

    public class ViewHolderLogs extends RecyclerView.ViewHolder {

        TextView Empresa,Fecha,Personas;
        ImageView Logo;


        public ViewHolderLogs(@NonNull View itemView) {

            super(itemView);
            Logo = (ImageView)  itemView.findViewById(R.id.rPhoto);
            Empresa = (TextView) itemView.findViewById(R.id.rEmpresa);
            Fecha = (TextView) itemView.findViewById(R.id.rFecha);
            Personas = (TextView) itemView.findViewById(R.id.rPersonas);

        }

    }


    private Restaurant FoundRest(String ruc) {


        for (int i = 0; i < restaurants.size(); i++) {

            if (
                    restaurants.get(i).getRuc().equals(ruc))
            {
                return restaurants.get(i);


            }
        }
        return null;
    }

}


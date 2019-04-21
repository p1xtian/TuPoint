package t.local.tupoint.adapters;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import t.local.tupoint.models.Admin;
import t.local.tupoint.models.Restaurant;

public class RecyclerAdapterRestaurants
        extends RecyclerView.Adapter<RecyclerAdapterRestaurants.ViewHolderLogs> {

    ArrayList<Restaurant> restaurants ;

    public RecyclerAdapterRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolderLogs onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_restaurants,null,false);
        return new ViewHolderLogs(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLogs viewHolderLogs, int i) {
        try{

            HelperBase64 helperBase64 = new HelperBase64();
            Bitmap bLogo = helperBase64.Decode(restaurants.get(i).logo);
            viewHolderLogs.Email.setText(restaurants.get(i).getCorreo() );
            viewHolderLogs.RUC.setText(restaurants.get(i).getRuc());
            viewHolderLogs.Logo.setImageBitmap(bLogo);

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
        return restaurants.size();
    }

    public class ViewHolderLogs extends RecyclerView.ViewHolder {

        TextView Email,RUC;
        ImageView Logo;

        public ViewHolderLogs(@NonNull View itemView) {

            super(itemView);
            Email = (TextView) itemView.findViewById(R.id.rEmail);
            RUC = (TextView) itemView.findViewById(R.id.rRuc);
            Logo = (ImageView) itemView.findViewById(R.id.rPhoto);

        }

    }
}


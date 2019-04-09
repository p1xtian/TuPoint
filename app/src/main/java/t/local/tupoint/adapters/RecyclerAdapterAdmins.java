package t.local.tupoint.adapters;

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
import t.local.tupoint.models.Admin;

public class RecyclerAdapterAdmins
        extends RecyclerView.Adapter<RecyclerAdapterAdmins.ViewHolderLogs> {

    ArrayList<Admin> admins ;

    public RecyclerAdapterAdmins(ArrayList<Admin> admins) {
        this.admins = admins;
    }

    @NonNull
    @Override
    public ViewHolderLogs onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_admins,null,false);
        return new ViewHolderLogs(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLogs viewHolderLogs, int i) {
        try{
            viewHolderLogs.Email.setText(admins.get(i).getEmail() );
            viewHolderLogs.RUC.setText(admins.get(i).getRuc());

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
        return admins.size();
    }

    public class ViewHolderLogs extends RecyclerView.ViewHolder {

        TextView Email,RUC;

        public ViewHolderLogs(@NonNull View itemView) {

            super(itemView);
            Email = (TextView) itemView.findViewById(R.id.rEmail);
            RUC = (TextView) itemView.findViewById(R.id.rRuc);
        }

    }
}


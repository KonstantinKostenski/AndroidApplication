package com.example.mobilemoviedatabase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobilemoviedatabase.models.BusinessEntity;

import java.util.ArrayList;
import java.util.HashMap;

public class StableArrayAdapterBusinessEntity extends ArrayAdapter<BusinessEntity> {
    private MovieService movieService;
    HashMap<BusinessEntity, Integer> mIdMap = new HashMap<>();
    ArrayList<BusinessEntity> data;
    Context context;

    public StableArrayAdapterBusinessEntity(Context context, int textViewResourceId, ArrayList<BusinessEntity> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        movieService = new MovieService(context);
        data = objects;
        for (int i = 0; i < objects.size(); ++i) {
            BusinessEntity currentObject = objects.get(i);
            mIdMap.put(currentObject, currentObject.getId());
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        TextView textView = rowView.findViewById(R.id.textViewName);
        textView.setText(getItem(position).getName());
        Button btn = rowView.findViewById(R.id.button_delete_id);
        btn.setTag(position);
        btn.setOnClickListener(v -> {
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.delete_prompt, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setView(promptsView);

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            (dialog, id) -> {
                                int dataId = data.get(position).getId();
                                data.remove(position);
                                movieService.DeleteBusinessEntity(dataId);
                                notifyDataSetChanged();
                            })
                    .setNegativeButton("Cancel",
                            (dialog, id) -> dialog.cancel());

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();
        });

        Button btnView = rowView.findViewById(R.id.button_view_id);
        btnView.setTag(position);
        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(parent.getContext(), DisplayBusinessEntityActivity.class);
            intent.putExtra("business_activity_id", Integer.toString(data.get(position).getId()));
            context.startActivity(intent);
        });

        return rowView;
    }

    @Override
    public long getItemId(int position) {
        BusinessEntity item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

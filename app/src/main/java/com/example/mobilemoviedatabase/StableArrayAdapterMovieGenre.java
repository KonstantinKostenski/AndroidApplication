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

import androidx.annotation.NonNull;

import com.example.mobilemoviedatabase.models.Genre;

import java.util.ArrayList;
import java.util.HashMap;

public class StableArrayAdapterMovieGenre  extends ArrayAdapter<Genre> {
    HashMap<Genre, Integer> mIdMap = new HashMap<>();
    ArrayList<Genre> data;
    private MovieService movieService;
    Context context;

    public StableArrayAdapterMovieGenre(@NonNull Context context, int resource, @NonNull ArrayList<Genre> objects) {
        super(context, resource, objects);
        this.context = context;
        data = objects;
        movieService = new MovieService(context);
        for (int i = 0; i < objects.size(); ++i) {
            Genre currentObject = objects.get(i);
            mIdMap.put(currentObject, currentObject.getId());
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);

        TextView textViewName = rowView.findViewById(R.id.textViewName);
        textViewName.setText(getItem(position).getName());

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
                                movieService.DeleteGenre(dataId);
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
            Intent intent = new Intent(parent.getContext(), DisplayGenreActivity.class);
            intent.putExtra("genre_id", Integer.toString(data.get(position).getId()));
            context.startActivity(intent);
        });

        return rowView;
    }

    @Override
    public long getItemId(int position) {
        Genre item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

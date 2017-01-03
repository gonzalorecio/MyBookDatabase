package com.example.pr_idi.mydatabaseexample;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Gonzalo on 01/01/2017.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    static private List<Book> booksDataset;
    static private BookData bookData;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder  {
        // each data item is just a string in this case
        public View mView;
        public TextView titleView;
        public TextView authorView;
        public TextView categoryView;
        public BooksAdapter BA;

        public ViewHolder(View v, BooksAdapter _BA) {
            super(v);
            mView = v;
            BA = _BA;
            titleView = (TextView) v.findViewById(R.id.title);
            authorView = (TextView) v.findViewById(R.id.author);
            categoryView= (TextView) v.findViewById(R.id.category);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText("Anakin",Toast.LENGTH_SHORT);
                    Log.d("AY","short");
                }
            });
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Log.d("AY","long");
                    // 1. Instantiate an AlertDialog.Builder with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage(titleView.getText()+" is going to be deleted.")
                            .setTitle("Delete Book");

                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            int position = getAdapterPosition();
                            BA.removeAt(position);

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            dialog.cancel();
                        }
                    });
                    // 3. Get the AlertDialog from create()
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return true;
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BooksAdapter(List<Book> dataset, BookData bookData) {
        booksDataset = dataset;
        this.bookData = bookData;

    }

    public void addBook(Book book){
        booksDataset.add(book);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_scrolling, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //... Al bind ficar les paraules al text view (No aqui)
        ViewHolder vh = new ViewHolder(v,this);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BooksAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.titleView.setText(booksDataset.get(position).getTitle());
        holder.authorView.setText(booksDataset.get(position).getAuthor());
        holder.categoryView.setText(booksDataset.get(position).getCategory().toUpperCase());
        //System.out.println(booksDataset.get(position).getCategory());
    }

    public void removeAt(int position) {
        Book removed_book = booksDataset.get(position);

        booksDataset.remove(position);
        bookData.deleteBook(removed_book);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, booksDataset.size());
    }

    @Override
    public int getItemCount() {
        return booksDataset.size();
    }
}

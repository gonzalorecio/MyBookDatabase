package com.example.pr_idi.mydatabaseexample;


import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private BookData bookData;
    private RecyclerView mRecyclerView;
    private BooksAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //TEEEEEEEEEEEEEEEEEedEEST
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerList);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        bookData = new BookData(this);
        bookData.open();
        List<Book> values = bookData.getAllBooks();

        // specify an adapter (see also next example)
        mAdapter = new BooksAdapter(values,bookData);
        mRecyclerView.setAdapter(mAdapter);


       /* bookData = new BookData(this);
        bookData.open();

        List<Book> values = bookData.getAllBooks();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Book> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Basic method to add pseudo-random list of books so that
    // you have an example of insertion and deletion

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
       // ArrayAdapter<Book> adapter = (ArrayAdapter<Book>) getListAdapter();
        Book book;
        //Toast.makeText(getApplicationContext(),"oiii",Toast.LENGTH_LONG).show();
        Log.d("AY","View clicada: "+view.getId());
        switch (view.getId()) {
            /*case R.id.add:
                String[] newBook = new String[] { "Miguel Strogoff", "Jules Verne", "Ulysses", "James Joyce", "Don Quijote", "Miguel de Cervantes", "Metamorphosis", "Kafka" };
                int nextInt = new Random().nextInt(4);
                // save the new book to the database
                book = bookData.createBook(newBook[nextInt*2], newBook[nextInt*2 + 1]);

                // After I get the book data, I add it to the adapter
                adapter.add(book);
                break;*/
            case R.id.floatingActionButton:
                //String[] newBook2 = new String[] { "Miguel Strogoff", "Jules Verne", "Ulysses", "James Joyce", "Don Quijote", "Miguel de Cervantes", "Metamorphosis", "Kafka" };
                //int nextInt2 = new Random().nextInt(4);
                // save the new book to the database
                //book = bookData.createBook(newBook2[nextInt2*2], newBook2[nextInt2*2 + 1]);
                //Toast.makeText(getApplicationContext(),"Ayyy",Toast.LENGTH_SHORT).show();
                // After I get the book data, I add it to the adapter

                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);

                //mAdapter.addBook(book);
                break;
            /*case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    book = (Book) getListAdapter().getItem(0);
                    bookData.deleteBook(book);
                    adapter.remove(book);
                }
                break;*/
        }
        System.out.println("Fora");
        mAdapter.notifyDataSetChanged();
    }

    // Life cycle methods. Check whether it is necessary to reimplement them

    @Override
    protected void onResume() {
        bookData.open();
        Log.d("AY","Resume");
        List<Book> values = bookData.getAllBooks();
        System.out.println(values);
        mAdapter = new BooksAdapter(values,bookData);
        super.onResume();
    }

    // Life cycle methods. Check whether it is necessary to reimplement them

    @Override
    protected void onPause() {
        bookData.close();
        Log.d("AY","Pause");
        super.onPause();
    }

    /*@Override
    protected void onRestart() {
        bookData.open();
        List<Book> values = bookData.getAllBooks();
        System.out.println(values);
        mAdapter = new BooksAdapter(values,bookData);
        super.onRestart();
    }

    @Override
    protected void onStart() {
        bookData.open();
        List<Book> values = bookData.getAllBooks();
        System.out.println(values);
        mAdapter = new BooksAdapter(values,bookData);
        super.onStart();
    }*/
}
package com.example.pr_idi.mydatabaseexample;


import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private BookData bookData;
    private RecyclerView mRecyclerView;
    private BooksAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private SearchView searchView;

    //TEEEEEEEEEEEEEEEEEedEEST
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Book List");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0)
            {
                public void onDrawerClosed(View view)
                {
                    supportInvalidateOptionsMenu();
                    //drawerOpened = false;
                }

                public void onDrawerOpened(View drawerView)
                {
                    supportInvalidateOptionsMenu();
                    //drawerOpened = true;
                }
            };

            mDrawerToggle.setDrawerIndicatorEnabled(true);
            drawerLayout.setDrawerListener(mDrawerToggle);
            mDrawerToggle.syncState();
        }

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

        //Listeners
        MenuItem help = menu.findItem(R.id.help);
        help.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this,HelpActivity.class);
                startActivity(intent);
                return false;
            }
        });
        MenuItem about = menu.findItem(R.id.about);
        about.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String message = "Application developed by Gonzalo Recio and Òscar Pons. \n" +
                                 "    Universitat Politècnica de Catalunya, 2017.";
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                return true;
            }
        });

        final MenuItem myActionMenuItem = menu.findItem( R.id.search);
        searchView = (SearchView) myActionMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("Submited " + query);

                List<Book> books = bookData.findBooksByTitle(query);
                mAdapter.setBooksDataset(books);

                if( ! searchView.isIconified()) {
                    //searchView.setIconified(true);
                }
                //myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println(s);
                if(s.length()>0) {
                    List<Book> books = bookData.findBooksByTitle(s);
                    mAdapter.setBooksDataset(books);
                }
                else{
                    List<Book> books = bookData.getAllBooks();
                    mAdapter.setBooksDataset(books);
                }
                return false;
            }

        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                List<Book> books = bookData.getAllBooks();
                mAdapter.setBooksDataset(books);
                System.out.println("onClose Search");
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(Gravity.LEFT);

        }
        return super.onOptionsItemSelected(item);
    }

    // Basic method to add pseudo-random list of books so that
    // you have an example of insertion and deletion

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        Book book;
        //Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_LONG).show();
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
                // After I get the book data, I add it to the adapter
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);

                //mAdapter.addBook(book);
                break;

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
        mAdapter.setBooksDataset(values);
        super.onResume();
    }

    // Life cycle methods. Check whether it is necessary to reimplement them

    @Override
    protected void onPause() {
        bookData.close();
        Log.d("AY","Pause");
        super.onPause();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
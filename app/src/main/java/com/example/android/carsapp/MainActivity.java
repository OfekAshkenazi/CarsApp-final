package com.example.android.carsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

import Adapters.CarsAdapter;
import Entities.Car;
import SQLite.SQLiteDAL;

public class MainActivity extends AppCompatActivity {
    private static final int SEARCH_BY_TAG =0 ;
    Toolbar toolbar;
    EditText searchBarTB;
    ArrayList<Car> carsList=new ArrayList<>();
    RecyclerView carsRV;
    CarsAdapter adapter;
    int currentSearchField=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mockDatabaseList();  /*----Run only in the first time !----*/
        setCarsList();
        setToolbar();
    }

    private void mockDatabaseList() {
        SQLiteDAL dal=new SQLiteDAL(this);
        dal.insertCar(new Car("Audi TT",2017,"Blue"));
        dal.insertCar(new Car("Audi TT",2016,"White"));
        dal.insertCar(new Car("Audi TT",2015,"Red"));
        dal.insertCar(new Car("Bugati Veron",2017,"Red"));
        dal.insertCar(new Car("Bugati Veron",2016,"White"));
        dal.insertCar(new Car("Bugati Veron",2015,"Blue"));
        dal.insertCar(new Car("Audi A3",2017,"Red"));
        dal.insertCar(new Car("Audi A3",2016,"Blue"));
        dal.insertCar(new Car("Audi A3",2015,"White"));
    }

    private void setCarsList() {
        carsRV=findViewById(R.id.carsRV);
        SQLiteDAL dal=new SQLiteDAL(this);
        carsList=dal.getAllCars(null);
        adapter=new CarsAdapter(carsList);
        carsRV.setAdapter(adapter);
        carsRV.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setToolbar() {
        toolbar=findViewById(R.id.layoutToolbar);
        toolbar.inflateMenu(R.menu.sort_menu_layout);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.modelItem_menu: currentSearchField=0;
                    break;
                    case R.id.yearItem_menu: currentSearchField=1;
                        break;
                    case R.id.colorItem_menu: currentSearchField=2;
                        break;
                }
                return true;
            }
        });
        searchBarTB=toolbar.findViewById(R.id.searchBarET_tb);
        searchBarTB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                SQLiteDAL dal=new SQLiteDAL(MainActivity.this);
                Object[] searchValues={currentSearchField,text.toString()};
                adapter.setCarsList(dal.getAllCars(searchValues));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}

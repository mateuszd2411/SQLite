package com.mat.sqlitelist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    /// references to buttons and other controls on the layout
    Button btn_add, btn_viewAll;
    EditText et_name, et_age;
    Switch sw_activiteCustomer;
    ListView lv_customersList;

    ArrayAdapter customerArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        sw_activiteCustomer = findViewById(R.id.sw_active);
        lv_customersList = findViewById(R.id.lv_customerList);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        // on app launch
        ShowCustomersOnListView(dataBaseHelper);

        // button listeners for the add and view all buttons
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomerModel customerModel;

                try {
                    customerModel = new CustomerModel(-1,et_name.getText().toString(),Integer.parseInt(et_age.getText().toString()),sw_activiteCustomer.isChecked());
                    Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Error creating customer", Toast.LENGTH_SHORT).show();
                    customerModel = new CustomerModel(-1,"error",0,false);

                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                boolean success = dataBaseHelper.addOne(customerModel);

                Toast.makeText(MainActivity.this, "Success = " +success, Toast.LENGTH_SHORT).show();

                //for update list when click add button
                ShowCustomersOnListView(dataBaseHelper);

            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                //on viewAll click
                ShowCustomersOnListView(dataBaseHelper);


//                Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        lv_customersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel clickedCustomer = (CustomerModel) parent.getItemAtPosition(position);
                dataBaseHelper.deleteOne(clickedCustomer);
                //update
                ShowCustomersOnListView(dataBaseHelper);
                Toast.makeText(MainActivity.this, "Deleted " + clickedCustomer.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ShowCustomersOnListView(DataBaseHelper dataBaseHelper2) {
        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getEveryone());     // simple_list_item_1  default android list
        lv_customersList.setAdapter(customerArrayAdapter);
    }
}

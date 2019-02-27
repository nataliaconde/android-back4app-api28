package com.back4app.back4appandroid9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ArrayList<String> dataList = new ArrayList<String>();
    public String[] myArray = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button retrieve_data = findViewById(R.id.btnRetrieveData);

        retrieve_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myArray = new String[]{};
                final ListView listView = (ListView) findViewById(R.id.listviewA);

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Cars");
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> results, ParseException e) {
                        if (e == null) {
                            for(int i = 0; i < results.size(); i++) {
                                String carName = results.get(i).getString("name");
                                dataList.add(carName.toString());
                            }
                        } else {
                            // results have all the Posts the current user liked.
                            final Toast toast = Toast.makeText(
                                    MainActivity.this,
                                    String.valueOf("Error =>" + e.getMessage()),
                                    Toast.LENGTH_LONG
                            );
                            toast.show();
                        }
                    }
                });

                myArray = dataList.toArray(new String[dataList.size()]);
                ArrayAdapter<String> adapterList
                        = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_single_choice, myArray);

                listView.setAdapter(adapterList);
            }
        });

    }
}

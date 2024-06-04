package com.example.shopping_list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Choreographer;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shopping_list.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'shopping_list' library on application startup.
    static {
        System.loadLibrary("shopping_list");
    }

    private ActivityMainBinding binding;

    //UI STUFF
    EditText addItemField;
    FloatingActionButton addItemButton;
    ListView displayList;
    ArrayAdapter adapter;
    List<String> shoppingList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());


        //set up basic list functionality
        this.addItemField = findViewById(R.id.addItemField);
        this.addItemButton = findViewById(R.id.addItemButton);
        this.displayList = findViewById(R.id.list);

        // Create an adapter to link the list with the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shoppingList);
        displayList.setAdapter(adapter);


        //create listener
        addItemButton.setOnClickListener(v -> {
            String newItem = addItemField.getText().toString();
            if(newItem.isEmpty())
                return;

            shoppingList.add(newItem); // Add item to the list
            adapter.notifyDataSetChanged(); // Update the ListView
            addItemField.setText(""); // Clear the EditText field
        });



        //create runtime thing
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                update();
                Choreographer.getInstance().postFrameCallback(this);
            }
        });

    }



    void update()
    {



    }

    /**
     * A native method that is implemented by the 'shopping_list' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
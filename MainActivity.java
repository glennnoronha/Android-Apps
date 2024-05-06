package com.example.todolist;
/*
   App name: To Do List App
   @author: Glenn Noronha
   minSDK version = 21
   targetSDK version = 34
   @version: 04/15/2024
 */
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button addButton;
    private Button showAllButton;
    private Button deleteButton;
    private ListView listView;
    private ArrayList<String> itemList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        addButton = findViewById(R.id.addButton);
        showAllButton = findViewById(R.id.showAllButton);
        deleteButton = findViewById(R.id.deleteButton);
        listView = findViewById(R.id.listView);

        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, itemList);
        listView.setAdapter(adapter);

        // Enable multiple choice mode
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = editText.getText().toString().trim();
                if (!item.isEmpty()) {
                    itemList.add(item);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Item added: " + item, Toast.LENGTH_SHORT).show();
                    editText.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Please enter an item", Toast.LENGTH_SHORT).show();
                }
            }
        });

        showAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.VISIBLE);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedItems();
            }
        });
    }

    // Method to delete selected items
    private void deleteSelectedItems() {
        SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
        for (int i = checkedItems.size() - 1; i >= 0; i--) {
            if (checkedItems.valueAt(i)) {
                int position = checkedItems.keyAt(i);
                String item = itemList.get(position);
                itemList.remove(position);
                Toast.makeText(MainActivity.this, "Item deleted: " + item, Toast.LENGTH_SHORT).show();
            }
        }
        adapter.notifyDataSetChanged();
        listView.clearChoices();
        listView.setVisibility(View.GONE);
    }
}

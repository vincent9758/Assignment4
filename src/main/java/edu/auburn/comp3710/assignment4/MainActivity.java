package edu.auburn.comp3710.assignment4;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper Database;
    TextView balance;
    EditText date;
    EditText amount;
    EditText event;
    Button btnAdd;
    Button btnSub;
    TextView history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Database = new DatabaseHelper(this);

        balance = findViewById(R.id.balance);
        date =  findViewById(R.id.editDate);
        amount = findViewById(R.id.editAmount);
        event = findViewById(R.id.editEvent);
        btnAdd =  findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        history =  findViewById(R.id.historyContent);
        AddEvent();
        GetHistory();
    }

    public void AddEvent(){
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double Amount = Double.parseDouble(amount.getText().toString());
                        Database.Create(date.getText().toString(), Amount, event.getText().toString());
                        GetHistory();
                        MainActivity.this.date.setText("");
                        MainActivity.this.amount.setText("");
                        MainActivity.this.event.setText("");
                    }
                }
        );

        btnSub.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double Amount = -1 * Double.parseDouble(amount.getText().toString());
                        Database.Create(date.getText().toString(), Amount, event.getText().toString());
                        GetHistory();
                        MainActivity.this.date.setText("");
                        MainActivity.this.amount.setText("");
                        MainActivity.this.event.setText("");
                    }
                }
        );
    }

    public void GetHistory(){
        Cursor result = Database.ReadData();
        StringBuffer string = new StringBuffer();
        Double balance = 0.0;
        while(result.moveToNext()){
            double price = Double.parseDouble(result.getString(2));
            String priceString = result.getString(2);
            if (price < 0) {
                string.append("Spent $" + priceString.substring(1) +" on " + result.getString(1) +
                        " for " + result.getString(3) + "\n");
                balance += price;

            }
            else { string.append("Added $" + priceString + " on " + result.getString(1) +
                    " for " + result.getString(3) + "\n");
                balance += price;
            }

        }
        MainActivity.this.balance.setText("Current Balance: $" + Double.toString(balance));
        MainActivity.this.history.setText(string);
    }
}
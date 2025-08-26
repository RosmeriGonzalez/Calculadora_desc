package com.example.myapplication1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText num1, num2;
    private TextView result;
    private Button btnSum, btnRes, btnMul, btnDiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        result = findViewById(R.id.result);
        btnSum = findViewById(R.id.btnSum);
        btnRes = findViewById(R.id.btnRes);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);

        View.OnClickListener listener = v -> {
            double n1 = getValue(num1);
            double n2 = getValue(num2);
            double r;

            if (v.getId() == R.id.btnSum) {
                r = n1 + n2;
            } else if (v.getId() == R.id.btnRes) {
                r = n1 - n2;
            } else if (v.getId() == R.id.btnMul) {
                r = n1 * n2;
            } else { // btnDiv
                if (n2 == 0) {
                    result.setText("Error: división por cero");
                    return;
                }
                r = n1 / n2;
            }

            result.setText(String.format("%.2f", r));
        };

        btnSum.setOnClickListener(listener);
        btnRes.setOnClickListener(listener);
        btnMul.setOnClickListener(listener);
        btnDiv.setOnClickListener(listener);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private double getValue(EditText field) {
        String text = field.getText().toString();
        if (text.isEmpty()) {
            return 0;
        }
        return Double.parseDouble(text);
    }
}

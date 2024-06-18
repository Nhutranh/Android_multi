package com.phanngocnhutranh.multi_04;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phanngocnhutranh.multi_04.databinding.ActivityMainBinding;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ExecutorService executorService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        executorService = Executors.newSingleThreadExecutor();// khởi tạo exectors

        addEvent();
    }
    public void addEvent(){
    binding.btnDraw.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            binding.col1.removeAllViews();
            binding.col2.removeAllViews();
            binding.col3.removeAllViews();
            int numb = Integer.parseInt(binding.edtNumb.getText().toString());
            execFeatures(numb);
        }
    });
    }

    public void execFeatures( int numb){
        executorService.execute(() -> {
            runOnUiThread(() -> {
                Random random = new Random();
                for(int i=0; i< numb; i++){
                    int rand = random.nextInt(10);

                    TextView textView = new TextView(this);
                    textView.setText(String.valueOf(rand));
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(rand % 2 == 0 ? Color.BLUE : Color.GRAY);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
                    params.setMargins(10, 10, 10, 10);
                    textView.setLayoutParams(params);

                    if(i % 3 == 0) {
                        binding.col1.addView(textView);
                    } else if (i % 3 == 1) {
                        binding.col2.addView(textView);
                    }else {
                        binding.col3.addView(textView);
                    }
                }
            });
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (executorService != null){
            executorService.shutdownNow();
        }
    }
}
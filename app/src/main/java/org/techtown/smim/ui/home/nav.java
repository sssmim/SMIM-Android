package org.techtown.smim.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.github.mikephil.charting.charts.LineChart; 
import com.github.mikephil.charting.components.Description; 
import com.github.mikephil.charting.components.XAxis; 
import com.github.mikephil.charting.components.YAxis; 
import com.github.mikephil.charting.data.Entry; 
import com.github.mikephil.charting.data.LineData; 
import com.github.mikephil.charting.data.LineDataSet; 
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet; 
import com.github.mikephil.charting.utils.ColorTemplate; 
import com.github.mikephil.charting.charts.LineChart; 

public class nav extends AppCompatActivity {
    LineChart mpLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header);

        mpLineChart = findViewById(R.id.LineChart);
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "Data Set 1");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);

        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();
    }

    private ArrayList<Enrty> dataValues1(){
        ArrayList<Enrty> dataVals = new ArrayList<Enrty>();

        dataVals.add(new Enrty(0, 20));
        dataVals.add(new Enrty(1, 30));
        dataVals.add(new Enrty(2, 40));
        dataVals.add(new Enrty(3, 50));
        dataVals.add(new Enrty(4, 30));

        return dataVals;
    }
}


package com.airluigi.powercalc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "PowerCalc.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    final static double ft_to_cm = 30.48;
    final static double cm_to_m = 100;
    final static double m_to_km = 1000;
    final static double km_to_ft = cm_to_m * m_to_km / ft_to_cm;
    final static double ft_to_km = 1 / km_to_ft;

    final static double lapse_rate_c_over_km = -6.5;
    final static double isa_temp_0_msl_c = 15;

    final static double c_inHg_reduction = 15;

    final static double takeoff_inHg = 41;

    public void compute(View view) {
        EditText intercoolingInput = (EditText) findViewById(R.id.editTextIntercoolingInput);
        final double intercooling_c = Double.parseDouble(intercoolingInput.getText().toString());
        Log.d(TAG, "intercooling_c: " + intercooling_c);

        EditText oatInput = (EditText) findViewById(R.id.editTextOATInput);
        final double oat_c = Double.parseDouble(oatInput.getText().toString());
        Log.d(TAG, "oat_c: " + oat_c);

        EditText mslInput = (EditText) findViewById(R.id.editTextMSLInput);
        final double msl_ft = Double.parseDouble(mslInput.getText().toString());
        Log.d(TAG, "msl_ft: " + msl_ft);

        final double isa_temp_c = isa_temp_0_msl_c + lapse_rate_c_over_km * msl_ft * ft_to_km;
        Log.d(TAG, "isa_temp_c: " + isa_temp_c);
        final double isa_temp_delta_c = oat_c - isa_temp_c;
        Log.d(TAG, "isa_temp_delta_c: " + isa_temp_delta_c);

        final double mp_delta_inHg = (isa_temp_delta_c + intercooling_c) / c_inHg_reduction;
        Log.d(TAG, "mp_delta_inHg: " + mp_delta_inHg);
        final double mp_inHg = takeoff_inHg - mp_delta_inHg;
        Log.d(TAG, "mp_inHg: " + mp_inHg);

        EditText result = (EditText) findViewById(R.id.editTextResult);
        result.setText(Double.toString(((double)Math.round(mp_inHg*10))/10.0));
    }
}

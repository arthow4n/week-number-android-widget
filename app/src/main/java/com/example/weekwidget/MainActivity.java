package com.example.weekwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class MainActivity extends Activity {

    private static final String PREFS_NAME = "WidgetConfig";
    private EditText widgetTextInput;
    private int textColor = Color.WHITE;
    private int backgroundColor = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        widgetTextInput = findViewById(R.id.widget_text_input);
        Button textColorButton = findViewById(R.id.text_color_button);
        Button backgroundColorButton = findViewById(R.id.background_color_button);
        SeekBar backgroundTransparencySeekbar = findViewById(R.id.background_transparency_seekbar);
        Button saveButton = findViewById(R.id.save_button);

        loadPreferences();

        textColorButton.setOnClickListener(v -> selectColor(true));
        backgroundColorButton.setOnClickListener(v -> selectColor(false));
        saveButton.setOnClickListener(v -> savePreferences());
    }

    private void selectColor(final boolean isTextColor) {
        int initialColor = isTextColor ? textColor : backgroundColor;
        ColorPickerDialogBuilder
                .with(this)
                .setTitle("Select color")
                .initialColor(initialColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(selectedColor -> {
                    // Not used
                })
                .setPositiveButton("ok", (dialog, selectedColor, allColors) -> {
                    if (isTextColor) {
                        textColor = selectedColor;
                    } else {
                        backgroundColor = selectedColor;
                    }
                })
                .setNegativeButton("cancel", (dialog, which) -> {
                })
                .build()
                .show();
    }

    private void loadPreferences() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        widgetTextInput.setText(prefs.getString("widget_text", "Vecka"));
        textColor = prefs.getInt("text_color", Color.WHITE);
        backgroundColor = prefs.getInt("background_color", Color.BLACK);
        ((SeekBar) findViewById(R.id.background_transparency_seekbar)).setProgress(prefs.getInt("background_transparency", 50));
    }

    private void savePreferences() {
        SharedPreferences.Editor prefs = getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString("widget_text", widgetTextInput.getText().toString());
        prefs.putInt("text_color", textColor);
        prefs.putInt("background_color", backgroundColor);
        prefs.putInt("background_transparency", ((SeekBar) findViewById(R.id.background_transparency_seekbar)).getProgress());
        prefs.commit();

        updateWidget();
        Toast.makeText(this, "Widget configured", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void updateWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, WeekWidgetProvider.class));
        if (appWidgetIds.length > 0) {
            new WeekWidgetProvider().onUpdate(this, appWidgetManager, appWidgetIds);
        }
    }
}
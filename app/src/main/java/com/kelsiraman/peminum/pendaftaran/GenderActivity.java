package com.kelsiraman.peminum.pendaftaran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelsiraman.peminum.R;
import com.kelsiraman.peminum.model.DataUser;

public class GenderActivity extends AppCompatActivity implements View.OnClickListener {
    private DataUser du;
    private static final String PARCEL = "DATAUSER";
    private ImageView maleImageView, femaleImageView;
    private TextView genderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
        init();
    }

    private void init(){
        du = new DataUser();
        maleImageView = findViewById(R.id.maleImageView);
        femaleImageView = findViewById(R.id.femaleImageView);
        genderTextView = findViewById(R.id.genderTextView);
        femaleImageView.setOnClickListener(this);
        maleImageView.setOnClickListener(this);
        final ContextThemeWrapper wrapper = new ContextThemeWrapper(this, R.style.DefaultGender);
        changeThemeFemale(wrapper.getTheme());
        changeThemeMale(wrapper.getTheme());
    }

    private void changeThemeFemale(final Resources.Theme theme){
        final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_female_character, theme);
        femaleImageView.setImageDrawable(drawable);
    }

    private void changeThemeMale(final Resources.Theme theme){
        final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_male_character, theme);
        maleImageView.setImageDrawable(drawable);
    }

    public void openWeightActivity(View view){
        DataUser parcelDU = new DataUser("joni@yespapa.com", "Joni", du.getUserGender(), null, null, 0);
        Intent intent = new Intent(this, WeightActivity.class);
        intent.putExtra(PARCEL, parcelDU);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.femaleImageView:
                du.setUserGender("Female");
                final ContextThemeWrapper wrapperFemale = new ContextThemeWrapper(this, R.style.FemalePick);
                changeThemeFemale(wrapperFemale.getTheme());
                changeThemeMale(wrapperFemale.getTheme());
                genderTextView.setText(R.string.female);
                genderTextView.setTextColor(getResources().getColor(R.color.femaleColor));
                break;
            case R.id.maleImageView:
                du.setUserGender("Male");
                final ContextThemeWrapper wrapperMale = new ContextThemeWrapper(this, R.style.MalePick);
                changeThemeFemale(wrapperMale.getTheme());
                changeThemeMale(wrapperMale.getTheme());
                genderTextView.setText(R.string.male);
                genderTextView.setTextColor(getResources().getColor(R.color.maleColor));
                break;
        }
    }
}

package com.kaancaliskan.guvenlinot;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.danielstone.materialaboutlibrary.model.MaterialAboutList;

@SuppressWarnings("unused")
public class LicenseActivity extends AboutActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_MaterialAboutActivity);
    }

    @NonNull @Override
    protected MaterialAboutList getMaterialAboutList(@NonNull final Context c) {
        return AboutActivity.Companion.createMaterialAboutLicenseList(c);
    }

    @Override
    protected CharSequence getActivityTitle() {
        return getString(R.string.licenses);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }
}
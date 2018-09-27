package com.kaancaliskan.guvenlinot

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
/**
 * This creates our license cards.
 */
class LicenseActivity : AboutActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_MaterialAboutActivity)
    }
    override fun getMaterialAboutList(context: Context): MaterialAboutList {
        return AboutActivity.createMaterialAboutLicenseList(context)
    }
    override fun getActivityTitle(): CharSequence? {
        return getString(R.string.licenses)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return false
        }
    }
}
package com.kaancaliskan.guvenlinot

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import com.danielstone.materialaboutlibrary.ConvenienceBuilder
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.danielstone.materialaboutlibrary.util.OpenSourceLicense
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.Display
import com.github.javiersantos.appupdater.enums.UpdateFrom
/**
 * This is about activity.
 */
open class AboutActivity : MaterialAboutActivity() {
    /**
     * This function is creating about page components.
     */
    override fun getMaterialAboutList(context: Context): MaterialAboutList {

        val appUpdater = AppUpdater(this)
        appUpdater.setUpdateFrom(UpdateFrom.GITHUB)
        appUpdater.setGitHubUserAndRepo(getString(R.string.github_user), getString(R.string.github_repo))
        appUpdater.setDisplay(Display.SNACKBAR)
        appUpdater.showAppUpdated(true)
        appUpdater.setContentOnUpdateAvailable(getString(R.string.update_available))
        appUpdater.setContentOnUpdateNotAvailable(getString(R.string.update_not_available))
        appUpdater.setButtonUpdate(getString(R.string.button_update))

        val appCardBuilder = MaterialAboutCard.Builder()

        appCardBuilder.addItem(MaterialAboutTitleItem.Builder()
                .text(getString(R.string.app_name))
                .desc(getString(R.string.copyright))
                .icon(R.mipmap.ic_launcher_round)
                .build())

        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.version))
                .subText(BuildConfig.VERSION_NAME)
                .icon(getDrawable(R.drawable.update))
                .setOnClickAction { appUpdater.start() }
                .build())

        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.github))
                .icon(getDrawable(R.drawable.github_circle))
                .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(context, Uri.parse("https://github.com/hakkikaancaliskan/guvenlinot")))
                .build())

        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.changelog))
                .icon(getDrawable(R.drawable.radar))
                .setOnClickAction (ConvenienceBuilder.createWebViewDialogOnClickAction(context, getString(R.string.changelog), "https://github.com/hakkikaancaliskan/guvenlinot/releases", true, false))
                .build())

        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.licenses))
                .icon(getDrawable(R.drawable.book))
                .setOnClickAction {
                    val intent = Intent(context, LicenseActivity::class.java)
                    context.startActivity(intent)
                }
                .build())

        appCardBuilder.addItem( MaterialAboutActionItem.Builder()
                .text(R.string.report_issue)
                .subText(R.string.report_issue_sub)
                .icon(ContextCompat.getDrawable(context, R.drawable.bug))
                .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(context, Uri.parse("https://github.com/hakkikaancaliskan/GuvenliNot/issues/new/choose")))
                .build())

        val authorCardBuilder = MaterialAboutCard.Builder()
        authorCardBuilder.title(getString(R.string.dev))

        authorCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.dev_name))
                .subText(getString(R.string.country))
                .icon(getDrawable(R.drawable.account))
                .build())

        authorCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.github))
                .icon(getDrawable(R.drawable.github_circle))
                .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(context, Uri.parse("https://github.com/hakkikaancaliskan")))
                .build())

        authorCardBuilder.addItem(ConvenienceBuilder.createEmailItem(context,
                getDrawable(R.drawable.email),
                getString(R.string.send_mail),
                true,
                getString(R.string.dev_mail),
                getString(R.string.mail_title)))
        return MaterialAboutList(appCardBuilder.build(), authorCardBuilder.build())
    }

    companion object {
        /**
         * This function creates our license cards.
         */
        fun createMaterialAboutLicenseList(c: Context): MaterialAboutList {

            val materialAboutLibraryLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                    ContextCompat.getDrawable(c, R.drawable.book),
                    "material-about-library",
                    "2016",
                    "Daniel Stone",
                    OpenSourceLicense.APACHE_2)

            val toastyLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                    ContextCompat.getDrawable(c, R.drawable.book),
                    "Toasty",
                    "2017",
                    "Daniel Morales",
                    OpenSourceLicense.GNU_GPL_3)

            val supportLibraryLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                    ContextCompat.getDrawable(c, R.drawable.book),
                    "AOSP Support Libraries",
                    "2011",
                    "The Android Open Source Project",
                    OpenSourceLicense.APACHE_2)

            val kotlinLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                    ContextCompat.getDrawable(c, R.drawable.book),
                    "Kotlin",
                    "2010",
                    "JetBrains s.r.o",
                    OpenSourceLicense.APACHE_2)

            val appUpdaterLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                    ContextCompat.getDrawable(c, R.drawable.book),
                    "AppUpdater",
                    "2016",
                    "Javier Santos",
                    OpenSourceLicense.APACHE_2)

            val okHttpLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                    ContextCompat.getDrawable(c, R.drawable.book),
                    "OkHttp",
                    "2012",
                    "Jesse Wilson",
                    OpenSourceLicense.APACHE_2)

        return MaterialAboutList(materialAboutLibraryLicenseCard, toastyLicenseCard, supportLibraryLicenseCard, kotlinLicenseCard, appUpdaterLicenseCard, okHttpLicenseCard)
    }}
    /**
     * This function names the activity.
     */
    override fun getActivityTitle(): CharSequence? {
        return getString(R.string.about)
    }
}
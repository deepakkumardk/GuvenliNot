package com.kaancaliskan.guvenlinot

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import com.danielstone.materialaboutlibrary.ConvenienceBuilder
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.danielstone.materialaboutlibrary.util.OpenSourceLicense
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard

/**
 * This is about activity.
 */
open class AboutActivity : MaterialAboutActivity() {
    /**
     * This function is creating about page components.
     */
    override fun getMaterialAboutList(context: Context): MaterialAboutList {
        val appCardBuilder = MaterialAboutCard.Builder()

        appCardBuilder.addItem(MaterialAboutTitleItem.Builder()
                .text(getString(R.string.app_name))
                .desc(getString(R.string.copyright))
                .icon(R.mipmap.ic_launcher_round)
                .build())

        appCardBuilder.addItem(ConvenienceBuilder.createVersionActionItem(context,
                IconicsDrawable(context)
                        .icon(CommunityMaterial.Icon2.cmd_information_outline)
                        .color(ContextCompat.getColor(context, R.color.colorAccent))
                        .sizeDp(18),
                getString(R.string.version),
                false))

        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.github))
                .icon(IconicsDrawable(context)
                        .icon(CommunityMaterial.Icon.cmd_github_circle)
                        .color(ContextCompat.getColor(context, R.color.colorAccent))
                        .sizeDp(18))
                .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(context, Uri.parse("https://github.com/hakkikaancaliskan/guvenlinot")))
                .build())

        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.changelog))
                .icon(IconicsDrawable(context)
                        .icon(CommunityMaterial.Icon2.cmd_history)
                        .color(ContextCompat.getColor(context, R.color.colorAccent))
                        .sizeDp(18))
                .setOnClickAction (ConvenienceBuilder.createWebViewDialogOnClickAction(context, getString(R.string.changelog), "https://github.com/hakkikaancaliskan/guvenlinot/releases", true, false))
                .build())

        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.licenses))
                .icon(IconicsDrawable(context)
                        .icon(CommunityMaterial.Icon.cmd_book)
                        .color(ContextCompat.getColor(context, R.color.colorAccent))
                        .sizeDp(18))
                .setOnClickAction {
                    val intent = Intent(context, LicenseActivity::class.java)
                    context.startActivity(intent)
                }
                .build())

        val authorCardBuilder = MaterialAboutCard.Builder()
        authorCardBuilder.title(getString(R.string.dev))

        authorCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.dev_name))
                .subText(getString(R.string.country))
                .icon(IconicsDrawable(context)
                        .icon(CommunityMaterial.Icon.cmd_account)
                        .color(ContextCompat.getColor(context, R.color.colorAccent))
                        .sizeDp(18))
                .build())

        authorCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.github))
                .icon(IconicsDrawable(context)
                        .icon(CommunityMaterial.Icon.cmd_github_circle)
                        .color(ContextCompat.getColor(context, R.color.colorAccent))
                        .sizeDp(18))
                .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(context, Uri.parse("https://github.com/hakkikaancaliskan")))
                .build())

        authorCardBuilder.addItem(ConvenienceBuilder.createEmailItem(context,
                IconicsDrawable(context)
                        .icon(CommunityMaterial.Icon.cmd_email)
                        .color(ContextCompat.getColor(context, R.color.colorAccent))
                        .sizeDp(18),
                getString(R.string.send_mail),
                true,
                getString(R.string.dev_mail),
                getString(R.string.mail_title)))
        return MaterialAboutList(appCardBuilder.build(), authorCardBuilder.build())
    }

    companion object {

        fun createMaterialAboutLicenseList(c: Context): MaterialAboutList {

            val materialAboutLibraryLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_book)
                        .color(ContextCompat.getColor(c, R.color.colorAccent))
                        .sizeDp(18),
                "material-about-library", "2016", "Daniel Stone",
                OpenSourceLicense.APACHE_2)

            val androidIconicsLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_book)
                        .color(ContextCompat.getColor(c, R.color.colorAccent))
                        .sizeDp(18),
                "Android Iconics", "2014", "Mike Penz",
                OpenSourceLicense.APACHE_2)

            val toastyLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_book)
                        .color(ContextCompat.getColor(c, R.color.colorAccent))
                        .sizeDp(18),
                "Toasty", "2017", "Daniel Morales",
                OpenSourceLicense.GNU_GPL_3)

            val supportLibraryLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                    IconicsDrawable(c)
                            .icon(CommunityMaterial.Icon.cmd_book)
                            .color(ContextCompat.getColor(c, R.color.colorAccent))
                            .sizeDp(18),
                    "AOSP Support Library", "2011", "The Android Open Source Project",
                    OpenSourceLicense.APACHE_2)

            val kotlinLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                    IconicsDrawable(c)
                            .icon(CommunityMaterial.Icon.cmd_book)
                            .color(ContextCompat.getColor(c, R.color.colorAccent))
                            .sizeDp(18),
                    "Kotlin Library", "2010", "JetBrains s.r.o",
                    OpenSourceLicense.APACHE_2)

        return MaterialAboutList(materialAboutLibraryLicenseCard, androidIconicsLicenseCard, toastyLicenseCard, supportLibraryLicenseCard, kotlinLicenseCard)
    }}
    /**
     * This function names the activity.
     */
    override fun getActivityTitle(): CharSequence? {
        return getString(R.string.about)
    }
}
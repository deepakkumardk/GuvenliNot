package com.kaancaliskan.guvenlinot

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import com.danielstone.materialaboutlibrary.ConvenienceBuilder
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem
import com.danielstone.materialaboutlibrary.model.*
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable

/**
 * This is about activity.
 */
class AboutActivity : MaterialAboutActivity() {
    /**
     * This function is creating about page components.
     */
    override fun getMaterialAboutList(context: Context): MaterialAboutList {
        val appCardBuilder = MaterialAboutCard.Builder()

        appCardBuilder.addItem(MaterialAboutTitleItem.Builder()
                .text(getString(R.string.app_name))
                .desc(getString(R.string.copyright))
                .icon(R.drawable.app_logo)
                .build())

        appCardBuilder.addItem(ConvenienceBuilder.createVersionActionItem(context,
                IconicsDrawable(context)
                        .icon(CommunityMaterial.Icon2.cmd_information_outline)
                        .color(ContextCompat.getColor(context, R.color.colorAccent))
                        .sizeDp(18),
                getString(R.string.version),
                false))

        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.app_github))
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
                .setOnClickAction (ConvenienceBuilder.createWebViewDialogOnClickAction(context, getString(R.string.releases), "https://github.com/hakkikaancaliskan/guvenlinot/releases", true, false))
                .build())

        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.licenses))
                .icon(IconicsDrawable(context)
                        .icon(CommunityMaterial.Icon.cmd_book)
                        .color(ContextCompat.getColor(context, R.color.colorAccent))
                        .sizeDp(18))
                .setOnClickAction {
                    val intent = Intent(context, Licenses::class.java)
                    context.startActivity(intent)
                }
                .build())

        val authorCardBuilder = MaterialAboutCard.Builder()
        authorCardBuilder.title("Author")

        authorCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.my_name))
                .subText(getString(R.string.country))
                .icon(IconicsDrawable(context)
                        .icon(CommunityMaterial.Icon.cmd_account)
                        .color(ContextCompat.getColor(context, R.color.colorAccent))
                        .sizeDp(18))
                .build())

        authorCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.personal_github))
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
                getString(R.string.my_mail),
                getString(R.string.mail_title)))
        return MaterialAboutList(appCardBuilder.build(), authorCardBuilder.build())
    }
    /**
     * This function names the activity.
     */
    override fun getActivityTitle(): CharSequence? {
        return getString(R.string.about)
    }
}
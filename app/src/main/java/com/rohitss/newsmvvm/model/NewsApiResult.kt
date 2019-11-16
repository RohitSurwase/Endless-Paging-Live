package com.rohitss.newsmvvm.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.rohitss.newsmvvm.utils.COVER_IMAGE_FORMAT
import com.rohitss.newsmvvm.utils.THUMBNAIL_FORMAT

class NewsApiResult {
    @SerializedName("section")
    @Expose
    var section: String? = null
    @SerializedName("results")
    @Expose
    var results: List<Result>? = null
    /*@SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("copyright")
    @Expose
    var copyright: String? = null
    @SerializedName("last_updated")
    @Expose
    var lastUpdated: String? = null
    @SerializedName("num_results")
    @Expose
    var numResults: Int? = null*/
}

class Multimedia {
    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("format")
    @Expose
    var format: String? = null
    /*@SerializedName("height")
    @Expose
    var height: Int? = null
    @SerializedName("width")
    @Expose
    var width: Int? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("subtype")
    @Expose
    var subtype: String? = null
    @SerializedName("caption")
    @Expose
    var caption: String? = null
    @SerializedName("copyright")
    @Expose
    var copyright: String? = null*/
}

class Result {
    fun getImage(isThumbnail: Boolean): String {
        multimedia?.forEach {
            if (isThumbnail && it.format == THUMBNAIL_FORMAT && !it.url.isNullOrEmpty()) {
                return it.url!!
            } else if (!isThumbnail && it.format == COVER_IMAGE_FORMAT && !it.url.isNullOrEmpty()) {
                return it.url!!
            }
        }
        return ""
    }

    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("abstract")
    @Expose
    var abstract: String? = null
    @SerializedName("byline")
    @Expose
    var byline: String? = null
    @SerializedName("published_date")
    @Expose
    var publishedDate: String? = null
    @SerializedName("multimedia")
    @Expose
    var multimedia: List<Multimedia>? = null
    @SerializedName("short_url")
    @Expose
    var shortUrl: String? = null
    /*@SerializedName("section")
    @Expose
    var section: String? = null
    @SerializedName("subsection")
    @Expose
    var subsection: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("item_type")
    @Expose
    var itemType: String? = null
    @SerializedName("updated_date")
    @Expose
    var updatedDate: String? = null
    @SerializedName("created_date")
    @Expose
    var createdDate: String? = null
    @SerializedName("material_type_facet")
    @Expose
    var materialTypeFacet: String? = null
    @SerializedName("kicker")
    @Expose
    var kicker: String? = null
    @SerializedName("des_facet")
    @Expose
    var desFacet: List<String>? = null
    @SerializedName("org_facet")
    @Expose
    var orgFacet: List<String>? = null
    @SerializedName("per_facet")
    @Expose
    var perFacet: List<String>? = null
    @SerializedName("geo_facet")
    @Expose
    var geoFacet: List<String>? = null*/
}
package com.develop.basicarchitecture.network.dataclasses


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("restaurants")
    val restaurants: List<Restaurant>,
    @SerializedName("results_found")
    val resultsFound: Int,
    @SerializedName("results_shown")
    val resultsShown: Int,
    @SerializedName("results_start")
    val resultsStart: Int
) {
    data class Restaurant(
        @SerializedName("restaurant")
        val restaurant: Restaurant
    ) {
        data class Restaurant(
            @SerializedName("all_reviews")
            val allReviews: AllReviews,
            @SerializedName("all_reviews_count")
            val allReviewsCount: Int,
            @SerializedName("apikey")
            val apikey: String,
            @SerializedName("average_cost_for_two")
            val averageCostForTwo: Int,
            @SerializedName("book_again_url")
            val bookAgainUrl: String,
            @SerializedName("book_form_web_view_url")
            val bookFormWebViewUrl: String,
            @SerializedName("cuisines")
            val cuisines: String,
            @SerializedName("currency")
            val currency: String,
            @SerializedName("deeplink")
            val deeplink: String,
            @SerializedName("establishment")
            val establishment: List<String>,
            @SerializedName("establishment_types")
            val establishmentTypes: List<Any>,
            @SerializedName("events_url")
            val eventsUrl: String,
            @SerializedName("featured_image")
            val featuredImage: String,
            @SerializedName("has_online_delivery")
            val hasOnlineDelivery: Int,
            @SerializedName("has_table_booking")
            val hasTableBooking: Int,
            @SerializedName("highlights")
            val highlights: List<String>,
            @SerializedName("id")
            val id: String,
            @SerializedName("include_bogo_offers")
            val includeBogoOffers: Boolean,
            @SerializedName("is_book_form_web_view")
            val isBookFormWebView: Int,
            @SerializedName("is_delivering_now")
            val isDeliveringNow: Int,
            @SerializedName("is_table_reservation_supported")
            val isTableReservationSupported: Int,
            @SerializedName("is_zomato_book_res")
            val isZomatoBookRes: Int,
            @SerializedName("location")
            val location: Location,
            @SerializedName("menu_url")
            val menuUrl: String,
            @SerializedName("mezzo_provider")
            val mezzoProvider: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("offers")
            val offers: List<Any>,
            @SerializedName("opentable_support")
            val opentableSupport: Int,
            @SerializedName("phone_numbers")
            val phoneNumbers: String,
            @SerializedName("photo_count")
            val photoCount: Int,
            @SerializedName("photos")
            val photos: List<Photo>,
            @SerializedName("photos_url")
            val photosUrl: String,
            @SerializedName("price_range")
            val priceRange: Int,
            @SerializedName("R")
            val r: R,
            @SerializedName("store_type")
            val storeType: String,
            @SerializedName("switch_to_order_menu")
            val switchToOrderMenu: Int,
            @SerializedName("thumb")
            val thumb: String,
            @SerializedName("timings")
            val timings: String,
            @SerializedName("url")
            val url: String,
            @SerializedName("user_rating")
            val userRating: UserRating
        ) {
            data class AllReviews(
                @SerializedName("reviews")
                val reviews: List<Review>
            ) {
                data class Review(
                    @SerializedName("review")
                    val review: List<Any>
                )
            }

            data class Location(
                @SerializedName("address")
                val address: String,
                @SerializedName("city")
                val city: String,
                @SerializedName("city_id")
                val cityId: Int,
                @SerializedName("country_id")
                val countryId: Int,
                @SerializedName("latitude")
                val latitude: String,
                @SerializedName("locality")
                val locality: String,
                @SerializedName("locality_verbose")
                val localityVerbose: String,
                @SerializedName("longitude")
                val longitude: String,
                @SerializedName("zipcode")
                val zipcode: String
            )

            data class Photo(
                @SerializedName("photo")
                val photo: Photo
            ) {
                data class Photo(
                    @SerializedName("caption")
                    val caption: String,
                    @SerializedName("friendly_time")
                    val friendlyTime: String,
                    @SerializedName("height")
                    val height: Int,
                    @SerializedName("id")
                    val id: String,
                    @SerializedName("res_id")
                    val resId: Int,
                    @SerializedName("thumb_url")
                    val thumbUrl: String,
                    @SerializedName("timestamp")
                    val timestamp: Int,
                    @SerializedName("url")
                    val url: String,
                    @SerializedName("user")
                    val user: User,
                    @SerializedName("width")
                    val width: Int
                ) {
                    data class User(
                        @SerializedName("foodie_color")
                        val foodieColor: String,
                        @SerializedName("name")
                        val name: String,
                        @SerializedName("profile_deeplink")
                        val profileDeeplink: String,
                        @SerializedName("profile_image")
                        val profileImage: String,
                        @SerializedName("profile_url")
                        val profileUrl: String
                    )
                }
            }

            data class R(
                @SerializedName("has_menu_status")
                val hasMenuStatus: HasMenuStatus,
                @SerializedName("res_id")
                val resId: Int
            ) {
                data class HasMenuStatus(
                    @SerializedName("delivery")
                    val delivery: Int,
                    @SerializedName("takeaway")
                    val takeaway: Int
                )
            }

            data class UserRating(
                @SerializedName("aggregate_rating")
                val aggregateRating: Double,
                @SerializedName("rating_color")
                val ratingColor: String,
                @SerializedName("rating_obj")
                val ratingObj: RatingObj,
                @SerializedName("rating_text")
                val ratingText: String,
                @SerializedName("votes")
                val votes: Int
            ) {
                data class RatingObj(
                    @SerializedName("bg_color")
                    val bgColor: BgColor,
                    @SerializedName("title")
                    val title: Title
                ) {
                    data class BgColor(
                        @SerializedName("tint")
                        val tint: String,
                        @SerializedName("type")
                        val type: String
                    )

                    data class Title(
                        @SerializedName("text")
                        val text: String
                    )
                }
            }
        }
    }
}
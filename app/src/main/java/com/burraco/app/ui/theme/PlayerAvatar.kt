package com.burraco.app.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.burraco.app.R

enum class PlayerAvatar(@param:DrawableRes val resouceId: Int) {
    CLUB_CROCHET(R.drawable.club_crochet),
    CLUB_FRAPPE(R.drawable.club_frappe),
    CLUB_SUNGLASSES(R.drawable.club_sunglasses),
    CLUB_HACKER(R.drawable.club_hacker),
    DIAMOND_BLANKET(R.drawable.diamond_blanket),
    DIAMOND_FRAPPE(R.drawable.diamond_frappe),
    DIAMOND_COFFE(R.drawable.diamond_coffe),
    DIAMOND_WIZARD(R.drawable.diamond_wizard),
    HEART_COFFE(R.drawable.heart_coffe),
    HEART_PIZZA(R.drawable.heart_pizza),
    HEART_QUEEN(R.drawable.heart_queen),
    HEART_WARRIOR(R.drawable.heart_warrior),
    SPADE_COFFE(R.drawable.spade_coffe),
    SPADE_FRENCH(R.drawable.spade_french),
    SPADE_MUSIC(R.drawable.spade_music),
    SPADE_COOKING(R.drawable.spade_cooking);


    val painter: Painter
        @Composable
        get() = painterResource(id = resouceId)

}
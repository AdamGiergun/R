package com.example.ryanair.util

import com.example.ryanair.db.Filters
import java.text.SimpleDateFormat
import java.util.*

object DefaultFilters {
    val value = Filters(
        0,
        Calendar.getInstance().run
        {
            add(Calendar.DAY_OF_YEAR, 30)
            val formatter =
                SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            formatter.format(time)
        },
        "DUB",
        "STN",
        1,
        0,
        0,
        0
    )
}
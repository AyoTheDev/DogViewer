package com.ayo.api

import com.ayo.api.model.DogApiResponseItem
import com.ayo.domain.model.DogDomain

fun DogApiResponseItem.toDomain(): DogDomain {
    return DogDomain(
        bred_for = bred_for,
        breed_group = breed_group,
        country_code = country_code,
        description = description,
        imageHeight = image?.height,
        imperialHeight = height?.imperial,
        metricHeight = height?.metric,
        history = history,
        id = id,
        imageWidth = image?.width,
        imageId = image?.id,
        imageUrl = image?.url,
        imperialWeight = weight?.imperial,
        metricWeight = weight?.metric,
        lifeSpan = life_span,
        name = name,
        origin = origin,
        referenceImageId = reference_image_id,
        temperament = temperament
    )
}
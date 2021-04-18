package com.ayo.doggallery.common

import com.ayo.domain.model.DogDomain


class DogBuilder {
    companion object {
        fun build(): DogDomain {
            return DogDomain(
                "Small rodent hunting, lapdog",
                "Toy",
                "",
                "",
                "9 - 11.5",
                "23 - 29",
                "",
                1,
                1199,
                1600,
                "BJa4kxc4X",
                "https://cdn2.thedogapi.com/images/BJa4kxc4X.jpg",
                "6 - 13",
                "3 - 6",
                "10 - 12 years",
                "Affenpinscher",
                "Germany, France",
                "BJa4kxc4X",
                "Stubborn, Curious, Playful, Adventurous, Active, Fun-loving",
            )

        }
    }
}
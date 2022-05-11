/*
 Copyright (c) 2022 L9CRO1XX

 This Source Code Form is subject to the terms of the Mozilla Public License,
 v. 2.0. If a copy of the MPL was not distributed with this file, You can
 obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.github.l9cro1xx.boonforge

import org.jetbrains.annotations.NotNull
import java.util.*

private val resourceBundle: ResourceBundle = ResourceBundle.getBundle(
    "BoonForge"
)

/**
 * Gets a string for the given key from the boonforge resource bundle.
 *
 * This method is essentially a wrapper around [ResourceBundle.getString].
 *
 * @param[key] the key for the desired string
 * @throws[NullPointerException] if [key] is null
 * @throws[MissingResourceException] if no object for the given key can be found
 * @throws[ClassCastException] if the object found for the given key is not a string
 * @return the string for the given key
 */
@NotNull
fun getResourceString(@NotNull key: String): String {
    return resourceBundle.getString(key)
}

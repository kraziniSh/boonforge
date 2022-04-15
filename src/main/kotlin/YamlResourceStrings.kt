package com.github.l9cro1xx.boonforge

import dev.akkinoc.util.YamlResourceBundle
import org.jetbrains.annotations.NotNull
import java.util.*

val resourceBundle: ResourceBundle = ResourceBundle.getBundle(
    "BoonForge",
    YamlResourceBundle.Control
)

// TODO Depend on system locale
/**
 * Gets a string for the given key from the boonforge resource bundle.
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

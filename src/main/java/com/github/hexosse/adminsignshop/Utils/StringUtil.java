/*
 * Copyright 2015 Hexosse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.hexosse.adminsignshop.Utils;

import org.apache.commons.lang.WordUtils;

/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class StringUtil
{
    /**
     * Capitalizes every first letter of a word
     *
     * @param string    String to reformat
     * @param separator Word separator
     * @return Reformatted string
     */
    public static String capitalizeFirstLetter(String string, char separator) {
        char[] separators = new char[]{separator};

        return WordUtils.capitalizeFully(string, separators).replace(String.valueOf(separator), " ");
    }

    /**
     * Capitalizes every first letter of a word
     *
     * @param string String to reformat
     * @return Reformatted string
     * @see com.github.hexosse.adminsignshop.Utils.StringUtil#capitalizeFirstLetter(String, char)
     */
    public static String capitalizeFirstLetter(String string) {
        return capitalizeFirstLetter(string, ' ');
    }
}

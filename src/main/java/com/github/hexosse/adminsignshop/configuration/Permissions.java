package com.github.hexosse.adminsignshop.configuration;

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

import com.github.hexosse.adminsignshop.AdminSignShop;
import com.github.hexosse.baseplugin.permissions.BasePermissions;
import com.github.hexosse.baseplugin.permissions.PluginPermission;
import org.bukkit.permissions.PermissionDefault;


/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class Permissions extends BasePermissions<AdminSignShop>
{
    public static final PluginPermission ADMIN      = new PluginPermission("AutoAdminShop.admin", PermissionDefault.OP,	"Gives access to all AdminSignShop permissions");


    /**
     * @param plugin The plugin that this object belong to.
     */
    public Permissions(AdminSignShop plugin) {
        super(plugin, Permissions.class);
    }
}

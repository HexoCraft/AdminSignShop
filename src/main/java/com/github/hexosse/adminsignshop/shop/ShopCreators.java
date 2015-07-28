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

package com.github.hexosse.adminsignshop.shop;

import java.util.Hashtable;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.bukkit.entity.Player;


/**
 * This file is part AdminSignShop
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ShopCreators
{
	private final Lock lock = new ReentrantLock();
    private final Hashtable<UUID, Creator> creatorList;		// Liste des utilisateur en train de créer un AdminShop

    
	// Constructeur
    public ShopCreators()
    {
    	//creatorList = new Vector<Creator>();
    	creatorList = new Hashtable<UUID, Creator>();
    }
    
    // Ajout d'un créateur
    public void add(Player player)
    {
    	lock.lock();
    	try
		{
    		creatorList.put(player.getUniqueId(),new Creator(player));
		}
    	finally
		{
    		lock.unlock();
		}
    }
    
    // Suppression d'un créateur
    public void remove(Player player)
    {
    	lock.lock();
    	try
		{
    		creatorList.remove(player.getUniqueId());
		}
    	finally
		{
    		lock.unlock();
		}
    }
    
    // Test si un créateur exist
    public boolean exist(Player player)
    {
    	lock.lock();
    	boolean bret = true;
    	try
		{
    		bret = creatorList.containsKey(player.getUniqueId());
		}
    	finally
		{
    		lock.unlock();
		}
		return bret;
    }
    
    // récupère un créateur si il exist
    public Creator get(Player player)
    {
    	lock.lock();
    	Creator creator = null;
    	try
		{
    		creator = creatorList.get(player.getUniqueId());
		}
    	finally
		{
    		lock.unlock();
		}
		return creator;
    }


	//
	public void enable(Player player, boolean enable)
	{
		Creator creator = get(player);

		if(creator!=null)
			creator.enable = enable;
	}

}

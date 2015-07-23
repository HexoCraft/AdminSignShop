package com.github.hexosse.adminsignshop.Utils;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

/**
 * @author Acrobot
 */
public class LocationUtil
{
	/**
     * Returns a string representing the location
     *
     * @param location Location represented
     * @return Representation of the location
     */
    public static String locationToString(Location location)
    {
        return '[' + location.getWorld().getName() + "] " + (int)location.getBlockX() + ", " + (int)location.getBlockY() + ", " + (int)location.getBlockZ();
    }

    public static boolean equals(Location location1, Location location2)
    {
    	return (location1.getWorld().getName()==location2.getWorld().getName())
    			&&  (location1.getBlockX()==location2.getBlockX())
    			&&  (location1.getBlockY()==location2.getBlockY())
    			&&  (location1.getBlockZ()==location2.getBlockZ());
    }

    
    public static Location top(Location location)
	{
		Location top = location.clone();
		return top.add(0, 1, 0);
	}
	
	public static Location bottom(Location location)
	{
		Location top = location.clone();
		return top.add(0, -1, 0);
	}
	
	public static Entity[] getNearbyEntities(Location l, int radius)
	{
        HashSet<Entity> radiusEntities = new HashSet<Entity>();

        for (int chX = 0 - radius; chX <= radius; chX ++)
        {
        	for (int chZ = 0 - radius; chZ <= radius; chZ++)
        	{
        		int x=(int) l.getX(),y=(int) l.getY(),z=(int) l.getZ();
        		for (Entity e : new Location(l.getWorld(),x+chX,y,z+16).getChunk().getEntities())
        		{
        			if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()) radiusEntities.add(e);
        		}
        	}
        }
        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }
	
	public static void RemoveBlock(Location l)
	{
		Block b = l.getBlock();
		if(b!=null) b.setType(Material.AIR);
	}
	
    public static double distance(Location a, Location b)
    {
        return Math.sqrt(square(a.getX() - b.getX()) + square(a.getY() - b.getY()) + square(a.getZ() - b.getZ()));
    }
   
    public static double square(double x)
    {
        return x * x;
    }
}

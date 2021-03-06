package game.level.block;

import game.entity.Banana;
import game.entity.Entity;
import game.entity.Heart;
import game.entity.enemy.Minion;
import game.entity.enemy.Roller;
import game.entity.weapon.Pistol;
import java.util.Collection;
import java.util.HashMap;

public abstract class Item
{
	private static final HashMap<Integer, Item>	ITEMS			= new HashMap<>();
	private static final HashMap<Short, Item>	ALPHAS			= new HashMap<>();
	
	/**
	 * Creates a new static heart.
	 */
	public static final Item					HEART_STATIC	= new Item("Statisches Herz", 0xFF0000, 252)
																{
																	@Override
																	public Entity create(final int aX, final int aY)
																	{
																		return new Heart(aX, aY, 2, true);
																	}
																	
																	@Override
																	public int getWidth()
																	{
																		return 10;
																	}
																	
																	@Override
																	public int getHeight()
																	{
																		return 10;
																	}
																};
	
	/**
	 * Creates a new non static heart.
	 */
	public static final Item					HEART			= new Item("Herz", 0xFE0000, 253)
																{
																	@Override
																	public Entity create(final int aX, final int aY)
																	{
																		return new Heart(aX, aY, 2, false);
																	}
																	
																	@Override
																	public int getWidth()
																	{
																		return 10;
																	}
																	
																	@Override
																	public int getHeight()
																	{
																		return 10;
																	}
																};
	
	/**
	 * Creates a new banana.
	 */
	public static final Item					BANANA			= new Item("Banane", 0xFFFF00, 254)
																{
																	@Override
																	public Entity create(final int aX, final int aY)
																	{
																		return new Banana(aX, aY, false);
																	}
																	
																	@Override
																	public int getWidth()
																	{
																		return 16;
																	}
																	
																	@Override
																	public int getHeight()
																	{
																		return 16;
																	}
																};
	
	/**
	 * Creates a new super banana.
	 */
	public static final Item					SUPER_BANANA	= new Item("Super Banane", 0xFF00FF, 255)
																{
																	@Override
																	public Entity create(final int aX, final int aY)
																	{
																		return new Banana(aX, aY, true);
																	}
																	
																	@Override
																	public int getWidth()
																	{
																		return 16;
																	}
																	
																	@Override
																	public int getHeight()
																	{
																		return 16;
																	}
																};
	
	/**
	 * Creates a new Rolling minion that tries to kill you but doesn't follow.
	 */
	public static final Item					ROLLER			= new Item("Rollender Minion", 0xB6FF00, 251)
																{
																	@Override
																	public Entity create(final int aX, final int aY)
																	{
																		return new Roller(aX, aY);
																	};
																	
																	@Override
																	public int getWidth()
																	{
																		return 16;
																	}
																	
																	@Override
																	public int getHeight()
																	{
																		return 16;
																	}
																};
	
	/**
	 * Creates a new Minion that tries to kill and follow you.
	 */
	public static final Item					MINION			= new Item("Minion", 0x007F0E, 250)
																{
																	@Override
																	public Entity create(final int aX, final int aY)
																	{
																		return new Minion(aX, aY);
																	};
																	
																	@Override
																	public int getWidth()
																	{
																		return 16;
																	}
																	
																	@Override
																	public int getHeight()
																	{
																		return 32;
																	}
																};
	
	/**
	 * Creates the pistol weapon that can be picked up and used to shoot.
	 */
	public static final Item					PISTOL			= new Item("Pistole", 0x039505, 249)
																{
																	@Override
																	public Entity create(final int aX, final int aY)
																	{
																		return new Pistol(aX, aY);
																	}
																	
																	@Override
																	public int getHeight()
																	{
																		return 10;
																	}
																	
																	@Override
																	public int getWidth()
																	{
																		return 20;
																	}
																};
	
	private final String						mName;
	
	private final int							mRGB;
	private final short							mAlpha;
	
	private Item(final String aName, final int aRGB, final int aAlpha)
	{
		mName = aName;
		mRGB = aRGB;
		mAlpha = (short) aAlpha;
		ITEMS.put(mRGB, this);
		ALPHAS.put(mAlpha, this);
		RGBManager.instance().addRGB(mRGB);
		RGBManager.instance().addAlpha(mAlpha);
	}
	
	public static Collection<Item> values()
	{
		return ITEMS.values();
	}
	
	/**
	 * Creates the entity.
	 * 
	 * @param aX
	 *            The x position to create at.
	 * @param aY
	 *            The y position to create at.
	 * @return a new entity.
	 */
	public abstract Entity create(int aX, int aY);
	
	/**
	 * Returns the width of the entity to create.
	 * 
	 * @return an integer representing the width.
	 */
	public abstract int getWidth();
	
	/**
	 * Returns the height of the entity to create.
	 * 
	 * @return an integer representing the height.
	 */
	public abstract int getHeight();
	
	/**
	 * Returns whether an items was defined with the given RGB code.
	 * 
	 * @param aRGB
	 *            The color to check.
	 * @return {@code true} if an item with color {@code aRGB} exists and {@code false} if not.
	 */
	public static boolean containsCode(final int aRGB)
	{
		return ITEMS.containsKey(aRGB);
	}
	
	/**
	 * Creates a new entity that is referenced by the given color code.
	 * 
	 * @param aX
	 *            The x position of the entity.
	 * @param aY
	 *            The y position of the entity.
	 * @param aRGB
	 *            The color code.
	 * @return a new item.
	 */
	public static Entity getEntity(final int aX, final int aY, final int aRGB)
	{
		return ITEMS.get(aRGB).create(aX, aY);
	}
	
	public static Item getItem(final short aAlpha)
	{
		return ALPHAS.get(aAlpha);
	}
	
	public static Item getItem(final int aRGB)
	{
		return ITEMS.get(aRGB);
	}
	
	public short getAlpha()
	{
		return mAlpha;
	}
	
	public int getRGB()
	{
		return mRGB;
	}
	
	@Override
	public String toString()
	{
		return mName;
	}
}

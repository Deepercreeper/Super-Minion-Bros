package game.entity.weapon;

import game.entity.Entity;
import game.entity.Player;
import game.level.Level;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public abstract class Weapon extends Entity
{
	protected Entity	mParent;
	
	protected int		mTime	= 0;
	
	public Weapon(final Entity aParent, final int aWidth, final int aHeight)
	{
		super((int) (aParent.getX() + aParent.getWidth() / 2 - aWidth / 2), (int) (aParent.getY() + aParent.getHeight() / 2 - aHeight / 2), aWidth, aHeight);
	}
	
	public Weapon(final int aX, final int aY, final int aWidth, final int aHeight)
	{
		super(aX, aY, aWidth, aHeight);
		mParent = null;
	}
	
	@Override
	public void update(final Input aInput)
	{
		mTime++ ;
		if (mParent != null)
		{
			mX = (int) (mParent.getX() + mParent.getWidth() / 2 - mWidth / 2) + getXOffset();
			mY = (int) (mParent.getY() + mParent.getHeight() / 2 - mHeight / 2) + getYOffset();
		}
		else
		{
			// Reset attributes
			mHurt = mOnIce = false;
			
			move();
			
			mXV *= 0.95f - (mOnGround ? 0.45 : 0) + (mOnIce ? 0.48 : 0) - (mInLiquid ? 0.3 : 0);
			
			final double gravity = Level.GRAVITY - (mInLiquid ? 0.1 : 0), friction = Level.FRICTION - (mInLiquid ? 0.1 : 0);
			
			mYV *= friction;
			mYV += gravity;
		}
	}
	
	@Override
	public final boolean isSolid()
	{
		return mParent == null;
	}
	
	@Override
	public final boolean isParticle()
	{
		return mParent != null;
	}
	
	@Override
	public final void hitEntity(final double aXV, final double aYV, final Entity aEntity)
	{
		if (aEntity instanceof Player)
		{
			Player p = (Player) aEntity;
			p.addWeapon(this);
			mParent = p;
		}
	}
	
	public abstract void shoot(Input aInput);
	
	protected abstract int getXOffset();
	
	protected abstract int getYOffset();
	
	@Override
	public void render(final Graphics aG)
	{
		aG.setColor(Color.red);
		aG.fillRect((int) (mX - mLevel.getScreenX()), (int) (mY - mLevel.getScreenY()), mWidth, mHeight);
	}
}

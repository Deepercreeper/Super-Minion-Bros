package game.entity;

import game.world.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Blood extends Entity
{
	private int	mLife;
	
	public Blood(int aX, int aY)
	{
		super(aX, aY, 2, 2);
		mXV = (Math.random() - Math.random()) * 5;
		mYV = -Math.random() * 5;
		mLife = (int) (20 + Math.random() * 100);
	}
	
	@Override
	public void hitEntity(double aXV, double aYV, Entity aEntity)
	{}
	
	@Override
	public void update(Input aInput)
	{
		if (--mLife < 0) remove();
		
		mOnIce = false;
		
		move();
		
		mXV *= 0.95f - (mOnGround ? 0.45 : 0) + (mOnIce ? 0.48 : 0) - (mInLiquid ? 0.3 : 0);
		
		final double gravity = World.GRAVITY - (mInLiquid ? 0.1 : 0), friction = World.FRICTION - (mInLiquid ? 0.1 : 0);
		
		mYV *= friction;
		mYV += gravity;
		
		// Reset attributes
		mInLiquid = false;
	}
	
	@Override
	public void render(Graphics g)
	{
		g.setColor(Color.red);
		g.fillRect((float) (mX - mWorld.getScreenX()), (float) (mY - mWorld.getScreenY()), mWidth, mHeight);
	}
	
	@Override
	public boolean isSolid()
	{
		return false;
	}
}
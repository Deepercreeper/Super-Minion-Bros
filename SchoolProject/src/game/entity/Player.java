package game.entity;

import game.world.Level;
import game.world.block.Block;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import data.DataManager;

public class Player extends Entity
{
	private int			mTime		= 0;
	
	private final int	mMaxLife	= 10;
	
	private int			mLife		= mMaxLife;
	
	private int			mHurtDelay;
	
	private boolean		mCannon;
	
	private int			mCannonDelay;
	
	/**
	 * Creates a new player at the given position.
	 * 
	 * @param aX
	 *            The x position.
	 * @param aY
	 *            The y position.
	 */
	public Player()
	{
		super(0, 0, Block.SIZE - 2, Block.SIZE * 2 - 2);
	}
	
	@Override
	public void update(Input aInput)
	{
		mTime++ ;
		if (mHurtDelay > 0) mHurtDelay-- ;
		if ( !mHurt)
		{
			if (aInput.isKeyPressed(Input.KEY_S) && !mOnGround)
			{
				mCannon = true;
				mCannonDelay = 10;
				DataManager.playSound("cannon");
			}
			if (aInput.isKeyPressed(Input.KEY_W) || mOnGround)
			{
				if (mCannon && mOnGround) DataManager.playSound("bomb");
				mCannon = false;
			}
			
			if ( !mCannon)
			{
				mXA = 0;
				if (aInput.isKeyDown(Input.KEY_D)) mXA += 1.5;
				if (aInput.isKeyDown(Input.KEY_A)) mXA -= 1.5;
				if (aInput.isKeyDown(Input.KEY_LSHIFT)) mXA *= 1.5;
				if ( !mOnGround) mXA *= 0.125;
				if (mOnIce) mXA *= 0.08;
				
				if (aInput.isKeyPressed(Input.KEY_SPACE) && (mOnGround || mOnWall))
				{
					if (mOnWall)
					{
						mXV = 10 * (mLeftWall ? 1 : -1);
						mYV = -5;
					}
					else mYV = -6;
					DataManager.playSound("jump");
				}
			}
			else
			{
				mXV = 0;
				if (--mCannonDelay <= 0) mYV = 10;
				else mYV = 0;
			}
		}
		mHurt = false;
		
		mXV += mXA;
		
		// Reset attributes
		mOnIce = false;
		
		move();
		
		mXV *= 0.95f - (mOnGround ? 0.45 : 0) + (mOnIce ? 0.48 : 0) - (mInLiquid ? 0.3 : 0);
		
		final double gravity = Level.GRAVITY - (mInLiquid ? 0.1 : 0), friction = Level.FRICTION - (mInLiquid ? 0.1 : 0);
		
		if (mYV < 0 && aInput.isKeyDown(Input.KEY_SPACE))
		{
			mYV *= friction + 0.002;
			mYV += gravity * 0.5f;
		}
		else
		{
			mYV *= friction;
			if (mOnWall) mYV += gravity * 0.3;
			else mYV += gravity;
		}
		
		// Reset attributes
		mInLiquid = false;
	}
	
	/**
	 * Returns whether this player has started to do a cannon ball (ass bomb).
	 * 
	 * @return {@code true} if doing and {@code false} if not.
	 */
	public boolean isCannonBall()
	{
		return mCannon;
	}
	
	@Override
	public void render(Graphics g)
	{
		// Player
		if (mHurtDelay > 0 && mTime % 10 < 5) g.drawImage(DataManager.getSplitImage("player", 0), (float) mX - mLevel.getScreenX(), (float) mY - mLevel.getScreenY(), new Color(1, 1, 1, 0.5f));
		else g.drawImage(DataManager.getSplitImage("player", 0).getScaledCopy(mWidth, mHeight), (float) mX - mLevel.getScreenX(), (float) mY - mLevel.getScreenY());
		
		// HUD
		g.setColor(Color.red);
		g.fillRect(10, mLevel.getScreenHeight() - 20, 100, 10);
		g.setColor(Color.green);
		g.fillRect(10, mLevel.getScreenHeight() - 20, 100 * mLife / mMaxLife, 10);
	}
	
	@Override
	public void hitEntity(double aXV, double aYV, Entity aEntity)
	{
		if (aEntity instanceof Gore) ((Gore) aEntity).hit(this);
		if (aEntity instanceof Banana) ((Banana) aEntity).collect();
		if (aEntity instanceof Heart) ((Heart) aEntity).collect();
		if (aEntity.isSolid()) hitWall(aXV, aYV);
	}
	
	@Override
	public void hurt(int aAmount, float aXV, float aYV)
	{
		if (mHurtDelay <= 0)
		{
			mLife -= aAmount;
			mHurtDelay = 100;
			mHurt = true;
			mXV = aXV;
			mYV = aYV;
			for (int i = (int) (Math.random() * 100 + 50 + (mLife <= 0 ? 50 : 0)); i > 0; i-- )
				mLevel.addEntity(new Blood((int) (mX + mWidth / 2), (int) (mY + mHeight / 2)));
			for (int i = (int) (Math.random() * 3 + (mLife <= 0 ? 10 : 0)); i > 0; i-- )
				mLevel.addEntity(new Gore((int) (mX + mWidth / 2), (int) (mY + mHeight / 2)));
			if (mLife <= 0) die();
		}
	}
	
	@Override
	public void respawn()
	{
		mDead = false;
		mLife = mMaxLife;
		mXV = mYV = 0;
		
	}
	
	@Override
	public void die()
	{
		mDead = true;
	}
	
	public void addLife(int aAmount)
	{
		mLife += aAmount;
		if (mLife > mMaxLife) mLife = mMaxLife;
	}
	
	@Override
	public boolean canDestroyBlocks()
	{
		return true;
	}
	
	@Override
	public boolean isSolid()
	{
		return true;
	}
}

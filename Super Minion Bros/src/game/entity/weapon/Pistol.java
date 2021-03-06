package game.entity.weapon;

import game.entity.Bullet;
import game.entity.Entity;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import util.Direction;
import data.DataManager;
import data.names.ImageName;

public class Pistol extends Weapon
{
	private int	mDelay	= 0;
	
	public Pistol(final Entity aParent)
	{
		super(aParent, 20, 10);
	}
	
	public Pistol(final int aX, final int aY)
	{
		super(aX, aY, 20, 10);
	}
	
	@Override
	protected int getXOffset()
	{
		return 5;
	}
	
	@Override
	protected int getYOffset()
	{
		return 0;
	}
	
	@Override
	public void shoot(final Input aInput)
	{
		if (mDelay > 0) return;
		mDelay = 20;
		final int speed = 10;
		final int mouseX = aInput.getMouseX() + mLevel.getScreenX(), mouseY = aInput.getMouseY() + mLevel.getScreenY();
		int startX, startY = (int) (mY + mHeight / 2);
		if (mouseX > mX + mWidth) startX = (int) (mX + mWidth);
		else if (mouseX < mX) startX = (int) mX;
		else
		{
			startX = (int) (mX + mWidth / 2);
			startY = (int) mY;
		}
		final double xd = mouseX - startX, yd = mouseY - startY;
		final double a = Math.acos(Math.abs(xd) / Math.sqrt(xd * xd + yd * yd));
		final double xv = Math.cos(a) * speed * Math.signum(xd), yv = Math.sin(a) * speed * Math.signum(yd);
		mLevel.addEntity(new Bullet(startX, startY, xv, yv, 3, this));
	}
	
	@Override
	protected void tick()
	{
		mDelay-- ;
		if (mDelay < 0) mDelay = 0;
	}
	
	@Override
	public String getName()
	{
		return "Pistol";
	}
	
	@Override
	public void render(final Graphics aG)
	{
		Image image = DataManager.instance().getTexturedSplitImage(ImageName.WEAPON, 0);
		if (mDir == Direction.LEFT) image = image.getFlippedCopy(true, false);
		aG.drawImage(image, (int) (mX - mLevel.getScreenX()), (int) (mY - mLevel.getScreenY()), null);
	}
	
	@Override
	public void renderIcon(final Graphics aG, final int aX, final int aY)
	{
		final Image image = DataManager.instance().getTexturedSplitImage(ImageName.WEAPON, 0);
		aG.drawImage(image, aX, aY, null);
	}
}

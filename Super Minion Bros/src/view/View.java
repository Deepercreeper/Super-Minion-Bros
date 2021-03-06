package view;

import game.Game;
import java.awt.Toolkit;
import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.CursorLoader;

public class View extends BasicGame
{
	private final AppGameContainer	mGameContainer;
	
	private final Game				mGame;
	
	/**
	 * Creates a new Minion bros. game.
	 * 
	 * @throws SlickException
	 *             when a slick exception occurs.
	 */
	public View() throws SlickException
	{
		super("Super Minion Bros");
		final Toolkit tk = Toolkit.getDefaultToolkit();
		mGameContainer = new AppGameContainer(this);
		mGameContainer.setDisplayMode(tk.getScreenSize().width, tk.getScreenSize().height, true);
		mGameContainer.setVSync(true);
		mGameContainer.setTargetFrameRate(60);
		mGameContainer.setShowFPS(false);
		
		mGame = new Game();
		mGameContainer.start();
	}
	
	@Override
	public void render(final GameContainer aGC, final Graphics aG)
	{
		mGame.render(aG);
	}
	
	@Override
	public void update(final GameContainer aGC, final int aDelta)
	{
		if (mGame.isRunning()) mGame.update(aDelta);
		else mGameContainer.exit();
	}
	
	@Override
	public void init(final GameContainer aGC) throws SlickException
	{
		try
		{
			mGameContainer.setMouseCursor(CursorLoader.get().getCursor("data/images/Cursor.png", 16, 16), 16, 16);
		}
		catch (IOException | LWJGLException e)
		{
			e.printStackTrace();
		}
		mGame.init(aGC);
	}
}

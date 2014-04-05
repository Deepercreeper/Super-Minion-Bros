package main;

import org.newdawn.slick.SlickException;
import view.View;
import editor.Editor;

public class Main
{
	public static void main(final String[] args)
	{
		if (args.length > 0 && args[0].equals("Editor"))
		{
			new Editor();
			return;
		}
		try
		{
			new View();
		}
		catch (final SlickException e)
		{
			e.printStackTrace();
		}
	}
}
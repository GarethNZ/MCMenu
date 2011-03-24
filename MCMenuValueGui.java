package net.minecraft.src;

import org.lwjgl.input.Keyboard;

// Maybe DON't extend GuiChat :(
public class MCMenuValueGui extends GuiChat {
	private String title;
	public boolean expired = false;
	
	public MCMenuValueGui(String t)
	{
		title = t;
	}
	
	public void drawScreen(int i, int j, float f)
    {
		// TODO: draw title in a box above this:
        drawRect(2, height - 14, width - 2, height - 2, 0x80000000);
        drawString(fontRenderer, title, 4, height - 12, 0xe0e0e0);
		// end todo
        super.drawScreen(i, j, f);
    }
	
	protected void keyTyped(char c, int i)
    {
        if(i == Keyboard.KEY_RETURN) // steal the enter event
        {
			String s = message.trim();
            if(s.length() > 0)
            {
                mc.thePlayer.sendChatMessage("/menu " + s);
            }
            mc.displayGuiScreen(null);
            return;
        }
		else
			super.keyTyped(c, i);
	}
}

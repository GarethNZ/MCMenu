package net.minecraft.src;

import org.lwjgl.input.Keyboard;

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
	
	public void drawScreen(int i, int j, float f)
    {
		int maxWidth = 0;
		for(String s : options)
		{
			maxWidth = Math.max(maxWidth, fontRenderer.getStringWidth(s));
		}
		
		drawGradientRect(0, 0, 20+maxWidth+20, height, 0xc0101010, 0xd0101010);
        drawCenteredString(fontRenderer, title, (20+maxWidth+20) / 2, 20, 0xffffff);
        
        for(int s = 0; s < options.length; s++)
        {
        	drawString(fontRenderer, options[s], 20, 30+(20*s), 0xffffff);
        }
        
        super.drawScreen(i, j, f);
    }
	
	protected void keyTyped(char c, int i)
    {
        if(i == KeyBoard.KEY_ENTER;) // steal the enter event
        {
            String s = message.trim();
            if(s.length() > 0)
            {
                mc.thePlayer.sendChatMessage("/menu " + s1);
            }
            mc.displayGuiScreen(null);
            return;
        }
		else
			super.keyTyped(c, i);
	}
}

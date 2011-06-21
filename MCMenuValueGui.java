package net.minecraft.src;

import org.lwjgl.input.Keyboard;

public class MCMenuValueGui extends GuiScreen {
    private String title;
    public boolean expired = false;
	private String message;
	private int updateCounter;
    
    public MCMenuValueGui(String t)
    {
        title = t;
		message = "";
        updateCounter = 0;
    }
    
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }
    
	public void updateScreen()
    {
        updateCounter++;
    }
	
    public void drawScreen(int i, int j, float f)
    {
        // draw title
        drawRect(2, height - 26, width - 2, height - 14, 0x80000000);
        drawString(fontRenderer, title, 4, height - 24, 0xe0e0e0);
        // Draw input
		drawRect(2, height - 14, width - 2, height - 2, 0x80000000);
        drawString(fontRenderer, (new StringBuilder()).append("> ").append(message).append((updateCounter / 6) % 2 != 0 ? "" : "_").toString(), 4, height - 12, 0xe0e0e0);
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
        else if ( i == 0 )
        {
            mc.displayGuiScreen(null);
            return;
        }
		else if(i == 14 && message.length() > 0) // delete
        {
            message = message.substring(0, message.length() - 1);
        }
        else // Keep anything else
        {
        	message += c;
        }
        //else
        //    super.keyTyped(c, i);
    }
}

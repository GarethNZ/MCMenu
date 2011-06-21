package net.minecraft.src;

import org.lwjgl.input.Keyboard;

public class MCMenuGui extends GuiScreen {
	private String title;
	private String options[];
	public boolean expired = false;
	
	public MCMenuGui(String t, String[] o)
	{
		title = t;
		if( o.length > 10 )
		{
			options = new String[10];
			for(int i = 0; i < 10; i++)
				options[i] = o[i];
		}
		else
			options = o;
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
	
	public void handleKeyboardInput()
    {
        if(Keyboard.getEventKeyState())
        {
            if(Keyboard.getEventKey() >= Keyboard.KEY_1 && Keyboard.getEventKey() <= Keyboard.KEY_0)
            {
            	int key = Keyboard.getEventKey() - (Keyboard.KEY_1 - 1);
                //System.out.println("Key " +key+ " pushed while in a menu");
                mc.thePlayer.sendChatMessage("/menu "+key);
                mc.displayGuiScreen(null);
				expired = true;
                //mc.func_6259_e();
                return;
            }
            
            if( Keyboard.getEventKey() == Keyboard.KEY_ESCAPE )
            {
            	mc.displayGuiScreen(null);
				expired = true;
                //mc.func_6259_e();
            }
        }
    }
}

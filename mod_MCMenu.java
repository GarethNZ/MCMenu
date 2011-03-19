package net.minecraft.src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;

public class mod_MCMenu extends BaseMod {
	private static MCMenuGui currentmenu;
	
	public mod_MCMenu()
	{
		ModLoader.SetInGameHook(this,
                true,
                true); // every tick NOT frame
        
		//currentmenu = new MCMenuGui();
		
	}
	
	@Override
	public String Version() {
		return "0.1a";
	}
	
	public void OnTickInGame(Minecraft game)
	{
		if( currentmenu != null && !currentmenu.expired)
		{
			if(Keyboard.isKeyDown(Keyboard.KEY_GRAVE) )
			{
				game.displayGuiScreen(currentmenu);
			}
			return;
		}
		
		// Check text messages :|
		try {
			@SuppressWarnings("unchecked")
			ArrayList<ChatLine> chatLog = (ArrayList<ChatLine>) ModLoader.getPrivateValue(GuiIngame.class, game.ingameGUI, "e"); // 'chatMessageList' obf to 'e'
			
			// Chats are stored in reverse order
			//Collections.reverse(chatLog);
			
			if( chatLog.size() > 0 )
			{
					String title = "Test Menu";
					LinkedList<String> options = new LinkedList<String>();
					
					for(int m = 0; m < chatLog.size(); m++)
					{
						ChatLine chat = chatLog.get(m); 
						if( isDigit(chat.message.charAt(0)) ) // 0,9,8....1 Title
						{
							options.add(chat.message);
							for(; m < chatLog.size() && options.size() < 10; m++)
							{
								options.add(chatLog.get(m).message);
							}
						}
						else
						{
							if( options.size() > 1 )
								title = chat.message;
							break;
						}
							
					}
					if( options.size() < 1 ) return;
					Collections.reverse(options);
					String[] o = new String[1];
					o = options.toArray(o);
					currentmenu = new MCMenuGui(title, o);
					game.displayGuiScreen(currentmenu);
			}
		} catch (IllegalArgumentException e) {
			String title = "IllegalArgumentException";
			String[] options = new String[1];
			options[0] = "Sad Face";
			currentmenu = new MCMenuGui(title, options);
			game.displayGuiScreen(currentmenu);
			e.printStackTrace();
		} catch (SecurityException e) {
			String title = "SecurityException";
			String[] options = new String[1];
			options[0] = "Sad Face";
			currentmenu = new MCMenuGui(title, options);
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			String title = "NoSuchFieldException";
			String[] options = new String[1];
			options[0] = "Sad Face";
			currentmenu = new MCMenuGui(title, options);
			e.printStackTrace();
		}
		
		
		
	}
	
	// Helper
	public boolean isDigit(char c)
	{
		if( c >= '1' && c <= '0')
			return true;
		return false;
	}
	
	

}

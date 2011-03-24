package net.minecraft.src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;

public class mod_MCMenu extends BaseMod {
	private static GuiScreen currentmenu;
	private boolean serverNotified;
	
	public mod_MCMenu()
	{
		ModLoader.SetInGameHook(this,
                true,
                true); // every tick NOT frame
        serverNotified = false;
		//currentmenu = new MCMenuGui();
		
	}
	
	@Override
	public String Version() {
		return "0.1a";
	}
	
	public void OnTickInGame(Minecraft game)
	{
		if( !serverNotified && game.isMultiplayerWorld() )
		{
			game.thePlayer.sendChatMessage("/menu modinstalled");
			serverNotified = true;
		}
		
		// Check text messages :|
		try {
			@SuppressWarnings("unchecked")
			ArrayList<ChatLine> chatLog = (ArrayList<ChatLine>) ModLoader.getPrivateValue(GuiIngame.class, game.ingameGUI, "e"); // 'chatMessageList' obf to 'e'
			
			// Chats are stored in reverse order
			Collections.reverse(chatLog);
			
			if( chatLog.size() > 0 )
			{
					String title = "Test Menu";
					LinkedList<String> options = new LinkedList<String>();
					int firstIndex = 0, lastIndex = 0;
					for(int m = 0; m < chatLog.size(); m++)
					{
						ChatLine chat = chatLog.get(m); 
						// 0,9,8....1 Title
						if( chat.message.startsWith("##Menu_") ) // Titile row
						{
							title = chat.message.substring(8); // Title in message before
							firstIndex = m; // also remove title
							
							/*
							// temp
							options.add(chat.message + "("+chat.message.indexOf("1")+")");
							m++;
							//end temp
							*/
							for(; m < chatLog.size() && options.size() < 10; m++)
							{
								chat = chatLog.get(m);
								if( startsWithDigit(chat.message) )
									options.add(chat.message);
								//else
								//	break;
							}
							// Check for end to menu.. else 
							chat = chatLog.get(m);
							if( options.size() < 1 || !chat.message.startsWith("##EndMenu") )
							{
								// we havent got it all yet..
								Collections.reverse(chatLog);
								return;
							}
								
							
							lastIndex = m;
							
							//ModLoader.setPrivateValue(GuiIngame.class, game.ingameGUI, "e", chatLog	); // 'chatMessageList' obf to 'e'
							//Collections.reverse(options);
							String[] o = new String[1];
							o = options.toArray(o);
							currentmenu = new MCMenuGui(title, o);
							game.displayGuiScreen(currentmenu);
							break;
						}
						else if( chat.message.startsWith("##Value_") )
						{
							title = chat.message.substring(9);
							currentmenu = new MCMenuValueGui(title);
							game.displayGuiScreen(currentmenu);
						}
							
					}
					
					for(;lastIndex >= firstIndex; lastIndex--)
					{
						chatLog.remove(lastIndex);
					}
					Collections.reverse(chatLog);
					
			
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
	
	// I think UTF encoding is screwing up charAt stuff
	public boolean startsWithDigit(String s)
	{
		return s.matches(".?[0-9].*");
	}
	
	/*public boolean startsWithDigit(String s)
	{
		if( isDigit(s.charAt(0)) ) // No colour prefix
			return true;
		else if( isDigit(s.charAt(1)) ) // dunno prefix
			return true;
		else if( isDigit(s.charAt(2)) ) // dunno prefix
			return true;
		else
			return isDigit(s.charAt(3)); // colour prefix
		
	}*/
	// Helper
	public boolean isDigit(char c)
	{
		if( c >= '1' && c <= '0')
			return true;
		return false;
	}
	
	

}

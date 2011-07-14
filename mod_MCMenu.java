package net.minecraft.src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;
import java.util.HashMap;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;

public class mod_MCMenu extends BaseMod {

	private static GuiScreen currentmenu;
	private boolean serverNotified;
	/*Menu key index*/
	HashMap<Integer,String> quickKeys;
	
	public mod_MCMenu()
	{
		ModLoader.SetInGameHook(this,
                true,
                true); // every tick NOT frame
        serverNotified = false;
		//currentmenu = new MCMenuGui();
		//RegisterKey(BaseMod basemod, KeyBinding keybinding, boolean flag)
		
	}

	private void loadSettings()
	{
		Minecraft game = ModLoader.getMinecraftInstance();
		quickKeys = new HashMap<Integer,String>();
		
		File path = Minecraft.getAppDir("minecraft/mods/"+"MCMenu"+"/");
		if (! path.exists())
		{
			path.mkdirs();
		}
		File file = new File(path,"guiconfig.properties");
		Properties p = new Properties();
		try
		{
			p.load(new FileInputStream(file));
			for( String keyStr : p.stringPropertyNames() )
			{
				if( keyStr != null )
				{
					keyStr = keyStr.toUpperCase();
					// String to Key
					int key;
					if (keyStr.equals("UNBOUND"))
						key = Keyboard.KEY_NONE;
					else
						key = Keyboard.getKeyIndex(keyStr);
					
					if( key == 0 ) continue; // skip
					
					// Add command and key to something
					String command = (String)p.get(keyStr);
					quickKeys.put(new Integer(key), command);
					//game.thePlayer.sendChatMessage("Added " + keyStr + ":"+new Integer(key).toString()+" "+command);
				}
			}
			
		}
		catch (Exception e) {
			System.out.println(e);
			return;
		}
		
		// TEMP
		// Register ALLLLLL Keys:
		for(char c = 8; c < 128; c++)
		{
			KeyBinding binding = new KeyBinding("Dynamic Bindings", c);
			ModLoader.RegisterKey(this, binding, false); // false for non-repeat I think
		}
		
		
		/*for(Integer key : quickKeys.keySet() )
		{
			game.thePlayer.sendChatMessage(key.toString() + " monitored");
		}*/
		
	}
	
	@Override
	public String Version() {
		return "0.1a";
	}
	
	@Override
	public void KeyboardEvent(KeyBinding keybinding)
    {
		Minecraft game = ModLoader.getMinecraftInstance();
        if (game.currentScreen==null )
		{
			for(Integer key : quickKeys.keySet() )
			{
				if( Keyboard.isKeyDown(key.intValue()) )
				{
					game.thePlayer.sendChatMessage(quickKeys.get(key));
				}
			}
		}
    }
	
	public boolean OnTickInGame(Minecraft game)
	{
		if( game.isMultiplayerWorld() )
		{
			if( !serverNotified )
			{
				game.thePlayer.sendChatMessage("/menu modinstalled");
				
				loadSettings();
				
				serverNotified = true;
			}
		}
		else 
			serverNotified = false;
		
		
				
		// Check text messages :|
		try {
			@SuppressWarnings("unchecked")
			ArrayList<ChatLine> chatLog = (ArrayList<ChatLine>) ModLoader.getPrivateValue(GuiIngame.class, game.ingameGUI, "e"); // 'chatMessageList' obf to 'e'
			
			// Chats are stored in reverse order
			Collections.reverse(chatLog);
			
			if( chatLog.size() > 0 )
			{
					String title = "Menu";
					LinkedList<String> options = new LinkedList<String>();
					int firstIndex = -1, lastIndex = -1;
					for(int m = 0; m < chatLog.size(); m++)
					{
						ChatLine chat = chatLog.get(m); 
						// 0,9,8....1 Title
						if( chat.message.startsWith("##Menu_") ) // Titile row
						{
							title = chat.message.substring(7); // Title in message before
							firstIndex = m; // also remove title
							m++;
							for(; m < chatLog.size() && options.size() < 10; m++)
							{
								chat = chatLog.get(m);
								if( startsWithDigit(chat.message) )
									options.add(chat.message);
								else
									break;
							}
							
							if( options.size() < 1 ||  // no options
								m >= chatLog.size() || // End of messages
								!chatLog.get(m).message.startsWith("##EndMenu") ) // last option != EndMenu
							{
								// we havent got it all yet..
								Collections.reverse(chatLog);
								return true;
							}
							lastIndex = m;
							
							String[] o = new String[1];
							o = options.toArray(o);
							currentmenu = new MCMenuGui(title, o);
							game.displayGuiScreen(currentmenu);
							
							break;
						}
						else if( chat.message.startsWith("##Value_") )
						{
							firstIndex = m;
							lastIndex = m;
							title = chat.message.substring(8);
							currentmenu = new MCMenuValueGui(title);
							game.displayGuiScreen(currentmenu);
							break;
						}
							
					}
					
					if(firstIndex >= 0 )
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
		
		
		return true;
	}
	
	// I think UTF encoding is screwing up charAt stuff
	public boolean startsWithDigit(String s)
	{
		return s.matches(".?[0-9].*");
	}
	
	// Helper
	public boolean isDigit(char c)
	{
		if( c >= '1' && c <= '0')
			return true;
		return false;
	}
	
	

}

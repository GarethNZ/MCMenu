/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Mouse;
import java.util.Random;

public class mod_MCMenu extends BaseMod { // implements Runnable
	private Minecraft game; 

	/*Menu Showing*/
	public boolean iMenu = false;

	/*Display anything at all, menu, etc..*/
	private boolean enabled = true;

	/*Was mouse down last render?*/
	private boolean lfclick = false;

	/*Current build version*/
	public String modver = "v0.1a";

	/*Menu input string*/
	private String inStr = "";

	/*Holds error exceptions thrown*/
	private String error = "";

	/*Strings to show for menu*/
	private String[] sMenu = new String[8];
	
	// TEMP
	//Menu key index
	private int menuKey = Keyboard.KEY_P;
	// END TEMP

	/*Time remaining to show error thrown for*/
	private int ztimer = 0;

	/*Key entry interval*/
	private int fudge = 0;

	/*Menu level for next render*/
	private int next = 0;

	/*Cursor blink interval*/
	private int blink = 0;

	/*Last key down on previous render*/
	private int lastKey= 0;

	/*Setting file access*/
	//private File settingsFile;


	/*Is the scrollbar being dragged?*/
	private boolean scrClick = false;

	/*Scrollbar drag start position*/
	private int scrStart = 0;

	/*Scrollbar offset*/
	private int sMin = 0;

	/*Scrollbar size*/
	private int sMax = 67;

	/*1st waypoint entry shown*/
	private int min = 0;


	//TODO: update
			/*Polygon creation class*/
			private kv lDraw = kv.a;
		
			/*Font rendering class*/
			private oi lang;
		
			/*Render texture*/
			private hf renderEngine;


			public static File getAppDir(String app)
			{
				return Minecraft.a(app);
			}

			public void chatInfo(String s) {
				game.t.a(s);
			}
			public String getMapName()
			{
				return game.e.q.j();
			}
			public String getServerName()
			{
				return game.x.B;
			}
			public void drawPre()
			{
				lDraw.b();
			}
			public void drawPost()
			{
				lDraw.a();
			}
			public void glah(int g)
			{
				renderEngine.a(g);
			}
			public void ldrawone(int a, int b, double c, double d, double e)
			{
				lDraw.a(a, b, c, d, e);
			}
			public void ldrawtwo(double a, double b, double c)
			{
				lDraw.a(a, b, c);
			}
			public void ldrawthree(double a, double b, double c, double d, double e)
			{
				lDraw.a(a, b, c, d, e);
			}
			public int getMouseX(int scWidth)
			{
				return Mouse.getX()*(scWidth+5)/game.c;
			}
			public int getMouseY(int scHeight)
			{
				return (scHeight+5) - Mouse.getY() * (scHeight+5) / this.game.d - 1;
			}
			public void setMenuNull()
			{
				game.q=null;
			}
			public Object getMenu()
			{
				return game.q;
			}
			public void OSDHook(Minecraft mc,boolean inmenu) {
				if(game==null) game = mc;

				if(renderEngine==null) renderEngine = this.game.o;

				mt scSize = new mt(game.c, game.d);
				int scWidth = scSize.a();
				int scHeight = scSize.b();

				if (Keyboard.isKeyDown(menuKey) && this.game.q==null) {
					this.iMenu = true;
					this.game.a(new cb());
				}

				
				
				if ((this.game.q instanceof lo) || (this.game.g.p==-1))
					this.enabled=false;
				else this.enabled=true;

				scWidth -= 5;
				scHeight -= 5;

				if ((!this.error.equals("")) && (this.ztimer == 0)) this.ztimer = 500;

				if (this.ztimer > 0) this.ztimer -= 1;

				if (this.fudge > 0) this.fudge -= 1;

				if ((this.ztimer == 0) && (!this.error.equals(""))) this.error = "";

				if (this.enabled) {
					GL11.glDisable(2929);
					GL11.glEnable(3042);
					GL11.glDepthMask(false);
					GL11.glBlendFunc(770, 0);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					
					if (ztimer > 0)
						this.write(this.error, 20, 20, 0xffffff);

					if (this.iMenu) showMenu(scWidth, scHeight);

					GL11.glDepthMask(true);
					GL11.glDisable(3042);
					GL11.glEnable(2929);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				}
			}
			private int chkLen(String paramStr) {
				return this.lang.a(paramStr);
			}

			private void write(String paramStr, int paramInt1, int paramInt2, int paramInt3) {
				this.lang.a(paramStr, paramInt1, paramInt2, paramInt3);
			}

			private int xCoord() {
				return (int)(this.game.g.aI < 0.0D ? this.game.g.aI - 1 : this.game.g.aI);
			}

			private int yCoord() {
				return (int)(this.game.g.aK < 0.0D ? this.game.g.aK - 1 : this.game.g.aK);
			}

			private int zCoord() {
				return (int)this.game.g.aJ;
			}

			private float radius() {
				return this.game.g.aO;
			}

			private String dCoord(int paramInt1) {
				if(paramInt1<0)
					return "-" + Math.abs(paramInt1+1);
				else
					return "+" + paramInt1;
			}

			private int tex(BufferedImage paramImg) {
				return this.renderEngine.a(paramImg);
			}

			private int img(String paramStr) {
				return this.renderEngine.a(paramStr);
			}

			private void disp(int paramInt) {
				this.renderEngine.b(paramInt);
			}
			public dt getWorld()
			{
				return game.e;
			}

	public static mod_MCMenu instance;

	public mod_MCMenu() {
		instance=this;
		
		// Clear menu
		for(int n = 0; n<8; n++)
			this.sMenu[n] = "";
		
		this.sMenu[0] = "Options";
		this.sMenu[1] = "Number 1:";
		this.sMenu[2] = "Number 2:";
		this.sMenu[3] = "Number 3:";
		this.sMenu[4] = "Number 4:";
		this.sMenu[5] = "Number 5:";
		this.sMenu[6] = "Number 6:";
		
		/*settingsFile = new File(getAppDir("minecraft"), "zan.settings");

		try {
			if(settingsFile.exists()) {
				BufferedReader in = new BufferedReader(new FileReader(settingsFile));
				String sCurrentLine;

				while ((sCurrentLine = in.readLine()) != null) {
					String[] curLine = sCurrentLine.split(":");

					if(curLine[0].equals("Show Minimap"))
						showmap = Boolean.parseBoolean(curLine[1]);
					else if(curLine[0].equals("Show Coordinates"))
						coords = Boolean.parseBoolean(curLine[1]);
					else if(curLine[0].equals("Dynamic Lighting"))
						lightmap = Boolean.parseBoolean(curLine[1]);
					else if(curLine[0].equals("Terrain Depth"))
						heightmap = Boolean.parseBoolean(curLine[1]);
					else if(curLine[0].equals("Welcome Message"))
						welcome = Boolean.parseBoolean(curLine[1]);
					else if(curLine[0].equals("Zoom Key"))
						zoomKey = Keyboard.getKeyIndex(curLine[1]);
					else if(curLine[0].equals("Menu Key"))
						menuKey = Keyboard.getKeyIndex(curLine[1]);
				}

				in.close();
			}
		} catch (Exception e) {}
		*/
	}
	
	private void showMenu (int scWidth, int scHeight) {
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		int option = 0;
		int maxSize = 0;
		int border = 2;
		boolean set = false;
		boolean click = false;
		int MouseX = getMouseX(scWidth);
		int MouseY = getMouseY(scHeight);

		if (Mouse.getEventButtonState() && Mouse.getEventButton() == 0)
			if (!this.lfclick) {
				set = true;
				this.lfclick = true;
			} else click = true;
		else if (this.lfclick) this.lfclick = false;

		String head = "Menu Options";
		String opt1 = "Exit Menu";
		
		if(this.iMenu) {
			head = this.sMenu[0];

			for(option=1; !(this.sMenu[option].equals("")); option++)
				if (this.chkLen(sMenu[option])>maxSize) maxSize = this.chkLen(sMenu[option]);
		}

		int title = this.chkLen(head);
		int centerX = (int)((scWidth+5)/2.0D);
		int centerY = (int)((scHeight+5)/2.0D);
		int footer = 80;
		GL11.glDisable(3553);
		GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.7f);
		double leftX = centerX - title/2.0D - border;
		double rightX = centerX + title/2.0D + border;
		double topY = centerY - (option-1)/2.0D*10.0D - border - 20.0D;
		double botY = centerY - (option-1)/2.0D*10.0D + border - 10.0D;
		this.drawBox(leftX, rightX, topY, botY);

		if(this.iMenu) {
			leftX = centerX - maxSize/2.0D - border;
			rightX = centerX + maxSize/2.0D + border;
			topY = centerY - (option-1)/2.0D*10.0D - border;
			botY = centerY + (option-1)/2.0D*10.0D + border;
			this.drawBox(leftX, rightX, topY, botY);
			leftX = centerX - footer/2.0D - border;
			rightX = centerX + footer/2.0D + border;
			topY = centerY + (option-1)/2.0D*10.0D - border + 10.0D;
			botY = centerY + (option-1)/2.0D*10.0D + border + 20.0D;
			this.drawBox(leftX, rightX, topY, botY);
		}

		GL11.glEnable(3553);
		this.write(head, centerX - title/2, (centerY - (option-1)*10/2) - 19, 0xffffff);

		if(this.iMenu) {
			for(int n=1; n<option; n++)
				this.write(this.sMenu[n], centerX - maxSize/2, ((centerY - (option-1)*10/2) + (n * 10))-9, 0xffffff);
		}

	}

	private void drawBox(double leftX, double rightX, double topY, double botY) {
		// Temp disable
		/*
		drawPre();
		ldrawtwo(leftX, botY, 0.0D);
		ldrawtwo(rightX, botY, 0.0D);
		ldrawtwo(rightX, topY, 0.0D);
		ldrawtwo(leftX, topY, 0.0D);
		drawPost();
		*/
	}
	
	private void drawOptions(double rightX,double topY,int MouseX,int MouseY,boolean set,boolean click) {
		if(this.iMenu) {
			if(min<0) min = 0;

			if(!Mouse.isButtonDown(0) && scrClick) scrClick = false;

			if (MouseX>(rightX-10) && MouseX<(rightX-2) && MouseY>(topY+1) && MouseY<(topY+10)) {
				if(set || click) {
					if(set&&min>0) min--;

					GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.7f);
				} else GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
			} else
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.3f);

			drawPre();
			ldrawtwo(rightX-10, topY+10, 0.0D);
			ldrawtwo(rightX-2, topY+10, 0.0D);
			ldrawtwo(rightX-6, topY+1, 0.0D);
			ldrawtwo(rightX-6, topY+1, 0.0D);
			drawPost();

			sMin = 0;
			sMax = 67;
			
			if (MouseX>rightX-10 && MouseX<rightX-2 && MouseY>topY+12+sMin && MouseY<topY+12+sMin+sMax || scrClick) {
				if(Mouse.isButtonDown(0)&&!scrClick) {
					scrClick = true;
					scrStart = MouseY;
				} else 
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
			} else {
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.3f);
			}

			this.drawBox(rightX-10, rightX-2, topY+12+sMin, topY+12+sMin+sMax);

			if (MouseX>rightX-10 && MouseX<rightX-2 && MouseY>topY+81 && MouseY<topY+90) {
				if(set || click) {
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.7f);
				} else GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
			} else
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.3f);

			drawPre();
			ldrawtwo(rightX-6, topY+90, 0.0D);
			ldrawtwo(rightX-6, topY+90, 0.0D);
			ldrawtwo(rightX-2, topY+81, 0.0D);
			ldrawtwo(rightX-10, topY+81, 0.0D);
			drawPost();
		}

		double leftX = rightX - 30;
		double botY = 0;
		topY+=1;
		int max = min+9;

		double leftCl = 0;
		double rightCl = 0;

		min = 0;
		max = 6;
		
		for(int i = min; i<max; i++) {
			if(i>min) topY += 10;

			botY = topY + 9;

			if (MouseX>leftX && MouseX<rightX && MouseY>topY && MouseY<botY )
				if (set || click) {
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				} else GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.6f);
			
			this.drawBox(leftX, rightX, topY, botY);

		}
	}


	private int drawFooter(int centerX,int centerY,int m, String opt1, String opt2, String opt3, int border,int MouseX,int MouseY,boolean set,boolean click) {
		int footer = this.chkLen(opt1);

		if (this.chkLen(opt2) > footer) footer = this.chkLen(opt2);

		double leftX = centerX - footer - border*2 - 5;
		double rightX = centerX - 5;
		double topY = centerY + (m-1)/2.0D*10.0D - border + 10.0D;
		double botY = centerY + (m-1)/2.0D*10.0D + border + 20.0D;

		if (this.iMenu) {
			if (this.chkLen(opt3) > footer) footer = this.chkLen(opt3);

			leftX = centerX - border*3 - footer*1.5 - 5;
			rightX = centerX - footer/2 - border - 5;
		}

		GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.7f);

		leftX = centerX - footer/2 - border;
		rightX = centerX + footer/2 + border;
		
		if (MouseX>leftX && MouseX<rightX && MouseY>topY && MouseY<botY )
			if (set || click) {
				if(set) {
					this.next=3;
				}

				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			} else GL11.glColor4f(0.5f, 0.5f, 0.5f, 0.7f);
		else GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.7f);

		this.drawBox(leftX, rightX, topY, botY);

		rightX = centerX + border*3 + footer*1.5 + 5;
		leftX = centerX + footer/2 + border + 5;

		GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.7f);

		
		return footer/2;
	}

	@Override
	public String Version() {
		return "1.3_01 - "+modver;
	}
}

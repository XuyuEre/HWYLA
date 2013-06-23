package mcp.mobius.waila.gui.widget;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;

public class ContainerTable extends BaseWidget{
	int rowHeight;
	int ncolumns;
	FontRenderer fontRender;
	int[]  columnPos;
	int[]  columnWidth;
	ArrayList<IWidget[]> table               = new ArrayList<IWidget[]>();	
	Label[] columnnames;
	
    /** where the mouse was in the window when you first clicked to scroll */
    public float initialClickY = -2.0F;	
    
    public ContainerTable(GuiScreen parent){
    	this.parent = parent;
    };
    
	public ContainerTable( GuiScreen parent, int height, int ncolumns, String[] columnnames) {
		this.parent = parent;
        this.height  = height;
        this.rowHeight = 8;
        this.fontRender = Minecraft.getMinecraft().fontRenderer;
        this.ncolumns = ncolumns;
        this.columnPos   = new int[this.ncolumns];
        this.columnWidth = new int[this.ncolumns];
        this.columnnames = new Label[this.ncolumns];
        
        for (int i = 0; i < this.ncolumns; i++)
        	this.columnnames[i] = new Label(columnnames[i]);
	}

	public void setColumns(int rowHeight, String... header){
		this.rowHeight   = rowHeight;
		this.ncolumns    = header.length;
        this.columnPos   = new int[this.ncolumns];
        this.columnWidth = new int[this.ncolumns];
        this.columnnames = new Label[this.ncolumns];
        
        for (int i = 0; i < this.ncolumns; i++)
        	this.columnnames[i] = new Label(header[i]);        
	}
	
	public int getSize() {
		return this.table.size();
	}

	public void addRow(IWidget... data){
		this.table.add(data);
		for (int i = 0; i < ncolumns; i++){
			this.columnWidth[i] = Math.max(data[i].getWidth() + 8, this.columnWidth[i]);
			this.columnWidth[i] = Math.max(this.columnnames[i].getWidth() + 8, this.columnWidth[i]);			
		}
		
        this.columnPos[0] = 0;
        for (int i = 1; i < this.ncolumns; i++)
        	this.columnPos[i] = this.columnPos[i-1] + this.columnWidth[i-1];		
	}

	@Override
	public void draw() {
		this.drawTitle(this.posX, this.posY);
		
    	for (int i = 0; i < this.table.size(); i++)
    		this.drawRow(i, this.posX, this.posY+(i+1)*this.rowHeight);		
	}	

	@Override
	public int getWidth(){
		int width = 0;
		for (int i: this.columnWidth)
			width += i;
		return width;
	}

	@Override
	public int getHeight(){
		return this.rowHeight * (this.getSize() + 1);
	}	
	
	public void drawTitle(int rowleft, int rowtop)
	{
		for (int j = 0; j < this.ncolumns; j++){
			this.columnnames[j].setWidth(this.columnWidth[j]);
			this.columnnames[j].draw(rowleft + this.columnPos[j], rowtop, 0);
		}		
	}
	
	public void drawRow(int index, int rowleft, int rowtop)
	{
		if (index%2 == 0)
			this.parent.drawGradientRect(rowleft, rowtop, rowleft+this.getWidth(), rowtop+this.rowHeight, 0xff333333, 0xff333333);
		else
			this.parent.drawGradientRect(rowleft, rowtop, rowleft+this.getWidth(), rowtop+this.rowHeight, 0xff000000, 0xff000000);			
		
		IWidget[] data = this.table.get(index);
		for (int j = 0; j < this.ncolumns; j++){
			data[j].setWidth(this.columnWidth[j]);
			data[j].draw(rowleft + this.columnPos[j], rowtop, 0);
		}
			
	}	
}

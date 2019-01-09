package screens;

import java.util.ArrayList;
import java.util.List;

import apimodel.UserProfile;
import gameplay.ArrowsButtonsManager;
import processing.MariellaSaysMain;
import processing.core.PApplet;

public class SelectPlayerScreen implements PScreen{

	private PApplet parent;
	private List<UserProfile> userProfiles;
	private int selectedPlayer;
	private List<ArrowsButtonsManager> arrows;
	
	
	public SelectPlayerScreen(PApplet parent) {
		arrows = new ArrayList<>();
		this.parent = parent;
		arrows.add(new ArrowsButtonsManager(MariellaSaysMain.UP));
		arrows.add(new ArrowsButtonsManager(MariellaSaysMain.DOWN));
		arrows.add(new ArrowsButtonsManager(MariellaSaysMain.LEFT));
		arrows.add(new ArrowsButtonsManager(MariellaSaysMain.RIGHT));
	}
	
	@Override
	public void setup() {
		selectedPlayer = 0;
		parent.textSize(22);
		parent.noFill();
		
	}

	@Override
	public void draw() {
		parent.background(240);

		int index = 0;
		
		
		for(UserProfile up: userProfiles)
		{
			int rectX = 80 + 280 * (index / 3) ;
			int rectY = 130 + 190 * (index % 3);
			
			parent.strokeWeight(4);
			
			
			if(index == selectedPlayer)
				parent.strokeWeight(8);
			
			parent.rect(rectX, rectY, 250, 70, 7);
			parent.text(up.getUsername(), rectX + 20, rectY + 55);
			
					
			index++;
		}
		
	}

	@Override
	public void keyPressed() {
		
		
		if(parent.key == MariellaSaysMain.CODED)
			for(ArrowsButtonsManager arrow : arrows)
			{
				int steps = arrow.hasToMove(parent.keyCode, parent.millis());
			
				if (steps == 0)
					continue;
				selectedPlayer += steps;
				
				if(selectedPlayer >= userProfiles.size())
					selectedPlayer %= userProfiles.size();
			
				if(selectedPlayer < 0)
					selectedPlayer = userProfiles.size() + selectedPlayer;
			
			}
		
	}

	@Override
	public void keyReleased() {
	
		if(parent.key == MariellaSaysMain.CODED)
			for(ArrowsButtonsManager arrow : arrows)
			{
				arrow.reset(parent.keyCode);
			}
		
	}

	public void setUserProfiles(List<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}

	
	
}

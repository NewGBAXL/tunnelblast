package com.newgbaxl.blastmaze;

import com.badlogic.gdx.Game;
import com.newgbaxl.blastmaze.multiplayer.StreamController;

public class MazeGame extends Game {
	int carSkin = -1;
	int special = 0;
	int scenarioID = -1;
	int vsMode = 0;

	MazeGame(){
		super();
	}

	//where different game modes will be called
	MazeGame(int nCarSkin, int nSpecial, int nScenarioID, int nVsMode){
		super();
		carSkin = nCarSkin;
		special = nSpecial;
		scenarioID = nScenarioID;
		vsMode = nVsMode;
	}

	@Override
	public void create () {
		if (vsMode == 2)
			this.setScreen(new StreamController());
		else if (carSkin == -1)
			this.setScreen(new MazeScreen2d(-1, special, scenarioID, (vsMode==1)));
		else if (scenarioID == -1)
			this.setScreen(new MazeScreen2d(carSkin, special, (vsMode==1)));
		else
			this.setScreen(new MazeScreen2d(carSkin, special, scenarioID, (vsMode==1)));
	}

	@Override
	public void render () {
		super.render();
	}
}

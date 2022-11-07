package com.newgbaxl.blastmaze;

import com.badlogic.gdx.Game;

public class MazeGame extends Game {
	int carSkin = -1;
	int special = 0;
	int scenarioID = -1;

	MazeGame(){
		super();
	}

	//where different game modes will be called
	MazeGame(int nCarSkin, int nSpecial, int nScenarioID){
		super();
		carSkin = nCarSkin;
		special = nSpecial;
		scenarioID = nScenarioID;
	}

	@Override
	public void create () {
		if (carSkin == -1)
			this.setScreen(new MazeScreen2d());
		else if (scenarioID == -1)
			this.setScreen(new MazeScreen2d(carSkin, special));
		else
			this.setScreen(new MazeScreen2d(carSkin, special, scenarioID));
	}

	@Override
	public void render () {
		super.render();
	}
}

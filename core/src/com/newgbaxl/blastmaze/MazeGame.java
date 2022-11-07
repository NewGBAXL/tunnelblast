package com.newgbaxl.blastmaze;

import com.badlogic.gdx.Game;

public class MazeGame extends Game {

	MazeGame(){
		super();
	}

	//where different game modes will be called
	MazeGame(int mode){
		super();
	}

	@Override
	public void create () {
		this.setScreen(new MazeScreen2d());
	}

	@Override
	public void render () {
		super.render();
	}
}

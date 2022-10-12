package com.newgbaxl.blastmaze;


public class MazeMapRenderer extends MazeTileRenderer {

	public MazeMapRenderer(Maze maze, String wallFile, int magnify) {
		super(maze, wallFile);
		this.mazeMagnifyToWorld = magnify;
	}
}

package com.kry.brickgame;

public interface IGameListener {
	
    public void boardChanged(GameEvent event);
    public void previewChanged(GameEvent event);
    public void statusChanged(GameEvent event);
    public void infoChanged(GameEvent event);
}

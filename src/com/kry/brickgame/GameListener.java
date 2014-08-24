package com.kry.brickgame;

import java.util.EventListener;

public interface GameListener extends EventListener{
	
    public void boardChanged(GameEvent event);
    public void previewChanged(GameEvent event);
    public void statusChanged(GameEvent event);
    public void infoChanged(GameEvent event);
    public void speedChanged(GameEvent event);
    public void levelChanged(GameEvent event);
    
}

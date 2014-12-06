package com.kry.brickgame.UI;

import java.util.EventListener;


/**
 * @author noLive
 */
public interface GameListener extends EventListener {
	
	public void boardChanged(GameEvent event);
	
	public void exit(GameEvent event);
	
	public void infoChanged(GameEvent event);
	
	public void levelChanged(GameEvent event);
	
	public void muteChanged(GameEvent event);
	
	public void previewChanged(GameEvent event);
	
	public void rotationChanged(GameEvent event);
	
	public void speedChanged(GameEvent event);
	
	public void statusChanged(GameEvent event);
	
}

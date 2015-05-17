package com.kry.brickgame.UI;

import java.util.EventListener;


/**
 * @author noLive
 */
public interface GameListener extends EventListener {
	
	void boardChanged(GameEvent event);
	
	void exit(GameEvent event);
	
	void infoChanged(GameEvent event);
	
	void levelChanged(GameEvent event);
	
	void muteChanged(GameEvent event);
	
	void previewChanged(GameEvent event);
	
	void rotationChanged(GameEvent event);
	
	void speedChanged(GameEvent event);
	
	void statusChanged(GameEvent event);
	
}

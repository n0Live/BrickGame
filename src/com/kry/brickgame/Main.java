package com.kry.brickgame;

import java.awt.EventQueue;

/**
 * Основной класс для запуска
 * @author noLive
 *
 */
public final class Main{

	/**
	 * Текущая игра 
	 */
	private static Game game;
	/**
	 * Текущий поток игры 
	 */
	private static Thread gameThread;
	/**
	 * Диалог выбора игры
	 */
	public static GameSelector gameSelector = new GameSelector();
	/**
	 * Подписчик на события нажатия кнопок
	 */
	public static GameKeyAdapter gameKeyAdapter = new GameKeyAdapter();

	public static Game getGame() {
		return game;
	}

	public static void setGame(Game game) {
		Main.game = game;
		gameThread = new Thread(game, "TGameThread");
		gameThread.start();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

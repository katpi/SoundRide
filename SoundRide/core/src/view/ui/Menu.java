package view.ui;

import java.util.ArrayList;
import java.util.List;

import model.helpers.AssetLoader;
import view.helpers.TextureLoader;

public class Menu {
	private static List<SimpleButton> menuButtons;
	public static SimpleButton playButton;

	public Menu() {
		menuButtons = new ArrayList<SimpleButton>();
		playButton = new SimpleButton(
				AssetLoader.buttonX, AssetLoader.buttonY, 60, 32, TextureLoader.playButtonUp,
				TextureLoader.playButtonDown);
		menuButtons.add(playButton);
	}

	public List<SimpleButton> getMenuButtons() {
		return menuButtons;
	}
}

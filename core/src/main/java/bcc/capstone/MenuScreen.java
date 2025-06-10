package bcc.capstone;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class MenuScreen extends ScreenAdapter{
    private Stage stage;
    private Skin skin;
    private Othello game;

    public MenuScreen(Othello game) {
        this.game = game;
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("skins/clean-crispy/clean-crispy-ui.json"));
        Gdx.input.setInputProcessor(stage);
        createMenu();
    }

    private void createMenu() {

        Texture backgroundTexture = new Texture(Gdx.files.internal("gray.png"));
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label titleLabel = new Label("Configure Game", skin);
        titleLabel.setFontScale(2);
        table.add(titleLabel).padBottom(50).row();

        // Add label and text field for number input
        Label inputLabel = new Label("Board Size:", skin);
        inputLabel.setFontScale(1.5f);
        final TextField boardSizeField = new TextField("8", skin); // default value "8"
        boardSizeField.setMessageText("Enter board size");
        table.add(inputLabel).padBottom(10).right();
        table.add(boardSizeField).width(100).padBottom(10).left().row();

        TextButton startButton = new TextButton("Start Game", skin);
        startButton.getLabel().setFontScale(2f);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int boardSize = 8; // default
                try {
                    boardSize = Integer.parseInt(boardSizeField.getText());
                } catch (NumberFormatException e) {
                    // Optionally show an error or keep default
                }
                game.setBoard(new Board(boardSize));
                game.setScreen(new GameScreen(game));
            }
        });
        table.add(startButton).colspan(2).fillX().uniformX().padBottom(10).row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}

package bcc.capstone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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


public class StartScreen extends ScreenAdapter{
 
        // Load the skin
        private Skin skin;
        Stage stage = new Stage();
    public StartScreen(Othello game) {
        
        Gdx.input.setInputProcessor(stage);
        

        skin = new Skin(Gdx.files.internal("skins/clean-crispy/clean-crispy-ui.json"));
        skin.getAtlas().getTextures().forEach(texture -> texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest));
        // Load the skin

        //load image
        Texture backgroundTexture = new Texture(Gdx.files.internal("gray.png"));//note that this is stored in assets directory
        TextureRegionDrawable backgroundDrawable =
                new TextureRegionDrawable(new TextureRegion(backgroundTexture));

        //title
        //Container<Label> titleLabel = Constants.createLabelWithBackgrounColor("Othello",Color.BLACK,  skin);
        // Buttons with the skin       
         
        TextButton playButton = new TextButton("Start Game", skin);
        TextButton simulateButton = new TextButton("Simulate Games", skin);
        
        //resize text on simulate button
        simulateButton.getLabel().setFontScale(2f);
        playButton.getLabel().setFontScale(2f);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setSimulated(false);
               //game.startPlayerSelection();
                //game.setScreen(new GameScreen(game));
                //game.skipCheckpoint1();
                game.setBoard(new Board(4));
                //game.setScreen(new GameScreen(game));
                game.setScreen(new MenuScreen(game));
            }
        });

        simulateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               //game.setSimulated(true);
                //game.startPlayerSelection();
                System.out.println("bbb");
            }
        });

        Table table = new Table();

        Label titleLabel = new Label("Othello with Friends!", skin);

        titleLabel.setFontScale(3);
        table.add(titleLabel).padBottom(50).row();

        table.setFillParent(true);
        table.center();
        //table.add(titleLabel).pad(10).row();
        table.add(playButton).pad(10).row();
        //table.add(simulateButton).pad(10).row();
        table.setBackground(backgroundDrawable);

        stage.addActor(table);
    }

    @Override
     public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}


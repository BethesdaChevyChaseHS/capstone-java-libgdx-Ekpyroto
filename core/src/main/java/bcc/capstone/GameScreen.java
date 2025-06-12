package bcc.capstone;

import org.w3c.dom.Text;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;


public class GameScreen extends ScreenAdapter{
    private Skin skin;
    private Stage stage = new Stage();
    private Othello game;
    private ImageButton[][] gridArray;
    private TextureRegionDrawable black;
    private TextureRegionDrawable white;
    private Piece curPlayer = Piece.BLACK; // Assuming the game starts



    public GameScreen(Othello game) {
        gridArray = new ImageButton[game.getBoard().getSize()][game.getBoard().getSize()];
        black = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Black.png"))));
        white = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("White.png"))));
        this.game = game;
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skins/clean-crispy/clean-crispy-ui.json"));
        skin.getAtlas().getTextures().forEach(texture -> texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest));


        Texture backgroundTexture = new Texture(Gdx.files.internal("gray.png"));
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Table table = new Table();
        table.setPosition(320,240);

        // Create grid/buttons ONCE
        for(int i = 0; i < game.getBoard().getSize(); i++) {
            for(int j = 0; j < game.getBoard().getSize(); j++) {
                // Clone the default style for each button
                ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle(skin.get(ImageButton.ImageButtonStyle.class));
                gridArray[i][j] = new ImageButton(style);
                int tempI = i;
                int tempJ = j;
                gridArray[i][j].addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        boolean temp = game.getBoard().move(tempI, tempJ, curPlayer);
                        if (temp) {
                            if(game.getBoard().hasLegalMove(curPlayer.opposite())) {
                                curPlayer = curPlayer.opposite();
                            }
                            else if(game.getBoard().hasLegalMove(curPlayer)){

                            }
                            else{
                                if(game.getBoard().checkScore(Piece.BLACK) > game.getBoard().checkScore(Piece.WHITE)) {
                                    TextButton winnerButton = new TextButton("Black Wins!", skin);
                                    winnerButton.setPosition(170, 205);
                                    winnerButton.setSize(300, 70);
                                    winnerButton.getLabel().setFontScale(2);

                                    winnerButton.addListener(new ClickListener() {
                                        @Override
                                        public void clicked(InputEvent event, float x, float y) {
                                            game.setScreen(new MenuScreen(game)); // Go back to start screen
                                        }
                                    });
                                    stage.addActor(winnerButton);
                                } else if(game.getBoard().checkScore(Piece.BLACK) < game.getBoard().checkScore(Piece.WHITE)) {
                                    TextButton winnerButton = new TextButton("White Wins!", skin);
                                    winnerButton.setPosition(170, 205);
                                    winnerButton.setSize(300, 70);

                                    winnerButton.getLabel().setFontScale(2);
                                    winnerButton.addListener(new ClickListener() {
                                        @Override
                                        public void clicked(InputEvent event, float x, float y) {
                                            game.setScreen(new MenuScreen(game)); // Go back to start screen
                                        }
                                    });
                                    stage.addActor(winnerButton);
                                } else {
                                    TextButton winnerButton = new TextButton("Draw!", skin);
                                    winnerButton.setPosition(170, 205);
                                    winnerButton.setSize(300, 70);

                                    winnerButton.getLabel().setFontScale(2);

                                    winnerButton.addListener(new ClickListener() {
                                        @Override
                                        public void clicked(InputEvent event, float x, float y) {
                                            game.setScreen(new MenuScreen(game)); // Go back to start screen
                                        }
                                    });
                                    stage.addActor(winnerButton);
                                }
                            }
                        }
                        updateAllButtons(); // Only update this button
                    } 
                });
                table.add(gridArray[i][j]).size(50,50);
            }
            table.row();
        }

        // Initial update of all buttons
        updateAllButtons();

        stage.addActor(table);
    }

    // Update only one button
    private void updateButton(int i, int j) {
        System.out.println("updateButton: (" + i + "," + j + ") = " + game.getBoard().getPiece(i, j));
        if(game.getBoard().getPiece(i, j) == Piece.BLACK) {
            gridArray[i][j].getStyle().imageUp = black;
        } 
        else if(game.getBoard().getPiece(i, j) == Piece.WHITE) {
            gridArray[i][j].getStyle().imageUp = white;
        }
        else {
            gridArray[i][j].getStyle().imageUp = null;
        }
        gridArray[i][j].invalidate();
    }

    // Update all buttons (call once at start)
    private void updateAllButtons() {
        for(int i = 0; i < game.getBoard().getSize(); i++) {
            for(int j = 0; j < game.getBoard().getSize(); j++) {
                updateButton(i, j);
            }
        }
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

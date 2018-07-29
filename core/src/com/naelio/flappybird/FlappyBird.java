package com.naelio.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {

    /**
     * ultilizada para criar animações
     */
    private SpriteBatch batch;
    private Texture bird;

    @Override
    public void create() {

        batch = new SpriteBatch();
        bird = new Texture("passaro1.png"); // recebe uma imagem como parametro
    }

    @Override
    public void render() {

        /**
         * Iniciando a exibição das nossas imagens
         */
        batch.begin();

        batch.draw(bird,300,0);

        batch.end();
        /**
         * Finalizando a exibição das nossas imagens
         */
    }

}

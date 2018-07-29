package com.naelio.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {

    /**
     * ultilizada para criar animações
     */
    private SpriteBatch batch;

    private Texture bird;
    private Texture background;

    private int eixo_x = 0;
    private int alturaDispositivo;
    private int larguraDispositivo;

    @Override
    public void create() {

        batch = new SpriteBatch();
        bird = new Texture("passaro1.png"); // recebe uma imagem como parametro
        background = new Texture("fundo.png");

        alturaDispositivo = Gdx.graphics.getHeight();
        larguraDispositivo = Gdx.graphics.getWidth();
    }

    @Override
    public void render() {

        eixo_x++;
        /**
         * Iniciando a exibição das nossas imagens
         */
        batch.begin();

        batch.draw(background, 0, 0, larguraDispositivo, alturaDispositivo);
        batch.draw(bird, eixo_x, 500);

        batch.end();
        /**
         * Finalizando a exibição das nossas imagens
         */
    }

}

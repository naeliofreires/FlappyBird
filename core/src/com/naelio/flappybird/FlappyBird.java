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

    private Texture[] birds;
    private Texture background;

    private int alturaDispositivo;
    private int larguraDispositivo;

    private int posicaoInicialVertical;
    private int posicaoInicialHorizontal;


    private float variacao = 0; // variável responsável por ficar alternando as imagens dos passaros

    @Override
    public void create() {

        batch = new SpriteBatch();
        birds = new Texture[3];

        birds[0] = new Texture("passaro1.png");
        birds[1] = new Texture("passaro2.png");
        birds[2] = new Texture("passaro3.png");

        background = new Texture("fundo.png"); // recebe uma imagem como parametro

        alturaDispositivo = Gdx.graphics.getHeight();
        larguraDispositivo = Gdx.graphics.getWidth();

        posicaoInicialHorizontal = 0;
        posicaoInicialVertical = alturaDispositivo / 2;
    }

    @Override
    public void render() {

        variacao += Gdx.graphics.getDeltaTime() * 10;

        if (variacao > 2)
            variacao = 0;

        if(posicaoInicialVertical != 0)
            posicaoInicialVertical--;

        /**
         * Iniciando a exibição das nossas imagens
         */
        batch.begin();

        batch.draw(background, 0, 0, larguraDispositivo, alturaDispositivo);

        batch.draw(birds[(int) variacao], posicaoInicialHorizontal, posicaoInicialVertical);

        batch.end();
        /**
         * Finalizando a exibição das nossas imagens
         */
    }

}

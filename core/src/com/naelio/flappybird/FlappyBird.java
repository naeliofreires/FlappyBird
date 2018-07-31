package com.naelio.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

    /**
     * ultilizada para criar animações
     */
    private SpriteBatch batch;

    private Texture[] birds;
    private Texture background;
    private Texture canoBaixo;
    private Texture canoTopo;
    private Random numeroRandomico;

    private int alturaDispositivo;
    private int larguraDispositivo;
    private int velocidadeQueda = 0;
    private int posicaoInicialVertical;
    private int posicaoInicialHorizontal;


    private float variacao = 0.0f; // variável responsável por ficar alternando as imagens dos passaros
    private float espacamentoCano;
    private float posicaoMovimentoCanoHorizontal;
    private float alturaEntreCanosRandomica;

    @Override
    public void create() {

        birds = new Texture[3];
        batch = new SpriteBatch();
        numeroRandomico = new Random();

        birds[0] = new Texture("passaro1.png");
        birds[1] = new Texture("passaro2.png");
        birds[2] = new Texture("passaro3.png");

        background = new Texture("fundo.png");

        canoTopo = new Texture("cano_topo_maior.png");
        canoBaixo = new Texture("cano_baixo_maior.png");

        // pegando a altura e largura da tela
        alturaDispositivo = Gdx.graphics.getHeight();
        larguraDispositivo = Gdx.graphics.getWidth();

        posicaoInicialHorizontal = 0;
        posicaoInicialVertical = alturaDispositivo / 2;

        espacamentoCano = 300f;
        posicaoMovimentoCanoHorizontal = larguraDispositivo - 100;
    }

    @Override
    public void render() {

        velocidadeQueda++;
        posicaoMovimentoCanoHorizontal -= Gdx.graphics.getDeltaTime() * 200;
        variacao += Gdx.graphics.getDeltaTime() * 10;

        if (variacao > 2)
            variacao = 0;

        if (Gdx.input.justTouched()) {
            velocidadeQueda = -20;
        }

        if (posicaoInicialVertical > 0 || velocidadeQueda < 0) {
            posicaoInicialVertical -= velocidadeQueda;
        }

        // verificando se o cano saiu inteiramente da tela
        if (posicaoMovimentoCanoHorizontal < -100){
            posicaoMovimentoCanoHorizontal = larguraDispositivo;
            alturaEntreCanosRandomica = numeroRandomico.nextInt(400) - 200;
        }

        /**
         * Iniciando a exibição das nossas imagens
         */
        batch.begin();

        batch.draw(background, 0, 0, larguraDispositivo, alturaDispositivo);

        batch.draw(canoTopo, posicaoMovimentoCanoHorizontal, alturaDispositivo/ 2 + (espacamentoCano/2) + alturaEntreCanosRandomica);
        batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - (espacamentoCano/2) + alturaEntreCanosRandomica);

        batch.draw(birds[(int) variacao], 120, posicaoInicialVertical);

        batch.end();
        /**
         * Finalizando a exibição das nossas imagens
         */
    }

}

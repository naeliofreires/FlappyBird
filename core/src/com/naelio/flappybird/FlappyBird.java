package com.naelio.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

    /**
     * ultilizada para criar animações
     */
    private SpriteBatch batch;
    private Random numeroRandomico;
    private BitmapFont font;

    private Texture[] birds;
    private Texture background;
    private Texture canoBaixo;
    private Texture canoTopo;

    private int pontuacao = 0;
    private int estadoJogo = 0;
    private int alturaDispositivo;
    private int larguraDispositivo;
    private int velocidadeQueda = 0;
    private int posicaoInicialVertical;
    private int posicaoInicialHorizontal;

    private float variacao = 0.0f; // variável responsável por ficar alternando as imagens dos passaros
    private float espacamentoCano;
    private float posicaoMovimentoCanoHorizontal;
    private float alturaEntreCanosRandomica;

    private boolean marcou = false;

    @Override
    public void create() {

        birds = new Texture[3];
        batch = new SpriteBatch();

        numeroRandomico = new Random();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(6);

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

        /**
         * responsável pela batida de asas
         */
        this.variacao();

        if (estadoJogo == 0) { // Jogo não iniciado
            if (Gdx.input.justTouched())
                estadoJogo = 1;
        } else {

            velocidadeQueda++;
            posicaoMovimentoCanoHorizontal -= Gdx.graphics.getDeltaTime() * 200;


            if (Gdx.input.justTouched()) {
                velocidadeQueda = -20;
            }

            if (posicaoInicialVertical > 0 || velocidadeQueda < 0) {
                posicaoInicialVertical -= velocidadeQueda;
            }

            if (this.canoForaDaTela()) {
                posicaoMovimentoCanoHorizontal = larguraDispositivo;
                alturaEntreCanosRandomica = numeroRandomico.nextInt(400) - 200;
                marcou = false;
            }

            if (posicaoMovimentoCanoHorizontal < 120) {
                if(!marcou){
                    pontuacao++;
                    marcou = true;
                }
            }

            /**
             * Iniciando a exibição das nossas imagens
             */
            batch.begin();

            batch.draw(background, 0, 0, larguraDispositivo, alturaDispositivo);

            batch.draw(canoTopo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 + (espacamentoCano / 2) + alturaEntreCanosRandomica);
            batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - (espacamentoCano / 2) + alturaEntreCanosRandomica);

            /**
             * Imagem dos passaros, batida de asas
             */
            batch.draw(birds[(int) variacao], 120, posicaoInicialVertical);
            font.draw(batch, String.valueOf(pontuacao), larguraDispositivo / 2, alturaDispositivo - 100);
            batch.end();
            /**
             * Finalizando a exibição das nossas imagens
             */
        }
    }

    private void variacao() {
        variacao += Gdx.graphics.getDeltaTime() * 10;

        /**
         * São apenas 3 imagens no array indo da posição [0,1,2], se passar do 2, voltar a zero.
         */
        if (variacao > 2) variacao = 0;
    }

    private boolean canoForaDaTela() {
        return posicaoMovimentoCanoHorizontal < -100;
    }

}

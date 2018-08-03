package com.naelio.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

    /**
     * ultilizada para criar animações
     */
    private SpriteBatch batch;
    private Random numeroRandomico;
    private BitmapFont font;
    private BitmapFont mensagem;

    private Circle passaroCirculo;
    private Rectangle rectangleCanobaixo;
    private Rectangle rectangleCanoAlto;
    private ShapeRenderer shape;

    private Texture[] birds;
    private Texture background;
    private Texture canoBaixo;
    private Texture canoTopo;
    private Texture gameOver;

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

        shape = new ShapeRenderer();
        passaroCirculo = new Circle();
        rectangleCanoAlto = new Rectangle();
        rectangleCanobaixo = new Rectangle();

        mensagem = new BitmapFont();
        mensagem.setColor(Color.WHITE);
        mensagem.getData().setScale(3);

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(6);
        numeroRandomico = new Random();

        birds[0] = new Texture("passaro1.png");
        birds[1] = new Texture("passaro2.png");
        birds[2] = new Texture("passaro3.png");

        background = new Texture("fundo.png");

        canoTopo = new Texture("cano_topo_maior.png");
        canoBaixo = new Texture("cano_baixo_maior.png");
        gameOver = new Texture("game_over.png");

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
        } else { // Jogo iniciado


            velocidadeQueda++;
            if (posicaoInicialVertical > 0 || velocidadeQueda < 0) {
                posicaoInicialVertical -= velocidadeQueda;
            }

            if (estadoJogo == 1) {

                posicaoMovimentoCanoHorizontal -= Gdx.graphics.getDeltaTime() * 200;

                if (Gdx.input.justTouched())
                    velocidadeQueda = -20;

                if (this.canoForaDaTela()) {
                    posicaoMovimentoCanoHorizontal = larguraDispositivo;
                    alturaEntreCanosRandomica = numeroRandomico.nextInt(400) - 200;
                    marcou = false;
                }

                if (posicaoMovimentoCanoHorizontal < 120) {
                    if (!marcou) {
                        pontuacao++;
                        marcou = true;
                    }
                }

            } else { // tela de gamer over
                if (Gdx.input.justTouched()) {
                    estadoJogo = 0;
                    pontuacao = 0;
                    velocidadeQueda = 0;
                    posicaoInicialVertical = alturaDispositivo / 2;
                    posicaoMovimentoCanoHorizontal = larguraDispositivo;
                }
            }


            /**
             * Iniciando a exibição das nossas imagens
             */
            batch.begin();

            batch.draw(background, 0, 0, larguraDispositivo, alturaDispositivo);

            batch.draw(canoTopo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 + (espacamentoCano / 2) + alturaEntreCanosRandomica);
            batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - (espacamentoCano / 2) + alturaEntreCanosRandomica);

            if (estadoJogo == 2) {
                batch.draw(gameOver, larguraDispositivo / 2 - gameOver.getWidth() / 2, alturaDispositivo / 2);
                mensagem.draw(batch, "Toque para reiniciar", larguraDispositivo / 2 - 200, alturaDispositivo / 2 - 150);
            }

            /**
             * Imagem dos passaros, batida de asas
             */
            batch.draw(birds[(int) variacao], 120, posicaoInicialVertical);
            font.draw(batch, String.valueOf(pontuacao), larguraDispositivo / 2, alturaDispositivo - 100);
            batch.end();
            /**
             * Finalizando a exibição das nossas imagens
             */

            passaroCirculo.set(120 + birds[0].getWidth() / 2, posicaoInicialVertical + birds[0].getHeight() / 2, birds[0].getWidth() / 2);
            rectangleCanobaixo.set(
                    posicaoMovimentoCanoHorizontal,
                    alturaDispositivo / 2 - canoBaixo.getHeight() - (espacamentoCano / 2) + alturaEntreCanosRandomica,
                    canoBaixo.getWidth(),
                    canoBaixo.getHeight()
            );

            rectangleCanoAlto.set(
                    posicaoMovimentoCanoHorizontal,
                    alturaDispositivo / 2 + (espacamentoCano / 2) + alturaEntreCanosRandomica,
                    canoTopo.getWidth(),
                    canoTopo.getHeight()
            );
            /**
             * Desenhando formas
             */
//            shape.begin(ShapeRenderer.ShapeType.Filled);
//            shape.circle(passaroCirculo.x, passaroCirculo.y, passaroCirculo.radius);
//            shape.rect(rectangleCanobaixo.x, rectangleCanobaixo.y, rectangleCanobaixo.width, rectangleCanobaixo.height);
//            shape.rect(rectangleCanoAlto.x, rectangleCanoAlto.y, rectangleCanoAlto.width, rectangleCanoAlto.height);
//            shape.setColor(Color.RED);
//            shape.end();

            /**
             * Teste de Colisão
             */
            if (Intersector.overlaps(passaroCirculo, rectangleCanobaixo) || Intersector.overlaps(passaroCirculo, rectangleCanoAlto)
                    || posicaoInicialVertical <= 0) {
                estadoJogo = 2;
            }
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

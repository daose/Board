package com.daose.board.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.daose.board.Board;

/**
 * Created by student on 29/12/15.
 */
public class TransitionState extends State {

    public enum TransitionStyle {
        FADE,
        POP
    }

    private State prev, next;
    private TransitionStyle style;

    private float timer;
    private float alpha;
    private float maxTime;

    private ShapeRenderer box;

    public TransitionState(GSM gsm, State prev, State next, TransitionStyle style) {
        super(gsm);
        this.prev = prev;
        this.next = next;
        this.style = style;
        maxTime = 1f;
        timer = 0;
        box = new ShapeRenderer();
    }

    public void handleInput() {

    }

    public void update(float dt) {
        if (timer < maxTime) {
            timer += dt;
            alpha = fade();
        } else {
            timer = maxTime;
            dispose();
            gsm.set(next);
        }
    }

    public void dispose() {
        box.dispose();
        prev.dispose();
    }

    private float fade() {
        return (float) (0.5 * MathUtils.sin(2 * MathUtils.PI * (timer / maxTime) - (MathUtils.PI / 2)) + 0.5);
    }

    public void render(SpriteBatch sb) {
        if (style == TransitionStyle.FADE) {
            if (timer < maxTime / 2) {
                prev.render(sb);
            } else {
                next.render(sb);
            }
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            box.setProjectionMatrix(cam.combined);
            box.setColor(241f / 255, 242f / 255, 240f / 255, alpha);
            box.begin(ShapeRenderer.ShapeType.Filled);
            box.rect(0, 0, Board.WIDTH, Board.HEIGHT);
            box.end();

            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }
}

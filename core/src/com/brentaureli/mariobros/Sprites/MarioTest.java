package com.brentaureli.mariobros.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.brentaureli.mariobros.MarioBros;

/**
 * Created by brentaureli on 8/29/15.
 */
public class MarioTest extends Sprite{
    public World world;
    public Body b2body;
    public Animation animation;
    public TextureRegion keyFrame;
    Texture run;
    float stateTime;
    TextureRegion marioStand;
    boolean faceLeft;
    boolean jumping;
    public Animation jumpAnimation;
    float jumpStateTime;

    public MarioTest(World world){
        super(new TextureRegion(new Texture("mario_all.png")));
        TextureAtlas ta = new TextureAtlas("test2.pack");

        marioStand = new TextureRegion(ta.findRegion("little_mario"), 0, 0, 16, 16);
//        run = new Texture("mario_run.png");
        Array<TextureRegion> runAnimation = new Array<TextureRegion>();
        for(int i = 1; i < 4; i++){
            runAnimation.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        }
        animation = new Animation(0.1f, runAnimation);
        jumpAnimation = new Animation(0.1f, new TextureRegion(getTexture(), 64, 0, 16, 16), new TextureRegion(getTexture(), 80, 0, 16, 16));
        this.world = world;
        defineMario();
        setBounds(32 / MarioBros.PPM, 32 / MarioBros.PPM, 16 / MarioBros.PPM, 16 / MarioBros.PPM);
        faceLeft = false;

        jumpStateTime = 0;
    }

    public void defineMario(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MarioBros.PPM, 32 / MarioBros.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MarioBros.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);

    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        stateTime += dt;
        if(b2body.getLinearVelocity().y > 0) {
            jumping = true;
            jumpStateTime += dt;
        }
        if(b2body.getLinearVelocity().y == 0) {
            jumping = false;
            jumpStateTime = 0;
        }


        keyFrame = animation.getKeyFrame(stateTime, true);
        if(jumping){
            keyFrame = jumpAnimation.getKeyFrame(jumpStateTime, false);
        }
        if(!keyFrame.isFlipX() && b2body.getLinearVelocity().x < 0) {
            keyFrame.flip(true, false);
            faceLeft = true;
        }
        if(keyFrame.isFlipX() && b2body.getLinearVelocity().x > 0) {
            keyFrame.flip(true, false);
            faceLeft = false;
        }



        else if(b2body.getLinearVelocity().x == 0) {
            if((faceLeft && !marioStand.isFlipX()) || (!faceLeft && marioStand.isFlipX()))
                marioStand.flip(true, false);

            setRegion(marioStand);
        }
        else
            setRegion(keyFrame);

    }


}

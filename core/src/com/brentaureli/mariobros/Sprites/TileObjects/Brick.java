package com.brentaureli.mariobros.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.brentaureli.mariobros.MarioBros;
import com.brentaureli.mariobros.Scenes.Hud;
import com.brentaureli.mariobros.Screens.PlayScreen;

/**
 * Created by brentaureli on 8/28/15.
 */
public class Brick extends InteractiveTileObject {
    public Brick(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        setCategoryFilter(MarioBros.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
        MarioBros.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
    }

}

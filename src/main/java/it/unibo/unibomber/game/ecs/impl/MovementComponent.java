package it.unibo.unibomber.game.ecs.impl;

import java.util.Optional;

import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;

public class MovementComponent extends AbstractComponent {

    private static float globalSpeedMultiplier=1;
    private Pair<Float,Float> moveBy;
    private Direction direction = Direction.CENTER;
    private int framesInDirection=0;

    public MovementComponent(){
        moveBy= new Pair<Float,Float>(0f, 0f);
    }
    @Override
    public void update() {
        this.getEntity().addPosition(moveBy);
        //handleDirection();
        //checkCollisions();
        moveBy= new Pair<Float,Float>(0f, 0f);
    }

    private void handleDirection() {
        Direction newDirection=Direction.extractDirecion(moveBy);
        if(this.direction == newDirection){
            this.framesInDirection++;
        }
        else{
            this.framesInDirection=0;
            this.direction = newDirection;
        } 
    }
    private void checkCollisions() {        
        Optional<CollisionComponent> collisionComp = this.getEntity().getComponent(CollisionComponent.class);
        if(collisionComp.isPresent()){
            collisionComp.get().checkCollisions();
        }
    }
    public void moveBy(final Pair<Float,Float> moveBy){
        this.moveBy=new Pair<>( moveBy.getX()*this.getEntity().getSpeed()*globalSpeedMultiplier,
                                moveBy.getY()*this.getEntity().getSpeed()*globalSpeedMultiplier);
    }
    public Direction getDirection(){
        return this.direction;
    }
    public int getFrameInDirection(){
        return this.framesInDirection;
    }

    public static void setGlobalSpeedMultiplier(float speed){
        globalSpeedMultiplier=speed;
    }
}

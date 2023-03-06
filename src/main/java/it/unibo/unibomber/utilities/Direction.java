package it.unibo.unibomber.utilities;

public enum Direction {
    CENTER(0,0),
    UP(0,1),
    LEFT(-1,0),
    DOWN(0,-1),
    RIGHT(1,0);

    int x;
    int y;
    Direction(int x, int y){
        this.x=x;
        this.y=y;
    }

    //TODO refactor
    public static Direction extractDirecion(Pair<Float,Float> movement){
        int x = (int)(movement.getX()/(1/movement.getX()));
        int y = (int)(movement.getY()/(1/movement.getY()));

        if(x>0 && y==0)return RIGHT;
        else if(x<0 && y==0)return LEFT;
        else if(x==0 && y<0)return DOWN;
        else if(x==0 && y>0)return UP;
        return CENTER; 
    }

}

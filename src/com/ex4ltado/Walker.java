package com.ex4ltado;

import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;

@ScriptMeta(name = "Walker",  desc = "I love Walk <3", developer = "ex4ltado", category = ScriptCategory.TOOL)
public class Walker extends Script {


    static Position posToWalk = null;
    private static boolean canRun = true;

    public static void walkToNearestBank(){
        BankLocation bancoMaisPerto = BankLocation.getNearest();
        posToWalk = bancoMaisPerto.getPosition();
    }

    public static BankLocation getNearestBankPosition(){
        return BankLocation.getNearest();
    }


    public static void walkTo(Position position){
        try {
            Movement.walkTo(position);
            Script.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isInAreaWithPosition(Position position){
        if (Area.rectangular(position.getX() - 2,position.getY() + 2,position.getX() + 2,position.getY() - 2,position.getFloorLevel()).contains(Players.getLocal().getPosition())){
            return true;
        }
        return false;
    }


    @Override
    public int loop() {
        if (posToWalk == null) return 300;

        if (Movement.getRunEnergy() >= 50){
            canRun = true;
        } else if (Movement.getRunEnergy() <= 25 ) {
            canRun = false;
        }
        Movement.toggleRun(Movement.getRunEnergy() >= 25 && canRun ? true : false);


        if (!isInAreaWithPosition(posToWalk)){
            walkTo(posToWalk);
        } else {
            Log.info("Chegou.");
            posToWalk = null;
        }

        return 300;
    }

    @Override
    public void onStart() {
        new WalkGUI();
    }
}

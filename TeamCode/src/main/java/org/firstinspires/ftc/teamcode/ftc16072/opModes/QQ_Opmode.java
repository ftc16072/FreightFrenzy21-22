package org.firstinspires.ftc.teamcode.ftc16072.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.ftc16072.actions.QQ_Action;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.utils.QQ_Gamepad;

import java.util.List;

public abstract class QQ_Opmode extends OpMode {
    public Robot robot = new Robot();
    protected QQ_Action curr;
    public QQ_Gamepad gp1;
    public QQ_Gamepad gp2;
    public int barcodeLocation = 0;
    protected boolean usesGamepad = true;
    protected boolean initLoopConfig = false;
    public static boolean redAlliance = false;

    @Override
    public void init() {
        gp1 = new QQ_Gamepad(gamepad1);
        gp2 = new QQ_Gamepad(gamepad2);
        robot.init(hardwareMap);
        updateGamepads();
    }

    @Override
    public void init_loop() {
        super.init_loop();
        if(initLoopConfig){
            updateGamepads();
        }
    }

    @Override
    public void loop() {

        updateGamepads();
        robot.update(time);
        if (curr != null) {
            telemetry.addData("State", curr);
            curr = curr.run(this);
        } else {
            telemetry.addData("State", "No Action");
        }

    }

    private void updateGamepads(){
        if (usesGamepad) {
            gp1.update(gamepad1);
            gp2.update(gamepad2);
        }
    }


    public int updatePosition(int number, int max) {
        if (number > max) {
            number = 0;
        } else if (number < 0) {
            number = max - 1;
        }
        return number;
    }
}

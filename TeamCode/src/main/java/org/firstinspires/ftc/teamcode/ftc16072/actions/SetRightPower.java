package org.firstinspires.ftc.teamcode.ftc16072.actions;


import org.firstinspires.ftc.teamcode.ftc16072.opModes.QQ_Opmode;

public class SetRightPower extends PassedValueAction {

    @Override
    public QQ_Action run(QQ_Opmode opmode, double speed) {
        opmode.robot.driveTrain.drive(opmode.robot.driveTrain.getLeftSpeed(), speed);
        return this;
    }
}
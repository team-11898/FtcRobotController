package org.firstinspires.ftc.teamcode;


import android.provider.CalendarContract;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

//flkajslfkasf


@TeleOp
public class Startup extends LinearOpMode{
    ColorSensor color;


    private ElapsedTime runtime = new ElapsedTime();

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    @Override
    public void runOpMode() {
        color = hardwareMap.get(ColorSensor.class, "Color");
        float x;
        float y;
        float xR;
        float yR;
        float maxT;
        float minT;
        DcMotor FR = hardwareMap.get(DcMotor.class, "motorFR");
        DcMotor FL = hardwareMap.get(DcMotor.class, "motorFL");
        DcMotor BR = hardwareMap.get(DcMotor.class, "motorBR");
        DcMotor BL = hardwareMap.get(DcMotor.class, "motorBL");
        DcMotor armLeft = hardwareMap.get(DcMotor.class, "motorAL");
        Servo servo_r = hardwareMap.servo.get("servoDropperR");
        Servo servo_l = hardwareMap.servo.get("servoDropperL");
        Servo servo_d = hardwareMap.servo.get("servoDropperF");
        DcMotor armRight = hardwareMap.get(DcMotor.class, "motorAR");
        DcMotor feeder = hardwareMap.get(DcMotor.class, "motorFeeder");
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//        FR.setDirection(DcMotorSimple.Direction.REVERSE);
//        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        servo_l.setDirection(Servo.Direction.REVERSE);
//        armLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);

//        armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        armLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

//        ------ Initialize Servos ------
//        higher value is forward
        servo_l.setPosition(0.7);
        servo_r.setPosition(0.7);
//        higher is forward
        servo_d.setPosition(0.15);


        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            x = gamepad1.left_stick_x * 1.1f;
            y = -gamepad1.left_stick_y;
            xR = gamepad1.right_stick_x;
            yR = 0-gamepad1.right_stick_y;
            double extendArm = gamepad1.right_trigger;
            double retractArm = gamepad1.left_trigger;
            double armPower = 0.0;
            boolean feederActive = gamepad1.right_bumper;
            int extendPosition = armLeft.getCurrentPosition();
            maxT = 1f;
            minT = 0.01f;

            double theta = Math.atan2(y, x);
            double power = Math.hypot(x, y);
//            telemetry.addLine("hehehehehhehehe");
            telemetry.addData("Left X: ", x);
            telemetry.addData("Left Y: ", y);
            telemetry.addData("Right x: ", xR);
            telemetry.addData("Left Trigger: ", gamepad1.left_trigger);
            telemetry.addData("Right Trigger: ", gamepad1.right_trigger);
            telemetry.addData("Arm Position", extendPosition);
            telemetry.addData("Red", color.red());
            telemetry.addData("Blue", color.blue());
            telemetry.addData("Green", color.green());
            telemetry.addData("rgb", color.argb());

            telemetry.update();

//            feeder
            if (feederActive) {
                if (color.argb() > 0) {
                    feeder.setPower(0.35);
                } else {
                    feeder.setPower(0.15);
                }
            } else {
                feeder.setPower(0);
            }
//            arm

            if (extendArm > 0.0) {
                armLeft.setPower(-extendArm);
                armRight.setPower(extendArm);
            } else {
                armRight.setPower(0);
                armRight.setPower(0);

            }
            if (retractArm >= 0.0) {
                armLeft.setPower(retractArm);
                armRight.setPower(-retractArm);
            } else {
                armRight.setPower(0);
                armRight.setPower(0);

            }

//            flipper dropper
            if (gamepad1.a) {
                servo_l.setPosition(.8);
                servo_r.setPosition(.8);
                servo_d.setPosition(0.8);
            } else {
                servo_r.setPosition(0.7);
                servo_l.setPosition(0.7);
                servo_d.setPosition(0.15);

            }


//            movement
            if ((x < .9) && (x > .7)) {
                x = 0;
            }
            if ((x > -.9) && (x < -.7)) {
                x = 0;
            }
//            FL BR
            double frontLeftPower = (y + x + xR);
            double backRightPower = (y + x - xR);

            double frontRightPower = (y - x - xR);
            double backLeftPower = (y - x + xR);

//            double sin = Math.sin(theta - Math.PI/4);
//            double cos = Math.cos(theta - Math.PI/4);
//            double max = Math.max(Math.abs(sin), Math.abs(cos));
//
//
//            double frontLeftPower = power * cos/max + xR;
//            double backRightPower = power * cos/max - xR;
//
//            double frontRightPower = power * sin/max - xR;
//            double backLeftPower = power * sin/max + xR;
//
//            if ((power + Math.abs(xR)) > 1) {
//                frontLeftPower /= power + xR;
//                backRightPower /= power + xR;
//                frontRightPower /= power + xR;
//                backLeftPower /= power + xR;
//            }

//          FR BL
            FR.setPower(frontRightPower);
            FL.setPower(frontLeftPower);
            BR.setPower(backRightPower);
            BL.setPower(backLeftPower);

//            // Move backwards
//            if ((y <= -minT && y >= -maxT) && (x<=0.5 && x>=-0.5)){
//                FR.setPower(-y);
//                FL.setPower(y);
//                BR.setPower(-y);
//                BL.setPower(y);
//            }
//            //move forwards
//            else if ((y <= maxT && y >= minT) && (x<=0.5 && x>=-0.5)){
//                FR.setPower(-y);
//                FL.setPower(y);
//                BR.setPower(-y);
//                BL.setPower(y);
//            }
//            //strafe left
//            else if ((x <= -minT && x >= -maxT) && (y <= 0.5 && y >= -0.5)) {
//                FR.setPower(x);
//                FL.setPower(x);
//                BR.setPower(-x);
//                BL.setPower(-x);
//            }
//            // strafe right
//            else if ((x <= maxT && x >= minT) && (y <= 0.5 && y >= -0.5)) {
//                FR.setPower(x);
//                FL.setPower(x);
//                BR.setPower(-x);
//                BL.setPower(-x);
//            }
//            // reset motors
//            else {
//                FR.setPower(0);
//                FL.setPower(0);
//                BR.setPower(0);
//                BL.setPower(0);
//            }
//            // diagonals
//            // up right
//            if ((x > 0.5) && (y > 0.5)) {
//                FR.setPower(0);
//                FL.setPower(x);
//                BR.setPower(-x);
//                BL.setPower(0);
//            }
//            // up left
//            if ((x < -0.5) && (y > 0.5)) {
//                FR.setPower(-1);
//                FL.setPower(0);
//                BR.setPower(0);
//                BL.setPower(1);
//            }
//            // back left
//            if ((x < -0.5) && (y < -0.5)) {
//                FR.setPower(0);
//                FL.setPower(-1);
//                BR.setPower(1);
//                BL.setPower(0);
//            }
//            // back right
//            if ((x > 0.5) && (y < -0.5)) {
//                FR.setPower(1);
//                FL.setPower(0);
//                BR.setPower(0);
//                BL.setPower(-1);
//            }
//            // else {
//            //     FR.setPower(0);
//            //     FL.setPower(0);
//            //     BR.setPower(0);
//            //     BL.setPower(0);
//            // }
//
//
////            rotation
//            if (xR == 1 && (yR <=0.2 || yR <= -0.2)) {
//                FR.setPower(xR);
//                FL.setPower(xR);
//                BR.setPower(xR);
//                BL.setPower(xR);
//            } else if (xR == -1 && (yR <=0.2 || yR <= -0.2)) {
//                FR.setPower(xR);
//                FL.setPower(xR);
//                BR.setPower(xR);
//                BL.setPower(xR);
//            }
//            else {
//                FR.setPower(0);
//                FL.setPower(0);
//                BR.setPower(0);
//                BL.setPower(0);
//            }
        }
    }

}
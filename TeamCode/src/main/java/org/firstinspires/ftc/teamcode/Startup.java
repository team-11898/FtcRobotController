package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
//flkajslfkasf


@TeleOp
public class Startup extends LinearOpMode{


    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() {
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

        waitForStart();
        runtime.reset();
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        //servo_r.setDirection(Servo.Direction.REVERSE);

        while (opModeIsActive()) {
            // FR.setPower(1);
            x = gamepad1.left_stick_x;
            y = 0-gamepad1.left_stick_y;
            xR = gamepad1.right_stick_x;
            yR = 0-gamepad1.right_stick_y;
            boolean extendArm = gamepad1.right_bumper;
//            boolean retractArm = gamepad1.left_bumper;
            int extendPosition = armLeft.getCurrentPosition();
            boolean flipped = false;
//            servo_l.setPosition(0);
//            servo_r.setPosition(0);
            maxT = 1f;
            minT = 0.01f;

            telemetry.addData("Left X: ", x);
            telemetry.addData("Left Y: ", y);
            telemetry.addData("Right x: ", xR);
            telemetry.addData("Trigger: ", gamepad1.left_trigger);
            telemetry.update();

            if (extendArm) {
                feeder.setPower(1);
            } else {
                feeder.setPower(0);
            }
//            if (retractArm && extendPosition > 0) {
//                armLeft.setPower(-1);
//                armRight.setPower(1);
//            } else {
//                armLeft.setPower(0);
//                armRight.setPower(0);
//            }
//            if (extendArm && extendPosition < 3080 && !retractArm) {
//                armLeft.setPower(1);
//                armRight.setPower(-1);
//            } else {
//                armLeft.setPower(0);
//                armRight.setPower(0);
//            }
            if (gamepad1.a) {
//                servo_l.setPosition(1);
//                servo_r.setPosition(1);
                servo_l.setPosition(0.5);
                servo_r.setPosition(0.5);
//                servo_d.setPosition(1);
                flipped = true;
            }


            // Move backwards
            if ((y <= -minT && y >= -maxT) && (x<=0.5 && x>=-0.5)){
                FR.setPower(-y);
                FL.setPower(y);
                BR.setPower(-y);
                BL.setPower(y);
            }
            //move forwards
            else if ((y <= maxT && y >= minT) && (x<=0.5 && x>=-0.5)){
                FR.setPower(-y);
                FL.setPower(y);
                BR.setPower(-y);
                BL.setPower(y);
            }
            //strafe left
            else if ((x <= -minT && x >= -maxT) && (y <= 0.5 && y >= -0.5)) {
                FR.setPower(x);
                FL.setPower(x);
                BR.setPower(-x);
                BL.setPower(-x);
            }
            // strafe right
            else if ((x <= maxT && x >= minT) && (y <= 0.5 && y >= -0.5)) {
                FR.setPower(x);
                FL.setPower(x);
                BR.setPower(-x);
                BL.setPower(-x);
            }
            // reset motors
            else {
                FR.setPower(0);
                FL.setPower(0);
                BR.setPower(0);
                BL.setPower(0);
            }
            // diagonals
            // up right
            if ((x > 0.5) && (y > 0.5)) {
                FR.setPower(0);
                FL.setPower(x);
                BR.setPower(-x);
                BL.setPower(0);
            }
            // up left
            if ((x < -0.5) && (y > 0.5)) {
                FR.setPower(-1);
                FL.setPower(0);
                BR.setPower(0);
                BL.setPower(1);
            }
            // back left
            if ((x < -0.5) && (y < -0.5)) {
                FR.setPower(0);
                FL.setPower(-1);
                BR.setPower(1);
                BL.setPower(0);
            }
            // back right
            if ((x > 0.5) && (y < -0.5)) {
                FR.setPower(1);
                FL.setPower(0);
                BR.setPower(0);
                BL.setPower(-1);
            }
            // else {
            //     FR.setPower(0);
            //     FL.setPower(0);
            //     BR.setPower(0);
            //     BL.setPower(0);
            // }
            if (xR == 1 && (yR <=0.2 || yR <= -0.2)) {
                FR.setPower(xR);
                FL.setPower(xR);
                BR.setPower(xR);
                BL.setPower(xR);
            } else if (xR == -1 && (yR <=0.2 || yR <= -0.2)) {
                FR.setPower(xR);
                FL.setPower(xR);
                BR.setPower(xR);
                BL.setPower(xR);
            }
            else {
                FR.setPower(0);
                FL.setPower(0);
                BR.setPower(0);
                BL.setPower(0);
            }
        }
    }

}
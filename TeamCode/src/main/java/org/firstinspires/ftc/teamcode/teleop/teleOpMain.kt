package org.firstinspires.ftc.robotcontroller.external.samples

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.TankDrive
import com.qualcomm.robotcore.eventloop.opmode.TeleOp


@TeleOp(name = "teleOpMain", group = "Robot")
class teleOpMain : OpMode() {

    override fun init() {
        // Define and Initialize Motors
        TankDrive.init(this)
    }

    override fun loop() {

        // Controller input
        var drive = gamepad1.left_stick_y.toDouble()
        var turn = -gamepad1.right_stick_x.toDouble()

        TankDrive.power(turn, drive)


        // Outtake control
        if (gamepad1.right_trigger > 0.1) {
            TankDrive.powerOuttake()
        } else {
            TankDrive.stopOuttake()
        }
    }

    }

package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.robotcontroller.external.samples.teleOpMain
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.api.TriWheels.red
import org.firstinspires.ftc.teamcode.core.API

object TankDrive : API() {
    // Define leftDrive, rightDrive, etc.
    val OUTTAKE_POWER: Double = 0.50 // Run outtake motor at 50% power
    lateinit var leftDrive: DcMotor
        private set
    lateinit var rightDrive: DcMotor
        private set
    lateinit var outtakeMotor: DcMotor
        private set
    lateinit var preOuttake1: Servo
        private set
    lateinit var preOuttake2: Servo
        private set

    override fun init(opMode: OpMode) {

        super.init(opMode)

        leftDrive = this.opMode.hardwareMap.get(DcMotorEx::class.java, "leftDrive")
        rightDrive = this.opMode.hardwareMap.get(DcMotor::class.java, "rightDrive")
        outtakeMotor = this.opMode.hardwareMap.get(DcMotor::class.java, "outtakeMotor")
        preOuttake1 = this.opMode.hardwareMap.get(Servo::class.java, "pre_outtake1")
        preOuttake2 = this.opMode.hardwareMap.get(Servo::class.java, "pre_outtake2")



    }

    fun power(drive: Double, turn: Double) {

        val leftPower = drive + turn
        val rightPower = drive - turn

        // Clip power values to [-1.0, 1.0]
        leftDrive.power = leftPower.coerceIn(-1.0, 1.0)
        rightDrive.power = rightPower.coerceIn(-1.0, 1.0)

    }

    fun powerOuttake(){
        outtakeMotor.power = 1.0
    }

    fun stopOuttake(){
        outtakeMotor.power = 0.0
    }
}

package org.firstinspires.ftc.teamcode.api
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.teamcode.core.API

object Intake : API() {
    private lateinit var intakeMotor: DcMotorEx

    override fun init(opMode: OpMode) {
        super.init(opMode)

        intakeMotor = this.opMode.hardwareMap.get(DcMotorEx::class.java, "intakeMotor")
    }

    fun move(power: Double = 0.35) {
        intakeMotor.power = power
    }

    fun stop() {
        intakeMotor.power = 0.0
    }
}
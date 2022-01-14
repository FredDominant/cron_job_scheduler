package com.freddominant
import java.io.File

fun main(args: Array<String>) {
    if (args.size != 1) {
        val errorMessage = "Please pass only the simulated_current_time"
        throw IllegalArgumentException(errorMessage)
    }
    val currentTime = args[0].split(":")
    val currentHour = currentTime[0].toInt()
    val currentMinute = currentTime[1].toInt()

    // This reads the command line until there's nothing left to be read.
    val cronJobs = generateSequence(::readLine).toList()

    cronJobs.map { schedule ->
        val result = checkNextCronSchedule(
                schedule = schedule,
                currentMinute = currentMinute,
                currentHour = currentHour)
        println(result)
    }
}

private fun checkNextCronSchedule(schedule: String, currentMinute: Int, currentHour: Int): String {
    val TODAY = "today"
    val TOMORROW = "tomorrow"

    val cronJobDetails = schedule.trim().split(" ")

    if (cronJobDetails.size != 3) {
        val errorMessage = "Please pass the cron details in this format: <minute_of_the_hour> <hour> <command_to_run>"
        throw IllegalArgumentException(errorMessage)
    }

    val cronJobMinute = cronJobDetails[0]
    val cronJobHour = cronJobDetails[1]
    val command = cronJobDetails[2]

    // Since "*" signifies it has to be ran at every hour or minute depending on the use case.
    val shouldRunEveryHour = "*" == cronJobHour
    val shouldRunEveryMinute = "*" == cronJobMinute

    // Since we know that this job should run every time, then we know the next earliest time to run is now, the current time
    // So we early return here.
    if (shouldRunEveryHour && shouldRunEveryMinute) {
        return formatResult(currentHour, currentMinute, TODAY, command)
    }

    // Because it runs every hour, we know that the next best time to run would be the current hour
    var hour = if (shouldRunEveryHour) currentHour else cronJobHour.toString().toInt()
    val day = if (currentHour > hour) TOMORROW else TODAY


    var minute = when {
        shouldRunEveryMinute && TODAY == day -> 0
        shouldRunEveryMinute -> currentMinute
        else -> cronJobMinute.toString().toInt()
    }
    return formatResult(hour, minute, day, command)
}

private fun formatResult(hour: Int, minute: Int, day: String, command: String): String {
    return "$hour:${if (minute == 0) "00" else minute} $day - $command"
}
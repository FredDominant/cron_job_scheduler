# Cron Job Submission For Frederick Adewole

## How to run

- Install kotlin if you already haven't [here](https://kotlinlang.org/docs/command-line.html#sdkman)
- Navigate into this folder by running `cd <path_to_this_folder>` 
- Compile the code by running `kotlinc <name_of_kotlin_file> -include-runtime -d <name_of_compiled_jar_file.jar` Assuming the name of the kotlin file is `cron.kt`, then we'd run our command as so `kotlinc cron.kt -include-runtime -d cron.jar`
- Next we run this command to concatenate and run the command line app: `cat <name_of_file_containing_our_parameters>| java -jar <compiled_jar_file>.jar <simulated_current_time>`. A valid command to run this file would be this `cat input.txt| java -jar cron.jar 16:10`

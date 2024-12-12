# LSEG
LSEG outlier detection repository

This is a project which determines the outlier in a stock file(s).
To setup the project:
Clone the repository.
Make sure Java 17 or higher is installed in your device.
Make sure an IDE compatible with Java is installed(recommended IDE is Intellij)
Make sure maven is setup.
Open the project and go to path src->test->java>lseg->LSEG_main class and click on run .
You will be asked to enter stock input, enter preferred choice.
Then enter the number of files to be processed for outlier and hit enter.
The program will run and store the output under src->test->output directory and also print the result in console.

Troubleshooting:
If program doesnt run, try mvn clean install on the terminal and rerun .

Enhancements:
The project can be enhanced to use builder pattern and make code look cleaner.
The use of TestNG can also be done to enhance readability and maintainability of tests.


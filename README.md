cs428crs
========

#############################
## Getting Started
#############################
cd to the directory where you want to store the root of your project

    git clone https://github.com/byuCs428CRS/cs428crs.git

Now you can open this project in any IDE you want.
I used IntelliJ. With intelliJ you can simply do File > Open.. then select the build.gradle file
it should pull in the whole project.
If your using eclipse you can go: File>Import>Gradle (If this is not an option,
install the gradle integration from marketplace via help). Follow the instructions after this.

#############################
## Building
#############################

Run the following to build.

    ./gradlew clean build
    
This will create a war file. 

########################
## Running Locally
########################

To run directly, you will need to edit the gradle.build file. Comment out line 21, and uncomment line 20. Then, run the following:

    ./gradlew run
    
You should see some text say "Spring" like this:
    
    BUILD SUCCESSFUL
    Total time: 22.429 secs
    . ____ _ __ _ _
    /\\ / ___'_ __ _ _(_)_ __ __ _ \ \ \ \
    ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
    \\/ ___)| |_)| | | | | || (_| | ) ) ) )
    ' |____| .__|_| |_|_| |_\__, | / / / /
    =========|_|==============|___/=/_/_/_/
    :: Spring Boot :: (v0.5.0.M6)

If you have that then you are good to go!!!
You can now connect to your local server at localhost:8080

###########
## Spring
###########
If you havent used spring before, below is a basic tutorial.
http://spring.io/guides/gs/rest-service/

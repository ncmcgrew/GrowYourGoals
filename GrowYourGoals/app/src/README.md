# README #


### What is this repository for? ###

* I was having problems with gradle, and we also wanted to change the package name.  This repository only contains source code, without the gradle scripts.

### How do I get set up? ###

* First, create a new project in AndroidStudio
* for project name, enter GrowYourGoals
* for company domain, enter cs498r
* Next, delete the src folder located in <projectName>/app
* In <projectName>/app, clone this repo.
* Restart AndroidStudio
* When a message comes up about Git, click ignore

### Who do I talk to? ###

* For questions, see Nate McGrew
* Other community or team contact


**here are some instructions for setting up google sign in**

* put the google-services.json file into the GrowYourGoals/app directory

* Add the dependency to your project-level build.gradle:
classpath 'com.google.gms:google-services:2.0.0-alpha6'

* Add the plugin to your app-level build.gradle (just below the first line where it says "apply plugin: 'com.android.application'"): 
apply plugin: 'com.google.gms.google-services'

* Add the dependency to your app-level build.gradle: 
compile 'com.google.android.gms:play-services-auth:8.3.0'

* growYourGoals.jks is used for building a signed apk.  the alias is gyg, and both passwords are 1234pass (you'll be prompted for this info if you go to build a signed apk)
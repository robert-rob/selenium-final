<img src="http://community.neur.io/uploads/default/201/e73a338e371e3192.png" width="350px" alt="Neurio Logo" />

# Automated Web App Selenium Tests

A Selenium based automated test project for the Neurio Web App.

## Installation

Maven and Chrome Web Browser needs to be installed to run this project.

The project uses the following dependencies:
* TestNG 6.10
* Selenium 3.0.1
* Maven Surefire Plugin 2.18.1
* Apache POI 3.15
* Mashape Unirest 1.4.9
* JSON-java v20160810

1. Pull the code from GitHub.

## Usage

Go to the project folder in command prompt.

1. Make sure ChromeDriver is running before running the test suite.
   Go to https://sites.google.com/a/chromium.org/chromedriver/downloads
   to download the latest version if it is not yet installed.
   There is also a folder in the project with chrome driver executable 
   files called "chromedriver" if you prefer to use them.  
      
To run the whole test suite:

    mvn integration-test

To run a single test such as "LoginTest":

    mvn -Dtest=LoginTest test
    
To run specific tests such as "LoginTest" and "SettingsTest":

    mvn -Dtest=LoginTest,SettingsTest test
    
All test files are located in com.neurio.tests folder.
To change browsers, edit the config.properties file.
After a test run, the HTML Test Report will be located in 
/target/surefire-reports/emailable-report.html.
Screenshots will be saved in the /target/web-failure-screenshots folder.
Test Failure Screenshots will be saved in the /target/web-screenshots
folder.

## Contributing

Fork it!
Create your feature branch: git checkout -b my-new-feature
Commit your changes: git commit -am 'Add some feature'
Push to the branch: git push origin my-new-feature
Submit a pull request

## History

Version 1.5 - November 22, 2016
 - Added numerous improvements to the project
 - Updated the Read Me file

Version 1.0 - May 18, 2016

## Credits

Robert T.

## License

Copyright 2016 Robert T. robert@neur.io

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is
distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and limitations under the License.

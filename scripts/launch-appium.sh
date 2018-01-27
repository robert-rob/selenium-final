#! /bin/sh
# ==================== Notes =========================
#	Requirements:
# 	 * git v2.3+
#	 * node v0.12.0+
# 	 * npm
#
#	Notes:
#	 * appium versions before 1.6 (which is currently is beta) are not compatible with Xcode 8 and iOS 10
#	 * ParallelAndroidWebForecastTest assumes:
#	  ** 
#
# ====================================================

# brew install carthage
# npm install appium@beta
# npm install ios-sim

main() {
#---------- LAUNCH WEB REQUIREMENTS --------- #
echo "Launching chromedriver"
./chromedriver/chromedriver &
test_pids="${test_pids-} $!"


#---------- LAUNCH ANDROID REQUIREMENTS ----- #
echo "Launching Android emulator and waiting 30 seconds for it to boot up"
$ANDROID_HOME/tools/emulator -avd NEXUS_5X_API_23 &
test_pids="${test_pids-} $!"
sleep 30

echo "Launching appium server for Android"
# @Note: locally using this installation of node because global version is 0.10 on Soroush's machine
/Applications/Appium.app/Contents/Resources/node/bin/node /Users/soroushnorouzi/.node/lib/node_modules/appium/build/lib/main.js \
--automation-name "Appium" \
--platform-name "Android" \
--app "/Users/soroushnorouzi/Neurio-Home/mobile/mobile-release.apk" &
test_pids="${test_pids-} $!"


#----------- LAUNCH IOS REQUIREMENTS ------- #
echo "Launching a simulator"
./node_modules/ios-sim/bin/ios-sim start --devicetypeid com.apple.CoreSimulator.SimDeviceType.iPhone-6,\ 9.3 &
test_pids="${test_pids-} $!"
sleep 10

echo "Launching appium for iOS and waiting for 5 seconds"
/Applications/Appium.app/Contents/Resources/node/bin/node /Users/soroushnorouzi/.node/lib/node_modules/appium/build/lib/main.js \
--debug-log-spacing \
--platform-version "9.3" \
--platform-name "iOS" \
--port 4724 \
--app "/Users/soroushnorouzi/Desktop/neuriohome.app" \
--show-ios-log --default-device &
test_pids="${test_pids-} $!"
sleep 5

echo "pids are $test_pids"

### LAUNCH THE TEST
echo "Launching the test"
mvn -Dtest=CalculatorTest#ParallelAndroidWebForecastTest test
echo "Killing the launched tasks and the iOS simulator"
kill $test_pids
}

time main

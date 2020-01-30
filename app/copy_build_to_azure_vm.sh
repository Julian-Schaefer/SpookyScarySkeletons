flutter build apk
flutter build web
scp -r ./build/web/* JSheeper@spookyscaryskeletons.northeurope.cloudapp.azure.com:/home/JSheeper/Componentware_App/build/web
scp -r ./build/app/outputs/apk/release/app-release.apk JSheeper@spookyscaryskeletons.northeurope.cloudapp.azure.com:/home/JSheeper/Componentware_App/build/web/SpookyScarySkeletons.apk
scp ./start_web_server.sh JSheeper@spookyscaryskeletons.northeurope.cloudapp.azure.com:/home/JSheeper/Componentware_App/start_web_server.sh
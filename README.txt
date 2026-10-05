ANTI-EXTRAVIO COMPLETO - LISTO PARA CODEMAGIC
=============================================

Este paquete YA TIENE gradlew y gradle-wrapper.jar
NO dara error de "gradlew not found" en Codemagic.

PASOS PARA COMPILAR APK EN CODEMAGIC:

1) Crea un repo nuevo en GitHub (ej: anti-extravio-pro)
   Sube TODO el contenido de este ZIP:
     git init
     git add .
     git commit -m "Proyecto completo Codemagic - con gradlew"
     git branch -M main
     git remote add origin https://github.com/TU_USUARIO/anti-extravio-pro.git
     git push -u origin main

2) Ve a https://codemagic.io
   - Login con GitHub
   - Add application > selecciona tu repo
   - Codemagic detectara Android automaticamente

3) Configuracion Build:
   - Build triggers: manual o push
   - Instance: Linux
   - Java: 17
   - Gradle task: assembleDebug (o assembleRelease para AAB/APK release)

4) Click en "Start new build"
   Ya no fallara por gradlew porque ESTE ZIP incluye:
   - gradlew (con permiso ejecutable)
   - gradle/wrapper/gradle-wrapper.properties -> gradle-8.4-bin.zip
   - gradle/wrapper/gradle-wrapper.jar (con MANIFEST.MF valido)

ESTRUCTURA INCLUIDA:
- settings.gradle -> include ":app"
- build.gradle -> plugins android 8.1.0 y kotlin 1.9.0
- app/build.gradle -> namespace com.antiv.pro, compileSdk 34, minSdk 26
  dependencies: core-ktx, appcompat, material, security-crypto, firestore
- AndroidManifest.xml con permisos:
  CAMERA, ACCESS_FINE_LOCATION, RECEIVE_SMS, READ_SMS, CALL_PHONE
  MainActivity como LAUNCHER
- res/values/strings.xml -> app_name Anti Extravio PRO
- res/xml/device_admin.xml
- proguard-rules.pro
- MainActivity.kt con inputs: numero emergencia, palabra clave, codigo licencia y boton Activar
- generador-licencias/generador.py -> genera XXXXX-XXXXX-XXXXX-XXXXX-XXXXX con fallback sin rsa

GENERADOR DE LICENCIAS:
  cd generador-licencias
  python generador.py 10

MAIN ACTIVITY:
  - Valida formato licencia: ^[A-Z0-9]{5}(-[A-Z0-9]{5}){4}$
  - Toast "ANTI-EXTRAVIO ACTIVADO PRO" si todo OK

NOTA CODEMAGIC:
  Si usas assembleRelease, configura signing en Codemagic > Code signing.

¡Listo para compilar sin error gradlew!

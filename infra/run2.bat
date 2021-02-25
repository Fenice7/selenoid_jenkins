
wget -O cm.exe --no-check-certificate https://github.com/aerokube/cm/releases/download/1.7.2/cm_windows_amd64.exe


cm.exe selenoid start –-vnc
cm.exe selenoid-ui start


docker pull selenoid/vnc_chrome:88.0
docker pull selenoid/vnc_firefox:85.0


docker ps


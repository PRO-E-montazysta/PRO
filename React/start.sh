#!/bin/sh
echo "window.REACT_APP_ENVIRONMENT = '$REACT_APP_ENVIRONMENT';" > /usr/share/nginx/html/config.js
nginx -g 'daemon off;'
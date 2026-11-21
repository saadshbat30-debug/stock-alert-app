#!/data/data/com.termux/files/usr/bin/bash
cd "$HOME/raqami_v2" || exit 1
if ! curl --silent --fail --max-time 2 http://127.0.0.1:8080/ >/dev/null; then
  nohup python server.py >> server.log 2>&1 < /dev/null &
fi

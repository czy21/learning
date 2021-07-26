
curl -H 'Content-Type:application/json' -X POST -d '{"id":"2f5aa878-352d-49a9-a18e-188449e9e649"}' http://192.168.2.21:8076/stock/sale

ab -n 30000 -c 5000 -p post_data.json -T 'application/json' http://192.168.2.21:8076/stock/sale
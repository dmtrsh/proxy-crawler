# proxy-crawler

The application check proxies for accessebility add aliveness.

Each suitable proxy is logged to the console and stored in a CSV file. 
Each record of the result CSV file has proxy host, port, latency and country.

The application accepts 3 arguments:
- Country code (e.g. UA, US)
- Max. latency (ms.)
- Max. last check time (ms.)

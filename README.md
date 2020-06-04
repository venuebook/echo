# Echo Server

A small script to start an [express](https://www.npmjs.com/package/express) server that echos JSON requests and an [ngrok](https://www.npmjs.com/package/ngrok) tunnel to allow requests over the web.

## Requirements
- [Node 10+](https://nodejs.org/en/)
- Some terminal experience

## Installation
1. Clone this directory `git clone https://github.com/venuebook/echo.git`
2. Enter the repo `cd echo`
3. Install the npm dependencies `npm install`
4. Start the server and the tunnel with `npm start`

## Usage
Once the server is started you should see three URLs:

- Local server URL handles incoming JSON requests and returns JSON info on port 8080
- The ngrok tunnel URL which forwards web traffic to your local server
- The ngrok inspector URL which will log incoming requests from your ngrok tunnel. This runs on port 4040.

From this point you should be able to make requests to your ngrok tunnel URL and see it logged in the console where you ran `npm start`.

You can start the server on another local port like 9090 by running the following in a terminal:

```shell
npm start 9090
```

## Docker
An optional docker file is included if you don't wish to run these tools directly on your computer.

### Usage
1. Clone this directory `git clone git@github.com:venuebook/echo.git`
2. Enter the repo `cd echo`
3. Start the docker container `docker-compose up`

Now you can view the local web server at http://localhost:8080, ngrok inspector at http://localhost:4040, and your dynamic ngrok tunnel URL will be displayed in the console.

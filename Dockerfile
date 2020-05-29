FROM node:lts

STOPSIGNAL SIGINT
COPY package.json /echo/package.json
COPY package-lock.json /echo/package-lock.json
COPY ngrok.yml /echo/ngrok.yml
WORKDIR /echo
RUN npm install
CMD ["npm", "start", "8080", "--", "--docker"]

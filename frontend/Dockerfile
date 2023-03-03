FROM node:16.16.0
WORKDIR /app
COPY package*.json ./
RUN npm install --silent
COPY . .
EXPOSE 4200 4200
CMD ["npm", "start"]
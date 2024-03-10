FROM node:18 AS build
WORKDIR /app
COPY package.json /app/package.json
COPY package-lock.json /app/package-lock.json
RUN npm install --force
COPY . /app
RUN npm run build

FROM nginx:1.21.4-alpine

COPY --from=build /app/build /usr/share/nginx/html

COPY /nginx/nginx.conf /etc/nginx/conf.d/default.conf

CMD ["nginx", "-g", "daemon off;"]

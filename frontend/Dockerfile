# build
FROM node:20.11.1-alpine as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# slim image
FROM nginx:stable
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /app/dist/equadis-frontend/browser /usr/share/nginx/html
EXPOSE 80

# Build stage
FROM node:latest as build
WORKDIR /app
COPY package.json package-lock.json ./
COPY . ./
RUN npm ci
RUN npm run build

# Production stage
FROM nginx:stable-alpine
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]


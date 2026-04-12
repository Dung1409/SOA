# Use a lightweight Nginx image
FROM nginx:alpine

# Configure Nginx to proxy API calls to the gateway container
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy static files from the Frontend directory to the Nginx public HTML directory
COPY . /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# The default command for Nginx is to start the server
CMD ["nginx", "-g", "daemon off;"]

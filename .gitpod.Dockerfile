# Use an official MySQL runtime as a parent image
FROM mysql:latest

# Set the root password for MySQL (change 'yourpassword' to a secure password)
ENV MYSQL_ROOT_PASSWORD=pass

# Expose the MySQL port (optional)
EXPOSE 3306

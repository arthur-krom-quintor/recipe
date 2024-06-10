#!/bin/bash
sudo docker container run --name recipe-mysql-db -d -p 13306:3306 -e MYSQL_DATABASE=recipe -e MYSQL_USER=user1 -e MYSQL_PASSWORD=password1 -e MYSQL_ROOT_PASSWORD=s3cr3t mysql

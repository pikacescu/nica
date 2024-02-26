-- Run in MySQL as root
create user if not exists 'user'@'localhost' identified by 'pass';
create database if not exists automobile;
grant all privileges on automobile.* to 'user'@'localhost';
flush privileges;
INSERT INTO user (dtype, id, email,last_password_reset_date,password, username, available_minutes, immunity, immunity_points, registry_date) VALUES ('RegisteredUser', 1, 'a@a','2019-08-08 00:00','$2a$10$xMipTNv6mB4FdLt52YK4KuzVVFx891Pr0cnWySeko67UbjbZcIAK2','user1',0,null,0,null) ON DUPLICATE KEY UPDATE id = 1;

INSERT INTO user (dtype, id, email, last_password_reset_date,password, username, available_minutes, immunity, immunity_points, registry_date) VALUES ('Administrator', 2, 'b@b','2019-09-08 00:00','$2a$10$xMipTNv6mB4FdLt52YK4KuzVVFx891Pr0cnWySeko67UbjbZcIAK2','user2',0,null,0,null) ON DUPLICATE KEY UPDATE id = 2;
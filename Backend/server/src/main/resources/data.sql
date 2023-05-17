INSERT INTO appuser (id, name, surname, firebase_token)
VALUES ('fAsTAzll1fbLRMczYPlOKOcdw6H3', 'Buyer user', 'Demo', 'eyJhbGciOiJSUzI1NiIsImtpZCI6ImI2NzE1ZTJmZjcxZDIyMjQ5ODk1MDAyMzY2ODMwNDc3Mjg2Nzg0ZTMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vbW9uc3RlcmFsLXRlY2giLCJhdWQiOiJtb25zdGVyYWwtdGVjaCIsImF1dGhfdGltZSI6MTY4MzQwNTQzMSwidXNlcl9pZCI6ImZBc1RBemxsMWZiTFJNY3pZUGxPS09jZHc2SDMiLCJzdWIiOiJmQXNUQXpsbDFmYkxSTWN6WVBsT0tPY2R3NkgzIiwiaWF0IjoxNjgzNDA1NDMxLCJleHAiOjE2ODM0MDkwMzEsImVtYWlsIjoic29nZWtpbmdkYWJlc3RAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbInNvZ2VraW5nZGFiZXN0QGdtYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.RP85WYytCliiUiUae5zTISupjv9NFZpQ0SCc2An_xXt4CMn08u4hfXXyQCmmwf1RDYmGvoBkkuSajnVcMZ976PXRqmlVhx-SennxiPJjXP3QNY61kz84lXMXi-vVXZkiOz7xfA6BUhwX9YjAyUdzC-a4F4wFwzxFdW-k0FLWCRoVqkOVi96RdqB-5NiUOoXTEmpEJdjbClEYlWzUOD432zlnjjZkxlJwuBk0V_BwCXMWsBrZ0GUH_3SJciq6r0JYeK7sSU8ojcwkzjZ-_ZQI2kligKFMRrsMvnhcxrZuiqCnUtdM6T39Pkkxgbj44V0lQVp1Gr4y6XEKz5nmajC2Xg');

INSERT INTO appuser (id, name, surname, firebase_token)
VALUES ('pfpY9jg25hfdKPaDMyKqg5GIkPV2', 'Seller user', 'Demo', 'eyJhbGciOiJSUzI1NiIsImtpZCI6ImI2NzE1ZTJmZjcxZDIyMjQ5ODk1MDAyMzY2ODMwNDc3Mjg2Nzg0ZTMiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiU2hhayBCZXJtbyIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BR05teXhZM1doZG9DOEF6dWQ1LUhBcHRtY1NTNk11eFEwd1ozQlJLcjlCdD1zOTYtYyIsImlzcyI6Imh0dHBzOi8vc2VjdXJldG9rZW4uZ29vZ2xlLmNvbS9tb25zdGVyYWwtdGVjaCIsImF1ZCI6Im1vbnN0ZXJhbC10ZWNoIiwiYXV0aF90aW1lIjoxNjgzMzczNDYzLCJ1c2VyX2lkIjoicGZwWTlqZzI1aGZkS1BhRE15S3FnNUdJa1BWMiIsInN1YiI6InBmcFk5amcyNWhmZEtQYURNeUtxZzVHSWtQVjIiLCJpYXQiOjE2ODM0Nzk4MTIsImV4cCI6MTY4MzQ4MzQxMiwiZW1haWwiOiJ4YWtpbjFAbGl2ZS5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJnb29nbGUuY29tIjpbIjEwNTUxOTIzNTI1MjQ0Mzk1OTA3MCJdLCJlbWFpbCI6WyJ4YWtpbjFAbGl2ZS5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJnb29nbGUuY29tIn19.HF3gnXr0cb959GighNyLGXJn9TsnGIfGwICDqrTXJqZIOA7KEoqhjNrBuiFBmjin1dHb4TbXjMTTjj93mu0NhztWUVRIOeAmUTfxlx0tOKtT5bhFfylayibY38rlURFSzJ3WZwP6MLvrSht1K5HGaSCNWHV-ILOigap3tlPHhIPS8PaIX7keCgb80fW52GuWX0vk8WHbtuPgKvvUPZRervpNimBAzvhjd');

INSERT INTO appuser (id, name, surname, firebase_token)
VALUES ('YcwbOWjeNOWrNCqMr36crsKEKP13', 'Facebook user', 'Demo', 'a);



 
----------- CASAS----------------

-- Casa 1
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Casa de campo', 'Hermosa casa de campo en las afueras de la ciudad', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 150000.00);
INSERT INTO t_house (id,m2)
VALUES (LASTVAL(),100);

-- Casa 2
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Casa en la montaÒa', 'Acogedora casa en la monta√±a con vista panor·mica', 'NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 200000.00);
INSERT INTO t_house (id,m2)
VALUES (LASTVAL(),500);

-- Casa 3
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Casa en la playa', 'Hermosa casa en la playa con acceso directo a la arena', 'SEMI_NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 350000.00);
INSERT INTO t_house (id,m2)
VALUES (LASTVAL(),750);

-- Casa 4
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Casa de lujo', 'Exclusiva casa de lujo con amplios espacios y acabados de alta calidad', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 750000.00);
INSERT INTO t_house (id,m2)
VALUES (LASTVAL(),75);

-- Casa 5
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Casa moderna', 'Impresionante casa de dise√±o moderno con grandes ventanales y mucha luz natural', 'NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 400000.00);
INSERT INTO t_house (id,m2)
VALUES (LASTVAL(),80);

-- Casa 6
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Casa en la colina', 'Amplia casa en la cima de la colina con impresionantes vistas de la ciudad', 'SEMI_NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 500000.00);
INSERT INTO t_house (id,m2)
VALUES (LASTVAL(),100);

-- Casa 7
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Casa de estilo colonial', 'Elegante casa de estilo colonial con hermoso jard√≠n', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 600000.00);
INSERT INTO t_house (id,m2)
VALUES (LASTVAL(),100);

-- Casa 8
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Casa con piscina', 'Casa con piscina climatizada y zona de barbacoa', 'NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 450000.00);
INSERT INTO t_house (id,m2)
VALUES (LASTVAL(),100);

-- Casa 9
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Casa de campo renovada', 'Casa de campo renovada con estilo r√∫stico y comodidades modernas', 'SEMI_NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 250000.00);
INSERT INTO t_house (id,m2)
VALUES (LASTVAL(),100);

-- Casa 10
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Casa en la ciudad', 'Casa ubicada en el centro de la ciudad con f·cil acceso a todos los servicios', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 300000.00);
INSERT INTO t_house (id,m2)
VALUES (LASTVAL(),100);

-- Casa 11
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Casa minimalista', 'Casa minimalista con dise√±o funcional y acabados de alta calidad', 'NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 550000.00);
INSERT INTO t_house (id,m2)
VALUES (LASTVAL(),100);


----------- Muebles ----------------

-- Mueble 1
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Sof· gris', 'Sof· de 3 plazas en color gris', 'SEMI_NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 350.00);
INSERT INTO t_furniture (id)
VALUES (LASTVAL());

-- Mueble 2
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Mesa de comedor', 'Mesa de comedor rectangular en madera', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 200.00);
INSERT INTO t_furniture (id)
VALUES (LASTVAL());

--  Mueble 3
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Silla de oficina', 'Silla ergon√≥mica para oficina', 'NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 150.00);
INSERT INTO t_furniture (id)
VALUES (LASTVAL());

--  Mueble4
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Armario blanco', 'Armario de 2 puertas en color blanco', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 300.00);
INSERT INTO t_furniture (id)
VALUES (LASTVAL());

--  Mueble 5
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Cama individual', 'Cama individual en color marr√≥n oscuro', 'SEMI_NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 250.00);
INSERT INTO t_furniture (id)
VALUES (LASTVAL());

--  Mueble 6
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Sof· cama', 'Sof· cama en color beige', 'NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 400.00);
INSERT INTO t_furniture (id)
VALUES (LASTVAL());

-- Mueble 7
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Mesa de centro', 'Mesa de centro redonda en cristal', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 100.00);
INSERT INTO t_furniture (id)
VALUES (LASTVAL());

-- Mueble 8
INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Silla de escritorio', 'Silla de escritorio con ruedas', 'SEMI_NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 120.00);
INSERT INTO t_furniture (id)
VALUES (LASTVAL());

-- Mueble 9

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Mesa de comedor', 'Mesa grande de madera para comedor', 'SEMI_NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 350.00);

INSERT INTO t_furniture (id)
VALUES (LASTVAL());

-- Mueble 10

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Sof· de cuero', 'Sof· grande de cuero negro', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 250.00);

INSERT INTO t_furniture (id)
VALUES (LASTVAL());

-- Mueble 11

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Armario de madera', 'Armario grande de madera oscura', 'NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 800.00);

INSERT INTO t_furniture (id)
VALUES (LASTVAL());


----------- Electrodomestico ----------------

-- Electrodomestico 1


INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Lavadora Samsung', 'Lavadora de carga frontal', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 300.00);

INSERT INTO t_appliances (id)
VALUES (LASTVAL());

-- Electrodomestico 2

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Refrigerador LG', 'Refrigerador de 2 puertas', 'NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 700.00);

INSERT INTO t_appliances (id)
VALUES (LASTVAL());

-- Electrodomestico 3

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Estufa Mabe', 'Estufa de gas con horno', 'SEMI_NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 450.00);

INSERT INTO t_appliances (id)
VALUES (LASTVAL());

-- Electrodomestico 4


INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Licuadora Oster', 'Licuadora de vaso', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 50.00);

INSERT INTO t_appliances (id)
VALUES (LASTVAL());

-- Electrodomestico 5

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Microondas Panasonic', 'Microondas de 1.2 pies c√∫bicos', 'NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 100.00);

INSERT INTO t_appliances (id)
VALUES (LASTVAL());

-- Electrodomestico 6

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Aspiradora Hoover', 'Aspiradora de trineo', 'SEMI_NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 80.00);

INSERT INTO t_appliances (id)
VALUES (LASTVAL());

-- Electrodomestico 7

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Batidora KitchenAid', 'Batidora de pedestal', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 120.00);

INSERT INTO t_appliances (id)
VALUES (LASTVAL());

-- Electrodomestico 8

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Secadora Whirlpool', 'Secadora el√©ctrica', 'NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 400.00);

INSERT INTO t_appliances (id)
VALUES (LASTVAL());

-- Electrodomestico 9


INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Lavadora Samsung', 'Lavadora autom·tica de carga frontal', 'SEMI_NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 800.00);

INSERT INTO t_appliances (id)
VALUES (LASTVAL());


-- Electrodomestico 10

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Licuadora Oster', 'Licuadora de 6 velocidades', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 40.00);

INSERT INTO t_appliances (id)
VALUES (LASTVAL());

-- Electrodomestico 11

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Aspiradora LG', 'Aspiradora de mano sin cable', 'NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 200.00);

INSERT INTO t_appliances (id)
VALUES (LASTVAL());

----------- Coches ----------------

-- Coche 1

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Mercedes Clase A', 'Coche en perfecto estado', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 12000.00);
INSERT INTO t_car (id,km) VALUES (LASTVAL(),2000);

-- Coche 2

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Volkswagen Golf', 'Coche como nuevo', 'SEMI_NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 8000.00);
INSERT INTO t_car (id,km) VALUES (LASTVAL(),2500);

-- Coche 3

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Ford Focus', 'Coche usado en buen estado', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 5000.00);
INSERT INTO t_car (id,km) VALUES (LASTVAL(),3000);

-- Coche 4

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('BMW Serie 3', 'Coche en excelente estado', 'NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 22000.00);
INSERT INTO t_car (id,km) VALUES (LASTVAL(),0);

-- Coche 5

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Audi A4', 'Coche en buen estado', 'SEMI_NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 13000.00);
INSERT INTO t_car (id,km) VALUES (LASTVAL(),2500);

-- Coche 6

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Renault Megane', 'Coche usado con algunos arreglos', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 3500.00);
INSERT INTO t_car (id,km) VALUES (LASTVAL(),4000);

-- Coche 7

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Toyota Corolla', 'Coche en excelente estado', 'NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 18000.00);
INSERT INTO t_car (id,km) VALUES (LASTVAL(),31000);

-- Coche 8

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Honda Civic', 'Coche semi nuevo', 'SEMI_NEW', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 11000.00);
INSERT INTO t_car (id,km) VALUES (LASTVAL(),15000);

-- Coche 9

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Citroen C3', 'Coche usado en buen estado', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 4000.00);
INSERT INTO t_car (id,km) VALUES (LASTVAL(),7000);

-- Coche 10

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Citroen C4', 'Coche usado en buen estado', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 4000.00);
INSERT INTO t_car (id,km) VALUES (LASTVAL(),12000);

-- Coche 11

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Citroen C5', 'Coche usado en buen estado', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 4000.00);
INSERT INTO t_car (id,km) VALUES (LASTVAL(),22000);

-- Coche 12

INSERT INTO t_product (name, description, state, owner, price)
VALUES ('Citroen C6', 'Coche usado en buen estado', 'SECOND_HAND', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 4000.00);
INSERT INTO t_car (id,km) VALUES (LASTVAL(),27000);

--Favoritos

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 1);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 3);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 4);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 7);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 12);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 19);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 22);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 24);


INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 29);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 33);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'pfpY9jg25hfdKPaDMyKqg5GIkPV2', 43);


INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'fAsTAzll1fbLRMczYPlOKOcdw6H3', 1);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'fAsTAzll1fbLRMczYPlOKOcdw6H3', 2);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'fAsTAzll1fbLRMczYPlOKOcdw6H3', 6);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'fAsTAzll1fbLRMczYPlOKOcdw6H3', 9);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'fAsTAzll1fbLRMczYPlOKOcdw6H3', 14);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'fAsTAzll1fbLRMczYPlOKOcdw6H3', 21);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'fAsTAzll1fbLRMczYPlOKOcdw6H3', 23);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'fAsTAzll1fbLRMczYPlOKOcdw6H3', 24);


INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'fAsTAzll1fbLRMczYPlOKOcdw6H3', 25);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'fAsTAzll1fbLRMczYPlOKOcdw6H3', 35);

INSERT INTO public.t_favourites
("date", appuser, product)
VALUES('2023-05-10 14:30:00', 'fAsTAzll1fbLRMczYPlOKOcdw6H3', 45);


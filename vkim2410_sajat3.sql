--DROP DATABASE IF EXISTS UtazasiIroda;
--go
--CREATE DATABASE UtazasiIroda;
--go

use UtazasiIroda;
go

DROP TABLE IF EXISTS Szallashelyek;
go
DROP TABLE IF EXISTS Ettermek;
go
DROP TABLE IF EXISTS Latvanyossagok;
go
DROP TABLE IF EXISTS Hotelek;
go
DROP TABLE IF EXISTS Foglalasok;
go
DROP TABLE IF EXISTS UtazasiHelyek;
go
DROP TABLE IF EXISTS Szemelyek;
go
DROP TABLE IF EXISTS Irodak;
go

CREATE TABLE Irodak(
	IrodaID int primary key,
	Cime nvarchar(max)
);

CREATE TABLE Szemelyek(
	CNP bigint primary key,
	Telefonszam int,
	Szuletett date,
	IrodaDolgozID int,
	IrodaFonokID int,
	foreign key (IrodaDolgozID) references Irodak(IrodaID),
	foreign key (IrodaFonokID) references Irodak(IrodaID)
);

CREATE TABLE UtazasiHelyek(
	HelyID int primary key,
	Orszag nvarchar(50),
	Kontinenes nvarchar(50)
);

CREATE TABLE Foglalasok(
	SzerzodesSzama int primary key,
	Ar int,
	SzerzodesDatuma date,
	Mikortol date,
	Mikorig date,
	CNP bigint,
	HelyID int,
	foreign key (CNP) references Szemelyek(CNP),
	foreign key (HelyID) references UtazasiHelyek(HelyID)
);

CREATE TABLE Hotelek(
	HotelID int primary key,
	Nev nvarchar(100),
	ReviewHotelek nvarchar(max),
	Csillagok int,
	HelyID int,
	foreign key(HelyId) references UtazasiHelyek(HelyID)
);

CREATE TABLE Latvanyossagok(
	LatvanyID int primary key,
	ReviewLatvany nvarchar(max),
	HelyID int,
	foreign key(HelyID) references UtazasiHelyek(HelyID)
);

CREATE TABLE Ettermek(
	EtteremID int primary key,
	ReviewEtterem nvarchar(max),
	HelyID int,
	foreign key(HelyID) references UtazasiHelyek(HelyID)
);

CREATE TABLE Szallashelyek(
	HotelID int,
	SzerzodesSzama int,
	primary key (HotelID,SzerzodesSzama),
	foreign key (HotelID) references Hotelek(HotelID),
	foreign key (SzerzodesSzama) references Foglalasok(SzerzodesSzama)
);

INSERT INTO Irodak (IrodaID, Cime)
VALUES 
('0001','Lakatos utca 64'),
('0002','Imre utca 12'),
('0003','Kalman ter 42'),
('0004','Oltului utca 57'),
('0005','Mihaly utca 6');

INSERT INTO Szemelyek (CNP, Telefonszam, Szuletett)
VALUES
('6050223314031','0754016346','2005-02-23'),
('5040313314031','0755656152','2004-03-13'),
('6011113314031','0778216983','2001-11-13'),
('6000111314031','0779164565','2000-01-11'),
('5030625314031','0754554555','2003-06-25');

INSERT INTO Szemelyek(CNP, Telefonszam, Szuletett, IrodaFonokID)
VALUES 
('6000101314031','0734546755','2000-01-01','0001'),
('6020223314031','0754555489','2002-02-23','0002'),
('5000201314031','0756654433','2000-02-01','0005');

INSERT INTO Szemelyek(CNP, Telefonszam, Szuletett, IrodaDolgozID)
VALUES 
('6010201314031','0743546445','2001-02-01','0001'),
('5010301314031','0753455553','2001-03-01','0003'),
('6040223314031','0754016346','2004-02-23','0002');

INSERT INTO UtazasiHelyek(HelyID, Orszag, Kontinenes)
VALUES 
('1001','UAE','Azsia'),
('1002','USA','Amerika'),
('1003','Olaszorszag','Europa'),
('1004','Egyiptom','Afrika'),
('1005','Ausztralia','Ausztralia');

INSERT INTO Foglalasok(SzerzodesSzama, Ar, SzerzodesDatuma, Mikortol, Mikorig, CNP, HelyID)
VALUES 
('2001','3500','2023-02-02','2024-06-23','2024-06-30','6050223314031','1003'),
('2002','1500','2023-04-04','2024-02-22','2024-02-27','6000111314031','1001'),
('2003','200','2023-08-08','2024-04-12','2024-04-22','5030625314031','1002'),
('2004','1000','2023-09-09','2024-10-10','2024-10-15','6011113314031','1004'),
('2005','2200','2023-12-12','2024-04-28','2024-05-03','5040313314031','1005');

INSERT INTO Hotelek(HotelID, Nev, ReviewHotelek, Csillagok, HelyID)
VALUES 
('3001','Prestige','Volt mar jobb is.','3','1004'),
('3002','Glamour','Fantasztikus elmeny!','5','1005'),
('3003','Hamptons','Masoknak is ajanlom.','4','1002'),
('3004','Anamaria','Patkanyok voltak a foldon.','1','1003'),
('3005','FeelGood','Gyerek barat hotel.','4','1001');

INSERT INTO Latvanyossagok(LatvanyID, ReviewLatvany, HelyID)
VALUES 
('4001','Luxusos felhokarcolo.','1001'),
('4002','Epitkeznek a kornyeken es zavaro.','1005'),
('4003','Turistas hely.','1004'),
('4004','Legszebb szokokut.','1003'),
('4005','Mindenkepp latogatni kell!','1002');

INSERT INTO Ettermek(EtteremID, ReviewEtterem, HelyID)
VALUES 
('5001','Legjobb pizza.','1003'),
('5002','Ne egyetek itt!','1005'),
('5003','Turistas hely.','1002'),
('5004','Dubai csokijuk nagyon jo.','1001'),
('5005','Tularazott.','1004');

INSERT INTO Szallashelyek(HotelID, SzerzodesSzama)
VALUES 
('3001','2004'),
('3002','2005'),
('3003','2003'),
('3004','2001'),
('3005','2002');


--sajat 3 

--ii)

--Módosítsuk a hotelek csillagbesorolását, ha az értékelés (ReviewHotelek) szövegében negatív kritika található.
UPDATE Hotelek
SET Csillagok = 2
WHERE ReviewHotelek LIKE '%Patkany%' OR ReviewHotelek LIKE '%rossz%';

--Az "Ausztrália" helyeket "Óceánia" kontinensre módosítjuk.
UPDATE UtazasiHelyek
SET Kontinenes = 'Oceania'
WHERE Kontinenes = 'Ausztralia';

--Emeljük meg 10%-kal azoknak a foglalásoknak az árát, amelyek hossza (napok száma) több mint 5 nap.
UPDATE Foglalasok
SET Ar = Ar * 1.10
WHERE DATEDIFF(day, Mikortol, Mikorig) > 5;

--Módosítsuk azoknak a személyeknek a telefonszámát, akiknek a születési évük páratlan.
UPDATE Szemelyek
SET Telefonszam = Telefonszam + 1
WHERE YEAR(Szuletett) % 2 = 1;

--Növeljük meg 20%-kal azoknak a foglalásoknak az árát, amelyek a legmagasabb csillagos hotelekhez tartoznak.
UPDATE F
SET Ar = Ar * 1.20
FROM Foglalasok F
	JOIN Szallashelyek S ON F.SzerzodesSzama = S.SzerzodesSzama
	JOIN Hotelek H ON S.HotelID = H.HotelID
WHERE H.Csillagok = 5;

--sajat 3

--iii)

--Töröljük azokat az éttermeket, amelyek értékelésében szerepel a "Ne egyetek itt!" szöveg.

DELETE FROM Ettermek
WHERE ReviewEtterem LIKE '%Ne egyetek itt!%';

--Töröljük azokat a személyeket, akiknek a telefonszáma 0754-gyel kezdődik.

DELETE FROM Szemelyek
WHERE Telefonszam LIKE '0754%';

--Töröljük azokat a hoteleket, amelyeknek csak 1 csillaguk van.

DELETE FROM Hotelek
WHERE Csillagok = 1;

--Töröljük azokat az utazási helyeket, amelyek kontinense "Ausztrália".

DELETE FROM UtazasiHelyek
WHERE Kontinenes = 'Ausztralia';

--Töröljük a Szallashelyek táblából azokat a szállásokat, amelyekhez tartozó hotel neve tartalmazza az "Anamaria" szót.

DELETE SH
FROM Szallashelyek SH
	JOIN Hotelek H ON SH.HotelID = H.HotelID
WHERE H.Nev LIKE '%Anamaria%';




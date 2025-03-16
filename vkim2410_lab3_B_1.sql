use AdventureWorks2022
go

--Jelenítsük meg a Production.Product táblában szereplő termékek nevét (Product.Name) és listaárát (Product.ListPrice), listaár szerint növekvő sorrendben!
--Kiírandó attribútumok:  Production.Product.Name, Production.Product.ListPrice


SELECT Production.Product.Name, Production.Product.ListPrice
FROM Production.Product
ORDER BY Product.ListPrice ASC


--Válasszuk ki az összes olyan terméket a Production.Product táblából, amelynek nevében bárhol szerepel a ‘Bike’ szó (Vigyázzunk: a szöveges literálok nagy- és kisbetű érzékenyek).
--Kiírandó attribútumok: Production.Product.Name

SELECT Production.Product.Name
FROM Production.Product
WHERE Production.Product.Name LIKE '%Bike%'

-- Válasszuk ki azoknak a vásárlóknak (Sales.Customer) a nevét (lsd. Person.Person tábla), akik 2014. január 1. és 2014. december 31. között helyeztek el olyan rendelést, melynek értéke több, mint 100.000 volt (lsd. Sales.SalesOrderHeader.Subtotal attribútum). Minden vásárló neve csak egyszer jelenjen meg az eredményben!
--Megj.  A személyek ID-ját a BusinessEntityID mezőben tárolja az adatbázis.
--Kiírandó attribútumok: Person.Person.FirstName, Person.Person.LastName

SELECT DISTINCT Person.Person.FirstName, Person.Person.LastName, Sales.SalesOrderHeader.SubTotal
FROM Person.Person
	JOIN Sales.Customer ON Sales.Customer.PersonID = Person.Person.BusinessEntityID
	JOIN Sales.SalesOrderHeader ON Sales.SalesOrderHeader.CustomerID = Sales.Customer.CustomerID 
WHERE Sales.SalesOrderHeader.SubTotal > 100000 AND Sales.SalesOrderHeader.OrderDate BETWEEN '2014.01.01' AND '2014.12.31' 

-- Adjuk meg a Production.Product táblában található termékek átlagárat (lsd. ListPrice oszlop). 
--Kiírandó attribútum: TermekAtlagAr

SELECT AVG(Production.Product.ListPrice) AS TermekekAtlagAr
FROM Production.Product

--Adjuk meg az egyes vásárlók (lsd. Person.Person tábla) összes rendelésének számát és rendeléseik összértékét (SalesOrderHeader.TotalDue mezőt figyelembe véve)! 
--Megj. Használnunk kell a Sales.Customer táblát is a Person.Person és 
--Sales.SalesOrderHeader táblák összekapcsolásakor.
--Kiírandó attribútumok: Person.Person.LastName, Person.Person.FirstName, OrderCount, TotalAmount

SELECT Person.Person.LastName, Person.Person.FirstName, COUNT(Sales.SalesOrderHeader.SalesOrderID) AS OrderCount, SUM(Sales.SalesOrderHeader.TotalDue) AS TotalAmount
FROM Person.Person
	JOIN Sales.Customer ON Sales.Customer.PersonID = Person.Person.BusinessEntityID
	JOIN Sales.SalesOrderHeader ON Sales.SalesOrderHeader.CustomerID = Sales.Customer.CustomerID 
GROUP BY Person.Person.LastName, Person.Person.FirstName

--Adjuk meg azon részlegeket (HumanResources.Department tábla), ahol dolgoznak nappali vagy éjszakai műszakban, (HumanResources.Shift tábla Name attribútuma szerint: ‘Day’, ‘Night’)! A részlegek csak egyszer szerepeljenek a felsorolásban!  
--Megj. A HumanResources.Employee és HumanResources.EmployeeDepartmentHistory táblákat is kell használni a lekérdezésben, hogy a kért eredményt megadhassuk.
--Kiírandó attribútumok: HumanResources.Department.Name

SELECT DISTINCT HumanResources.Department.Name
FROM HumanResources.Department
	JOIN HumanResources.EmployeeDepartmentHistory ON HumanResources.Department.DepartmentID = HumanResources.EmployeeDepartmentHistory.DepartmentID
	--JOIN HumanResources.Employee ON HumanResources.Employee.BusinessEntityID = HumanResources.EmployeeDepartmentHistory.BusinessEntityID
	JOIN HumanResources.Shift ON HumanResources.Shift.ShiftID = HumanResources.EmployeeDepartmentHistory.ShiftID
WHERE HumanResources.Shift.Name IN ('Day', 'Night')

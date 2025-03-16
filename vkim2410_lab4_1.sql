use AdventureWorks2022
go


--1. Adjuk meg azon termékeket, melyeknél nem adtuk meg, kiknek való (Production.Product tábla Style mezője “üres”), és amelyekből a legtöbb van raktáron (Production.ProductInventory tábla Quantity mezője alapján)! 
--Kiírandó attribútumok: Production.Product.Name, Production.Product.ProductNumber, 
--  Production.Product.Class, Production.Product.Weight,   
-- Production.ProductInventory.Quantity

SELECT P.Name, P.ProductNumber, P.Class, P.Weight, PI.Quantity, P.Style
FROM Production.Product P
	JOIN Production.ProductInventory PI ON P.ProductID = PI.ProductID
WHERE P.Style IS NULL AND PI.Quantity = (
        SELECT MAX(Quantity)
        FROM Production.ProductInventory PI2
        WHERE PI2.ProductID = P.ProductID
    )
ORDER BY PI.Quantity DESC  

--2. Adjuk meg, hogy az egyes alkategóriákba tartozó termékeket hány különböző helyen gyártják/raktározzák (Production.ProductInventory táblával dolgozzunk-LocationID alapján kapjuk meg a helyeket)! Csak azokat a termékeket vegyük figyelembe, melyek biztonsági készletszintje (lsd. Production.Product.SafetyStockLevel mező) kisebb, mint az átlagos készletszint (az összes terméket figyelembe véve) és csak azokat az alkategóriákat jelenítsük meg, melyek adatait 2008-ban módosították (Production.ProductSubcategory.ModifiedDate mező alapján) és amelyek termékei megtalálhatóak készleten (gyártanak/raktároznak) min 3 különböző helyen, helyek száma szerint csökkenő sorrendben!
--Kiírandó attribútumok: Production.ProductSubcategory.ProductSubcategoryID, 
--  Production.ProductSubcategory.Name, HelyekSzama

SELECT PS.ProductSubcategoryID, PS.Name, COUNT(DISTINCT PI.LocationID) AS HelyekSzama
FROM Production.ProductSubcategory PS
	JOIN Production.Product P ON PS.ProductSubcategoryID = P.ProductSubcategoryID
	JOIN Production.ProductInventory PI ON P.ProductID = PI.ProductID
WHERE P.SafetyStockLevel < (
        SELECT AVG(SafetyStockLevel)
        FROM Production.Product
    )
    AND PS.ModifiedDate BETWEEN '2008-01-01' AND '2008-12-31'
GROUP BY PS.ProductSubcategoryID, PS.Name
HAVING COUNT(DISTINCT PI.LocationID) >= 3
ORDER BY HelyekSzama DESC;

--3. Minden vásárló esetén adjuk meg a rendeléseinek számát és a legutolsó rendelésének dátumát, rendelések száma szerint csökkenő sorrendben! A lekérdezéshez szükség van a Person.Person, Sales.Customer és a Sales.SalesOrderHeader táblákra is!
--Megj. (A feladatot enélkül is elfogadjuk, csak kevesebb pontra.) Oldjuk meg azt is, hogy azokat a személyeket is írassuk ki, akik nem vásároltak egyszer sem (lehet, hogy nem is vásárlók-csak a Person táblában találhatóak meg az adataik).
--Kiírandó attribútumok: Person.Person.FirstName, Person.Person.LastName, 
--  UtolsoRendelesDatuma, RendelesekSzama

SELECT P.FirstName, P.LastName, 
    MAX(SOH.OrderDate) AS UtolsoRendelesDatuma,
    COUNT(SOH.SalesOrderID) AS RendelesekSzama
FROM Person.Person P
	LEFT JOIN Sales.Customer C ON P.BusinessEntityID = C.PersonID
	LEFT JOIN Sales.SalesOrderHeader SOH ON C.CustomerID = SOH.CustomerID
GROUP BY P.BusinessEntityID, P.FirstName, P.LastName
ORDER BY  RendelesekSzama DESC;

--4. Adjuk meg azon bankkártyákat (lsd. Sales.CreditCard tábla), amelyek típusa ’SuperiorCard’ (Sales.CreditCard.CardType mező alapján) és amelyeket nem használtak egyetlen rendelésnél sem (lsd. Sales.SalesOrderHeader tábla)! 
--A lekérdezést adjuk meg kétféleképpen: i) halmazművelettel, majd ii) anélkül!
--Kiírandó attribútumok: Sales.CreditCard.CreditCardID, Sales.CreditCard.CardNumber

--i)
SELECT CreditCardID, CardNumber
FROM Sales.CreditCard
WHERE CardType = 'SuperiorCard'

EXCEPT

SELECT C.CreditCardID, C.CardNumber
FROM Sales.CreditCard C
	JOIN Sales.SalesOrderHeader SOH ON C.CreditCardID = SOH.CreditCardID;

--ii)
SELECT C.CreditCardID, C.CardNumber
FROM Sales.CreditCard C
	LEFT JOIN Sales.SalesOrderHeader SOH ON C.CreditCardID = SOH.CreditCardID
WHERE C.CardType = 'SuperiorCard' AND SOH.CreditCardID IS NULL;

--5. Adjuk meg azon termékek számát, melyeket 15%-nál kisebb profittal árusítanak ((Product.ListPrice-Product.StandardCost)*100/Product.ListPrice < 15%) és amelyeket csak az ‘International Trek Center’ nevű beszállító szállít be!
--Kiírandó attribútumok: TermekekSzama

SELECT COUNT(*) AS TermekekSzama
FROM Production.Product P
	JOIN Purchasing.ProductVendor PV ON P.ProductID = PV.ProductID
	JOIN Purchasing.Vendor V ON PV.BusinessEntityID = V.BusinessEntityID
WHERE ((P.ListPrice - P.StandardCost) * 100 / P.ListPrice) < 15
	AND V.Name = 'International Trek Center'
GROUP BY P.ProductID
HAVING COUNT(DISTINCT PV.BusinessEntityID) = 1;

--6. Adjuk meg azon termékeket, amelyek standard költsége (Production.Product.StandardCost attribútum alapján) nagyobb, mint 40, és legalább 2 különleges ajánlatban szerepeltek (Sales.SpecialOfferProduct attribútum alapján)! A termékek csak egyszer jelenjenek meg a felsorolásban!
--Kiírandó attribútumok: Production.Product.Name, Production.Product.StandardCost

SELECT DISTINCT P.Name, P.StandardCost
FROM Production.Product P
	JOIN Sales.SpecialOfferProduct SOP ON P.ProductID = SOP.ProductID
WHERE P.StandardCost > 40
GROUP BY P.ProductID, P.Name, P.StandardCost
HAVING COUNT(DISTINCT SOP.SpecialOfferID) >= 2;


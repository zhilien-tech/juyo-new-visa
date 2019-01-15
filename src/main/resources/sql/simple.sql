
/*get_simplelist_data_apply*/
SELECT
	CONCAT(ta.firstName, ta.lastName) applicant,
	CONCAT(tap.firstNameEn, tap.lastNameEn) applicanten,
	tap.sex,
	ta.province,
	tap.passport passportNo,
	taoj.id applicatid,
	taoj.isMainApplicant,
	taoj.applicantId applyid,
	ta.telephone,
	ta.email
FROM
	t_applicant_order_jp taoj
INNER JOIN t_applicant ta ON taoj.applicantId = ta.id
LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
$condition



/*cityselectBygodeparturecity*/
SELECT
tc.*, count(*) AS count
FROM
t_city tc
LEFT JOIN t_order_trip_jp totj ON totj.goDepartureCity = tc.id
LEFT JOIN t_order_jp toj ON totj.orderId = toj.id
LEFT JOIN t_order tr ON toj.orderId = tr.id
$condition


/*cityselectBygotransferarrivedcity*/
SELECT
tc.*, count(*) AS count
FROM
t_city tc
LEFT JOIN t_order_trip_jp totj ON totj.gotransferarrivedcity = tc.id
LEFT JOIN t_order_jp toj ON totj.orderId = toj.id
LEFT JOIN t_order tr ON toj.orderId = tr.id
$condition


/*cityselectBygotransferdeparturecity*/
SELECT
tc.*, count(*) AS count
FROM
t_city tc
LEFT JOIN t_order_trip_jp totj ON totj.gotransferdeparturecity = tc.id
LEFT JOIN t_order_jp toj ON totj.orderId = toj.id
LEFT JOIN t_order tr ON toj.orderId = tr.id
$condition


/*cityselectBygoarrivedcity*/
SELECT
tc.*, count(*) AS count
FROM
t_city tc
LEFT JOIN t_order_trip_jp totj ON totj.goArrivedCity = tc.id
LEFT JOIN t_order_jp toj ON totj.orderId = toj.id
LEFT JOIN t_order tr ON toj.orderId = tr.id
$condition


/*cityselectBynewgodeparturecity*/
SELECT
tc.*, count(*) AS count
FROM
t_city tc
LEFT JOIN t_order_trip_jp totj ON totj.newgodeparturecity = tc.id
LEFT JOIN t_order_jp toj ON totj.orderId = toj.id
LEFT JOIN t_order tr ON toj.orderId = tr.id
$condition


/*cityselectBynewgoarrivedcity*/
SELECT
tc.*, count(*) AS count
FROM
t_city tc
LEFT JOIN t_order_trip_jp totj ON totj.newgoarrivedcity = tc.id
LEFT JOIN t_order_jp toj ON totj.orderId = toj.id
LEFT JOIN t_order tr ON toj.orderId = tr.id
$condition

/*cityselectBynewreturndeparturecity*/
SELECT
tc.*, count(*) AS count
FROM
t_city tc
LEFT JOIN t_order_trip_jp totj ON totj.newreturndeparturecity = tc.id
LEFT JOIN t_order_jp toj ON totj.orderId = toj.id
LEFT JOIN t_order tr ON toj.orderId = tr.id
$condition

/*cityselectBynewreturnarrivedcity*/
SELECT
tc.*, count(*) AS count
FROM
t_city tc
LEFT JOIN t_order_trip_jp totj ON totj.newreturnarrivedcity = tc.id
LEFT JOIN t_order_jp toj ON totj.orderId = toj.id
LEFT JOIN t_order tr ON toj.orderId = tr.id
$condition


/*cityselectByreturndeparturecity*/
SELECT
tc.*, count(*) AS count
FROM
t_city tc
LEFT JOIN t_order_trip_jp totj ON totj.returnDepartureCity = tc.id
LEFT JOIN t_order_jp toj ON totj.orderId = toj.id
LEFT JOIN t_order tr ON toj.orderId = tr.id
$condition


/*cityselectByreturntransferarrivedcity*/
SELECT
tc.*, count(*) AS count
FROM
t_city tc
LEFT JOIN t_order_trip_jp totj ON totj.returntransferarrivedcity = tc.id
LEFT JOIN t_order_jp toj ON totj.orderId = toj.id
LEFT JOIN t_order tr ON toj.orderId = tr.id
$condition


/*cityselectByreturntransferdeparturecity*/
SELECT
tc.*, count(*) AS count
FROM
t_city tc
LEFT JOIN t_order_trip_jp totj ON totj.returntransferdeparturecity = tc.id
LEFT JOIN t_order_jp toj ON totj.orderId = toj.id
LEFT JOIN t_order tr ON toj.orderId = tr.id
$condition

/*cityselectByreturnarrivedcity*/
SELECT
tc.*, count(*) AS count
FROM
t_city tc
LEFT JOIN t_order_trip_jp totj ON totj.returnArrivedCity = tc.id
LEFT JOIN t_order_jp toj ON totj.orderId = toj.id
LEFT JOIN t_order tr ON toj.orderId = tr.id
$condition


/*getMainapplyinfo*/
SELECT
ta.province,
ta.detailedAddress,
taoj.isMainApplicant
FROM
t_applicant ta
LEFT JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id
LEFT JOIN t_order_jp toj ON taoj.orderId = toj.id
INNER JOIN t_order tr ON toj.orderId = tr.id
WHERE
toj.id = @id AND taoj.isMainApplicant = 1

/*getSingleperson*/
SELECT
count(tr.orderNum) as ct
FROM
t_order tr
INNER JOIN t_order_jp toj ON toj.orderId = tr.id
LEFT JOIN t_applicant_order_jp taoj ON taoj.orderId = toj.id
LEFT JOIN t_company tcom ON tr.comId = tcom.id
LEFT JOIN t_company tcompany ON toj.sendsignid = tcompany.id
LEFT JOIN t_user tuser ON tr.salesOpid = tuser.id
LEFT JOIN t_customer tc ON tr.customerId = tc.id
LEFT JOIN (
SELECT
taoj.orderId,
tap.passport,
GROUP_CONCAT(
CONCAT(ta.firstname, ta.lastname) SEPARATOR 'төл'
) applyname
FROM
t_applicant ta
INNER JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id
LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
GROUP BY
taoj.orderId
) taj ON taj.orderId = toj.id
$condition
/*WHERE 
comId = @comid AND zhaobaoupdate = 1 
GROUP BY tr.orderNum
HAVING
ct = 1*/


/*ishaveMainapply*/
SELECT
taoj.isMainApplicant,
ta.id applicantid
FROM
t_applicant_order_jp taoj
INNER JOIN t_order_jp toj ON taoj.orderId = toj.id
INNER JOIN t_applicant ta ON taoj.applicantId = ta.id
WHERE
toj.id=@id AND taoj.isMainApplicant = 1

/*simpleJP_getUnitname*/
SELECT
tawj.name,
tawj.telephone,
tawj.address
FROM
t_applicant_work_jp tawj
LEFT JOIN t_applicant_order_jp taoj ON taoj.id = tawj.applicantId
INNER JOIN t_order_jp toj ON toj.id = taoj.orderId
LEFT JOIN t_order tr ON tr.id = toj.orderId
$condition


/*simpleJP_getUnittelephone*/
SELECT
name,
telephone,
address
FROM
t_applicant_work_jp
$condition


/*simpleJP_getUnitaddress*/
SELECT
name,
telephone,
address
FROM
t_applicant_work_jp
$condition



/*simpleJP_insertCustomertoAnother*/
insert into
t_customer(name,shortname,source,payType,isCustomerAdd,linkman,mobile,email)
select name,shortname,source,payType,isCustomerAdd,linkman,mobile,email
from t_customer
where id = @customerid


/*simpleJP_copyordertripjp*/
UPDATE 
	t_order_trip_jp totj,t_order_trip_jp totj2
SET
	totj.tripType=totj2.tripType,totj.tripPurpose=totj2.tripPurpose,totj.goDate=totj2.goDate,totj.goDepartureCity=totj2.goDepartureCity,totj.goArrivedCity=totj2.goArrivedCity,totj.goFlightNum=totj2.goFlightNum,totj.returnDate=totj2.returnDate,totj.returnDepartureCity=totj2.returnDepartureCity,totj.returnArrivedCity=totj2.returnArrivedCity,totj.returnFlightNum=totj2.returnFlightNum,totj.gotransferarrivedcity=totj2.gotransferarrivedcity,totj.gotransferdeparturecity=totj2.gotransferdeparturecity,totj.returntransferarrivedcity=totj2.returntransferarrivedcity,totj.returntransferdeparturecity=totj2.returntransferdeparturecity,totj.gotransferflightnum=totj2.gotransferflightnum,totj.returntransferflightnum=totj2.returntransferflightnum,totj.newgodeparturecity=totj2.newgodeparturecity,totj.newgoarrivedcity=totj2.newgoarrivedcity,totj.newgoflightnum=totj2.newgoflightnum,totj.newreturndeparturecity=totj2.newreturndeparturecity,totj.newreturnarrivedcity=totj2.newreturnarrivedcity,totj.newreturnflightnum=totj2.newreturnflightnum
$condition


/*simpleJP_downloadExcel*/
SELECT
tr.id,
tr.orderNum,
tr.cityId,
toj.visaType,
CONCAT(ta.firstName,ta.lastName) applyname,
CONCAT(ta.firstNameEn,' ',ta.lastNameEn) applynameen,
ta.sex,
tap.passport,
ta.province,
(
SELECT
count(*)
FROM
t_applicant_order_jp
WHERE
orderId = toj.id
) peopleNumber,
DATE_FORMAT(totj.goDate, '%Y-%m-%d') godate,
DATE_FORMAT(totj.returnDate,'%Y-%m-%d') returndate,
totj.goFlightNum,
totj.newgodeparturecity,
totj.gotransferdeparturecity,
totj.newreturnarrivedcity,
totj.returntransferarrivedcity,
totj.goArrivedCity,
totj.newgoflightnum,
totj.newgoarrivedcity,
totj.gotransferflightnum,
totj.returntransferflightnum,
totj.newreturndeparturecity,
totj.newreturnflightnum,
totj.returnFlightNum,
totj.returnDepartureCity,
totj.goDepartureCity,
totj.returnArrivedCity,
taoj.mainRelation,
DATE_FORMAT(tr.sendVisaDate,'%Y-%m-%d') sendvisadate,
tu.`name`
FROM
t_applicant ta
LEFT JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id
LEFT JOIN t_order_jp toj ON taoj.orderId = toj.id
LEFT JOIN t_order tr ON toj.orderId = tr.id
LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
LEFT JOIN t_order_trip_jp totj ON totj.orderId = toj.id
LEFT JOIN t_user tu ON tr.salesOpid = tu.id
LEFT JOIN t_customer tc ON tr.customerId = tc.id
$condition

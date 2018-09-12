
/*get_simplelist_data_apply*/
SELECT
	CONCAT(ta.firstName, ta.lastName) applicant,
	CONCAT(tap.firstNameEn, tap.lastNameEn) applicanten,
	tap.sex,
	ta.province,
	tap.passport passportNo,
	taoj.id applicatid,
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

/*cityselectBygoarrivedcity*/
SELECT
tc.*, count(*) AS count
FROM
t_city tc
LEFT JOIN t_order_trip_jp totj ON totj.goArrivedCity = tc.id
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
$condition
/*WHERE 
comId = @comid AND zhaobaoupdate = 1 
GROUP BY tr.orderNum
HAVING
ct = 1*/
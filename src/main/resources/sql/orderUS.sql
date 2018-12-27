/*orderUS_orderNum_nowDate*/
SELECT
	tou.id orderId,
	tou.ordernumber,
	tou.createtime
FROM
	t_order_us tou 
WHERE
	DATE( tou.createtime ) = DATE( NOW( ) )
ORDER by tou.createtime DESC

/*orderUS_listData*/
SELECT
	tou.id orderid,
	tou.opid,
	tu.usertype,
	tou.ispayed,
	tou.cityid,
	tou.isdisable,
	tou.ordernumber,
	taj.staffid staffid,
	tou.`status` orderstatus,
	taj.applicantName,
	taj.telephone,
	taj.passport,
	taj.email,
	taj.type,
	taj.content,
	taj.aacode,
	taj.Interviewdate
FROM
	t_order_us tou
	LEFT JOIN t_user tu ON tu.id = tou.opid
	INNER JOIN (
		SELECT
		tasou.orderid stafforderid,
		tasb.id staffid,
		tasb.Interviewdate,
		tasp.passport,
		tasb.telephone,
		tasb.email,
		taspu.type,
		tasb.aacode,
		taspu.content,
		GROUP_CONCAT( tasb.telephone SEPARATOR 'төл' ) phone,
		GROUP_CONCAT( CONCAT( tasp.firstname, tasp.lastname ) SEPARATOR 'төл' ) applicantName 
		FROM
		t_app_staff_order_us tasou
		INNER JOIN t_app_staff_basicinfo tasb ON tasou.staffid = tasb.id
		LEFT JOIN t_app_staff_passport tasp ON tasp.staffid = tasb.id
		LEFT JOIN t_app_staff_paperwork_us taspu ON taspu.staffid = tasb.id
		GROUP BY tasou.orderid
	)taj ON taj.stafforderid = tou.id
	$condition

/*orderUS_listData_staff*/
SELECT
	tasb.id staffid,
	tasou.id orderid,
	CONCAT(tasp.firstname, tasp.lastname) applicantname ,
	tasb.telephone,
	tasb.email,
	tasb.aacode,
	tasp.passport,
	taspu.type,
	taspu.content
FROM
	t_app_staff_order_us tasou
	INNER JOIN t_app_staff_basicinfo tasb ON tasou.staffid = tasb.id
	LEFT JOIN t_app_staff_passport tasp ON tasp.staffid = tasb.id
	LEFT JOIN t_app_staff_paperwork_us taspu ON taspu.staffid = tasb.id
	$condition
	
/*orderUS_getLogs*/
SELECT
	tu.`name`,
	toul.orderstatus,
	toul.createtime
FROM
	t_order_us_logs toul
	LEFT JOIN t_user tu ON tu.id = toul.opid
	LEFT JOIN t_order_us tou ON tou.id = toul.orderid
WHERE
	tou.id = @id

/*orderUS_getFollows*/
SELECT
	touf.id,
	touf.solveid,
	tu.`name`,
	touf.createtime,
	touf.solvetime,
	touf.content,
	touf.`status`
FROM
	t_order_us_followup touf
	LEFT JOIN t_user tu ON touf.userid = tu.id
	LEFT JOIN t_order_us tou ON tou.id = touf.orderid
WHERE
	tou.id = @id
	ORDER BY touf.createtime DESC
	
	
/*orderUS_mobile_listdata*/
SELECT
tou.id orderusid,
tou.`status` orderstatus,
tou.ordernumber,
tou.cityid,
tasb.id staffid,
CONCAT(tasb.firstname,tasb.lastname) staffname
FROM
t_order_us tou
INNER JOIN t_app_staff_order_us tasou ON tasou.orderid = tou.id
INNER JOIN t_app_staff_basicinfo tasb ON tasou.staffid = tasb.id
LEFT JOIN t_user tu ON tasb.userid = tu.id
WHERE
tu.mobile = @telephone
ORDER BY tou.createtime DESC

/*orderUS_mobile_getProvince*/
SELECT
province
FROM
t_idcard
GROUP BY province
ORDER BY id

/*orderUS_mobile_getCity*/
SELECT
city
FROM
t_idcard
WHERE
province=@province
AND
city!=''
GROUP BY city
ORDER BY id

/*orderUS_PC_getCredentials*/
SELECT
*
FROM
t_app_staff_credentials
WHERE
staffid=@staffid


/*orderUS_PC_getGocountrys*/
SELECT
*
FROM
t_app_staff_gocountry
WHERE
staffid=@staffid


/*orderUS_getSomeState*/
SELECT
tsu.id,
tsu.`name`
FROM
t_hotel_us thu
LEFT JOIN t_city_us tcu ON tcu.id = thu.cityid
LEFT JOIN t_state_us tsu ON tcu.stateid = tsu.id
GROUP BY tsu.id

/*orderUS_getSomeCity*/
SELECT
tcu.id,
tcu.cityname
FROM
t_hotel_us thu
LEFT JOIN t_city_us tcu ON tcu.id = thu.cityid
LEFT JOIN t_state_us tsu ON tcu.stateid = tsu.id
WHERE tsu.id=@stateid
GROUP BY cityname


/*orderusmobile_getbasic*/
SELECT
tasb.*,
tasf.divorcedate,
tasf.marrieddate
FROM
t_app_staff_basicinfo tasb
LEFT JOIN t_app_staff_familyinfo tasf ON tasf.staffid = tasb.id
WHERE
staffid=@staffid

/*orderusmobile_getpassport*/
SELECT
*
FROM
t_app_staff_passport
WHERE
staffid=@staffid


/*orderusmobile_getworkandeducation*/
SELECT
*
FROM
t_app_staff_work_education_training
WHERE
staffid = @staffid


/*orderusmobile_getbeforework*/
SELECT
*
FROM
t_app_staff_beforework
WHERE
staffid = @staffid


/*orderusmobile_getbeforeeducation*/
SELECT
*
FROM
t_app_staff_beforeeducation
WHERE
staffid = @staffid



/*orderusmobile_getprevioustrip*/
SELECT
*
FROM
t_app_staff_previoustripinfo
WHERE
staffid = @staffid
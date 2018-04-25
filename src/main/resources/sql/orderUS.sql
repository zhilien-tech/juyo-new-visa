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
	taj.aacode
	
FROM
	t_order_us tou
	LEFT JOIN t_user tu ON tu.id = tou.opid
	INNER JOIN (
		SELECT
		tasou.orderid stafforderid,
		tasb.id staffid,
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
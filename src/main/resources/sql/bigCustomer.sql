/*bigCustomer_staff_list*/
SELECT
	tasb.id staffid,
	CONCAT( tasb.firstname, tasb.lastname ) NAME,
	tasb.telephone,
	tasb.email,
	tasb.department,
	tasb.job,
	tasp.passport,
	tasp.id passportId
FROM
	t_app_staff_basicinfo tasb
	INNER JOIN t_app_staff_passport tasp ON tasp.staffId = tasb.id
	$condition
	
/*bigCustomer_staff_passport*/
SELECT
	tasp.id possportId,
	tasb.id staffId,
	tasp.firstName,
	tasp.firstNameEn,
	tasp.lastName,
	tasp.lastNameEn,
	tasp.type,
	tasp.passport,
	tasp.passportUrl,
	tasp.sex,
	tasp.sexEn,
	tasp.birthAddress,
	tasp.birthAddressEn,
	tasp.birthday,
	tasp.issuedPlace,
	tasp.issuedPlaceEn,
	tasp.issuedDate,
	tasp.validType,
	tasp.validStartDate,
	tasp.validEndDate,
	tasp.issuedOrganization,
	tasp.issuedOrganizationEn
FROM
	t_app_staff_passport tasp
	INNER JOIN t_app_staff_basicinfo tasb ON tasb.id = tasp.staffId
	$condition

/*bigCustomer_staff_checkPassport*/
SELECT
	tasb.id staffid,
	tasp.id passportid,
	tasb.comid,
	CONCAT( tasb.firstname, tasb.lastname ) NAME,
	tasp.passport 
FROM
	t_app_staff_basicinfo tasb
	INNER JOIN t_app_staff_passport tasp ON tasp.staffId = tasb.id 
	$condition

/*bigCustomer_events_count*/
SELECT
	tase.eventsId,
	tae.eventsName,
	tae.pictureUrl,
	tae.dueDate,
	( SELECT count( * ) FROM t_app_staff_events tase WHERE tase.eventsId = tae.id ) signUpCount 
FROM
	t_app_events tae
	LEFT JOIN t_app_staff_events tase ON tae.id = tase.eventsId
	$condition
GROUP BY
	tae.pictureUrl
	
/*bigCustomer_staff_visa_list*/
SELECT
	tos.id orderid,
	tos.ordernumber,
	tos.STATUS
FROM
	t_order_us tos
	LEFT JOIN t_app_staff_order_us tasou ON tasou.orderid = tos.id
	LEFT JOIN t_app_staff_basicinfo tasb ON tasb.id = tasou.staffid
	$condition

/*bigCustomer_order_applicant_list*/
SELECT
	tasb.id staffid,
	CONCAT( tasb.firstname, tasb.lastname ) staffname,
	tasb.telephone,
	tasb.cardnum,
	tasb.email,
	tasb.visastatus,
	tasp.passport,
	tasou.orderid,
	tos.ordernumber,
	tasb.aacode,
	tos.id
FROM
	t_app_staff_basicinfo tasb
	LEFT JOIN t_app_staff_passport tasp ON tasb.id = tasp.staffid
	INNER JOIN t_app_staff_order_us tasou ON tasou.staffid = tasb.id
	LEFT JOIN t_order_us tos ON tos.id = tasou.orderid
	$condition
	
/*bigCustomer_order_applicant_list2*/
SELECT
	tasb.id staffid,
	CONCAT( tasb.firstname, tasb.lastname ) staffname,
	tasb.telephone,
	tasb.cardnum,
	tasb.email,
	tasb.visastatus,
	tasp.passport,
	tasou.orderid,
	tos.ordernumber,
	tasb.aacode,
	tos.id
FROM
	t_app_staff_basicinfo tasb
	LEFT JOIN t_app_staff_passport tasp ON tasb.id = tasp.staffid
	INNER JOIN t_app_staff_order_us tasou ON tasou.staffid = tasb.id
	LEFT JOIN t_order_us tos ON tos.id = tasou.orderid
	$condition

/*t_app_paperwork_US_info*/
SELECT
	taspu.id,
	taspu.staffid,
	taspu.type,
	taspu.content,
	taspu.count,
	taspu.opid,
	taspu.createtime,
	taspu.updatetime,
	taspu.realinfo,
	taspu.status
FROM
	t_app_staff_paperwork_us taspu
	
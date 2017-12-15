/*myvisa_inProcessVisa_list*/
SELECT
	taoj.applicantId,
	CONCAT( ta.firstName, ta.lastName ) applicantname,
	ta.telephone,
	tap.passport,
	taoj.orderId orderJpId,
	torj.id orderId,
	torj.ordernum,
	torj.STATUS orderstatus,
	torj.sendVisaDate,
	torj.outVisaDate 
FROM
	t_applicant_order_jp taoj
	INNER JOIN t_applicant ta ON taoj.applicantId = ta.id
	LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
	LEFT JOIN ( SELECT toj.id orderjpid, tor.* FROM t_order_jp toj LEFT JOIN t_order tor ON tor.id = toj.orderid ) torj ON torj.orderjpid = taoj.orderId
	$condition
	
/*myvisa_applicant_by_id*/
SELECT
	ta.userid,
	taoj.applicantId,
	CONCAT( ta.firstName, ta.lastName ) applicantname,
	ta.telephone,
	ta.status applicantstatus,
	tap.passport,
	taoj.orderid,
	tor.ordernum,
	tor.STATUS orderstatus,
	tor.sendVisaDate,
	tor.outVisaDate 
FROM
	t_applicant_order_jp taoj
	INNER JOIN t_applicant ta ON taoj.applicantId = ta.id
	LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
	LEFT JOIN t_order tor ON tor.id = taoj.orderid 
	$condition
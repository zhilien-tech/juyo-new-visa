/*myvisa_inProcessVisa_list*/
SELECT
	taoj.applicantId,
	taoj.isSameLinker,
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

/*myvisa_japan_visa_list_data*/
SELECT
	toj.id,
	tr.orderNum japanNumber,
	toj.acceptDesign number,
	DATE_FORMAT(tr.sendVisaDate, '%Y-%m-%d') sendingTime,
	DATE_FORMAT(tr.outVisaDate, '%Y-%m-%d') signingTime,
	tr.STATUS japanState,
	toj.visastatus visastatus,
	(
		SELECT
			count(*)
		FROM
			t_applicant_order_jp
		WHERE
			orderId = toj.id
	) peopleNumber
FROM
	t_order tr
INNER JOIN t_order_jp toj ON toj.orderId = tr.id
LEFT JOIN t_customer tc ON tr.customerId = tc.id
LEFT JOIN (
	SELECT
		taoj.orderId,
		GROUP_CONCAT(
			CONCAT(ta.firstname, ta.lastname) SEPARATOR 'төл'
		) applyname
	FROM
		t_applicant ta
	INNER JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id
	GROUP BY
		taoj.orderId
) taj ON taj.orderId = toj.id
$condition

/*myvisa_japan_visa_list_data_apply*/
SELECT
	CONCAT(ta.firstName, ta.lastName) applicant,
	tap.passport passportNo,
	taoj.id applicatid,
	ta.telephone,
	ta.email,
	tavpj.type dataType,
	tavpj. DATA DATA,
	tavpj. STATUS STATUS
FROM
	t_applicant_order_jp taoj
INNER JOIN t_applicant ta ON taoj.applicantId = ta.id
LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
LEFT JOIN (
	SELECT
		applicantId,
		type,
		GROUP_CONCAT(
			(
				CASE
				WHEN STATUS = 0 THEN
						realInfo
				ELSE
					CONCAT(
						'<font color="blue">',
						realInfo,
						'</font>'
					)
				END
			) SEPARATOR '、'
		) DATA,
		STATUS
	FROM
		t_applicant_visa_paperwork_jp
	GROUP BY
		applicantId
) tavpj ON tavpj.applicantId = taoj.id
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
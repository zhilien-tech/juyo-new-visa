
/*
 SELECT
	toj.id,
	taj.passport,
	taj.applyname,
	tr.orderNum japanNumber,
	toj.acceptDesign number,
	DATE_FORMAT(tr.sendVisaDate, '%Y-%m-%d') sendingTime,
	DATE_FORMAT(tr.outVisaDate, '%Y-%m-%d') signingTime,
	tr.STATUS japanState,
	tu.name username,
	tcom.name comname,
	(
	SELECT
	count(*)
	FROM
	t_applicant_order_jp
	WHERE
	orderId = toj.id
	) peopleNumber,
	tr.isDisabled,
	tr.zhaobaocomplete,
	tr.zhaobaoupdate,
	toj.visastatus visastatus,
	toj.visaType,
	toj.acceptDesign,
	tr.id orderid
FROM
	t_order tr
INNER JOIN t_order_jp toj ON toj.orderId = tr.id
LEFT JOIN t_customer tc ON tr.customerId = tc.id
LEFT JOIN t_user tu ON tr.userId = tu.id
LEFT JOIN t_company tcom ON toj.sendsignid = tcom.id
LEFT JOIN (
	SELECT
		taoj.orderId,
		tap.passport,
		GROUP_CONCAT(
			CONCAT(ta.firstname, ta.lastname) SEPARATOR 'төл'
		) applyname,
		GROUP_CONCAT(
			CONCAT(ta.firstnameen, ta.lastnameen) SEPARATOR 'төл'
		) applynameen
	FROM
		t_applicant ta
	INNER JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id
	LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
	GROUP BY
		taoj.orderId
) taj ON taj.orderId = toj.id
 */

/*get_Japan_dijie_list_data*/
SELECT
	toj.id,
	
	tr.orderNum japanNumber,
	toj.acceptDesign number,
	DATE_FORMAT(tr.sendVisaDate, '%Y-%m-%d') sendingTime,
	DATE_FORMAT(tr.outVisaDate, '%Y-%m-%d') signingTime,
	tr.STATUS japanState,
	tu.name username,
	tcom.name comname,
	(
	SELECT
	count(*)
	FROM
	t_applicant_order_jp
	WHERE
	orderId = toj.id
	) peopleNumber,
	tr.isDisabled,
	tr.zhaobaocomplete,
	tr.zhaobaoupdate,
	toj.visastatus visastatus,
	toj.visaType,
	toj.acceptDesign,
	tr.id orderid
FROM
	t_order tr
INNER JOIN t_order_jp toj ON toj.orderId = tr.id
LEFT JOIN t_customer tc ON tr.customerId = tc.id
LEFT JOIN t_user tu ON tr.userId = tu.id
LEFT JOIN t_company tcom ON toj.sendsignid = tcom.id
$condition
/*get_Japan_dijie_list_data_paper*/
SELECT
	tr.id
FROM
	t_order tr
INNER JOIN t_order_jp toj ON toj.orderId = tr.id
LEFT JOIN t_customer tc ON tr.customerId = tc.id
LEFT JOIN t_user tu ON tr.userId = tu.id
LEFT JOIN t_company tcom ON toj.sendsignid = tcom.id
$condition
/*get_japan_dijie_list_apply_data*/
SELECT
	ta.id,
	CONCAT(ta.firstName, ta.lastName) applicant,
	CONCAT(
		ta.firstNameEn,
		ta.lastNameEn
	) applicanten,
	ta.sex,
	ta.province,
	DATE_FORMAT(ta.birthday, '%Y-%m-%d') birthday,
	tap.passport
FROM
	t_applicant ta
LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
INNER JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id
$condition

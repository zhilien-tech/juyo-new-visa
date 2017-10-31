/*get_japan_visa_list_data*/
SELECT
	toj.id,
	tr.orderNum japanNumber,
	toj.acceptDesign number,
	DATE_FORMAT(tr.sendVisaDate, '%Y-%m-%d') sendingTime,
	DATE_FORMAT(tr.outVisaDate, '%Y-%m-%d') signingTime,
	tr. STATUS japanState,
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
INNER JOIN t_customer tc ON tr.customerId = tc.id
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
		ta.id
) taj ON taj.orderId = toj.id
$condition

/*get_japan_visa_list_data_apply*/
SELECT
	CONCAT(ta.firstName, ta.lastName) applicant,
	tap.passport passportNo,
	tavpj.type dataType,
	tavpj.data data
FROM
	t_applicant_order_jp taoj
INNER JOIN t_applicant ta ON taoj.applicantId = ta.id
LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
LEFT JOIN (
	SELECT
		applicantId,
		type,
		GROUP_CONCAT(CAST(content AS CHAR) SEPARATOR '、') data
	FROM
		t_applicant_visa_paperwork_jp
) tavpj ON tavpj.applicantId = taoj.id
$condition
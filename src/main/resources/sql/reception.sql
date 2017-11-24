/*reception_list*/
SELECT
	toj.id,
	tr.orderNum orderNumber,
	toj.acceptDesign number,
	tr.STATUS orderStatus

FROM
	t_order tr
	INNER JOIN t_order_jp toj ON toj.orderId = tr.id 

	INNER JOIN t_customer tc ON tr.customerId = tc.id 

	LEFT JOIN (
		SELECT
			taoj.orderId,
			GROUP_CONCAT( ta.status SEPARATOR 'төл' ) applicantStatus,
			tap.passport passportNum,
			ta.telephone,
			GROUP_CONCAT( ta.telephone SEPARATOR 'төл' ) phone,
			GROUP_CONCAT( CONCAT( ta.firstname, ta.lastname ) SEPARATOR 'төл' ) applicantName 
		FROM
			t_applicant ta
			INNER JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id 

			LEFT JOIN t_applicant_passport tap ON tap.applicantId=ta.id 

		GROUP BY
			taoj.orderId
	) taj ON taj.orderId = toj.id 
$condition

/*reception_list_data*/
SELECT
	CONCAT(ta.firstName, ta.lastName) applicant,
	tap.passport passportNo,
	taoj.id applicantid,
	tae.expressNum,
	tae.expressType,
	ta.telephone,
	ta.email,
	tavpj.type dataType,
	tavpj.data data
FROM
	t_applicant_order_jp taoj
INNER JOIN t_applicant ta ON taoj.applicantId = ta.id
LEFT JOIN t_applicant_express tae ON tae.applicantId = ta.id
LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
LEFT JOIN (
	SELECT
		applicantId,
		type,
		GROUP_CONCAT(realInfo SEPARATOR '、') data
	FROM
		t_applicant_front_paperwork_jp
	GROUP BY applicantId
) tavpj ON tavpj.applicantId = taoj.id
$condition


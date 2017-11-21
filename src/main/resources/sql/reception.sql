/*reception_list*/
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


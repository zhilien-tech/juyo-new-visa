/*get_applyinfo_from_filedown_by_orderid_jp*/
SELECT
	ta.*,
	tap.passport passportNo,
	tap.issuedPlace,
	tap.issuedDate,
	tap.issuedOrganization,
	tap.validEndDate passportenddate,
	taoj.id applicatid,
	tavpj.type dataType,
	tavpj.data data,
	tawj.occupation,
	tawj.telephone workphone,
	tawj.address workaddress,
	tawj.`name` workname
FROM
	t_applicant_order_jp taoj
INNER JOIN t_applicant ta ON taoj.applicantId = ta.id
LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
LEFT JOIN (
	SELECT
		applicantId,
		type,
		GROUP_CONCAT(realInfo SEPARATOR '、') data
	FROM
		t_applicant_visa_paperwork_jp
	GROUP BY applicantId
) tavpj ON tavpj.applicantId = taoj.id
LEFT JOIN t_applicant_work_jp tawj ON tawj.applicantId = taoj.id
$condition
/*get_applyinfo_from_filedown_by_orderid_jp*/
SELECT
	ta.*, tap.passport passportNo,
	tap.issuedPlace,
	tap.issuedDate,
	tap.issuedOrganization,
	tap.validEndDate passportenddate,
	taoj.id applicatid,
	taoj.relationRemark,
	tavpj.type dataType,
	tavpj. DATA DATA,
	tawj.occupation,
	tawj.careerstatus,
	tawj.telephone workphone,
	tawj.address workaddress,
	tawj.`name` workname,
	tawlj.wealthtype,
	tawlj.wealthcontent,
	tavoi.hotelname,
	tavoi.vouchname,
	tavoi.invitename,
	tavoi.traveladvice
FROM
	t_applicant_order_jp taoj
INNER JOIN t_applicant ta ON taoj.applicantId = ta.id
LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
LEFT JOIN (
	SELECT
		applicantId,
		type,
		GROUP_CONCAT(realInfo SEPARATOR '、') DATA
	FROM
		t_applicant_visa_paperwork_jp
	GROUP BY
		applicantId
) tavpj ON tavpj.applicantId = taoj.id
LEFT JOIN t_applicant_work_jp tawj ON tawj.applicantId = taoj.id
LEFT JOIN (
	SELECT
		GROUP_CONCAT(type SEPARATOR '\n') wealthtype,
		GROUP_CONCAT(details SEPARATOR '\n') wealthcontent,
		applicantId
	FROM
		t_applicant_wealth_jp
	GROUP BY
		applicantId
) tawlj ON tawlj.applicantId = taoj.id
LEFT JOIN t_applicant_visa_other_info tavoi ON tavoi.applicantid = taoj.id
$condition
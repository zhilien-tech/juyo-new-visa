/*select_simulate_jp_order*/
SELECT
	tr.*, toj.id orderjpid,
	toj.visaType,
	toj.sendsignid,
	toj.groundconnectid,
	toj.isVisit,
	toj.excelurl
FROM
	t_order tr
INNER JOIN t_order_jp toj ON tr.id = toj.orderId
$condition
/*get_applicantinfo_simulate_from_order*/
SELECT
	tr.*
FROM
	t_applicant tr
INNER JOIN t_applicant_order_jp taoj ON taoj.applicantId = tr.id
LEFT JOIN t_applicant_passport tap ON tap.applicantId = tr.id
where taoj.orderid=@orderid
ORDER BY taoj.isMainApplicant DESC, taoj.id ASC
/*get_aftermarket_list_data*/
SELECT
	tr.*,
	DATE_FORMAT(tr.sendVisaDate, '%Y-%m-%d') sendingTime,
	DATE_FORMAT(tr.outVisaDate, '%Y-%m-%d') signingTime,
	toj.id orderjpid
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

/*get_aftermarket_list_applicat_data*/
SELECT
	ta.*,
	toj.orderid,
	taoj.id applicatid,
	tabj.linkman,
	tabj.telephone backtelephone,
	tabj.expressAddress 
FROM
	t_applicant ta
	INNER JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id
	LEFT JOIN t_applicant_backmail_jp tabj ON tabj.applicantId = taoj.id
	LEFT JOIN t_order_jp toj ON toj.id = taoj.orderid
$condition

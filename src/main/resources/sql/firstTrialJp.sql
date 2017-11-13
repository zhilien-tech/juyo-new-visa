/*firstTrialJp_list*/
SELECT
	toj.id,
	tr.orderNum japanNumber,
	toj.acceptDesign number,
	DATE_FORMAT( tr.goTripDate, '%Y-%m-%d' ) goTripTime,
	DATE_FORMAT( tr.backTripDate, '%Y-%m-%d' ) backTripTime,
	tr.STATUS japanState,
	( SELECT count( * ) FROM t_applicant_order_jp WHERE orderId = toj.id ) peopleNumber 
FROM
	t_order tr
	INNER JOIN t_order_jp toj ON toj.orderId = tr.id
	INNER JOIN t_customer tc ON tr.customerId = tc.id
	LEFT JOIN (
				SELECT
					taoj.orderId,
					GROUP_CONCAT( ta.status SEPARATOR 'төл' ) applystatus,
					tap.passport passportNum,
					GROUP_CONCAT( CONCAT( ta.firstname, ta.lastname ) SEPARATOR 'төл' ) applyname 
				FROM
					t_applicant ta
					INNER JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id
					INNER JOIN t_applicant_passport tap ON tap.applicantId=ta.id
				GROUP BY
					taoj.orderId
	) taj ON taj.orderId = toj.id
$condition
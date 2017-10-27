/*orderJp_list*/
SELECT 
o.orderNum,
o.number,
o.`status`,
o.comShortName,
c.source,
o.linkman,
o.telephone,
(
		SELECT
			GROUP_CONCAT(
				cast(a.id AS CHAR) SEPARATOR ','
			)
		FROM
			t_applicant a
		WHERE
			aoj.applicantId = a.id
	) AS applicants
FROM
t_order o
LEFT JOIN t_customer c ON o.customerId = c.id
LEFT JOIN t_order_jp oj ON oj.orderId = o.id
LEFT JOIN t_applicant_order_jp aoj ON aoj.orderId = oj.id
LEFT JOIN t_applicant a ON aoj.applicantId = a.id
LEFT JOIN t_applicant_passport ap ON ap.applicantId = a.id

$condition

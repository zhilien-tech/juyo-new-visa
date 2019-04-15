/*autofillInterface_getApplicants*/
SELECT
ta.id
FROM
t_applicant ta
LEFT JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id
WHERE
taoj.orderId = @orderjpid
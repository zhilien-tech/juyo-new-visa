/*backmail_info_by_applicantId*/
SELECT
	tabj.id,
	taoj.applicantId,
	tabj.applicantId applicantJPId,
	tabj.source,
	tabj.expressType,
	tabj.teamName,
	tabj.expressType,
	tabj.expressNum,
	tabj.expressAddress,
	tabj.linkman,
	tabj.telephone,
	tabj.invoiceContent,
	tabj.invoiceHead,
	tabj.invoiceMobile,
	tabj.invoiceAddress,
	tabj.taxNum,
	tabj.remark 
FROM
	t_applicant_backmail_jp tabj
	INNER JOIN t_applicant_order_jp taoj ON tabj.applicantId = taoj.id 
WHERE
	taoj.applicantId = @applicantId
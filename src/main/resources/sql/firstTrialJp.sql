/*firstTrialJp_list_data*/
SELECT
	tr.id orderid,
	toj.id orderjpId,
	tr.orderNum orderNumber,
	toj.acceptDesign number,
	DATE_FORMAT( tr.goTripDate, '%Y-%m-%d' ) goTripTime,
	DATE_FORMAT( tr.backTripDate, '%Y-%m-%d' ) backTripTime,
	tr.STATUS orderStatus,
	taj.applicantStatus,
	CONCAT( CAST( tr.STATUS AS CHAR ), 'төл', taj.applicantStatus ) searchStatus,
	( SELECT count( * ) FROM t_applicant_order_jp WHERE orderId = toj.id ) peopleCount 
FROM
	t_order tr
	INNER JOIN t_order_jp toj ON toj.orderId = tr.id
	LEFT JOIN t_customer tc ON tr.customerId = tc.id
	LEFT JOIN (
SELECT
	taoj.orderId,
	GROUP_CONCAT( CAST( ta.STATUS AS CHAR ) SEPARATOR 'төл' ) applicantStatus,
	tap.passport passportNum,
	ta.telephone,
	GROUP_CONCAT( ta.telephone SEPARATOR 'төл' ) phone,
	GROUP_CONCAT( CONCAT( ta.firstname, ta.lastname ) SEPARATOR 'төл' ) applicantName 
FROM
	t_applicant ta
	INNER JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id
	LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id 
GROUP BY
	taoj.orderId 
	) taj ON taj.orderId = toj.id
$condition

/*firstTrialJp_list_data_applicant*/
SELECT
	taoj.applicantId applyid,
	CONCAT( ta.firstName, ta.lastName ) applicantname,
	taoj.isMainApplicant,
	ta.sex,
	ta.STATUS applicantStatus,
	ta.telephone,
	ta.email,
	tap.id passportid,
	tap.passport passportNum,
	tau.id qualifiedId,
	tavpj.careerStatus dataType,
	tavpj.DATA DATA 
FROM
	t_applicant_order_jp taoj
	INNER JOIN t_applicant ta ON taoj.applicantId = ta.id
	LEFT JOIN t_applicant_unqualified tau ON tau.applicantId = ta.id
	LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
	LEFT JOIN ( 
		SELECT
			applicantId,
			careerStatus,
			PrepareMaterials DATA 
		FROM
			t_applicant_work_jp 
		GROUP BY
			applicantId
	) tavpj ON tavpj.applicantId = taoj.id
$condition

/*firstTrialJp_order_info_byid*/
SELECT
	tr.*, 
	toj.visaCounty,
	toj.id orderid,
	toj.visaType,
	toj.isVisit,
	toj.threeCounty,
	toj.acceptDesign,
	toj.visastatus
FROM
	t_order tr
INNER JOIN t_order_jp toj ON toj.orderId = tr.id
WHERE
	toj.id = @orderjpid
	
/*firstTrialJp_orderDetail_applicant_by_orderid*/
SELECT
	taoj.applicantId applyid,
	ta.mainId,
	CONCAT( ta.firstName, ta.lastName ) applyname,
	ta.telephone,
	tap.passport,
	tavpj.careerStatus datatype,
	tavpj.DATA,
	ta.sex,
	ta.STATUS applicantstatus,
	tae.expressType,
	tae.expressNum 
FROM
	t_applicant_order_jp taoj
	INNER JOIN t_applicant ta ON taoj.applicantId = ta.id
	LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
	LEFT JOIN t_applicant_express tae ON tae.applicantId = ta.id
	LEFT JOIN ( 
		SELECT
			applicantId,
			careerStatus,
			PrepareMaterials DATA 
		FROM
			t_applicant_work_jp 
		GROUP BY
			applicantId
	) tavpj ON tavpj.applicantId = taoj.id
where taoj.orderId = @orderjpid

/*firstTrialJp_receive_address_by_orderid*/
SELECT
	tor.orderId,
	tor.expressType,
	tor.receiveAddressId,
	tor.expressAddress,
	tor.receiver,
	tor.telephone,
	tr.address 
FROM
	t_order_recipient tor
	LEFT JOIN t_receiveaddress tr ON tor.receiveAddressId = tr.id
where tor.orderId = @orderid

/*firstTrialJp_share_sms_applicant*/
SELECT
	taoj.applicantId applyid,
	CONCAT( ta.firstName, ta.lastName ) applicantname,
	taoj.isMainApplicant,
	taoj.isSameLinker,
	taoj.isShareSms,
	ta.sex,
	ta.STATUS applicantStatus,
	ta.telephone,
	ta.email,
	tap.id passportid,
	tap.passport passportNum,
	tau.id qualifiedId,
	tavpj.careerStatus dataType,
	tavpj.DATA DATA 
FROM
	t_applicant_order_jp taoj
	INNER JOIN t_applicant ta ON taoj.applicantId = ta.id
	LEFT JOIN t_applicant_unqualified tau ON tau.applicantId = ta.id
	LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
	LEFT JOIN ( SELECT applicantId, careerStatus, PrepareMaterials DATA FROM t_applicant_work_jp GROUP BY applicantId ) tavpj ON tavpj.applicantId = taoj.id
	$condition

/*firstTrialJp_careerStatus_by_orderjpid*/	
SELECT
	taoj.applicantId,
	CONCAT( ta.firstname, ta.lastname ) applicantName,
	taoj.orderId orderJpId,
	tawj.careerStatus 
FROM
	t_applicant_work_jp tawj
	LEFT JOIN t_applicant_order_jp taoj ON tawj.applicantId = taoj.id
	LEFT JOIN t_applicant ta ON ta.id = taoj.applicantId
	$condition

/*myvisa_inProcessVisa_list*/
SELECT
	taoj.applicantId,
	taoj.isSameLinker,
	CONCAT( ta.firstName, ta.lastName ) applicantname,
	ta.telephone,
	tap.passport,
	ta.userId,
	taoj.orderId orderJpId,
	torj.id orderId,
	torj.ordernum,
	torj.STATUS orderstatus,
	torj.sendVisaDate,
	torj.outVisaDate 
FROM
	t_applicant_order_jp taoj
	INNER JOIN t_applicant ta ON taoj.applicantId = ta.id
	LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
	LEFT JOIN ( SELECT toj.id orderjpid, tor.* FROM t_order_jp toj LEFT JOIN t_order tor ON tor.id = toj.orderid ) torj ON torj.orderjpid = taoj.orderId
	$condition

/*myvisa_japan_visa_list_data*/
SELECT
	toj.id,
	tr.orderNum japanNumber,
	taoj.isSameLinker,
	toj.acceptDesign number,
	DATE_FORMAT(tr.sendVisaDate, '%Y-%m-%d') sendingTime,
	DATE_FORMAT(tr.outVisaDate, '%Y-%m-%d') signingTime,
	tr.STATUS japanState,
	toj.visastatus visastatus,
	(
		SELECT
			count(*)
		FROM
			t_applicant_order_jp
		WHERE
			orderId = toj.id
	) peopleNumber
FROM
	t_order tr
INNER JOIN t_order_jp toj ON toj.orderId = tr.id
LEFT JOIN t_applicant_order_jp  taoj ON taoj.orderId = toj.id 
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

/*myvisa_japan_visa_list_data_apply*/
SELECT
	CONCAT(ta.firstName, ta.lastName) applicant,
	tap.passport passportNo,
	taoj.id applicatid,
	ta.id applyId,
	ta.telephone,
	ta.email,
	tavpj.type dataType,
	tavpj. DATA DATA,
	tavpj. STATUS STATUS
FROM
	t_applicant_order_jp taoj
INNER JOIN t_applicant ta ON taoj.applicantId = ta.id
LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
LEFT JOIN (
	SELECT
		applicantId,
		type,
		GROUP_CONCAT(
			(
				CASE
				WHEN STATUS = 0 THEN
						realInfo
				ELSE
					CONCAT(
						'<font color="blue">',
						realInfo,
						'</font>'
					)
				END
			) SEPARATOR '、'
		) DATA,
		STATUS
	FROM
		t_applicant_visa_paperwork_jp
	GROUP BY
		applicantId
) tavpj ON tavpj.applicantId = taoj.id
$condition

/*myvisa_applicant_by_id*/
SELECT
	ta.userid,
	taoj.applicantId,
	CONCAT( ta.firstName, ta.lastName ) applicantname,
	ta.telephone,
	ta.status applicantstatus,
	tap.passport,
	taoj.orderid,
	tor.ordernum,
	tor.STATUS orderstatus,
	tor.sendVisaDate,
	tor.outVisaDate 
FROM
	t_applicant_order_jp taoj
	INNER JOIN t_applicant ta ON taoj.applicantId = ta.id
	LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
	LEFT JOIN t_order tor ON tor.id = taoj.orderid 
	$condition
	
/*copyBaseToPersonnel*/
UPDATE 
	t_applicant ta
LEFT JOIN 
	t_tourist_baseinfo tb ON ta.userId = tb.userId
SET 
	ta.firstName = tb.firstName,ta.firstNameEn=tb.firstNameEn,ta.lastName=tb.lastName,ta.lastNameEn=tb.lastNameEn,ta.telephone=tb.telephone,ta.email=tb.email,ta.sex=tb.sex,
	ta.nation=tb.nation,ta.birthday=tb.birthday,ta.address=tb.address,ta.cardId=tb.cardId,ta.cardFront=tb.cardFront,ta.cardBack=tb.cardBack,
	ta.issueOrganization=tb.issueOrganization,ta.validEndDate=tb.validEndDate,ta.validStartDate=tb.validStartDate,ta.province=tb.province,
	ta.city=tb.city,ta.detailedAddress=tb.detailedAddress,ta.otherFirstName=tb.otherFirstName,ta.otherFirstNameEn=tb.otherFirstNameEn,
	ta.otherLastName=tb.otherLastName,ta.otherLastNameEn=tb.otherLastNameEn,ta.emergencyLinkman=tb.emergencyLinkman,ta.emergencyTelephone=tb.emergencyTelephone,
	ta.cardProvince=tb.cardProvince,ta.cardCity=tb.cardCity,ta.hasOtherName=tb.hasOtherName,ta.hasOtherNationality=tb.hasOtherNationality,
	ta.addressIsSameWithCard=tb.addressIsSameWithCard,ta.nationality=tb.nationality
WHERE
	ta.userId = @userId AND ta.id = @applyId

/*copyPassToPersonnel*/
UPDATE 
	t_applicant_passport ta
LEFT JOIN
	t_applicant tat ON ta.applicantId=tat.id
LEFT JOIN 
	t_tourist_passport tp ON tp.userId = tat.userId
SET 
	ta.type=tp.type,ta.passport=tp.passport,ta.sex=tp.sex,ta.sexEn=tp.sexEn,ta.birthAddress=tp.birthAddress,ta.issuedDate=tp.issuedDate,ta.validType=tp.validType,ta.passportUrl=tp.passportUrl,
	ta.birthAddressEn=tp.birthAddressEn,ta.birthday=tp.birthday,ta.issuedPlace=tp.issuedPlace,ta.issuedPlaceEn=tp.issuedPlaceEn,ta.validEndDate=tp.validEndDate,ta.validStartDate=tp.validStartDate
WHERE
	tat.userId = @userId AND tat.id = @applyId
/*get_japan_visa_list_data*/
SELECT
toj.id,
toj.errormsg,
taj.passport,
taj.applyname,
tuser.`name` opname,
tcompany.shortName,
tr.orderNum japanNumber,
tr.sendvisanum,
tr.`status` orderstatus,
tr.comshortname,
tc.shortname customershotname,
toj.acceptDesign number,
DATE_FORMAT(tr.sendVisaDate, '%Y-%m-%d') sendingTime,
DATE_FORMAT(tr.outVisaDate, '%Y-%m-%d') signingTime,
tr.STATUS japanState,
(
SELECT
count(*)
FROM
t_applicant_order_jp
WHERE
orderId = toj.id
) peopleNumber,
tr.isDisabled,
tr.zhaobaocomplete,
tr.zhaobaoupdate,
toj.visastatus visastatus,
toj.visaType,
toj.acceptDesign,
tr.id orderid
FROM
t_order tr
INNER JOIN t_order_jp toj ON toj.orderId = tr.id
LEFT JOIN t_company tcom ON tr.comId = tcom.id
LEFT JOIN t_company tcompany ON toj.sendsignid = tcompany.id
LEFT JOIN t_user tuser ON tr.salesOpid = tuser.id
LEFT JOIN t_customer tc ON tr.customerId = tc.id
LEFT JOIN (
SELECT
taoj.orderId,
tap.passport,
GROUP_CONCAT(
CONCAT(ta.firstname, ta.lastname) SEPARATOR 'төл'
) applyname
FROM
t_applicant ta
INNER JOIN t_applicant_order_jp taoj ON taoj.applicantId = ta.id
LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
GROUP BY
taoj.orderId
) taj ON taj.orderId = toj.id
$condition

/*get_japan_visa_list_data_apply*/
SELECT
	CONCAT(ta.firstName, ta.lastName) applicant,
	CONCAT(tap.firstNameEn, tap.lastNameEn) applicanten,
	tap.sex,
	ta.province,
	tap.passport passportNo,
	taoj.id applicatid,
	taoj.applicantId applyid,
	ta.telephone,
	ta.email,
	tavpj.type dataType,
	tavpj.blue blue,
	tavpj.black black,
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
				WHEN STATUS = 1 THEN
					CONCAT(
						'<font color="#64a4f3">',
						realInfo,
						'</font>'
					)
				END
			) SEPARATOR '、'
		) blue,
		GROUP_CONCAT(
			(
				CASE
				WHEN STATUS = 0 THEN
						realInfo
				END
			) SEPARATOR '、'
		)
		black,
		STATUS
	FROM
		t_applicant_visa_paperwork_jp
	GROUP BY
		applicantId
) tavpj ON tavpj.applicantId = taoj.id
$condition

/*get_jp_visa_order_info_byid*/
SELECT
	tr.*, toj.visaCounty,
	toj.id orderid,
	toj.visaType,
	toj.isVisit,
	toj.threeCounty,
	toj.acceptDesign,
	toj.visastatus,
	toj.remark
FROM
	t_order tr
INNER JOIN t_order_jp toj ON toj.orderId = tr.id
WHERE
	toj.id = @orderid
/*get_jporder_detail_applyinfo_byorderid*/
SELECT
	taoj.id applyid,
	CONCAT(ta.firstName, ta.lastName) applyname,
	ta.telephone,
	ta.email,
	ta.id,
	ta.mainid,
	tap.passport,
	tavpj.type,
	tavpj.blue,
	tavpj.black,
	taoj.mainRelation,
	taoj.relationRemark,
	taoj.isMainApplicant,
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
		type,
		GROUP_CONCAT(
			(
				CASE
				WHEN STATUS = 1 THEN
					CONCAT(
						'<font color="#64a4f3">',
						realInfo,
						'</font>'
					)
				END
			) SEPARATOR '、'
		) blue,
		GROUP_CONCAT(
			(
				CASE
				WHEN STATUS = 0 THEN
						realInfo
				END
			) SEPARATOR '、'
		)
		black
	FROM
		t_applicant_visa_paperwork_jp
	GROUP BY
		applicantId
) tavpj ON tavpj.applicantId = taoj.id
where taoj.orderId = @orderid
ORDER BY taoj.isMainApplicant DESC

/*get_travel_plan_by_orderid*/
SELECT
	totj.*, th.namejp hotelname,th.addressjp hoteladdress,th.mobile hotelmobile
FROM
	t_order_travelplan_jp totj
left JOIN t_hotel th ON totj.hotel = th.id
where totj.orderId = @orderid
order by outDate asc

/*get_reset_travel_plan_scenic*/
SELECT
	ts.*
FROM
	t_scenic ts
WHERE
	NAME NOT IN (
		SELECT
			scenic
		FROM
			t_order_travelplan_jp
		WHERE
			orderId = @orderid
		AND scenic != @scenicname
	)
and cityId = @cityid

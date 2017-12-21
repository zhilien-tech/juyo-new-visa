/*orderJp_list*/
SELECT
oj.id,
oj.orderid,
o.orderNum,
(
	SELECT
		count(*)
	FROM
		t_applicant_order_jp
	WHERE
		orderId = oj.id
) peopleNum,
o.`status`,
o.comName,
o.isDisabled,
c.source,
o.comShortName,
o.isDirectCus,
o.createTime,
o.sendVisaDate,
o.outVisaDate,
o.linkman,
o.telephone,
o.customerId,
aj.applicants
FROM
t_order o
LEFT JOIN t_order_jp oj ON oj.orderId = o.id
LEFT JOIN t_customer c ON o.customerId = c.id
LEFT JOIN (
	SELECT
		aoj.orderId,a.id,
		GROUP_CONCAT(CONCAT(a.firstName, a.lastName) SEPARATOR '、') applicants
	FROM 
		t_applicant a
	LEFT JOIN 
	t_applicant_order_jp aoj ON aoj.applicantId = a.id
	LEFT JOIN
	t_order_jp oj ON aoj.orderId = oj.id
	GROUP BY
	aoj.orderId
) aj ON aj.orderId = oj.id
LEFT JOIN t_applicant_passport ap ON ap.applicantId = aj.id
LEFT JOIN t_company tc ON tc.id = o.comId
$condition

/*orderJp_list_orderInfo_byOrderId*/
SELECT
	o.*, 
	oj.visaCounty,
	oj.visaType,
	oj.isVisit,
	oj.threeCounty,
	oj.acceptDesign,
	oj.visastatus
FROM
	t_order o
INNER JOIN t_order_jp oj ON oj.orderId = o.id
WHERE
	o.id = @id
	
/*orderJp_list_applicantInfo_byOrderId*/
SELECT
a.id,
aoj.id applicantJpId,
CONCAT(a.firstName, a.lastName) applyname,
a.email,
a.mainId,
a.telephone,
a.sex,
ap.passport
FROM
t_applicant_order_jp aoj
LEFT JOIN
t_order_jp oj ON aoj.orderId = oj.id 
LEFT JOIN
t_applicant a ON aoj.applicantId = a.id
LEFT JOIN
t_applicant_passport ap ON ap.applicantId = a.id
WHERE
oj.orderId = @id

/*orderJp_list_passportInfo_byApplicantId*/
SELECT
	ap.id,
	ap.`type`,
	ap.passport,
	ap.sex,
	ap.firstName,
	ap.lastName,
	ap.firstNameEn,
	ap.lastNameEn,
	ap.sexEn,
	ap.validType,
	ap.birthAddress,
	ap.birthAddressEn,
	ap.birthday,
	ap.issuedPlace,
	ap.issuedPlaceEn,
	ap.issuedDate,
	ap.validEndDate,
	ap.issuedOrganization,
	ap.issuedOrganizationEn,
	ap.passportUrl
FROM
	t_applicant_passport ap
INNER JOIN 
t_applicant a ON ap.applicantId = a.id
WHERE
	a.id = @id

/*orderJp_ordernum*/	
SELECT
 * 
FROM 
t_order
WHERE 
DATE(createTime) = DATE(NOW())
$condition
order by createtime desc

/*orderJp_applicantTable*/
SELECT
a.id,
taoj.id applicantJpId,
CONCAT(a.firstName, a.lastName) applyname,
a.email,
a.telephone,
a.mainId,
a.sex,
ap.passport
FROM
t_applicant a
LEFT JOIN
t_applicant_passport ap ON ap.applicantId = a.id
LEFT JOIN
t_applicant_order_jp taoj ON taoj.applicantId = a.id
$condition

/*visaInfo_byApplicantId*/
SELECT
aoj.isMainApplicant,
a.mainId,
aoj.applicantId,
aoj.mainRelation,
aoj.relationRemark,
aoj.sameMainTrip,
aoj.sameMainWealth,
aoj.sameMainWork,
awj.occupation,
awj.`name`,
awj.telephone,
awj.address,
awtj.type,
awtj.details
FROM
t_applicant_order_jp aoj
LEFT JOIN
t_applicant a ON aoj.applicantId = a.id
LEFT JOIN
t_applicant_work_jp awj ON awj.applicantId = aoj.id
LEFT JOIN
t_applicant_wealth_jp awtj ON awtj.applicantId = aoj.id
WHERE
aoj.applicantId = @id


/*mainApplicant_byOrderId*/
SELECT
a.id,
CONCAT(a.firstName, a.lastName) applyname,
a.email,
a.mainId,
a.telephone,
a.sex,
ap.passport
FROM
t_applicant_order_jp aoj
LEFT JOIN
t_order_jp oj ON aoj.orderId = oj.id 
LEFT JOIN
t_applicant a ON aoj.applicantId = a.id
LEFT JOIN
t_applicant_passport ap ON ap.applicantId = a.id
$condition

/*wealth_byApplicantId*/
SELECT
tawj.id,
tawj.applicantId,
tawj.type,
tawj.details,
tawj.opId
FROM
t_applicant_wealth_jp tawj
LEFT JOIN
t_applicant_order_jp taoj ON tawj.applicantId = taoj.id
WHERE tawj.applicantId = @id

/*username_logs*/
SELECT
tol.createTime,
tu.`name`,
tol.orderStatus
FROM
t_order_logs tol
LEFT JOIN
t_user tu ON tol.opId = tu.id
LEFT JOIN
t_order o ON tol.orderId = o.id
WHERE
o.id = @id

/*passportInfo_byOrderId*/
SELECT
	ap.passport
FROM
	t_applicant_passport ap
INNER JOIN 
t_applicant a ON ap.applicantId = a.id
LEFT JOIN
t_applicant_order_jp taoj ON taoj.applicantId = a.id
LEFT JOIN
t_order_jp toj ON taoj.orderId = toj.id
$condition
/*mydata_list*/
SELECT
	taoj.applicantId,
	CONCAT( ta.firstName, ta.lastName ) applicantname,
	ta.telephone,
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

/*mydata_inProcessVisa_list*/
SELECT
	ttb.applicantId,
	CONCAT( ttb.firstName, ttb.lastName ) applicantname,
	ttb.telephone,
	ttp.passport
FROM
	t_applicant ta 
	INNER JOIN t_tourist_baseinfo ttb ON ta.id = ttb.applicantId
	LEFT JOIN t_tourist_passport ttp ON ta.id = ttp.applicantId
	$condition	

/*mydata_japan_visa_list_data_apply*/
SELECT
	CONCAT(ta.firstName, ta.lastName) applicant,
	tap.passport passportNo,
	taoj.id applicatid,
	ta.id applyId,
	ta.telephone,
	ta.userId
FROM
	t_applicant_order_jp taoj
INNER JOIN t_applicant ta ON taoj.applicantId = ta.id
LEFT JOIN t_applicant_passport tap ON tap.applicantId = ta.id
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
	
/*insertBaseToTourist*/
INSERT INTO
	t_tourist_baseinfo(applicantId,userId,firstName,firstNameEn,lastName,lastNameEn,telephone,email,sex,nation,birthday,address,cardId,cardFront,cardBack,issueOrganization,validStartDate,validEndDate,province,city,detailedAddress,otherFirstName,otherFirstNameEn,otherLastName,otherLastNameEn,emergencyLinkman,emergencyTelephone,hasOtherNationality,hasOtherName,nationality,addressIsSameWithCard,saveIsPrompted,updateIsPrompted)
SELECT
	id,userId,firstName,firstNameEn,lastName,lastNameEn,telephone,email,sex,nation,birthday,address,cardId,cardFront,cardBack,issueOrganization,validStartDate,validEndDate,province,city,detailedAddress,otherFirstName,otherFirstNameEn,otherLastName,otherLastNameEn,emergencyLinkman,emergencyTelephone,hasOtherNationality,hasOtherName,nationality,addressIsSameWithCard,0,0
FROM
	t_applicant ta 
	$condition
	
/*insertBaseToTouristNoUserId*/
INSERT INTO
	t_tourist_baseinfo(applicantId,firstName,firstNameEn,lastName,lastNameEn,telephone,email,sex,nation,birthday,address,cardId,cardFront,cardBack,issueOrganization,validStartDate,validEndDate,province,city,detailedAddress,otherFirstName,otherFirstNameEn,otherLastName,otherLastNameEn,emergencyLinkman,emergencyTelephone,hasOtherNationality,hasOtherName,nationality,addressIsSameWithCard)
SELECT
	id,firstName,firstNameEn,lastName,lastNameEn,telephone,email,sex,nation,birthday,address,cardId,cardFront,cardBack,issueOrganization,validStartDate,validEndDate,province,city,detailedAddress,otherFirstName,otherFirstNameEn,otherLastName,otherLastNameEn,emergencyLinkman,emergencyTelephone,hasOtherNationality,hasOtherName,nationality,addressIsSameWithCard
FROM
	t_applicant ta 
	$condition
	
/*insertPassToTourist*/
INSERT INTO
	t_tourist_passport(applicantId,userId,type,passport,sex,sexEn,birthAddress,birthAddressEn,birthday,issuedPlace,issuedPlaceEn,issuedDate,validStartDate,validType,validEndDate,issuedOrganization,issuedOrganizationEn,passportUrl,OCRline1,OCRline2)
SELECT
	ta.id,ta.userId,tap.type,tap.passport,tap.sex,tap.sexEn,tap.birthAddress,tap.birthAddressEn,tap.birthday,tap.issuedPlace,tap.issuedPlaceEn,tap.issuedDate,tap.validStartDate,tap.validType,tap.validEndDate,tap.issuedOrganization,tap.issuedOrganizationEn,tap.passportUrl,tap.OCRline1,tap.OCRline2
FROM
	t_applicant_passport tap
LEFT JOIN
	t_applicant ta ON tap.applicantId = ta.id
	$condition
	
/*insertPassToTouristNoUserId*/
INSERT INTO
	t_tourist_passport(applicantId,type,passport,sex,sexEn,birthAddress,birthAddressEn,birthday,issuedPlace,issuedPlaceEn,issuedDate,validStartDate,validType,validEndDate,issuedOrganization,issuedOrganizationEn,passportUrl,OCRline1,OCRline2)
SELECT
	ta.id,tap.type,tap.passport,tap.sex,tap.sexEn,tap.birthAddress,tap.birthAddressEn,tap.birthday,tap.issuedPlace,tap.issuedPlaceEn,tap.issuedDate,tap.validStartDate,tap.validType,tap.validEndDate,tap.issuedOrganization,tap.issuedOrganizationEn,tap.passportUrl,tap.OCRline1,tap.OCRline2
FROM
	t_applicant_passport tap
LEFT JOIN
	t_applicant ta ON tap.applicantId = ta.id
	$condition
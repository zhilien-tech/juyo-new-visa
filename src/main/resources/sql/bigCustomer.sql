/*bigCustomer_staff_list*/
SELECT
	tasb.id staffid,
	CONCAT( tasb.firstname, tasb.lastname ) NAME,
	tasb.telephone,
	tasb.email,
	tasb.department,
	tasb.job,
	tasp.passport,
	tasp.id passportId
FROM
	t_app_staff_basicinfo tasb
	INNER JOIN t_app_staff_passport tasp ON tasp.staffId = tasb.id
	$condition
	
/*bigCustomer_staff_passport*/
SELECT
	tasp.id possportId,
	tasb.id staffId,
	tasp.firstName,
	tasp.firstNameEn,
	tasp.lastName,
	tasp.lastNameEn,
	tasp.type,
	tasp.passport,
	tasp.sex,
	tasp.sexEn,
	tasp.birthAddress,
	tasp.birthAddressEn,
	tasp.birthday,
	tasp.issuedPlace,
	tasp.issuedPlaceEn,
	tasp.issuedDate,
	tasp.validType,
	tasp.validStartDate,
	tasp.validEndDate,
	tasp.issuedOrganization,
	tasp.issuedOrganizationEn
FROM
	t_app_staff_passport tasp
	INNER JOIN t_app_staff_basicinfo tasb ON tasb.id = tasp.staffId
	$condition

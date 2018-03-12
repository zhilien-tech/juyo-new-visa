/*appevents_detail_by_eventId*/
SELECT
	tae.id eventId,
	tae.eventsNum,
	tae.eventsName,
	tae.pictureUrl,
	tae.detailsUrl,
	tae.dueDate,
	tae.departureDate,
	tae.returnDate,
	tae.visaCountry,
	tae.visitCity,
	tae.attentions,
	tae.descriptions,
	( SELECT count( * ) FROM t_app_staff_events tase WHERE tase.eventsId = tae.id ) signUpCount 
FROM
	t_app_events tae 
WHERE
	tae.id = @eventId

	
/*appevents_staffs_infos_by_eventId*/
SELECT
	tase.eventsId,
	tasb.* 
FROM
	t_app_staff_basicinfo tasb
	INNER JOIN t_app_staff_events tase ON tasb.id = tase.staffId
WHERE
	tase.eventsId = @eventId
GROUP BY
	tasb.id

	
/*appevents_process_list_by_staffId*/
	SELECT
	tase.staffId,
	tae.id eventId,
	tae.eventsNum,
	tae.eventsName,
	tae.pictureUrl,
	tae.detailsUrl,
	tae.dueDate,
	tae.departureDate,
	tae.returnDate,
	tae.visaCountry,
	tae.visitCity,
	tae.attentions,
	tae.descriptions 
FROM
	t_app_events tae
	LEFT JOIN t_app_staff_events tase ON tase.eventsId = tae.id 
WHERE
	tase.staffId =@staffIds
	
/*appevents_staff_list_by_userId*/
SELECT
	tasb.id staffId,
	tasb.userId,
	CONCAT( tasb.firstname, tasb.lastname ) staffName,
	tasb.status
FROM
	t_app_staff_basicinfo tasb
WHERE
	tasb.userId = @userId
	
/*appevents_staff_baseInfo_by_staffId*/
SELECT
	tasb.id staffId,
	tasb.firstname,
	tasb.lastname,
	tasb.firstNameEn,
	tasb.lastNameEn,
	tasb.hasOtherName,
	tasb.otherFirstName,
	tasb.otherLastName,
	tasb.otherFirstNameEn,
	tasb.otherLastNameEn
FROM
	t_app_staff_basicinfo tasb
WHERE
	tasb.id = @staffId
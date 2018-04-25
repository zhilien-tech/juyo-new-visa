/*pcVisa_travelCompanion*/
SELECT
	* 
FROM
	t_app_staff_travelcompanion tast 
WHERE
	tast.staffid = @staffid
	
/*pcVisa_previousTrip*/
SELECT
	* 
FROM
	t_app_staff_previoustripinfo tasp
WHERE
	tasp.staffid = @staffid

/*pcVisa_familyInfo*/
SELECT
	* 
FROM
	t_app_staff_familyinfo tasf
WHERE
	tasf.staffid = @staffid
	
	
/*pcVisa_word_education_training_list*/
SELECT
	* 
FROM
	t_app_staff_work_education_training taswet
where 
	taswet.staffid = @staffid
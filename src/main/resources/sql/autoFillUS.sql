/*getAutofilldata*/
SELECT
tou.id orderusid,
tou.ordernumber ordernum,
tasb.telecodefirstname,
tasb.telecodelastname,
tasb.firstname,
tasb.firstnameen,
tasb.lastname,
tasb.lastnameen,
tasb.otherfirstname,
tasb.otherfirstnameen,
tasb.otherlastname,
tasb.otherlastnameen,
tasp.sex,
tasp.birthday,
tasb.cardcity,
tasb.cardprovince,
tasb.cardId,
tasb.telephone,
tasb.email,
tasb.marrystatus,
tasb.address,
tasb.city,
tasb.province,
tasf.fatherfirstname,
tasf.fatherfirstnameen,
tasf.fatherlastname,
tasf.fatherlastnameen,
tasf.fatherbirthday,
tasf.motherfirstname,
tasf.motherfirstnameen,
tasf.motherlastname,
tasf.motherlastnameen,
tasf.motherbirthday,
tasf.spousefirstname,
tasf.spousefirstnameen,
tasf.spouselastname,
tasf.spouselastnameen,
tasf.spousecity,
tasf.spousecountry,
tasf.spousebirthday,
tasf.spousenationality,
tasf.spouseaddress,
tasf.firstaddress,
tasf.city,
tasf.province,
tasf.country,
tasf.zipcode,
taswet.occupation,
tasp.issuedplace,
tasp.type,
tasp.passport,
tasp.issueddate,
tasp.validenddate,
tout.travelpurpose,
tout.goFlightNum,
tout.goArrivedCity,
tout.returnDepartureCity,
tout.arrivedate,
tout.leavedate,
tout.returnFlightNum,
tast.groupname,
tasb.socialsecuritynumber,
tasb.taxpayernumber,
tasb.nationalidentificationnumber,
tasf.isfatherinus,
tasf.fatherstatus,
tasf.ismotherinus,
tasf.motherstatus,
tasf.hasotherrelatives,
tasc.firstname,
tasc.firstnameen,
tasc.lastname,
tasc.lastnameen,
tasc.organizationname,
tasc.ralationship,
tasc.telephone,
tasc.email,
tasc.address,
tasc.city,
tasc.state,
tasc.zipcode,
taspt.issueddate,
taspt.visanumber,
taspt.isapplyingsametypevisa,
taspt.issamecountry,
taspt.istenprinted,
taspt.lostyear,
taspt.lostexplain,
taspt.iscancelled,
taspt.cancelexplain,
taspt.isrefused,
taspt.refusedexplain,
taspt.islegalpermanentresident,
taspt.permanentresidentexplain,
taspt.isfiledimmigrantpetition,
taspt.immigrantpetitionexplain,
taswet.clanname,
taswet.skillexplain
FROM
t_order_us tou
INNER JOIN
t_app_staff_order_us tasou ON tou.id = tasou.orderid
INNER JOIN
t_app_staff_basicinfo tasb ON tasb.id = tasou.staffid
LEFT JOIN
t_app_staff_passport tasp ON tasp.staffid = tasb.id
LEFT JOIN
t_app_staff_familyinfo tasf ON tasf.staffid = tasb.id
LEFT JOIN
t_app_staff_work_education_training taswet ON taswet.staffid = tasb.id
LEFT JOIN
t_order_us_travelinfo tout ON tout.orderid = tou.id
LEFT JOIN
t_app_staff_travelcompanion tast ON tast.staffid = tasb.id
LEFT JOIN
t_app_staff_contactpoint tasc ON tasc.staffid = tasb.id
LEFT JOIN
t_app_staff_previoustripinfo taspt ON taspt.staffid = tasb.id
$condition
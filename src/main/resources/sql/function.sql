/*function_list*/
SELECT
	f.id,
	f.parentId,
	f. LEVEL LEVEL,
	ifnull(p.funName, '') parentName,
	f.funName,
	ifnull(f.url, '') url,
	ifnull(f.remark, '') remark,
	f.createTime,
	f.updateTime,
	f.sort,
	bf.compType,
	bf.countryId
FROM
	t_function f
LEFT JOIN t_function p ON f.parentId = p.id
LEFT JOIN t_businessScope_function bf ON bf.funId = f.id
$condition

/*function_by_Id*/
SELECT
	f.id,
	f.parentId,
	f. LEVEL LEVEL,
	ifnull(p.funName, '') parentName,
	f.funName,
	ifnull(f.url, '') url,
	ifnull(f.remark, '') remark,
	f.createTime,
	f.updateTime,
	f.sort,
	bf.compType,
	bf.countryId
FROM
	t_function f
LEFT JOIN t_function p ON f.parentId = p.id
LEFT JOIN t_businessScope_function bf ON bf.funId = f.id
$condition
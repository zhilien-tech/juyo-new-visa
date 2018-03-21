<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>签证信息</title>
		<link rel="stylesheet" href="${base}/references/public/css/visaInfo.css">
	</head>
	<body>
		<div class="head">
			<div class="title">
				<span>签证信息</span>
			</div>
			<div>
				<a >保存</a>
				<a >取消</a>
			</div>
		</div>
		
		<div class="topHide"></div>
		<div class="section">
			<!--旅伴信息-->
			<div class="companyInfoModule">
				<div>旅伴信息</div>
				<div class="companyMain">
					<label>是否与其他人一起旅游</label>
					<input type="radio" class="companyInfo" name="companyInfo" value="1" />是
					<input type="radio" class="companyInfo" name="companyInfo" value="2"/>否
				</div>
				<!--yes-->
				<div class="teamture" style="display: none;">
					<label>是否作为团队或组织的一部分旅游</label>
					<input type="radio" class="team" name="team" value="1" />是
					<input type="radio" class="team" name="team" value="2" />否
				</div>
				<!--第二部分yes-->
				<div class="teamnameture" style="display: none;">
					<label>团队名称</label>
					<input type="text" placeholder="团队名称" />
				</div>
				<!--第二部分No-->
				<div class="teamnamefalse" style="display: none;">
					<div>
						<label>同伴名称</label>
						<input type="text" placeholder="同伴名称" />
					</div>
					<div>
						<label>同伴姓</label>
						<input type="text" placeholder="同伴姓" />
					</div>
					<div>
						<label>同伴名</label>
						<input type="text" placeholder="同伴名" />
					</div>
					<div>
						<label>与你的关系</label>
						<select>
							<option>父母</option>
							<option>配偶</option>
							<option>子女</option>
							<option>其他亲属</option>
							<option>商业伙伴</option>
							<option>其他</option>
						</select>
					</div>
					<div>
						<a>添加</a>
						<a>去掉 </a>
					</div>
				</div>
			</div>
			<!--旅伴信息END-->
			<hr />
			<!--以前的美国旅游信息-->
			<div class="beforeTourism">
				<!--是否去过美国-->
				<div class="goUSModule">
					<div class="goUSMain">
						<label>是否去过美国</label>
						<input type="radio" name="goUS" class="goUS" value="1" />是
						<input type="radio" name="goUS" class="goUS" value="2" />否
					</div>
					<!--yes-->
					<div class="goUSInfo" style="display: none;">
						<div>
							<label>抵达日期</label>
							<input type="date">
						</div>
						<div>
							<label>停留时间</label>
							<input type="text" />
							<select>
								<option>年</option>
								<option>月</option>
								<option>周</option>
								<option>日</option>
								<option>少于24小时</option>
							</select>
						</div>
						<div>
							<a>添加</a>
							<a>去掉</a>
						</div>
						<div>
							<label>是否有美国驾照</label>
							<input type="radio" name="license" class="license" value="1" />是
							<input type="radio" name="license" class="license" value="2" />否
						</div>
						<div class="driverInfo" style="display: none;">
							<div>
								<label>驾照号</label>
								<input type="text" >
								<input type="checkbox" />
							</div>
							<div>
								<label>哪个州的驾照</label>
								<select>
									<option></option>
								</select>
							</div>
							<div>
								<a>添加</a>
								<a>去掉</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<hr />
			<!--是否有美国签证-->
			<div class="visaUSMain">
				<div>
					<label>是否有美国签证</label>
					<input type="radio" name="visaUS" class="visaUS" value="1" />是
					<input type="radio" name="visaUS" class="visaUS" value="2" />否
				</div>
				<div class="dateIssue" style="display: none;">
					<div>
						<label>最后一次签证的签发日期</label>
						<input type="date" />
					</div>
					<div>
						<label>签证号吗</label>
						<input type="text" />
						<input type="checkbox" />
					</div>
					<div>
						<label>是否在申请相同类型的签证</label>
						<input type="radio" />
						<input type="radio" />
					</div>
					<div>
						<label>是否在签发上述签证的国家或地区申请并且是您所在的国家或地区的居住地</label>
						<input type="radio" />
						<input type="radio" />
					</div>
					<div>
						<label>是否采集过指纹</label>
						<input type="radio" />
						<input type="radio" />
					</div>
					<div>
						<label>你的美国签证是否丢失或被盗过</label>
						<input type="radio" name="lose" class="lose" value="1" />是
						<input type="radio" name="lose" class="lose" value="2" />否
					</div>
					<div class="yearExplain" style="display: none;">
						<div>
							<label>年份</label>
							<input type="text" />
						</div>
						<div>
							<label>说明</label>
							<textarea></textarea>
						</div>
					</div>
					<div>
						<label>你的美国签证是否被取消或撤销过</label>
						<input type="radio" name="revoke" class="revoke" value="1" />是
						<input type="radio" name="revoke" class="revoke" value="2" />否
					</div>
					<div class="explain" style="display: none;">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
			</div>
			<hr />
			<!--被拒绝过美国签证，或被拒绝入境美国，或撤回入境口岸的入境-->
			<div>
				<div>
					<label>被拒绝过美国签证，或被拒绝入境美国，或撤回入境口岸的入境</label>
					<input type="radio" name="refuse" class="refuse" value="1" />是
					<input type="radio" name="refuse" class="refuse" value="2" />否
				</div>
				<div class="refuseExplain" style="display: none ;">
					<label>说明</label>
					<textarea></textarea>
				</div>
			</div>
			<hr />
			<!--曾经是否是美国合法永久居民-->
			<div>
				<div>
					<label>曾经是否是美国合法永久居民</label>
					<input type="radio" name="onceLegitimate" class="onceLegitimate" value="1" />是
					<input type="radio" name="onceLegitimate" class="onceLegitimate" value="2" />否
				</div>
				<div class="onceExplain" style="display: none ;">
					<label>说明</label>
					<textarea></textarea>
				</div>
			</div>
			<hr />
			<!--有没有人曾代表您向美国公民和移民服务局提交过移民申请-->
			<div>
				<div>
					<label>有没有人曾代表您向美国公民和移民服务局提交过移民申请</label>
					<input type="radio" name="onceImmigration" class="onceImmigration" value="1" />是
					<input type="radio" name="onceImmigration" class="onceImmigration" value="2" />否
				</div>
				<div class="immigrationExplain" style="display: none;">
					<label>说明</label>
					<textarea></textarea>
				</div>
			</div>
			<!--以前的美国旅游信息END-->
			<hr />
			<!--美国联络点-->
			<div class="contactPoint">
				<div>
					<label>联系人姓</label>
					<input type="text" />
				</div>
				<div>
					<label>联系人名</label>
					<input type="text" />
					<input type="checkbox" />
				</div>
				<div>
					<label>组织名称</label>
					<input type="text" />
					<input type="checkbox" />
				</div>
				<div>
					<label>与你的关系</label>
					<select>
						<option>请选择</option>
						<option>亲属</option>
						<option>配偶</option>
						<option>朋友</option>
						<option>商业伙伴</option>
						<option>雇主</option>
						<option>学校官方</option>
						<option>其他</option>
					</select>
				</div>
				<div class="">
					<div>
						<label>美国街道地址(首选)</label>
						<input type="text" />
					</div>
					<div>
						<label>美国街道地址(次选)*可选</label>
						<input type="text" />
					</div>
					<div>
						<label>市</label>
						<input type="text" />
					</div>
					<div>
						<label>州</label>
						<select>
							<option></option>
						</select>
					</div>
					<div>
						<label>邮政编码</label>
						<input type="text" />
					</div>
					<div>
						<label>电话号码</label>
						<input type="text" />
					</div>
					<div>
						<label>邮件地址</label>
						<input type="text" />
						<input type="checkbox" />
					</div>
				</div>
			</div>
			<!--美国联络点END-->
			<hr />
			<!--家庭信息-->
			<!--亲属-->
			<div class="familyInfo">
				<div>
					<label>父亲的姓</label>
					<input type="text" />
					<input type="checkbox" />
				</div>
				<div>
					<label>父亲的名</label>
					<input type="text" />
					<input type="checkbox" />
				</div>
				<div>
					<div>
						<label>你的父亲是否在美国</label>
						<input type="radio" name="fatherUS" class="fatherUS" value="1" />是
						<input type="radio" name="fatherUS" class="fatherUS" value="2" />否
					</div>
					<!--yes-->
					<div class="fatherUSYes" style="display: none;">
						<select>
							<option>美国公民</option>
							<option>美国合法永久居住者</option>
							<option>非移民</option>
							<option>其他/不知道</option>
						</select>
					</div>
				</div>
				<div>
					<label>母亲的姓</label>
					<input type="text" />
					<input type="checkbox" />
				</div>
				<div>
					<label>母亲的名</label>
					<input type="text" />
					<input type="checkbox" />
				</div>
				<div>
					<div>
						<label>你的母亲是否在美国</label>
						<input type="radio" name="motherUS" class="motherUS" value="1" />是
						<input type="radio" name="motherUS" class="motherUS" value="2" />否
					</div>
					<div class="motherUSYes" style="display: none;">
						<label>身份状态</label>
						<select>
							<option>美国公民</option>
							<option>美国合法永久居住者</option>
							<option>非移民</option>
							<option>其他/不知道</option>
						</select>
					</div>
				</div>
				<div>
					<div>
						<label>在美国除了父母还有没有直系亲属</label>
						<input type="radio" name="directRelatives" class="directRelatives" value="1" />是
						<input type="radio" name="directRelatives" class="directRelatives" value="2" />否
					</div>
					<!--yes-->
					<div class="directRelativesYes" style="display: none;">
						<div>
							<label>姓</label>
							<input type="text" />
						</div>
						<div>
							<label>名</label>
							<input type="text" />
						</div>
						<div>
							<label>与你的关系</label>
							<select>
								<option>配偶</option>
								<option>未婚夫/妻子</option>
								<option>子女</option>
								<option>兄弟姐妹</option>
							</select>
						</div>
						<div>
							<label>亲属的身份</label>
							<select>
								<option>美国公民</option>
								<option>美国合法永久居住者</option>
								<option>非移民</option>
								<option>其他/不知道</option>
							</select>
						</div>
					</div>
					<!--NO-->
					<div class="directRelativesNo" style="display: none;">
						<label>在美国是否还有别的亲属</label>
						<input type="radio" name="otherDirect" />是
						<input type="radio" name="otherDirect" checked/>否
					</div>
				</div>
			</div>
			<!--配偶-->
			<div>
				<div>
					<label>配偶的名</label>
					<input type="text" />
				</div>
				<div>
					<label>配偶的姓</label>
					<input type="text" />
				</div>
				<div>
					<label>配偶的生日</label>
					<input type="date" placeholder="日月年" />
				</div>
				<div>
					<label>配偶的国籍</label>
					<select>
						<option>中国</option>
						<option>美国</option>
					</select>
				</div>
				<div>
					<label>配偶的出生城市</label>
					<input type="text" />
					<input type="checkbox" />
				</div>
				<div>
					<label>配偶的出生国家</label>
					<select>
						<option>中国</option>
						<option>美国</option>
					</select>
				</div>
				<div>
					<label>配偶的联系地址</label>
					<select>
						<option>与居住地址一样</option>
						<option>与邮寄地址一样</option>
						<option>与美国联系地址一样</option>
						<option>不知道</option>
						<option>其他(制定地址)</option>
					</select>
				</div>
				<!--选择其他-->
				<div class="otherSpouseInfo" style="display: none;">
					<div>
						<label>街道地址(首选)</label>
						<input type="text" />
					</div>
					<div>
						<label>街道地址(次要)*可选</label>
						<input type="text" />
					</div>
					<div>
						<label>市</label>
						<input type="text" />
					</div>
					<div>
						<label>州/省</label>
						<input type="text" />
						<input type="checkbox" />
					</div>
					<div>
						<label>邮政编码</label>
						<input type="text" />
						<input type="checkbox" />
					</div>
					<div>
						<label>国家/地区</label>
						<select>
							<option>美国</option>
							<option>中国</option>
						</select>
					</div>
				</div>
			</div>
			<!--家庭信息END-->
			<hr />
			<!--工作/教育/培训信息-->
			<div class="experience">
				<div>
					<label>主要职业</label>
					<select>
						<option value="1">农业</option>
						<option value="2">艺术家/表演家</option>
						<option value="3">商业</option>
						<option value="4">通信</option>
						<option value="5">计算机科学</option>
						<option value="6">烹饪/食品服务</option>
						<option value="7">教育</option>
						<option value="8">工程</option>
						<option value="9">政府</option>
						<option value="10">家庭主妇</option>
						<option value="11">法律界人士</option>
						<option value="12">医疗/保健</option>
						<option value="13">军事</option>
						<option value="14">自然科学</option>
						<option value="15">不受雇佣</option>
						<option value="16">物理科学</option>
						<option value="17">宗教职业</option>
						<option value="18">研究</option>
						<option value="19">退休</option>
						<option value="20 ">社会科学</option>
						<option value="21">学生</option>
						<option value="22">其他</option>
					</select>
				</div>
				<div>
					<div>
						<label>目前的工作点位或者学校名称</label>
						<input type="text" />
					</div>
					<div>
						<label>街道地址(首选)</label>
						<input type="text" />
					</div>
					<div>
						<label>街道地址(次要)*可选</label>
						<input type="text" />
					</div>
					<div>
						<label>市</label>
						<input type="text" />
					</div>
					<div>
						<label>州/省</label>
						<input type="text" />
						<input type="checkbox" />
					</div>
					<div>
						<label>邮政编码</label>
						<input type="text" />
						<input type="checkbox" />
					</div>
					<div>
						<label>电话号吗</label>
						<input type="text" />
					</div>
					<div>
						<label>国家/地区</label>
						<select>
							<option>中国</option>
							<option>美国</option>
						</select>
					</div>
					<div>
						<label>开始日期</label>
						<input type="date" />
					</div>
					<div>
						<label>当地月收入(如果雇佣)</label>
						<input type="text" />
					</div>
					<div>
						<label>不适用</label>
						<input type="checkbox" />
					</div>
					<div>
						<label>简要描述你的指责</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<label>说明</label>
					<textarea></textarea>
				</div>
			</div>
			<!--以前-->
			<div>
				<div>
					<div>
						<label>以前是否工作过</label>
						<input type="radio" name="beforeWork" class="beforeWork" value="1" />是
						<input type="radio" name="beforeWork" class="beforeWork" value="2" />否
					</div>
					<!--yes-->
					<div class="beforeWorkInfo" style="display: none;">
						<div>
							<label>雇主名字</label>
							<input type="text" />
						</div>
						<div>
							<label>雇主街道地址(首选)</label>
							<input type="text" />
						</div>
						<div>
							<label>雇主街道地址(次选)*可选</label>
							<input type="text" />
						</div>
						
						<div>
							<label>市</label>
							<input type="text" />
							<input type="checkbox" />
						</div>
						<div>
							<label>州/省</label>
							<input type="text" />
						</div>
						<div>
							<label>邮政编码</label>
							<input type="text" />
							<input type="checkbox" />
						</div>
						<div>
							<label>国家/地区</label>
							<select>
								<option>中国</option>
								<option>美国</option>
							</select>
						</div>
						<div>
							<label>电话号码</label>
							<input type="text" />
						</div>
						<div>
							<label>职称</label>
							<input type="text" />
						</div>
						<div>
							<label>主管的姓</label>
							<input type="text" />
							<input type="checkbox" />
						</div>
						<div>
							<label>主管的名</label>
							<input type="text" />
							<input type="checkbox" />
						</div>
						<div>
							<label>入职时间</label>
							<input type="date" />
						</div>
						<div>
							<label>离职时间</label>
							<input type="date" />
						</div>
						
						<div>
							<label>简要描述你的指责</label>
							<textarea></textarea>
						</div>
						<div>
							<a>添加</a>
							<a>去掉</a>
						</div>
					</div>
				</div>
				<div>
					<div>
						<label>是否上过中学或以上的任何教育</label>
						<input type="radio" name="education" class="education" value="1" />是
						<input type="radio" name="education" class="education" value="2" />否
					</div>
					<!--yes-->
					<div class="educationInfo">
						<div>
							<label>机构名称</label>
							<input type="text" />
						</div>
						<div>
							<label>街道地址(首选)</label>
							<input type="text" />
						</div>
						<div>
							<label>街道地址(次选)*可选</label>
							<input type="text" />
						</div>
						
						<div>
							<label>市</label>
							<input type="text" />
							<input type="checkbox" />
						</div>
						<div>
							<label>州/省</label>
							<input type="text" />
						</div>
						<div>
							<label>邮政编码</label>
							<input type="text" />
							<input type="checkbox" />
						</div>
						<div>
							<label>国家/地区</label>
							<select>
								<option>中国</option>
								<option>美国</option>
							</select>
						</div>
						<div>
							<label>学科</label>
							<input type="text" />
						</div>
						<div>
							<label>参加课程开始时间</label>
							<input type="date" />
						</div>
						<div>
							<label>结束时间</label>
							<input type="date" />
						</div>
						<div>
							<a>添加</a>
							<a>去掉</a>
						</div>
					</div>
				</div>
			</div>
			<!--额外-->
			<div>
				<div>
					<div>
						<label>是否属于氏族或部落</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>氏族或部落名称</label>
						<input type="text" />
					</div>
				</div>
				<div>
					<label>使用的语言名称</label>
					<input type="text" />
				</div>
				<div>
					<a>添加</a>
					<a>去掉</a>
				</div>
				<div>
					<div>
						<label>过去五年是否曾去过任何国家/地区旅游</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<div>
							<label>国家/地区</label>
							<input type="text" />
						</div>
						<div>
							<a>添加</a>
							<a>去掉</a>
						</div>
					</div>
				</div>
				<div>
					<div>
						<label>是否属于、致力于、或为任何专业、社会或慈善组织而工作</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<div>
							<label>组织名称</label>
							<input type="text" />
						</div>
						<div>
							<a>添加</a>
							<a>去掉</a>
						</div>
					</div>
				</div>
				<div>
					<div>
						<label>是否有专业技能或培训，如强制、爆炸物、核能、生物或化学</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否曾服兵役</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<div>
							<label>国家/地区</label>
							<select>
								<option>中国</option>
								<option>美国</option>
							</select>
						</div>
						<div>
							<label>服务分支</label>
							<input type="text" />
						</div>
						<div>
							<label>排名/位置</label>
							<input type="text" />
						</div>
						<div>
							<label>军事专业</label>
							<input type="text" />
						</div>
						<div>
							<label>服兵役开始时间日期</label>
							<input type="date" />
						</div>
						<div>
							<label>结束时间</label>
							<input type="date" />
						</div>
						<div>
							<a>添加</a>
							<a>去掉</a>
						</div>
					</div>
				</div>
				<div>
					<div>
						<label>是否参与或菜价过准军事部队、治安单位、叛乱集团、游击队或叛乱组织</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
			</div>
			<!--工作/教育/培训信息END-->
			<hr />
			<!--安全和背景-->
			<div class="safe">
				<div>
					<div>
						<label>是否患有传染性疾病</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否有精神或身体疾病，可能对他人安全和福利构成威胁</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否吸毒或曾经吸毒</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否因犯罪或违法而逮捕或被判刑，即使后来受到赦免、宽恕或其他类似的裁决</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否违反过有关管控物资方面法律</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>您是来美国从事卖淫或非法商业性交吗？在过去十年中，您是否从事过卖淫或组织介绍过卖淫</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否曾经彩玉或意图从事洗钱活动</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否曾在美国或美国意外的地方犯有或密谋人口走私罪</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否故意资助、教唆、协助或勾结某个人，而这个人在美国或美国以外的地方曾犯有或密谋了严重的人口走私案</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否是曾在美国或美国以外犯有或密谋人口走私案的配偶或子女？是否在最近5年里从走私活动中获得过好处</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否在美国企图从事间谍活动，破坏活动，违反出口管制或任何其他非法活动</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否在美国企图从事恐怖活动，还是曾经参与过恐怖行动</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否有意向向恐怖分子或组织提供财政支援或其他支持</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否是恐怖组织的成员或代表</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否曾经下令、煽动、承诺、协助或以其他方式参与种族灭绝</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否曾经犯下、下令、煽动协助或以其他方式参与过酷刑</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否曾经犯下、下令、煽动协助或以其他方式参与过法外杀戮、政治杀戮或其他暴力行为</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否参与招募或使用童兵</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>担任官员时，是否曾经负责或直接执行特别严重的侵犯宗教自由的行为</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否曾经直接参与或建立执行人口控制措施，强迫妇女对其自由选择进行堕胎，或是否有男人或女人
违反其自由意志进行绝育</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否曾经直接参与人体器官或身体组织的强制移植</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否曾通过欺诈或故意虚假陈述或其他非法手段获得或协助他人获得美国签证，入境美国或获取任何
其他移民福利</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否曾被驱逐出境</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>在过去的5年中是否参加过远程访问或不可受理的听证会</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否曾经非法存在，超出了入境官员的时间，或以其他方式违反了美国的签证条款</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>您是否曾经拒绝将一在美国境外的美籍儿童的监护权移交给一被美国法庭批准享有法定监护权的人</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否违反了法律或规定在美国进行过投票选举</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否为逃税放弃过美国公民身份</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div>
					<div>
						<label>是否曾在1996年11月30日之后以学生身份到美国的一公立小学或公立中学就读而没有向学校补交费用</label>
						<input type="radio" />是
						<input type="radio" />否
					</div>
					<div>
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
			</div>
			<!--安全和背景END-->
		</div>
	</body>
			<script type="text/javascript" src="js/jquery-1.10.2.js" ></script>
		<script>
			$(function(){
				//旅伴信息
				$(".companyInfo").change(function(){
					var companyVal = $("input[name=companyInfo]:checked").val();
					if(companyVal == 1){
						$(".teamture").show();
					}else {
						$(".teamture").hide();
					}
				});
				//旅伴信息--是否作为团队或组织的一部分旅游
				$(".team").change(function(){
					var teamVal = $("input[name=team]:checked").val(); 
					if(teamVal == 1){
						$(".teamnameture").show();
						$(".teamnamefalse").hide();
					}else {
						$(".teamnameture").hide();
						$(".teamnamefalse").show();
					}
				});
				//旅伴信息END
				
				//以前的美国旅游信息
				//(1)是否去过美国
				$(".goUS").change(function(){
					var goUS = $("input[name=goUS]:checked").val();
					if(goUS == 1){
						$(".goUSInfo").show();
					}else{
						$(".goUSInfo").hide();
					}
					
				});
				$(".license").change(function(){
					var license = $("input[name=license]:checked").val();
					if(license == 1){
						$(".driverInfo").show();
					}else{
						$(".driverInfo").hide();
					}
				});
				//(2)是否有美国签证
				$(".visaUS").change(function(){
					var visaUS = $("input[name=visaUS]:checked").val();
					if(visaUS == 1){
						$(".dateIssue").show();
					}else {
						$(".dateIssue").hide();
					}
				});
				$(".lose").change(function(){
					var lose = $("input[name=lose]:checked").val();
					if(lose == 1){
						$(".yearExplain").show();
					}else {
						$(".yearExplain").hide();
					}
				});
				$(".revoke").change(function(){
					var revoke = $("input[name=revoke]:checked").val();
					if(revoke == 1){
						$(".explain").show();
					}else {
						$(".explain").hide();
					}
				});
				$(".refuse").change(function(){
					var refuse = $("input[name=refuse]:checked").val();
					if(refuse == 1){
						$(".refuseExplain").show();
					}else {
						$(".refuseExplain").hide();
					}
				});
				$(".onceLegitimate").change(function(){
					var onceLegitimate = $("input[name=onceLegitimate]:checked").val();
					if(onceLegitimate == 1){
						$(".onceExplain").show();
					}else {
						$(".onceExplain").hide();
					}
				});
				$(".onceImmigration").change(function(){
					var onceImmigration = $("input[name=onceImmigration]:checked").val();
					if(onceImmigration == 1){
						$(".immigrationExplain").show();
					}else {
						$(".immigrationExplain").hide();
					}
				});
				
//				亲属信息
				$(".fatherUS").change(function(){
					var fatherUS = $("input[name=fatherUS]:checked").val();
					if(fatherUS == 1){
						$(".fatherUSYes").show();
					}else {
						$(".fatherUSYes").hide();
					}
				});
				$(".motherUS").change(function(){
					var motherUS = $("input[name=motherUS]:checked").val();
					if(motherUS == 1){
						$(".motherUSYes").show();
					}else {
						$(".motherUSYes").hide();
					}
				});
				$(".directRelatives").change(function(){
					var directRelatives = $("input[name=directRelatives]:checked").val();
					if(directRelatives == 1){
						$(".directRelativesYes").show();
						$(".directRelativesNo").hide();
					}else {
						$(".directRelativesYes").hide();
						$(".directRelativesNo").show();
					}
				});
				
//				以前工作经历
				$(".beforeWork").change(function(){
					var beforeWork = $("input[name=beforeWork]:checked").val();
					if(beforeWork == 1){
						$(".beforeWorkInfo").show();
					}else {
						$(".beforeWorkInfo").hide();
					}
				});
			})
		</script>
</html>

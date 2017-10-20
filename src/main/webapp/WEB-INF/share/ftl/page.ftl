<div class="vkoPagination">
	<div class="vkop_listlen">
		<span class="vkop_listlen_tag">显示</span> 
		<select class="vkop_listlen_combox" unformat="true" name="numPerPage" onchange="<#if isDialog>dialogPageBreak<#else>navTabPageBreak</#if>({numPerPage:this.value},'${rel!''}')">
			<#list list as i>
				<option value="${i}" <#if (pager??&&i=pager.pageSize)>selected="selected"</#if>>${i}</option>
			</#list>
		</select>
		<span class="vkop_listlen_tag">条，共<#if pager??>${pager.recordCount!"0"}<#else>0</#if>条</span>
	</div>
	<div class="pagination" rel="${rel!''}" targetType="<#if isDialog>dialog<#else>navTab</#if>" totalCount="<#if pager??>#{pager.recordCount!"0"}<#else>0</#if>" numPerPage="<#if pager??>${pager.pageSize!"0"}<#else>0</#if>" pageNumShown="${pageNumShown!"10"}" currentPage="<#if pager??>${pager.pageNumber!"1"}<#else>1</#if>"></div>
</div>
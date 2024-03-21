<#macro insertLayout
	_layoutModel
>
	${dataFactory.toInsertSQL(_layoutModel)}

	${dataFactory.toInsertSQL(dataFactory.newLayoutFriendlyURLModel(_layoutModel))}
</#macro>

<#include "../../../../../../../../../../../../../../../util/portal-tools-sample-sql-builder/src/main/resources/com/liferay/portal/tools/sample/sql/builder/dependencies/counters.ftl">

COMMIT_TRANSACTION
<!DOCTYPE html>
<html lang="en">
<head>
   <title>集团信息</title>
   <#include "classpath:com/cloudcode/framework/common/ftl/head.ftl"/>
  <#include "classpath:com/cloudcode/framework/common/ftl/vendor.ftl"/>
   <script type="text/javascript">
	var params = $.cc.getIframeParams();
	var textField = params.textField;
	var url = params.url;
	var width = 400;
	var height = 400;
	var treeconfig = {
		id : 'tree',
		url : '../resourcefile/queryIconFilePathList',
		onDblClick : onDblClick,
		params : params.params
	};
	function onDblClick( treeNode) {
		//ok();
	}
	function ok() {
		var selectNodes = $.fn.zTree.getZTreeObj("tree").getSelectedNodes();
		if (selectNodes != null && selectNodes.length > 0) {
			var selectNode = selectNodes[0];
			if(selectNode.isParent==true){
				Dialog.infomsg("请选择子节点！！");
				return;
			}
			textField.val(selectNode.id);
			params.onChange();
			Dialog.close();
			textField.focus();
		}else{
			Dialog.infomsg("请选中一条数据！！");
		}
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<span xtype="tree" configVar="treeconfig"></span>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'确定' , onClick : ok "></span>
	</div>
</body>
</html>
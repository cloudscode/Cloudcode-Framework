<!DOCTYPE html>
<html lang="en">
<head>
   <title>请选择</title>
   <#include "classpath:com/cloudcode/framework/common/ftl/head.ftl"/>
   <#include "classpath:com/cloudcode/framework/common/ftl/require.ftl"/>
   <script type="text/javascript">
  var treeconfig = {};
  var a={};
  
   requirejs(['main','tree','Dialog'], function( $ ,tree,Dialog) {
	var params = $.cc.getIframeParams();
	var span = params.span;
	var hiddenField = params.hiddenField;
	var baseValue = hiddenField.val();
	var textField = params.textField;
	var url = params.url;
	var noCheckParent = params.config.noCheckParent;
	params.params = params.params || {};
	$.extend(params.params, span.data('params'));

	var selectType = params.config.many == true ? 'many' : 'radio';

	var selectUserList = [];

	var check = {
		enable : true,
		chkStyle : "radio",
		radioType : 'all'
	};
	if (selectType == 'many') {
		check = {
			enable : true,
			chkboxType : {
				"Y" : "",
				"N" : ""
			}
		}
	}

	 treeconfig = {
		id : 'tree',
		check : check,
		url : url,
		onDblClick : onDblClick,
		params : params.params,
		onCheck : onCheck,
		dataLoad : dataLoad
	};
	function onDblClick(treeNode) {
		//ok();
	}
	function dataLoad(treeNode) {
		if (treeNode == null) {
			var zTree = $.cc.tree.getTree('tree')
			checkNode(zTree, zTree.getNodes());
		} else {
			var children = treeNode.children;
			checkNode($.cc.tree.getTree('tree'), children);
		}
	}

	function checkNode(zTree, treeNodes) {
		if (treeNodes) {
			for (var j = 0; j < treeNodes.length; j++) {
				var treeNode = treeNodes[j];
				if (treeNode) {
					if(treeNode.nocheck==true){
						zTree.hideNode(treeNode);
						continue;
					}
					if (noCheckParent == true) {
						if (treeNode.leaf == 0) {
							treeNode.nocheck = true;
							zTree.updateNode(treeNode);
						}
					}
					var children = treeNode.children;
					checkNode(zTree, children);
				}
				if (isInSelectUserList(treeNode.id)) {
					zTree.checkNode(treeNode, true, true);
				}
			}
		}
	}
	function onCheck(treeNode) {
		if (treeNode.checked) {
			addSelectUserList(treeNode);
		} else {
			removeSelectUserList(treeNode.id);
		}
	}
	a.ok =function() {
		var id = "";
		var text = "";
		if (selectUserList.length > 0) {
			for (var i = 0; i < selectUserList.length; i++) {
				var selectUser = selectUserList[i];
				id += selectUser.id + ",";
				text += selectUser.text + ",";
			}
			if (id != "") {
				id = id.substr(0, id.length - 1);
				text = text.substr(0, text.length - 1);
			}
		}
		hiddenField.val(id);
		textField.val(text);
		if (selectType == 'radio') {
			params.onChange(selectUserList[0], baseValue != id);
		} else {
			params.onChange(selectUserList, baseValue != id);
		}
		Dialog.closethis();
		textField.focus();
	}

	var ids = "";//hiddenField.val();
	var texts ="";// textField.val();
	var idsarr = ids.split(",");
	var textsarr = texts.split(",");
	for (var i = 0; i < idsarr.length; i++) {
		if (idsarr[i] != null && idsarr[i] != '' && idsarr[i] != 'root') {
			selectUserList.push({
				id : idsarr[i],
				text : textsarr[i]
			});
		}
	}

	function init() {
		renderSpanUserHtml();
	}

	function renderSpanUserHtml() {
		var html = "";
		for (var i = 0; i < selectUserList.length; i++) {
			var selectUser = selectUserList[i];
			html += '&nbsp;<span>' + selectUser.text
			html += '<img src="/cccommon/images/extjsico/delete2.gif" onclick="unSelect(\''
					+ selectUser.id + '\')" />|</span>';
		}
		$('#userspan').html(html);
	}
	function removeSelectUserList(id) {
		var userList = [];
		for (var i = 0; i < selectUserList.length; i++) {
			var selectUser = selectUserList[i];
			if (selectUser.id != id) {
				userList.push(selectUser);
			}
		}
		selectUserList = userList;
		renderSpanUserHtml();
	}

	function isInSelectUserList(id) {
		var userList = [];
		for (var i = 0; i < selectUserList.length; i++) {
			var selectUser = selectUserList[i];
			if (selectUser.id == id) {
				return true;
			}
		}
		return false;
	}
	function unSelect(id) {
		//pagelistobject.unSelectRow('id', id);
		removeSelectUserList(id);
	}
	function addSelectUserList(object) {
		if (selectType == 'radio') {
			selectUserList = [ object ];
			renderSpanUserHtml();
		} else {
			var isin = isInSelectUserList(object.id);
			if (isin == false) {
				selectUserList.push(object);
				renderSpanUserHtml();
			}
		}
	}
	$("[xtype]").each(function() {
		$(this).render('initRender');
	});
	
});
</script>
</head>

<body data-spy="scroll" data-target=".bs-docs-sidebar" data-twttr-rendered="true"> 


<script type="text/javascript">

</script>
<div xtype="hh_content">
		<span xtype="tree" configVar="treeconfig"></span>
		<hr />
		<span id="userspan"></span>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'确定' , onClick : a.ok "></span>
	</div>
</body>
</html>
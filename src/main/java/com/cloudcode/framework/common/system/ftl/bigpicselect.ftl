<!DOCTYPE html>
<html lang="en">
<head>
   <title>集团信息</title>
   <#include "classpath:com/cloudcode/framework/common/ftl/head.ftl"/>
  <#include "classpath:com/cloudcode/framework/common/ftl/vendor.ftl"/>
  <script type="text/javascript">
	var params = $.cc.getIframeParams();
	var requestParams = {path:params.params.path};
	var textField = params.textField;

	var width = 640;
	var height = 500;

	var hidata = {};
	var pathList = [];

	function ok(data) {
		if (data.leaf == 0) {
			requestParams.node = data.id;
			requestParams.path = data.id;
			loadData();
		} else {
			if (textField) {
				textField.val(data.id);
			}
			params.onChange(data);
			Dialog.close();
			if (textField) {
				textField.focus();
			}
		}
	}

	var gridViewConfig = {
		width : params.config.width,
		onClick : function(data) {
			ok(data);
		},
		render : false
	};

	function renderList(dataList) {
		for (var i = 0; i < dataList.length; i++) {
			dataList[i].img = dataList[i].id;
			dataList[i].text = dataList[i].id.substr(dataList[i].id
					.lastIndexOf('/') + 1);
			if (dataList[i].leaf == 0) {
				dataList[i].img = '/hhcommon/images/framework/wenjianjia.jpg';
			}
		}
		$('#gridView').setConfig({
			data : dataList
		});
		$('#gridView').render();
	}

	function loadData() {
		if (hidata[requestParams.path]) {
			pathList.push(requestParams.path);
			renderList(hidata[requestParams.path]);
			if (pathList.length > 1) {
				$('#backbtn').undisabled();
			} else {
				$('#backbtn').disabled();
			}
		} else {
			Request.request('system-ResourceFile-queryIconFilePathList', {
				data : requestParams
			}, function(dataList) {
				pathList.push(requestParams.path);
				hidata[requestParams.path] = dataList;
				renderList(dataList);
				if (pathList.length > 1) {
					$('#backbtn').undisabled();
				} else {
					$('#backbtn').disabled();
				}
			});
		}
	}

	function init() {
		requestParams.node = 'root';
		loadData();
	}
	function doBack() {
		var tempPathList = [];
		var path ='';
		for(var i=0;i<pathList.length-1;i++){
			tempPathList.push(pathList[i]);
			path=pathList[i];
		}
		pathList=tempPathList;
		
		if (pathList.length > 1) {
			$('#backbtn').undisabled();
		} else {
			$('#backbtn').disabled();
		}
		requestParams.node = path;
		requestParams.path = path;
		renderList(hidata[path]);
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span id="backbtn" xtype="button"
			config="onClick:doBack,text:'后退' , icon : 'ui-icon-arrow-1-w' "></span>
	</div>
	<div style="padding: 25px;">
		<span id="gridView" xtype="gridView" configVar="gridViewConfig"></span>
	</div>
</body>
</html>
<!-- Placed at the end of the document so the pages load faster -->
<script src="/cccommon/static/jquery/ui/bootstrap/assets/js/vendor/jquery-1.10.2.js" type="text/javascript"></script>
<script src="/cccommon/static/jquery/ui/bootstrap/assets/js/vendor/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<script src="/cccommon/static/jquery/ui/bootstrap/assets/js/vendor/bootstrap.js" type="text/javascript"></script>
<script src="/cccommon/static/jquery/ui/bootstrap/assets/js/vendor/holder.js" type="text/javascript"></script>
<script src="/cccommon/static/jquery/ui/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
<!--<script src="/cccommon/static/jquery/ui/bootstrap/assets/js/vendor/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<script src="/cccommon/static/jquery/framework/ajaxframework.js" type="text/javascript"></script>-->
<script src="/cccommon/static/jquery/ztree/3.5.15/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script  type="text/javascript">
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });0
    return o;
};
</script>
<script src="/cccommon/static/jquery/msg/wHumanMsg.js" type="text/javascript"></script>
<!--<script src="/cccommon/static/jquery/ui/bootstrap/third-party/jquery.iframeDialog.js" type="text/javascript"></script>-->
<script src="/cccommon/opensource/ckeditor/ckeditor.js" type="text/javascript"></script>
<script src="/cccommon/opensource/ckeditor/config.js" type="text/javascript"></script>
<script src="/cccommon/opensource/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<#--
<script src="/cccommon/static/jquery/framework/mainframe.js" type="text/javascript"></script>
<script src="/cccommon/static/jquery/framework/ccqueryui.js" type="text/javascript"></script>-->
<script src="/cccommon/static/jquery/validation/jquery.validationEngine-zh_CN.js" type="text/javascript"></script>
<script src="/cccommon/static/jquery/validation/jquery.validationEngine.js" type="text/javascript"></script>
<script src="/cccommon/opensource/jquery/layout/jquery.layout-latest.js" type="text/javascript"></script>

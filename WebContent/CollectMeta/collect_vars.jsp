<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script src="${ContextPath}/js/jquery.form.min.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Controllers/date_controller.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Controllers/yearMonth_controller.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Controllers/percent_controller.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Controllers/onlyRead_controller.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Controllers/uploadFile_controller.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Controllers/mulSelect_controller.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Rule/logicchecknumberrules.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/initRules.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Rule/logicchecklogisticrules.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/logiccheckdaterules.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/checkDataLength.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Rule/logiccheckstringrules.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/jscheckfuncsfactory.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/checkentitycount.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/logiccheckMulCols.js"></script>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/checkIfExistAttach.js"></script>
<script type="text/javascript">
var jqGridConfig;//存贮jqgrid的配置信息
var lastsel;//最后选择行号
var titleValues;//表的可编辑列名
var tableId='';//实体表的Id
var tableName;//实体中文名
var remark;//实体说明
var titleNames=new Array();//实体header
var controllerDic;
var primaryKey;//条目的主键
var attrId;//主属性
var seqNo;//序号
var records;//总记录数
var sortorder;//排序方式
var sortname;//排序字段
var jsCheckfunParamsDic;//前台js验证函数参数及其值的字典
var jsCheckFunNameDic;//
var contextPath="${ContextPath}";//绝对路径
var collegeId="${unitId}";//学校代码
var disciplineId="${discId}";//学科代码
var unitId="${unitId}";//学校代码
var discId="${discId}";//学科代码
var userId="${userSession.id}"; // 用户Id
var isSelect=true;//是否可选择
var maxNum;//记录的最大条数
var viewType;//页面显示类型
var version=-1;
var dataUrl='';//获取jqGrid的数据的URL
var batchUrl='${ContextPath}/Collect/toCollect/JqOper/batchSubmit/';//批量添加的URL
var uploadUrl = '';//上传附件的url
var sortUrl = '${ContextPath}/Collect/toCollect/JqOper/reorder';
var percentUrl = '${ContextPath}/Collect/toCollect/percentdialog';
var isAllData = true;//是所有的数据
var rowNumber= false;//显示默认序号
var attachId;//附件号(每一行附件临时使用)
var attachElement='';//附件元素
var attachOperElem='';//附件操作元素
var fileValues= new Array();//上传文件的列
var currentSaveRowId='';//正在保存的行id
var currentJqGridId='';//正在保存的jqgirdID
var searchRule='';
var initJqRules= new Array();//对于需要初始化的表格预先配置
var controlTypeRules = new Array();//对于空间类型的规则
var templateId='';//采集项模板ID
var entityRule;//实体检查规则
</script>
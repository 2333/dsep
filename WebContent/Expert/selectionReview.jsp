<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>

<style type="text/css">
${demo.css}
</style>
</head>
<div id="container"
	style="min-width: 310px; height: 400px; margin: 0 auto"></div>
<script type="text/javascript">
	function getData() {
		url = '${ContextPath}/expert/selectExpertReview/'+currentBatchId;
		$.post(url, function(data) {
			obj = eval(data);
			test(obj[0], obj[1], obj[3], obj[2]);
		});
	}
	
	getData();			

	function test(arg1, arg2, arg3, arg4) {
		$('#container').highcharts({
			chart : {type : 'column'},
			title : {text : '统计信息'},
			subtitle : {text : '按照学科大类分类'},
			xAxis : {categories : arg1},
			yAxis : {min : 0,title : {text : '专家人数 '}},
			tooltip : {
				headerFormat : '<span style="font-size:10px">{point.key}学科</span><table>',
				pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
										+ '<td style="padding:0"><b>{point.y:.0f}人</b></td></tr>',
				footerFormat : '</table>',
					shared : true,
					useHTML : true
				},
			plotOptions : {
				column : {
					pointPadding : 0.2,
					borderWidth : 0
				}
			},
			series : [ arg2, arg3, arg4 ]
		});
	};
</script>
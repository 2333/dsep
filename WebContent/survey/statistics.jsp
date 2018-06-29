<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>问卷统计
	</h3>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">当前问卷：</span>
			</td>
			<td>
				<label>关于毕业研究生的满意度调查</label>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<span class="TextFont">选择题目：</span>
			</td>
			<td>
				<select id="questions" style="width:153px">
					<option value="no1">1.您的性别</option>
					<option value="no1">2.您的民族是</option>
					<option value="no1">3.您的婚姻状况</option>
					<option value="no1">4.您的毕业专业</option>
					<option value="no1">5.您的学历层次</option>
					<option value="no1">6.您的毕业时间</option>
					<option value="no1">7.您的毕业去向</option>
				</select>
			</td>
		</tr>
	</table>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">选择您要显示的图表类型：</span>
			</td>
			<td>
				<input type="checkbox" name='displayTable' id='displayTable'
				checked="checked"
				>表格&nbsp;&nbsp;
			</td>
			<td>
				<input type="checkbox" name='displayPie' id='displayPie'
				checked="checked"
				>饼状图&nbsp;&nbsp;
			</td>
			<td>
				<input type="checkbox" name='displayCol' id='displayCol'
				checked="checked"
				>柱状图&nbsp;&nbsp;
			</td>
			<td>
				<input type="checkbox" name='displayBar' id='displayBar'
				checked="checked"
				>条形图&nbsp;&nbsp;
			</td>
			<td>
				<input type="checkbox" name='displayLine' id='displayLine'
				checked="checked"
				>折线图&nbsp;&nbsp;
			</td>
		</tr>
	</table>
	<table class="layout_table right">
		<tr>	
             <td>
				<a id="quesreport_download_btn" class="button " href="#"><span class="icon icon-download"></span>下载调查报告</a>
			 </td>
		</tr>
    </table>
</div>

<div class="layout_holder">
	 <div id="chart" style="min-width: 310px; height: 200px; max-width: 600px; margin: 20px auto;">
	 <table id="chart_container"></table>
	 </div>
	 <div id="pie_container" style="width: 600px; height: auto;float:left;display:inline"></div>
	 <div id="column_container" style="width:600px; height: auto;float:left;display:inline"></div>
	 <div id="bar_container" style="width: 600px; height: auto;float:left;display:inline"></div>
	 <div id="line_container"style="width: 600px; height: auto;float:left;display:inline"></div>
</div>
<script src="${ContextPath}/js/exporting.js"></script>
<script type="text/javascript">
    var chartExists=1;
    var pieExists=1;
    var colExists=1;
    var barExists=1;
    var lineExists=1;
    
    
	$(document).ready(function(){
		$( 'input[type=submit], a.button , button' ).button();
		
		$('#pie_container').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: 1,//null,
	            plotShadow: false
	        },
	        title: {
	            text: '您的毕业去向？'
	        },
	        exporting: { enabled:false},
	        tooltip: {
	    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                    style: {
	                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                    }
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: '毕业去向',
	            data: [
	                ['升学',   45.0],
	                ['签约',       26.8],
	                {
	                    name: '出国',
	                    y: 12.8,
	                    sliced: true,
	                    selected: true
	                },
	                ['定向委培',    8.5],
	                ['自主创业',     6.2],
	                ['待业',   0.7]
	            ]
	        }]
	    });
		
		$('#column_container').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: '您的毕业去向？'
            },
            exporting: { enabled:false},
            xAxis: {
                categories: [
                    '升学',
                    '签约',
                    '出国',
                    '定向委培',
                    '自主创业',
                    '待业',
                ]
            },
            yAxis: {
                min: 0,
                title: {
                    text: '比例（100%）'
                }
            },
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f} %</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
            series: [{
                name: '毕业去向',
            	data: [45.0,26.8,12.8,8.5,6.2,0.7]
            }]
        });
		
		 $('#bar_container').highcharts({
	            chart: {
	                type: 'bar'
	            },
	            title: {
	                text: '您的毕业去向'
	            },
	            exporting: { enabled:false},
	            xAxis: {
	                categories: [  '升学',
	                               '签约',
	                               '出国',
	                               '定向委培',
	                               '自主创业',
	                               '待业'],
	                title: {
	                    text: null
	                }
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: '比例（100%）',
	                    align: 'high'
	                },
	                labels: {
	                    overflow: 'justify'
	                }
	            },
	            tooltip: {
	                valueSuffix: ' %'
	            },
	            plotOptions: {
	                bar: {
	                    dataLabels: {
	                        enabled: true
	                    }
	                }
	            },
	            legend: {
	                layout: 'vertical',
	                align: 'right',
	                verticalAlign: 'top',
	                x: -40,
	                y: 100,
	                floating: true,
	                borderWidth: 1,
	                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor || '#FFFFFF'),
	                shadow: true
	            },
	            credits: {
	                enabled: false
	            },
	            series: [{
	                name: '毕业去向',
	                data: [45.0,26.8,12.8,8.5,6.2,0.7]
	            }]
	        });
		  $('#line_container').highcharts({
	            title: {
	                text: '您的毕业去向',
	            },
	            exporting: { enabled:false},
	            xAxis: {
	                categories: ['升学',
	                               '签约',
	                               '出国',
	                               '定向委培',
	                               '自主创业',
	                               '待业']
	            },
	            yAxis: {
	                title: {
	                    text: '比例 (100%))'
	                },
	                plotLines: [{
	                    value: 0,
	                    width: 1,
	                    color: '#808080'
	                }]
	            },
	            tooltip: {
	                valueSuffix: '%'
	            },
	            legend: {
	                layout: 'vertical',
	                align: 'right',
	                verticalAlign: 'middle',
	                borderWidth: 0
	            },
	            series: [{
	            	 name: '毕业去向',
		             data: [45.0,26.8,12.8,8.5,6.2,0.7]
	            }]
	        });
		  jQuery("#chart_container").jqGrid({
	   			datatype: "local",
	   		   	colNames:['选项', '小计', '比例'],
	   		   	colModel:[
	   		   		{name:'option',index:'option', width:300,align:"center"},
	   		   		{name:'sum',index:'sum', width:135,align:"center"},
	   		   		{name:'percentage',index:'percentage', width:135, align:"center"},
	   		  				
	   		   	],
	   		   	rowNum:10,
	   		   	height:"100%",
	   		   	rowList:[10,20,30],
	   		   	sortname: 'name',
	   		    viewrecords: true,
	   		    sortorder: "desc",
	   		    caption: "您的毕业去向"
	   		});
	   		
	   		var mydata= [
	   		             {option:"升学",sum:"45",percentage:"45.0%"},
	   		             {option:"签约",sum:"27",percentage:"26.8%"},
	   		         	 {option:"出国",sum:"13",percentage:"12.8%"},
	   		     		  {option:"定向委培",sum:"8",percentage:"8.5%"},
	   			  		  {option:"自主创业",sum:"6",percentage:"6.2%"},
	   					 {option:"待业",sum:"1",percentage:"0.7%"},
	   		             ];
	   		for( var i=0;i<mydata.length;i++ )
	   		{
	   			jQuery("#chart_container").addRowData(i+1,mydata[i]);	
	   		}
		$('#displayPie').click(function(){
			if(!pieExists){
		  		pieExists=1;
		  		$("#pie_container").css('visibility','visible');
	   		}
		  	else 
		  	{
		  		pieExists=0;
		  		$("#pie_container").css('visibility','hidden');
		  	}
		});
		
		$('#displayCol').click(function(){
			if(!colExists){
		  		colExists=1;
		  		$("#column_container").css('visibility','visible');
	   		}
		  	else 
		  	{
		  		colExists=0;
		  		$("#column_container").css('visibility','hidden');
		  	}
		 });
		$('#displayBar').click(function(){
			if(!barExists){
		  		barExists=1;
		  		$("#bar_container").css('visibility','visible');
	   		}
		  	else 
		  	{
		  		barExists=0;
		  		$("#bar_container").css('visibility','hidden');
		  	}
		});
		$('#displayLine').click(function(){
			if(!lineExists){
		  		lineExists=1;
		  		$("#line_container").css('visibility','visible');
	   		}
		  	else 
		  	{
		  		lineExists=0;
		  		$("#line_container").css('visibility','hidden');
		  	}
		 });
		$('#displayTable').click(function(){
		  	if(!chartExists){
		  		chartExists=1;
		  		$("#chart").css('visibility','visible');
	   		}
		  	else 
		  	{
		  		chartExists=0;
		  		$("#chart").css('visibility','hidden');
		  	}
		});
	})
</script>

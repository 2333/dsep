<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<div class="MOM">
    <div class="MOM-box" id="divSearch" style="">
        <div class="MOM-bt">
            <div class="MOM-lbt">
                <h3>问卷调查模板</h3> &gt; <span id="spanKey">员工满意度调查</span>
            </div>
            <div class="MOM-rbt">
                 筛选问卷调查模板：
                <select id="ddlQCount">
                   <option value="" selected="selected">所有题目数</option>
                    <option value="0,20">0-20题</option>
                    <option value="21,40">21-40题</option>
                    <option value="41,60">41-60题</option>
                    <option value="61,80">61-80题</option>
                    <option value="81,500">80题以上</option>
                </select>
           </div>
        </div>
        <div id="divSearchResult">
        	<ul class="MOM-list" id="momlist">
        		<li>
        			<div  class="MOM-BCB-L">
        				<a href="#">
        					<span >企业员工满意度调查问卷
        					</span>
        				</a>
        			</div>
        			<div class="divclear">
        			</div>
        		</li>
        		<li>
        			<div class="MOM-BCB-L" onclick="return doPreview(3685290,this.parentNode);">
        				<a href="#">
        					<span>企业社会责任对员工工作满意度的影响调查
        					</span>
        				</a>
        			</div>
        			<div class="divclear">
        			</div>
        		</li>
        		<li>
        			<div class="MOM-BCB-L" onclick="return doPreview(3662767,this.parentNode);">
        				<a href="#">
        					<span>企业员工满意度调查
        					</span>
        				</a>
        			</div>
        			<div class="divclear">
        			</div>
        		</li>
        		<li>
        			<div class="MOM-BCB-L" onclick="return doPreview(3684928,this.parentNode);">
        				<a href="#">
        					<span>客户呼叫服务中心员工满意度调查
        					</span>
        				</a>
        			</div>
        			<div class="divclear">
        			</div>
        		</li>
        		<li>
        			<div class="MOM-BCB-L" onclick="return doPreview(3684929,this.parentNode);">
        				<a href="#">
        					<span>(餐饮业)饭店一线员工工作满意度调查
        					</span>
        				</a>
        			</div>
        			<div class="divclear">
        			</div>
        		</li>
        		<li>
        			<div class="MOM-BCB-L" onclick="return doPreview(3662691,this.parentNode);">
        				<a href="#">
        					<span>(活动)员工集体活动的满意度调查
        					</span>
        				</a>
        			</div>
        			<div class="divclear">
        			</div>
        		</li>
        	</ul>
        	<div style="text-align: center;margin-bottom:15px;" id="TE-pager">
        		<a  href="javascript:nextPage();">下一页</a>
        	</div>
        </div>
    	<div style="clear: both; height:30px;">
    	</div>
    </div>
</div>

<style>

</style>
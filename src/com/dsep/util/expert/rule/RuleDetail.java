package com.dsep.util.expert.rule;

public class RuleDetail {
		public RuleDetail(String sequ, String chName, String itemName, String prior, String operator) {
			this.sequ = sequ;
			this.chName = chName;
			this.itemName = itemName;
			this.prior = prior;
			this.operator = operator;
		}
		
		private String sequ;
		private String chName;
		private String itemName;
		private String prior;
		private String operator;
		
		
		public String getSequ() {
			return sequ;
		}
		public void setSequ(String sequ) {
			this.sequ = sequ;
		}
		public String getChName() {
			return chName;
		}
		public void setChName(String chName) {
			this.chName = chName;
		}
		public String getItemName() {
			return itemName;
		}
		public void setItemName(String itemName) {
			this.itemName = itemName;
		}
		public String getPrior() {
			return prior;
		}
		public void setPrior(String prior) {
			this.prior = prior;
		}
		public String getOperator() {
			return operator;
		}
		public void setOperator(String operator) {
			this.operator = operator;
		}
}

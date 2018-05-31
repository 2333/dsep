<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/question">
		<div class="publishQ">
			<span class="publishQStem">
				<xsl:value-of select="qStem" />
			</span>
			<xsl:for-each select="qItems/singleItem">
				<xsl:if test="selectBoxNum=1">
					<br />
					<input type="radio" class="value">
						<xsl:attribute name="name">
  								<xsl:value-of select="parentQRefId" />
  						</xsl:attribute>
  						<xsl:attribute name="value">
  							<xsl:value-of select="value" />
  						</xsl:attribute>
						<xsl:attribute name="itemid">
  							<xsl:value-of select="itemId" />
  						</xsl:attribute>
					</input>
				</xsl:if>
				<xsl:value-of select="str1" />
				<xsl:if test="blank">
					<input type="text" class="value">
						<xsl:attribute name="name">
   							<xsl:value-of select="parentQRefId" />
   						</xsl:attribute>
						<xsl:attribute name="itemId">
							<xsl:value-of select="itemId" />
						</xsl:attribute>
					</input>
				</xsl:if>
				<xsl:value-of select="str2" />
			</xsl:for-each>
		</div>
	</xsl:template>
</xsl:stylesheet>

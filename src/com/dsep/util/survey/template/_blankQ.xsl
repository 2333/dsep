<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/question">
		<div class="publishQ">
			<span class="publishQStem">
				<xsl:value-of select="qStem"></xsl:value-of>
			</span>
			<span>
				<xsl:for-each select="qItems/singleItem">
					<xsl:if test="str1">
						<xsl:value-of select="str1" />
					</xsl:if>
					<xsl:if test="blank='1'">
						<input type="text" class="value" style="width:90px;border:0;border-bottom:solid 1px;">
							<xsl:attribute name="name">
    						<xsl:value-of select="parentQRefId" />
    						</xsl:attribute>
							<xsl:attribute name="itemId">
 							<xsl:value-of select="itemId" />
							</xsl:attribute>
						</input>
					</xsl:if>
					<xsl:if test="str2">
						<xsl:value-of select="str2" />
					</xsl:if>
				</xsl:for-each>
			</span>
		</div>
	</xsl:template>

</xsl:stylesheet>